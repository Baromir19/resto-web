package com.resto.pizzeria.web.service;

import com.resto.pizzeria.web.model.DishDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DishService {
    private final RestTemplate restTemplate;

    @Value("${com.resto.pizzeria.web.apiUrl}")
    private String apiBaseUrl;

    public DishService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DishDto> getAllDishes() {
        final String url = apiBaseUrl + "/dishes";
        final ResponseEntity<DishDto[]> response =
                restTemplate.getForEntity(url, DishDto[].class);

        return Arrays.asList(response.getBody());
    }

    public DishDto getDishById(final Long id) {
        return restTemplate.getForObject(
                apiBaseUrl + "/dishes/" + id,
                DishDto.class
        );
    }

    public void createDish(final DishDto dish) {
        restTemplate.postForObject(
                apiBaseUrl + "/dishes",
                dish,
                DishDto.class
        );
    }

    public void updateDish(final Long id, final DishDto dish) {
        restTemplate.put(
                apiBaseUrl + "/dishes/" + id,
                dish
        );
    }

    public void deleteDish(final Long id) {
        restTemplate.delete(
                apiBaseUrl + "/dishes/" + id
        );
    }
}