package com.user.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 17:28 2018/5/22 0022
 * @Description: 用户id，名称，头像路径，关注人数，发起活动数
 */
public class UserBaseVO {
    private String userId;
    private String userName;
    private String headImgPath;
    private int activityNum;
    private int followNum;

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

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
    }

    public int getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(int activityNum) {
        this.activityNum = activityNum;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }
}
