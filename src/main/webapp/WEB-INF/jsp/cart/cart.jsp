<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="cart-page">

    <p class="eyebrow"><spring:message code="cart.eyebrow"/></p>
    <h1><spring:message code="cart.title"/></h1>

    <c:if test="${empty cart.items}">
        <p class="cart-empty">
            <spring:message code="cart.empty"/>
        </p>
    </c:if>

    <c:if test="${not empty cart.items}">
        <table>
            <thead>
            <tr>
                <th><spring:message code="common.product"/></th>
                <th><spring:message code="common.unitPrice"/></th>
                <th><spring:message code="common.quantity"/></th>
                <th><spring:message code="common.subtotal"/></th>
                <th><spring:message code="cart.modify"/></th>
                <th><spring:message code="common.delete"/></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <a href="<c:url value='/weapons/${item.weapon.id}'/>">
                            <c:out value="${item.weapon.name}"/>
                        </a>
                    </td>

                    <td>${item.weapon.price} €</td>
                    <td>${item.quantity}</td>
                    <td>${item.subtotal} €</td>

                    <td>
                        <form method="post"
                              class="cart-quantity-form"
                              action="<c:url value='/cart/update/${item.weapon.id}'/>">

                            <input type="number"
                                   name="quantity"
                                   class="quantity-input"
                                   value="${item.quantity}"
                                   min="1"
                                   max="${item.weapon.stock}"
                                   required>

                            <sec:csrfInput/>

                            <button type="submit"
                                    class="btn btn--primary btn--full">
                                <spring:message code="common.update"/>
                            </button>
                        </form>
                    </td>

                    <td>
                        <form method="post"
                              action="<c:url value='/cart/remove/${item.weapon.id}'/>">
                            <sec:csrfInput/>

                            <button type="submit"
                                    class="btn btn--primary btn--full btn--delete">
                                <spring:message code="common.delete"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="cart-summary">
            <p>
                <spring:message code="cart.totalItems"/>
                <strong>${cart.totalQuantity}</strong>
            </p>

            <p>
                <spring:message code="common.subtotal"/> :
                <strong>${originalPrice} €</strong>
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
                <spring:message code="cart.totalAfterPromotions"/>
                <strong>${finalPrice} €</strong>
            </p>
        </div>

        <sec:authorize access="isAuthenticated()">
            <c:if test="${not empty remainingForFreeDelivery && remainingForFreeDelivery > 0}">
                <p class="promo-incentive">
                    <spring:message code="cart.freeDelivery.before"/>
                    <strong>${remainingForFreeDelivery} €</strong>
                    <spring:message code="cart.freeDelivery.after"/>
                </p>
            </c:if>

            <c:if test="${not empty remainingForNextTier}">
                <p class="promo-incentive">
                    <spring:message code="cart.nextTier.before"/>
                    <strong>${remainingForNextTier} €</strong>
                    <spring:message code="cart.nextTier.middle"/>
                    <strong>-${nextTierRate}%</strong>
                    <spring:message code="cart.nextTier.after"/>
                </p>
            </c:if>
        </sec:authorize>

        <sec:authorize access="isAnonymous()">
            <c:url var="loginFromCartUrl" value="/login">
                <c:param name="redirect" value="/cart"/>
            </c:url>

            <div class="cart-login-prompt">
                <span><spring:message code="cart.loginPrompt"/></span>
                <a href="${loginFromCartUrl}" class="link--accent">
                    <spring:message code="common.login"/>
                </a>
            </div>
        </sec:authorize>

        <hr>

        <div class="cart-actions">
            <sec:authorize access="isAuthenticated()">
                <form method="get"
                      action="<c:url value='/order/checkout'/>"
                      class="cart-checkout-form">
                    <button type="submit" class="btn btn--primary">
                        <spring:message code="cart.checkout"/>
                    </button>
                </form>
            </sec:authorize>

            <sec:authorize access="isAnonymous()">
                <c:url var="checkoutLoginUrl" value="/login">
                    <c:param name="redirect" value="/order/checkout"/>
                </c:url>

                <a href="${checkoutLoginUrl}" class="btn btn--primary">
                    <spring:message code="cart.checkout"/>
                </a>
            </sec:authorize>

            <a href="<c:url value='/catalogue'/>" class="btn btn--primary">
                <spring:message code="common.backCatalogue"/>
            </a>

            <form method="post"
                  action="<c:url value='/cart/clear'/>"
                  class="cart-clear-form">
                <sec:csrfInput/>

                <button type="submit" class="btn btn--delete">
                    <spring:message code="cart.clear"/>
                </button>
            </form>
        </div>
    </c:if>

</section>
