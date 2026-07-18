<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<h1>Votre panier</h1>

<c:if test="${empty cart.items}">
    <p>Votre panier est vide.</p>
</c:if>

<c:if test="${not empty cart.items}">
    <table>
        <thead>
        <tr>
            <th>Produit</th>
            <th>Prix unitaire</th>
            <th>Quantité</th>
            <th>Sous-total</th>
            <th>Action</th>
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

                <td>${item.weapon.price} €</td>

                <td>${item.quantity}</td>

                <td>${item.subtotal} €</td>

                <td>
                    <form method="post" action="<c:url value='/cart/remove/${item.weapon.id}'/>">
                    <button type="submit">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p>
        Nombre total d’articles :
        <strong>${cart.totalQuantity}</strong>
    </p>

    <p>
        Total :
        <strong>${cart.totalPrice} €</strong>
    </p>
</c:if>