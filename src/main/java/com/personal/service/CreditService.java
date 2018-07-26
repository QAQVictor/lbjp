package com.personal.service;

import com.personal.model.DO.CreditDO;

import java.util.Map;

/**
 * @Author: 李亚卿
 * @Date: Created in 23:21 2018/6/20 0020
 * @Description:
 */
public interface CreditService {
    /**
     * 获取用户的信用记录
     *
     * @param userId
     * @return {"breakNum":..,"cancelNum":..}
     */
    Map getCreditNum(String userId);

    /**
     * 添加一条信用记录
     *
     * @param credit
     */
    void addCredit(CreditDO credit);

    CreditDO get(CreditDO credit);

}
