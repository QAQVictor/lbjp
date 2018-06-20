package com.personal.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 22:00 2018/6/18 0018
 * @Description:
 */
public class StarTagVO {
    private String userId;
    private int tagId;

    public StarTagVO(String userId, int tagId) {
        this.userId = userId;
        this.tagId = tagId;
    }

    public StarTagVO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
