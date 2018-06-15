package com.taotao.service.core.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatlist(long parentId);
}
