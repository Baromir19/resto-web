/**
 * Ouvre la modale et configure l'URL de suppression
 */
function openDeleteModal(id, name, endpoint) {
    document.getElementById('modalName').textContent = name;

    // document.getElementById('confirmDeleteBtn').href = endpoint + '/' + id;

    const btn = document.getElementById('confirmDeleteBtn');

    btn.onclick = async function () {
        try {
            const response = await fetch(endpoint + id, {
                method: 'DELETE'
            });

            if (response.ok) {
                closeDeleteModal();

                window.location.reload();
            } else {
                console.error('Delete failed');
            }
        } catch (err) {
            console.error('Error:', err);
        }
    };

    // 4. On affiche la modale (on passe de display:none à display:flex)
    document.getElementById('deleteModal').style.display = 'flex';
}

function openDeleteModalClient(buttonElement) {
    const id = buttonElement.getAttribute('data-id');
    const name = buttonElement.getAttribute('data-name');
    openDeleteModal(id, name, 'clients/');
}

function openDeleteModalOrder(buttonElement) {
    const id = buttonElement.getAttribute('data-id');
    openDeleteModal(id, "", 'orders/');
}

/**
 * Ferme la modale
 */
function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}