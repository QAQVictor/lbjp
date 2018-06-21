package com.society.controller;

import com.personal.service.TagService;
import com.society.model.DO.ActivityDO;
import com.society.model.VO.ScheduleActivityVO;
import com.society.service.ActivityService;
import com.user.model.DO.UserDO;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        //map.put("tagList", );
        map.put("activityList", activityService.getAllActivity());
        return map;
    }

    /**
     * 添加一个活动
     *
     * @param activity
     * @param tagName
     * @param request
     * @return 0添加成功 5该时间段已经有其他安排
     */
    @RequestMapping("/addActivity")
    @ResponseBody
    public Map<String, Object> addActivity(ActivityDO activity, String tagName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        activity.setTagId(tagService.getTag(tagName, activity.getCreator()).getTagId());
        map.put("state", activityService.saveActivity(activity));
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

    /**
     * 报名，成功向前端传0，过期向前端传1，未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5
     *
     * @param userId
     * @param activityId
     */
    @RequestMapping("/join")
    @ResponseBody
    public String join(String userId, String activityId) {
       /* UserDO user = (UserDO) request.getSession().getAttribute("user");
        if (!user.getUserId().equals(userId)) {
            return;
        }*/
        return String.valueOf(activityService.join(userId, activityId));
    }

    /**
     * 可报名向前端传0，报名过期向前端传1，报名未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5
     *
     * @param userId
     * @param activityId
     */
    @RequestMapping("judgeJoin")
    @ResponseBody
    public String judgeJoin(String userId, String activityId) {
        return String.valueOf(activityService.judgeJoin(userId, activityId));
    }

    @RequestMapping("getTodayActivity")
    @ResponseBody
    public Map<String, Object> getTodayActivity(String userId, String date) {
        Map<String, Object> map = new HashMap<>();
        List<ScheduleActivityVO> joinActivity = activityService.getJoinActivityByDay(userId, date);
        map.put("joinActivityNum", joinActivity.size());
        map.put("joinActivity", joinActivity);
        List<ScheduleActivityVO> createActivity = activityService.getCreateActivityByDay(userId, date);
        map.put("createActivity", createActivity);
        map.put("createActivityNum", createActivity.size());
        return map;
    }

    @RequestMapping("noticeAll")
    @ResponseBody
    public Map<String, Object> noticeAll(String activityId) {
        return activityService.noticeAll(activityId);
    }

    @RequestMapping("cancelActivity")
    @ResponseBody
    public Map<String, Object> cancelActivity(String activityId) {
        return activityService.cancelActivity(activityId);
    }

    @RequestMapping("breakUpActivity")
    @ResponseBody
    public Map<String, Object> breakUpActivity(String activityId, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("state", String.valueOf(activityService.breakUpActivity(userId, activityId)));
        map.put("creatorEmail", activityService.getCreatorEmail(activityId));
        return map;
    }

    @RequestMapping("getComment")
    @ResponseBody
    public String getComment(String activityId) {
        return null;
    }

    @RequestMapping("addComment")
    @ResponseBody
    public String addComment() {
        return null;
    }

    @RequestMapping("getCreateActivityNum")
    @ResponseBody
    public int getCreateActivityNum(String userId) {
        return activityService.getCreateActivityNum(userId);
    }

    @RequestMapping("getJoinActivity")
    @ResponseBody
    public Map<String, Object> getJoinActivity(String userId) {
        System.out.println(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("joinActivityList", activityService.getJoinActivity(userId));
        return map;
    }

    @RequestMapping("getCreateActivity")
    @ResponseBody
    public Map<String, Object> getCreateActivity(String userId) {
        System.out.println(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("createActivityList", activityService.getCreateActivity(userId));
        return map;
    }
}
