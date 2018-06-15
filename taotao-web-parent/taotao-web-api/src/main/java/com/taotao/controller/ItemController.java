package com.taotao.controller;

import com.taotao.business.core.service.ItemBusinessService;
import com.taotao.business.core.to.ItemBTO;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ItemFrom;
import com.taotao.service.core.to.ItemTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 *
 * @version 1.0
 */
@Controller
public class ItemController {

    @Resource
    private ItemBusinessService itemBusinessService;


    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public ItemBTO getItemById(@PathVariable Long itemId) {
        ItemBTO tbItem = itemBusinessService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemBusinessService.getItemList(page, rows);
        return result;
    }


    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id", defaultValue = "1") Long parentId) {
        List<EasyUITreeNode> itemCatlist = itemBusinessService.getItemCatlist(parentId);
        return itemCatlist;
    }


    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult itemSave(ItemFrom itemFrom, String desc) {
        return itemBusinessService.insertTbItem(itemFrom, desc);
    }

}
