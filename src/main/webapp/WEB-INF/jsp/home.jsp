<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<main class="home-page">

    <section class="home-hero">
        <div class="home-hero-content">
            <p class="eyebrow eyebrow--on-dark">Manufacture &amp; Maison de confiance</p>
            <h1>Hyperion Industries</h1>

            <p class="home-hero-subtitle">
                Solutions de défense, équipements spécialisés
                et technologies militaires de nouvelle génération.
            </p>

            <div class="home-actions">
                <a href="<c:url value='/company'/>"
                   class="btn btn--primary btn--large">
                    Notre société
                </a>
                <a href="<c:url value='/catalogue'/>"
                   class="btn btn--primary btn--large">
                    Découvrir le catalogue
                </a>
                <a class="btn btn--primary btn--large" href="<c:url value='/cart'/>">
                    Accéder à votre panier
                </a>
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="<c:url value='/admin'/>" class="btn btn--primary btn--large">
                            Administration
                        </a>
                    </sec:authorize>
                </sec:authorize>
            </div>
        </div>
    </section>

    <section class="home-section home-promo-banner">
        <p class="eyebrow">Avantages fidélité</p>
        <div class="ornament-divider"><span></span></div>
        <h2>Plus vous commandez, plus vous économisez</h2>

        <div class="home-features">

            <article class="home-feature">
                <h3>Livraison offerte</h3>
                <p>
                    Dès <strong>${freeDeliveryThreshold} €</strong> d'achat,
                    la livraison ne vous coûte rien.
                </p>
            </article>

            <article class="home-feature">
                <h3>-${tier2Rate}% de réduction</h3>
                <p>
                    À partir de <strong>${tier2Threshold} €</strong>
                    d'achat sur votre commande.
                </p>
            </article>

            <article class="home-feature">
                <h3>-${tier3Rate}% de réduction</h3>
                <p>
                    À partir de <strong>${tier3Threshold} €</strong>
                    d'achat, notre meilleur taux.
                </p>
            </article>

        </div>

        <c:if test="${not empty promotions}">
            <div class="home-active-promos">
                <h3>Offres en cours</h3>

                <c:forEach var="promo" items="${promotions}">
                    <div class="home-promo-card">
                        <strong>${promo.title}</strong>
                        <p>${promo.description}</p>

                    <c:if test="${promo.discountPercentage != null}">
                        <span class="home-promo-badge">-${promo.discountPercentage}%</span>
                    </c:if>

                    <c:if test="${promo.freeDelivery}">
                        <span class="home-promo-badge">Livraison offerte</span>
                    </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </section>
    <section class="home-section">
        <p class="eyebrow">Savoir-faire</p>
        <div class="ornament-divider"><span></span></div>
        <h2>Notre expertise</h2>

        <p>
            Hyperion Industries conçoit et distribue des équipements
            destinés aux opérations terrestres, maritimes et tactiques.
            Notre catalogue rassemble des produits adaptés à différents
            besoins, des équipements individuels aux systèmes lourds.
        </p>

        <div class="home-features">

            <article class="home-feature">
                <h3>Équipements spécialisés</h3>

                <p>
                    Une sélection de produits répartis par catégories :
                    armes, protections, munitions, lanceurs et équipements
                    maritimes.
                </p>
            </article>

            <article class="home-feature">
                <h3>Stock actualisé</h3>

                <p>
                    La disponibilité de chaque produit est affichée dans
                    le catalogue et vérifiée lors de la confirmation de
                    la commande.
                </p>
            </article>

            <article class="home-feature">
                <h3>Commande sécurisée</h3>

                <p>
                    Ajoutez les produits au panier, adaptez les quantités
                    et vérifiez le détail de votre commande avant sa
                    validation définitive.
                </p>
            </article>

        </div>
    </section>

    <section class="home-section home-catalogue-preview">
        <p class="eyebrow">Collections</p>
        <div class="ornament-divider"><span></span></div>
        <h2>Explorer nos catégories</h2>

        <div class="home-category-grid">

            <a href="<c:url value='/catalogue'/>"
               class="home-category-card">
                <h3>Armes et équipements</h3>
                <p>
                    Consultez nos armes de poing, fusils,
                    équipements tactiques et systèmes spécialisés.
                </p>
            </a>

            <a href="<c:url value='/catalogue'/>"
               class="home-category-card">
                <h3>Protections</h3>
                <p>
                    Équipements de protection individuelle
                    et solutions renforcées.
                </p>
            </a>

            <a href="<c:url value='/catalogue'/>"
               class="home-category-card">
                <h3>Équipements maritimes</h3>
                <p>
                    Sous-marins, croiseurs et bâtiments
                    militaires spécialisés.
                </p>
            </a>

        </div>
    </section>

    <section class="home-section home-call-to-action">
        <div class="ornament-divider"><span></span></div>
        <h2>Accéder au catalogue</h2>

        <p>
            Consultez les caractéristiques, les prix et les stocks
            disponibles pour chaque produit.
        </p>

        <a href="<c:url value='/catalogue'/>"
           class="btn btn--primary btn--large">
            Voir tous les produits
        </a>
    </section>

</main>