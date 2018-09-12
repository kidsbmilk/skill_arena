package com.zz4955.ehcache_springboot_ex2.service;

import com.zz4955.ehcache_springboot_ex2.annotation.LocalCache;
import com.zz4955.ehcache_springboot_ex2.bean.Tasklog;
import com.zz4955.ehcache_springboot_ex2.ehcache.CustomEhCacheCacheManager;
import com.zz4955.ehcache_springboot_ex2.ehcache.LocalCacheConfiguration;
import com.zz4955.ehcache_springboot_ex2.mapper.TaskLogMapper;
import com.zz4955.ehcache_springboot_ex2.tool.CommonUtils;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Autowired
    private LocalCacheConfiguration localCacheConfiguration;

    private EhCacheCacheManager ehCacheCacheManager;

    /**
     * 缓存的key
     */
    public static final String CACHE_KEY = "cacheBeanName";

    /**
     * 添加tasklog
     *
     * @param tasklog
     * @return
     */
    @LocalCache(expire = 20L)
//    @CachePut(value = CACHE_KEY, key = "#tasklog.id")
    public Tasklog create(Tasklog tasklog) {
        System.out.println("CREATE");
        System.out.println(tasklog);
        taskLogMapper.insert(tasklog);
        return tasklog;
    }

    /**
     * 根据ID获取Tasklog
     *
     * @param id
     * @return
     */
    @LocalCache(expire = 5L)
//    @Cacheable(value = CACHE_KEY, key = "#id")
    public Tasklog findById(String id) {
        System.out.println("FINDBYID");
        System.out.println("ID:" + id);
        return taskLogMapper.selectById(id);
    }

    public TaskLogService() {
        super();
    }

    /**
     * 根据ID更新Tasklog
     *
     * @param tasklog
     * @return
     */
    @LocalCache(expire = 5L)
//    @CachePut(value = CACHE_KEY, key = "#tasklog.id")
    public Tasklog update(Tasklog tasklog) {
        System.out.println("UPDATE");
        System.out.println(tasklog);
        Integer result = taskLogMapper.updateById(tasklog);
        if(result == 0) {
            create(tasklog);
        }
        return tasklog;
    }

    /**
     * 根据ID删除Tasklog
     *
     * @param id
     */
//    @CacheEvict(value = CACHE_KEY, key = "#id")
    public void delete(String id) {
        System.out.println("DELETE");
        System.out.println("ID:" + id);
        taskLogMapper.deleteById(id);
    }


    /**
     * LocalCache创建了很多命名空间：这里需要得到所有命名空间中的键值对。
     */
    public void getCache() {
        System.out.println("GETCACHE");
        ehCacheCacheManager = localCacheConfiguration.ehCacheCacheManager();
        List<String> names = new ArrayList<>();
        names.addAll(ehCacheCacheManager.getCacheNames());
        Map<String, Ehcache> ehcacheMap = ((CustomEhCacheCacheManager)ehCacheCacheManager).getNamespaceCacheMap();
        for(String key : ehcacheMap.keySet()) {
            Ehcache cache = ehcacheMap.get(key);
            Map<Object, Element> elements = cache.getAll(cache.getKeys());
            if (elements != null) {
                for (Map.Entry<Object, Element> entry : elements.entrySet()) {
                    System.out.println("key= " + entry.getValue().getObjectKey());
                    System.out.println("value= " + entry.getValue().getObjectValue());
                }
            }
        }
    }

//    @Cacheable、@CachePut、@CacheEvict 注释介绍
//    @Cacheable 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
//    @CachePut 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
//    @CachEvict 的作用 主要针对方法配置，能够根据一定的条件对缓存进行清空
}