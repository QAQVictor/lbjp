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

    //initUserHistoryActivity();

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

    $("#label ul li").attr("data-isSelect", 0);

    //通过各种方式进入home页面
    switch (localStorage.goTo) {
        case "6":
            click6();
            localStorage.goTo = 0;
            break;
        case "5":
            click5(urlUserId, 1);
            localStorage.goTo = 0;
            break;
        case "4":
            click4(urlUserId);
            localStorage.goTo = 0;
            break;
        case "3":
            click3();
            localStorage.goTo = 0;
            break;
        case "2":
            click2();
            localStorage.goTo = 0;
            break;
        case "1":
            click1();
            localStorage.goTo = 0;
            break;
        default:
            click1();
            localStorage.goTo = 0;
            break;
    }

    //点击“最近”1
    $("#recently").click(function () {
        click1();
    });
    //点击“发起”2
    $("#myActivity").click(function () {
        click2();
    });
    //点击“参与”3
    $("#joinedActivity").click(function () {
        click3();
    });
    //点击收藏标签4
    $("#hobby").click(function () {
        click4(urlUserId);
    });
    //点击“关注”5
    $("#follow").click(function () {
        click5(urlUserId, 1);
    });
    //点击“日程”6
    $("#schedule").click(function () {
        click6();
    });

    //点击右边关注者
    $("#follower").click(function () {
        click5(urlUserId, 1);
    });
    //点击右边关注了
    $("#star").click(function () {
        click5(urlUserId, 2);
    });
    //点击左边关注者
    $("#detail").on("click", "#followers", function () {
        getFollower(urlUserId);
    });
    //点击左边关注了
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
                            } else {
                                $(followTemp).css("background-color", "rgb(0, 170, 238)");
                                $(followTemp).html("关注");
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

//蓝色粗线条移动
function drawLine(e) {
    $(e).css("border-bottom", "3px solid rgb(0, 170, 238)");
    $(e).css("color", "rgb(0, 170, 238)");
    $(e).css("font-weight", "bold");

    $(e).siblings("li").css("border-bottom", "0px solid");
    $(e).siblings("li").css("color", "rgb(0,0,0)");
    $(e).siblings("li").css("font-weight", "normal");
    $(e).siblings("li").attr("data-isSelect", 0);
}

function click1() {
    if ($("#recently").attr("data-isSelect") == 0) {
        $("#detail").empty();
        drawLine($("#recently"));
        localStorage.recently = 1;
        $("#detail").load("/recently");

    }
    $("#recently").attr("data-isSelect", 1);
}

function click2() {
    if ($("#myActivity").attr("data-isSelect") == 0) {
        $("#detail").empty();
        drawLine($("#myActivity"));
        localStorage.recently = 2;
        $("#detail").load("/recently");

    }
    $("#myActivity").attr("data-isSelect", 1);
}

function click3() {
    if ($("#joinedActivity").attr("data-isSelect") == 0) {
        $("#detail").empty();
        drawLine($("#joinedActivity"));
        localStorage.recently = 3;
        $("#detail").load("/recently");

    }
    $("#joinedActivity").attr("data-isSelect", 1);
}

function click4(urlUserId) {
    if ($("#hobby").attr("data-isSelect") == 0) {
        $("#detail").empty();
        drawLine($("#hobby"));
        if (urlUserId == "" || urlUserId == undefined || urlUserId == null) {
            initHobby(localStorage.userId);
        } else {
            initHobby(urlUserId);
        }
    }
    $("#hobby").attr("data-isSelect", 1);
}

function click5(urlUserId, type) {
    $("#detail").empty();
    drawLine($("#follow"));
    var select = $("<ul id='selectUl'></ul>");
    select.html(
        "<li id='followers'>关注者</li>" +
        "<li id='stars'>关注了</li>"
    );
    $("#detail").append($(select));
    if (type == 1) {
        getFollower(urlUserId);
    } else {
        getStar(urlUserId);
    }
}

function click6() {
    if ($("#schedule").attr("data-isSelect") == 0) {
        $("#detail").empty();
        drawLine($("#schedule"));
        $("#detail").load("/schedule");
    }
    $("#schedule").attr("data-isSelect", 1);
}

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
    //基本个人信息初始化
    $.ajax({
        url: "/initUserInfo?userId=" + userId,
        success: function (data) {
            var userInfo = JSON.parse(data);
            $("#headImg").attr("style", "background-image:url('../" + userInfo.headImgPath + "');");
            $("#userName").html(userInfo.userName);
            $("#school").html(userInfo.school);
            $("#remark").html(userInfo.remark);
            $("#imgUserId").val(userId);
        }
    });
    //关注人数初始化
    $.ajax({
        url: "getFollowNum?userId=" + userId,
        success: function (data) {
            $("#star").html(data.starNum);
            $("#follower").html(data.followerNum);
        }
    });
    //成就初始化
    $.ajax({
        url: "getCreateActivityNum?userId=" + userId,
        success: function (data) {
            $("#achievement").find("span").eq(1).html("发起活动 <p style='color: rgb(0, 170, 238);font-weight: bold;display: inline;'>" + data + "</p> 次");
        }
    });
    $.ajax({
        url: "getTagNumByUserId?userId=" + userId,
        success: function (data) {
            $("#achievement").find("span").eq(2).html("创建标签 <p style='color: rgb(0, 170, 238);font-weight: bold;display: inline;'>" + data + "</p> 个");
        }
    })

    //信用记录初始化
    $.ajax({
        url: "getCreditNum?userId=" + userId,
        success: function (data) {
            $("#cancelNum").find("span").eq(1).find("span").eq(0).html(data.cancelNum);
            $("#cancelNum").find("span").eq(1).append("/" + data.createNum);
            $("#breakNum").find("span").eq(1).find("span").eq(0).html(data.breakNum);
            $("#breakNum").find("span").eq(1).append("/" + data.entryNum);
        }
    })
}

//获取关注者
function getFollower(urlUserId) {
    $("#followers").css("color", "rgb(0, 170, 238)");
    $("#stars").css("color", "rgb(178, 178, 178)");
    if (urlUserId == "" || urlUserId == undefined || urlUserId == null) {
        initFollow("follow", localStorage.userId);
    } else {
        initFollow("follow", urlUserId);
    }
}

//获取关注人
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
                            "<img class='headImg' src='../" + item.headImgPath + "'/>" +
                            "<span class='userName'>" + item.userName + "</span>" +
                            "<span class='remark'>" + item.remark + "</span>" +
                            "<div class='follow' data-id='" + item.userId + "' data-isFollow='" + item.isFollow + "' data-isStar='1'></div>"
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
                            "<img class='headImg' src='../" + item.headImgPath + "'/>" +
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