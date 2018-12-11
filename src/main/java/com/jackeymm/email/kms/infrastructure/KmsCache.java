package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsCacheIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;

@Service
public class KmsCache<T> {

    @Autowired
    private KmsRedis kmsRedis;

    private KmsEhCache kmsEhCache;


    public KmsCache(){
        this.kmsEhCache = new KmsEhCache(10, String.class, KeyPair.class,timeToLiveExpiration(Duration.ofDays(1)));
    }

    public boolean setCache(T data){
        if(null == data){
            throw new KmsCacheIsNullException("cache is null");
        }
        if(data instanceof KeyPair){
            kmsRedis.setCache(((KeyPair) data).getTemail(), data);

            kmsEhCache.put("test", data);

            Optional ol = kmsEhCache.get("test");
            System.out.println(ol.isPresent());
        }
        return true;
    }

    public Optional getCache(String cacheKey){
        Optional<KeyPair> result = kmsEhCache.get(cacheKey);
        if(!result.isPresent()){
            result = kmsRedis.getCache(cacheKey);
            if(result.isPresent()){
                kmsEhCache.put(cacheKey, result.get());
            }
        }
        return  result;
    }
}
