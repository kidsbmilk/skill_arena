package com.zz4955.ehcache_springboot_ex.service;

import com.zz4955.ehcache_springboot_ex.bean.Tasklog;
import com.zz4955.ehcache_springboot_ex.mapper.TaskLogMapper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Autowired
    private net.sf.ehcache.CacheManager cacheManager;

    /**
     * 缓存的key
     */
    public static final String CACHE_KEY = "taskLog";

    /**
     * 添加tasklog
     *
     * @param tasklog
     * @return
     */
    @CachePut(value = CACHE_KEY, key = "#tasklog.id")
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
    @Cacheable(value = CACHE_KEY, key = "#id")
    public Tasklog findById(String id) {
        System.out.println("FINDBYID");
        System.out.println("ID:" + id);
        return taskLogMapper.selectById(id);
    }

    /**
     * 根据ID更新Tasklog
     *
     * @param tasklog
     * @return
     */
    @CachePut(value = CACHE_KEY, key = "#tasklog.id")
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
    @CacheEvict(value = CACHE_KEY, key = "#id")
    public void delete(String id) {
        System.out.println("DELETE");
        System.out.println("ID:" + id);
        taskLogMapper.deleteById(id);
    }


    public void getCache() {
        System.out.println("GETCACHE");
        Cache cache = cacheManager.getCache(CACHE_KEY);
        Map<Object, Element> elements = cache.getAll(cache.getKeys());
        if (elements != null) {
            for (Map.Entry<Object, Element> entry : elements.entrySet()) {
                System.out.println("key= " + entry.getValue().getObjectKey());
                System.out.println("value= " + entry.getValue().getObjectValue());
            }
        }
    }

//    @Cacheable、@CachePut、@CacheEvict 注释介绍
//    @Cacheable 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
//    @CachePut 的作用 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
//    @CachEvict 的作用 主要针对方法配置，能够根据一定的条件对缓存进行清空
}