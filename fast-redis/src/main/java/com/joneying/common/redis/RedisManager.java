package com.joneying.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 *@Title
 *@Description redis 管理
 *@author Yingjianghua
 *@date 8:45 2018/11/7
 *@param
 *@return
 */
@Repository
public class RedisManager {

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    public void set(String key, Object value) {
        valueOperations.set(key, value);
    }

    public Object get(String key) {
        return valueOperations.get(key);
    }

    /**
     * 设置key-value 并将timeout时间
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        valueOperations.set(key, value, timeout, timeUnit);
    }

    /**
     * 设置key-value 并将timeout秒后过期
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, Object value, long timeout) {
        this.set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 删除一个key-value
     * @param key
     */
    public void remove(String key) {
        valueOperations.getOperations().delete(key);
    }

    /**
     * 查询是否存在key
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return valueOperations.get(key) != null;
    }

    /**
     * 保存key-hash
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Object value) {
        hashOperations.put(key, field, value);
    }

    /**
     * 获取hash中的值
     * @param key
     * @param field
     */
    public Object hget(String key, String field ) {
        return hashOperations.get(key, field);
    }

    /**
     * 判断是否存在key-hash
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    /**
     * 返回key中的所有hash
     * @param key
     * @return
     */
    public Map<String, Object> entries(String key) {
        return hashOperations.entries(key);
    }

    /**
     * 删除key-hash中的某些hash
     * @param key
     * @param field
     */
    public void hremove(String key, String... field) {
        hashOperations.delete(key, field);
    }

    /**
     * 将Map存入key-hash
     * @param key
     * @param m
     */
    public void hsetAll(String key, Map<String, Object> m) {
        hashOperations.putAll(key, m);
    }


}
