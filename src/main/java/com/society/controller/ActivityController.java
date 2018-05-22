package com.society.controller;

import com.personal.model.VO.TagVO;
import com.personal.service.TagService;
import com.society.model.DO.ActivityDO;
import com.society.service.ActivityService;
import com.user.model.DO.UserDO;
import com.user.service.UserService;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 15:20 2018/5/13 0013
 * @Description:
 */

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    /**
     * 主页面展示数据
     *
     * @param request
     * @return
     */
    @RequestMapping("/getIndexActivity")
    @ResponseBody
    public Map<String, Object> getIndexActivity(HttpServletRequest request) {
        UserDO user = (UserDO) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("activityList", activityService.getAllActivity());
        return map;
    }

    /**
     * 添加一个活动
     *
     * @param activity
     * @param tagName
     * @param request
     * @return
     */
    @RequestMapping("addActivity")
    @ResponseBody
    public Map<String, Object> addActivity(ActivityDO activity, String tagName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        activity.setCreateDate(DateUtils.getNowDate());
        activity.setActivityId(DateUtils.getIDByDate(activity.getCreateDate()));
        activity.setEndDate(activity.getEndDate() + " 23:59:59");
        activity.setEntryEndDate(activity.getEntryEndDate() + " 23:59:59");
        activity.setHot(0);
        activity.setActualNum(0);
        activity.setTagId(tagService.getTag(tagName, activity.getCreator()).getTagId());
        activityService.saveActivity(activity);
        map.put("state", "true");
        map.put("activity", activity);
        return map;
    }

    /**
     * 获取活动详情
     *
     * @param activityId
     * @return
     */
    @RequestMapping("/activityDetailInfo")
    @ResponseBody
    public Map<String, Object> activityDetailInfo(String activityId) {
        return activityService.getActivity(activityId);
    }

    @RequestMapping("/index")
    public String index(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //response.getWriter().print(((UserDO) request.getSession().getAttribute("user")).getUserId());
        return "/index";
    }

}
