package com.zz4955.ehcache_springboot_ex2.ehcache;

import lombok.Data;

@Data
public class CustomCacheNameContext {

    private String realCacheName;
    private long timeToLive;
    private boolean localCache;

    public CustomCacheNameContext(String realCacheName, long timeToLive) {
        this(realCacheName, timeToLive, true);
    }

    public CustomCacheNameContext(String realCacheName, long timeToLive, boolean localCache) {
        this.realCacheName = realCacheName;
        this.timeToLive = timeToLive;
        this.localCache = localCache;
    }

    public static CustomCacheNameContext defaultContext(String realCacheName) {
        return new CustomCacheNameContext(realCacheName, Integer.MAX_VALUE, false);
    }
}
