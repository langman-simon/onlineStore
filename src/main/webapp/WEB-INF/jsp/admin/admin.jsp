<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="admin-page">

    <header class="admin-hero">

        <div>
            <p class="admin-hero__eyebrow">
                Espace administrateur
            </p>

            <h1>Gestion du catalogue</h1>

            <p class="admin-hero__description">
                Gérez les produits, les stocks et les informations
                du catalogue Hyperion.
            </p>
        </div>

        <details class="admin-add-panel">
            <summary class="btn btn--primary">
                Ajouter un produit
            </summary>

            <div class="admin-add-panel__content">

                <h2>Nouveau produit</h2>

                <form method="post"
                      action="<c:url value='/admin/weapons/add'/>"
                      enctype="multipart/form-data"
                      class="admin-product-form form--light">

                    <div class="admin-product-form__grid">

                        <div class="form-group">
                            <label for="addName">Nom</label>

                            <input type="text"
                                   id="addName"
                                   name="name"
                                   maxlength="150"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addReference">Référence</label>

                            <input type="text"
                                   id="addReference"
                                   name="reference"
                                   maxlength="100"
                                   placeholder="HYP-020"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addManufacturer">Fabricant</label>

                            <input type="text"
                                   id="addManufacturer"
                                   name="manufacturer"
                                   maxlength="100"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addCategoryId">Catégorie</label>

                            <select id="addCategoryId"
                                    name="categoryId"
                                    required>

                                <option value="" disabled selected>
                                    Choisir une catégorie
                                </option>

                                <c:forEach var="category"
                                           items="${categories}">

                                    <option value="${category.id}">
                                        ${category.name}
                                    </option>

                                </c:forEach>

                            </select>
                        </div>

                        <div class="form-group">
                            <label for="addPrice">Prix</label>

                            <input type="number"
                                   id="addPrice"
                                   name="price"
                                   min="0"
                                   step="0.01"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addStock">Stock</label>

                            <input type="number"
                                   id="addStock"
                                   name="stock"
                                   min="0"
                                   required>
                        </div>

                        <div class="form-group admin-product-form__full">
                            <label for="addDescription">Description</label>

                            <textarea id="addDescription"
                                      name="description"
                                      rows="5"
                                      maxlength="1000"
                                      required></textarea>
                        </div>

                        <div class="form-group admin-product-form__full">
                            <label for="addImage">Image</label>

                            <input type="file"
                                   id="addImage"
                                   name="image"
                                   accept="image/jpeg,image/png,image/webp"
                                   required>
                        </div>

                    </div>

                    <sec:csrfInput/>

                    <div class="admin-product-form__actions">
                        <button type="submit" class="btn btn--primary">
                            Ajouter au catalogue
                        </button>
                    </div>

                </form>

            </div>
        </details>

    </header>

    <%-- =====================================================
         BARRE D’OUTILS
         ===================================================== --%>

    <div class="admin-toolbar">

        <div>
            <h2>Produits</h2>

            <p>
                ${weapons.size()} produit(s) affiché(s)
            </p>
        </div>

        <div class="filter--list">

            <a href="<c:url value='/admin'/>"
               class="btn btn--filter ${empty selectedCategoryId ? 'is-active' : ''}">
                Toutes les catégories
            </a>

            <c:forEach var="category" items="${categories}">

                <c:url var="categoryUrl" value="/admin">
                    <c:param name="categoryId"
                             value="${category.id}"/>
                </c:url>

                <a href="${categoryUrl}"
                   class="btn btn--filter ${selectedCategoryId == category.id ? 'is-active' : ''}">
                    ${category.name}
                </a>

            </c:forEach>

        </div>

    </div>

    <%-- =====================================================
         LISTE DES PRODUITS
         ===================================================== --%>

    <c:choose>

        <c:when test="${empty weapons}">

            <div class="admin-empty">
                <h2>Aucun produit trouvé</h2>

                <p>
                    Aucun produit ne correspond à la catégorie sélectionnée.
                </p>
            </div>

        </c:when>

        <c:otherwise>

            <div class="admin-product-list">

                <c:forEach var="weapon" items="${weapons}">

                    <article class="admin-product">

                        <%-- Product image --%>

                        <div class="admin-product__image">

                            <c:choose>

                                <c:when test="${not empty weapon.imageUrl}">
                                    <img src="${weapon.imageUrl}"
                                         alt="${weapon.name}">
                                </c:when>

                                <c:otherwise>
                                    <div class="admin-product__placeholder">
                                        Aucune image
                                    </div>
                                </c:otherwise>

                            </c:choose>

                        </div>


                        <%-- Product summary --%>

                        <div class="admin-product__content">

                            <div class="admin-product__header">

                                <div>
                                    <h3>${weapon.name}</h3>

                                    <p class="admin-product__reference">
                                        ${weapon.reference}
                                    </p>
                                </div>

                                <strong class="admin-product__price">
                                    ${weapon.price} €
                                </strong>

                            </div>


                            <div class="admin-product__info">

                                <span>
                                    <strong>Catégorie</strong>
                                    ${weapon.category.name}
                                </span>

                                <span>
                                    <strong>Fabricant</strong>
                                    ${weapon.manufacturer}
                                </span>

                                <span>
                                    <strong>Stock</strong>
                                    ${weapon.stock}
                                </span>

                            </div>


                            <%-- Product actions --%>

                            <div class="admin-product__actions">

                                <a href="<c:url value='/weapons/${weapon.id}'/>"
                                   class="btn btn--primary">
                                    Voir la fiche
                                </a>


                                <details class="admin-product-edit">

                                    <summary class="btn btn--primary">
                                        Modifier
                                    </summary>

                                    <div class="admin-product-edit__content">

                                        <h3>
                                            Modifier ${weapon.name}
                                        </h3>

                                        <form method="post"
                                              action="<c:url value='/admin/weapons/update/${weapon.id}'/>"
                                              enctype="multipart/form-data"
                                              class="admin-product-form form--light">

                                            <div class="admin-product-form__grid">

                                                <div class="form-group">
                                                    <label for="name-${weapon.id}">
                                                        Nom
                                                    </label>

                                                    <input type="text"
                                                           id="name-${weapon.id}"
                                                           name="name"
                                                           value="${weapon.name}"
                                                           maxlength="150"
                                                           required>
                                                </div>


                                                <div class="form-group">
                                                    <label for="reference-${weapon.id}">
                                                        Référence
                                                    </label>

                                                    <input type="text"
                                                           id="reference-${weapon.id}"
                                                           name="reference"
                                                           value="${weapon.reference}"
                                                           maxlength="100"
                                                           required>
                                                </div>


                                                <div class="form-group">
                                                    <label for="manufacturer-${weapon.id}">
                                                        Fabricant
                                                    </label>

                                                    <input type="text"
                                                           id="manufacturer-${weapon.id}"
                                                           name="manufacturer"
                                                           value="${weapon.manufacturer}"
                                                           maxlength="100"
                                                           required>
                                                </div>


                                                <div class="form-group">
                                                    <label for="category-${weapon.id}">
                                                        Catégorie
                                                    </label>

                                                    <select id="category-${weapon.id}"
                                                            name="categoryId"
                                                            required>

                                                        <c:forEach var="category"
                                                                   items="${categories}">

                                                            <option value="${category.id}"
                                                                <c:if test="${weapon.category.id == category.id}">
                                                                    selected
                                                                </c:if>>

                                                                ${category.name}

                                                            </option>

                                                        </c:forEach>

                                                    </select>
                                                </div>


                                                <div class="form-group">
                                                    <label for="price-${weapon.id}">
                                                        Prix
                                                    </label>

                                                    <input type="number"
                                                           id="price-${weapon.id}"
                                                           name="price"
                                                           min="0"
                                                           step="0.01"
                                                           value="${weapon.price}"
                                                           required>
                                                </div>


                                                <div class="form-group">
                                                    <label for="stock-${weapon.id}">
                                                        Stock
                                                    </label>

                                                    <input type="number"
                                                           id="stock-${weapon.id}"
                                                           name="stock"
                                                           min="0"
                                                           value="${weapon.stock}"
                                                           required>
                                                </div>


                                                <div class="form-group admin-product-form__full">

                                                    <label for="description-${weapon.id}">
                                                        Description
                                                    </label>

                                                    <textarea id="description-${weapon.id}"
                                                              name="description"
                                                              rows="4"
                                                              maxlength="1000"
                                                              required>${weapon.description}</textarea>

                                                </div>


                                                <div class="form-group admin-product-form__full">

                                                    <label for="image-${weapon.id}">
                                                        Remplacer l'image
                                                    </label>

                                                    <input type="file"
                                                           id="image-${weapon.id}"
                                                           name="image"
                                                           accept="image/jpeg,image/png,image/webp">

                                                    <small>
                                                        Laissez vide pour conserver l'image actuelle.
                                                    </small>

                                                </div>

                                            </div>

                                            <sec:csrfInput/>

                                            <div class="admin-product-form__actions">

                                                <button type="submit"
                                                        class="btn btn--primary">
                                                    Enregistrer
                                                </button>

                                            </div>

                                        </form>

                                    </div>

                                </details>


                                <button type="button"
                                        class="btn btn--delete"
                                        onclick="openDeleteDialog('${weapon.id}')">
                                    Supprimer
                                </button>

                            </div>

                        </div>


                        <%-- Delete confirmation dialog --%>

                        <dialog id="delete-dialog-${weapon.id}"
                                class="admin-dialog">

                            <div class="admin-dialog__content">

                                <h3>
                                    Supprimer le produit
                                </h3>

                                <p>
                                    Voulez-vous supprimer
                                    <strong>${weapon.name}</strong> ?
                                </p>

                                <p class="admin-dialog__warning">
                                    Cette action est définitive.
                                </p>

                                <div class="admin-dialog__actions">

                                    <button type="button"
                                            class="btn btn--primary"
                                            onclick="closeDeleteDialog('${weapon.id}')">
                                        Annuler
                                    </button>

                                    <form method="post"
                                          action="<c:url value='/admin/remove/${weapon.id}'/>">

                                        <sec:csrfInput/>

                                        <button type="submit"
                                                class="btn btn--delete">
                                            Confirmer
                                        </button>

                                    </form>

                                </div>

                            </div>

                        </dialog>

                    </article>

                </c:forEach>

            </div>

        </c:otherwise>

    </c:choose>

