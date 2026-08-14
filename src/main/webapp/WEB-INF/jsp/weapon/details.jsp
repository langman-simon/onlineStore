<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="product-details">

    <div class="product-details__image">
        <c:choose>
            <c:when test="${not empty weapon.imageUrl}">
                <c:url var="weaponImageUrl" value="${weapon.imageUrl}"/>
                <img src="${weaponImageUrl}"
                     alt="<c:out value='${weapon.name}'/>"/>
            </c:when>

            <c:otherwise>
                <div class="image-placeholder">
                    <spring:message code="common.noImage"/>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="product-details__content">
        <p class="eyebrow">
            <spring:message code="${weapon.category.name}"
                            text="${weapon.category.name}"/>
        </p>

        <h1><c:out value="${weapon.name}"/></h1>

        <p class="product-price">${weapon.price} €</p>

        <p><c:out value="${weapon.description}"/></p>

        <dl class="product-characteristics">
            <dt><spring:message code="common.category"/></dt>
            <dd>
                <spring:message code="${weapon.category.name}"
                                text="${weapon.category.name}"/>
            </dd>

            <dt><spring:message code="common.manufacturer"/></dt>
            <dd><c:out value="${weapon.manufacturer}"/></dd>

            <dt><spring:message code="common.reference"/></dt>
            <dd><c:out value="${weapon.reference}"/></dd>

            <dt><spring:message code="common.stock"/></dt>
            <dd>${weapon.stock}</dd>
        </dl>

        <c:choose>
            <c:when test="${weapon.stock > 0}">
                <form method="post"
                      action="<c:url value='/cart/add/${weapon.id}'/>"
                      class="add-to-cart-form">

                    <div>
                        <label for="quantity">
                            <spring:message code="common.quantity"/>
                        </label>

                        <input id="quantity"
                               type="number"
                               class="quantity-input"
                               name="quantity"
                               value="1"
                               min="1"
                               max="${weapon.stock}"
                               required>
                    </div>

                    <sec:csrfInput/>

                    <button type="submit" class="btn btn--primary btn--large">
                        <spring:message code="product.addToCart"/>
                    </button>

                    <a class="btn btn--primary btn--large"
                       href="<c:url value='/catalogue'/>">
                        <spring:message code="common.backCatalogue"/>
                    </a>

                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="<c:url value='/admin'/>"
                           class="btn btn--primary btn--large">
                            <spring:message code="common.admin"/>
                        </a>
                    </sec:authorize>
                </form>
            </c:when>

            <c:otherwise>
                <p class="error">
                    <spring:message code="product.unavailable"/>
                </p>

                <a class="btn btn--primary btn--large"
                   href="<c:url value='/catalogue'/>">
                    <spring:message code="common.backCatalogue"/>
                </a>
            </c:otherwise>
        </c:choose>
    </div>

</section>
