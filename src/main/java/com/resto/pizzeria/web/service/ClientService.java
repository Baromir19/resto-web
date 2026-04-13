package com.resto.pizzeria.web.service;

import com.resto.pizzeria.web.model.ClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {
    private final RestTemplate restTemplate;

    @Value("${com.resto.pizzeria.web.apiUrl}")
    private String apiBaseUrl;

    public ClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtient tous les clients.
     */
    public List<ClientDto> getAllClients() {
        String url = apiBaseUrl + "/clients";

        final ResponseEntity<ClientDto[]> response =
                restTemplate.getForEntity(url, ClientDto[].class);

        return Arrays.asList(response.getBody());
    }

    /**
     * Obtient le client par id.
     * @param id Identifiant du client.
     */
    public ClientDto getClientById(final Long id) {
        return restTemplate.getForObject(
                apiBaseUrl + "/clients/" + id,
                ClientDto.class
        );
    }

    /**
     * Sauvegarde le client.
     * @param client Client pour sauvegarder.
     */
    public void createClient(final ClientDto client) {
        restTemplate.postForObject(
                apiBaseUrl + "/clients",
                client,
                ClientDto.class
        );
    }

    /**
     * Met à jour le client.
     * @param id Identifiant du client.
     * @param client Client pour mettre à jour.
     */
    public void updateClient(
            final Long id,
            final ClientDto client) {
        restTemplate.put(
                apiBaseUrl + "/clients/" + id,
                client
        );
    }

    /**
     * Supprimer le client.
     * @param id Identifiant du client.
     */
    public void deleteClient(final Long id) {
        restTemplate.delete(
                apiBaseUrl + "/clients/" + id
        );
    }
}