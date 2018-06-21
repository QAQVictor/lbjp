(function () {
    /*
     * 用于记录日期，显示的时候，根据dateObj中的日期的年月显示
     */
    var dateObj = (function () {
        var _date = new Date();    // 默认为当前系统时间
        return {
            getDate: function () {
                return _date;
            },
            setDate: function (date) {
                _date = date;
            }
        };
    })();

    // 设置calendar div中的html部分
    renderHtml();
    // 表格中显示日期
    showCalendarData();
    // 绑定事件
    bindEvent();

    /**
     * 渲染html结构
     */
    function renderHtml() {
        var calendar = document.getElementById("calendar");
        var titleBox = document.createElement("div");  // 标题盒子 设置上一月 下一月 标题
        var bodyBox = document.createElement("div");  // 表格区 显示数据

        // 设置标题盒子中的html
        titleBox.className = 'calendar-title-box';
        titleBox.innerHTML = "<span class='prev-month' id='prevMonth'></span>" +
            "<span class='calendar-title' id='calendarTitle'></span>" +
            "<span id='nextMonth' class='next-month'></span>";
        calendar.appendChild(titleBox);    // 添加到calendar div中

        // 设置表格区的html结构
        bodyBox.className = 'calendar-body-box';
        var _headHtml = "<tr>" +
            "<th>日</th>" +
            "<th>一</th>" +
            "<th>二</th>" +
            "<th>三</th>" +
            "<th>四</th>" +
            "<th>五</th>" +
            "<th>六</th>" +
            "</tr>";
        var _bodyHtml = "";

        // 一个月最多31天，所以一个月最多占6行表格
        for (var i = 0; i < 6; i++) {
            _bodyHtml += "<tr>" +
                "<td></td>" +
                "<td></td>" +
                "<td></td>" +
                "<td></td>" +
                "<td></td>" +
                "<td></td>" +
                "<td></td>" +
                "</tr>";
        }
        bodyBox.innerHTML = "<table id='calendarTable' class='calendar-table'>" +
            _headHtml + _bodyHtml +
            "</table>";
        // 添加到calendar div中
        calendar.appendChild(bodyBox);
    }

    /**
     * 表格中显示数据，并设置类名
     */
    function showCalendarData() {
        var _year = dateObj.getDate().getFullYear();
        var _month = dateObj.getDate().getMonth() + 1;
        var _dateStr = getDateStr(dateObj.getDate());

        // 设置顶部标题栏中的 年、月信息
        var calendarTitle = document.getElementById("calendarTitle");
        var titleStr = _dateStr.substr(0, 4) + "年" + _dateStr.substr(4, 2) + "月";
        calendarTitle.innerText = titleStr;

        // 设置表格中的日期数据
        var _table = document.getElementById("calendarTable");
        var _tds = _table.getElementsByTagName("td");
        var _firstDay = new Date(_year, _month - 1, 1);  // 当前月第一天
        for (var i = 0; i < _tds.length; i++) {
            var _thisDay = new Date(_year, _month - 1, i + 1 - _firstDay.getDay());
            var _thisDayStr = getDateStr(_thisDay);
            _tds[i].innerText = _thisDay.getDate();
            _tds[i].data = _thisDayStr;
            _tds[i].setAttribute('data', _thisDayStr);
            if (_thisDayStr == getDateStr(new Date())) {    // 当前天
                _tds[i].className = 'currentDay';
            } else if (_thisDayStr.substr(0, 6) == getDateStr(_firstDay).substr(0, 6)) {
                _tds[i].className = 'currentMonth';  // 当前月
            } else {    // 其他月
                _tds[i].className = 'otherMonth';
            }
        }
    }

    /**
     * 绑定上个月下个月事件
     */
    function bindEvent() {
        var prevMonth = document.getElementById("prevMonth");
        var nextMonth = document.getElementById("nextMonth");
        addEvent(prevMonth, 'click', toPrevMonth);
        addEvent(nextMonth, 'click', toNextMonth);
        /*var table = document.getElementById("calendarTable");
        var tds = table.getElementsByTagName('td');
        for(var i = 0; i < tds.length; i++) {
            addEvent(tds[i], 'click', function(e){
                console.log(e.target.getAttribute('data'));
            });
        }*/
    }

    /**
     * 绑定事件
     */
    function addEvent(dom, eType, func) {
        if (dom.addEventListener) {  // DOM 2.0
            dom.addEventListener(eType, function (e) {
                func(e);
            });
        } else if (dom.attachEvent) {  // IE5+
            dom.attachEvent('on' + eType, function (e) {
                func(e);
            });
        } else {  // DOM 0
            dom['on' + eType] = function (e) {
                func(e);
            }
        }
    }

    /**
     * 点击上个月图标触发
     */
    function toPrevMonth() {
        var date = dateObj.getDate();
        dateObj.setDate(new Date(date.getFullYear(), date.getMonth() - 1, 1));
        showCalendarData();
    }

    /**
     * 点击下个月图标触发
     */
    function toNextMonth() {
        var date = dateObj.getDate();
        dateObj.setDate(new Date(date.getFullYear(), date.getMonth() + 1, 1));
        showCalendarData();
    }

    /**
     * 日期转化为字符串， 4位年+2位月+2位日
     */
    function getDateStr(date) {
        var _year = date.getFullYear();
        var _month = date.getMonth() + 1;    // 月从0开始计数
        var _d = date.getDate();

        _month = (_month > 9) ? ("" + _month) : ("0" + _month);
        _d = (_d > 9) ? ("" + _d) : ("0" + _d);
        return _year + _month + _d;
    }
})();

