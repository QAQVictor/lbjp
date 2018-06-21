package com.personal.service;

import com.personal.model.DO.HistoryDO;

/**
 * @Author: 李亚卿
 * @Date: Created in 00:33 2018/6/22 0022
 * @Description:
 */
public interface HistoryService {

    /**
     * 保存一条用户使用记录
     *
     * @param history
     */
    void save(HistoryDO history);
}
