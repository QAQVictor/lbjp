package com.society.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 23:41 2018/6/14 0014
 * @Description:
 */
public class ScheduleActivityVO {
    private String activityId;
    private String userId;
    private String userName;
    private int tagId;
    private String tagName;
    private String theme;
    private String startDate;
    private String endDate;
    private int plannedNum;
    private int actualNum;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPlannedNum() {
        return plannedNum;
    }

    public void setPlannedNum(int plannedNum) {
        this.plannedNum = plannedNum;
    }

    public int getActualNum() {
        return actualNum;
    }

    public void setActualNum(int actualNum) {
        this.actualNum = actualNum;
    }
}
