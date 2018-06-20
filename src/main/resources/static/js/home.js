$(function () {

    var urlUserId = getQueryString("userId");

    //头像和编辑按钮
    if ("" == urlUserId || undefined == urlUserId || null == urlUserId || localStorage.userId == urlUserId) {
        initUserInfo(localStorage.userId);
    } else {
        initUserInfo(urlUserId);
        $("#imgForm").remove();
        $("#schedule").remove();
        judgeFollow(urlUserId);
    }

    initUserHistoryActivity();

    //编辑按钮点击事件
    $("#edit").click(function () {
        if ($(this).html() == "编辑") {
            createInfoDiv();
        } else if ($(this).html() == "关注") {
            followSomeone(urlUserId);
        } else if ($(this).html() == "取消关注") {
            cancelFollow(urlUserId);
        }
    });

    //蓝色粗线条移动
    $("#label ul li").first().css("border-bottom", "3px solid rgb(0, 170, 238)");
    $("#label ul li").first().css("color", "rgb(0, 170, 238)");
    $("#label ul li").first().css("font-weight", "bold");
    $("#label ul li").attr("data-isSelect", 0);
    $("#label ul li").first().attr("data-isSelect", 1);
    //$("#detail").load("/recently");

    //标签点击事件
    $("#label ul li").click(function () {
        $(this).css("border-bottom", "3px solid rgb(0, 170, 238)");
        $(this).css("color", "rgb(0, 170, 238)");
        $(this).css("font-weight", "bold");

        $(this).siblings("li").css("border-bottom", "0px solid");
        $(this).siblings("li").css("color", "rgb(0,0,0)");
        $(this).siblings("li").css("font-weight", "normal");
        $(this).siblings("li").attr("data-isSelect", 0);
    });

    //点击“日程”
    $("#schedule").click(function () {
        if ($(this).attr("data-isSelect") == 0) {
            $("#detail").empty();
            $("#detail").load("schedule.html");
        }
        $(this).attr("data-isSelect", 1);
    });

    //点击“最近”
    $("#recently").click(function () {
        if ($(this).attr("data-isSelect") == 0) {
            $("#detail").empty();
            $("#detail").load("/recently");
        }
        $(this).attr("data-isSelect", 1);
    });

    //点击收藏标签
    $("#hobby").click(function () {
        if ($(this).attr("data-isSelect") == 0) {
            $("#detail").empty();
            if (urlUserId == "" || urlUserId == undefined || urlUserId == null) {
                initHobby(localStorage.userId);
            } else {
                initHobby(urlUserId);
            }
        }
        $(this).attr("data-isSelect", 1);
    });

    //点击“关注”
    $("#follow").click(function () {
        if ($(this).attr("data-isSelect") == 0) {
            $("#detail").empty();
            var select = $("<ul id='selectUl'></ul>");
            select.html(
                "<li id='followers'>关注者</li>" +
                "<li id='stars'>关注了</li>"
            );
            $("#detail").append($(select));
            getFollower(urlUserId);
        }
        $(this).attr("data-isSelect", 1);
    });
    //点击关注者
    $("#detail").on("click", "#followers", function () {
        getFollower(urlUserId);
    });
    //点击关注了
    $("#detail").on("click", "#stars", function () {
        getStar(urlUserId);
    });

    //点击头像
    $("#headImg").click(function () {
        $("#imgInput").click();
    });
    //更改图像事件
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

    //收藏页面每个标签的鼠标移入，鼠标移出，点击事件
    $("#detail").on("mouseover", ".tags .tagDiv .star", function () {
        if ($(this).html() == "取消收藏") {
            $(this).css("background-color", "rgb(189, 189, 189)");
        } else {
            $(this).css("background-color", "rgb(0, 180, 248)");
        }
    });
    $("#detail").on("mouseleave", ".tags .tagDiv .star", function () {
        if ($(this).html() == "取消收藏") {
            $(this).css("background-color", "rgb(179, 179, 179)");
        } else {
            $(this).css("background-color", "rgb(0, 170, 238)");
        }
    });
    $("#detail").on("click", ".tags .tagDiv .star", function () {
        var hobbyTemp = $(this);
        if ($(this).html() == "取消收藏") {
            $.ajax({
                url: "/cancelStar",
                data: "tagId=" + $(this).attr("data-id") + "&userId=" + localStorage.userId,
                success: function (data) {
                    if (data == "1") {
                        if ("" == urlUserId || undefined == urlUserId || null == urlUserId) {
                            $(hobbyTemp).parent().remove();
                        } else {
                            $(hobbyTemp).css("background-color", "rgb(0, 170, 238)");
                            $(hobbyTemp).html("收藏");
                        }
                    } else {
                        alert("取消收藏失败");
                    }
                }
            });
        } else {
            $.ajax({
                url: "/starTag",
                data: "tagId=" + $(this).attr("data-id") + "&userId=" + localStorage.userId,
                success: function (data) {
                    if (data == "0") {
                        $(hobbyTemp).css("background-color", "rgb(0, 170, 238)");
                        $(hobbyTemp).html("取消收藏");
                    } else {
                        alert("收藏失败");
                    }
                }
            })
        }
    });

    //关注页面每个标签的鼠标移入，鼠标移出，点击事件
    $("#detail").on("mouseover", ".users .userDiv .follow", function () {
        if ($(this).html() == "取消关注") {
            $(this).css("background-color", "rgb(189, 189, 189)");
        } else {
            $(this).css("background-color", "rgb(0, 180, 248)");
        }
    });
    $("#detail").on("mouseleave", ".users .userDiv .follow", function () {
        if ($(this).html() == "取消关注") {
            $(this).css("background-color", "rgb(179, 179, 179)");
        } else {
            $(this).css("background-color", "rgb(0, 170, 238)");
        }
    });
    $("#detail").on("click", ".users .userDiv .follow", function () {
        var followTemp = $(this);
        if ($(this).html() == "取消关注") {
            $.ajax({
                url: "/cancelFollow",
                data: "starId=" + $(this).attr("data-id") + "&followerId=" + localStorage.userId,
                success: function (data) {
                    if (data == "1") {
                        if ("" == urlUserId || undefined == urlUserId || null == urlUserId) {
                            if ($(followTemp).attr("data-isStar") == "1") {
                                $(followTemp).parent().remove();
                            }
                        } else {
                            $(followTemp).css("background-color", "rgb(0, 170, 238)");
                            $(followTemp).html("关注");
                        }
                    } else {
                        alert("取消关注失败");
                    }
                }
            });
        } else {
            $.ajax({
                url: "/followSomeone",
                data: "starId=" + $(this).attr("data-id") + "&followerId=" + localStorage.userId,
                success: function (data) {
                    if (data == "0") {
                        $(followTemp).css("background-color", "rgb(0, 170, 238)");
                        $(followTemp).html("取消关注");
                    } else {
                        alert("关注失败");
                    }
                }
            })
        }
    });


});

