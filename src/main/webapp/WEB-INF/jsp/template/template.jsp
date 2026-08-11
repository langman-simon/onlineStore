<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>
        <c:choose>
            <c:when test="${not empty titleKey}">
                <spring:message code="${titleKey}"/>
            </c:when>
            <c:otherwise>
                <c:out value="${title}"/>
            </c:otherwise>
        </c:choose>
    </title>

    <link rel="stylesheet" href="<c:url value='/css/base/core.css'/>">

    <link rel="stylesheet" href="<c:url value='/css/layout/layout.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/layout/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/layout/header-bubbles.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/layout/footer.css'/>">

    <link rel="stylesheet" href="<c:url value='/css/components/buttons.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/components/forms.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/components/tables.css'/>">

    <link rel="stylesheet" href="<c:url value='/css/pages/home.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/catalogue.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/product-details.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/cart.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/checkout.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/company.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/order.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/auth.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/account.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/pages/admin.css'/>">
</head>

<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<c:if test="${not empty sessionScope.globalBannerMessage}">
    <spring:message code="common.bannerAria" var="bannerAria"/>
    <spring:message code="common.close" var="bannerClose"/>

    <div id="global-banner"
         class="global-banner global-banner--${sessionScope.globalBannerType}"
         role="status"
         aria-live="polite"
         tabindex="0"
         aria-label="${bannerAria}">

        <span class="global-banner__icon" aria-hidden="true">
            <c:choose>
                <c:when test="${sessionScope.globalBannerType == 'success'}">✓</c:when>
                <c:when test="${sessionScope.globalBannerType == 'error'}">!</c:when>
                <c:when test="${sessionScope.globalBannerType == 'warning'}">!</c:when>
                <c:otherwise>i</c:otherwise>
            </c:choose>
        </span>

        <span class="global-banner__message">
            <c:out value="${sessionScope.globalBannerMessage}"/>
        </span>

        <span class="global-banner__hint" aria-hidden="true">
            ${bannerClose}
        </span>

        <div class="global-banner__timer" aria-hidden="true"></div>
    </div>

    <c:remove var="globalBannerMessage" scope="session"/>
    <c:remove var="globalBannerType" scope="session"/>
</c:if>

<main>
    <div class="container">
        <jsp:include page="${body}"/>
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const banner = document.getElementById("global-banner");

        if (!banner) {
            return;
        }

        let closing = false;

        function closeBanner() {
            if (closing) {
                return;
            }

            closing = true;
            banner.classList.add("global-banner--closing");

            window.setTimeout(function () {
                banner.remove();
            }, 350);
        }

        banner.addEventListener("click", closeBanner);

        banner.addEventListener("keydown", function (event) {
            if (
                event.key === "Enter"
                || event.key === " "
                || event.key === "Escape"
            ) {
                event.preventDefault();
                closeBanner();
            }
        });

        window.setTimeout(closeBanner, 5000);
    });
</script>

<script src="<c:url value='/js/header-menu.js'/>"></script>
<script src="<c:url value='/js/catalogue-explorer.js'/>"></script>

</body>
</html>
