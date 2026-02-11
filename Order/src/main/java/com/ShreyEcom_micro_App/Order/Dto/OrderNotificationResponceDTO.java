package com.ShreyEcom_micro_App.Order.Dto;

import com.ShreyEcom_micro_App.Order.Entity.OrderItem;
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
public class OrderNotificationResponceDTO {

    private long orderId;
    private long userId;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private LocalDateTime creationDate;

}
