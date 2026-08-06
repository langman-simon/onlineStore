<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="cart-page">

    <p class="eyebrow">Votre sélection</p>
    <h1>Votre panier</h1>

    <c:if test="${empty cart.items}">
        <p class="cart-empty">
            Votre panier est vide.
        </p>
    </c:if>

    <c:if test="${not empty cart.items}">

        <table>
            <thead>
            <tr>
                <th>Produit</th>
                <th>Prix unitaire</th>
                <th>Quantité</th>
                <th>Sous-total</th>
                <th>Modifier</th>
                <th>Supprimer</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <a href="<c:url value='/weapons/${item.weapon.id}'/>">
                            ${item.weapon.name}
                        </a>
                    </td>

                    <td>
                        ${item.weapon.price} €
                    </td>

                    <td>
                        ${item.quantity}
                    </td>

                    <td>
                        ${item.subtotal} €
                    </td>

                    <td>
                        <form method="post"
                              action="<c:url value='/cart/update/${item.weapon.id}'/>">

                            <input type="number"
                                   name="quantity"
                                   value="${item.quantity}"
                                   min="0"
                                   max="${item.weapon.stock}"
                                   required>

                            <sec:csrfInput/>

                            <button type="submit">
                                Mettre à jour
                            </button>
                        </form>
                    </td>

                    <td>
                        <form method="post"
                              action="<c:url value='/cart/remove/${item.weapon.id}'/>">

                            <sec:csrfInput/>

                            <button type="submit" class="btn-delete">
                                Supprimer
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="cart-summary">
            <p>
                Nombre total d'articles :
                <strong>${cart.totalQuantity}</strong>
            </p>

            <p>
                Sous-total :
                <strong>${originalPrice} €</strong>
            </p>

            <p>
                Frais de livraison :
                <c:choose>
                    <c:when test="${freeDelivery}">
                        <span class="strikethrough">${standardDeliveryFee} €</span>
                        <span class="free-delivery">Offerts !</span>
                    </c:when>
                    <c:otherwise>
                        <span>${deliveryFee} €</span>
                    </c:otherwise>
                </c:choose>
            </p>

            <c:if test="${discountAmount > 0}">
                <p class="discount">
                    Réduction fidélité :
                    <span>- ${discountAmount} €</span>
                </p>
            </c:if>

            <p>
                Total :
                <strong>${finalPrice} €</strong>
            </p>
        </div>

        <div class="promo-summary">
    <h2>Promotions appliquées</h2>

    <c:if test="${discountAmount > 0}">
        <p>
            <strong>Réduction fidélité :</strong>
            - ${discountAmount} €
        </p>
    </c:if>

    <c:choose>
        <c:when test="${freeDelivery}">
            <p>
                <strong>Livraison :</strong>
                <span class="strikethrough">${standardDeliveryFee} €</span>
                <span class="free-delivery">Offerte</span>
            </p>
        </c:when>
        <c:otherwise>
            <p>
                <strong>Frais de livraison :</strong>
                ${deliveryFee} €
            </p>
        </c:otherwise>
    </c:choose>

    <hr>

    <p>
        <strong>Total après promotions :</strong>
        ${finalPrice} €
    </p>
</div>


        <div class="cart-actions">

            <form method="get"
                  action="<c:url value='/order/checkout'/>"
                  class="cart-checkout-form">

                <button type="submit" class="btn">
                    Continuer vers la commande
                </button>
            </form>

        <a class="btn" href="<c:url value='/catalogue'/>">
            Retour au catalogue
        </a>

        <form method="post"
              action="<c:url value='/cart/clear'/>"
              class="cart-clear-form">

            <sec:csrfInput/>

            <button type="submit" class="btn-delete">
                Vider le panier
            </button>
        </form>

        </div>

    </c:if>

</section>