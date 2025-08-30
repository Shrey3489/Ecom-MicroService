package com.ShreyEcomMicro.product.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer quantity;
    private String imageUrl;
}
