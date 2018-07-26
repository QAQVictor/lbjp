package com.personal.service.impl;

import com.personal.dao.HistoryMapper;
import com.personal.model.DO.HistoryDO;
import com.personal.service.HistoryService;
import com.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 李亚卿
 * @Date: Created in 00:34 2018/6/22 0022
 * @Description:
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public void save(HistoryDO history) {
        if (historyMapper.select(history) == null) {
            historyMapper.insert(history);
        } else {
            historyMapper.update(history);
        }
    }
}
