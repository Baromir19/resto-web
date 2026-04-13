package com.resto.pizzeria.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    private Long id;

    @NotBlank(message = "Le nom du plat est obligatoire")
    private String name;

    private String description;

    @NotBlank(message = "La catégorie est obligatoire")
    private String category;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être strictement positif")
    private BigDecimal price;
}