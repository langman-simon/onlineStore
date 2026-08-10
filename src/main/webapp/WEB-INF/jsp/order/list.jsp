<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="orders">

    <p class="eyebrow">Historique</p>
    <h1>Mes commandes</h1>

    <c:choose>

        <c:when test="${empty orders}">
            <p>Vous n’avez encore aucune commande.</p>

            <a class="btn btn--primary btn--small" href="<c:url value='/catalogue'/>">
                Voir le catalogue
            </a>
        </c:when>

        <c:otherwise>

            <table>
                <thead>
                <tr>
                    <th>Numéro</th>
                    <th>Date</th>
                    <th>Prix initial</th>
                    <th>Réduction</th>
                    <th>Total</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>

                        <td><fmt:formatNumber value="${order.createdAt.dayOfMonth}" pattern="00"/>/<fmt:formatNumber value="${order.createdAt.monthValue}" pattern="00"/>/${order.createdAt.year} à <fmt:formatNumber value="${order.createdAt.hour}" pattern="00"/>:<fmt:formatNumber value="${order.createdAt.minute}" pattern="00"/></td>

                        <td>${order.originalPrice} €</td>

                        <td>
                            <c:choose>
                                <c:when test="${order.discountAmount > 0}">
                                    - ${order.discountAmount} €
                                </c:when>

                                <c:otherwise>
                                    Aucune
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <strong>${order.totalPrice} €</strong>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${order.status == 'PAID'}">
                                    <span class="success">
                                        Payée
                                    </span>
                                </c:when>

                                <c:otherwise>
                                    <span class="error">
                                        Paiement en attente
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>

                            <a class="btn btn--primary btn--full"
                               href="<c:url value='/order/${order.id}'/>">
                                Détails
                            </a>

                            <c:if test="${order.status != 'PAID'}">

                                <form method="post"
                                      action="<c:url value='/order/${order.id}/cancel'/>">

                                    <sec:csrfInput/>

                                    <button type="submit"
                                            class="btn btn--delete btn--full">
                                        Annuler la commande
                                    </button>

                                </form>

                            </c:if>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a class="btn btn--primary btn--full" href="<c:url value='/catalogue'/>">
                Retour au catalogue
            </a>

        </c:otherwise>

    </c:choose>

</section>