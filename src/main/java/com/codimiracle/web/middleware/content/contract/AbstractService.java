package com.codimiracle.web.middleware.content.contract;

import com.codimiracle.web.response.contract.Filter;
import com.codimiracle.web.response.contract.Page;
import com.codimiracle.web.response.contract.PageSlice;
import com.codimiracle.web.response.contract.Sorter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public abstract class AbstractService<K, T, V> extends com.codimiracle.web.mybatis.contract.AbstractService<K, T> {
    @Autowired
    private Mapper<T, V> mapper;

    protected V mutate(V inflatedObject) {
        return inflatedObject;
    }
    protected PageSlice<V> mutate(PageSlice<V> slice) {
        slice.getList().forEach(this::mutate);
        return slice;
    }
    public V findByIdIntegrally(K id) {
        return mutate(mapper.selectByIdIntegrally(id));
    }
    public PageSlice<V> findAllIntegrally(Filter filter, Sorter sorter, Page page) {
        return mutate(extractPageSlice(mapper.selectAllIntegrally(filter, sorter, page)));
    }
}
