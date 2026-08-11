<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="orders">

    <p class="eyebrow"><spring:message code="order.history"/></p>
    <h1><spring:message code="order.title"/></h1>

    <c:choose>
        <c:when test="${empty orders}">
            <p><spring:message code="order.empty"/></p>

            <a class="btn btn--primary btn--small"
               href="<c:url value='/catalogue'/>">
                <spring:message code="order.viewCatalogue"/>
            </a>
        </c:when>

        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th><spring:message code="common.number"/></th>
                    <th><spring:message code="common.date"/></th>
                    <th><spring:message code="order.initialPrice"/></th>
                    <th><spring:message code="common.discount"/></th>
                    <th><spring:message code="common.total"/></th>
                    <th><spring:message code="common.status"/></th>
                    <th><spring:message code="common.action"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>

                        <td>
                            <fmt:formatNumber value="${order.createdAt.dayOfMonth}" pattern="00"/>/<fmt:formatNumber value="${order.createdAt.monthValue}" pattern="00"/>/${order.createdAt.year}
                            <spring:message code="common.at"/>
                            <fmt:formatNumber value="${order.createdAt.hour}" pattern="00"/>:<fmt:formatNumber value="${order.createdAt.minute}" pattern="00"/>
                        </td>

                        <td>${order.originalPrice} €</td>

                        <td>
                            <c:choose>
                                <c:when test="${order.discountAmount > 0}">
                                    - ${order.discountAmount} €
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="common.none"/>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td><strong>${order.totalPrice} €</strong></td>

                        <td>
                            <span class="${order.status == 'PAID' ? 'success' : 'error'}">
                                <c:choose>
                                    <c:when test="${order.status == 'PAID'}">
                                        <spring:message code="order.status.paid"/>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="order.status.pending_payment"/>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </td>

                        <td>
                            <a class="btn btn--primary btn--full"
                               href="<c:url value='/order/${order.id}'/>">
                                <spring:message code="common.details"/>
                            </a>

                            <c:if test="${order.status != 'PAID'}">
                                <form method="post"
                                      action="<c:url value='/order/${order.id}/cancel'/>">
                                    <sec:csrfInput/>

                                    <button type="submit"
                                            class="btn btn--delete btn--full">
                                        <spring:message code="order.cancel"/>
                                    </button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a class="btn btn--primary btn--full"
               href="<c:url value='/catalogue'/>">
                <spring:message code="common.backCatalogue"/>
            </a>
        </c:otherwise>
    </c:choose>

</section>
