package com.codimiracle.web.middleware.content.contract;

import com.codimiracle.web.basic.contract.Filter;
import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.basic.contract.Sorter;

public interface Service<K, T, V> extends com.codimiracle.web.mybatis.contract.Service<K, T> {
    PageSlice<V> findAllIntegrally(Filter filter, Sorter sorter, Page page);

    V findByIdIntegrally(String contentId);
}
