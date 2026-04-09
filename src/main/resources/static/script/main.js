// =====================
// FILTER SYSTEM
// =====================
function filterItems(category) {
    const cards = document.querySelectorAll('.card');

    cards.forEach(card => {
        if (category === 'all') {
            card.style.display = 'block';
        } else {
            if (card.classList.contains(category)) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        }
    });
}


// =====================
// CART SYSTEM
// =====================
let cart = [];

function addToCart(name, price) {
    const existing = cart.find(item => item.name === name);

    if (existing) {
        existing.quantity++;
    } else {
        cart.push({ name, price, quantity: 1 });
    }

    renderCart();
}

function renderCart() {
    const container = document.getElementById("cart-items");
    if (!container) return;

    container.innerHTML = "";

    let subtotal = 0;

    cart.forEach((item, index) => {
        subtotal += item.price * item.quantity;

        container.innerHTML += `
            <div class="item">
                <div>
                    <div>${item.name}</div>
                    <div class="price">${item.price.toFixed(2)} €</div>
                </div>
                <div class="quantity">
                    <button onclick="decrease(${index})">-</button>
                    <span>${item.quantity}</span>
                    <button onclick="increase(${index})">+</button>
                </div>
            </div>
        `;
    });

    const tax = subtotal * 0.10;
    const total = subtotal + tax;

    document.getElementById("subtotal").innerText = subtotal.toFixed(2) + " €";
    document.getElementById("tax").innerText = tax.toFixed(2) + " €";
    document.getElementById("total").innerText = total.toFixed(2) + " €";
}

function increase(index) {
    cart[index].quantity++;
    renderCart();
}

function decrease(index) {
    cart[index].quantity--;

    if (cart[index].quantity <= 0) {
        cart.splice(index, 1);
    }

    renderCart();
}

function clearCart() {
    cart = [];
    renderCart();
}


// =====================
// DISH NEW
// =====================

// Save dish from create form
function saveDish(event) {
    event.preventDefault();

    const newDish = {
        name: document.getElementById("name").value,
        description: document.getElementById("description").value,
        price: parseFloat(document.getElementById("price").value),
        type: document.getElementById("type").value
    };

    let dishes = JSON.parse(localStorage.getItem("dishes")) || [];
    let editDish = JSON.parse(localStorage.getItem("editDish"));

    if (editDish) {
        // UPDATE
        dishes = dishes.map(d => {
            if (d.name === editDish.name) {
                return newDish;
            }
            return d;
        });
        localStorage.removeItem("editDish");
    } else {
        // CREATE
        dishes.push(newDish);
    }

    localStorage.setItem("dishes", JSON.stringify(dishes));

    window.location.href = "dish.html";
}


// =====================
// INIT
// =====================
window.onload = function () {
    filterItems('pizza');
};

// =====================
// Delete
// =====================
function deleteDish(name) {
    const confirmDelete = confirm("Êtes-vous sûr de vouloir supprimer cet article ?");

    if (!confirmDelete) return;

    let dishes = JSON.parse(localStorage.getItem("dishes")) || [];

    dishes = dishes.filter(d => d.name !== name);

    localStorage.setItem("dishes", JSON.stringify(dishes));

    location.reload();
}

function loadDishForEdit() {
    const dish = JSON.parse(localStorage.getItem("editDish"));

    if (!dish) return;

    // Fill form
    document.getElementById("name").value = dish.name;
    document.getElementById("description").value = dish.description;
    document.getElementById("price").value = dish.price;
    document.getElementById("type").value = dish.type;

    // CHANGE BUTTON TEXT
    document.querySelector("button[type='submit']").innerText = "Modifier";
}

function goBack() {
    window.location.href = "dish.html";
}

function editDish(name) {
    let dishes = JSON.parse(localStorage.getItem("dishes")) || [];

    const dish = dishes.find(d => d.name === name);

    if (!dish) {
        alert("Ce plat n'est pas modifiable (pas encore enregistré).");
        return;
    }

    localStorage.setItem("editDish", JSON.stringify(dish));

    window.location.href = "create-dish.html";
}