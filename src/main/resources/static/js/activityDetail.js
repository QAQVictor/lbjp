$(function () {
    var userId = localStorage.userId;
    var activityId = getQueryString("activityId");

    if (userId != "") {
        $.ajax({
            url: "/judgeJoin",
            data: "userId=" + userId + "&activityId=" + activityId,
            success: function (data) {
                if (data == "1") {
                    $("#entryButton").css("background-color", "rgb(179,179,179)");
                    $("#entryButton").html("已报名");
                    $("#entryButton").unbind();
                }
            }
        })
    } else {
        alert("请重新登录");
        self.location = "/loginPage";
    }

    $.ajax({
        url: "getActivityCreator?activityId=" + getQueryString("activityId"),
        dataType: "json",
        success: function (data) {
            $("#creatorHeadImg").attr("src", data.headImgPath);
            $("#creatorName").html(data.userName);
            $("#activityNum span").eq(1).html(data.activityNum);
            $("#followNum span").eq(1).html(data.followNum);
            $("#creatorInfo").attr("data--id", data.userId);
        }
    });

    $.ajax({
        url: "activityDetailInfo?activityId=" + getQueryString("activityId"),
        dataType: "json",
        success: function (data) {
            $("#tags div").html(data.tagName);
            $("#title").html(data.theme);
            $("#activityDate").html("活动时间（" + new Date(data.startDate).toLocaleString() +
                " — " + new Date(data.endDate).toLocaleString() + "）");
            $("#detail p").html(data.content);
            $(".operations .comments").html(data.commentNum + " 条评论");
            $(".operations .nice").html(data.agreeNum + " 个赞");

            $("#entry span").eq(1).html(data.actualNum + "/" + data.plannedNum);
            $("#hot span").eq(1).html(data.hot);
            $("#startDate").html("报名开始于 " + new Date(data.entryStartDate).toLocaleString());
            $("#endDate").html("截止到 " + new Date(data.entryEndDate).toLocaleString());


            var nowTimeStamp = Date.parse(new Date());
            if (nowTimeStamp < data.entryStartDate) {
                $("#entryButton").css("background-color", "rgb(179,179,179)");
                $("#entryButton").html("未开始");
                $("#entryButton").unbind();
            }
            if (nowTimeStamp > data.entryEndDate) {
                $("#entryButton").css("background-color", "rgb(179,179,179)");
                $("#entryButton").html("已结束");
                $("#entryButton").unbind();
            }
        }
    })
    $("#entryButton").click(function () {
        if (userId != "") {
            $.ajax({
                url: "/join",
                data: "userId=" + userId + "&activityId=" + activityId,
                success: function (data) {
                    //成功传0，过期向前端传1，未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5
                    alert(typeof data);
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
    })
});

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

Date.prototype.toLocaleString = function () {
    return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + "";
};