<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>
<header class="navbar">

    <div class="container navbar__container">

        <%-- Langue fixe à gauche --%>

        <div class="header-language">

            <div class="hover-menu">

                <button type="button"
                        class="language-button"
                        aria-label="Changer de langue"
                        title="Langue">

                    <span aria-hidden="true">🌐</span>

                    <span class="language-button__label">
                        Langue
                    </span>

                </button>

                <div class="hover-menu__content language-dropdown">

                    <c:url var="frUrl" value="">
                        <c:param name="lang" value="fr"/>
                    </c:url>

                    <a href="${frUrl}">
                        <img src="<c:url value='/assets/flags/fr.png'/>"
                             alt="">
                        Français
                    </a>

                    <c:url var="enUrl" value="">
                        <c:param name="lang" value="en"/>
                    </c:url>

                    <a href="${enUrl}">
                        <img src="<c:url value='/assets/flags/en.png'/>"
                             alt="">
                        English
                    </a>

                </div>

            </div>

        </div>

        <%-- Navigation centrale animée --%>

        <div class="header-navigation"
             id="headerNavigation">

            <a href="<c:url value='/'/>"
               class="header-brand"
               aria-label="Accueil Hyperion">
                Hyperion
            </a>

            <nav class="header-links"
                 aria-label="Navigation principale">

                <a href="<c:url value='/'/>">
                    Accueil
                </a>

                <a href="<c:url value='/catalogue'/>">
                    Catalogue
                </a>

                <a href="<c:url value='/company'/>">
                    Notre société
                </a>

                <a href="<c:url value='/cart'/>"
                   class="header-cart-link">

                    Panier

                    <span class="header-cart-count">
                        ${sessionCart.cart.totalQuantity}
                    </span>
                </a>

                <sec:authorize access="isAuthenticated()">
                    <a href="<c:url value='/order'/>">
                        Mes commandes
                    </a>
                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">
                    <a href="<c:url value='/admin'/>">
                        Administration
                    </a>
                </sec:authorize>

            </nav>

        </div>

        <%-- Utilisateur fixe à droite --%>

        <div class="header-user">

            <sec:authorize access="isAnonymous()">

                <div class="hover-menu">

                    <button type="button"
                            class="user-avatar user-avatar--anonymous"
                            aria-label="Menu utilisateur non connecté">
                        ?
                    </button>

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

                <sec:authentication property="name"
                                    var="currentLogin"/>

                <div class="hover-menu">

                    <button type="button"
                            class="user-avatar"
                            aria-label="Menu de ${currentLogin}">

                            ${fn:toUpperCase(
                                    fn:substring(currentLogin, 0, 1)
                                    )}

                    </button>

                    <div class="hover-menu__content user-dropdown">

                        <p class="user-dropdown__login">
                                ${currentLogin}
                        </p>

                        <a href="<c:url value='/order'/>">
                            Mes commandes
                        </a>

                        <a href="<c:url value='/account'/>">
                            Gérer mon compte
                        </a>

                        <sec:authorize access="hasRole('ADMIN')">
                            <a href="<c:url value='/admin'/>">
                                Administration
                            </a>
                        </sec:authorize>

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

        </div>

    </div>

</header>