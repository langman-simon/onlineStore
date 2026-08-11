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

            <form method="post"
                  action="<c:url value='/register'/>"
                  class="auth-form auth-form--register">

                <div class="auth-form-grid">
                    <div class="form-group">
                        <label for="firstName"><spring:message code="auth.firstName"/></label>
                        <input id="firstName"
                               type="text"
                               name="firstName"
                               value="<c:out value='${registrationForm.firstName}'/>"
                               placeholder="${firstNamePlaceholder}"
                               autocomplete="given-name"
                               required
                               autofocus>
                    </div>

                    <div class="form-group">
                        <label for="lastName"><spring:message code="auth.lastName"/></label>
                        <input id="lastName"
                               type="text"
                               name="lastName"
                               value="<c:out value='${registrationForm.lastName}'/>"
                               placeholder="${lastNamePlaceholder}"
                               autocomplete="family-name"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="username"><spring:message code="auth.username"/></label>
                        <input id="username"
                               type="text"
                               name="username"
                               value="<c:out value='${registrationForm.username}'/>"
                               placeholder="${usernamePlaceholder}"
                               autocomplete="username"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="email"><spring:message code="auth.email"/></label>
                        <input id="email"
                               type="email"
                               name="email"
                               value="<c:out value='${registrationForm.email}'/>"
                               placeholder="${emailPlaceholder}"
                               autocomplete="email"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="password"><spring:message code="auth.password"/></label>
                        <input id="password"
                               type="password"
                               name="password"
                               placeholder="${passwordPlaceholder}"
                               autocomplete="new-password"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="passwordConfirmation">
                            <spring:message code="auth.passwordConfirmation"/>
                        </label>
                        <input id="passwordConfirmation"
                               type="password"
                               name="passwordConfirmation"
                               placeholder="${passwordConfirmationPlaceholder}"
                               autocomplete="new-password"
                               required>
                    </div>

                    <div class="form-group auth-form-grid__full">
                        <label for="deliveryAddress">
                            <spring:message code="auth.deliveryAddress"/>
                        </label>
                        <input id="deliveryAddress"
                               type="text"
                               name="deliveryAddress"
                               value="<c:out value='${registrationForm.deliveryAddress}'/>"
                               placeholder="${addressPlaceholder}"
                               autocomplete="street-address"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="phone"><spring:message code="auth.phone"/></label>
                        <input id="phone"
                               type="tel"
                               name="phone"
                               value="<c:out value='${registrationForm.phone}'/>"
                               placeholder="${phonePlaceholder}"
                               autocomplete="tel"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="secondaryPhone">
                            <spring:message code="auth.secondaryPhone"/>
                            <span class="optional-field">
                                <spring:message code="common.optional"/>
                            </span>
                        </label>
                        <input id="secondaryPhone"
                               type="tel"
                               name="secondaryPhone"
                               value="<c:out value='${registrationForm.secondaryPhone}'/>"
                               placeholder="${secondaryPhonePlaceholder}"
                               autocomplete="tel">
                    </div>
                </div>

                <sec:csrfInput/>

                <button type="submit" class="btn btn--primary btn--large">
                    <spring:message code="auth.register.submit"/>
                </button>
            </form>

            <div class="auth-footer">
                <span><spring:message code="auth.register.already"/></span>
                <a href="<c:url value='/login'/>">
                    <spring:message code="common.login"/>
                </a>
            </div>

        </div>
    </div>
</section>
