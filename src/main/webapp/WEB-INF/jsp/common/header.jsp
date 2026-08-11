<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/importTags.jsp" %>

<spring:message code="header.brandAria"
                var="brandAria"
                htmlEscape="true"/>

<spring:message code="header.navigationAria"
                var="navigationAria"
                htmlEscape="true"/>

<%-- Retrieve the original public URI before the JSP forward --%>
<c:set var="currentUri"
       value="${requestScope['jakarta.servlet.forward.request_uri']}"/>

<%-- Fallback when the request was not forwarded --%>
<c:if test="${empty currentUri}">
    <c:set var="currentUri"
           value="${pageContext.request.requestURI}"/>
</c:if>

<%-- Remove the application context path before using c:url --%>
<c:choose>

    <c:when test="${not empty pageContext.request.contextPath}">
        <c:set var="currentPath"
               value="${fn:substring(
                   currentUri,
                   fn:length(pageContext.request.contextPath),
                   fn:length(currentUri)
               )}"/>
    </c:when>

    <c:otherwise>
        <c:set var="currentPath"
               value="${currentUri}"/>
    </c:otherwise>

</c:choose>


<%-- French URL: preserve every current parameter except lang --%>
<c:url var="frUrl"
       value="${currentPath}">

    <c:forEach var="parameter"
               items="${paramValues}">

        <c:if test="${parameter.key ne 'lang'}">

            <c:forEach var="parameterValue"
                       items="${parameter.value}">

                <c:param name="${parameter.key}"
                         value="${parameterValue}"/>

            </c:forEach>

        </c:if>

    </c:forEach>

    <c:param name="lang"
             value="fr"/>

</c:url>


<%-- English URL: preserve every current parameter except lang --%>
<c:url var="enUrl"
       value="${currentPath}">

    <c:forEach var="parameter"
               items="${paramValues}">

        <c:if test="${parameter.key ne 'lang'}">

            <c:forEach var="parameterValue"
                       items="${parameter.value}">

                <c:param name="${parameter.key}"
                         value="${parameterValue}"/>

            </c:forEach>

        </c:if>

    </c:forEach>

    <c:param name="lang"
             value="en"/>

</c:url>


<header class="navbar">

    <div class="container navbar__container">


        <%-- =====================================================
             LANGUAGE
             ===================================================== --%>

        <div class="header-language">

            <div class="hover-menu">

                <span class="language-button"
                      aria-hidden="true">

                    <span aria-hidden="true">
                        🌐
                    </span>

                    <span class="language-button__label">
                        <spring:message code="common.language"/>
                    </span>

                </span>


                <div class="hover-menu__content language-dropdown">

                    <a href="${frUrl}"
                       lang="fr"
                       hreflang="fr">

                        <img src="<c:url value='/assets/flags/fr.png'/>"
                             alt="">

                        <spring:message code="common.french"/>

                    </a>


                    <a href="${enUrl}"
                       lang="en"
                       hreflang="en">

                        <img src="<c:url value='/assets/flags/en.png'/>"
                             alt="">

                        <spring:message code="common.english"/>

                    </a>

                </div>

            </div>

        </div>


        <%-- =====================================================
             MAIN NAVIGATION
             ===================================================== --%>

        <div class="header-navigation"
             id="headerNavigation">

            <a href="<c:url value='/'/>"
               class="header-brand"
               aria-label="${brandAria}">
                Hyperion
            </a>


            <nav class="header-links"
                 aria-label="${navigationAria}">

                <a href="<c:url value='/'/>">
                    <spring:message code="common.home"/>
                </a>


                <a href="<c:url value='/catalogue'/>">
                    <spring:message code="common.catalogue"/>
                </a>


                <a href="<c:url value='/company'/>">
                    <spring:message code="common.company"/>
                </a>


                <a href="<c:url value='/cart'/>"
                   class="header-cart-link">

                    <spring:message code="header.cart"/>

                    <span class="header-cart-count">
                        ${sessionCart.cart.totalQuantity}
                    </span>

                </a>


                <sec:authorize access="isAuthenticated()">

                    <a href="<c:url value='/order'/>">
                        <spring:message code="header.orders"/>
                    </a>

                </sec:authorize>


                <sec:authorize access="hasRole('ADMIN')">

                    <a href="<c:url value='/admin'/>">
                        <spring:message code="common.admin"/>
                    </a>

                </sec:authorize>

            </nav>

        </div>


        <%-- =====================================================
             USER MENU
             ===================================================== --%>

        <div class="header-user">


            <%-- Anonymous user --%>

            <sec:authorize access="isAnonymous()">

                <div class="hover-menu">

                    <span class="user-avatar user-avatar--anonymous"
                          aria-hidden="true">
                        ?
                    </span>


                    <div class="hover-menu__content user-dropdown">

                        <a href="<c:url value='/login'/>">
                            <spring:message code="header.login"/>
                        </a>


                        <a href="<c:url value='/register'/>">
                            <spring:message code="header.register"/>
                        </a>

                    </div>

                </div>

            </sec:authorize>


            <%-- Authenticated user --%>

            <sec:authorize access="isAuthenticated()">

                <sec:authentication property="name"
                                    var="currentLogin"/>

                <div class="hover-menu">

                    <span class="user-avatar"
                          aria-hidden="true">

                        <c:out value="${fn:toUpperCase(
                            fn:substring(currentLogin, 0, 1)
                        )}"/>

                    </span>


                    <div class="hover-menu__content user-dropdown">

                        <p class="user-dropdown__login">
                            <c:out value="${currentLogin}"/>
                        </p>


                        <a href="<c:url value='/order'/>">
                            <spring:message code="header.orders"/>
                        </a>


                        <a href="<c:url value='/account'/>">
                            <spring:message code="header.account"/>
                        </a>


                        <sec:authorize access="hasRole('ADMIN')">

                            <a href="<c:url value='/admin'/>">
                                <spring:message code="common.admin"/>
                            </a>

                        </sec:authorize>


                        <form method="post"
                              action="<c:url value='/logout'/>">

                            <sec:csrfInput/>

                            <button type="submit">
                                <spring:message code="header.logout"/>
                            </button>

                        </form>

                    </div>

                </div>

            </sec:authorize>

        </div>

    </div>

</header>