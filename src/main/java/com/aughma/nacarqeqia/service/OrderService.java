package com.aughma.nacarqeqia.service;

import com.aughma.nacarqeqia.entity.Order;
import com.aughma.nacarqeqia.model.AddOrder;
import com.aughma.nacarqeqia.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Value("${nacarqeqia.images.base-path}")
    private String imagesBasePath;

    @Transactional
    @SneakyThrows
    public Order save(@Valid AddOrder addOrder, MultipartFile image) {
        Path folder = Path.of(imagesBasePath);
        Files.createDirectories(folder);

        String ext = getExtension(image.getOriginalFilename());
        String fileName = UUID.randomUUID() + (ext.isBlank() ? ".png" : ext);
        Path target = folder.resolve(fileName);
        Files.write(target, image.getBytes());

        Order order = new Order();
        order.setName(addOrder.getGrandma_name());
        order.setDescription(addOrder.getDescription());
        order.setImgUrl("/image/" + fileName);
        return orderRepository.save(order);
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot > 0) ? filename.substring(dot) : "";
    }
}
