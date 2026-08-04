<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="order-details">

    <div class="order-header">
        <p class="eyebrow">Détail de commande</p>
        <h1>Commande n°${order.id}</h1>

        <p>
            Date :
            <strong><fmt:formatNumber value="${order.createdAt.dayOfMonth}" pattern="00"/>/<fmt:formatNumber value="${order.createdAt.monthValue}" pattern="00"/>/${order.createdAt.year} à <fmt:formatNumber value="${order.createdAt.hour}" pattern="00"/>:<fmt:formatNumber value="${order.createdAt.minute}" pattern="00"/></strong>
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
            Prix initial :
            <strong>${order.originalPrice} €</strong>
        </p>

        <c:if test="${order.discountAmount > 0}">
            <p>
                Réduction :
                <strong>- ${order.discountAmount} €</strong>
            </p>
        </c:if>

        <p>
            Total :
            <strong>${order.totalPrice} €</strong>
        </p>

        <c:choose>
            <c:when test="${order.status == 'PAID'}">
                <p class="success">
                    Commande payée
                </p>
            </c:when>

            <c:otherwise>
                <p class="error">
                    Paiement en attente
                </p>
            </c:otherwise>
        </c:choose>

    </div>

    <div class="order-actions">

        <a class="btn"
           href="<c:url value='/order'/>">
            Mes commandes
        </a>

        <a class="btn"
           href="<c:url value='/catalogue'/>">
            Retour au catalogue
        </a>

        <c:if test="${order.status == 'PENDING_PAYMENT'}">

            <form method="post"
                  action="${paypalSandboxUrl}">

                <input type="hidden"
                       name="business"
                       value="${paypalSellerEmail}">

                <input type="hidden"
                       name="cmd"
                       value="_xclick">

                <input type="hidden"
                       name="amount"
                       value="${order.totalPrice}">

                <input type="hidden"
                       name="item_name"
                       value="Commande Hyperion n°${order.id}">

                <input type="hidden"
                       name="currency_code"
                       value="EUR">

                <input type="hidden"
                       name="lc"
                       value="FR">

                <input type="hidden"
                       name="return"
                       value="${baseUrl}/order/${order.id}/payment/success">

                <input type="hidden"
                       name="cancel_return"
                       value="${baseUrl}/order/${order.id}/payment/cancel">

                <button type="submit">
                    Payer avec PayPal
                </button>

            </form>

        </c:if>

    </div>

</section>