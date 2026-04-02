package com.resto.pizzeria.web.service;

import com.resto.pizzeria.web.model.Client;
import com.resto.pizzeria.web.repository.ClientRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClientServices {

  private final ClientRepository clientRepository;

  // Injection de dépendance par constructeur (Principe d'Inversion des Dépendances - SOLID)
  public ClientServices(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  /**
   * Récupère la liste de tous les clients
   */
  public Iterable<Client> getClients() {
    return clientRepository.getClients();
  }

  /**
   * Récupère un client spécifique par son ID
   */
  public Client getClient(Integer id) {
    return clientRepository.getClient(id);
  }

  /**
   * Sauvegarde un client (Gère la création ET la modification)
   */
  public Client saveClient(Client client) {
    Client savedClient;

    // Règle de gestion métier : Le nom de famille doit être mis en majuscule.
    if (client.getLastName() != null) {
      client.setLastName(client.getLastName().trim().toUpperCase());
    }

    // Nettoyage basique pour le prénom
    if (client.getFirstName() != null) {
      client.setFirstName(client.getFirstName().trim());
    }

    // Si l'ID est nul, alors c'est un nouveau client
    if (client.getId() == null) {
      log.info("Traitement métier : Création d'un nouveau client");
      savedClient = clientRepository.createClient(client);
    } else  {
      // Sinon, c'est une mise à jour
      log.info("Traitement métier : Mise à jour du client avec l'ID {}", client.getId());
      savedClient = clientRepository.updateClient(client);
    }
    return savedClient;
  }

  /**
   * Supprime un client
   */
  public void deleteClient(Integer id) {
    log.info("Traitement métier : Suppression du client avec l'ID {}", id);
    clientRepository.deleteClient(id);
  }
}
