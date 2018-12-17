package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.exceptions.KmsCacheIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class KmsRedisImpl<K, V> implements KCache<K, V> {


    @Autowired
    private RedisTemplate redisTemplate;



    KmsRedisImpl(){
    }


    @Override
    public boolean setCache(K key, V data) {
        if(StringUtils.isEmpty(key) && null == data){
            throw new KmsCacheIsNullException("key & data is null");
        }

        redisTemplate.opsForValue().set(key, data);
        return true;
    }

    @Override
    public Optional getCache(K cacheKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(cacheKey));
    }

}
