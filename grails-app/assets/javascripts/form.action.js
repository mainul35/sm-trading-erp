Fusion.requestManager.beforeSubmit(function () {
    $("form").attr("onsubmit", "return false")

})

Fusion.requestManager.onSubmit(function () {
    $(".btnSubmit").on('click', function (e) {
        var form = $(this).closest("form")
        var url = form.attr("to-url")
        var data = JSON.stringify(form.serializeArray())
        data = btoa(data)
        var clientDetails = JSON.stringify([
            {appName: 'bismillah-app'},
            {clientId: 'NzA3NjM4YmUtMjViMy00ZDVlLThhYzctNjYyZjFhNWJhOTI1'},
            {clientSecret:'ODE4N2E2NDQtMmExOS00NTQ0LWE5MjUtYmQwNmEwMjMwM2Fm'}
        ])

        $.ajax({
            type: 'POST',
            url: url+"?key="+data,
            data: {},
            dataType: "json",
            headers: {
                "client-details": btoa(clientDetails)
            },
            success: function (result) {
                console.log(result.url)
            }
        });
    })
}())