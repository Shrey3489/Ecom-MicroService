package com.ShreyEcom_micro_App.Order.clients;

import com.ShreyEcom_micro_App.Order.Dto.ProductResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient
{
    @GetExchange("/api/product/{id}")
    ProductResponseDto getProductDetail(@PathVariable Long id);
}
