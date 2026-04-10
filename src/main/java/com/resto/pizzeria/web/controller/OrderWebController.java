package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.DishDto;
import com.resto.pizzeria.web.model.OrderDto;
import com.resto.pizzeria.web.model.OrderItemDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/orders")
public class OrderWebController {

    @Value("${com.resto.pizzeria.web.apiUrl}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public String listOrders(Model model) {
        String url = apiBaseUrl + "/orders";
        ResponseEntity<OrderDto[]> response
                = restTemplate.getForEntity(url, OrderDto[].class);
        model.addAttribute("orders", response.getBody());
        return "pages/order/list";
    }

    @GetMapping("/new")
    public String showCreateForm(final Model model) {
        String url = apiBaseUrl + "/dishes";
        ResponseEntity<DishDto[]> response
                = restTemplate.getForEntity(url, DishDto[].class);
        model.addAttribute("dishes", response.getBody());

        model.addAttribute("order", new OrderDto());
        return "pages/order/create";
    }

    @PostMapping
    public String createOrder(
            @Valid @ModelAttribute("order") final OrderDto order,
            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/order/form";
        }
        restTemplate.postForObject(
                apiBaseUrl + "/orders",
                order,
                OrderDto.class);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(
            @PathVariable final Long id,
            final Model model) {
        OrderDto order = restTemplate.getForObject(
                apiBaseUrl + "/orders/" + id, OrderDto.class);
        model.addAttribute("order", order);
        return "pages/order/form";
    }

    @PostMapping("/{id}")
    public String updateOrder(
            @PathVariable final Long id,
            @Valid @ModelAttribute("order") final OrderDto order,
            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/order/form";
        }

        restTemplate.postForObject(
                apiBaseUrl + "/orders", order, DishDto.class);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        restTemplate.delete(apiBaseUrl + "/orders/" + id);
        return "redirect:/orders";
    }
}