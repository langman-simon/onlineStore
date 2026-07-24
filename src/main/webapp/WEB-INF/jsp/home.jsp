<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<main class="home-page">

    <section class="home-hero">
        <div class="home-hero-content">
            <h1>Hyperion Industries</h1>

            <p class="home-hero-subtitle">
                Solutions de défense, équipements spécialisés
                et technologies militaires de nouvelle génération.
            </p>

            <div class="home-actions">
                <a href="<c:url value='/catalogue'/>"
                   class="btn">
                    Découvrir le catalogue
                </a>

                <a href="<c:url value='/company'/>"
                   class="btn btn-secondary">
                    Notre société
                </a>
            </div>
        </div>
    </section>

    <section class="home-section">
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
        <h2>Accéder au catalogue</h2>

        <p>
            Consultez les caractéristiques, les prix et les stocks
            disponibles pour chaque produit.
        </p>

        <a href="<c:url value='/catalogue'/>"
           class="btn">
            Voir tous les produits
        </a>
    </section>

</main>