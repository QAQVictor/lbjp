package com.society.dao;

import com.society.model.DO.ActivityDO;
import com.society.model.VO.ActivityBaseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 根据id获取活动信息
     *
     * @param activityId
     * @return
     */
    ActivityDO getById(String activityId);

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
     *
     * @param activityId
     * @return
     */
    Map get(String activityId);

    /**
     * @param activityId
     * @return
     */
    String getCreator(String activityId);

    /**
     * 报名参加
     *
     * @param userId
     * @param activityId
     */
    void join(@Param("userId") String userId, @Param("activityId") String activityId, @Param("createDate") String createDate);

    /**
     * 判断是否参加过
     *
     * @param userId
     * @param activityId
     * @return
     */
    int judgeJoin(@Param("userId") String userId, @Param("activityId") String activityId);

    /**
     * 获取现在活动的报名人数
     *
     * @param activityId
     * @return
     */
    int getActualNum(String activityId);

    /**
     * 获取该用户这一天参加的活动
     *
     * @param userId
     * @param date
     * @return
     */
    List getJoinActivityByDay(@Param("userId") String userId, @Param("date") String date, @Param("date1") String date1);

    /**
     * 获取该用户这天发起的活动
     *
     * @param userId
     * @param date
     * @return
     */
    List getCreateActivityByDay(@Param("userId") String userId, @Param("date") String date, @Param("date1") String date1);

    /**
     * 判断这段时间的参加的活动个数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    int judgeByDate(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取参加该活动的人的邮箱列表
     *
     * @param activityId
     * @return
     */
    List getUserEmails(String activityId);
}
