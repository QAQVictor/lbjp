$(function () {

    initActivity();
    var userId = localStorage.userId;
    $.ajax({
        url: "/getUser?userId=" + userId,
        success: function (data) {
            if (data == "0") {
                createInfoDiv();
            }
        }
    })

    $(".title,.content").click(function () {
        var activityId = $(this).parent(".activity").attr("id");
        $.ajax({
            url: "activityDetail?activityId=" + activityId,
            success: function (data) {
                console.log(data);
            }
        })
    });

    $(".content").each(function () {
        if ($(this).css("height") == $(this).css("max-height")) {

        }
    });
    $("#mine1").click(function () {
        createAddActivityDiv();
    });

    $("#goToHome").click(function () {
        self.location = "/home";
    });

    $(document).on("click", "#addActivity", function (event) {
        //处理空格和换行
        $("#content").val($("#content").val().replace(/\n/g, "<br/>").replace(/\s/g, "&nbsp;"));

        event.preventDefault();
        if ($("#activityTitle").val() == "" || $("#tag").val() == "" ||
            $("#startDate").val() == undefined || $("#endDate").val() == undefined ||
            $("#entryStartDate").val() == undefined || $("#entryEndDate").val() == undefined ||
            $("#plannedNum").val() == undefined || $("#content").val() == "") {
            alert("信息输入不全！");
            return;
        }
        var userId = localStorage.userId;
        if (userId != "") {
            $.ajax({
                type: "POST",
                url: "/addActivity?creator=" + userId,
                data: $("#addActivityForm").serializeArray(),
                dataType: "json",
                success: function (data) {
                    if (data.state == "true") {
                        alert("发布成功！");
                        $("#hideDiv").remove();
                        $("#newActivity").remove();
                    } else {
                        alert("请您重新输入!");
                    }
                    initActivity();
                }
            })
        }
    });

    $("#detail").on("click", ".title,.content", function () {
        self.location = "/activityDetail?activityId=" + $(this).parent("div").attr("id");
    })
});

function initActivity() {
    $.ajax({
        url: "/getIndexActivity",
        success: function (data) {
            $.each(data.activityList, function (idx, item) {
                var activity = $("<div class='activity' id='" + item.activityId + "'></div>");
                activity.html(
                    "<div class='infoDiv'>" +
                    "<img class='userHeadImg' style='width: 30px;height: 30px;' src='" + item.headImgPath + "'/>" +
                    "<span class='userName'>" + item.userName + "</span>" +
                    "<span class='userRemark'>" + item.remark + "</span>" +
                    "<span class='tag'>" + item.tagName + "</span>" +
                    "</div>" +
                    "<span class='title'>" + item.theme + "</span>" +
                    "<p class='content'>" + item.content + "</p>" +
                    "<div class='operations'>" +
                    "<div><img src='/img/icon/comments.ico'/><span class='comments'>" + item.commentNum + "条评论</span></div>" +
                    "<div><img src='/img/icon/thumb.ico'/><span class='nice'>" + item.agreeNum + "个赞</span></div>" +
                    "<div><img src='/img/icon/star0.ico'/><span class='star'>收藏</span></div>" +
                    "<div><img src='/img/icon/pay.ico'/><span class='admire'>赞赏</span></div>" +
                    "</div>"
                );
                $("#detail").append($(activity));
            });
        }
    });
}

