package com.aughma.nacarqeqia.service;

import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.entity.User;
import com.aughma.nacarqeqia.model.AddOrder;
import com.aughma.nacarqeqia.repository.OrderRepository;
import com.aughma.nacarqeqia.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    @Value("${nacarqeqia.images.base-path}")
    private String imagesBasePath;

    @Transactional
    @SneakyThrows
    public Order save(String username, @Valid AddOrder addOrder, MultipartFile image) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Path folder = Path.of(imagesBasePath);
        Files.createDirectories(folder);

        String ext = getExtension(image.getOriginalFilename());
        String fileName = UUID.randomUUID() + (ext.isBlank() ? ".png" : ext);
        Path target = folder.resolve(fileName);
        Files.write(target, image.getBytes());

        Order order = new Order();
        order.setDeceasedName(addOrder.getDeceasedName());
        order.setDescription(addOrder.getDescription());
        order.setImgUrl("/image/" + fileName);
        order.setUpdatedAt(LocalDateTime.now());
        order.setClientName("GOD"); // TODO: IMPLEMENT CLIENTNAMES
        order.setAuthor(user);
        return orderRepository.save(order);
    }

    @Transactional
    public Order save(String username, @Valid AddOrder addOrder) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setDeceasedName(addOrder.getDeceasedName ());
        order.setDescription(addOrder.getDescription());
        order.setImgUrl("/image/default.png"); // dummy image
        order.setUpdatedAt(LocalDateTime.now());
        order.setAuthor(user); // You forgot to assign the user!
        order.setClientName("GOD");
        order.setAuthor(user);
        return orderRepository.save(order);
    }


    private String getExtension(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot > 0) ? filename.substring(dot) : "";
    }

    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isAuthor(#postId)")
    public void deletePostById(Long postId) {
        orderRepository.deleteById(postId);
    }
}
