package com.ShreyEcom_micro_App.GateWay;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route("product-service", p ->
                        p.path("/api/product/**")
                                .filters(f ->
                                        f.circuitBreaker(config -> config
                                                .setName("Ecomservice")
                                                .setFallbackUri("forward:/fallback/products")))
//                                .filters(f -> f.rewritePath("/product(?<segment>/?.*)", "/api/product${segment}"))
                                .uri("lb://product-service"))
                .route("user-service", p ->
                        p.path("/api/users/**")
//                                .filters(f -> f.rewritePath("/users(?<segment>/?.*)", "/api/users${segment}"))
                                .uri("lb://user-service"))
                .route("order-service", p ->
                        p.path("/api/order/**","/api/cart/**")
//                                .filters(f -> f.rewritePath("/(?<segment>/?.*)", "/api/${segment}"))
                                .uri("lb://order-service"))
                .build();
    }
}