function createAddActivityDiv() {
    var hideDiv = $("<div id='hideDiv'></div>");
    var newActivity = $("<div id='newActivity'></div>>");
    newActivity.html(
        "<img class='close' src='/img/icon/close.ico' onclick='removeAddActivityDiv()'/>" +
        "<h3 style='text-align: center'>发帖</h3>" +
        "<span style='text-align: center;display: block;font-size: 12px;color: rgb(178,178,178);'>发起一次活动，和未来的朋友一起结伴出行吧！</span>" +
        "<form id='addActivityForm'>" +
        "<ul id='activityInfo'>" +
        "<li id='newTitle'>" +
        "<input type='text' id='activityTitle' placeholder='活动标题' name='theme' maxlength='25'/>" +
        "</li>" +
        "<li id='newTag'>" +
        "<input type='text' id='tag' placeholder='活动所属标签' name='tagName'/>" +
        "<!-- <select type=\"text\" id=\"tag\" placeholder=\"活动所属标签\" name=\"tagSelect\"/>-->" +
        "</li>" +
        "<li id='newTime'>" +
        "<span>活动起止时间</span>" +
        "<input type='datetime-local' name='startDate' id='startDate'/>" +
        "<input type='datetime-local' name='endDate' id='endDate'/>" +
        "</li>" +
        "<li id='newEntryTime'>" +
        "<span>报名起止时间</span>" +
        "<input type='date' name='entryStartDate' id='entryStartDate'/>" +
        "<input type='date' name='entryEndDate' id='entryEndDate'/>" +
        "</li>" +
        "<li id='newNum'>" +
        "<span>人数</span>" +
        "<input type='number' placeholder='人数限制' name='plannedNum' min='1' id='plannedNum'/>" +
        "</li>" +
        "<li id='newContent'>" +
        "<span>具体活动内容</span>" +
        "<textarea id='content' cols='100' rows='6' hard='hard' name='content' placeholder='输入具体的活动内容'></textarea>" +
        "</li>" +
        "<li>" +
        "<input type='submit' value='发起' id='addActivity' class='submitBtn' />" +
        "</li>" +
        "</ul>" +
        "</form>"
    );
    $("#main").after($(hideDiv));
    $("#hideDiv").after($(newActivity));
}

function createInfoDiv() {
    var hideDiv = $("<div id='hideDiv'></div>");
    var infoDiv = $("<div id='infoDiv' style='text-align: center;'></div>");
    infoDiv.html(
        "<img class='close' src='/img/icon/close.ico' onclick='removeInfoDiv()'/>" +
        " <h3>个人资料</h3>" +
        "<span style='text-align: center;display: block;font-size: 12px;color: rgb(178,178,178); margin: 10px auto;'>补全个人资料，让其他人更了解你</span>" +
        "<ul>" +
        "<li>真实姓名：<input class='userInfo' type='text' name='realName' id='realName' placeholder='请输入您的真实姓名' maxlength='10'/></li>" +
        "<li id='gender'>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：" +
        "<input type='radio' name='gender' value='1'/>男&nbsp;&nbsp;&nbsp;&nbsp;" +
        "<input type='radio' name='gender' value='0'/>女" +
        "</li>" +
        "<li>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校：" +
        "<input type='text' class='userInfo' name='school' id='school' placeholder='请输入您的学校' maxlength='17'/></li>" +
        "<li>个性签名：" +
        "<input type='text' class='userInfo' name='remark' id='remark' placeholder='请输入个性签名' maxlength='100'/>" +
        "</li>" +
        "</ul>" +
        "<div class='submitBtn' onclick='updateUserInfo()'>提交</div>"
    )
    ;
    $("#main").after($(hideDiv));
    $("#hideDiv").after($(infoDiv));
}

function updateUserInfo(){
    $.ajax({
        url: "updateUser",
        data: "userId=" + localStorage.userId +
        "&realName=" + $("#realName").val() +
        "&gender=" + $("input[name='gender']:checked").val() +
        "&school=" + $("#school").val() +
        "&remark=" + $("#remark").val(),
        success: function (data) {
            if (data == "1") {
                alert("开始快乐之旅吧！");
                $("#hideDiv").remove();
                $("#infoDiv").remove();
            }
        }
    });
}

function removeAddActivityDiv() {
    $("#hideDiv").remove();
    $("#newActivity").remove();
}

function removeInfoDiv() {
    $("#hideDiv").remove();
    $("#infoDiv").remove();
}
