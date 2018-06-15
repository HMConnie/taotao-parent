package com.taotao.business.core.service;

import com.taotao.business.core.to.ItemBTO;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ItemFrom;
import com.taotao.service.core.to.ItemTO;

import java.util.List;

public interface ItemBusinessService {
    List<EasyUITreeNode> getItemCatlist(long parentId);

    ItemBTO getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult insertTbItem(ItemFrom itemFrom, String desc);
}
