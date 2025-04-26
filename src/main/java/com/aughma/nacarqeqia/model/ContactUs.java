package com.aughma.nacarqeqia.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactUs {
    @NotNull(message = "Firstname is required")
    @Size(min = 2, max = 20, message = "invalid name")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "Message is required")
    @Size(min = 5, max = 100, message = "message too long/short")
    private String message;

}
