$(function () {
    function judgeLogin() {
        var userId = localStorage.userId;
        if (userId == "") {
            self.location = "/loginPage";
        }
    }
});