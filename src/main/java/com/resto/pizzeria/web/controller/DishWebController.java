package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.DishDto;
import com.resto.pizzeria.web.service.DishService;
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
@RequestMapping("/dishes")
public class DishWebController {

    private final DishService dishService;

    public DishWebController(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public String listDishes(final Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "pages/dish/list";
    }

    @GetMapping("/new")
    public String showCreateForm(final Model model) {
        model.addAttribute("dish", new DishDto());
        return "pages/dish/form";
    }

    @PostMapping
    public String createDish(
            @Valid @ModelAttribute("dish") final DishDto dish,
            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/dish/form";
        }

        dishService.createDish(dish);
        return "redirect:/dishes";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(
            @PathVariable final Long id,
            final Model model) {
        model.addAttribute("dish", dishService.getDishById(id));
        return "pages/dish/form";
    }

    @PostMapping("/{id}")
    public String updateDish(
            @PathVariable final Long id,
            @Valid @ModelAttribute("dish") final DishDto dish,
            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/dish/form";
        }

        dishService.updateDish(id, dish);

        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/dishes";
    }
}