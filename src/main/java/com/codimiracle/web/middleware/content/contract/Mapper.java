package com.codimiracle.web.middleware.content.contract;

import com.codimiracle.web.basic.contract.Filter;
import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.Sorter;

import java.util.List;

public interface Mapper<T, V> extends com.codimiracle.web.mybatis.contract.Mapper<T> {
    List<V> selectAllIntegrally(Filter filter, Sorter sorter, Page page);

    V selectByIdIntegrally(Object id);
}
