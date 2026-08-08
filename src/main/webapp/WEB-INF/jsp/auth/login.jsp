<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="auth-page">

    <div class="auth-box auth-box--login">

        <div class="auth-card">

            <div class="auth-heading">

                <p class="eyebrow">
                    Accès membre
                </p>

                <h1>
                    Connexion
                </h1>

                <p class="auth-introduction">
                    Accédez à votre compte Hyperion.
                </p>

            </div>

            <form method="post"
                  action="<c:url value='/login'/>"
                  class="auth-form">

                  <c:if test="${not empty param.redirect}">
                        <input type="hidden"
                               name="redirect"
                               value="<c:out value='${param.redirect}'/>">
                  </c:if>

                <div class="form-group">

                    <label for="username">
                        Pseudo
                    </label>

                    <input id="username"
                           type="text"
                           name="username"
                           placeholder="Votre pseudo"
                           autocomplete="username"
                           required
                           autofocus>

                </div>

                <div class="form-group">

                    <label for="password">
                        Mot de passe
                    </label>

                    <input id="password"
                           type="password"
                           name="password"
                           placeholder="Votre mot de passe"
                           autocomplete="current-password"
                           required>

                </div>

                <sec:csrfInput/>

                <button type="submit"
                        class="btn btn--primary btn--full">
                    Se connecter
                </button>

            </form>

            <div class="auth-footer">

                <span>
                    Pas encore de compte ?
                </span>

                <a href="<c:url value='/register'/>">
                    Créer un compte
                </a>

            </div>

        </div>

    </div>

</section>