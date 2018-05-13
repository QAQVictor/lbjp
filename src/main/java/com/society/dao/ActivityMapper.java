package com.society.dao;

import com.society.model.DO.ActivityDO;
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
    List<ActivityDO> getActivityByUserId(String userId);

    /**
     * @return
     */
    List<ActivityDO> getIndexActivity();

    /**
     * 所有活动
     *
     * @return
     */
    List<ActivityBaseVO> getAllActivity();

    /**
     * 添加新活动
     */
    void insert(ActivityDO activity);

    /**
     * 获取一个活动的详情
     * @param activityId
     * @return
     */
    ActivityDO get(String activityId);
}
