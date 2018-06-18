package com.society.service.impl;

import com.society.dao.ActivityMapper;
import com.society.model.DO.ActivityDO;
import com.society.model.VO.ActivityBaseVO;
import com.society.model.VO.ScheduleActivityVO;
import com.society.service.ActivityService;
import com.user.dao.UserMapper;
import com.user.model.DO.UserDO;
import com.user.model.VO.UserBaseVO;
import com.util.DateUtils;
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
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<ActivityBaseVO> getAllActivity() {
        return activityMapper.getAllActivity();
    }


    @Override
    public void saveActivity(ActivityDO activity) {
        activityMapper.insert(activity);
        activityMapper.join(activity.getCreator(), activity.getActivityId(), activity.getCreateDate());
    }

    @Override
    public Map getActivity(String activityId) {
        return activityMapper.get(activityId);
    }

    //过期向前端传1，未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5
    @Override
    public int join(String userId, String activityId) {
        ActivityDO activity = activityMapper.getById(activityId);
        if (!judgeJoin(userId, activityId)) {
            return 4;
        } else if (activityMapper.getActualNum(activityId) >= activity.getPlannedNum()) {
            return 3;
        } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryStartDate()) == -1) {
            return 2;
        } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryEndDate()) == 1) {
            return 1;
        } else if (judgeByDate(userId, activityId)) {
            return 5;
        } else {
            activityMapper.join(userId, activityId, DateUtils.getNowDate());
            return 0;
        }
    }

    @Override
    public boolean judgeJoin(String userId, String activityId) {
        return activityMapper.judgeJoin(userId, activityId) == 0;
    }

    @Override
    public boolean judgeByDate(String userId, String activityId) {
        ActivityDO activity = activityMapper.getById(activityId);
        String startDate = activity.getStartDate();
        String endDate = activity.getEndDate();
        return activityMapper.judgeByDate(userId, startDate, endDate) >= 1;
    }

    @Override
    public List<ScheduleActivityVO> getJoinActivityByDay(String userId, String date) {
        return activityMapper.getJoinActivityByDay(userId, date, date + " 23:59:59");
    }

    @Override
    public List<ScheduleActivityVO> getCreateActivityByDay(String userId, String date) {
        return activityMapper.getCreateActivityByDay(userId, date, date + " 23:59:59");
    }

    @Override
    public int cancelActivity(String activityId) {
        String userId = activityMapper.getCreator(activityId);
        if (userMapper.updateCancelNum(userId) != 1) {
            return 1;
        }
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);

        String content = "活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”已经由发起者取消，抱歉给您带来不便。";
        return sendEmail(userEmailList, content);
    }

    @Override
    public int breakUpActivity(String userId, String activityId) {
        //String userId = activityMapper.getCreator(activityId);
        if (userMapper.updateBreakNum(userId) != 1) {
            return 1;
        }
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);
        UserDO user = userMapper.getById(userId);
        String content = "您报名的活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”中，用户“" + user.getUserName() + "”由于个人原因无法参加，抱歉给您带来不便。";
        return sendEmail(userEmailList, content);
    }

    @Override
    public int noticeAll(String activityId) {
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);
        String content = "感谢您报名活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”，发起这提示您提前准备，按时集合，谢谢配合。";
        return sendEmail(userEmailList, content);
    }

    /**
     * 发送邮件
     *
     * @param emailList
     * @param content
     * @return 0全部发送成功 1部分发送成功 -1 全部发送失败
     */
    private int sendEmail(List<String> emailList, String content) {

        return 0;
    }
}
