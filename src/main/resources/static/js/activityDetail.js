$(function () {
    //console.log(getQueryString("activityId"));
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
    })

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