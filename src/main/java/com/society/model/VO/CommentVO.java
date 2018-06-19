package com.society.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:43 2018/5/2 0002
 * @Description:
 */
public class CommentVO {
    private String activityId;//活动id
    private String commentator;//评论者id
    private String content;//内容
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
