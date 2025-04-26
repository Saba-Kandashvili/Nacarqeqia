package com.aughma.nacarqeqia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "IMG_URL", nullable = false)
    private String imgUrl;
}
