package com.codimiracle.web.middleware.content.contract;

import com.codimiracle.web.response.contract.Filter;
import com.codimiracle.web.response.contract.Page;
import com.codimiracle.web.response.contract.Sorter;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

public interface Mapper<T, V> extends com.codimiracle.web.mybatis.contract.Mapper<T> {
    List<V> selectAllIntegrally(Filter filter, Sorter sorter, Page page);

    V selectByIdIntegrally(Object id);
}
