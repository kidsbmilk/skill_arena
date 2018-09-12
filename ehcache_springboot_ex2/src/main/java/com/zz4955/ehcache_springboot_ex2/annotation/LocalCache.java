package com.zz4955.ehcache_springboot_ex2.annotation;

import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Cacheable(cacheResolver = "customCacheResolver", keyGenerator = "customKeyGenerator")
public @interface LocalCache {

    /**
     * expire time for timeToLive, default timeToIdle is 0, see CustomEhCacheManager.class
     */
    long expire() default 60L;

    /**
     * time unit for expire
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
