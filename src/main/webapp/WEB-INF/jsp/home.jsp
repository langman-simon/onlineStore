<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<section class="home-page">

    <section class="home-hero">
        <div class="home-hero-content">
            <p class="eyebrow eyebrow--on-dark">
                <spring:message code="home.hero.eyebrow"/>
            </p>

            <h1>Hyperion Industries</h1>

            <p class="home-hero-subtitle">
                <spring:message code="home.hero.subtitle"/>
            </p>

            <div class="home-actions">
                <a href="<c:url value='/company'/>"
                   class="btn btn--primary btn--large">
                    <spring:message code="home.hero.company"/>
                </a>

                <a href="<c:url value='/catalogue'/>"
                   class="btn btn--primary btn--large">
                    <spring:message code="home.hero.catalogue"/>
                </a>

                <a href="<c:url value='/cart'/>"
                   class="btn btn--primary btn--large">
                    <spring:message code="home.hero.cart"/>
                </a>

                <sec:authorize access="hasRole('ADMIN')">
                    <a href="<c:url value='/admin'/>"
                       class="btn btn--primary btn--large">
                        <spring:message code="common.admin"/>
                    </a>
                </sec:authorize>
            </div>
        </div>
    </section>

    <section class="home-section home-promo-banner">
        <p class="eyebrow">
            <spring:message code="home.loyalty.eyebrow"/>
        </p>

        <div class="ornament-divider"><span></span></div>

        <h2>
            <spring:message code="home.loyalty.title"/>
        </h2>

        <div class="home-features">
            <article class="home-feature">
                <h3>
                    <spring:message code="home.loyalty.freeDelivery.title"/>
                </h3>
                <p>
                    <spring:message code="home.loyalty.freeDelivery.before"/>
                    <strong>${freeDeliveryThreshold} €</strong>
                    <spring:message code="home.loyalty.freeDelivery.after"/>
                </p>
            </article>

            <article class="home-feature">
                <h3>
                    -${tier2Rate}% <spring:message code="home.loyalty.discount.title"/>
                </h3>
                <p>
                    <spring:message code="home.loyalty.discount.before"/>
                    <strong>${tier2Threshold} €</strong>
                    <spring:message code="home.loyalty.discount.after"/>
                </p>
            </article>

            <article class="home-feature">
                <h3>
                    -${tier3Rate}% <spring:message code="home.loyalty.discount.title"/>
                </h3>
                <p>
                    <spring:message code="home.loyalty.discount.before"/>
                    <strong>${tier3Threshold} €</strong>
                    <spring:message code="home.loyalty.best.after"/>
                </p>
            </article>
        </div>

        <c:if test="${not empty promotions}">
            <div class="home-active-promos">
                <h3><spring:message code="home.promotions.title"/></h3>

                <c:forEach var="promo" items="${promotions}">
                    <div class="home-promo-card">
                        <strong><c:out value="${promo.title}"/></strong>

                        <c:if test="${not empty promo.description}">
                            <p><c:out value="${promo.description}"/></p>
                        </c:if>

                        <c:if test="${promo.discountPercentage != null}">
                            <span class="home-promo-badge">
                                -${promo.discountPercentage}%
                            </span>
                        </c:if>

                        <c:if test="${promo.freeDelivery}">
                            <span class="home-promo-badge">
                                <spring:message code="home.loyalty.freeDelivery.title"/>
                            </span>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </section>

    <section class="home-section">
        <p class="eyebrow">
            <spring:message code="home.expertise.eyebrow"/>
        </p>

        <div class="ornament-divider"><span></span></div>

        <h2><spring:message code="home.expertise.title"/></h2>
        <p><spring:message code="home.expertise.intro"/></p>

        <div class="home-features">
            <article class="home-feature">
                <h3><spring:message code="home.expertise.specialized.title"/></h3>
                <p><spring:message code="home.expertise.specialized.text"/></p>
            </article>

            <article class="home-feature">
                <h3><spring:message code="home.expertise.stock.title"/></h3>
                <p><spring:message code="home.expertise.stock.text"/></p>
            </article>

            <article class="home-feature">
                <h3><spring:message code="home.expertise.order.title"/></h3>
                <p><spring:message code="home.expertise.order.text"/></p>
            </article>
        </div>
    </section>

    <section class="home-section home-catalogue-preview">
        <p class="eyebrow">
            <spring:message code="home.categories.eyebrow"/>
        </p>

        <div class="ornament-divider"><span></span></div>

        <h2><spring:message code="home.categories.title"/></h2>

        <div class="home-category-grid">
            <a href="<c:url value='/catalogue'/>"
               class="home-category-card">
                <h3><spring:message code="home.categories.weapons.title"/></h3>
                <p><spring:message code="home.categories.weapons.text"/></p>
            </a>

            <a href="<c:url value='/catalogue'/>"
               class="home-category-card">
                <h3><spring:message code="category.protection"/></h3>
                <p><spring:message code="home.categories.protection.text"/></p>
            </a>

            <a href="<c:url value='/catalogue'/>"
               class="home-category-card">
                <h3><spring:message code="category.maritime"/></h3>
                <p><spring:message code="home.categories.maritime.text"/></p>
            </a>
        </div>
    </section>

    <section class="home-section home-call-to-action">
        <div class="ornament-divider"><span></span></div>

        <h2><spring:message code="home.cta.title"/></h2>
        <p><spring:message code="home.cta.text"/></p>

        <a href="<c:url value='/catalogue'/>"
           class="btn btn--primary btn--large">
            <spring:message code="home.cta.button"/>
        </a>
    </section>

</section>
