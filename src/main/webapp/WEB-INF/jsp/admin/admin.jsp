<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="admin-page">

    <%-- =====================================================
         EN-TÊTE
         ===================================================== --%>

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
            <summary class="btn admin-add-panel__button">
                Ajouter un produit
            </summary>

            <div class="admin-add-panel__content">

                <h2>Nouveau produit</h2>

                <form method="post"
                      action="<c:url value='/admin/weapons/add'/>"
                      enctype="multipart/form-data"
                      class="admin-form">

                    <div class="admin-form__grid">

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

                        <div class="form-group admin-form__full">
                            <label for="addDescription">Description</label>

                            <textarea id="addDescription"
                                      name="description"
                                      rows="5"
                                      maxlength="1000"
                                      required></textarea>
                        </div>

                        <div class="form-group admin-form__full">
                            <label for="addImage">Image</label>

                            <input type="file"
                                   id="addImage"
                                   name="image"
                                   accept="image/jpeg,image/png,image/webp"
                                   required>
                        </div>

                    </div>

                    <sec:csrfInput/>

                    <div class="admin-form__actions">
                        <button type="submit">
                            Ajouter au catalogue
                        </button>
                    </div>

                </form>

            </div>
        </details>

    </header>

    <%-- =====================================================
         MESSAGES
         ===================================================== --%>

    <c:if test="${not empty success}">
        <div class="admin-alert admin-alert--success">
            <span>${success}</span>

            <button type="button"
                    class="admin-alert__close"
                    aria-label="Fermer"
                    onclick="this.parentElement.remove()">
                ×
            </button>
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="admin-alert admin-alert--error">
            <span>${error}</span>

            <button type="button"
                    class="admin-alert__close"
                    aria-label="Fermer"
                    onclick="this.parentElement.remove()">
                ×
            </button>
        </div>
    </c:if>

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

        <form method="get"
              action="<c:url value='/admin'/>"
              class="admin-filter">

            <div class="form-group">
                <label for="filterCategoryId">
                    Filtrer par catégorie
                </label>

                <select id="filterCategoryId"
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
            </div>

        </form>

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

                        <%-- Image --%>

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

                        <%-- Informations principales --%>

                        <div class="admin-product__main">

                            <div class="admin-product__heading">

                                <div>
                                    <span class="admin-product__category">
                                        ${weapon.category.name}
                                    </span>

                                    <h2>${weapon.name}</h2>
                                </div>

                                <span class="admin-product__reference">
                                    ${weapon.reference}
                                </span>

                            </div>

                            <p class="admin-product__description">
                                ${weapon.description}
                            </p>

                            <div class="admin-product__metadata">

                                <div>
                                    <span>Fabricant</span>
                                    <strong>${weapon.manufacturer}</strong>
                                </div>

                                <div>
                                    <span>Prix</span>
                                    <strong>${weapon.price} €</strong>
                                </div>

                                <div>
                                    <span>Stock</span>

                                    <c:choose>

                                        <c:when test="${weapon.stock > 5}">
                                            <strong class="admin-stock admin-stock--good">
                                                ${weapon.stock}
                                            </strong>
                                        </c:when>

                                        <c:when test="${weapon.stock > 0}">
                                            <strong class="admin-stock admin-stock--low">
                                                ${weapon.stock}
                                            </strong>
                                        </c:when>

                                        <c:otherwise>
                                            <strong class="admin-stock admin-stock--empty">
                                                Rupture
                                            </strong>
                                        </c:otherwise>

                                    </c:choose>

                                </div>

                            </div>

                        </div>

                        <%-- Actions rapides --%>

                        <aside class="admin-product__actions">

                            <a class="btn admin-product__details"
                               href="<c:url value='/weapons/${weapon.id}'/>">
                                Voir la fiche
                            </a>

                            <form method="post"
                                  action="<c:url value='/admin/weapons/stock/${weapon.id}'/>"
                                  class="admin-stock-form">

                                <label for="stock-${weapon.id}">
                                    Stock
                                </label>

                                <div class="admin-stock-form__controls">

                                    <input type="number"
                                           id="stock-${weapon.id}"
                                           name="stock"
                                           min="0"
                                           value="${weapon.stock}"
                                           required>

                                    <sec:csrfInput/>

                                    <button type="submit">
                                        Mettre à jour
                                    </button>

                                </div>

                            </form>

                            <details class="admin-edit">

                                <summary class="btn">
                                    Modifier
                                </summary>

                                <div class="admin-edit__content">

                                    <h3>Modifier ${weapon.name}</h3>

                                    <form method="post"
                                          action="<c:url value='/admin/weapons/update/${weapon.id}'/>"
                                          enctype="multipart/form-data"
                                          class="admin-form">

                                        <div class="admin-form__grid">

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
                                                <label for="edit-stock-${weapon.id}">
                                                    Stock
                                                </label>

                                                <input type="number"
                                                       id="edit-stock-${weapon.id}"
                                                       name="stock"
                                                       min="0"
                                                       value="${weapon.stock}"
                                                       required>
                                            </div>

                                            <div class="form-group admin-form__full">
                                                <label for="description-${weapon.id}">
                                                    Description
                                                </label>

                                                <textarea id="description-${weapon.id}"
                                                          name="description"
                                                          rows="5"
                                                          maxlength="1000"
                                                          required>${weapon.description}</textarea>
                                            </div>

                                            <div class="form-group admin-form__full">
                                                <label for="image-${weapon.id}">
                                                    Remplacer l’image
                                                </label>

                                                <input type="file"
                                                       id="image-${weapon.id}"
                                                       name="image"
                                                       accept="image/jpeg,image/png,image/webp">

                                                <small>
                                                    Laissez vide pour conserver l’image actuelle.
                                                </small>
                                            </div>

                                        </div>

                                        <sec:csrfInput/>

                                        <div class="admin-form__actions">
                                            <button type="submit">
                                                Enregistrer
                                            </button>
                                        </div>

                                    </form>

                                </div>

                            </details>

                            <button type="button"
                                    class="btn-delete"
                                    onclick="openDeleteDialog('${weapon.id}')">
                                Supprimer
                            </button>

                            <dialog id="delete-dialog-${weapon.id}"
                                    class="admin-dialog">

                                <div class="admin-dialog__content">

                                    <h3>Supprimer le produit</h3>

                                    <p>
                                        Voulez-vous supprimer
                                        <strong>${weapon.name}</strong> ?
                                    </p>

                                    <p class="admin-dialog__warning">
                                        Cette action est définitive.
                                    </p>

                                    <div class="admin-dialog__actions">

                                        <button type="button"
                                                class="btn"
                                                onclick="closeDeleteDialog('${weapon.id}')">
                                            Annuler
                                        </button>

                                        <form method="post"
                                              action="<c:url value='/admin/remove/${weapon.id}'/>">

                                            <sec:csrfInput/>

                                            <button type="submit"
                                                    class="btn-delete">
                                                Confirmer
                                            </button>

                                        </form>

                                    </div>

                                </div>

                            </dialog>

                        </aside>

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

    <h2>Promotions</h2>

    <details class="admin-add-panel">
        <summary class="btn admin-add-panel__button">
            Ajouter une promotion
        </summary>

        <div class="admin-add-panel__content">

            <h3>Nouvelle promotion</h3>

            <form method="post" action="<c:url value='/admin/promotions/add'/>" class="admin-form">

                <div class="admin-form__grid">

                    <div class="form-group">
                        <label for="promoTitle">Titre</label>
                        <input type="text" id="promoTitle" name="title" maxlength="150" required>
                    </div>

                    <div class="form-group admin-form__full">
                        <label for="promoDescription">Description</label>
                        <textarea id="promoDescription" name="description" rows="4"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="promoDiscount">Réduction (%)</label>
                        <input type="number" id="promoDiscount" name="discountPercentage" min="0" max="100" step="0.01">
                    </div>

                    <div class="form-group">
                        <label for="promoFreeDelivery">Livraison gratuite</label>
                        <input type="checkbox" id="promoFreeDelivery" name="freeDelivery">
                    </div>

                    <div class="form-group">
                        <label for="promoStart">Date début</label>
                        <input type="date" id="promoStart" name="startDate">
                    </div>

                    <div class="form-group">
                        <label for="promoEnd">Date fin</label>
                        <input type="date" id="promoEnd" name="endDate">
                    </div>

                    <div class="form-group">
                        <label for="promoActive">Active</label>
                        <input type="checkbox" id="promoActive" name="active" checked>
                    </div>

                </div>

                <sec:csrfInput/>

                <div class="admin-form__actions">
                    <button type="submit">Ajouter la promotion</button>
                </div>

            </form>

        </div>
    </details>

    <h3>Liste des promotions</h3>

    <c:forEach var="promo" items="${promotions}">
        <article class="admin-promo">

            <div class="admin-promo__main">

                <h4>${promo.title}</h4>

                <p>${promo.description}</p>

                <p>
                    Réduction :
                    <c:choose>
                        <c:when test="${promo.discountPercentage != null}">
                            ${promo.discountPercentage}%
                        </c:when>
                        <c:otherwise>
                            Aucune
                        </c:otherwise>
                    </c:choose>
                </p>

                <p>
                    Livraison gratuite :
                    <c:if test="${promo.freeDelivery}">Oui</c:if>
                    <c:if test="${!promo.freeDelivery}">Non</c:if>
                </p>

                <p>
                    Valable du ${promo.startDate} au ${promo.endDate}
                </p>

            </div>

            <details class="admin-edit">
                <summary class="btn">Modifier</summary>

                <div class="admin-edit__content">

                    <h3>Modifier ${promo.title}</h3>

                    <form method="post" action="<c:url value='/admin/promotions/update/${promo.id}'/>" class="admin-form">

                        <div class="admin-form__grid">

                            <div class="form-group">
                                <label>Titre</label>
                                <input type="text" name="title" value="${promo.title}" required>
                            </div>

                            <div class="form-group admin-form__full">
                                <label>Description</label>
                                <textarea name="description">${promo.description}</textarea>
                            </div>

                            <div class="form-group">
                                <label>Réduction (%)</label>
                                <input type="number" name="discountPercentage"
                                       value="${promo.discountPercentage}"
                                       min="0" max="100" step="0.01">
                            </div>

                            <div class="form-group">
                                <label>Livraison gratuite</label>
                                <input type="checkbox" name="freeDelivery"
                                       <c:if test="${promo.freeDelivery}">checked</c:if>>
                            </div>

                            <div class="form-group">
                                <label>Date début</label>
                                <input type="date" name="startDate" value="${promo.startDate}">
                            </div>

                            <div class="form-group">
                                <label>Date fin</label>
                                <input type="date" name="endDate" value="${promo.endDate}">
                            </div>

                            <div class="form-group">
                                <label>Active</label>
                                <input type="checkbox" name="active"
                                       <c:if test="${promo.active}">checked</c:if>>
                            </div>

                        </div>

                        <sec:csrfInput/>

                        <div class="admin-form__actions">
                            <button type="submit">Modifier</button>
                        </div>

                    </form>

                </div>
            </details>

            <form method="post" action="<c:url value='/admin/promotions/remove/${promo.id}'/>">
                <sec:csrfInput/>
                <button type="submit" class="btn-delete">Supprimer</button>
            </form>

        </article>
    </c:forEach>

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
</script>