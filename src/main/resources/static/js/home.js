$(function () {
    initUserInfo();

    initUserHistoryActivity();

    $("#edit").click(function () {
        createInfoDiv();
    });

    $("#label ul li").first().css("border-bottom", "3px solid rgb(0, 170, 238)");
    $("#detail").load("/recently");

    $("#label ul li").click(function () {
        $(this).css("border-bottom", "3px solid rgb(0, 170, 238)");
        $(this).siblings("li").css("border-bottom", "0px solid");
    });

    $("#schedule").click(function () {
        $("#detail").load("schedule.html");
    });

    $("#recently").click(function () {
        $("#detail").load("/recently");
    });

    $("#headImg").click(function () {
        $("#imgInput").click();
    });
    $('#imgInput').change(function () {
        var name, fileName, file;
        file = $("#imgInput");
        name = $("#imgInput").val();
        if (undefined == name || "" == name) {
            return;
        }
        fileName = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        if ("png" != fileName && "jpg" != fileName && "jpeg" != fileName) {
            alert("上传图片格式不正确，请重新上传");
            file.after(file.clone().val(""));
            file.remove();
        }
        var imgSize = $("#imgInput")[0].files[0].size;
        if (imgSize >= 1024 * 1024 * 3) {
            alert("图片大小在3M以内，为：" + imgSize / (1024 * 1024) + "M");
            file.after(file.clone().val(""));
            file.remove();
        } else {
            $("#imgForm").submit();
        }
    });
});

function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

function initUserInfo() {
    $.ajax({
        url: "/initUserInfo?userId=" + localStorage.userId,
        success: function (data) {
            var userInfo = JSON.parse(data);
            $("#headImg").attr("style", "background-image:url('../" + userInfo.headImgPath + "');");
            $("#userName").html(userInfo.userName);
            $("#school").html(userInfo.school);
            $("#remark").html(userInfo.remark);
            $("#imgUserId").val(localStorage.userId);
        }
    });
}

function initUserHistoryActivity() {

}

function initCreateActivity() {
    $.ajax({
        url: "getCreateActivity?userId=" + localStorage.userId,
        success: function (data) {

        }
    })
}

function initJoinActivity() {
    $.ajax({
        url: "getJoinActivity?userId=" + localStorage.userId,
        success: function (data) {

        }
    })
}

function initFollow() {
    $.ajax({
        url: "getFollow?userId=" + localStorage.userId,
        success: function (data) {

        }
    })

}

function initStar() {
    $.ajax({
        url: "getStar?userId=" + localStorage.userId,
        success: function (data) {

        }
    })
}

function createInfoDiv() {
    var hideDiv = $("<div id='hideDiv'></div>");
    var infoDiv = $("<div id='infoDiv' style='text-align: center;'></div>");
    infoDiv.html(
        "<img class='close' src='/img/icon/close.ico' onclick='removeInfoDiv()'/>" +
        " <h3>个人资料</h3>" +
        "<span style='text-align: center;display: block;font-size: 12px;color: rgb(178,178,178); margin: 10px auto;'>补全个人资料，让其他人更了解你</span>" +
        "<ul>" +
        "<li>昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：" +
        "<input class='userInfo' type='text' name='userName' id='newUserName' placeholder='请输入您的昵称' maxlength='10'/></li>" +
        "<li>真实姓名：<input class='userInfo' type='text' name='realName' id='realName' placeholder='请输入您的真实姓名' maxlength='10'/></li>" +
        "<li id='gender'>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：" +
        "<input type='radio' name='gender' value='1'/>男&nbsp;&nbsp;&nbsp;&nbsp;" +
        "<input type='radio' name='gender' value='0'/>女" +
        "</li>" +
        "<li>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校：" +
        "<input type='text' class='userInfo' name='school' id='newSchool' placeholder='请输入您的学校' maxlength='17'/></li>" +
        "<li>个性签名：" +
        "<input type='text' class='userInfo' name='remark' id='newRemark' placeholder='请输入个性签名' maxlength='100'/>" +
        "</li>" +
        "</ul>" +
        "<div class='submitBtn' onclick='updateUserInfo()'>提交</div>"
    )
    ;
    $("#main").after($(hideDiv));
    $("#hideDiv").after($(infoDiv));
}

function updateUserInfo() {
    $.ajax({
        url: "updateUser",
        data: "userId=" + localStorage.userId +
        "&realName=" + $("#realName").val() +
        "&gender=" + $("input[name='gender']:checked").val() +
        "&school=" + $("#newSchool").val() +
        "&remark=" + $("#newRemark").val() +
        "&userName=" + $("#newUserName").val(),
        success: function (data) {
            if (data == "1") {
                alert("更改成功！");
                $("#hideDiv").remove();
                $("#infoDiv").remove();
                window.location.reload();
            }
        }
    });
}

function removeInfoDiv() {
    $("#hideDiv").remove();
    $("#infoDiv").remove();
}