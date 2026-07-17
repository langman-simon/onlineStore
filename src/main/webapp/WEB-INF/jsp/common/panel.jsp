<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>
<h2>Votre Panier</h2>

<%-- Ton code pour lister les produits ici --%>
<c:choose>
    <c:when test="${empty cart}">
        <p>Votre panier est vide.</p>
    </c:when>
    <c:otherwise>
        <%-- Boucle sur les produits --%>
    </c:otherwise>
</c:choose>