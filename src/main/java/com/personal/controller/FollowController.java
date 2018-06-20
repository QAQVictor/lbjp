package com.personal.controller;

import com.personal.model.VO.FollowVO;
import com.personal.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 08:50 2018/6/19 0019
 * @Description:
 */

@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @RequestMapping("followSomeone")
    @ResponseBody
    public String followSomeone(FollowVO followVO) {
        return String.valueOf(followService.follow(followVO));
    }

    @RequestMapping("judgeFollow")
    @ResponseBody
    public String judgeFollow(FollowVO followVO) {
        return String.valueOf(followService.judgeFollow(followVO));
    }

    @RequestMapping("cancelFollow")
    @ResponseBody
    public String cancelFollow(FollowVO followVO) {
        return String.valueOf(followService.cancelFollow(followVO));
    }

    @RequestMapping("getFollowNum")
    @ResponseBody
    public Map getFollowNum(String userId) {
        return followService.getFollowNum(userId);
    }

    /**
     *
     * @param userId
     * @param urlUserId
     * @return
     */
    @RequestMapping("getStar")
    @ResponseBody
    public List getStar(String userId, String urlUserId) {
        return followService.getStar(userId, urlUserId);
    }

    /**
     * 获取跟随者
     * @param userId
     * @param urlUserId
     * @return
     */
    @RequestMapping("getFollower")
    @ResponseBody
    public List getFollower(String userId, String urlUserId) {
        return followService.getFollower(userId, urlUserId);
    }
}
