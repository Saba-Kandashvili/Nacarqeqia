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
        // 1. arrange
        String username = "giorgi";
        User author = new User();
        author.setUsername(username);

        AddOrder addOrderDto = new AddOrder();
        addOrderDto.setDeceasedName("John Smith");
        addOrderDto.setDescription("A very solemn service");
        addOrderDto.setYourName("giorgi");

        // mock behavior
        when(userRepository.findById(username)).thenReturn(Optional.of(author));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. act
        Order savedOrder = orderService.save(username, addOrderDto);

        // 3. assert & verify
        assertNotNull(savedOrder);

        // use ArgumentCaptor to inspect the Order passed to the repository
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();

        // check if the captured order has the correct data based on the service logic
        assertEquals("John Smith", capturedOrder.getDeceasedName());
        assertEquals("A very solemn service", capturedOrder.getDescription());
        assertEquals("/image/default.png", capturedOrder.getImgUrl());
        assertEquals(author, capturedOrder.getAuthor());
        assertEquals("giorgi", capturedOrder.getClientName());
        assertNotNull(capturedOrder.getUpdatedAt());
    }
}