$(function () {

    initActivity();

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
        $("#hideDiv").show();
        $("#newActivity").show();
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
                        $("#hideDiv").hide();
                        $("#newActivity").hide();
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
        "<img id='close' src='/img/icon/close.ico' onclick='removeAddActivityDiv()'/>" +
        "<h3 style='text-align: center'>发帖</h3>" +
        "<span style='text-align: center;display: block;font-size: 12px;color: rgb(178,178,178);'>发起一次活动，和未来的朋友一起结伴出行吧！</span>" +
        "<form id='addActivityForm'>" +
        "<ul id='activityInfo'>" +
        "<li id='newTitle'>" +
        "<input type='text' id='activityTitle' placeholder='活动标题' name='theme'/>" +
        "</li>" +
        "<li id='newTag'>" +
        "<input type='text' id='tag' placeholder='活动所属标签' name='tagName'/>" +
        "<!-- <select type=\"text\" id=\"tag\" placeholder=\"活动所属标签\" name=\"tagSelect\"/>-->" +
        "</li>" +
        "<li id='newTime'>" +
        "<span>活动起止时间</span>" +
        "<input type='date' placeholder='开始时间' name='startDate' id='startDate'/>" +
        "<input type='date' placeholder='结束时间' name='endDate' id='endDate'/>" +
        "</li>" +
        "<li id='newEntryTime'>" +
        "<span>报名起止时间</span>" +
        "<input type='date' placeholder='开始时间' name='entryStartDate' id='entryStartDate'/>" +
        "<input type='date' placeholder='结束时间' name='entryEndDate' id='entryEndDate'/>" +
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
        "<input type='submit' value='发起' id='addActivity' />" +
        "</li>" +
        "</ul>" +
        "</form>"
    );
    $("#main").after($(hideDiv));
    $("#hideDiv").after($(newActivity));
}

function removeAddActivityDiv() {
    $("#hideDiv").remove();
    $("#newActivity").remove();
}