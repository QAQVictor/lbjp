package com.personal.dao;

import com.personal.model.VO.FollowVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 李亚卿
 * @Date: Created in 08:36 2018/6/19 0019
 * @Description:
 */

@Mapper
public interface FollowMapper {
    /**
     * 添加记录
     *
     * @param followVO
     */
    void insert(FollowVO followVO);

    /**
     * 判断是是否已经关注
     *
     * @param followVO
     * @return 1已经关注，0未关注
     */
    int judgeFollow(FollowVO followVO);

    /**
     * 删除记录
     *
     * @param followVO
     */
    void delete(FollowVO followVO);
}
