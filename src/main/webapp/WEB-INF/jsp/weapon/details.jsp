<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<section class="product-details">

    <div class="product-details__image">
        <c:choose>
            <c:when test="${not empty weapon.imageUrl}">
                <img src="${weapon.imageUrl}" alt="${weapon.name}">
            </c:when>

            <c:otherwise>
                <div class="image-placeholder">
                    Aucune image
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="product-details__content">

        <p class="eyebrow">${weapon.category.name}</p>
        <h1>${weapon.name}</h1>

        <p class="product-price">
            ${weapon.price} €
        </p>

        <p>
            ${weapon.description}
        </p>

        <dl class="product-characteristics">
            <dt>Catégorie</dt>
            <dd>${weapon.category.name}</dd>

            <dt>Fabricant</dt>
            <dd>${weapon.manufacturer}</dd>

            <dt>Référence</dt>
            <dd>${weapon.reference}</dd>

            <dt>Stock</dt>
            <dd>${weapon.stock}</dd>
        </dl>

        <c:choose>
            <c:when test="${weapon.stock > 0}">
                <form method="post"
                      action="<c:url value='/cart/add/${weapon.id}'/>"
                      class="add-to-cart-form">

                    <div>
                        <label for="quantity">Quantité</label>

                        <input id="quantity"
                               type="number"
                               name="quantity"
                               value="1"
                               min="1"
                               max="${weapon.stock}"
                               required>
                    </div>

                    <sec:csrfInput/>

                    <button type="submit" class="btn btn--primary btn--large">
                        Ajouter au panier
                    </button>

                    <a class="btn btn--primary btn--large" href="<c:url value='/catalogue'/>">
                        Retour au catalogue
                    </a>

                </form>
            </c:when>

            <c:otherwise>
                <p class="error">
                    Produit indisponible.
                </p>
            </c:otherwise>
        </c:choose>

    </div>

</section>