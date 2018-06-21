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

    //goTo 0home主页，1最近，2发起，3参与，4收藏，5关注，6日程
    $("#personal").find("img").eq(0).click(function () {
        localStorage.goTo = 6;
        self.location = "/home";
    })
    //goTo 0home主页，1最近，2发起，3参与，4收藏，5关注，6日程
    $("#personal").find("img").eq(1).click(function () {
        localStorage.goTo = 1;
        self.location = "/home";
    })
});
