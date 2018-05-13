package com.user.model.DO;

/**
 * @Author: 李亚卿
 * @Date: Created in 20:45 2018/4/10 0010
 * @Description: 用户类
 */
public class UserDO {
    private String userId;
    private String userName;
    private String password;
    private String realName;
    private String gender;
    private String email;
    private String phone;
    private String remark;//签名
    private String headImgPath;//头像路径
    private String school;//学校名
    private int cancelNum;//取消次数
    private int breakNum;//失约次数
    private String createDate;//注册时间

    //待添加
    /*private String wechat;
    private String certification;
    private String idCard;
    private String leader;//是否是领头人
    */

    public UserDO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDO(){

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(int cancelNum) {
        this.cancelNum = cancelNum;
    }

    public int getBreakNum() {
        return breakNum;
    }

    public void setBreakNum(int breakNum) {
        this.breakNum = breakNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
