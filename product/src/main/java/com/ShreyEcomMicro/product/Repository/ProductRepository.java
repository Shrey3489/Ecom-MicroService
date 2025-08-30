package com.ShreyEcomMicro.product.Repository;


import com.ShreyEcomMicro.product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    @Query(" select p from Product p where p.active = true and p.quantity > 0 and LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')) ")
    List<Product> findByKeyWord(String keyword);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
