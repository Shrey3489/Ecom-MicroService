package com.ShreyEcom_micro_App.Order.Dto;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long productId;
    private Integer quantity;
}
