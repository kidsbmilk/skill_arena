package com.zz4955.ehcache_springboot_ex.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zz4955.ehcache_springboot_ex.bean.Tasklog;
import org.springframework.stereotype.Component;

@Component
public interface TaskLogMapper extends BaseMapper<Tasklog> {
}
