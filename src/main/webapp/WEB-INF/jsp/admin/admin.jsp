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

            <article class="product-card product-card--add">

                <form method="post"
                      action="<c:url value='/admin/weapons/add'/>"
                      enctype="multipart/form-data"
                      class="product-card__form">

                    <div class="product-card__image">

                        <label for="image" class="image-upload-label">
                            Image du produit
                        </label>

                        <input type="file"
                               id="image"
                               name="image"
                               accept="image/jpeg,image/png,image/webp"
                               required>

                    </div>

                    <div class="product-card__content">

                        <div class="form-group">
                            <label for="name">Nom</label>

                            <input type="text"
                                   id="name"
                                   name="name"
                                   placeholder="Nom du produit"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="categoryId">Catégorie</label>

                            <select id="categoryId"
                                    name="categoryId"
                                    required>

                                <option value="" disabled selected>
                                    Choisir une catégorie
                                </option>

                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.id}">
                                        ${category.name}
                                    </option>
                                </c:forEach>

                            </select>
                        </div>

                        <div class="form-group">
                            <label for="price">Prix</label>

                            <input type="number"
                                   id="price"
                                   name="price"
                                   min="0"
                                   step="0.01"
                                   placeholder="0.00"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="stock">Stock</label>

                            <input type="number"
                                   id="stock"
                                   name="stock"
                                   min="0"
                                   placeholder="0"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="reference">Référence</label>

                            <input type="text"
                                   id="reference"
                                   name="reference"
                                   placeholder="HYP-020"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="manufacturer">Fabricant</label>

                            <input type="text"
                                   id="manufacturer"
                                   name="manufacturer"
                                   placeholder="Hyperion Industries"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="description">Description</label>

                            <textarea id="description"
                                      name="description"
                                      rows="4"
                                      placeholder="Description du produit"
                                      required></textarea>
                        </div>

                        <sec:csrfInput/>

                        <button type="submit" class="btn">
                            Valider l’ajout
                        </button>

                    </div>

                </form>

            </article>

        </div>
    </c:otherwise>
</c:choose>

</section>