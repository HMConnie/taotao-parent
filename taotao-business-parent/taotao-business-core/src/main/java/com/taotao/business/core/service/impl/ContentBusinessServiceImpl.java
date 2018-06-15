package com.taotao.business.core.service.impl;

import com.sgcai.commons.lang.utils.BeanConvertUtils;
import com.taotao.business.core.service.ContentBusinessService;
import com.taotao.business.core.to.ContentBTO;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.from.ContentFrom;
import com.taotao.service.core.service.ContentService;
import com.taotao.service.core.to.ContentCategoryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentBusinessServiceImpl implements ContentBusinessService {

    @Autowired
    private ContentService contentService;

    @Override
    public List<EasyUITreeNode> getContentCategory(long parentId) {
        List<ContentCategoryTO> contentCategory = contentService.getContentCategory(parentId);
        List<EasyUITreeNode> list = new ArrayList<>();
        for (ContentCategoryTO contentCategoryTO : contentCategory) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(contentCategoryTO.getId());
            easyUITreeNode.setText(contentCategoryTO.getName());
            easyUITreeNode.setState(contentCategoryTO.getIsParent() ? "closed" : "open");
            list.add(easyUITreeNode);
        }
        return list;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        return contentService.insertContentCategory(parentId, name);
    }

    @Override
    public void updateContentCategory(long id, String name) {
        contentService.updateContentCategory(id, name);
    }

    @Override
    public void deleteContentCategroy(long id) {
        contentService.deleteContentCategroy(id);
    }

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        EasyUIDataGridResult contentList = contentService.getContentList(categoryId, page, rows);
        List<ContentBTO> contentBTOList = BeanConvertUtils.convert(contentList.getRows(), ContentBTO.class);
        contentList.setRows(contentBTOList);
        return contentList;
    }

    @Override
    public void contentSave(ContentFrom contentFrom) {
        contentService.contentSave(contentFrom);
    }

    @Override
    public void contentUpdate(ContentFrom contentFrom) {
        contentService.contentUpdate(contentFrom);
    }

    @Override
    public void contentDelete(Long[] ids) {
        contentService.contentDelete(ids);
    }
}
