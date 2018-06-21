package com.personal.service.impl;

import com.personal.dao.TagMapper;
import com.personal.model.DO.HistoryDO;
import com.personal.model.DO.TagDO;
import com.personal.model.VO.HobbyPageVO;
import com.personal.model.VO.StarTagVO;
import com.personal.model.VO.TagVO;
import com.personal.service.HistoryService;
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
    private TagMapper tagMapper;
    @Autowired
    private HistoryService historyService;

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

    @Override
    public int starTag(StarTagVO starTagVO) {
        if (judgeStar(starTagVO) == 0) {
            historyService.save(new HistoryDO(starTagVO.getUserId(),
                    starTagVO.getTagId(),
                    null,
                    null,
                    DateUtils.getNowDate(), 2));
            tagMapper.starTag(starTagVO);
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int judgeStar(StarTagVO starTagVO) {
        return tagMapper.judgeStar(starTagVO);
    }

    @Override
    public int cancelStar(StarTagVO starTagVO) {
        if (judgeStar(starTagVO) >= 1) {
            tagMapper.delete(starTagVO);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<HobbyPageVO> getTagByUserId(String userId, String urlUserId) {
        List<HobbyPageVO> list = tagMapper.getTagByUserId(urlUserId);
        if (userId.equals(urlUserId)) {
            for (HobbyPageVO hobbyPageVO : list) {
                hobbyPageVO.setIsStared(1);
            }
        } else {
            for (HobbyPageVO hobbyPageVO : list) {
                hobbyPageVO.setIsStared(judgeStar(new StarTagVO(userId, hobbyPageVO.getTagId())) >= 1 ? 1 : 0);
            }
        }
        return list;
    }

    @Override
    public int getTagNumByUserId(String userId) {
        return tagMapper.getTagNumByUserId(userId);
    }
}
