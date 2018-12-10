$(".btnSubmit").on('click', function (e) {
    e.preventDefault()
    $.ajax({
        type: 'POST',
        url: "someaction.do?action=saveData",
        data: $('.form').serialize(),
        dataType: "json",
        success: function (resultData) {
            alert("Save Complete")
        }
    });
})