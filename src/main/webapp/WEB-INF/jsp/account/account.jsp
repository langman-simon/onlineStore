<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="auth-page">
    <div class="auth-card">

        <p class="eyebrow">Profil</p>
        <h1>Mon compte</h1>

        <c:if test="${not empty success}">
            <p class="success">${success}</p>
        </c:if>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form method="post"
              action="<c:url value='/account'/>"
              class="auth-form">

            <div class="form-group">
            <label for="login">Pseudo</label>
            <input type="text" id="login" name="login" value="${user.login}" required>
            </div>

            <div class="form-group">
                <label for="firstName">Prénom</label>
                <input id="firstName" type="text" name="firstName"
                       value="${user.firstName}" required>
            </div>

            <div class="form-group">
                <label for="lastName">Nom</label>
                <input id="lastName" type="text" name="lastName"
                       value="${user.lastName}" required>
            </div>

            <div class="form-group">
                <label for="deliveryAddress">Adresse de livraison</label>
                <input id="deliveryAddress" type="text" name="deliveryAddress"
                       value="${user.deliveryAddress}" required>
            </div>

            <div class="form-group">
                <label for="email">Adresse e-mail</label>
                <input id="email" type="email" name="email"
                       value="${user.email}" required>
            </div>

            <div class="form-group">
                <label for="phone">Téléphone</label>
                <input id="phone" type="tel" name="phone"
                       value="${user.phone}" required>
            </div>

            <div class="form-group">
                <label for="secondaryPhone">Téléphone secondaire (facultatif)</label>
                <input id="secondaryPhone" type="tel" name="secondaryPhone"
                       value="${user.secondaryPhone}">
            </div>

            <sec:csrfInput/>

            <button type="submit" class="btn btn--primary btn--full">Enregistrer les modifications</button>

        </form>

    </div>
</section>