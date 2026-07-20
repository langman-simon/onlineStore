<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value='/css/styles.css'/>">
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