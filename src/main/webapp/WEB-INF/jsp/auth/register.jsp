<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<spring:message code="auth.register.firstNamePlaceholder" var="firstNamePlaceholder"/>
<spring:message code="auth.register.lastNamePlaceholder" var="lastNamePlaceholder"/>
<spring:message code="auth.register.usernamePlaceholder" var="usernamePlaceholder"/>
<spring:message code="auth.register.emailPlaceholder" var="emailPlaceholder"/>
<spring:message code="auth.register.passwordPlaceholder" var="passwordPlaceholder"/>
<spring:message code="auth.register.passwordConfirmationPlaceholder" var="passwordConfirmationPlaceholder"/>
<spring:message code="auth.register.addressPlaceholder" var="addressPlaceholder"/>
<spring:message code="auth.register.phonePlaceholder" var="phonePlaceholder"/>
<spring:message code="auth.register.secondaryPhonePlaceholder" var="secondaryPhonePlaceholder"/>
<c:url var="registerUrl" value="/register"/>

<section class="auth-page">
    <div class="auth-box auth-box--register">
        <div class="auth-card">

            <div class="auth-heading">
                <p class="eyebrow"><spring:message code="auth.register.eyebrow"/></p>
                <h1><spring:message code="auth.register.title"/></h1>
                <p class="auth-introduction">
                    <spring:message code="auth.register.intro"/>
                </p>
            </div>

            <form:form method="post"
                       action="${registerUrl}"
                       modelAttribute="registrationForm"
                       class="auth-form auth-form--register"
                       novalidate="novalidate">

                <div class="auth-form-grid">
                    <div class="form-group">
                        <label for="firstName"><spring:message code="auth.firstName"/></label>
                        <form:input path="firstName"
                                    id="firstName"
                                    placeholder="${firstNamePlaceholder}"
                                    autocomplete="given-name"
                                    minlength="2"
                                    maxlength="50"
                                    required="required"
                                    autofocus="autofocus"/>
                    </div>

                    <div class="form-group">
                        <label for="lastName"><spring:message code="auth.lastName"/></label>
                        <form:input path="lastName"
                                    id="lastName"
                                    placeholder="${lastNamePlaceholder}"
                                    autocomplete="family-name"
                                    minlength="2"
                                    maxlength="50"
                                    required="required"/>
                    </div>

                    <div class="form-group">
                        <label for="username"><spring:message code="auth.username"/></label>
                        <form:input path="username"
                                    id="username"
                                    placeholder="${usernamePlaceholder}"
                                    autocomplete="username"
                                    minlength="3"
                                    maxlength="50"
                                    required="required"/>
                    </div>

                    <div class="form-group">
                        <label for="email"><spring:message code="auth.email"/></label>
                        <form:input path="email"
                                    id="email"
                                    type="email"
                                    placeholder="${emailPlaceholder}"
                                    autocomplete="email"
                                    maxlength="100"
                                    required="required"/>
                    </div>

                    <div class="form-group">
                        <label for="password"><spring:message code="auth.password"/></label>
                        <form:password path="password"
                                       id="password"
                                       placeholder="${passwordPlaceholder}"
                                       autocomplete="new-password"
                                       minlength="8"
                                       maxlength="72"
                                       required="required"/>
                    </div>

                    <div class="form-group">
                        <label for="passwordConfirmation">
                            <spring:message code="auth.passwordConfirmation"/>
                        </label>
                        <form:password path="passwordConfirmation"
                                       id="passwordConfirmation"
                                       placeholder="${passwordConfirmationPlaceholder}"
                                       autocomplete="new-password"
                                       minlength="8"
                                       maxlength="72"
                                       required="required"/>
                    </div>

                    <div class="form-group auth-form-grid__full">
                        <label for="deliveryAddress">
                            <spring:message code="auth.deliveryAddress"/>
                        </label>
                        <form:input path="deliveryAddress"
                                    id="deliveryAddress"
                                    placeholder="${addressPlaceholder}"
                                    autocomplete="street-address"
                                    minlength="5"
                                    maxlength="150"
                                    required="required"/>
                    </div>

                    <div class="form-group">
                        <label for="phone"><spring:message code="auth.phone"/></label>
                        <form:input path="phone"
                                    id="phone"
                                    type="tel"
                                    inputmode="numeric"
                                    pattern="[0-9]{8,20}"
                                    placeholder="${phonePlaceholder}"
                                    autocomplete="tel"
                                    required="required"/>
                    </div>

                    <div class="form-group">
                        <label for="secondaryPhone">
                            <spring:message code="auth.secondaryPhone"/>
                            <span class="optional-field">
                                <spring:message code="common.optional"/>
                            </span>
                        </label>
                        <form:input path="secondaryPhone"
                                    id="secondaryPhone"
                                    type="tel"
                                    inputmode="numeric"
                                    pattern="[0-9]{8,20}"
                                    placeholder="${secondaryPhonePlaceholder}"
                                    autocomplete="tel"/>
                    </div>
                </div>

                <sec:csrfInput/>

                <button type="submit" class="btn btn--primary btn--large">
                    <spring:message code="auth.register.submit"/>
                </button>
            </form:form>

            <div class="auth-footer">
                <span><spring:message code="auth.register.already"/></span>
                <a href="<c:url value='/login'/>">
                    <spring:message code="common.login"/>
                </a>
            </div>

        </div>
    </div>
</section>
