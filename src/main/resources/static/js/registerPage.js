$(function () {
    $("#email").focus();

    var userName = false;
    var phone = false;
    var email = false;
    var password = false;

    $("#email").blur(function () {
        var regex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/;
        if (regex.test($(this).val())) {
            $.ajax({
                url: "/judge?email=" + $(this).val(),
                type: "get",
                success: function (data) {
                    if (data == "false") {
                        email = false;
                        console.log("email" + email);
                        $("#email").css("border-color", "rgb(255,0,0)");
                    }
                    else {
                        email = true;
                        $(this).css("border-color", "rgb(187,187,187)");
                    }
                }
            });
        } else {
            email = false;
            $("#email").css("border-color", "rgb(255,0,0)");
        }
    });

    $("#password").blur(function () {
        var regex = /^[a-zA-Z][a-zA-Z0-9_]{6,12}$/;
        if (regex.test($(this).val())) {
            password = true;
            $(this).css("border-color", "rgb(187,187,187)");
        } else {
            password = false;
            $("#phone").css("border-color", "rgb(255,0,0)");
        }
    });

    $("#phone").blur(function () {
        var regex = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (regex.test($(this).val())) {
            $.ajax({
                url: "/judge?phone=" + $(this).val(),
                type: "get",
                success: function (data) {
                    if (data == "false") {
                        phone = false;
                        console.log("phone" + phone);
                        $("#phone").css("border-color", "rgb(255,0,0)");
                    }
                    else {
                        phone = true;
                        $(this).css("border-color", "rgb(187,187,187)");
                    }
                }
            });
        } else {
            phone = false;
            $("#phone").css("border-color", "rgb(255,0,0)");
        }
    });

    $("#userName").blur(function () {
        var regex = /^[A-Za-z0-9\u4e00-\u9fa5]{1,16}$/;
        if (regex.test($(this).val())) {
            userName = true;
            $("#userName").css("border-color", "rgb(187,187,187)");
        } else {
            userName = false;
            $("#userName").css("border-color", "rgb(255,0,0)");
        }
    });

    $("#goToLogin").click(function () {
        self.location = "/loginPage";
    });

    $("#registerButton").click(function () {
        console.log("userName" + userName + " " + "phone" + phone + " " + "email" + email + "password" + password);
        $(".registerInfo input").each(function () {
            if ($(this).val().length == "0") {
                console.log($(this).val());
                $(this).css("border-color", "rgb(255,0,0)");
                $("#registerForm").on("submit", function (event) {
                    event.preventDefault();
                })
                return false;
            }
        })
        if (userName == true && phone == true && email == true && password == true) {
            $("#registerForm").submit();
        }
    });

    $(".registerInfo input").focus(function () {
        $(this).css("border-color", "rgb(187,187,187)");
    });

    $(".registerInfo input").blur(function () {
        if ($(this).val().length == "0") {
            $(this).css("border-color", "rgb(255,0,0)");
        }
    });

});
