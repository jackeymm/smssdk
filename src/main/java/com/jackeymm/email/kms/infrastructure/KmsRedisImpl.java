package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsCacheIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class KmsRedisImpl implements KmsRedis {

    private String cacheKey;
    private ValueOperations valueOps;
    private String cachePre;

    @Autowired
    private RedisTemplate redisTemplate;



    KmsRedisImpl(){}

    KmsRedisImpl(String cachePre, RedisTemplate redisTemplate){
        this.valueOps = redisTemplate.opsForValue();
        this.cachePre = cachePre;
    }

    @Override
    public KeyPair query(String temail) {
        System.out.println("querey ~");
        return null;
    }

    @Override
    public boolean setCache(String key, Object data) {
        if(StringUtils.isEmpty(key) && null == data){
            throw new KmsCacheIsNullException("key & data is null");
        }

        redisTemplate.opsForValue().set(key, data);
        return true;
    }

    @Override
    public Optional getCache(String cacheKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(cacheKey));
    }

}
