package com.ShreyEcom_micro_App.NotificationMessage.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long Id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
