document.addEventListener("DOMContentLoaded", function () {
    const banner = document.getElementById("global-banner");

    if (!banner) {
        return;
    }

    let closing = false;

    function closeBanner() {
        if (closing) {
            return;
        }

        closing = true;
        banner.classList.add("global-banner--closing");

        window.setTimeout(function () {
            banner.remove();
        }, 350);
    }

    banner.addEventListener("click", closeBanner);

    banner.addEventListener("keydown", function (event) {
        if (
            event.key === "Enter"
            || event.key === " "
            || event.key === "Escape"
        ) {
            event.preventDefault();
            closeBanner();
        }
    });

    window.setTimeout(closeBanner, 5000);
});