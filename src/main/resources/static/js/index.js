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
    });
    $("#close").click(function () {
        $("#hideDiv").hide();
        $("#newActivity").hide();
    });

    $("#goToHome").click(function () {
        self.location = "/home";
    });

    $("#addActivity").click(function (event) {
        event.preventDefault();
        var userId = localStorage.userId;
        if (userId != "") {
            $.ajax({
                type: "POST",
                url: "/addActivity?creator=" + userId,
                data: $("#addActivityForm").serializeArray(),
                dataType: "json",
                success: function (data) {
                    alert(data);
                    if (data.state == "true") {
                        alert("发布成功！");
                        $("#hideDiv").hide();
                        $("#newActivity").hide();
                        initActivity();
                    } else {
                        alert("请您重新输入!");
                    }
                }
            })
        }
    })
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

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