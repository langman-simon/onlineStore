<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="account-page">
    <div class="account-card">

        <header class="account-card__header">
            <p class="eyebrow"><spring:message code="account.eyebrow"/></p>
            <h1><spring:message code="account.title"/></h1>
            <p><spring:message code="account.intro"/></p>
        </header>

        <form method="post"
              action="<c:url value='/account'/>"
              class="account-form">

            <div class="form-group account-form__full">
                <label for="login"><spring:message code="auth.username"/></label>
                <input type="text"
                       id="login"
                       name="login"
                       value="<c:out value='${user.login}'/>"
                       autocomplete="username"
                       required>
            </div>

            <div class="form-group">
                <label for="firstName"><spring:message code="auth.firstName"/></label>
                <input type="text"
                       id="firstName"
                       name="firstName"
                       value="<c:out value='${user.firstName}'/>"
                       autocomplete="given-name"
                       required>
            </div>

            <div class="form-group">
                <label for="lastName"><spring:message code="auth.lastName"/></label>
                <input type="text"
                       id="lastName"
                       name="lastName"
                       value="<c:out value='${user.lastName}'/>"
                       autocomplete="family-name"
                       required>
            </div>

            <div class="form-group account-form__full">
                <label for="deliveryAddress">
                    <spring:message code="auth.deliveryAddress"/>
                </label>
                <input type="text"
                       id="deliveryAddress"
                       name="deliveryAddress"
                       value="<c:out value='${user.deliveryAddress}'/>"
                       autocomplete="street-address"
                       required>
            </div>

            <div class="form-group account-form__full">
                <label for="email"><spring:message code="auth.email"/></label>
                <input type="email"
                       id="email"
                       name="email"
                       value="<c:out value='${user.email}'/>"
                       autocomplete="email"
                       required>
            </div>

            <div class="form-group">
                <label for="phone"><spring:message code="auth.phone"/></label>
                <input type="tel"
                       id="phone"
                       name="phone"
                       value="<c:out value='${user.phone}'/>"
                       autocomplete="tel"
                       required>
            </div>

            <div class="form-group">
                <label for="secondaryPhone">
                    <spring:message code="auth.secondaryPhone"/>
                    <span class="form-label-optional">
                        <spring:message code="common.optional"/>
                    </span>
                </label>
                <input type="tel"
                       id="secondaryPhone"
                       name="secondaryPhone"
                       value="<c:out value='${user.secondaryPhone}'/>">
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
        </form>

    </div>
</section>
