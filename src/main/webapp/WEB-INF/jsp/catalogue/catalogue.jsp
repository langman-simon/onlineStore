<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="catalogue-page">

    <header class="catalogue-header">

        <div>
            <p class="eyebrow">La collection</p>
            <h1>Catalogue</h1>
        </div>

        <form method="get"
              action="<c:url value='/catalogue'/>"
              class="catalogue-filter">

            <label for="categoryId">
                Catégorie
            </label>

            <select id="categoryId"
                    name="categoryId"
                    onchange="this.form.submit()">

                <option value="">
                    Toutes les catégories
                </option>

                <c:forEach var="category"
                           items="${categories}">

                    <option value="${category.id}"
                        <c:if test="${selectedCategoryId == category.id}">
                            selected
                        </c:if>>

                        ${category.name}

                    </option>

                </c:forEach>

            </select>

        </form>

    </header>

    <c:choose>

        <c:when test="${empty weapons}">

            <p class="catalogue-empty">
                Aucun produit disponible dans cette catégorie.
            </p>

        </c:when>

        <c:otherwise>

            <div class="catalogue-explorer"
                 id="catalogueExplorer">

                <%-- =========================================
                     LISTE LATÉRALE
                     ========================================= --%>

                <aside class="catalogue-sidebar">

                    <div class="catalogue-sidebar__heading">
                        <span>Produits</span>

                        <span class="catalogue-sidebar__count">
                            ${fn:length(weapons)}
                        </span>
                    </div>

                    <div class="catalogue-product-list"
                         role="tablist"
                         aria-label="Produits du catalogue">

                        <c:forEach var="weapon"
                                   items="${weapons}"
                                   varStatus="status">

                            <button type="button"
                                    class="catalogue-selector
                                           ${status.first ? 'is-active' : ''}"
                                    data-catalogue-index="${status.index}"
                                    role="tab"
                                    aria-selected="${status.first}"
                                    aria-controls="catalogue-product-${weapon.id}">

                                <span class="catalogue-selector__marker">
                                    <span class="catalogue-selector__preview">

                                        <c:choose>

                                            <c:when test="${not empty weapon.imageUrl}">
                                                <img src="${weapon.imageUrl}"
                                                     alt="">
                                            </c:when>

                                            <c:otherwise>
                                                <span class="catalogue-selector__placeholder">
                                                    H
                                                </span>
                                            </c:otherwise>

                                        </c:choose>

                                    </span>
                                </span>

                                <span class="catalogue-selector__information">

                                    <span class="catalogue-selector__name">
                                        ${weapon.name}
                                    </span>

                                    <span class="catalogue-selector__meta">
                                        ${weapon.category.name}
                                    </span>

                                </span>

                                <span class="catalogue-selector__price">
                                    ${weapon.price} €
                                </span>

                            </button>

                        </c:forEach>

                    </div>

                </aside>

                <%-- =========================================
                     SCÈNE DES PRODUITS
                     ========================================= --%>

                <div class="catalogue-stage">

                    <div class="catalogue-stage__background"
                         aria-hidden="true">

                        <span></span>
                        <span></span>
                        <span></span>

                    </div>

                    <div class="catalogue-stage__counter"
                         aria-live="polite">

                        <span id="catalogueCurrentIndex">
                            01
                        </span>

                        <span class="catalogue-stage__counter-separator">
                            /
                        </span>

                        <span>
                            <fmt:formatNumber value="${fn:length(weapons)}"
                                              pattern="00"/>
                        </span>

                    </div>

                    <div class="catalogue-products">

                        <c:forEach var="weapon"
                                   items="${weapons}"
                                   varStatus="status">

                            <article id="catalogue-product-${weapon.id}"
                                     class="catalogue-product
                                            ${status.first ? 'is-active' : ''}"
                                     data-catalogue-product
                                     data-index="${status.index}"
                                     role="tabpanel"
                                     aria-hidden="${not status.first}">

                                <div class="catalogue-product__visual">

                                    <div class="catalogue-product__orbit"
                                         aria-hidden="true">
                                    </div>

                                    <div class="catalogue-product__image">

                                        <c:choose>

                                            <c:when test="${not empty weapon.imageUrl}">
                                                <img src="${weapon.imageUrl}"
                                                     alt="${weapon.name}">
                                            </c:when>

                                            <c:otherwise>
                                                <div class="catalogue-product__placeholder">
                                                    <span>Hyperion</span>
                                                    <strong>${weapon.name}</strong>
                                                </div>
                                            </c:otherwise>

                                        </c:choose>

                                        <div class="catalogue-product__shade">
                                        </div>

                                    </div>

                                </div>

                                <div class="catalogue-product__content">

                                    <p class="catalogue-product__category">
                                        ${weapon.category.name}
                                    </p>

                                    <h2>
                                        ${weapon.name}
                                    </h2>

                                    <c:if test="${not empty weapon.description}">
                                        <p class="catalogue-product__description">
                                            ${weapon.description}
                                        </p>
                                    </c:if>

                                    <div class="catalogue-product__facts">

                                        <div class="catalogue-product__fact">

                                            <span>Prix</span>

                                            <strong>
                                                ${weapon.price} €
                                            </strong>

                                        </div>

                                        <div class="catalogue-product__fact">

                                            <span>Disponibilité</span>

                                            <c:choose>

                                                <c:when test="${weapon.stock > 0}">
                                                    <strong class="stock-available">
                                                        ${weapon.stock} en stock
                                                    </strong>
                                                </c:when>

                                                <c:otherwise>
                                                    <strong class="stock-unavailable">
                                                        Rupture de stock
                                                    </strong>
                                                </c:otherwise>

                                            </c:choose>

                                        </div>

                                    </div>

                                    <a class="catalogue-product__action"
                                       href="<c:url value='/weapons/${weapon.id}'/>">

                                        <span>
                                            Voir le produit
                                        </span>

                                        <span aria-hidden="true">
                                            →
                                        </span>

                                    </a>

                                </div>

                            </article>

                        </c:forEach>

                    </div>

                    <div class="catalogue-stage__controls">

                        <button type="button"
                                class="catalogue-control"
                                id="cataloguePrevious"
                                aria-label="Produit précédent">
                            ←
                        </button>

                        <button type="button"
                                class="catalogue-control"
                                id="catalogueNext"
                                aria-label="Produit suivant">
                            →
                        </button>

                    </div>

                </div>

            </div>

        </c:otherwise>

    </c:choose>

</section>