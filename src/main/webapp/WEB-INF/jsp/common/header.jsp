<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<header class="navbar">

    <div class="container">

        <h1 class="logo">Hyperion</h1>

        <nav>

            <a href="<c:url value='/'/>">Accueil</a>

            <a href="<c:url value='/catalogue'/>">Catalogue</a>

            <a href="<c:url value='/company'/>">Notre société</a>

            <a href="<c:url value='/cart'/>">
                Panier (${panel.cart.totalQuantity})
            </a>

            <a href="<c:url value='/login'/>">Connexion</a>

            <a href="<c:url value='/register'/>">Inscription</a>

            <a href="?lang=fr">FR</a>

            <a href="?lang=en">EN</a>

        </nav>

    </div>

</header>