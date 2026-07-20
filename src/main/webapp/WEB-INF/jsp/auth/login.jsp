<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="auth-page">
    <div class="auth-card">

        <h1>Connexion</h1>

        <form method="post"
              action="<c:url value='/login'/>"
              class="auth-form">

            <div class="form-group">
                <label for="username">Pseudo</label>
                <input id="username"
                       type="text"
                       name="username"
                       required>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input id="password"
                       type="password"
                       name="password"
                       required>
            </div>

            <button type="submit">Se connecter</button>

        </form>

        <p>
            Pas encore de compte ?
            <a href="<c:url value='/register'/>">Créer un compte</a>
        </p>

    </div>
</section>