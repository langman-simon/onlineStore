document.addEventListener("DOMContentLoaded", function () {
    const explorer = document.getElementById("catalogueExplorer");

    if (!explorer) {
        return;
    }

    const selectors = Array.from(
        explorer.querySelectorAll("[data-catalogue-index]")
    );

    const products = Array.from(
        explorer.querySelectorAll("[data-catalogue-product]")
    );

    const previousButton =
        document.getElementById("cataloguePrevious");

    const nextButton =
        document.getElementById("catalogueNext");

    const currentIndexElement =
        document.getElementById("catalogueCurrentIndex");

    if (selectors.length === 0 || products.length === 0) {
        return;
    }

    let activeIndex = 0;

    function formatIndex(index) {
        return String(index + 1).padStart(2, "0");
    }

    function getProductPosition(index) {
        const difference = index - activeIndex;
        const distance = Math.abs(difference);

        if (difference === 0) {
            return {
                x: "0px",
                y: "0px",
                scale: "1",
                opacity: "1",
                blur: "0px",
                zIndex: products.length + 10
            };
        }

        /*
         * Les autres produits restent visibles derrière
         * le produit actif, comme les planètes du modèle.
         */
        const direction = difference < 0 ? -1 : 1;

        return {
            x: `${210 + distance * 58}px`,
            y: `${direction * (125 + distance * 58)}px`,
            scale: String(Math.max(0.22, 0.54 - distance * 0.065)),
            opacity: String(Math.max(0.08, 0.42 - distance * 0.07)),
            blur: `${Math.min(6, 1.5 + distance * 0.8)}px`,
            zIndex: products.length - distance
        };
    }

    function updateCatalogue(newIndex) {
        activeIndex =
            (newIndex + products.length) % products.length;

        selectors.forEach(function (selector, index) {
            const isActive = index === activeIndex;

            selector.classList.toggle("is-active", isActive);
            selector.setAttribute(
                "aria-selected",
                String(isActive)
            );

            if (isActive) {
                selector.scrollIntoView({
                    block: "nearest",
                    inline: "nearest",
                    behavior: "smooth"
                });
            }
        });

        products.forEach(function (product, index) {
            const isActive = index === activeIndex;
            const position = getProductPosition(index);

            product.classList.toggle("is-active", isActive);

            product.setAttribute(
                "aria-hidden",
                String(!isActive)
            );

            product.style.setProperty(
                "--product-x",
                position.x
            );

            product.style.setProperty(
                "--product-y",
                position.y
            );

            product.style.setProperty(
                "--product-scale",
                position.scale
            );

            product.style.setProperty(
                "--product-opacity",
                position.opacity
            );

            product.style.setProperty(
                "--product-blur",
                position.blur
            );

            product.style.setProperty(
                "--product-z",
                String(position.zIndex)
            );
        });

        if (currentIndexElement) {
            currentIndexElement.textContent =
                formatIndex(activeIndex);
        }
    }

    selectors.forEach(function (selector) {
        selector.addEventListener("click", function () {
            const index = Number(
                selector.dataset.catalogueIndex
            );

            if (!Number.isNaN(index)) {
                updateCatalogue(index);
            }
        });
    });

    if (previousButton) {
        previousButton.addEventListener("click", function () {
            updateCatalogue(activeIndex - 1);
        });
    }

    if (nextButton) {
        nextButton.addEventListener("click", function () {
            updateCatalogue(activeIndex + 1);
        });
    }

    explorer.addEventListener("keydown", function (event) {
        if (event.key === "ArrowUp" ||
            event.key === "ArrowLeft") {

            event.preventDefault();
            updateCatalogue(activeIndex - 1);
        }

        if (event.key === "ArrowDown" ||
            event.key === "ArrowRight") {

            event.preventDefault();
            updateCatalogue(activeIndex + 1);
        }

        if (event.key === "Home") {
            event.preventDefault();
            updateCatalogue(0);
        }

        if (event.key === "End") {
            event.preventDefault();
            updateCatalogue(products.length - 1);
        }
    });

    updateCatalogue(0);
});