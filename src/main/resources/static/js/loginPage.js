$(function () {
    $("#goToRegister span").click(function () {
        self.location = "/registerPage";
    });

    $("#loginButton").click(function (event) {
        event.preventDefault();
        var email = $("#email").val();
        $.ajax({
            type: "POST",
            url: "/login",
            data: "email=" + email + "&password=" + $('#password').val(),
            dataType: "json",
            success: function (data) {
                console.log("email" + email + " " + data);
                if (data.state == "false") {
                    alert("用户名和密码不匹配");
                    localStorage.userId = "";
                }
                else {
                    self.location = "/index";
                    localStorage.userId = data.userId;
                }
            }
        });

    })
});