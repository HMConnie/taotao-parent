package com.taotao.business.core.service;

import com.taotao.common.exception.CustomException;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.SearchBusinessType;

public interface SearchBusinessService {

    EasyUIDataGridResult getSearch(String key, SearchBusinessType searchBusinessType,int pageNo,int pageSize) throws CustomException;
}
