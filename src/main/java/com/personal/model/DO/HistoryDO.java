package com.personal.model.DO;

/**
 * @Author: 李亚卿
 * @Date: Created in 00:21 2018/6/22 0022
 * @Description:
 */
public class HistoryDO {
    private String userId;
    private int tagId;
    private String activityId;
    private String starId;
    private String createDate;
    private int type;//1,活动 2,收藏 3,关注

    public HistoryDO(String userId, int tagId, String activityId, String starId, String createDate, int type) {
        this.userId = userId;
        this.tagId = tagId;
        this.activityId = activityId;
        this.starId = starId;
        this.createDate = createDate;
        this.type = type;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStarId() {
        return starId;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
