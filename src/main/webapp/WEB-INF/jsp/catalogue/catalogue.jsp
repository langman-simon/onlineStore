<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<spring:message code="catalogue.productsAria" var="productsAria"/>
<spring:message code="catalogue.previous" var="previousProduct"/>
<spring:message code="catalogue.next" var="nextProduct"/>
<spring:message code="catalogue.filter.searchPlaceholder" var="searchPlaceholder"/>

<section class="catalogue-page">

    <header class="catalogue-header">
        <div>
            <p class="eyebrow"><spring:message code="catalogue.eyebrow"/></p>
            <h1><spring:message code="page.catalogue"/></h1>
        </div>

        <form method="get"
              action="<c:url value='/catalogue'/>"
              class="catalogue-filter">

            <div class="catalogue-filter__field catalogue-filter__field--search">
                <label for="search">
                    <spring:message code="catalogue.filter.search"/>
                </label>

                <input id="search"
                       name="search"
                       type="search"
                       value="${fn:escapeXml(searchQuery)}"
                       placeholder="${searchPlaceholder}"
                       class="catalogue-filter__control"/>
            </div>

            <div class="catalogue-filter__field">
                <label for="categoryId">
                    <spring:message code="catalogue.filter.category"/>
                </label>

                <select id="categoryId"
                        name="categoryId"
                        class="catalogue-filter__control">

                    <option value="">
                        <spring:message code="catalogue.filter.all"/>
                    </option>

                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}"
                            <c:if test="${selectedCategoryId == category.id}">selected</c:if>>
                            <spring:message code="${category.name}" text="${category.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="catalogue-filter__field">
                <label for="sort">
                    <spring:message code="catalogue.filter.sort"/>
                </label>

                <select id="sort"
                        name="sort"
                        class="catalogue-filter__control">
                    <option value="nameAsc"
                        <c:if test="${selectedSort == 'nameAsc'}">selected</c:if>>
                        <spring:message code="catalogue.filter.nameAsc"/>
                    </option>
                    <option value="nameDesc"
                        <c:if test="${selectedSort == 'nameDesc'}">selected</c:if>>
                        <spring:message code="catalogue.filter.nameDesc"/>
                    </option>
                    <option value="priceAsc"
                        <c:if test="${selectedSort == 'priceAsc'}">selected</c:if>>
                        <spring:message code="catalogue.filter.priceAsc"/>
                    </option>
                    <option value="priceDesc"
                        <c:if test="${selectedSort == 'priceDesc'}">selected</c:if>>
                        <spring:message code="catalogue.filter.priceDesc"/>
                    </option>
                </select>
            </div>

            <div class="catalogue-filter__actions">
                <button type="submit" class="btn btn--primary">
                    <spring:message code="catalogue.filter.apply"/>
                </button>

                <a href="<c:url value='/catalogue'/>"
                   class="btn catalogue-filter__reset">
                    <spring:message code="catalogue.filter.reset"/>
                </a>
            </div>
        </form>
    </header>

    <c:choose>
        <c:when test="${empty weapons}">
            <p class="catalogue-empty">
                <spring:message code="catalogue.empty"/>
            </p>
        </c:when>

        <c:otherwise>
            <div class="catalogue-explorer" id="catalogueExplorer">

                <aside class="catalogue-sidebar">
                    <div class="catalogue-sidebar__heading">
                        <span><spring:message code="catalogue.products"/></span>
                        <span class="catalogue-sidebar__count">
                            ${fn:length(weapons)}
                        </span>
                    </div>

                    <div class="catalogue-product-list"
                         role="tablist"
                         aria-label="${productsAria}">

                        <c:forEach var="weapon"
                                   items="${weapons}"
                                   varStatus="status">

                            <button type="button"
                                    class="btn btn--selector catalogue-selector ${status.first ? 'is-active' : ''}"
                                    data-catalogue-index="${status.index}"
                                    role="tab"
                                    aria-selected="${status.first}"
                                    aria-controls="catalogue-product-${weapon.id}">

                                <span class="catalogue-selector__marker">
                                    <span class="catalogue-selector__preview">
                                        <c:choose>
                                            <c:when test="${not empty weapon.imageUrl}">
                                                <c:url var="weaponImageUrl" value="${weapon.imageUrl}"/>
                                                <img src="${weaponImageUrl}"
                                                     alt="<c:out value='${weapon.name}'/>"/>
                                            </c:when>

                                            <c:otherwise>
                                                <span class="catalogue-selector__placeholder">H</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </span>

                                <span class="catalogue-selector__information">
                                    <span class="catalogue-selector__name">
                                        <c:out value="${weapon.name}"/>
                                    </span>

                                    <span class="catalogue-selector__meta">
                                        <spring:message code="${weapon.category.name}"
                                                        text="${weapon.category.name}"/>
                                    </span>
                                </span>

                                <span class="catalogue-selector__price">
                                    ${weapon.price} €
                                </span>
                            </button>
                        </c:forEach>
                    </div>
                </aside>

                <div class="catalogue-stage">
                    <div class="catalogue-stage__background" aria-hidden="true">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>

                    <div class="catalogue-stage__counter" aria-live="polite">
                        <span id="catalogueCurrentIndex">01</span>
                        <span class="catalogue-stage__counter-separator">/</span>
                        <span>
                            <fmt:formatNumber value="${fn:length(weapons)}" pattern="00"/>
                        </span>
                    </div>

                    <div class="catalogue-products">
                        <c:forEach var="weapon"
                                   items="${weapons}"
                                   varStatus="status">

                            <article id="catalogue-product-${weapon.id}"
                                     class="catalogue-product ${status.first ? 'is-active' : ''}"
                                     data-catalogue-product
                                     data-index="${status.index}"
                                     role="tabpanel"
                                     aria-hidden="${not status.first}">

                                <div class="catalogue-product__visual">
                                    <div class="catalogue-product__orbit" aria-hidden="true"></div>

                                    <div class="catalogue-product__image">
                                        <c:choose>
                                            <c:when test="${not empty weapon.imageUrl}">
                                                <c:url var="weaponImageUrl" value="${weapon.imageUrl}"/>
                                                <img src="${weaponImageUrl}"
                                                     alt="<c:out value='${weapon.name}'/>"/>
                                            </c:when>

                                            <c:otherwise>
                                                <div class="catalogue-product__placeholder">
                                                    <span>Hyperion</span>
                                                    <strong><c:out value="${weapon.name}"/></strong>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>

                                        <div class="catalogue-product__shade"></div>
                                    </div>
                                </div>

                                <div class="catalogue-product__content">
                                    <p class="catalogue-product__category">
                                        <spring:message code="${weapon.category.name}"
                                                        text="${weapon.category.name}"/>
                                    </p>

                                    <h2><c:out value="${weapon.name}"/></h2>

                                    <c:if test="${not empty weapon.description}">
                                        <p class="catalogue-product__description">
                                            <c:out value="${weapon.description}"/>
                                        </p>
                                    </c:if>

                                    <div class="catalogue-product__facts">
                                        <div class="catalogue-product__fact">
                                            <span><spring:message code="common.price"/></span>
                                            <strong>${weapon.price} €</strong>
                                        </div>

                                        <div class="catalogue-product__fact">
                                            <span><spring:message code="catalogue.availability"/></span>

                                            <c:choose>
                                                <c:when test="${weapon.stock > 0}">
                                                    <strong class="stock-available">
                                                        ${weapon.stock} <spring:message code="common.available"/>
                                                    </strong>
                                                </c:when>

                                                <c:otherwise>
                                                    <strong class="stock-unavailable">
                                                        <spring:message code="common.unavailable"/>
                                                    </strong>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

                                    <a class="btn btn--primary btn--full"
                                       href="<c:url value='/weapons/${weapon.id}'/>">
                                        <span><spring:message code="catalogue.viewProduct"/></span>
                                    </a>
                                </div>
                            </article>
                        </c:forEach>
                    </div>

                    <div class="catalogue-stage__controls">
                        <button type="button"
                                class="catalogue-control"
                                id="cataloguePrevious"
                                aria-label="${previousProduct}">
                            ←
                        </button>

                        <button type="button"
                                class="catalogue-control"
                                id="catalogueNext"
                                aria-label="${nextProduct}">
                            →
                        </button>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</section>
