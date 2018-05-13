package com.society.controller;

import com.society.model.DO.ActivityDO;
import com.society.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 主页面展示数据
     *
     * @param request
     * @return
     */
    @RequestMapping("/getIndexActivity")
    @ResponseBody
    public Map<String, Object> getIndexActivity(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityList", activityService.getAllActivity());
        return map;
    }

    @RequestMapping("addActivity")
    public String addActivity(ActivityDO activity) {

        return "";
    }

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

}
