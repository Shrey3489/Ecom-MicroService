package com.ShreyEcom_micro_App.Order.clients;

import com.ShreyEcom_micro_App.Order.Dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient
{
    @GetExchange("/api/users/get/{id}")
    UserResponse getUserDetail(@PathVariable Long id);
}
