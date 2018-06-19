package com.personal.service;

import com.personal.model.VO.FollowVO;

/**
 * @Author: 李亚卿
 * @Date: Created in 08:47 2018/6/19 0019
 * @Description:
 */
public interface FollowService {
    /**
     * @param followVO
     * @return 0成功 1已关注
     */
    int save(FollowVO followVO);

    /**
     * 是否已关注
     *
     * @param followVO
     * @return 0未关注 1已关注
     */
    int judgeFollow(FollowVO followVO);

    int cancelFollow(FollowVO followVO);
}
