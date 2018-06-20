package com.personal.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:38 2018/6/20 0020
 * @Description:
 */
public class HobbyPageVO {
    private int tagId;//标签id
    private String tagName;//标签名
    private int isStared;//当前用户是否收藏 1收藏 0未收藏

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getIsStared() {
        return isStared;
    }

    public void setIsStared(int isStared) {
        this.isStared = isStared;
    }
}
