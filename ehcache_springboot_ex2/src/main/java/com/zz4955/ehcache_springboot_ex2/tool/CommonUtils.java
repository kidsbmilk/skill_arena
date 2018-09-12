package com.zz4955.ehcache_springboot_ex2.tool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {
    /**
     * Obj 转换为 map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> ObjToMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
}
