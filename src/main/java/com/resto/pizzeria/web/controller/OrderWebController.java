package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.ClientDto;
import com.resto.pizzeria.web.model.DishDto;
import com.resto.pizzeria.web.model.OrderDto;
import com.resto.pizzeria.web.model.StatusDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/orders")
public class OrderWebController {
    private Integer dailyIdCounter = 1;

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
        // ALL DISHES
        String allDishesUrl = apiBaseUrl + "/dishes";
        ResponseEntity<DishDto[]> responseDishes
                = restTemplate.getForEntity(allDishesUrl, DishDto[].class);
        model.addAttribute("dishes", responseDishes.getBody());

        // ALL CLIENTS
        String allClientsUrl = apiBaseUrl + "/clients";
        ResponseEntity<ClientDto[]> responseClients
                = restTemplate.getForEntity(allClientsUrl, ClientDto[].class);
        model.addAttribute("clients", responseClients.getBody());

        // model.addAttribute("order", new OrderDto());
        return "pages/order/form";
    }

    @PostMapping
    public String createOrder(
            @Valid @ModelAttribute("order") final OrderDto order,
            final BindingResult bindingResult) {
        order.setDailyId(dailyIdCounter++);

        if (bindingResult.hasErrors()) {
            return "form";
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
        // ORDER
        final OrderDto order = restTemplate.getForObject(
                apiBaseUrl + "/orders/" + id, OrderDto.class);
        model.addAttribute("order", order);

        // ALL DISHES
        final String allDishesUrl = apiBaseUrl + "/dishes";
        final ResponseEntity<DishDto[]> responseDishes
                = restTemplate.getForEntity(allDishesUrl, DishDto[].class);
        model.addAttribute("dishes", responseDishes.getBody());

        // ALL CLIENTS
        final String allClientsUrl = apiBaseUrl + "/clients";
        final ResponseEntity<ClientDto[]> responseClients
                = restTemplate.getForEntity(allClientsUrl, ClientDto[].class);
        model.addAttribute("clients", responseClients.getBody());

        /* todo: create Statuses Controller API?????

        // ALL STATUSES
        final String allStatusesUrl = apiBaseUrl + "/statuses";
        final ResponseEntity<StatusDto[]> responseStatuses
                = restTemplate.getForEntity(allStatusesUrl, StatusDto[].class);
        model.addAttribute("statuses", responseStatuses.getBody());*/

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteOrder(@PathVariable Long id) {
        restTemplate.delete(apiBaseUrl + "/orders/" + id);
        return "redirect:/orders";
    }
}