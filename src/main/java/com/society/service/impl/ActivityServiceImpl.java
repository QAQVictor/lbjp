package com.society.service.impl;

import com.society.dao.ActivityMapper;
import com.society.model.DO.ActivityDO;
import com.society.model.VO.ActivityBaseVO;
import com.society.model.VO.ActivityDetailVO;
import com.society.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 13:49 2018/5/13 0013
 * @Description:
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<ActivityBaseVO> getAllActivity() {
        return activityMapper.getAllActivity();
    }


    @Override
    public void saveActivity(ActivityDO activity) {
        activityMapper.insert(activity);
    }

    @Override
    public Map getActivity(String activityId) {
        return activityMapper.get(activityId);
    }
}
