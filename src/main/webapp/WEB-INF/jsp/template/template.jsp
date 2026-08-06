<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value='/css/admin.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/layout.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/components/buttons.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/header-bubbles.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/components/forms.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/tables.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/catalogue.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/product-details.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/cart.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/checkout.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/company.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/order.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/auth.css'/>">
</head>

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

<body>

<header>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
</header>

<c:if test="${not empty sessionScope.globalBannerMessage}">

    <div id="global-banner"
         class="global-banner global-banner--${sessionScope.globalBannerType}"
         role="status"
         aria-live="polite"
         tabindex="0"
         aria-label="Message de confirmation. Cliquer pour fermer.">

        <span class="global-banner__icon"
              aria-hidden="true">

            <c:choose>
                <c:when test="${sessionScope.globalBannerType == 'success'}">
                    ✓
                </c:when>

                <c:when test="${sessionScope.globalBannerType == 'error'}">
                    !
                </c:when>

                <c:when test="${sessionScope.globalBannerType == 'warning'}">
                    !
                </c:when>

                <c:otherwise>
                    i
                </c:otherwise>
            </c:choose>

        </span>

        <span class="global-banner__message">
            <c:out value="${sessionScope.globalBannerMessage}"/>
        </span>

        <span class="global-banner__hint"
              aria-hidden="true">
            Cliquer pour fermer
        </span>

        <div class="global-banner__timer"
             aria-hidden="true"></div>

    </div>

    <c:remove var="globalBannerMessage" scope="session"/>
    <c:remove var="globalBannerType" scope="session"/>

</c:if>

<main>
    <div class="container">
        <c:if test="${not empty success}">
            <p class="success">
                ${success}
            </p>
            </c:if>

            <c:if test="${not empty error}">
            <p class="error">
                ${error}
            </p>
        </c:if>
        <jsp:include page="${body}" />
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>