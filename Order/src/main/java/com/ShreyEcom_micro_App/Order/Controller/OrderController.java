package com.ShreyEcom_micro_App.Order.Controller;

import com.ShreyEcom_micro_App.Order.Dto.OrderResponseDto;
import com.ShreyEcom_micro_App.Order.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestHeader("X-User-ID") Long userId) {
        return orderService.createOrder(userId)
                .map(orderResponseDto -> new ResponseEntity<>(orderResponseDto,HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
