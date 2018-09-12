package com.zz4955.ehcache_springboot_ex2.ehcache;

import com.zz4955.ehcache_springboot_ex2.annotation.LocalCache;
import com.zz4955.ehcache_springboot_ex2.constant.DelimiterConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CacheNameParser {

    public static CustomCacheNameContext parse(String cacheName) {
        try {
            if(StringUtils.isEmpty(cacheName)) {
                throw new RuntimeException("cache name should not be empty!");
            }
            if(!cacheName.contains(DelimiterConstant.COLON)) {
                return CustomCacheNameContext.defaultContext(cacheName);
            }
            String[] nameInfos = cacheName.split(DelimiterConstant.COLON);
            CustomCacheNameContext context = new CustomCacheNameContext(nameInfos[0],
                    Long.parseLong(nameInfos[1]));
            log.info("custom name context is {}", context);
            return context;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static String parse(String realCacheName, LocalCache localCache) {
        return String.join(DelimiterConstant.COLON, realCacheName,
                        String.valueOf(localCache.timeUnit().toSeconds(localCache.expire()))
                );
    }
}
