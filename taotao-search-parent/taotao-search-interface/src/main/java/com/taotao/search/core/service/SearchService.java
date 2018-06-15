package com.taotao.search.core.service;

import com.taotao.common.pojo.SearchBusinessType;
import com.taotao.search.core.criteria.SearchCriteria;
import com.taotao.search.core.from.SearchFrom;
import com.taotao.search.core.to.SearchTO;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public interface SearchService {
    /**
     * 查询
     */
    SearchTO query(SearchCriteria searchCriteria) throws UnknownHostException;


    /**
     * 删除
     */
    void delete(String id,SearchBusinessType searchBusinessType) throws UnknownHostException;

    /**
     * 添加
     */
    void insertOrUpdate(SearchFrom searchFrom) throws IOException;

}
