package com.personal.service;

import com.personal.model.DO.TagDO;
import com.personal.model.VO.HobbyPageVO;
import com.personal.model.VO.StarTagVO;
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


    /**
     * 收藏标签
     *
     * @param starTagVO
     */
    int starTag(StarTagVO starTagVO);

    /**
     * 取消收藏
     *
     * @param starTagVO
     * @return
     */
    int cancelStar(StarTagVO starTagVO);

    /**
     * 判断是否收藏
     *
     * @param starTagVO
     * @return 1已收藏 0未收藏
     */
    int judgeStar(StarTagVO starTagVO);

    /**
     * 获取某用户的关注的标签
     *
     * @param userId
     * @return
     */
    List<HobbyPageVO> getTagByUserId(String userId, String urlUserId);
}