//获取文件对象的url
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

//初始化页面的用户信息
function initUserInfo(userId) {
    $.ajax({
        url: "/initUserInfo?userId=" + userId,
        success: function (data) {
            var userInfo = JSON.parse(data);
            $("#headImg").attr("style", "background-image:url('../" + userInfo.headImgPath + "');");
            $("#userName").html(userInfo.userName);
            $("#school").html(userInfo.school);
            $("#remark").html(userInfo.remark);
            $("#imgUserId").val(userId);

            $("#achievement")
        }
    });
}

//初始化该用户的历史记录
function initUserHistoryActivity() {

}

function initHistory() {

}

//初始化该用户发起的活动
function initCreateActivity() {
    $.ajax({
        url: "getCreateActivity?userId=" + localStorage.userId,
        success: function (data) {

        }
    })
}

//初始化该用户参与的活动
function initJoinActivity() {
    $.ajax({
        url: "getJoinActivity?userId=" + localStorage.userId,
        success: function (data) {
            var activity = $("<div class='activity'></div>");
            activity.html("<div></div>");
        }
    })
}

function getFollower(urlUserId) {
    $("#followers").css("color", "rgb(0, 170, 238)");
    $("#stars").css("color", "rgb(178, 178, 178)");
    if (urlUserId == "" || urlUserId == undefined || urlUserId == null) {
        initFollow("follow", localStorage.userId);
    } else {
        initFollow("follow", urlUserId);
    }
}

function getStar(urlUserId) {
    $("#followers").css("color", "rgb(178, 178, 178)");
    $("#stars").css("color", "rgb(0, 170, 238)");
    if (urlUserId == "" || urlUserId == undefined || urlUserId == null) {
        initFollow("star", localStorage.userId);
    } else {
        initFollow("star", urlUserId);
    }
}

