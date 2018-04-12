package com.d4smart.traveller.dao.cache;

import com.d4smart.traveller.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by d4smart on 2018/3/29 10:47
 */
@Component
public class RedisDao {

    private final JedisPool jedisPool = new JedisPool(
            PropertiesUtil.getProperty("redis.ip", "127.0.0.1"),
            Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379")));

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 给内容点赞，成功会对likes +1
     * @param key 内容的key
     * @param userId 用户id
     * @return 结果
     */
    public Boolean like(String key, Integer userId) {
        String value = userId.toString();

        try {
            Jedis jedis = jedisPool.getResource();

            try {
                if (jedis.sismember(key, value)) {
                    return false;
                }

                jedis.sadd(key, value);
                return true;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return false;
    }

    /**
     * 取消用户给内容的赞，成功会对likes -1
     * @param key 内容的key
     * @param userId 用户id
     * @return 结果
     */
    public Boolean unlike(String key, Integer userId) {
        String value = userId.toString();

        try {
            Jedis jedis = jedisPool.getResource();

            try {
                if (!jedis.sismember(key, value)) {
                    return false;
                }

                jedis.srem(key, value);
                return true;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return false;
    }
}
