package com.taotao.service.core.task;

import com.taotao.common.pojo.SearchBusinessType;
import com.taotao.search.core.from.SearchFrom;
import com.taotao.search.core.service.SearchService;
import com.taotao.service.core.service.ContentService;
import com.taotao.service.core.service.ItemService;
import com.taotao.service.core.to.ContentTO;
import com.taotao.service.core.to.ItemTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SearchTask {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ItemService itemService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeItemInsert() throws IOException {
        List<ItemTO> itemList = itemService.getItemList();
        for (ItemTO itemTO : itemList) {
            SearchFrom searchFrom = new SearchFrom();
            searchFrom.setId(String.valueOf(itemTO.getId()));
            searchFrom.setKey(itemTO.getTitle());
            searchFrom.setSearchBusinessType(SearchBusinessType.Item);
            searchFrom.setObject(itemTO);
            searchService.insertOrUpdate(searchFrom);
        }
    }


    @Scheduled(cron = "0 0/1 * * * ?")
    public void executeContentInsert() throws IOException {
        List<ContentTO> contentList = contentService.getContentList();
        for (ContentTO contentTO : contentList) {
            SearchFrom searchFrom = new SearchFrom();
            searchFrom.setId(String.valueOf(contentTO.getId()));
            searchFrom.setKey(contentTO.getTitle());
            searchFrom.setSearchBusinessType(SearchBusinessType.Content);
            searchFrom.setObject(contentTO);
            searchService.insertOrUpdate(searchFrom);
        }
    }

}
