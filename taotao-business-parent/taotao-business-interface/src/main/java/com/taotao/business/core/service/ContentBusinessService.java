package com.taotao.business.core.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ContentFrom;

import java.util.List;

public interface ContentBusinessService {
    List<EasyUITreeNode> getContentCategory(long parentId );

    TaotaoResult insertContentCategory(long parentId, String name);

    void updateContentCategory(long id, String name);

    void deleteContentCategroy(long id);

    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

    void contentSave(ContentFrom contentFrom);

    void contentUpdate(ContentFrom contentFrom);

    void contentDelete(Long[] ids);
}
