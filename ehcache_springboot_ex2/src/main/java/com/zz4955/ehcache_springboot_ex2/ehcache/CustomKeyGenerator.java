package com.zz4955.ehcache_springboot_ex2.ehcache;

import com.zz4955.ehcache_springboot_ex2.constant.DelimiterConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.stream.Stream;

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
