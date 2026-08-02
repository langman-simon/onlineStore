<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="order-list">

    <h1>Mes commandes</h1>

    <c:choose>

        <c:when test="${empty orders}">
            <p>Aucune commande.</p>
        </c:when>

        <c:otherwise>

            <table>
                <thead>
                <tr>
                    <th>Numéro</th>
                    <th>Date</th>
                    <th>Total</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>

                        <td>
                            ${order.createdAt}
                        </td>

                        <td>${order.totalPrice} €</td>

                        <td>
                            <c:choose>
                                <c:when test="${order.status == 'PAID'}">
                                    Payée
                                </c:when>
                                <c:otherwise>
                                    En attente de paiement
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <a class="btn"
                               href="<c:url value='/order/${order.id}'/>">
                                Voir les détails
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </c:otherwise>

    </c:choose>

</section>