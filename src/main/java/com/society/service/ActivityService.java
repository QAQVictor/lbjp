package com.society.service;

import com.society.model.DO.ActivityDO;
import com.society.model.VO.ActivityBaseVO;
import com.society.model.VO.ScheduleActivityVO;

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

    List<ActivityBaseVO> getIndexActivity(String userId);

    /**
     * 保存活动
     *
     * @param activity
     */
    int saveActivity(ActivityDO activity);

    /**
     * 获取活动详情
     *
     * @param activityId
     * @return
     */
    Map<String, Object> getActivity(String activityId, String userId);

    /**
     * 报名,成功向前端传0，过期向前端传1，未开始传2，人数已满传3,已经报名传4,该时间段已有其他安排传5
     *
     * @param userId
     * @param activityId
     * @return
     */
    int join(String userId, String activityId);

    /**
     * 判断是否可以报名
     *
     * @param userId
     * @param activityId
     * @return 0可报名，1报名结束，2报名未开始，3人数满，4已报名，5时间冲突，6活动被取消
     */
    int judgeJoin(String userId, String activityId);

    /**
     * 判断该时间段是否有其他活动
     *
     * @param userId
     * @param activityId
     * @return 有true 无 false
     */
    //boolean judgeByDate(String userId, String activityId);

    /**
     * @param userId
     * @param date
     * @return
     */
    List<ScheduleActivityVO> getJoinActivityByDay(String userId, String date);

    /**
     * 获取某一天该用户的发起的活动
     *
     * @param userId
     * @param date
     * @return
     */
    List<ScheduleActivityVO> getCreateActivityByDay(String userId, String date);

    /**
     * 取消活动（向参加人发送邮件活动取消）
     *
     * @param activityId
     * @return
     */
    Map<String, Object> cancelActivity(String activityId);

    /**
     * 无法参加（通知其他人自己无法参加）
     *
     * @param userId
     * @param activityId
     * @return
     */
    int breakUpActivity(String userId, String activityId);

    /**
     * 通知用户（界面中“一键通知功能”）
     *
     * @param activityId
     * @return
     */
    Map<String, Object> noticeAll(String activityId);

    /**
     * 获取用户创建的活动数目
     *
     * @param userId
     * @return
     */
    int getCreateActivityNum(String userId);

    /**
     * 获取用户参与的活动
     *
     * @param userId
     * @return
     */
    List<ActivityBaseVO> getJoinActivity(String userId);

    /**
     * 获取用户发起的活动
     *
     * @param userId
     * @return
     */
    List<ActivityBaseVO> getCreateActivity(String userId);

    List<ActivityBaseVO> getHistoryActivity(String userId);

    /**
     * 获取创建人邮箱
     *
     * @return
     */
    String getCreatorEmail(String activityId);
}
