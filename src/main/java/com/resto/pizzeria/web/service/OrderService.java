package com.resto.pizzeria.web.service;

import com.resto.pizzeria.web.model.OrderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final RestTemplate restTemplate;

    @Value("${com.resto.pizzeria.web.apiUrl}")
    private String apiBaseUrl;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<OrderDto> getAllOrders() {
        ResponseEntity<OrderDto[]> response =
                restTemplate.getForEntity(apiBaseUrl + "/orders", OrderDto[].class);

        return Arrays.asList(response.getBody());
    }

    public OrderDto getOrderById(Long id) {
        return restTemplate.getForObject(
                apiBaseUrl + "/orders/" + id,
                OrderDto.class
        );
    }

    public void createOrder(OrderDto order) {
        restTemplate.postForObject(
                apiBaseUrl + "/orders",
                order,
                OrderDto.class
        );
    }

    public void updateOrder(Long id, OrderDto order) {
        restTemplate.put(
                apiBaseUrl + "/orders/" + id,
                order
        );
    }

    public void deleteOrder(Long id) {
        restTemplate.delete(
                apiBaseUrl + "/orders/" + id
        );
    }
}