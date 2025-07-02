package com.aughma.nacarqeqia.repository;

import com.aughma.nacarqeqia.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByDeceasedName(String deceased_name);

    // --- ADD THIS NEW METHOD ---
    /**
     * Finds a paginated list of orders created by a specific user.
     * @param username The username of the author.
     * @param pageable The pagination information.
     * @return A page of orders.
     */
    Page<Order> findByAuthorUsername(String username, Pageable pageable);
}