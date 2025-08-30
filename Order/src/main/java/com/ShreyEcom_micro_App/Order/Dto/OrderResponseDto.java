package com.ShreyEcom_micro_App.Order.Dto;


import com.ShreyEcom_micro_App.Order.Entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private BigDecimal TotalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> items;
    private LocalDateTime createdDate;

}
