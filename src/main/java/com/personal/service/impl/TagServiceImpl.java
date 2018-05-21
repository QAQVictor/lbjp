package com.personal.service.impl;

import com.personal.dao.TagMapper;
import com.personal.model.DO.TagDO;
import com.personal.model.VO.TagVO;
import com.personal.service.TagService;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 李亚卿
 * @Date: Created in 10:59 2018/5/15 0015
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public boolean saveTag(TagDO tag) {
        if (null == tagMapper.getIsName(tag.getTagName())) {
            tagMapper.insert(tag);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TagVO> getLikeName(String tagName) {
        return tagMapper.getLikeName(tagName);
    }

    @Override
    public TagVO getTag(String tagName, String userId) {
        if (null == tagMapper.getIsName(tagName)) {
            TagDO tag = new TagDO();
            tag.setCreateDate(DateUtils.getNowDate());
            tag.setCreator(userId);
            tag.setTagName(tagName);
            tagMapper.insert(tag);
            return new TagVO(tag.getTagId(), tag.getTagName());
        } else {
            return tagMapper.getIsName(tagName);
        }
    }
}
