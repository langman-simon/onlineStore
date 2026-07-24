<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="cart-page">

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

                            <button type="submit">
                                Mettre à jour
                            </button>
                        </form>
                    </td>

                    <td>
                        <form method="post"
                              action="<c:url value='/cart/remove/${item.weapon.id}'/>">

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
                Nombre total d’articles :
                <strong>${cart.totalQuantity}</strong>
            </p>

            <p>
                Total :
                <strong>${cart.totalPrice} €</strong>
            </p>
        </div>

        <div class="cart-actions">

            <form method="get"
                  action="<c:url value='/checkout'/>"
                  class="cart-checkout-form">

                <button type="submit">
                    Continuer vers la commande
                </button>
            </form>

        <form method="post"
              action="<c:url value='/cart/clear'/>"
              class="cart-clear-form">

            <button type="submit" class="btn-delete">
                Vider le panier
            </button>
        </form>

        </div>

    </c:if>

</section>