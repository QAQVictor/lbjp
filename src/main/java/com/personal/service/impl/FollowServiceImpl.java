package com.personal.service.impl;

import com.personal.dao.FollowMapper;
import com.personal.model.VO.FollowVO;
import com.personal.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int save(FollowVO followVO) {
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
}
