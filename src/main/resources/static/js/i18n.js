window.onload = function() {
    const select = document.getElementById("locales");
    const categoryDiv = document.getElementsByClassName("dropdown category").item(0);
    const infoNode = document.getElementById("category-info");
    let language = document.documentElement.lang;

    select.onchange = function() {
        let lang = select.options[select.selectedIndex].value
        if (lang != "") {
            window.location.replace("?lang=" + lang);
        }
    }

    function switchToPolish(node) {
        switch(node.innerText) {
            case "business": node.innerText = "biznes";
                break;
            case "entertainment": node.innerText = "rozrywka";
                break;
            case "general": node.innerText = "ogólne";
                break;
            case "health": node.innerText = "zdrowie";
                break;
            case "science": node.innerText = "nauka";
                break;
            case "sports": node.innerText = "sport";
                break;
            case "technology": node.innerText = "technologia";
                break;
            default: console.log('Sorry, we are out of ' + node.innerText + '.');
        }
    }

    if (language === "pl") {
        if (categoryDiv) {
            const categoryList = categoryDiv.childNodes[3].querySelectorAll("li");
            if (categoryList) {
                try {
                    categoryList.forEach(element => {
                        let aLink = element.querySelector("a");
                        switchToPolish(aLink);
                    });
                } catch (e) {
                    console.log(e)
                }
            }
        }
        if (infoNode) {
            switchToPolish(infoNode);
        }
    }
}