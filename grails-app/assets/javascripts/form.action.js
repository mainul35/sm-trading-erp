Fusion.requestManager.beforeSubmit(function () {
    $("form").attr("onsubmit", "return false")

})

Fusion.requestManager.onSubmit(function () {
    $(".btnSubmit").unbind().on('click', function (e) {
        var form = $(this).closest("form")
        var url = form.attr("to-url")
        var data = JSON.stringify(form.serializeArray())
        data = btoa(data)
        $.ajax({
            type: 'POST',
            url: url + "?key=" + data,
            data: {},
            dataType: "json",
            headers: {
                "client-details": Fusion.authentication.clientDetails
            },
            success: function (result) {
                document.cookie = 'token='
                document.cookie = `token=${result.responseData.token}`
                if (result.responseData.statusCode == 200) {
                    location.href = '/management/dashboard'
                }
                // else if (result.responseData.statusCode == 404) {
                //     console.log(result.responseData.token.message)
                // }
            }
        });
    })
}())