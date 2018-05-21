package com.personal.controller;

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

}
