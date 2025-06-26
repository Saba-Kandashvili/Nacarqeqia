package com.aughma.nacarqeqia.service;

import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.entity.User;
import com.aughma.nacarqeqia.model.AddOrder;
import com.aughma.nacarqeqia.repository.OrderRepository;
import com.aughma.nacarqeqia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testSave_WithoutImage_Success() {
        // 1. Arrange
        String username = "vakho";
        AddOrder addOrderDto = new AddOrder("John Smith", "A very solemn service", "vakho");
        User author = new User();
        author.setUsername(username);

        // Define behavior for mocked repository: when findById is called, return our test user
        when(userRepository.findById(username)).thenReturn(Optional.of(author));
        // We also need to mock the save to return the saved order
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act
        Order savedOrder = orderService.save(username, addOrderDto);

        // 3. Assert & Verify
        assertNotNull(savedOrder);

        // Use ArgumentCaptor to inspect the order passed to the repository's save method
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();

        // Check if the captured order has the correct data
        assertEquals("John Smith", capturedOrder.getDeceasedName());
        assertEquals("A very solemn service", capturedOrder.getDescription());
        assertEquals("/image/default.png", capturedOrder.getImgUrl());
        assertEquals(author, capturedOrder.getAuthor()); // The user object should be the same
        assertEquals("GOD", capturedOrder.getClientName());
        assertNotNull(capturedOrder.getUpdatedAt());
    }
}