<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="auth-page">
    <div class="auth-card">

        <h1>Inscription</h1>

        <form method="post"
              action="<c:url value='/register'/>"
              class="auth-form">

            <div class="form-group">
                <label for="firstName">Prénom</label>
                <input id="firstName" type="text" name="firstName" required autofocus>
            </div>

            <div class="form-group">
                <label for="lastName">Nom</label>
                <input id="lastName" type="text" name="lastName" required>
            </div>

            <div class="form-group">
                <label for="username">Pseudo</label>
                <input id="username"
                       type="text"
                       name="username"
                       required>
            </div>


            <div class="form-group">
                <label for="email">Adresse e-mail</label>
                <input id="email"
                       type="email"
                       name="email"
                       required>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input id="password"
                       type="password"
                       name="password"
                       required>
            </div>

            <div class="form-group">
                <label for="passwordConfirmation">
                    Confirmation du mot de passe
                </label>

                <input id="passwordConfirmation"
                       type="password"
                       name="passwordConfirmation"
                       required>
            </div>

            <div class="form-group">
                <label for="deliveryAddress">Adresse de livraison</label>
                <input id="deliveryAddress" type="text" name="deliveryAddress" required>
            </div>

            <div class="form-group">
                <label for="phone">Téléphone</label>
                <input id="phone" type="tel" name="phone" required>
            </div>

            <div class="form-group">
                <label for="secondaryPhone">Téléphone secondaire (facultatif)</label>
                <input id="secondaryPhone" type="tel" name="secondaryPhone">
            </div>

            <sec:csrfInput/>

            <button type="submit">Créer le compte</button>

        </form>

        <p>
            Déjà inscrit ?
            <a href="<c:url value='/login'/>">Se connecter</a>
        </p>

    </div>
</section>