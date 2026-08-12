<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<spring:message code="auth.login.usernamePlaceholder" var="usernamePlaceholder"/>
<spring:message code="auth.login.passwordPlaceholder" var="passwordPlaceholder"/>

<section class="auth-page">
    <div class="auth-box auth-box--login">
        <div class="auth-card">

            <div class="auth-heading">
                <p class="eyebrow"><spring:message code="auth.login.eyebrow"/></p>
                <h1><spring:message code="auth.login.title"/></h1>
                <p class="auth-introduction">
                    <spring:message code="auth.login.intro"/>
                </p>
            </div>

            <form method="post"
                  action="<c:url value='/login'/>"
                  class="auth-form"
                  novalidate>

                <c:if test="${not empty param.redirect}">
                    <input type="hidden"
                           name="redirect"
                           value="<c:out value='${param.redirect}'/>">
                </c:if>

                <div class="form-group">
                    <label for="username"><spring:message code="auth.username"/></label>
                    <input id="username"
                           type="text"
                           name="username"
                           placeholder="${usernamePlaceholder}"
                           autocomplete="username"
                           required
                           autofocus>
                </div>

                <div class="form-group">
                    <label for="password"><spring:message code="auth.password"/></label>
                    <input id="password"
                           type="password"
                           name="password"
                           placeholder="${passwordPlaceholder}"
                           autocomplete="current-password"
                           required>
                </div>

                <sec:csrfInput/>

                <button type="submit" class="btn btn--primary btn--full">
                    <spring:message code="common.login"/>
                </button>
            </form>

            <div class="auth-footer">
                <span><spring:message code="auth.login.noAccount"/></span>
                <a href="<c:url value='/register'/>">
                    <spring:message code="common.register"/>
                </a>
            </div>

        </div>
    </div>
</section>
