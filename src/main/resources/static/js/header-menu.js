document.addEventListener("DOMContentLoaded", function () {
    const navigation = document.getElementById("headerNavigation");

    if (!navigation) {
        return;
    }

    let closeTimeout;

    function openNavigation() {
        window.clearTimeout(closeTimeout);

        navigation.classList.add(
            "header-navigation--open"
        );
    }

    function closeNavigation() {
        navigation.classList.remove(
            "header-navigation--open"
        );
    }

    function scheduleCloseNavigation() {
        window.clearTimeout(closeTimeout);

        closeTimeout = window.setTimeout(
            closeNavigation,
            250
        );
    }

    navigation.addEventListener(
        "mouseenter",
        openNavigation
    );

    navigation.addEventListener(
        "mouseleave",
        scheduleCloseNavigation
    );

    navigation.addEventListener(
        "focusin",
        openNavigation
    );

    navigation.addEventListener(
        "focusout",
        function (event) {
            if (!navigation.contains(event.relatedTarget)) {
                scheduleCloseNavigation();
            }
        }
    );

    document.addEventListener(
        "keydown",
        function (event) {
            if (event.key === "Escape") {
                closeNavigation();
            }
        }
    );
});