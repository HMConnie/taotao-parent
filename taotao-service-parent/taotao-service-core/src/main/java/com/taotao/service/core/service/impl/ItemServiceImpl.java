package com.taotao.service.core.service.impl;

import com.sgcai.commons.lang.utils.BeanConvertUtils;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.IDUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.dao.ItemDescMapper;
import com.taotao.service.core.dao.ItemMapper;
import com.taotao.service.core.entity.Item;
import com.taotao.service.core.entity.ItemDesc;
import com.taotao.service.core.entity.ItemExample;
import com.taotao.service.core.from.ItemFrom;
import com.taotao.service.core.service.ItemService;
import com.taotao.service.core.to.ItemTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 *
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Throwable.class})
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;


    @Autowired
    private ItemDescMapper itemDescMapper;


    @Override
    public ItemTO getItemById(long itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        return BeanConvertUtils.convert(item,ItemTO.class);
    }


    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //执行查询
        ItemExample example = new ItemExample();
        example.setLimit(rows);
        int offset = page >0 ?(page-1)*rows:1;
        example.setOffset(offset);
        List<Item> list = itemMapper.selectByExample(example);
        List<ItemTO> itemTOS = BeanConvertUtils.convert(list, ItemTO.class);
        long count = itemMapper.countByExample(new ItemExample());
        //取查询结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(itemTOS);
        result.setTotal(count);
        //返回结果
        return result;
    }

    @Override
    public TaotaoResult insertTbItem(ItemFrom itemFrom, String desc) {
        long itemId = IDUtils.genItemId();
        Item tbItem = BeanConvertUtils.convert(itemFrom, Item.class);
        tbItem.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        Date create = new Date();
        Date update = new Date();
        tbItem.setCreated(create);
        tbItem.setUpdated(update);

        ItemDesc tbItemDesc = new ItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(create);
        tbItemDesc.setUpdated(update);

        itemMapper.insert(tbItem);
        itemDescMapper.insert(tbItemDesc);
        return TaotaoResult.ok();
    }

    @Override
    public List<ItemTO> getItemList() {
        ItemExample example = new ItemExample();
        List<Item> items = itemMapper.selectByExample(example);
        return BeanConvertUtils.convert(items,ItemTO.class);
    }


}
