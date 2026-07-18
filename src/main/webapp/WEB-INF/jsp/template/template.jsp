<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include/importTags.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>

<header>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
</header>

<main>
    <jsp:include page="${body}"/>
</main>

<footer>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</footer>

</body>
</html>