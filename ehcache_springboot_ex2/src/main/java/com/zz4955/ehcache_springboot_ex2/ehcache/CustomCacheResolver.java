package com.zz4955.ehcache_springboot_ex2.ehcache;

import com.zz4955.ehcache_springboot_ex2.annotation.LocalCache;
import com.zz4955.ehcache_springboot_ex2.constant.DelimiterConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class CustomCacheResolver extends AbstractCacheResolver {

    /**
     * 定制cache name的结构为：className.methodName:expireTime
     */
    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        try {
            LocalCache cache;
            Class<?>[] parameterTypes = context.getMethod().getParameterTypes();
            Method method = context.getTarget().getClass().getMethod(context.getMethod().getName(), parameterTypes);
            method = method == null ? context.getMethod() : method;

            if((cache = method.getAnnotation(LocalCache.class)) == null) { // 这里的用法，导致LocalCache要有Inherited注解。
                return context.getOperation().getCacheNames();
            }
            String cacheName = CacheNameParser.parse(String.join(DelimiterConstant.POINT,
                                                        context.getMethod().getDeclaringClass().getCanonicalName(), method.getName()),
                                                cache);
            return Arrays.asList(cacheName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return context.getOperation().getCacheNames();
        }
    }
}
