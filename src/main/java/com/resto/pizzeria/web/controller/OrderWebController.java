package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.controller.utils.OrderUtils;
import com.resto.pizzeria.web.model.OrderDto;
import com.resto.pizzeria.web.service.ClientService;
import com.resto.pizzeria.web.service.DishService;
import com.resto.pizzeria.web.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/orders")
public class OrderWebController {
    private final OrderService orderService;
    private final ClientService clientService;
    private final DishService dishService;

    public OrderWebController(
            final OrderService orderService,
            final ClientService clientService,
            final DishService dishService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.dishService = dishService;
    }

    @GetMapping
    public String listOrders(final Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "pages/order/list";
    }

    @GetMapping("/new")
    public String showCreateForm(final Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        model.addAttribute("clients", clientService.getAllClients());
        // model.addAttribute("order", new OrderDto());

        return "pages/order/form";
    }

    @PostMapping
    public String createOrder(
            @Valid @ModelAttribute("order") final OrderDto order,
            final BindingResult bindingResult) {
        order.setDailyId(OrderUtils.generateDailyId(LocalDate.now()));

        if (bindingResult.hasErrors()) {
            return "pages/order/form";
        }

        orderService.createOrder(order);

        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(
            @PathVariable final Long id,
            final Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("dishes", dishService.getAllDishes());
        model.addAttribute("clients", clientService.getAllClients());

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

        orderService.updateOrder(id, order);

        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteOrder(@PathVariable final Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}