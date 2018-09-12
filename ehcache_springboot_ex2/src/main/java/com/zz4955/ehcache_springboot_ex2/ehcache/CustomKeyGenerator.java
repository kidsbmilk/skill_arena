package com.zz4955.ehcache_springboot_ex2.ehcache;

import com.zz4955.ehcache_springboot_ex2.constant.DelimiterConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * CustomCacheResolver是生成命名空间的，以类名.方法名.超时时间来生成，可能会有重载的方法缓存在同一个命名空间里。
 * 而CustomKeyGenerator里是生成命名空间中的键值对的键的，是具体对象缓存时的键，如果以用户id来参与生成的话，可以做到用户缓存隔离。
 * 上次我以随机值来参与生成CustomKeyGenerator了，导致每次请求时都是不同的键，当然得不到想要的缓存数据了，而且每次都会缓存一个新的。
 */
@Slf4j
public class CustomKeyGenerator implements KeyGenerator {

    private static final String PREFIX_KEY = StringUtils.join("UCID", DelimiterConstant.COLON);

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }

    /**
     * Generate a key based on the specified parameters and user id.
     * <p>
     *     example UCID:121#String:red_Integer:22_Long:100
     * </p>
     * ThreadContext.get("logId")，这个不能是随机的，可以以用户id来设置，实现用户缓存隔离的效果。
     */
    public static Object generateKey(Object... params) {
        StringBuilder sb = new StringBuilder().append(PREFIX_KEY).append(1)
                .append(DelimiterConstant.POUND_SIGN);

        try {
            if(params != null) {
                sb.append(Stream.of(params)
                            .map(item -> StringUtils.join(item.getClass().getSimpleName(), DelimiterConstant.COLON, item))
                            .reduce((a, b) -> String.join(DelimiterConstant.UNDER_LINE, a, b))
                            .get());
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        log.info("generated cached key is {}", sb.toString());
        return sb.toString();
    }
}
