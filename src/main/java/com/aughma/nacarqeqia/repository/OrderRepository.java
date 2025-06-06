package com.aughma.nacarqeqia.repository;

import com.aughma.nacarqeqia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByDeceasedName(String deceased_name);
}