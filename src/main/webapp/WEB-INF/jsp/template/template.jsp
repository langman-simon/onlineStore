<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value='/css/admin.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/base.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/layout.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/header.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/footer.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/buttons.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/forms.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/tables.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/catalogue.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/product-details.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/cart.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/checkout.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/company.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/order.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/responsive.css'/> ">
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
</header>

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