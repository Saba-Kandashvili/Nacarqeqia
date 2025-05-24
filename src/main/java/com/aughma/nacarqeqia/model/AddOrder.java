package com.aughma.nacarqeqia.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddOrder {
    @NotNull(message = "Name of the deceased one is required")
    @Size(min = 2, max = 20)
    private String deceased_name;

    @NotNull(message = "Description is required")
    @Size(min = 5, max = 100, message = "Description too long/short")
    private String description;

    @NotNull(message = "Your Name is required")
    @Size(min = 2, max = 20)
    private String your_name;
}
