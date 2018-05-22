package com.society.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 16:22 2018/5/22 0022
 * @Description:
 */
public class ActivityDetailVO {
    private String activityId;
    private int tagId;//
    private String tagName;
    private String theme;
    private String content;
    private int commentNum;//评论数
    private int agreeNum;//赞数
    private int plannedNum;
    private int actualNum;
    private int hot;//热度，浏览量
    private String entryStartDate;
    private String entryEndDate;
    private String startDate;
    private String endDate;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
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
}
