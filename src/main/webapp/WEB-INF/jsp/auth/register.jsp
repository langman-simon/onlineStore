<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="auth-page">

    <div class="auth-box auth-box--register">

        <div class="auth-card">

            <div class="auth-heading">

                <p class="eyebrow">
                    Nouveau client
                </p>

                <h1>
                    Inscription
                </h1>

                <p class="auth-introduction">
                    Créez votre compte Hyperion.
                </p>

            </div>

            <form method="post"
                  action="<c:url value='/register'/>"
                  class="auth-form auth-form--register">

                <div class="auth-form-grid">

                    <div class="form-group">

                        <label for="firstName">
                            Prénom
                        </label>

                        <input id="firstName"
                               type="text"
                               name="firstName"
                               placeholder="Votre prénom"
                               autocomplete="given-name"
                               required
                               autofocus>

                    </div>

                    <div class="form-group">

                        <label for="lastName">
                            Nom
                        </label>

                        <input id="lastName"
                               type="text"
                               name="lastName"
                               placeholder="Votre nom"
                               autocomplete="family-name"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="username">
                            Pseudo
                        </label>

                        <input id="username"
                               type="text"
                               name="username"
                               placeholder="Choisissez un pseudo"
                               autocomplete="username"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="email">
                            Adresse e-mail
                        </label>

                        <input id="email"
                               type="email"
                               name="email"
                               placeholder="exemple@hyperion.com"
                               autocomplete="email"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="password">
                            Mot de passe
                        </label>

                        <input id="password"
                               type="password"
                               name="password"
                               placeholder="Choisissez un mot de passe"
                               autocomplete="new-password"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="passwordConfirmation">
                            Confirmation du mot de passe
                        </label>

                        <input id="passwordConfirmation"
                               type="password"
                               name="passwordConfirmation"
                               placeholder="Confirmez le mot de passe"
                               autocomplete="new-password"
                               required>

                    </div>

                    <div class="form-group auth-form-grid__full">

                        <label for="deliveryAddress">
                            Adresse de livraison
                        </label>

                        <input id="deliveryAddress"
                               type="text"
                               name="deliveryAddress"
                               placeholder="Rue, numéro, ville et code postal"
                               autocomplete="street-address"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="phone">
                            Téléphone
                        </label>

                        <input id="phone"
                               type="tel"
                               name="phone"
                               placeholder="Numéro principal"
                               autocomplete="tel"
                               required>

                    </div>

                    <div class="form-group">

                        <label for="secondaryPhone">
                            Téléphone secondaire
                            <span class="optional-field">
                                Facultatif
                            </span>
                        </label>

                        <input id="secondaryPhone"
                               type="tel"
                               name="secondaryPhone"
                               placeholder="Numéro secondaire"
                               autocomplete="tel">

                    </div>

                </div>

                <sec:csrfInput/>

                <button type="submit"
                        class="btn btn--primary btn--large">
                    Créer le compte
                </button>

            </form>

            <div class="auth-footer">

                <span>
                    Déjà inscrit ?
                </span>

                <a href="<c:url value='/login'/>">
                    Se connecter
                </a>

            </div>

        </div>

    </div>

</section>