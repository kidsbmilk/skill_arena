package com.zz4955.ehcache_springboot_ex2.ehcache;

import com.zz4955.ehcache_springboot_ex2.annotation.LocalCache;
import com.zz4955.ehcache_springboot_ex2.constant.DelimiterConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * CustomCacheResolver是生成命名空间的，以类名.方法名.超时时间来生成，可能会有重载的方法缓存在同一个命名空间里。
 * 而CustomKeyGenerator里是生成命名空间中的键值对的键的，是具体对象缓存时的键，如果以用户id来参与生成的话，可以做到用户缓存隔离。
 * 上次我以随机值来参与生成CustomKeyGenerator了，导致每次请求时都是不同的键，当然得不到想要的缓存数据了，而且每次都会缓存一个新的。
 */
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
