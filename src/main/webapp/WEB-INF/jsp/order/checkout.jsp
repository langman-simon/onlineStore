<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="checkout-page">

    <h1>Récapitulatif de la commande</h1>

    <table>
        <thead>
        <tr>
            <th>Produit</th>
            <th>Prix unitaire</th>
            <th>Quantité</th>
            <th>Sous-total</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="item" items="${cart.items}">
            <tr>
                <td>${item.weapon.name}</td>
                <td>${item.weapon.price} €</td>
                <td>${item.quantity}</td>
                <td>${item.subtotal} €</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="order-total">
        <p>
            Total :
            <strong>${cart.totalPrice} €</strong>
        </p>
    </div>

    <div class="checkout-actions">

        <form method="post"
              action="<c:url value='/orders/confirm'/>">

            <button type="submit">
                Confirmer la commande
            </button>
        </form>

        <a class="btn" href="<c:url value='/cart'/>">
            Retour au panier
        </a>

        <a class="btn" href="<c:url value='/catalogue'/>">
            Retour au catalogue
        </a>

    </div>

</section>