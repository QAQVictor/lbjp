package com.personal.model.VO;

/**
 * @Author: 李亚卿
 * @Date: Created in 12:11 2018/5/15 0015
 * @Description:
 */
public class TagVO {
    private int tagId;//标签id
    private String tagName;//标签名

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

    @Override
    public String toString() {
        return "{'tagId':'" + tagId + "', 'tagName':'" + tagName + '}';
    }
}
