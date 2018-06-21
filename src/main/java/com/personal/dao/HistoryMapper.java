package com.personal.dao;

import com.personal.model.DO.HistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 李亚卿
 * @Date: Created in 17:32 2018/6/21 0021
 * @Description:
 */
@Mapper
public interface HistoryMapper {
    /**
     * 插入一条用户记录
     *
     * @param history
     */
    void insert(HistoryDO history);
}
