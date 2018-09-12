package com.zz4955.ehcache_springboot_ex2.ehcache;

import com.zz4955.ehcache_springboot_ex2.annotation.LocalCache;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CustomEhCacheCacheManager extends EhCacheCacheManager {

    private static final Integer MAX_OBJECT_SIZE_PER_NAMESPACE = 10000;
    private static final Integer TIME_TO_IDLE = 0;
    private final ConcurrentHashMap<String, Ehcache> ehcaches = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        CustomCacheNameContext nameContext = CacheNameParser.parse(name);
        Cache cache = super.getCache(nameContext.getRealCacheName());
        if(cache != null) {
            return cache;
        }

        /**
         * custom expire time by custom annotations
         * @see LocalCache
         */
        if(nameContext.isLocalCache()) {
            log.info("No custom cache with name {}, create dynamically!", nameContext.getRealCacheName());
            net.sf.ehcache.Cache one = new net.sf.ehcache.Cache(nameContext.getRealCacheName(),
                    MAX_OBJECT_SIZE_PER_NAMESPACE, false, false, nameContext.getTimeToLive(), TIME_TO_IDLE);
            getCacheManager().addCache(one);
            log.info("Custom Cache with name {} is created dynamically!", nameContext.getRealCacheName());
            EhCacheCache ehCacheCache =  new EhCacheCache(one);
            ehcaches.put(name, ehCacheCache.getNativeCache());
            return ehCacheCache;
        }

        /**
         * use raw annotation, set default propertie by ehcache-setting.xml
         * @see org.springframework.cache.annotation.Cacheable
         */
        getCacheManager().addCache(name);
        return getCache(name);
    }

    public Map<String, Ehcache> getNamespaceCacheMap() {
        return ehcaches;
    }
}
