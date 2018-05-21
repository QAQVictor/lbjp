package com.personal.service;

import com.personal.model.DO.TagDO;
import com.personal.model.VO.TagVO;

import java.util.List;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:57 2018/5/15 0015
 * @Description:
 */
public interface TagService {
    /**
     * 添加标签
     *
     * @param tag
     * @return
     */
    boolean saveTag(TagDO tag);

    /**
     * 模糊查询
     *
     * @param tagName
     * @return
     */
    List<TagVO> getLikeName(String tagName);

    /**
     * 查找标签。未找到则添加，否则返回找到的标签
     *
     * @param tagName
     * @param userId
     * @return
     */
    TagVO getTag(String tagName, String userId);
}
