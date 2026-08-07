<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="account-page">

    <div class="account-card">

        <header class="account-card__header">
            <p class="eyebrow">Profil</p>
            <h1>Mon compte</h1>
            <p>Modifiez vos informations personnelles et vos coordonnées.</p>
        </header>

        <form method="post"
              action="<c:url value='/account'/>"
              class="account-form">

            <div class="form-group account-form__full">
                <label for="login">Pseudo</label>

                <input type="text"
                       id="login"
                       name="login"
                       value="${user.login}"
                       autocomplete="username"
                       required>
            </div>

            <div class="form-group">
                <label for="firstName">Prénom</label>

                <input type="text"
                       id="firstName"
                       name="firstName"
                       value="${user.firstName}"
                       autocomplete="given-name"
                       required>
            </div>

            <div class="form-group">
                <label for="lastName">Nom</label>

                <input type="text"
                       id="lastName"
                       name="lastName"
                       value="${user.lastName}"
                       autocomplete="family-name"
                       required>
            </div>

            <div class="form-group account-form__full">
                <label for="deliveryAddress">
                    Adresse de livraison
                </label>

                <input type="text"
                       id="deliveryAddress"
                       name="deliveryAddress"
                       value="${user.deliveryAddress}"
                       autocomplete="street-address"
                       required>
            </div>

            <div class="form-group account-form__full">
                <label for="email">Adresse e-mail</label>

                <input type="email"
                       id="email"
                       name="email"
                       value="${user.email}"
                       autocomplete="email"
                       required>
            </div>

            <div class="form-group">
                <label for="phone">Téléphone</label>

                <input type="tel"
                       id="phone"
                       name="phone"
                       value="${user.phone}"
                       autocomplete="tel"
                       required>
            </div>

            <div class="form-group">
                <label for="secondaryPhone">
                    Téléphone secondaire
                    <span class="form-label-optional">Facultatif</span>
                </label>

                <input type="tel"
                       id="secondaryPhone"
                       name="secondaryPhone"
                       value="${user.secondaryPhone}">
            </div>

            <sec:csrfInput/>

            <div class="account-form__actions">
                <a href="<c:url value='/'/>"
                   class="btn account-form__cancel">
                    Annuler
                </a>

                <button type="submit"
                        class="btn btn--primary">
                    Enregistrer les modifications
                </button>
            </div>

        </form>

    </div>

</section>