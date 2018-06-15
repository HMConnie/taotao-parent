package com.taotao.business.core.service.impl;

import com.taotao.business.core.service.SearchBusinessService;
import com.taotao.common.exception.CustomException;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.SearchBusinessType;
import com.taotao.search.core.criteria.SearchCriteria;
import com.taotao.search.core.service.SearchService;
import com.taotao.search.core.to.SearchTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public class SearchBusinessServiceImpl implements SearchBusinessService {

    @Autowired
    private SearchService searchService;

    @Override
    public EasyUIDataGridResult getSearch(String key, SearchBusinessType searchBusinessType, int pageNo, int pageSize) throws CustomException {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey(key);
        searchCriteria.setSearchBusinessType(searchBusinessType);
        searchCriteria.setSize(pageSize);
        int offset = pageNo >0?(pageNo-1)*pageSize:0;
        searchCriteria.setOffset(offset);
        try {
            SearchTO searchTO = searchService.query(searchCriteria);
            EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
            easyUIDataGridResult.setTotal(searchTO.getTotalHits());
            easyUIDataGridResult.setRows(searchTO.getData());
            return easyUIDataGridResult;
        } catch (UnknownHostException e) {
            throw new CustomException("search failed","查询失败");
        }
    }
}
