/**
 * Ouvre la modale et configure l'URL de suppression
 */
function openDeleteModal(buttonElement) {
    // 1. On récupère les données cachées dans le bouton cliqué (ID et Nom)
    const clientId = buttonElement.getAttribute('data-id');
    const clientName = buttonElement.getAttribute('data-name');

    // 2. On met à jour le texte de la modale
    document.getElementById('modalClientName').textContent = clientName;

    // 3. On met à jour le lien du bouton "Oui, supprimer" pour qu'il pointe vers le bon ID
    document.getElementById('confirmDeleteBtn').href = '/clients/supprimer/' + clientId;

    // 4. On affiche la modale (on passe de display:none à display:flex)
    document.getElementById('deleteModal').style.display = 'flex';
}

/**
 * Ferme la modale
 */
function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}