$(function () {
    $(".currentDay,.currentMonth").click(function () {
        var date = formateDate($(this).attr("data"));
        //console.log(date);
        getTodayActivity(date);
        $("#hideDiv").show();
        $("#todaySchedule").show();
    });

    $("#close").click(function () {
        $("#hideDiv").hide();
        $("#todaySchedule").hide();
        $(".activityDetail").remove();
        $(".hint").remove();
    });
});

function formateDate(date) {
    return date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6, 2);
}

function getTime(date) {
    return date.substr(11, 5);
}

function getAllActivityInfo(date) {
    $.ajax({
        url: "getAllActivityInfo",
        data: "userId" + localStorage.userId + "&date=" + date,
        success: function (data) {

        }
    })
}

function getTodayActivity(date) {
    $.ajax({
        url: "getTodayActivity",
        data: "userId=" + localStorage.userId + "&date=" + date,
        success: function (data) {
            if (data.createActivityNum == 0) {
                $("#mineActivity").append("<span class='hint' style='font-size: 16px;color: #999999;text-align: center;display: inline-block;width: 560px;height: 170px;line-height: 200px;'>这天您未发起过活动</span>");
            } else {
                $.each(data.createActivity, function (idx, item) {
                    var activityDetail = $("<div class='activityDetail'></div>");
                    activityDetail.html(
                        "<div class='activityInfo'>" +
                        "<span>" + getTime(item.startDate) + "-" + getTime(item.endDate) + "</span>" +
                        "<span id='" + item.tagId + "'>标签：" + item.tagName + "</span>" +
                        "<span>报名/最大：" + item.actualNum + "/" + item.plannedNum + "</span>" +
                        "<span>发起人：" + item.userName + "</span>" +
                        "<span>主题：" + item.theme + "</span>" +
                        "<span class='followerInfo'>具体参与者信息</span>" +
                        "</div>"
                    );
                    $("#mineActivity").append($(activityDetail));

                    var operations = $("<div class='operations' id='" + item.activityId + "'></div>");
                    var nowTimeStamp = Date.parse(new Date());
                    if (Date.parse(new Date(item.startDate)) > nowTimeStamp) {
                        operations.html(
                            "<div class='notice' onclick='noticeAll()'>一键通知</div>" +
                            "<div class='cancel' onclick='cancel()'>取消活动</div>"
                        );
                    } else if (Date.parse(new Date(item.endDate)) >= nowTimeStamp) {
                        operations.html(
                            "<div class='unable'>活动已开始</div>"
                        );
                    } else if (Date.parse(new Date(item.endDate)) < nowTimeStamp) {
                        operations.html(
                            "<div class='unable'>活动已结束</div>"
                        )
                    }
                    if (item.invalided == "6") {
                        operations.html(
                            "<div class='unable'>活动已取消</div>"
                        )
                    }
                    $(activityDetail).append($(operations));
                });
            }
            if (data.joinActivityNum == 0) {
                $("#othersActivity").append("<span class='hint' style='font-size: 16px;color: #999999;text-align: center;display: inline-block;width: 560px;height: 200px;line-height: 200px;'>这天您未报名过活动</span>");
            } else {
                $.each(data.joinActivity, function (idx, item) {
                    var activityDetail = $("<div class='activityDetail'></div>");
                    activityDetail.html(
                        "<div class='activityInfo'>" +
                        "<span>" + getTime(item.startDate) + "-" + getTime(item.endDate) + "</span>" +
                        "<span id='" + item.tagId + "'>标签：" + item.tagName + "</span>" +
                        "<span>报名/最大：" + item.actualNum + "/" + item.plannedNum + "</span>" +
                        "<span>发起人：" + item.userName + "</span>" +
                        "<span>主题：" + item.theme + "</span>" +
                        "<span class='followerInfo'>具体参与者信息</span>" +
                        "</div>"
                    );
                    $("#othersActivity").append($(activityDetail));
                    var operations = $("<div class='operations' id='" + item.activityId + "'></div>");
                    var nowTimeStamp = Date.parse(new Date());
                    if (Date.parse(new Date(item.startDate)) > nowTimeStamp) {
                        operations.html(
                            "<div class='cancel' onclick='breakUp()'>无法参加</div>"
                        );
                    } else if (Date.parse(new Date(item.endDate)) >= nowTimeStamp) {
                        operations.html(
                            "<div class='unable'>活动已开始</div>"
                        );
                    } else if (Date.parse(new Date(item.endDate)) < nowTimeStamp) {
                        operations.html(
                            "<div class='unable'>活动已结束</div>"
                        )
                    }
                    if (item.invalided == "6") {
                        operations.html(
                            "<div class='unable'>活动已取消</div>"
                        )
                    } else if (item.invalided == "10") {
                        operations.html(
                            "<div class='unable'>已失约</div>"
                        )
                    }
                    $(activityDetail).find(".activityInfo").after($(operations));
                });
            }
        }
    })
}

