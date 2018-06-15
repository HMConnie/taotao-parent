package com.taotao.service.core.service.impl;

import com.sgcai.commons.lang.utils.BeanConvertUtils;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.core.dao.ContentCategoryMapper;
import com.taotao.service.core.dao.ContentMapper;
import com.taotao.service.core.entity.Content;
import com.taotao.service.core.entity.ContentCategory;
import com.taotao.service.core.entity.ContentCategoryExample;
import com.taotao.service.core.entity.ContentExample;
import com.taotao.service.core.from.ContentFrom;
import com.taotao.service.core.service.ContentService;
import com.taotao.service.core.to.ContentCategoryTO;
import com.taotao.service.core.to.ContentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentCategoryMapper tbContentCategoryMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public List<ContentCategoryTO> getContentCategory(long parentId) {
        ContentCategoryExample contentCategoryExample = new ContentCategoryExample();
        ContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        criteria.andStatusEqualTo(1);
        List<ContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(contentCategoryExample);
        return BeanConvertUtils.convert(tbContentCategories, ContentCategoryTO.class);
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        ContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (parentContentCategory != null) {
            parentContentCategory.setIsParent(true);
            parentContentCategory.setUpdated(new Date());
            tbContentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
        } else {
            return TaotaoResult.build(404, "父节点不存在");
        }
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        long id = tbContentCategoryMapper.insert(contentCategory);
        return TaotaoResult.ok(id);
    }

    @Override
    public void updateContentCategory(long id, String name) {
        ContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
    }

    @Override
    public void deleteContentCategroy(long id) {
        deleteById(id);
    }

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        ContentExample example = new ContentExample();
        example.setLimit(rows);
        int offset = page > 0 ? (page - 1) * rows : 1;
        example.setOffset(offset);
        ContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<Content> list = contentMapper.selectByExample(example);
        List<ContentTO> contentTOS = BeanConvertUtils.convert(list, ContentTO.class);
        long count = contentMapper.countByExample(new ContentExample());
        //取查询结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(contentTOS);
        result.setTotal(count);
        //返回结果
        return result;
    }

    @Override
    public void contentSave(ContentFrom contentFrom) {
        Date create = new Date();
        Date update = new Date();
        Content content = BeanConvertUtils.convert(contentFrom, Content.class);
        content.setCreated(create);
        content.setUpdated(update);

        contentMapper.insert(content);
    }

    @Override
    public void contentUpdate(ContentFrom contentFrom) {
        Content content = contentMapper.selectByPrimaryKey(contentFrom.getId());
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKey(content);
    }

    @Override
    public void contentDelete(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<ContentTO> getContentList() {
        List<Content> contents = contentMapper.selectByExample(new ContentExample());
        return BeanConvertUtils.convert(contents, ContentTO.class);
    }


    private void deleteById(long id) {
        ContentCategory contentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setStatus(2);
        contentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
        if (contentCategory.getIsParent()) {
            ContentCategoryExample contentCategoryExample = new ContentCategoryExample();
            ContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(contentCategory.getId());
            List<ContentCategory> tbContentCategoryList = tbContentCategoryMapper.selectByExample(contentCategoryExample);
            for (ContentCategory category : tbContentCategoryList) {
                deleteById(category.getId());
            }
        } else {
            // 是否父的叶子节点为 0
            ContentCategoryExample contentCategoryExample = new ContentCategoryExample();
            ContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(contentCategory.getParentId());
            criteria.andStatusEqualTo(1);
            List<ContentCategory> tbContentCategoryList = tbContentCategoryMapper.selectByExample(contentCategoryExample);
            if (tbContentCategoryList.size() == 0) {//如果一个都没有那么就修改父节点为叶子节点
                ContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
                parentContentCategory.setIsParent(false);
                parentContentCategory.setUpdated(new Date());
                tbContentCategoryMapper.updateByPrimaryKey(parentContentCategory);

            }
        }
    }

}
