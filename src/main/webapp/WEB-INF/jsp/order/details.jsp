<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="order-details">

    <div class="order-header">
        <h1>Commande n°${order.id}</h1>

        <p>
            Statut :
            <strong>${order.status}</strong>
        </p>

        <p>
            Date :
            <strong>${order.createdAt}</strong>
        </p>
    </div>

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
        <c:forEach var="item" items="${order.items}">
            <tr>
                <td>${item.weapon.name}</td>
                <td>${item.unitPrice} €</td>
                <td>${item.quantity}</td>
                <td>${item.subtotal} €</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="order-total">
        <p>
            Total de la commande :
            <strong>${order.totalPrice} €</strong>
        </p>
    </div>

    <a class="btn" href="<c:url value='/catalogue'/>">
        Retour au catalogue
    </a>

</section>