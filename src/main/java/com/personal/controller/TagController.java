package com.personal.controller;

import com.personal.model.VO.StarTag;
import com.personal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 李亚卿
 * @Date: Created in 11:02 2018/5/15 0015
 * @Description:
 */
@Controller
public class TagController {
    @Autowired
    TagService tagService;

    /**
     * 按照标签名查找类似标签
     *
     * @param tagName
     * @return [{'tagId':'...','tagName':'...'},...]
     */
    @RequestMapping("/getTagLikeName")
    @ResponseBody
    public String getTagLikeName(String tagName) {
        return tagService.getLikeName(tagName).toString();
    }

    @RequestMapping("starTag")
    @ResponseBody
    public String starTag(StarTag starTag) {
        System.out.println(starTag.getTagId() + "              " + starTag.getUserId());
        return null;
    }

    @RequestMapping("judgeStar")
    @ResponseBody
    public String judgeStar(StarTag starTag) {
        return String.valueOf(tagService.judgeStar(starTag));
    }
}
