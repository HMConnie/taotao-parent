package com.taotao.service.core.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ItemFrom;
import com.taotao.service.core.to.ItemTO;

import java.util.List;

public interface ItemService {
    ItemTO getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    TaotaoResult insertTbItem(ItemFrom itemFrom, String desc);

    List<ItemTO> getItemList();
}

