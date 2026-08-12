<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="checkout-page">

    <p class="eyebrow"><spring:message code="checkout.eyebrow"/></p>
    <h1><spring:message code="checkout.title"/></h1>

    <c:choose>
        <c:when test="${empty cart.items}">
            <p><spring:message code="cart.empty"/></p>

            <a class="btn" href="<c:url value='/catalogue'/>">
                <spring:message code="common.backCatalogue"/>
            </a>
        </c:when>

        <c:otherwise>
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
                <c:forEach var="item" items="${cart.items}">
                    <tr>
                        <td><c:out value="${item.weapon.name}"/></td>
                        <td>${item.weapon.price} €</td>
                        <td>${item.quantity}</td>
                        <td>${item.subtotal} €</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="order-total">
                <p>
                    <spring:message code="common.subtotal"/> :
                    <span>${originalPrice} €</span>
                </p>

                <p>
                    <spring:message code="cart.delivery"/>
                    <c:choose>
                        <c:when test="${freeDelivery}">
                            <span class="strikethrough">${standardDeliveryFee} €</span>
                            <span class="free-delivery">
                                <spring:message code="common.free"/>
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span>${deliveryFee} €</span>
                        </c:otherwise>
                    </c:choose>
                </p>

                <c:if test="${discountAmount > 0}">
                    <p class="discount">
                        <spring:message code="cart.loyaltyDiscount"/>
                        <span>- ${discountAmount} €</span>
                    </p>
                </c:if>

                <p>
                    <spring:message code="checkout.totalPay"/>
                    <strong>${finalPrice} €</strong>
                </p>
            </div>

            <div class="checkout-actions">
                <form method="post" action="<c:url value='/order/confirm'/>">
                    <sec:csrfInput/>
                    <button type="submit" class="btn btn--primary btn--large">
                        <spring:message code="checkout.confirm"/>
                    </button>
                </form>

                <a class="btn btn--primary btn--large"
                   href="<c:url value='/cart'/>">
                    <spring:message code="common.backCart"/>
                </a>

                <a class="btn btn--primary btn--large"
                   href="<c:url value='/catalogue'/>">
                    <spring:message code="common.backCatalogue"/>
                </a>
            </div>
        </c:otherwise>
    </c:choose>

</section>
