<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<header class="navbar">

    <div class="container">

        <h1 class="logo">Hyperion</h1>

        <nav>

            <a href="<c:url value='/'/>">Accueil</a>

            <sec:authorize access="hasRole('ADMIN')">
                <a href="<c:url value='/admin'/>">
                    Gestion Stock
                </a>
            </sec:authorize>

            <a href="<c:url value='/catalogue'/>">Catalogue</a>

            <a href="<c:url value='/company'/>">Notre société</a>

            <a href="<c:url value='/cart'/>">Panier (${panel.cart.totalQuantity})</a>

            <a href="?lang=fr">FR</a>

            <a href="?lang=en">EN</a>

            <sec:authorize access="isAnonymous()">
                <a href="<c:url value='/login'/>">Connexion</a>
                <a href="<c:url value='/register'/>">Inscription</a>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">
                <span>${sessionScope.login}</span>

                <form method="post"
                      action="<c:url value='/logout'/>"
                      class="logout-form">

                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}">

                    <button type="submit" class="nav-link-button">
                        Déconnexion
                    </button>
                </form>
            </sec:authorize>

        </nav>

    </div>

</header>