//该用户的关注信息
function initFollow(followType, urlUserId) {
    $(".users").remove();
    $(".hint").remove();
    if (followType == "star") {
        $.ajax({
            url: "getStar?userId=" + localStorage.userId + "&urlUserId=" + urlUserId,
            success: function (data) {
                if (data.length == 0) {
                    if (urlUserId == localStorage.userId) {
                        $("#detail").append("<span class='hint'>您还没有关注其他人</span>");
                    } else {
                        $("#detail").append("<span class='hint'>该用户还没有关注其他人</span>");
                    }
                } else {
                    var users = $("<div class='users'></div>");
                    $.each(data, function (idx, item) {
                        var user = $("<div class='userDiv'></div>");
                        user.html(
                            "<div class='headImg' data-img='" + item.headImgPath + "'></div>" +
                            "<span class='userName'>" + item.userName + "</span>" +
                            "<span class='remark'>" + item.remark + "</span>" +
                            "<div class='follow' data-id='" + item.userId + "' data-isFollow='" + item.isFollow + "' data-isStar='1'></div>"
                        );
                        $(users).append($(user));
                    });
                    $("#detail").append($(users));
                    /*$.each($("#detail .users .userDiv .headImg"), function (idx, item) {
                        $(item).css("style", "background-image:url('../" + $(item).attr("data-img") + "');");
                    });*/
                    $.each($("#detail .users .userDiv .follow"), function (idx, item) {
                        if ($(item).attr("data-isFollow") == "1") {
                            $(this).css("background-color", "rgb(179, 179, 179)");
                            $(this).html("取消关注");
                        } else {
                            $(this).css("background-isFollow", "rgb(0, 170, 238)");
                            $(this).html("关注");
                        }
                    });
                }
            }
        })
    } else {
        $.ajax({
            url: "getFollower?userId=" + localStorage.userId + "&urlUserId=" + urlUserId,
            success: function (data) {
                if (data.length == 0) {
                    if (urlUserId == localStorage.userId) {
                        $("#detail").append("<span class='hint'>您还没有关注者</span>");
                    } else {
                        $("#detail").append("<span class='hint'>该用户还没有关注者</span>");
                    }
                } else {
                    var users = $("<div class='users'></div>");
                    $.each(data, function (idx, item) {
                        var user = $("<div class='userDiv'></div>");
                        user.html(
                            "<div class='headImg'></div>" +
                            "<span class='userName'>" + item.userName + "</span>" +
                            "<span class='remark'>" + item.remark + "</span>" +
                            "<div class='follow' data-id='" + item.userId + "' data-isFollow='" + item.isFollow + "' data-isStar='0'></div>"
                        );
                        $(users).append($(user));
                    });
                    $("#detail").append($(users));
                    $.each($("#detail .users .userDiv .follow"), function (idx, item) {
                        if ($(item).attr("data-isFollow") == "1") {
                            $(this).css("background-color", "rgb(179, 179, 179)");
                            $(this).html("取消关注");
                        } else {
                            $(this).css("background-color", "rgb(0, 170, 238)");
                            $(this).html("关注");
                        }
                    });
                }
            }
        })
    }
}

//标签收藏页面数据
function initHobby(urlUserId) {
    $.ajax({
        url: "getTagByUserId?userId=" + localStorage.userId + "&urlUserId=" + urlUserId,
        success: function (data) {
            if (data.length == 0) {
                if (urlUserId == localStorage.userId) {
                    $("#detail").append("<span class='hint'>您还没有收藏标签</span>");
                } else {
                    $("#detail").append("<span class='hint'>该用户还没有收藏标签</span>");
                }
            } else {
                var tags = $("<div class='tags'></div>");
                $.each(data, function (idx, item) {
                    var tag = $("<div class='tagDiv'></div>");
                    tag.html(
                        "<div class='tagName'>" + item.tagName + "</div>" +
                        "<div class='star' data-id='" + item.tagId + "' data-isStar='" + item.isStared + "'></div>"
                    );
                    $(tags).append($(tag));
                });
                $("#detail").append($(tags));
                $.each($("#detail .tags .tagDiv .star"), function (idx, item) {
                    if ($(item).attr("data-isStar") == "1") {
                        $(this).css("background-color", "rgb(179, 179, 179)");
                        $(this).html("取消收藏");
                    } else {
                        $(this).css("background-color", "rgb(0, 170, 238)");
                        $(this).html("收藏");
                    }
                });
            }
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
                $("#edit").html("已关注");
                $("#edit").css("background-color", "rgb(179,179,179)");
                $("#edit").css("border", "rgb(179,179,179)");
                $("#edit").mouseover(function () {
                    $("#edit").html("取消关注");
                });
                $("#edit").mouseleave(function () {
                    $("#edit").html("已关注");
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
                $("#edit").html("关注");
            } else {
                $("#edit").html("已关注");
                $("#edit").css("background-color", "rgb(179,179,179)");
                $("#edit").css("border", "rgb(179,179,179)");
                $("#edit").mouseover(function () {
                    $("#edit").html("取消关注");
                });
                $("#edit").mouseleave(function () {
                    $("#edit").html("已关注");
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
                $("#edit").html("关注");
                $("#edit").css("background-color", "rgba(0, 170, 238, 1)");
                $("#edit").css("border", "rgba(0, 170, 238, 1)");
                $("#edit").unbind("mouseover");
                $("#edit").unbind("mouseleave");
            } else {
                alert("取消关注失败！");
            }
        }
    });
}