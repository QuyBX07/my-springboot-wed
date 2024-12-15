package com.example.foodwed.repository;

import com.example.foodwed.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,String> {
    Page<Orders> findByIsactive(boolean isactive, Pageable pageable);
    Page<Orders> findByUid(String uid, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.isactive = true AND o.uid =:uid")
    Page<Orders> findActiveOrders(String uid, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.isactive = false AND o.uid =:uid")
    Page<Orders> findInactiveOrders(String uid, Pageable pageable);


}
