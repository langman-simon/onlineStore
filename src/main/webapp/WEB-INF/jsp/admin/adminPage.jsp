<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="catalogue-page">

    <div class="catalogue-header">
        <h1>Catalogue</h1>

        <form method="get"
              action="<c:url value='/catalogue'/>"
              class="catalogue-filter">

            <label for="categoryId">Catégorie</label>

            <select id="categoryId"
                    name="categoryId"
                    onchange="this.form.submit()">

                <option value="">Toutes les catégories</option>

                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}"
                        <c:if test="${selectedCategoryId == category.id}">
                            selected
                        </c:if>>
                        ${category.name}
                    </option>
                </c:forEach>
            </select>

        </form>
    </div>

<c:choose>
    <c:when test="${empty weapons}">
        <p class="catalogue-empty">
            Aucun produit disponible dans cette catégorie.
        </p>
    </c:when>

    <c:otherwise>
        <div class="product-grid">

            <c:forEach var="weapon" items="${weapons}">
                <article class="product-card">

                    <div class="product-card__image">
                        <c:choose>
                            <c:when test="${not empty weapon.imageUrl}">
                                <img src="${weapon.imageUrl}"
                                     alt="${weapon.name}">
                            </c:when>

                            <c:otherwise>
                                <div class="image-placeholder">
                                    Aucune image
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="product-card__content">

                        <h2>${weapon.name}</h2>

                        <p class="product-card__category">
                            ${weapon.category.name}
                        </p>

                        <p class="product-price">
                            ${weapon.price} €
                        </p>

                        <c:choose>
                            <c:when test="${weapon.stock > 0}">
                                <p class="stock-available">
                                    En stock : ${weapon.stock}
                                </p>
                            </c:when>

                            <c:otherwise>
                                <p class="stock-unavailable">
                                    Rupture de stock
                                </p>
                            </c:otherwise>
                        </c:choose>

                        <a class="btn"
                           href="<c:url value='/weapons/${weapon.id}'/>">
                            Voir le détail
                        </a>

                        <form method="post"
                              action="<c:url value='/admin/remove/${item.weapon.id}'/>">

                            <button type="submit" class="btn-delete">
                                Supprimer Produit
                            </button>
                        </form>

                    </div>

                </article>
            </c:forEach>

        </div>
    </c:otherwise>
</c:choose>

</section>