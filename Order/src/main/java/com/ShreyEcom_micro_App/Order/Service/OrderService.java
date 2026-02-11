package com.ShreyEcom_micro_App.Order.Service;


import com.ShreyEcom_micro_App.Order.Dto.OrderItemDTO;
import com.ShreyEcom_micro_App.Order.Dto.OrderNotificationResponceDTO;
import com.ShreyEcom_micro_App.Order.Repository.OrderRepository;
import com.ShreyEcom_micro_App.Order.Dto.OrderResponseDto;
import com.ShreyEcom_micro_App.Order.Entity.CartItem;
import com.ShreyEcom_micro_App.Order.Entity.Order;
import com.ShreyEcom_micro_App.Order.Entity.OrderItem;
import com.ShreyEcom_micro_App.Order.Entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final cartItemService cartItemService;

    private final OrderRepository orderRepository;

    private final RabbitTemplate rabbitTemplate;


    public Optional<OrderResponseDto> createOrder(Long userId)
    {
        List<CartItem> loCartItem =cartItemService.getAllCartItems(userId);
        if (loCartItem.isEmpty())
        {
            return Optional.empty();
        }
        BigDecimal totalPrice = loCartItem.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = loCartItem.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).collect(Collectors.toList());
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        cartItemService.clearCart(userId);

        OrderNotificationResponceDTO orderResponseDto = new OrderNotificationResponceDTO(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getOrderStatus(),
                getOrderItemDTO(savedOrder.getItems()),
                savedOrder.getTotalAmount(),
                savedOrder.getCreationDate()
        );
        rabbitTemplate.convertAndSend("order.exchange",
                "order.tracking",
                orderResponseDto);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private List<OrderItemDTO> getOrderItemDTO(List<OrderItem> items)
    {
        return items.stream().map(
                item -> new OrderItemDTO(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                )
        ).toList();
    }

    private OrderResponseDto mapToOrderResponse(Order savedOrder) {
        return new OrderResponseDto(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getItems().stream()
                        .map(orderitem -> new OrderItemDTO(
                                orderitem.getId(),
                                orderitem.getProductId(),
                                orderitem.getQuantity(),
                                orderitem.getPrice()
                        )).toList(),
                savedOrder.getCreationDate()
        );
    }
}
