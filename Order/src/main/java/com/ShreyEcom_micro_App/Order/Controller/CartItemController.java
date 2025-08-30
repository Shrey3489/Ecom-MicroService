package com.ShreyEcom_micro_App.Order.Controller;


import com.ShreyEcom_micro_App.Order.Dto.CartItemRequestDto;
import com.ShreyEcom_micro_App.Order.Dto.CartItemResponseDto;
import com.ShreyEcom_micro_App.Order.Service.cartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final cartItemService cartItemService;

    @PostMapping("/item")
    public ResponseEntity<String> save(@RequestBody CartItemRequestDto cartItem,
                               @RequestHeader("X-User-ID") Long userId)
    {
        if(!cartItemService.addToCart(userId,cartItem))
        {
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/item/{productId}")
    public ResponseEntity<Void> removeFromCart( @RequestHeader("X-User-ID")Long userId,@PathVariable Long productId)
    {
        boolean deleted = cartItemService.deleteItemFromCart(userId,productId);
        if(deleted)
        {
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/item")
    public ResponseEntity<List<CartItemResponseDto>> getAllItem(@RequestHeader("X-User-ID") Long userId)
    {
        List<CartItemResponseDto> locartItemList = cartItemService.getAllCartItemofUser(userId);
        if(locartItemList == null || locartItemList.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locartItemList);
    }
}
