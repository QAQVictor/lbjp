package com.society.service.impl;

import com.personal.dao.CreditMapper;
import com.personal.model.DO.CreditDO;
import com.society.dao.ActivityMapper;
import com.society.model.DO.ActivityDO;
import com.society.model.VO.ActivityBaseVO;
import com.society.model.VO.ScheduleActivityVO;
import com.society.service.ActivityService;
import com.user.dao.UserMapper;
import com.user.model.DO.UserDO;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CreditMapper creditMapper;

    @Override
    public List<ActivityBaseVO> getAllActivity() {
        return activityMapper.getAllActivity();
    }

    @Override
    public int saveActivity(ActivityDO activity) {
        if (activityMapper.judgeByDate(activity.getCreator(), activity.getStartDate(), activity.getEndDate()) >= 1) {
            return 5;
        }
        activity.setCreateDate(DateUtils.getNowDate());
        activity.setActivityId(DateUtils.getIDByDate(activity.getCreateDate()));
        //activity.setEndDate(activity.getEndDate() + " 23:59:59");
        activity.setEntryEndDate(activity.getEntryEndDate() + " 23:59:59");
        activity.setHot(0);
        activity.setInvalided("0");
        activityMapper.insert(activity);
        activityMapper.join(activity.getCreator(), activity.getActivityId(), activity.getCreateDate());
        return 0;
    }

    @Override
    public Map getActivity(String activityId) {

        return activityMapper.get(activityId);
    }

    @Override
    public int join(String userId, String activityId) {
        int state = judgeJoin(userId, activityId);
        if (state == 0) {
            activityMapper.join(userId, activityId, DateUtils.getNowDate());
        }
        return state;
    }

    //报名已经结束向前端传1，未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5,被取消6，活动已经结束7
    @Override
    public int judgeJoin(String userId, String activityId) {
        ActivityDO activity = activityMapper.getById(activityId);
        int state = updateActivityInvalided(activity);
        if (state == 0) {
            if (activityMapper.judgeJoin(userId, activityId) == 1) {
                //该用户已报名
                return 4;
            } else if (activityMapper.judgeByDate(userId, activity.getStartDate(), activity.getEndDate()) >= 1) {
                //该时间段有其他安排
                return 5;
            }
            return state;
        } else {
            return Integer.parseInt(activity.getInvalided());
        }
    }


    @Override

    public List<ScheduleActivityVO> getJoinActivityByDay(String userId, String date) {
        List<ScheduleActivityVO> list = activityMapper.getJoinActivityByDay(userId, date, date + " 23:59:59");
        for (ScheduleActivityVO activity : list) {
            int state = updateActivityInvalided(activity);
            if (state == 0) {
                if (creditMapper.get(userId, activity.getActivityId()) != null) {
                    activity.setInvalided("10");
                }
            }
        }
        return list;
    }

    @Override
    public List<ScheduleActivityVO> getCreateActivityByDay(String userId, String date) {
        List<ScheduleActivityVO> list = activityMapper.getCreateActivityByDay(userId, date, date + " 23:59:59");
        for (ScheduleActivityVO activity : list) {
            updateActivityInvalided(activity);
        }
        return list;
    }

    @Override
    public Map<String, Object> cancelActivity(String activityId) {
        Map<String, Object> map = new HashMap<>();
        String userId = activityMapper.getCreator(activityId);
        userMapper.updateCancelNum(userId);
        creditMapper.insert(new CreditDO(userId, activityId, "2", DateUtils.getNowDate()));

        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        userEmailList.remove(activityMapper.getCreatorEmail(activityId));
        ActivityDO activity = activityMapper.getById(activityId);
        activityMapper.updateActivityInvalided(activityId, "6");

        String content = "活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”已经由发起者取消，抱歉给您带来不便。";
        map.put("userEmailList", userEmailList);
        map.put("state", sendEmail(userEmailList, content, 1));
        return map;
    }

    @Override
    public int breakUpActivity(String userId, String activityId) {
        userMapper.updateBreakNum(userId);
        creditMapper.insert(new CreditDO(userId, activityId, "1", DateUtils.getNowDate()));
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);
        UserDO user = userMapper.getById(userId);
        String content = "您报名的活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”中，用户“" + user.getUserName() + "”由于个人原因无法参加，抱歉给您带来不便。";
        //return sendEmail(userEmailList, content, 2);
        return 0;
    }

    @Override
    public Map<String, Object> noticeAll(String activityId) {
        Map<String, Object> map = new HashMap<>();
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        userEmailList.remove(activityMapper.getCreatorEmail(activityId));
        ActivityDO activity = activityMapper.getById(activityId);
        String content = "感谢您报名活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”，发起者提示您提前准备，按时集合。";
        map.put("userEmailList", userEmailList);
        map.put("state", sendEmail(userEmailList, content, 1));
        return map;
    }

    /**
     * @param emailList
     * @param content
     * @param type      1邮件提醒，2无法参加 3活动取消
     * @return 0全部发送成功 1发送失败
     */
    private int sendEmail(List<String> emailList, String content, int type) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //设置寄件人邮箱
        simpleMailMessage.setFrom("17600305595@163.com");
        for (String email : emailList) {
            try {
                //设置邮件主题
                switch (type) {
                    case 1:
                        simpleMailMessage.setSubject("发起人提醒您按时参加");
                        break;
                    case 2:
                        simpleMailMessage.setSubject("某用户无法参加");
                        break;
                    case 3:
                        simpleMailMessage.setSubject("活动取消");
                        break;
                }
                //设置邮件内容
                simpleMailMessage.setText(content);
                //设置收件人邮箱
                simpleMailMessage.setTo(email);
                //发送邮件
                mailSender.send(simpleMailMessage);
            } catch (Exception e) {
                return 1;
            }
        }
        return 0;
    }

    public int updateActivityInvalided(ActivityDO activity) {
        if (!"7".equals(activity.getInvalided()) && DateUtils.compareDate(DateUtils.getNowDate(), activity.getEndDate()) == 1) {
            //活动已经结束
            activityMapper.updateActivityInvalided(activity.getActivityId(), "7");
            activity.setInvalided("7");
            return 7;
        } else if (!"1".equals(activity.getInvalided()) && DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryEndDate()) == 1) {
            //报名已经结束
            activityMapper.updateActivityInvalided(activity.getActivityId(), "1");
            activity.setInvalided("1");
            return 1;
        } else if (!"3".equals(activity.getInvalided()) && activityMapper.getActualNum(activity.getActivityId()) >= activity.getPlannedNum()) {
            //人数已满
            activityMapper.updateActivityInvalided(activity.getActivityId(), "3");
            activity.setInvalided("3");
            return 3;
        } else if (!"2".equals(activity.getInvalided()) && DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryStartDate()) == -1) {
            //未开始报名
            activity.setInvalided("2");
            activityMapper.updateActivityInvalided(activity.getActivityId(), "2");
            return 2;
        } else {
            activity.setInvalided("0");
            return 0;
        }
    }

    public int updateActivityInvalided(ScheduleActivityVO activity) {
        if (!"7".equals(activity.getInvalided()) && DateUtils.compareDate(DateUtils.getNowDate(), activity.getEndDate()) == 1) {
            //活动已经结束
            activityMapper.updateActivityInvalided(activity.getActivityId(), "7");
            activity.setInvalided("7");
            return 7;
        } else if (!"6".equals(activity.getInvalided()) && creditMapper.get(activity.getUserId(), activity.getActivityId()) != null) {
            //活动被取消
            activityMapper.updateActivityInvalided(activity.getActivityId(), "6");
            activity.setInvalided("6");
            return 6;
        } else {
            activity.setInvalided("0");
            return 0;
        }
    }

    @Override
    public int getCreateActivityNum(String userId) {
        return activityMapper.getCreateActivityNum(userId);
    }

    @Override
    public List<ActivityBaseVO> getJoinActivity(String userId) {
        return activityMapper.getJoinActivity(userId);
    }

    @Override
    public List<ActivityBaseVO> getCreateActivity(String userId) {
        return activityMapper.getCreateActivity(userId);
    }

    @Override
    public String getCreatorEmail(String activityId) {
        return activityMapper.getCreatorEmail(activityId);
    }
}
