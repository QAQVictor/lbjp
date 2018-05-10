package com.personal.model.DO;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:51 2018/5/2 0002
 * @Description: 自定义标签
 */
public class tagDO {
    private String tagId;//标签id
    private String tagName;//标签名
    private String creator;//创建人Id;
    private String createDate;//创建时间

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
