package com.taotao.controller;

import com.taotao.business.core.service.SearchBusinessService;
import com.taotao.common.exception.CustomException;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.SearchBusinessType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class SearchController {

    @Resource
    private SearchBusinessService searchBusinessService;

    @RequestMapping("/search")
    @ResponseBody
    public EasyUIDataGridResult getSearch(String key, String type, int pageNo, int pageSize) throws CustomException {
        return searchBusinessService.getSearch(key, SearchBusinessType.getInstance(type), pageNo, pageSize);
    }
}
