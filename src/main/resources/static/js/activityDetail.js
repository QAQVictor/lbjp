$(function () {
    var userId = localStorage.userId;
    var activityId = getQueryString("activityId");
    var tagStar;

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
                    case "7":
                        $("#entryButton").html("活动已结束");
                        break;
                    case "6":
                        $("#entryButton").html("活动已取消");
                        break;
                    case "1":
                        $("#entryButton").html("报名已结束");
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
                    case "2":
                        $("#entryButton").html("报名未开始");
                        break;
                }
            }
        })
    } else {
        $("#entryButton").css("background-color", "rgb(179,179,179)");
        $("#entryButton").html("未登录");
        $("#entryButton").unbind("click");
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
            if (localStorage.userId == data.userId) {
                $("#follow").remove();
            }
            judgeFollow(data.userId);
        }
    });

    //获取活动详情
    $.ajax({
        url: "activityDetailInfo?activityId=" + getQueryString("activityId"),
        data: "&userId=" + localStorage.userId,
        dataType: "json",
        success: function (data) {
            $("#tags .tag").html(data.tagName);
            $("#tags .tag").attr("data-id", data.tagId);
            $("#tags .tag").attr("data-name", data.tagName);
            $("#title").html(data.theme);
            $("#activityDate").html("活动时间（" + new Date(data.startDate).toLocaleString() +
                " — " + new Date(data.endDate).toLocaleString() + "）");
            $("#detail p").html(data.content);
/*            $(".operations .comments").html(data.commentNum + " 条评论");
            $(".operations .nice").html(data.agreeNum + " 个赞");*/

            $("#entry span").eq(1).html(data.actualNum + "/" + data.plannedNum);
            $("#hot span").eq(1).html(data.hot);
            $("#startDate").html("报名开始于</br> " + new Date(data.entryStartDate).toLocaleString());
            $("#endDate").html("截止到</br> " + new Date(data.entryEndDate).toLocaleString());
            //判断标签是否收藏
            $.ajax({
                url: "judgeStar",
                data: "tagId=" + data.tagId + "&userId=" + userId,
                success: function (data) {
                    if (data == "0") {
                        tagStar = false;
                    } else {
                        tagStar = true;
                    }
                }
            });
        }
    });

    //点击报名按钮
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
                    } else if (data == "6") {
                        alert("活动已取消");
                    }
                }
            })
        }
        else {
            alert("请重新登录");
            self.location = "/loginPage";
        }
    });


    //标签的鼠标移动和点击事件
    $(".tag").mouseover(function () {
        if (tagStar) {
            $(this).html("取消收藏");
        } else {
            $(this).html("收藏");
        }
    });
    $(".tag").mouseout(function () {
        $(this).html($(this).attr("data-name"));
    });
    $(".tag").click(function () {
        var tagId = $(this).attr("data-id");
        //取消收藏
        if (tagStar) {
            $.ajax({
                url: "/cancelStar",
                data: "tagId=" + tagId + "&userId=" + userId,
                success: function (data) {
                    if (data == "1") {
                        tagStar = false;
                    } else {
                        tagStar = true;
                        alert("取消收藏失败");
                    }
                }
            })
        } else {//收藏
            $.ajax({
                url: "/starTag",
                data: "tagId=" + tagId + "&userId=" + userId,
                success: function (data) {
                    if (data == "0") {
                        tagStar = true;
                    } else {
                        tagStar = false;
                    }
                }
            })
        }

    });

    $("#creatorHeadImg,#creatorName").click(function () {
        if (localStorage.userId == $("#creatorInfo").attr("data-id")) {
            self.location = "/home";
        } else {
            self.location = "/home?userId=" + $("#creatorInfo").attr("data-id");
        }
    });

    //发起人的关注按钮点击事件
    $("#follow").click(function () {
        if ($(this).html() == "关注") {
            followSomeone($("#creatorInfo").attr("data-id"));
        } else if ($(this).html() == "取消关注") {
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