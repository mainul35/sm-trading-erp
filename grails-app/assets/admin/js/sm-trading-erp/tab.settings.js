APP.addTab({name: "Settings"})

APP.initializeTabs()

var settingsDropdownItems = [
    {name: "User settings", url:"/admin/settings/userSettings"}
]
APP.initializeDropdownItems("Settings", settingsDropdownItems)