</section>

<%-- =====================================================
     PROMOTIONS
     ===================================================== --%>

<hr class="admin-separator">

<section class="admin-promotions">

    <div class="admin-promotions__header">

        <h2>Promotions</h2>

        <details class="admin-add-panel">

            <summary class="btn btn--primary">
                Ajouter une promotion
            </summary>

            <div class="admin-add-panel__content">

                <h3>Nouvelle promotion</h3>

                <form method="post"
                      action="<c:url value='/admin/promotions/add'/>"
                      class="admin-product-form form--light">

                    <div class="admin-product-form__grid">

                        <div class="form-group">
                            <label for="promoTitle">
                                Titre
                            </label>

                            <input type="text"
                                   id="promoTitle"
                                   name="title"
                                   maxlength="150"
                                   required>
                        </div>


                        <div class="form-group">
                            <label for="promoDiscount">
                                Réduction (%)
                            </label>

                            <input type="number"
                                   id="promoDiscount"
                                   name="discountPercentage"
                                   min="0"
                                   max="100"
                                   step="0.01">
                        </div>


                        <div class="form-group">
                            <label for="promoStart">
                                Date de début
                            </label>

                            <input type="date"
                                   id="promoStart"
                                   name="startDate">
                        </div>


                        <div class="form-group">
                            <label for="promoEnd">
                                Date de fin
                            </label>

                            <input type="date"
                                   id="promoEnd"
                                   name="endDate">
                        </div>


                        <div class="form-group admin-product-form__full form--light">

                            <label for="promoDescription">
                                Description
                            </label>

                            <textarea id="promoDescription"
                                      name="description"
                                      rows="4"></textarea>

                        </div>


                        <div class="form-group">

                            <label for="promoFreeDelivery">
                                Livraison gratuite
                            </label>

                            <input type="checkbox"
                                   id="promoFreeDelivery"
                                   name="freeDelivery">

                        </div>


                        <div class="form-group">

                            <label for="promoActive">
                                Promotion active
                            </label>

                            <input type="checkbox"
                                   id="promoActive"
                                   name="active"
                                   checked>

                        </div>

                    </div>

                    <sec:csrfInput/>

                    <div class="admin-product-form__actions">

                        <button type="submit"
                                class="btn btn--primary">
                            Ajouter la promotion
                        </button>

                    </div>

                </form>

            </div>

        </details>

    </div>


    <%-- Promotion list --%>

    <div class="admin-promotion-list">

        <c:forEach var="promo"
                   items="${promotions}">

            <article class="admin-promo">

                <div class="admin-promo__main">

                    <h3>
                        ${promo.title}
                    </h3>

                    <c:if test="${not empty promo.description}">
                        <p>
                            ${promo.description}
                        </p>
                    </c:if>


                    <div class="admin-promo__metadata">

                        <p>
                            <strong>Réduction :</strong>

                            <c:choose>

                                <c:when test="${promo.discountPercentage != null}">
                                    ${promo.discountPercentage} %
                                </c:when>

                                <c:otherwise>
                                    Aucune
                                </c:otherwise>

                            </c:choose>
                        </p>


                        <p>
                            <strong>Livraison gratuite :</strong>

                            <c:choose>

                                <c:when test="${promo.freeDelivery}">
                                    Oui
                                </c:when>

                                <c:otherwise>
                                    Non
                                </c:otherwise>

                            </c:choose>
                        </p>


                        <p>
                            <strong>Début :</strong>

                            <c:choose>
                                <c:when test="${promo.startDate != null}">
                                    ${promo.startDate}
                                </c:when>
                                <c:otherwise>
                                    Non défini
                                </c:otherwise>
                            </c:choose>
                        </p>


                        <p>
                            <strong>Fin :</strong>

                            <c:choose>
                                <c:when test="${promo.endDate != null}">
                                    ${promo.endDate}
                                </c:when>
                                <c:otherwise>
                                    Non définie
                                </c:otherwise>
                            </c:choose>
                        </p>


                        <p>
                            <strong>Statut :</strong>

                            <c:choose>

                                <c:when test="${promo.active}">
                                    Active
                                </c:when>

                                <c:otherwise>
                                    Inactive
                                </c:otherwise>

                            </c:choose>
                        </p>

                    </div>

                </div>


                <%-- Editable promotion --%>

                <form method="post"
                      action="<c:url value='/admin/promotions/update/${promo.id}'/>"
                      class="admin-product-form form--light">

                    <div class="admin-product-form__grid">

                        <div class="form-group">

                            <label for="promo-title-${promo.id}">
                                Titre
                            </label>

                            <input type="text"
                                   id="promo-title-${promo.id}"
                                   name="title"
                                   value="${promo.title}"
                                   maxlength="150"
                                   required>

                        </div>


                        <div class="form-group">

                            <label for="promo-discount-${promo.id}">
                                Réduction (%)
                            </label>

                            <input type="number"
                                   id="promo-discount-${promo.id}"
                                   name="discountPercentage"
                                   value="${promo.discountPercentage}"
                                   min="0"
                                   max="100"
                                   step="0.01">

                        </div>


                        <div class="form-group">

                            <label for="promo-start-${promo.id}">
                                Date de début
                            </label>

                            <input type="date"
                                   id="promo-start-${promo.id}"
                                   name="startDate"
                                   value="${promo.startDate}">

                        </div>


                        <div class="form-group">

                            <label for="promo-end-${promo.id}">
                                Date de fin
                            </label>

                            <input type="date"
                                   id="promo-end-${promo.id}"
                                   name="endDate"
                                   value="${promo.endDate}">

                        </div>


                        <div class="form-group admin-product-form__full">

                            <label for="promo-description-${promo.id}">
                                Description
                            </label>

                            <textarea id="promo-description-${promo.id}"
                                      name="description"
                                      rows="4">${promo.description}</textarea>

                        </div>


                        <div class="form-group">

                            <label for="promo-free-delivery-${promo.id}">
                                Livraison gratuite
                            </label>

                            <input type="checkbox"
                                   id="promo-free-delivery-${promo.id}"
                                   name="freeDelivery"
                                   <c:if test="${promo.freeDelivery}">checked</c:if>>

                        </div>


                        <div class="form-group">

                            <label for="promo-active-${promo.id}">
                                Promotion active
                            </label>

                            <input type="checkbox"
                                   id="promo-active-${promo.id}"
                                   name="active"
                                   <c:if test="${promo.active}">checked</c:if>>

                        </div>

                    </div>

                    <sec:csrfInput/>

                    <div class="admin-product-form__actions">

                        <button type="submit"
                                class="btn btn--primary">
                            Enregistrer
                        </button>

                    </div>

                </form>


                <%-- Delete promotion --%>

                <form method="post"
                      action="<c:url value='/admin/promotions/remove/${promo.id}'/>"
                      class="admin-promo__delete">

                    <sec:csrfInput/>

                    <button type="submit"
                            class="btn btn--delete">
                        Supprimer
                    </button>

                </form>

            </article>

        </c:forEach>

    </div>

</section>


<script>
    function openDeleteDialog(weaponId) {
        const dialog = document.getElementById(
            "delete-dialog-" + weaponId
        );

        if (dialog) {
            dialog.showModal();
        }
    }

    function closeDeleteDialog(weaponId) {
        const dialog = document.getElementById(
            "delete-dialog-" + weaponId
        );

        if (dialog) {
            dialog.close();
        }
    }

    document.addEventListener("click", function (event) {
        if (event.target.classList.contains("admin-dialog")) {
            event.target.close();
        }
    });
    document.querySelectorAll(".admin-add-panel").forEach(function (panel) {
        panel.addEventListener("mouseleave", function () {
            panel.removeAttribute("open");
        });
    });
    document.querySelectorAll(".admin-product-edit").forEach(function (panel) {

        panel.addEventListener("toggle", function () {

            if (!panel.open) {
                return;
            }

            document
                .querySelectorAll(".admin-product-edit[open]")
                .forEach(function (otherPanel) {

                    if (otherPanel !== panel) {
                        otherPanel.removeAttribute("open");
                    }

                });

        });

        panel.addEventListener("mouseleave", function () {
            panel.removeAttribute("open");
        });

    });
</script>