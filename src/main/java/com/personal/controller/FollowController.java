package com.personal.controller;

import com.personal.model.VO.FollowVO;
import com.personal.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public int followSomeone(FollowVO followVO) {
        return followService.save(followVO);
    }

    @RequestMapping("judgeFollow")
    @ResponseBody
    public int judgeFollow(FollowVO followVO) {
        return followService.judgeFollow(followVO);
    }

    @RequestMapping("cancelFollow")
    @ResponseBody
    public int cancelFollow(FollowVO followVO) {
        return followService.cancelFollow(followVO);
    }
}
