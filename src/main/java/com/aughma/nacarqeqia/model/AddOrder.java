package com.aughma.nacarqeqia.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddOrder {
    @NotNull(message = "Grandma Name is required")
    @Size(min = 2, max = 20)
    private String grandma_name;

    @NotNull(message = "Description is required")
    @Size(min = 5, max = 100, message = "Description too long/short")
    private String description;

    @NotNull(message = "Your Name is required")
    @Size(min = 2, max = 20)
    private String your_name;
}
