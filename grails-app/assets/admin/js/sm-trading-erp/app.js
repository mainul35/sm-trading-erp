var APP = {}
APP.tabs = []

APP.addTab = function (tab) {
    var li = document.createElement("li")
    var a = document.createElement("a")
    var t = document.createTextNode(tab.name)
    var tab = {}

    li.classList.add("nav-item")
    li.appendChild(a)

    a.appendChild(t)
    a.href = tab.url ? tab.url : ""
    a.classList.add("nav-link")
    document.querySelector(".erp-nav").appendChild(li)
    tab.li = li
    tab.dropdownItems = []
    this.tabs.push(tab)
}

APP.addDropdownItem = function (tabName, dropdownItem) {
    var tab = APP.createDropdown(tabName)
    var t = document.createTextNode(dropdownItem.name)
    var childAnchor = document.createElement("a")
    childAnchor.classList.add("dropdown-item")
    childAnchor.appendChild(t)
    childAnchor.href = dropdownItem.url ? dropdownItem.url : ""
    tab.dropdownItems.push(childAnchor)
    APP.replaceTab(tab)
}

APP.replaceTab = function (tab) {
    APP.tabs.forEach(function (t, i) {
        var parentAnchor = t.li.querySelector("a")
        if (parentAnchor.textContent === tab.li.querySelector("a").textContent) {
            APP.tabs[i] = tab
        }
    })
}

APP.removeTab = function (tabName) {
    return this.tabs.filter(
        function (value) {
            return value.textContent !== tabName;
        })
}

APP.removeDropdownItem = function (dropdownItem) {
    return this.tabs.filter(
        function (value) {
            return value !== dropdownItem;
        })
}

APP.createDropdown = function (tabName) {
    for (var count = 0; count < APP.tabs.length; count++) {
        var tab = APP.tabs[count]
        var parentAnchor = tab.li.querySelector("a")
        if (parentAnchor.textContent === tabName) {
            if (!parentAnchor.classList.contains("dropdown-toggle")) {
                var div = document.createElement("div")
                parentAnchor.classList.add("dropdown-toggle")
                parentAnchor.dataset.toggle = "dropdown"
                parentAnchor.id = "navbarDropdownMenuLink"
                parentAnchor.setAttribute("aria-haspopup", "true");
                parentAnchor.setAttribute("aria-expanded", "true");
                tab.li.classList.add("dropdown")
                div.classList.add("dropdown-menu")
                div.classList.add("app-dropdown")
                div.setAttribute("aria-labelledby", "navbarDropdownMenuLink");
                div.setAttribute("x-placement", "bottom-start");
                tab.li.appendChild(div)
            }
            return tab
        }
    }
}

APP.initializeTabs = function () {
    APP.tabs.forEach(function (tab) {
        document.querySelector(".erp-nav").appendChild(tab.li)
    })
}

APP.initializeDropdownItems = function (tabName, dropdownItems) {

    dropdownItems.forEach(function (dropdownItem) {
        APP.addDropdownItem(tabName, dropdownItem)
    })

    APP.tabs.filter(function (tab) {

        var parentAnchor = tab.li.querySelector("a")
        if (parentAnchor.textContent === tabName) {
            tab.dropdownItems.forEach(function (ddi) {
                tab.li.querySelector(".dropdown-menu").appendChild(ddi)
            })
        }
    })
}