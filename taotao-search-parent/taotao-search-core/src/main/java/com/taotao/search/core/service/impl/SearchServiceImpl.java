package com.taotao.search.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.taotao.common.pojo.SearchBusinessType;
import com.taotao.search.core.criteria.SearchCriteria;
import com.taotao.search.core.from.SearchFrom;
import com.taotao.search.core.service.SearchService;
import com.taotao.search.core.to.SearchTO;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SearchServiceImpl implements SearchService {


    @Value("${cluster.name}")
    private String CLUSTER_NAME;
    @Value("${node.name}")
    private String NODE_NAME;
    @Value("${elasticsearch.host}")
    private String ELASTICSEARCH_HOST;
    @Value("${elasticsearch.port}")
    private String ELASTICSEARCH_PORT;


    static PreBuiltTransportClient preBuiltTransportClient = null;

    static TransportClient transportClient = null;

    TransportClient getClient() throws UnknownHostException {
        if (transportClient == null) {
            Settings settings = Settings.builder()
                    .put("cluster.name", CLUSTER_NAME)
                    .put("node.name", NODE_NAME)
                    .build();
            preBuiltTransportClient = new PreBuiltTransportClient(settings);

            InetAddress inetAddress = InetAddress.getByName(ELASTICSEARCH_HOST);
            TransportAddress transportAddress = new TransportAddress(inetAddress, Integer.parseInt(ELASTICSEARCH_PORT));
            transportClient = preBuiltTransportClient.addTransportAddress(transportAddress);
        }
        return transportClient;
    }

    @Override
    public SearchTO query(SearchCriteria searchCriteria) throws UnknownHostException {
        SearchTO searchTO = new SearchTO();
        List list = new ArrayList();
        TransportClient client = getClient();

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("search-data");

        SearchBusinessType searchBusinessType = searchCriteria.getSearchBusinessType();
        BoolQueryBuilder must = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("key", searchCriteria.getKey()))
                .must(QueryBuilders.matchQuery("SearchType", searchBusinessType.toString()));

        SearchResponse searchResponse = searchRequestBuilder
                .addSort("createTime", SortOrder.DESC)
                .setQuery(must)
                .setFrom(searchCriteria.getOffset())
                .setSize(searchCriteria.getSize())
                .get();

        SearchHits hitss = searchResponse.getHits();
        searchTO.setTotalHits(hitss.getTotalHits());
        SearchHit[] hits = hitss.getHits();
        for (SearchHit searchHit : hits) {
            Map<String, Object> source = searchHit.getSourceAsMap();
            Object data = source.get("data");
            list.add(JSONObject.parseObject(data.toString()));
        }
        searchTO.setData(list);
        return searchTO;
    }

    @Override
    public void delete(String id, SearchBusinessType searchBusinessType) throws UnknownHostException {
        TransportClient client = getClient();
        DeleteResponse deleteResponse = client
                .prepareDelete("search-data", "Search", searchBusinessType.toString() + id).get();
        System.out.println("status:" + deleteResponse.status());
    }

    @Override
    public void insertOrUpdate(SearchFrom searchFrom) throws IOException {
        TransportClient client = getClient();
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("@timestamp", "2000-01-01T00:00:00.000Z")
                .field("@version", "1")
                .field("ids", searchFrom.getId())
                .field("key", searchFrom.getKey())
                .field("SearchType", searchFrom.getSearchBusinessType())
                .field("data", searchFrom.getObject())
                .field("createTime", System.currentTimeMillis())
                .endObject();
        IndexRequestBuilder search = client.prepareIndex("search-data", "Search",
                searchFrom.getSearchBusinessType().toString() + searchFrom.getId());
        IndexResponse indexResponse = search.setSource(builder).get();
        System.out.println("status:" + indexResponse.status());

    }
}
