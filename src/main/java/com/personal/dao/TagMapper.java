package com.personal.dao;

import com.personal.model.DO.TagDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 李亚卿
 * @Date: Created in 22:51 2018/5/13 0013
 * @Description:
 */
@Mapper
public interface TagMapper {
    void saveTag(TagDO tag);
}
