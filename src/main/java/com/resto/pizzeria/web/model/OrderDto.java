package com.resto.pizzeria.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    @NotNull(message = "Le statut est obligatoire")
    private StatusDto status;

    @NotNull(message = "Le client est obligatoire")
    private ClientDto client;

    // @NotNull(message = "Le id est obligatoire")
    private Integer dailyId;

    private LocalDateTime creationDate;

    private List<OrderItemDto> items;
}
