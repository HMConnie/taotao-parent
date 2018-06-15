package com.taotao.business.core.service.impl;

import com.sgcai.commons.lang.utils.BeanConvertUtils;
import com.taotao.business.core.service.ItemBusinessService;
import com.taotao.business.core.to.ItemBTO;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ItemFrom;
import com.taotao.service.core.service.ItemCatService;
import com.taotao.service.core.service.ItemService;
import com.taotao.service.core.to.ItemTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemBusinessServiceImpl implements ItemBusinessService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCatService itemCatService;

    @Override
    public List<EasyUITreeNode> getItemCatlist(long parentId) {
        return itemCatService.getItemCatlist(parentId);
    }

    @Override
    public ItemBTO getItemById(long itemId) {
        return BeanConvertUtils.convert(itemService.getItemById(itemId), ItemBTO.class);
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
        List<ItemBTO> convert = BeanConvertUtils.convert(itemList.getRows(), ItemBTO.class);
        itemList.setRows(convert);
        return itemList;
    }

    @Override
    public TaotaoResult insertTbItem(ItemFrom itemFrom, String desc) {
        return itemService.insertTbItem(itemFrom, desc);
    }
}
