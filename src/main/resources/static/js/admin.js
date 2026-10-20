document.addEventListener("click", function (event) {
    const openButton = event.target.closest("[data-open-dialog]");
    if (openButton) {
        const dialog = document.getElementById(openButton.dataset.openDialog);
        if (dialog) {
            dialog.showModal();
        }
        return;
    }

    const closeButton = event.target.closest("[data-close-dialog]");
    if (closeButton) {
        const dialog = document.getElementById(closeButton.dataset.closeDialog);
        if (dialog) {
            dialog.close();
        }
        return;
    }

    if (event.target.classList.contains("admin-dialog")) {
        event.target.close();
    }
});

document.querySelectorAll(".admin-add-panel").forEach(function (panel) {
    panel.addEventListener("mouseleave", function () {
        panel.removeAttribute("open");
    });
});

document.querySelectorAll(".admin-product-edit").forEach(function (panel) {
    panel.addEventListener("toggle", function () {
        if (!panel.open) {
            return;
        }

        document.querySelectorAll(".admin-product-edit[open]").forEach(function (otherPanel) {
            if (otherPanel !== panel) {
                otherPanel.removeAttribute("open");
            }
        });
    });

    panel.addEventListener("mouseleave", function () {
        panel.removeAttribute("open");
    });
});

function setupPromotionTargetExclusivity(categorySelect, productSelect) {
    if (!categorySelect || !productSelect) {
        return;
    }

    function syncFields() {
        productSelect.disabled = categorySelect.value !== "";
        categorySelect.disabled = productSelect.value !== "";
    }

    categorySelect.addEventListener("change", syncFields);
    productSelect.addEventListener("change", syncFields);

    syncFields();
}

setupPromotionTargetExclusivity(
    document.getElementById("promoTargetCategory"),
    document.getElementById("promoTargetProduct")
);

document.querySelectorAll("[id^='promo-target-category-']").forEach(function (categorySelect) {
    const promoId = categorySelect.id.replace("promo-target-category-", "");

    setupPromotionTargetExclusivity(
        categorySelect,
        document.getElementById("promo-target-product-" + promoId)
    );
});