function noticeAll() {
    var activityId = $(".notice").parent().attr("id");
    $.ajax({
        url: "noticeAll",
        data: "activity=" + activityId,
        success: function (data) {
            if (data == "1") {

            } else {

            }
        }
    })
}

function cancel() {
    var activityId = $(this).parent().attr("id");
    alert(activityId);
    var confirm = confirm("您确定取消此次活动吗？（取消活动的记录会记录在个人主页，会影响您的信用记录。）");
    if (confirm == true) {
        $.ajax({
            url: "cancelActivity",
            data: "",
            success: function (data) {
                if (data == "1") {

                } else {

                }
            }
        })
    }
}

function breakUp() {
    var activityId = $(".cancel").parent().attr("id");
    if (confirm("您确定取消此次活动吗？（取消活动的记录会记录在个人主页，会影响您的信用记录。）")) {
        $(".cancel").removeAttr('onclick');
        //3495-4094
        $.ajax({
            url: "breakUpActivity",
            data: "activityId=" + activityId + "&userId=" + localStorage.userId,
            success: function (data) {
                if (data.state == "0") {
                    $(".cancel").css("background-color", "rgb(179,179,179)");
                    $(".cancel").css("color", "rgb(255,255,255)");
                    $(".cancel").css("border-color", "rgb(179,179,179)");
                    $(".cancel").html("已取消");
                } else {
                    alert("取消失败，请及时和发起者联系。联系者邮箱：“" + data.creatorEmail + "”");
                }
            }
        })
    }
}
