package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.DishDto;
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

    @Value("${com.resto.pizzeria.web.apiUrl}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public String listDishes(final Model model) {
        String url = apiBaseUrl + "/dishes";
        ResponseEntity<DishDto[]> response = restTemplate.getForEntity(url, DishDto[].class);
        model.addAttribute("dishes", response.getBody());
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
            return "pages/dish/form"; // todo: send json form with error
        }
        restTemplate.postForObject(apiBaseUrl + "/dishes", dish, DishDto.class);
        return "redirect:/dishes";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable final Long id, final Model model) {
        DishDto dish = restTemplate.getForObject(apiBaseUrl + "/dishes/" + id, DishDto.class);
        model.addAttribute("dish", dish);
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
        // Pour faire simple dans le Front, on peut utiliser postForObject ou put selon la configuration API
        final String url = apiBaseUrl + "/dishes/" + id;
        restTemplate.put(url, dish);

        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        restTemplate.delete(apiBaseUrl + "/dishes/" + id);
        return "redirect:/dishes";
    }
}