package com.aughma.nacarqeqia.repository;

import com.aughma.nacarqeqia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByDeceasedName(String deceased_name);

    Page<Order> findByAuthorUsername(String username, Pageable pageable);


    @Query(value = "SELECT o FROM Order o JOIN FETCH o.author",
            countQuery = "SELECT count(o) FROM Order o")
    Page<Order> findAllWithAuthor(Pageable pageable);
}