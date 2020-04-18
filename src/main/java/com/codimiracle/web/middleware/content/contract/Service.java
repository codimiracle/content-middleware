package com.codimiracle.web.middleware.content.contract;

import com.codimiracle.web.response.contract.Filter;
import com.codimiracle.web.response.contract.Page;
import com.codimiracle.web.response.contract.PageSlice;
import com.codimiracle.web.response.contract.Sorter;

import java.util.List;

public interface Service<K, T, V> extends com.codimiracle.web.mybatis.contract.Service<K, T> {
    PageSlice<V> findAllIntegrally(Filter filter, Sorter sorter, Page page);
    V findByIdIntegrally(String contentId);
}
