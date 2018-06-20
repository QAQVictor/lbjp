package com.personal.service.impl;

import com.personal.dao.FollowMapper;
import com.personal.model.VO.FollowUserInfoVO;
import com.personal.model.VO.FollowVO;
import com.personal.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 08:48 2018/6/19 0019
 * @Description:
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public int follow(FollowVO followVO) {
        if (judgeFollow(followVO) >= 1) {
            return 1;
        } else {
            followMapper.insert(followVO);
            return 0;
        }
    }

    @Override
    public int judgeFollow(FollowVO followVO) {
        return followMapper.judgeFollow(followVO);
    }

    @Override
    public int cancelFollow(FollowVO followVO) {
        if (judgeFollow(followVO) >= 1) {
            followMapper.delete(followVO);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Map<String, Object> getFollowNum(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("followerNum", followMapper.getFollowNum(userId));
        map.put("starNum", followMapper.getStarNum(userId));
        return map;
    }

    @Override
    public List<FollowUserInfoVO> getFollower(String userId, String urlUserId) {
        List<FollowUserInfoVO> list = followMapper.getFollower(urlUserId);
        //目前登录用户和关注页面展示的用户的关注关系
        for (FollowUserInfoVO followUserInfoVO : list) {
            followUserInfoVO.setIsFollow(judgeFollow(new FollowVO(userId, followUserInfoVO.getUserId())) >= 1 ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<FollowUserInfoVO> getStar(String userId, String urlUserId) {
        List<FollowUserInfoVO> list = followMapper.getStar(urlUserId);
        //目前登录用户和关注页面展示的用户的关注关系
        if (userId.equals(urlUserId)) {
            for (FollowUserInfoVO followUserInfoVO : list) {
                followUserInfoVO.setIsFollow(1);
            }
        } else {
            for (FollowUserInfoVO followUserInfoVO : list) {
                followUserInfoVO.setIsFollow(judgeFollow(new FollowVO(userId, followUserInfoVO.getUserId())) >= 1 ? 1 : 0);
            }
        }
        return list;
    }

    //目前登录用户和关注页面展示的用户的关注关系
   /* private void setFollowUserInfoVOListIsFollow(String userId, String urlUserId, List<FollowUserInfoVO> list) {
        if (userId.equals(urlUserId)) {
            for (FollowUserInfoVO followUserInfoVO : list) {
                followUserInfoVO.setIsFollow(1);
            }
        } else {
            for (FollowUserInfoVO followUserInfoVO : list) {
                followUserInfoVO.setIsFollow(judgeFollow(new FollowVO(userId, followUserInfoVO.getUserId())) >= 1 ? 1 : 0);
            }
        }
    }*/
}
