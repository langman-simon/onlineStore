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
                        <form method="post" class="cart-quantity-form"
                              action="<c:url value='/cart/update/${item.weapon.id}'/>">

                            <input type="number"
                                   name="quantity"
                                   class="quantity-input"
                                   value="${item.quantity}"
                                   min="0"
                                   max="${item.weapon.stock}"
                                   required>

                            <sec:csrfInput/>

                            <button type="submit" class="btn btn--primary btn--full">
                                Mettre à jour
                            </button>
                        </form>
                    </td>

                    <td>
                        <form method="post"
                              action="<c:url value='/cart/remove/${item.weapon.id}'/>">

                            <sec:csrfInput/>

                            <button type="submit" class="btn btn--primary btn--full btn--delete">
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

    <c:choose>
        <c:when test="${authenticated}">

            <c:if test="${not empty remainingForFreeDelivery && remainingForFreeDelivery > 0}">
                <p class="promo-incentive">
                    Plus que <strong>${remainingForFreeDelivery} €</strong>
                    d'achat pour bénéficier de la livraison offerte !
                </p>
            </c:if>

            <c:if test="${not empty remainingForNextTier}">
                <p class="promo-incentive">
                    Plus que <strong>${remainingForNextTier} €</strong>
                    d'achat pour passer à <strong>-${nextTierRate}%</strong> de réduction !
                </p>
            </c:if>

        </c:when>
        <c:otherwise>
            <c:url var="loginFromCartUrl" value="/login">
                <c:param name="redirect" value="/cart"/>
            </c:url>

            <span>
                Connectez vous pour bénéficier des promotions !
            </span>

            <a href="${loginFromCartUrl}"
               class="link--accent">
                Se connecter
            </a>
        </c:otherwise>
    </c:choose>

    <hr>

    <p>
        <strong>Total après promotions :</strong>
        ${finalPrice} €
    </p>
</div>

<div class="cart-actions">

    <form method="post"
          action="<c:url value='/order/confirm'/>">

        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}">

        <button type="submit"
                class="btn btn--primary">
            Continuer vers la commande
        </button>

    </form>

    <a href="<c:url value='/catalogue'/>"
       class="btn btn--primary">
        Retour au catalogue
    </a>

    <form method="post"
          action="<c:url value='/cart/clear'/>">

        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}">

        <button type="submit"
                class="btn btn--delete">
            Vider le panier
        </button>

    </form>

</div>

    </c:if>

</section>