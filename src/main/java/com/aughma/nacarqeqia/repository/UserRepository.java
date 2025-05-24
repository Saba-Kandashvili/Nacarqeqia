package com.aughma.nacarqeqia.repository;

import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}