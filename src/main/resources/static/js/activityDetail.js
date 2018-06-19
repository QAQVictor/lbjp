$(function () {
    var userId = localStorage.userId;
    var activityId = getQueryString("activityId");
    var tagName;

    if (userId != "") {
        $.ajax({
            url: "/judgeJoin",
            data: "userId=" + userId + "&activityId=" + activityId,
            success: function (data) {
                if (data != "0") {
                    $("#entryButton").css("background-color", "rgb(179,179,179)");
                    $("#entryButton").unbind();
                }
                switch (data) {
                    case "1":
                        $("#entryButton").html("报名已结束");
                        break;
                    case "2":
                        $("#entryButton").html("报名未开始");
                        break;
                    case "3":
                        $("#entryButton").html("人数已满");
                        break;
                    case "4":
                        $("#entryButton").html("已报名");
                        break;
                    case "5":
                        $("#entryButton").html("日程冲突");
                        break;
                    case "6":
                        $("#entryButton").html("活动已取消");
                        break;
                }
            }
        })
    } else {
        $("#entryButton").css("background-color", "rgb(179,179,179)");
        $("#entryButton").html("未登录");
        $("#entryButton").unbind();
    }

    $.ajax({
        url: "getActivityCreator?activityId=" + getQueryString("activityId"),
        dataType: "json",
        success: function (data) {
            $("#creatorHeadImg").attr("src", data.headImgPath);
            $("#creatorName").html(data.userName);
            $("#activityNum span").eq(1).html(data.activityNum);
            $("#followNum span").eq(1).html(data.followNum);
            $("#creatorInfo").attr("data-id", data.userId);
            judgeFollow(data.userId);
        }
    });

    $.ajax({
        url: "activityDetailInfo?activityId=" + getQueryString("activityId"),
        dataType: "json",
        success: function (data) {
            tagName = data.tagName;
            $("#tags div").html(data.tagName);
            $("#tags div").attr("data-id", data.tagId);
            $("#title").html(data.theme);
            $("#activityDate").html("活动时间（" + new Date(data.startDate).toLocaleString() +
                " — " + new Date(data.endDate).toLocaleString() + "）");
            $("#detail p").html(data.content);
            $(".operations .comments").html(data.commentNum + " 条评论");
            $(".operations .nice").html(data.agreeNum + " 个赞");

            $("#entry span").eq(1).html(data.actualNum + "/" + data.plannedNum);
            $("#hot span").eq(1).html(data.hot);
            $("#startDate").html("报名开始于</br> " + new Date(data.entryStartDate).toLocaleString());
            $("#endDate").html("截止到</br> " + new Date(data.entryEndDate).toLocaleString());


            /* var nowTimeStamp = Date.parse(new Date());
             if (nowTimeStamp < data.entryStartDate) {
                 $("#entryButton").css("background-color", "rgb(179,179,179)");
                 $("#entryButton").html("报名未开始");
                 $("#entryButton").unbind();
             }else if (nowTimeStamp > data.entryEndDate) {
                 $("#entryButton").css("background-color", "rgb(179,179,179)");
                 $("#entryButton").html("报名已结束");
                 $("#entryButton").unbind();
             }*/
        }
    });

    $("#entryButton").click(function () {
        if (userId != "") {
            $.ajax({
                url: "/join",
                data: "userId=" + userId + "&activityId=" + activityId,
                success: function (data) {
                    //成功传0，过期向前端传1，未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5
                    if (data == "0") {
                        alert("报名成功");
                        window.location.reload();
                    } else if (data == "1") {
                        alert("报名已结束");
                    } else if (data == "2") {
                        alert("报名未开始");
                    } else if (data == "3") {
                        alert("人数已满");
                    } else if (data == "4") {
                        alert("请不要重复报名");
                    } else if (data == "5") {
                        alert("请合理安排您的行程，该时间段已经有其他安排！");
                    }
                }
            })
        }
        else {
            alert("请重新登录");
            self.location = "/loginPage";
        }
    });

    $(".tag").mouseover(function () {
        $.ajax({
            url: "judgeStar",
            data: "tagId=" + $(this).attr("data-id") + "&userId=" + userId,
            success:

                function (data) {
                    if (data == "0") {
                        $(this).html("收藏");
                    } else {
                        $(this).html("已收藏");
                    }
                }
        })
        ;
    });
    $(".tag").mouseout(function () {
        $(this).html(tagName);
    });
    $(".tag").click(function () {
        $.ajax({
            url: "/starTag",
            data: "tagId=" + $(this).attr("data-id") + "&userId=" + userId,
            success: function (data) {
                if (data == "0") {
                    alert("收藏成功！");
                }
            }
        })
    });

    $("#creatorHeadImg,#creatorName").click(function () {
        self.location = "/home?userId=" + $("#creatorInfo").attr("data-id");
    })


    $("#follow").click(function () {
        if ($(this).html() == "关注") {
            followSomeone($("#creatorInfo").attr("data-id"));
        } else if ($(this).html("取消关注")) {
            cancelFollow($("#creatorInfo").attr("data-id"));
        }
    });
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function followSomeone(urlUserId) {
    $.ajax({
        url: "followSomeone",
        data: "followerId=" + localStorage.userId + "&starId=" + urlUserId,
        success: function (data) {
            if (data == "0") {
                $("#follow").html("已关注");
                $("#follow").css("background-color", "rgb(179,179,179)");
                $("#follow").css("border", "rgb(179,179,179)");
                $("#follow").mouseover(function () {
                    $("#follow").html("取消关注");
                });
                $("#follow").mouseleave(function () {
                    $("#follow").html("已关注");
                });
            } else {
                alert("关注失败！");
            }
        }
    })
}

function judgeFollow(urlUserId) {
    $.ajax({
        url: "judgeFollow",
        data: "followerId=" + localStorage.userId + "&starId=" + urlUserId,
        success: function (data) {
            if (data == "0") {
                $("#follow").html("关注");
            } else {
                $("#follow").html("已关注");
                $("#follow").css("background-color", "rgb(179,179,179)");
                $("#follow").css("border", "rgb(179,179,179)");
                $("#follow").mouseover(function () {
                    $("#follow").html("取消关注");
                });
                $("#follow").mouseleave(function () {
                    $("#follow").html("已关注");
                });
            }
        }
    });
}

function cancelFollow(urlUserId) {
    $.ajax({
        url: "cancelFollow",
        data: "followerId=" + localStorage.userId + "&starId=" + urlUserId,
        success: function (data) {
            if (data == "1") {
                $("#follow").html("关注");
                $("#follow").css("background-color", "rgba(0, 170, 238, 1)");
                $("#follow").css("border", "rgba(0, 170, 238, 1)");
                $("#follow").unbind("mouseover");
                $("#follow").unbind("mouseleave");
            } else {
                alert("取消关注失败！");
            }
        }
    });
}