package com.personal.dao;

import com.personal.model.DO.TagDO;
import com.personal.model.VO.HobbyPageVO;
import com.personal.model.VO.StarTagVO;
import com.personal.model.VO.TagVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 李亚卿
 * @Date: Created in 22:51 2018/5/13 0013
 * @Description:
 */
@Mapper
public interface TagMapper {
    /**
     * 新建标签
     *
     * @param tag
     * @return tagId
     */
    int insert(TagDO tag);

    /**
     * 根据标签名进行模糊查询
     *
     * @param tagName
     * @return
     */
    List<TagVO> getLikeName(String tagName);

    /**
     * 根据名字查询标签（主要用于重复检验）
     *
     * @param tagName
     * @return tagId
     */
    TagVO getIsName(String tagName);

    /**
     * 收藏标签
     *
     * @param starTagVO
     */
    void starTag(StarTagVO starTagVO);

    /**
     * 判断是否收藏
     *
     * @param starTagVO
     * @return
     */
    int judgeStar(StarTagVO starTagVO);

    /**
     * 删除收藏记录
     *
     * @param starTagVO
     */
    void delete(StarTagVO starTagVO);

    /**
     * 根据用户地获取标签信息
     *
     * @param userId
     * @return
     */
    List<HobbyPageVO> getTagByUserId(String userId);

    /**
     * 用户创建的标签数量
     *
     * @param userId
     * @return
     */
    int getTagNumByUserId(String userId);
}
