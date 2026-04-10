package com.resto.pizzeria.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {

    private Long id;

    @NotBlank(message = "Le libellé est obligatoire")
    @Size(min = 2, max = 50, message = "Le libellé doit contenir 50 caractères maximum")
    private String label;
}