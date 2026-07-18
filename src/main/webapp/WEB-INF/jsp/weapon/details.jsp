<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/importTags.jsp" %>

<h1>${weapon.name}</h1>

<p>${weapon.description}</p>

<p>
  Prix :
  <strong>${weapon.price} €</strong>
</p>

<p>Catégorie : ${weapon.category}</p>

<p>Fabricant : ${weapon.manufacturer}</p>

<p>Référence : ${weapon.reference}</p>

<p>Stock : ${weapon.stock}</p>

<form method="post"
      action="<c:url value='/cart/add/${weapon.id}'/>">

  <label>Quantité</label>

  <input
          type="number"
          name="quantity"
          value="1"
          min="1"
          max="${weapon.stock}">

  <button type="submit">
    Ajouter au panier
  </button>

</form>