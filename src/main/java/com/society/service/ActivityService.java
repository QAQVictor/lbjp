package com.society.service;

import com.society.model.DO.ActivityDO;
import com.society.model.VO.ActivityBaseVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 13:47 2018/5/13 0013
 * @Description:
 */

public interface ActivityService {

    /**
     * 查询所有活动
     *
     * @return
     */
    List<ActivityBaseVO> getAllActivity();

    /**
     * 保存活动
     *
     * @param activity
     */
    void saveActivity(ActivityDO activity);

    Map getActivity(String activityId);
}
