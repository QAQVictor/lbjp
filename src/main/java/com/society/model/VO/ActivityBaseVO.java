package com.society.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 16:59 2018/5/2 0002
 * @Description:
 */
public class ActivityBaseVO {
    private String activityId;
    private String creator;
    private int tagId;//
    private String tagName;
    private String userName;
    private String remark;//签名
    private String headImgPath;//头像路径
    private String content;//内容
    private String theme;//主题
    private int commentNum;//评论数
    private int agreeNum;//赞数

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
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
}
