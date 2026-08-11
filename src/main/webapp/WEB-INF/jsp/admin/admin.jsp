<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="admin-page">

    <header class="admin-hero">

        <div>
            <p class="admin-hero__eyebrow">
                <spring:message code="admin.eyebrow"/>
            </p>

            <h1><spring:message code="admin.title"/></h1>

            <p class="admin-hero__description">
                <spring:message code="admin.hero.description"/>
            </p>
        </div>

        <details class="admin-add-panel">
            <summary class="btn btn--primary">
                <spring:message code="admin.openAddProduct"/>
            </summary>

            <div class="admin-add-panel__content">

                <h2><spring:message code="admin.newProduct"/></h2>

                <form method="post"
                      action="<c:url value='/admin/weapons/add'/>"
                      enctype="multipart/form-data"
                      class="admin-product-form form--light">

                    <div class="admin-product-form__grid">

                        <div class="form-group">
                            <label for="addName"><spring:message code="common.name"/></label>

                            <input type="text"
                                   id="addName"
                                   name="name"
                                   maxlength="150"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addReference"><spring:message code="common.reference"/></label>

                            <input type="text"
                                   id="addReference"
                                   name="reference"
                                   maxlength="100"
                                   placeholder="HYP-020"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addManufacturer"><spring:message code="common.manufacturer"/></label>

                            <input type="text"
                                   id="addManufacturer"
                                   name="manufacturer"
                                   maxlength="100"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addCategoryId"><spring:message code="common.category"/></label>

                            <select id="addCategoryId"
                                    name="categoryId"
                                    required>

                                <option value="" disabled selected>
                                    <spring:message code="admin.chooseCategory"/>
                                </option>

                                <c:forEach var="category"
                                           items="${categories}">

                                    <option value="${category.id}">
                                        <spring:message code="${category.name}" text="${category.name}"/>
                                    </option>

                                </c:forEach>

                            </select>
                        </div>

                        <div class="form-group">
                            <label for="addPrice"><spring:message code="common.price"/></label>

                            <input type="number"
                                   id="addPrice"
                                   name="price"
                                   min="0"
                                   step="0.01"
                                   required>
                        </div>

                        <div class="form-group">
                            <label for="addStock"><spring:message code="common.stock"/></label>

                            <input type="number"
                                   id="addStock"
                                   name="stock"
                                   min="0"
                                   required>
                        </div>

                        <div class="form-group admin-product-form__full">
                            <label for="addDescription"><spring:message code="common.description"/></label>

                            <textarea id="addDescription"
                                      name="description"
                                      rows="5"
                                      maxlength="1000"
                                      required></textarea>
                        </div>

                        <div class="form-group admin-product-form__full">
                            <label for="addImage"><spring:message code="common.image"/></label>

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
                            <spring:message code="admin.addProduct"/>
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
            <h2><spring:message code="admin.products"/></h2>

            <p>
                <spring:message code="admin.productsDisplayed" arguments="${fn:length(weapons)}"/>
            </p>
        </div>

        <div class="filter--list">

            <a href="<c:url value='/admin'/>"
               class="btn btn--filter ${empty selectedCategoryId ? 'is-active' : ''}">
                <spring:message code="catalogue.filter.all"/>
            </a>

            <c:forEach var="category" items="${categories}">

                <c:url var="categoryUrl" value="/admin">
                    <c:param name="categoryId"
                             value="${category.id}"/>
                </c:url>

                <a href="${categoryUrl}"
                   class="btn btn--filter ${selectedCategoryId == category.id ? 'is-active' : ''}">
                    <spring:message code="${category.name}" text="${category.name}"/>
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
                <h2><spring:message code="admin.noProducts"/></h2>

                <p>
                    <spring:message code="admin.noProductsText"/>
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
                                        <spring:message code="common.noImage"/>
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
                                    <strong><spring:message code="common.category"/></strong>
                                    <spring:message code="${weapon.category.name}" text="${weapon.category.name}"/>
                                </span>

                                <span>
                                    <strong><spring:message code="common.manufacturer"/></strong>
                                    ${weapon.manufacturer}
                                </span>

                                <span>
                                    <strong><spring:message code="common.stock"/></strong>
                                    ${weapon.stock}
                                </span>

                            </div>


                            <%-- Product actions --%>

                            <div class="admin-product__actions">

                                <a href="<c:url value='/weapons/${weapon.id}'/>"
                                   class="btn btn--primary">
                                    <spring:message code="admin.viewProduct"/>
                                </a>


                                <details class="admin-product-edit">

                                    <summary class="btn btn--primary">
                                        <spring:message code="admin.edit"/>
                                    </summary>

                                    <div class="admin-product-edit__content">

                                        <h3>
                                            <spring:message code="admin.editProduct" arguments="${weapon.name}"/>
                                        </h3>

                                        <form method="post"
                                              action="<c:url value='/admin/weapons/update/${weapon.id}'/>"
                                              enctype="multipart/form-data"
                                              class="admin-product-form form--light">

                                            <div class="admin-product-form__grid">

                                                <div class="form-group">
                                                    <label for="name-${weapon.id}">
                                                        <spring:message code="common.name"/>
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
                                                        <spring:message code="common.reference"/>
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
                                                        <spring:message code="common.manufacturer"/>
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
                                                        <spring:message code="common.category"/>
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

                                                                <spring:message code="${category.name}" text="${category.name}"/>

                                                            </option>

                                                        </c:forEach>

                                                    </select>
                                                </div>


                                                <div class="form-group">
                                                    <label for="price-${weapon.id}">
                                                        <spring:message code="common.price"/>
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
                                                        <spring:message code="common.stock"/>
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
                                                        <spring:message code="common.description"/>
                                                    </label>

                                                    <textarea id="description-${weapon.id}"
                                                              name="description"
                                                              rows="4"
                                                              maxlength="1000"
                                                              required>${weapon.description}</textarea>

                                                </div>


                                                <div class="form-group admin-product-form__full">

                                                    <label for="image-${weapon.id}">
                                                        <spring:message code="admin.replaceImage"/>
                                                    </label>

                                                    <input type="file"
                                                           id="image-${weapon.id}"
                                                           name="image"
                                                           accept="image/jpeg,image/png,image/webp">

                                                    <small>
                                                        <spring:message code="admin.keepImage"/>
                                                    </small>

                                                </div>

                                            </div>

                                            <sec:csrfInput/>

                                            <div class="admin-product-form__actions">

                                                <button type="submit"
                                                        class="btn btn--primary">
                                                    <spring:message code="common.save"/>
                                                </button>

                                            </div>

                                        </form>

                                    </div>

                                </details>


                                <button type="button"
                                        class="btn btn--delete"
                                        onclick="openDeleteDialog('${weapon.id}')">
                                    <spring:message code="common.delete"/>
                                </button>

                            </div>

                        </div>


                        <%-- Delete confirmation dialog --%>

                        <dialog id="delete-dialog-${weapon.id}"
                                class="admin-dialog">

                            <div class="admin-dialog__content">

                                <h3>
                                    <spring:message code="admin.deleteProduct"/>
                                </h3>

                                <p>
                                    <spring:message code="admin.deleteProductQuestion" arguments="${weapon.name}"/>
                                </p>

                                <p class="admin-dialog__warning">
                                    <spring:message code="admin.deleteWarning"/>
                                </p>

                                <div class="admin-dialog__actions">

                                    <button type="button"
                                            class="btn btn--primary"
                                            onclick="closeDeleteDialog('${weapon.id}')">
                                        <spring:message code="common.cancel"/>
                                    </button>

                                    <form method="post"
                                          action="<c:url value='/admin/remove/${weapon.id}'/>">

                                        <sec:csrfInput/>

                                        <button type="submit"
                                                class="btn btn--delete">
                                            <spring:message code="admin.confirm"/>
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

        <h2><spring:message code="admin.promotions"/></h2>

        <details class="admin-add-panel">

            <summary class="btn btn--primary">
                <spring:message code="admin.addPromotion"/>
            </summary>

            <div class="admin-add-panel__content">

                <h3><spring:message code="admin.newPromotion"/></h3>

                <form method="post"
                      action="<c:url value='/admin/promotions/add'/>"
                      class="admin-product-form form--light">

                    <div class="admin-product-form__grid">

                        <div class="form-group">
                            <label for="promoTitle">
                                <spring:message code="admin.promotionTitle"/>
                            </label>

                            <input type="text"
                                   id="promoTitle"
                                   name="title"
                                   maxlength="150"
                                   required>
                        </div>


                        <div class="form-group">
                            <label for="promoDiscount">
                                <spring:message code="admin.promotionDiscount"/>
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
                                <spring:message code="admin.promotionStart"/>
                            </label>

                            <input type="date"
                                   id="promoStart"
                                   name="startDate">
                        </div>


                        <div class="form-group">
                            <label for="promoEnd">
                                <spring:message code="admin.promotionEnd"/>
                            </label>

                            <input type="date"
                                   id="promoEnd"
                                   name="endDate">
                        </div>


                        <div class="form-group admin-product-form__full form--light">

                            <label for="promoDescription">
                                <spring:message code="common.description"/>
                            </label>

                            <textarea id="promoDescription"
                                      name="description"
                                      rows="4"></textarea>

                        </div>


                        <div class="form-group">

                            <label for="promoFreeDelivery">
                                <spring:message code="admin.promotionFreeDelivery"/>
                            </label>

                            <input type="checkbox"
                                   id="promoFreeDelivery"
                                   name="freeDelivery">

                        </div>


                        <div class="form-group">

                            <label for="promoActive">
                                <spring:message code="admin.promotionActive"/>
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
                            <spring:message code="admin.addPromotion"/>
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
                            <strong><spring:message code="admin.promotionDiscountLabel"/></strong>

                            <c:choose>

                                <c:when test="${promo.discountPercentage != null}">
                                    ${promo.discountPercentage} %
                                </c:when>

                                <c:otherwise>
                                    <spring:message code="common.none"/>
                                </c:otherwise>

                            </c:choose>
                        </p>


                        <p>
                            <strong><spring:message code="admin.promotionFreeDeliveryLabel"/></strong>

                            <c:choose>

                                <c:when test="${promo.freeDelivery}">
                                    <spring:message code="common.yes"/>
                                </c:when>

                                <c:otherwise>
                                    <spring:message code="common.no"/>
                                </c:otherwise>

                            </c:choose>
                        </p>


                        <p>
                            <strong><spring:message code="admin.promotionStartLabel"/></strong>

                            <c:choose>
                                <c:when test="${promo.startDate != null}">
                                    ${promo.startDate}
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="admin.notDefined"/>
                                </c:otherwise>
                            </c:choose>
                        </p>


                        <p>
                            <strong><spring:message code="admin.promotionEndLabel"/></strong>

                            <c:choose>
                                <c:when test="${promo.endDate != null}">
                                    ${promo.endDate}
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="admin.notDefinedF"/>
                                </c:otherwise>
                            </c:choose>
                        </p>


                        <p>
                            <strong><spring:message code="admin.promotionStatusLabel"/></strong>

                            <c:choose>

                                <c:when test="${promo.active}">
                                    <spring:message code="admin.promotionActiveLabel"/>
                                </c:when>

                                <c:otherwise>
                                    <spring:message code="admin.promotionInactiveLabel"/>
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
                                <spring:message code="admin.promotionTitle"/>
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
                                <spring:message code="admin.promotionDiscount"/>
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
                                <spring:message code="admin.promotionStart"/>
                            </label>

                            <input type="date"
                                   id="promo-start-${promo.id}"
                                   name="startDate"
                                   value="${promo.startDate}">

                        </div>


                        <div class="form-group">

                            <label for="promo-end-${promo.id}">
                                <spring:message code="admin.promotionEnd"/>
                            </label>

                            <input type="date"
                                   id="promo-end-${promo.id}"
                                   name="endDate"
                                   value="${promo.endDate}">

                        </div>


                        <div class="form-group admin-product-form__full">

                            <label for="promo-description-${promo.id}">
                                <spring:message code="common.description"/>
                            </label>

                            <textarea id="promo-description-${promo.id}"
                                      name="description"
                                      rows="4">${promo.description}</textarea>

                        </div>


                        <div class="form-group">

                            <label for="promo-free-delivery-${promo.id}">
                                <spring:message code="admin.promotionFreeDelivery"/>
                            </label>

                            <input type="checkbox"
                                   id="promo-free-delivery-${promo.id}"
                                   name="freeDelivery"
                                   <c:if test="${promo.freeDelivery}">checked</c:if>>

                        </div>


                        <div class="form-group">

                            <label for="promo-active-${promo.id}">
                                <spring:message code="admin.promotionActive"/>
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
                            <spring:message code="common.save"/>
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
                        <spring:message code="common.delete"/>
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