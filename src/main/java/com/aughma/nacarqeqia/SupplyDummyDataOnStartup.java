package com.aughma.nacarqeqia;


import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.model.AddOrder;
import com.aughma.nacarqeqia.repository.OrderRepository;
import com.aughma.nacarqeqia.repository.UserRepository;
import com.aughma.nacarqeqia.service.OrderService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SupplyDummyDataOnStartup {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        var users = List.of(
                User.withUsername("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER", "ADMIN")
                        .build(),
                User.withUsername("vakho")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("gio")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("dato")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("mari")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build(),
                User.withUsername("bob")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER")
                        .build()
        );

        users.forEach(userDetailsManager::createUser);

        Random rnd = new Random();
        Faker faker = new Faker();
        int numberOfOrders = rnd.nextInt(100, 500);

        String[] topics = {"church", "cemetery", "coffin", "funeral", "grave", "memorial", "mourning", "tombstone"};

        for (int i = 0; i < numberOfOrders; i++) {
            String username = users.get(rnd.nextInt(users.size())).getUsername();
            int[] imageIds = {22, 28, 33, 42, 58, 60, 64, 65, 76, 77, 85, 90, 99}; // handpicked IDs
            String imageUrl = "https://picsum.photos/id/" + imageIds[rnd.nextInt(imageIds.length)] + "/600/400";

            AddOrder addOrder = new AddOrder(
                    users.get(rnd.nextInt(users.size())).getUsername(),
                    StringUtils.capitalize(String.join(" ", faker.lorem().words(rnd.nextInt(3, 6)))),
                    users.get(rnd.nextInt(users.size())).getUsername()
            );

            Order order = new Order();
            order.setDeceasedName(addOrder.getDeceased_name());
            order.setDescription(addOrder.getDescription());
            order.setImgUrl(imageUrl);
            order.setUpdatedAt(LocalDateTime.now());
            order.setClientName("GOD");
            order.setAuthor(userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found")));

            orderRepository.save(order);
        }
    }
}
