package com.ShreyEcom_micro_App.NotificationMessage.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEventDTO {

    private long orderId;
    private long userId;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private LocalDateTime creationDate;

}
