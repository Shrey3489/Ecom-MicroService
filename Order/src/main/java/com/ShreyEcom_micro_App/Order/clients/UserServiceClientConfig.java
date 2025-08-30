package com.ShreyEcom_micro_App.Order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig
{
    @Bean
    @LoadBalanced
    public RestClient.Builder userRestClient()
    {
        return RestClient.builder();
    }

    @Bean
    public UserServiceClient restUserInterface(RestClient.Builder userRestClient)
    {
        RestClient loRestClient = userRestClient.baseUrl("http://user-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,((request, response) ->
                        Optional.empty()))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(loRestClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserServiceClient.class);
    }
}
