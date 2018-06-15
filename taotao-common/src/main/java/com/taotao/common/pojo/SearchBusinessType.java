package com.taotao.common.pojo;

public enum SearchBusinessType {
    Content, Item;

    public static SearchBusinessType getInstance(String searchType) {
        if (Content.name().equals(searchType)) {
            return Content;
        } else if (Item.name().equals(searchType)) {
            return Item;
        } else {
            throw new RuntimeException("not find searchType");
        }
    }
}
