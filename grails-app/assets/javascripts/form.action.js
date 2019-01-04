Fusion.requestManager.beforeSubmit(function () {
    $("form").attr("onsubmit", "return false")

})

Fusion.requestManager.onSubmit(function () {
    $(".btnSubmit").on('click', function (e) {
        var form = $(this).closest("form")
        var url = form.attr("to-url")
        var data = JSON.stringify(form.serializeArray())
        console.log(navigator.userAgent)
        data = btoa(data)
        $.ajax({
            type: 'POST',
            url: url+"?key="+data,
            data: {},
            dataType: "json",
            success: function (result) {
                console.log(result.url)
                window.location.assign(`http://${result.url}?token=${result.token}`)
            }
        });
    })
}())