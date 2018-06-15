package com.taotao.service.core.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.core.dao.ItemCatMapper;
import com.taotao.service.core.entity.ItemCat;
import com.taotao.service.core.entity.ItemCatExample;
import com.taotao.service.core.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {


    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatlist(long parentId) {
        ItemCatExample example = new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();
        for (ItemCat tbItemCat : tbItemCats) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
            easyUITreeNode.setText(tbItemCat.getName());
            easyUITreeNode.setState(tbItemCat.getIsParent() ?"closed":"open" );
            easyUITreeNodes.add(easyUITreeNode);
        }
        return easyUITreeNodes;
    }
}
