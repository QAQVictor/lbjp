package com.personal.model.DO;

/**
 * @Author: 李亚卿
 * @Date: Created in 19:09 2018/6/18 0018
 * @Description:
 */
public class CreditDO {
    private String userId;
    private String activityId;
    private String creditType;
    private String createDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
