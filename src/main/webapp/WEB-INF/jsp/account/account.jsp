<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<c:url var="accountUrl" value="/account"/>

<section class="account-page">
    <div class="account-card">

        <header class="account-card__header">
            <p class="eyebrow"><spring:message code="account.eyebrow"/></p>
            <h1><spring:message code="account.title"/></h1>
            <p><spring:message code="account.intro"/></p>
        </header>

        <form:form method="post"
                   action="${accountUrl}"
                   modelAttribute="profileForm"
                   class="account-form">

            <div class="form-group account-form__full">
                <label for="login"><spring:message code="auth.username"/></label>
                <form:input path="login"
                            id="login"
                            autocomplete="username"
                            minlength="3"
                            maxlength="50"
                            required="required"/>
                <form:errors path="login" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label for="firstName"><spring:message code="auth.firstName"/></label>
                <form:input path="firstName"
                            id="firstName"
                            autocomplete="given-name"
                            minlength="2"
                            maxlength="50"
                            required="required"/>
                <form:errors path="firstName" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label for="lastName"><spring:message code="auth.lastName"/></label>
                <form:input path="lastName"
                            id="lastName"
                            autocomplete="family-name"
                            minlength="2"
                            maxlength="50"
                            required="required"/>
                <form:errors path="lastName" cssClass="field-error"/>
            </div>

            <div class="form-group account-form__full">
                <label for="deliveryAddress">
                    <spring:message code="auth.deliveryAddress"/>
                </label>
                <form:input path="deliveryAddress"
                            id="deliveryAddress"
                            autocomplete="street-address"
                            minlength="5"
                            maxlength="150"
                            required="required"/>
                <form:errors path="deliveryAddress" cssClass="field-error"/>
            </div>

            <div class="form-group account-form__full">
                <label for="email"><spring:message code="auth.email"/></label>
                <form:input path="email"
                            id="email"
                            type="email"
                            autocomplete="email"
                            maxlength="100"
                            required="required"/>
                <form:errors path="email" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label for="phone"><spring:message code="auth.phone"/></label>
                <form:input path="phone"
                            id="phone"
                            type="tel"
                            inputmode="numeric"
                            pattern="[0-9]{8,20}"
                            autocomplete="tel"
                            required="required"/>
                <form:errors path="phone" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <label for="secondaryPhone">
                    <spring:message code="auth.secondaryPhone"/>
                    <span class="form-label-optional">
                        <spring:message code="common.optional"/>
                    </span>
                </label>
                <form:input path="secondaryPhone"
                            id="secondaryPhone"
                            type="tel"
                            inputmode="numeric"
                            pattern="[0-9]{8,20}"/>
                <form:errors path="secondaryPhone" cssClass="field-error"/>
            </div>

            <sec:csrfInput/>

            <div class="account-form__actions">
                <a href="<c:url value='/'/>"
                   class="btn account-form__cancel">
                    <spring:message code="common.cancel"/>
                </a>

                <button type="submit" class="btn btn--primary">
                    <spring:message code="account.save"/>
                </button>
            </div>
        </form:form>

    </div>
</section>
