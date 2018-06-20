package com.personal.controller;

import com.personal.model.VO.StarTagVO;
import com.personal.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    /**
     * 收藏标签
     *
     * @param starTagVO
     * @return 1已收藏 0收藏成功
     */
    @RequestMapping("starTag")
    @ResponseBody
    public String starTag(StarTagVO starTagVO) {
        return String.valueOf(tagService.starTag(starTagVO));
    }

    /**
     * 取消收藏
     *
     * @param starTagVO
     * @return 1取消失败 0取消成功
     */
    @RequestMapping("cancelStar")
    @ResponseBody
    public String cancelTag(StarTagVO starTagVO) {
        return String.valueOf(tagService.cancelStar(starTagVO));
    }

    /**
     * 判断是否收藏标签
     *
     * @param starTagVO
     * @return
     */
    @RequestMapping("judgeStar")
    @ResponseBody
    public String judgeStar(StarTagVO starTagVO) {
        return String.valueOf(tagService.judgeStar(starTagVO));
    }

    /**
     * 获取某个用户收藏的标签
     *
     * @return
     */
    @RequestMapping("getTagByUserId")
    @ResponseBody
    public List getTagByUserId(String userId, String urlUserId) {
        return tagService.getTagByUserId(userId, urlUserId);
    }
}
