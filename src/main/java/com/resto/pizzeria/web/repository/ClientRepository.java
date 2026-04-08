package com.resto.pizzeria.web.repository;

import com.resto.pizzeria.web.config.CustomProperties;
import com.resto.pizzeria.web.model.Client;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClientRepository {

  private final CustomProperties customProperties;
  private final RestTemplate restTemplate;

  // Injection de dépendance par le constructeur (Bonne pratique SOLID)
  public ClientRepository(CustomProperties customProperties) {
    this.customProperties = customProperties;
    this.restTemplate = new RestTemplate();
  }

  /**
   * Récupère tous les clients depuis l'API
   */
  public Iterable<Client> getClients() {
    String baseApiUrl = customProperties.getApiUrl();

    // Attention à bien accorder avec le nom de l'endpoint de l'API
    String getClientsUrl = baseApiUrl + "/clients";

    ResponseEntity<Iterable<Client>> response = restTemplate.exchange(
        getClientsUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Iterable<Client>>() {}
    );

    log.debug("Get Clients call - Status code: {}", response.getStatusCode());
    return response.getBody();
  }

  public Client getClient(Integer id) {
    String getClientUrl = customProperties.getApiUrl() + "/client/" + id;

    ResponseEntity<Client> response = restTemplate.exchange(
        getClientUrl,
        HttpMethod.GET,
        null,
        Client.class
    );

    log.debug("Get Client call - Status code: {}", response.getStatusCode());
    return response.getBody();
  }

  /**
   * Ajoute un nouveau client
   */
  public Client createClient(Client client) {
    String createClientUrl = customProperties.getApiUrl() + "/client";
    HttpEntity<Client> request = new HttpEntity<>(client);

    ResponseEntity<Client> response = restTemplate.exchange(
        createClientUrl,
        HttpMethod.POST,
        request,
        Client.class
    );

    log.debug("Create Client call - Status code: {}", response.getStatusCode());
    return response.getBody();
  }

  /**
   * Met à jour un client existant [cite: 1266]
   */
  public Client updateClient(Client client) {
    String updateClientUrl = customProperties.getApiUrl() + "/client/" + client.getId();
    HttpEntity<Client> request = new HttpEntity<>(client);

    ResponseEntity<Client> response = restTemplate.exchange(
        updateClientUrl,
        HttpMethod.PUT,
        request,
        Client.class
    );

    log.debug("Update Client call - Status code: {}", response.getStatusCode());
    return response.getBody();
  }

  /**
   * Supprime un client
   */
  public void deleteClient(Integer id) {
    String deleteClientUrl = customProperties.getApiUrl() + "/client/" + id;

    ResponseEntity<Void> response = restTemplate.exchange(
        deleteClientUrl,
        HttpMethod.DELETE,
        null,
        Void.class
    );

    log.debug("Delete Client call - Status code: {}", response.getStatusCode());
  }
}
