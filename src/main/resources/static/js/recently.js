$(function () {
    var urlUserId = getQueryString("userId");
    var userId = urlUserId == null ? localStorage.userId : urlUserId;
    var type = localStorage.recently;

    //
    $("#detail").on("click", ".title,.content", function () {
        self.location = "/activityDetail?activityId=" + $(this).parent("div").attr("id");
    });

    //
    $("#detail").on("click", ".infoDiv", function () {
        if (localStorage.userId == $(this).attr("id")) {
            self.location = "/home";
        } else {
            self.location = "/home?userId=" + $(this).attr("id");
        }
    });

    if (type == 1) {
        //最近浏览
        $.ajax({
            url: "",
            success: function () {

            }
        })
    } else if (type == 2) {
        //发起的活动
        $.ajax({
            url: "getCreateActivity",
            data: "userId=" + userId,
            //dataType: "json",
            success: function (data) {
                //alert(typeof data.createActivityList);
                //createActivity(data.createActivityList);
                $.each(data.createActivityList, function (idx, item) {
                    var activity = $("<div class='activity' id='" + item.activityId + "'></div>");
                    activity.html(
                        "<div class='infoDiv' id='" + item.creator + "' >" +
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
        })

    } else if (type == 3) {
        //参与的活动
        $.ajax({
            url: "getJoinActivity",
            data: "userId=" + userId,
            //dataType: "json",
            success: function (data) {
                //alert(typeof data.joinActivityList);
                createActivity(data.joinActivityList);
            }
        })

    }
});

function createActivity(list) {
    //alert(list.length)
    $.each(list, function (idx, item) {
        var activity = $("<div class='activity' id='" + item.activityId + "'></div>");
        activity.html(
            "<div class='infoDiv' id='" + item.creator + "' >" +
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

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}