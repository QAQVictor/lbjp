package com.personal.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 21:20 2018/6/19 0019
 * @Description:
 */
public class FollowUserInfoVO {
    private String userId;
    private String userName;
    private String headImgPath;
    private String remark;
    private int isFollow;//1已经关注 0未关注

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }
}
