<%-- JSTL Core (loops, conditionals) - Example: <c:if test="${not empty cart}">...</c:if> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- JSTL Formatting (dates, currency) - Example: <fmt:formatNumber value="${item.price}" type="currency"/> --%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- JSTL Functions (string & collection helpers) - Example: ${fn:length(cart.items)} --%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- Spring Core (i18n, resource loading) - Example: <spring:message code="label.checkout"/> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Spring Forms (model binding, validation) - Example: <form:form modelAttribute="user">...</form:form> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- Apache Tiles (layout assembly) - Example: <tiles:insertAttribute name="main-content" /> --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>