package com.society.dao;

import com.society.model.VO.ActivityBaseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 李亚卿
 * @Date: Created in 16:59 2018/5/2 0002
 * @Description:
 */
@Mapper
public interface ActivityMapper {
    /**
     * 根据用户id查询主页面活动
     *
     * @param userId
     * @return
     */
    List<ActivityBaseVO> getActivityByUserId(String userId);
}
