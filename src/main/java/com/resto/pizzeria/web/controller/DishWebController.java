package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.DishDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
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
    public String listDishes(Model model) {
        String url = apiBaseUrl + "/dishes/menu";
        ResponseEntity<DishDto[]> response = restTemplate.getForEntity(url, DishDto[].class);
        model.addAttribute("dishes", response.getBody());
        return "dish/list";
    }

    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("dish", new DishDto());
        return "dish/form";
    }

    @PostMapping("/nouveau")
    public String createDish(@Valid @ModelAttribute("dish") DishDto dish, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "dish/form";
        }
        restTemplate.postForObject(apiBaseUrl + "/dishes", dish, DishDto.class);
        return "redirect:/dishes";
    }

    @GetMapping("/modifier/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        DishDto dish = restTemplate.getForObject(apiBaseUrl + "/dishes/" + id, DishDto.class);
        model.addAttribute("dish", dish);
        return "dish/form";
    }

    @PostMapping("/modifier/{id}")
    public String updateDish(@PathVariable Long id, @Valid @ModelAttribute("dish") DishDto dish, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "dish/form";
        }
        // Pour faire simple dans le Front, on peut utiliser postForObject ou put selon la configuration API
        restTemplate.postForObject(apiBaseUrl + "/dishes", dish, DishDto.class);
        return "redirect:/dishes";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteDish(@PathVariable Long id) {
        restTemplate.delete(apiBaseUrl + "/dishes/" + id);
        return "redirect:/dishes";
    }
}