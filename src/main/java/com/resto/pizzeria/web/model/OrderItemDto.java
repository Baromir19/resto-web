package com.resto.pizzeria.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    @NotNull(message = "La commande est obligatoire")
    private OrderDto order;

    @NotNull(message = "Le plat est obligatoire")
    private DishDto dish;

    @NotNull(message = "Le quantité est obligatoire")
    private int quantity;
}
