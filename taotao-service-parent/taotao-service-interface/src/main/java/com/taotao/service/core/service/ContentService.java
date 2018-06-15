package com.taotao.service.core.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ContentFrom;
import com.taotao.service.core.to.ContentCategoryTO;
import com.taotao.service.core.to.ContentTO;

import java.util.List;

public interface ContentService {
    List<ContentCategoryTO> getContentCategory(long parentId);

    TaotaoResult insertContentCategory(long parentId, String name);

    void updateContentCategory(long id, String name);

    void deleteContentCategroy(long id);

    EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

    void contentSave(ContentFrom contentFrom);

    void contentUpdate(ContentFrom contentFrom);

    void contentDelete(Long[] ids);

    List<ContentTO> getContentList();
}
