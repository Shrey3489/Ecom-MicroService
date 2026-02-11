package com.ShreyEcom_micro_App.NotificationMessage;

import com.ShreyEcom_micro_App.NotificationMessage.Payload.OrderCreatedEventDTO;
import com.ShreyEcom_micro_App.NotificationMessage.Payload.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderMessageListner
{
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void HandleOrderEvent(OrderCreatedEventDTO orderCreatedEventDTO)
    {
        long orderId = orderCreatedEventDTO.getOrderId();
        OrderStatus orderStatus = orderCreatedEventDTO.getStatus();

        System.out.println("Order ID = "+orderId+" Status = "+orderStatus);
    }
}
