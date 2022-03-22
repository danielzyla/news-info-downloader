window.onload = function() {
    var select = document.getElementById("locales")
    select.onchange = function() {
        var optionValue = select.options[select.selectedIndex].value
        if (optionValue != "") {
            window.location.replace('?lang=' + optionValue);
        }
    }
}