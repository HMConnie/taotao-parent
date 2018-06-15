package com.taotao.api.controller;

import com.taotao.business.core.service.ContentBusinessService;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ContentFrom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ContentController {

    @Resource
    private ContentBusinessService contentBusinessService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategory(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentBusinessService.getContentCategory(parentId);
    }


    @RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult insertContentCategory(@RequestParam(value = "parentId") Long parentId, @RequestParam("name") String name) {
        return contentBusinessService.insertContentCategory(parentId, name);
    }


    @RequestMapping(value = "/content/category/update", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateContentCategory(@RequestParam(value = "id") Long id, @RequestParam("name") String name) {
        contentBusinessService.updateContentCategory(id, name);
        return TaotaoResult.ok();
    }


    @RequestMapping(value = "/content/category/delete/", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContentCategory(@RequestParam(value = "id") Long id) {
        contentBusinessService.deleteContentCategroy(id);
        return TaotaoResult.ok();
    }


    @RequestMapping(value = "/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult contentList(@RequestParam(value = "categoryId") Long categoryId, @RequestParam("page") int page
            , @RequestParam("rows") int rows) {
        return contentBusinessService.getContentList(categoryId, page, rows);
    }

    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult contentSave(ContentFrom contentFrom) {
        contentBusinessService.contentSave(contentFrom);
        return TaotaoResult.ok();
    }

    @RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult contentUpdate(ContentFrom contentFrom) {
        contentBusinessService.contentUpdate(contentFrom);
        return TaotaoResult.ok();
    }

    @RequestMapping(value = "/content/delete", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult contentDelete(@RequestParam("ids") Long[] ids) {
        contentBusinessService.contentDelete(ids);
        return TaotaoResult.ok();
    }
}
