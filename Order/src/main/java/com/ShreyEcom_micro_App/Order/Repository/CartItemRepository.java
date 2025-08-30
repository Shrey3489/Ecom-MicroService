package com.ShreyEcom_micro_App.Order.Repository;


import com.ShreyEcom_micro_App.Order.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    CartItem findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    List<CartItem> findAllByUserId(Long userId);

    void deleteByUserId(Long userId);
}
