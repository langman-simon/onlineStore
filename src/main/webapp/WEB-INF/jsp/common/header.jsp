<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<header class="navbar">

    <div class="container">

        <h1 class="logo">Hyperion</h1>

        <nav>

            <a href="<c:url value='/'/>">
                Accueil
            </a>

            <sec:authorize access="hasRole('ADMIN')">
                <a href="<c:url value='/admin'/>">
                    Gestion Stock
                </a>
            </sec:authorize>

            <a href="<c:url value='/catalogue'/>">
                Catalogue
            </a>

            <a href="<c:url value='/company'/>">
                Notre société
            </a>

            <a href="<c:url value='/cart'/>">
                Panier (${sessionCart.cart.totalQuantity})
            </a>

            <div class="hover-menu">

                <div class="hover-menu__content language-dropdown">

                    <c:url var="frUrl" value="">
                        <c:param name="lang" value="fr"/>
                    </c:url>

                    <a href="${frUrl}">
                        <img src="<c:url value='/assets/flags/fr.png'/>"
                             alt="Français">
                        Français
                    </a>

                    <c:url var="enUrl" value="">
                        <c:param name="lang" value="en"/>
                    </c:url>

                    <a href="${enUrl}">
                        <img src="<c:url value='/assets/flags/en.png'/>"
                             alt="English">
                        English
                    </a>

                </div>

            </div>

            <sec:authorize access="isAnonymous()">

                <div class="hover-menu">

                    <div class="user-avatar user-avatar--anonymous"
                         title="Non connecté">
                        ?
                    </div>

                    <div class="hover-menu__content user-dropdown">

                        <p class="user-dropdown__login">
                            Non connecté
                        </p>

                        <a href="<c:url value='/login'/>">
                            Se connecter
                        </a>

                        <a href="<c:url value='/register'/>">
                            Créer un compte
                        </a>

                    </div>

                </div>

            </sec:authorize>

            <sec:authorize access="isAuthenticated()">

                <sec:authentication property="name" var="currentLogin"/>

                <div class="hover-menu">

                    <div class="user-avatar"
                         title="${currentLogin}">
                        ${fn:toUpperCase(fn:substring(currentLogin, 0, 1))}
                    </div>

                    <div class="hover-menu__content user-dropdown">

                        <p class="user-dropdown__login">
                            ${currentLogin}
                        </p>

                        <a href="<c:url value='/account'/>">
                            Gérer mon compte
                        </a>

                        <form method="post"
                              action="<c:url value='/logout'/>">

                            <sec:csrfInput/>

                            <button type="submit">
                                Déconnexion
                            </button>

                        </form>

                    </div>

                </div>

            </sec:authorize>

        </nav>

    </div>

</header>