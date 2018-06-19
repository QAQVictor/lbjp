package com.society.service.impl;

import com.personal.dao.CreditMapper;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    //报名已经结束向前端传1，未开始传2，人数已满传3,已经报名传4，该时间段已有其他安排传5,被取消6
    @Override
    public int judgeJoin(String userId, String activityId) {
        ActivityDO activity = activityMapper.getById(activityId);
        if ("0".equals(activity.getInvalided())) {
            //人数已满
            if (activityMapper.getActualNum(activityId) >= activity.getPlannedNum()) {
                activityMapper.updateActivityInvalided(activityId, "3");
                return 3;
            } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryStartDate()) == -1) {
                //未开始报名
                activityMapper.updateActivityInvalided(activityId, "2");
                return 2;
            } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryEndDate()) == 1) {
                //报名已经结束
                activityMapper.updateActivityInvalided(activityId, "1");
                return 1;
            } else if (activityMapper.judgeJoin(userId, activityId) == 1) {
                //该用户已报名
                return 4;
            } else if (activityMapper.judgeByDate(userId, activity.getStartDate(), activity.getEndDate()) >= 1) {
                //该时间段有其他安排
                return 5;
            } else {
                return 0;
            }
        } else {
            return Integer.parseInt(activity.getInvalided());
        }
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
            creditMapper.insert(userId, activityId, "2");
            return 1;
        }
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);
        activityMapper.updateActivityInvalided(activityId, "6");
        creditMapper.insert(userId, activityId, "2");
        String content = "活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”已经由发起者取消，抱歉给您带来不便。";
        return sendEmail(userEmailList, content, 3);
    }

    @Override
    public int breakUpActivity(String userId, String activityId) {
        //String userId = activityMapper.getCreator(activityId);
        if (userMapper.updateBreakNum(userId) != 1) {
            creditMapper.insert(userId, activityId, "1");
            return 1;
        }
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);
        UserDO user = userMapper.getById(userId);
        String content = "您报名的活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”中，用户“" + user.getUserName() + "”由于个人原因无法参加，抱歉给您带来不便。";
        return sendEmail(userEmailList, content, 2);
    }

    @Override
    public int noticeAll(String activityId) {
        List<String> userEmailList = activityMapper.getUserEmails(activityId);
        ActivityDO activity = activityMapper.getById(activityId);
        String content = "感谢您报名活动“" + activity.getTheme() +
                "(" + activity.getStartDate().substring(5, 10) +
                "——" + activity.getEndDate().substring(5, 10) + ")”，发起这提示您提前准备，按时集合，谢谢配合。";
        return sendEmail(userEmailList, content, 1);
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

    public void updateActivityInvalided(ActivityDO activity) {
        //人数已满
        if (activityMapper.getActualNum(activity.getActivityId()) >= activity.getPlannedNum()) {
            activityMapper.updateActivityInvalided(activity.getActivityId(), "3");
        } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryStartDate()) == -1) {
            //未开始报名
            activityMapper.updateActivityInvalided(activity.getActivityId(), "2");
        } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEntryEndDate()) == 1) {
            //报名已经结束
            activityMapper.updateActivityInvalided(activity.getActivityId(), "1");
        } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEndDate()) == 1) {
            //报名已经结束
            activityMapper.updateActivityInvalided(activity.getActivityId(), "7");
        } else if (DateUtils.compareDate(DateUtils.getNowDate(), activity.getEndDate()) == -1) {
            //报名已经结束
            activityMapper.updateActivityInvalided(activity.getActivityId(), "8");
        }
    }
}
