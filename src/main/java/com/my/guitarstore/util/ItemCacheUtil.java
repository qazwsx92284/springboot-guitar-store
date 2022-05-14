package com.my.guitarstore.util;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
public class ItemCacheUtil implements IRequestCache {

    private static final String MAP_NAME = "com.my.guitarstore.map.v1.itemMap";

    //public final Map<String, String> cacheMap;

    @Autowired
    public ItemCacheUtil() {
    //    this.cacheMap = new DistributedMap.
    }

    @Override
    public void setRequestInProgress(String method, String path, String itemId) {

    }

    @Override
    public boolean isRequestInProgress(String method, String path, String itemId) {
        return false;
    }

    @Override
    public void setRequestDone(String method, String path, String itemId) {

    }
}
