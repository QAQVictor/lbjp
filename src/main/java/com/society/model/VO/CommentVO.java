package com.society.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:43 2018/5/2 0002
 * @Description:
 */
public class CommentVO {
    private String activityId;//活动id
    //private String cardId;//帖子id
    private String commentator;//评论者id
    private String poster;//发帖人
    private String content;//内容
    private int agreeNum;//赞同数
    private int oppositionNum;//差评数
    private String createDate;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
    }

    public int getOppositionNum() {
        return oppositionNum;
    }

    public void setOppositionNum(int oppositionNum) {
        this.oppositionNum = oppositionNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
