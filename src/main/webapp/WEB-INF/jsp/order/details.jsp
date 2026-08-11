<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<spring:message code="order.paypalItem"
                arguments="${order.id}"
                var="paypalItemName"/>

<section class="order-details">

    <div class="order-header">
        <p class="eyebrow"><spring:message code="order.detailEyebrow"/></p>
        <h1>
            <spring:message code="order.number" arguments="${order.id}"/>
        </h1>

        <p>
            <spring:message code="common.date"/> :
            <strong>
                <fmt:formatNumber value="${order.createdAt.dayOfMonth}" pattern="00"/>/<fmt:formatNumber value="${order.createdAt.monthValue}" pattern="00"/>/${order.createdAt.year}
                <spring:message code="common.at"/>
                <fmt:formatNumber value="${order.createdAt.hour}" pattern="00"/>:<fmt:formatNumber value="${order.createdAt.minute}" pattern="00"/>
            </strong>
        </p>
    </div>

    <table>
        <thead>
        <tr>
            <th><spring:message code="common.product"/></th>
            <th><spring:message code="common.unitPrice"/></th>
            <th><spring:message code="common.quantity"/></th>
            <th><spring:message code="common.subtotal"/></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="item" items="${order.items}">
            <tr>
                <td><c:out value="${item.weapon.name}"/></td>
                <td>${item.unitPrice} €</td>
                <td>${item.quantity}</td>
                <td>${item.subtotal} €</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="order-total">
        <p>
            <spring:message code="order.initialPrice"/> :
            <strong>${order.originalPrice} €</strong>
        </p>

        <c:if test="${order.discountAmount > 0}">
            <p>
                <spring:message code="common.discount"/> :
                <strong>- ${order.discountAmount} €</strong>
            </p>
        </c:if>

        <p>
            <spring:message code="common.total"/> :
            <strong>${order.totalPrice} €</strong>
        </p>
        <p class="${order.status == 'PAID' ? 'success' : 'error'}">
            <c:choose>
                <c:when test="${order.status == 'PAID'}">
                    <spring:message code="order.status.paid"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="order.status.pending_payment"/>
                </c:otherwise>
            </c:choose>
        </p>
    </div>

    <div class="order-actions">
        <a class="btn btn--primary btn--large"
           href="<c:url value='/order'/>">
            <spring:message code="common.orders"/>
        </a>

        <a class="btn btn--primary btn--large"
           href="<c:url value='/catalogue'/>">
            <spring:message code="common.backCatalogue"/>
        </a>

        <c:if test="${order.status == 'PENDING_PAYMENT'}">
            <form method="post" action="${paypalSandboxUrl}">
                <input type="hidden" name="business" value="${paypalSellerEmail}">
                <input type="hidden" name="cmd" value="_xclick">
                <input type="hidden" name="amount" value="${order.totalPrice}">
                <input type="hidden" name="item_name" value="${paypalItemName}">
                <input type="hidden" name="currency_code" value="EUR">
                <input type="hidden"
                       name="lc"
                       value="${pageContext.response.locale.language == 'en' ? 'US' : 'FR'}">
                <input type="hidden"
                       name="return"
                       value="${baseUrl}/order/${order.id}/payment/success">
                <input type="hidden"
                       name="cancel_return"
                       value="${baseUrl}/order/${order.id}/payment/cancel">

                <button type="submit" class="btn btn--primary btn--large">
                    <spring:message code="order.payPaypal"/>
                </button>
            </form>
        </c:if>
    </div>

</section>
