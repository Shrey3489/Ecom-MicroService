package com.ShreyEcom_micro_App.Order.Repository;

import com.ShreyEcom_micro_App.Order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
