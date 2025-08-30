package com.ShreyEcom_micro_App.Order.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto
{
    private Long productId;
    private Integer quantity;
}
