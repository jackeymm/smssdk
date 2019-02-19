package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsCacheIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;

@Service
public class KmsCache<K, V> implements KCache<K, V> {

    @Autowired
    private KmsRedisImpl kmsRedis;

    private KmsEhCache kmsEhCache;


    public KmsCache(){
        this.kmsEhCache = new KmsEhCache(10, String.class, KeyPair.class,timeToLiveExpiration(Duration.ofSeconds(1000)));
    }

    public boolean setCache(K key, V data){
        if(null == data){
            throw new KmsCacheIsNullException("cache is null");
        }
        if(data instanceof KeyPair){
            kmsRedis.setCache(key, data);

            kmsEhCache.setCache("test", data);

            Optional ol = kmsEhCache.getCache("test");
            System.out.println(ol.isPresent());
        }
        return true;
    }

    public Optional getCache(K cacheKey){
        Optional<KeyPair> result = kmsEhCache.getCache(cacheKey);
        if(!result.isPresent()){
            result = kmsRedis.getCache(cacheKey);
            if(result.isPresent()){
                kmsEhCache.setCache(cacheKey, result.get());
            }
        }
        return  result;
    }
}
