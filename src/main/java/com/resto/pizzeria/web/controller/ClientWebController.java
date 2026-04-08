package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.ClientDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller // Attention : @Controller et non @RestController pour renvoyer du HTML
@RequestMapping("/clients")
public class ClientWebController {

  // On récupère l'URL de l'API définie dans application.properties (http://localhost:8080/api)
  @Value("${com.resto.pizzeria.web.apiUrl}")
  private String apiBaseUrl;

  // L'outil Spring pour faire des requêtes HTTP vers l'API
  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * READ : Affiche la page avec la liste de tous les clients
   */
  @GetMapping
  public String listClients(Model model) {
    String url = apiBaseUrl + "/clients";

    // Appel GET à l'API. On récupère un tableau de ClientDto.
    ResponseEntity<ClientDto[]> response = restTemplate.getForEntity(url, ClientDto[].class);

    // On injecte les données récupérées dans le modèle Thymeleaf
    model.addAttribute("clients", response.getBody());

    // On renvoie vers le fichier src/main/resources/templates/clients/list.html
    return "client/list";
  }

  /**
   * CREATE (Étape 1) : Affiche le formulaire vide pour ajouter un client
   */
  @GetMapping("/nouveau")
  public String showCreateForm(Model model) {
    // On envoie un objet vide à Thymeleaf pour qu'il puisse lier les champs du formulaire
    model.addAttribute("client", new ClientDto());
    return "client/form"; // Renvoie vers templates/clients/form.html
  }

  /**
   * CREATE (Étape 2) : Réceptionne les données du formulaire et les envoie à l'API
   */
  @PostMapping("/nouveau")
  public String createClient(
      @Valid @ModelAttribute("client") ClientDto client,
      BindingResult bindingResult
  ) {
    // 1. Vérification des erreurs de validation (les @NotBlank du DTO)
    if (bindingResult.hasErrors()) {
      // S'il y a une erreur, on recharge la page du formulaire avec les messages d'erreur
      return "client/form";
    }

    // 2. S'il n'y a pas d'erreur, on fait un POST vers l'API
    String url = apiBaseUrl + "/clients";
    restTemplate.postForObject(url, client, ClientDto.class);

    // 3. On redirige l'utilisateur vers la page de la liste des clients
    return "redirect:/clients";
  }
}