package com.personal.service;

import com.personal.model.VO.FollowUserInfoVO;
import com.personal.model.VO.FollowVO;

import java.util.List;
import java.util.Map;

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
    int follow(FollowVO followVO);

    /**
     * 是否已关注
     *
     * @param followVO
     * @return 0未关注 1已关注
     */
    int judgeFollow(FollowVO followVO);

    /**
     * 取消关注
     *
     * @param followVO
     * @return
     */
    int cancelFollow(FollowVO followVO);

    /**
     * 获取关注人数和被关注人数
     *
     * @param userId
     * @return
     */
    Map<String, Object> getFollowNum(String userId);

    /**
     * 获取某用户的粉丝们的用户信息
     *
     * @param userId
     * @return
     */
    List<FollowUserInfoVO> getFollower(String userId, String urlUserId);

    /**
     * 获取某用户的关注的人的用户信息
     *
     * @param userId
     * @return
     */
    List<FollowUserInfoVO> getStar(String userId, String urlUserId);
}
