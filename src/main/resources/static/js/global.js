$(function () {

    var userId = localStorage.userId;

    if ("" == userId) {
        $("#personal").hide();
    } else {
        $("#goToRegister").hide();
        $("#goToLogin").hide();
    }

    $("#logo").click(function () {
        self.location = "/index";
    });


});
