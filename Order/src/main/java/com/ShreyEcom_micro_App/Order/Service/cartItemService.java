package com.ShreyEcom_micro_App.Order.Service;

import com.ShreyEcom_micro_App.Order.Dto.CartItemRequestDto;
import com.ShreyEcom_micro_App.Order.Dto.CartItemResponseDto;
import com.ShreyEcom_micro_App.Order.Dto.ProductResponseDto;
import com.ShreyEcom_micro_App.Order.Dto.UserResponse;
import com.ShreyEcom_micro_App.Order.Entity.CartItem;
import com.ShreyEcom_micro_App.Order.Repository.CartItemRepository;
import com.ShreyEcom_micro_App.Order.clients.ProductServiceClient;
import com.ShreyEcom_micro_App.Order.clients.UserServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class cartItemService
{

    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    @CircuitBreaker(name = "productservice", fallbackMethod = "addtoCartFallBack")
    public boolean addToCart(Long userId, CartItemRequestDto cartItemRequest)
    {
        ProductResponseDto productResponseDto = productServiceClient.getProductDetail(cartItemRequest.getProductId());
        if(productResponseDto == null || productResponseDto.getQuantity() <= 0)
        {
            return false;
        }
        UserResponse loUserResponse = userServiceClient.getUserDetail(userId);
        if(loUserResponse == null)
        {
            return false;
        }
        CartItem existingCart = cartItemRepository.findByUserIdAndProductId(userId,cartItemRequest.getProductId());
        if(existingCart != null)
        {
            existingCart.setQuantity(existingCart.getQuantity() + cartItemRequest.getQuantity());
            existingCart.setPrice(productResponseDto.getPrice());
            cartItemRepository.save(existingCart);
        }
        else
        {
            CartItem cartItem1 = new CartItem();
            cartItem1.setProductId(cartItemRequest.getProductId());
            cartItem1.setQuantity(cartItemRequest.getQuantity());
            cartItem1.setPrice(productResponseDto.getPrice());
            cartItem1.setUserId(userId);
            cartItemRepository.save(cartItem1);
        }

        return true;
    }

    public boolean addtoCartFallBack(Long userId,
                                     CartItemRequestDto cartItemRequest
                                     , Exception exception)
    {
        System.out.println("addtoCartFallBack");
        return false;
    }

    public boolean deleteItemFromCart(Long userId, Long productId)
    {
       CartItem loCartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
       if(loCartItem != null)
       {
           cartItemRepository.delete(loCartItem);
       }
        return true;
    }

    public List<CartItemResponseDto> getAllCartItemofUser(Long userId)
    {
        List<CartItemResponseDto> collect = cartItemRepository.findAllByUserId(userId)
                .stream().map(this::MapCartItemToCartItemResponseDto).collect(Collectors.toList());
        return collect;
    }

    public List<CartItem> getAllCartItems(Long userId)
    {
        return  cartItemRepository.findAllByUserId(userId);
    }

    private CartItemResponseDto MapCartItemToCartItemResponseDto(CartItem cartItem) {
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setProductId(cartItem.getProductId());
        cartItemResponseDto.setQuantity(cartItem.getQuantity());
        return cartItemResponseDto;
    }

    public void clearCart(Long userId)
    {
        cartItemRepository.deleteByUserId(userId);
    }
}
