package com.aughma.nacarqeqia.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactUs {
    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Message is required")
    @Size(min = 5, max = 1000)
    private String message;
}
