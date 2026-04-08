package com.resto.pizzeria.web.controller;

import com.resto.pizzeria.web.model.Client;
import com.resto.pizzeria.web.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ClientController {

  private final ClientService clientService;

  // Injection par constructeur (Toujours SOLID !)
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  /**
   * Affiche la liste de tous les clients
   */
  @GetMapping("/clients")
  public String listClients(Model model) {
    Iterable<Client> listClients = clientService.getClients();

    // Ajoute la liste au "Model" pour qu'elle soit accessible dans le fichier HTML (Thymeleaf)
    model.addAttribute("clients", listClients);
    // Fichier Thymeleaf attendu : src/main/resources/templates/clients/list.html
    return "clients/list";
  }

  /**
   * Affiche le formulaire vide pour créer un nouveau client
   */
  @GetMapping("/client/create")
  public String createClientForm(Model model) {
    // On envoie un objet Client vide au formulaire
    model.addAttribute("client", new Client());
    // Fichier Thymeleaf attendu : src/main/resources/templates/clients/form.html
    return "clients/form";
  }

  /**
   * Affiche le formulaire pré-rempli pour modifier un client existant
   */
  @GetMapping("/client/update/{id}")
  public String updateClientForm(@PathVariable("id") final int id, Model model) {
    Client client = clientService.getClient(id);
    if (client != null) {
      model.addAttribute("client", client);
      return "clients/form"; // On réutilise la même vue HTML que pour la création
    }
    return "redirect:/clients"; // En cas d'erreur (ID introuvable), on redirige vers la liste
  }

  /**
   * Sauvegarde le client (suite à la soumission du formulaire de création ou de modification)
   */
  @PostMapping("/client/save")
  public ModelAndView saveClient(@ModelAttribute Client client) {
    log.info("Requête Web : Sauvegarde du client (Nom: {})", client.getLastName());

    // Le service gère automatiquement s'il s'agit d'un POST (création) ou PUT (mise à jour)
    clientService.saveClient(client);

    // Après la sauvegarde, on redirige l'utilisateur vers la liste des clients
    return new ModelAndView("redirect:/clients");
  }

  /**
   * Supprime un client
   */
  @GetMapping("/client/delete/{id}")
  public ModelAndView deleteClient(@PathVariable("id") final int id) {
    log.info("Requête Web : Suppression du client avec l'ID {}", id);
    clientService.deleteClient(id);
    return new ModelAndView("redirect:/clients");
  }

}
