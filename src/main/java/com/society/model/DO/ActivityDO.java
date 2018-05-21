package com.society.model.DO;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:43 2018/5/2 0002
 * @Description:
 */
public class ActivityDO {
    private String activityId;//
    private int tagId;//
    private String creator;//
    private String content;//内容
    private String theme;//主题
    private int plannedNum;//计划参与人数
    private int actualNum;//实际人数
    private int hot;//热度，浏览量
    private String entryStartDate;
    private String entryEndDate;
    private String startDate;
    private String endDate;
    private String createDate;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getEntryStartDate() {
        return entryStartDate;
    }

    public void setEntryStartDate(String entryStartDate) {
        this.entryStartDate = entryStartDate;
    }

    public String getEntryEndDate() {
        return entryEndDate;
    }

    public void setEntryEndDate(String entryEndDate) {
        this.entryEndDate = entryEndDate;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
