package com.aughma.nacarqeqia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Lob
    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "SUBMITTED_AT", nullable = false)
    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}
