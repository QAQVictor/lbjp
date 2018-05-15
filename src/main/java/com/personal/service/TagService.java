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
}
