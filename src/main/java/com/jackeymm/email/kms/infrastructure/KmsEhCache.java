package com.jackeymm.email.kms.infrastructure;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KmsEhCache<K, V> implements KCache<K, V> {

    private CacheManager cacheManager;
    private static final String CACHE_NAME = "EhCache-kms";
    private String cacheName;
    private Cache<K, V> cache;

    public KmsEhCache(){}

    public KmsEhCache(int entries, Class<K> keyType, Class<V> valueType, ExpiryPolicy<? super K, ? super V> expiryPolicy){
        this.cacheName = CACHE_NAME;
        this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();
        this.cache = createCache(this.cacheName,entries, keyType, valueType, expiryPolicy);
    }

    private Cache<K, V> createCache(String cacheName, int entries, Class<K> key,Class<V> value, ExpiryPolicy<? super K, ? super V> expiryPolicy){
        CacheConfiguration<K, V> cacheConfiguration =
                CacheConfigurationBuilder.
                    newCacheConfigurationBuilder(key, value,  ResourcePoolsBuilder.heap(entries))
                    .withExpiry(expiryPolicy)
                    .build();

        return cacheManager.createCache(cacheName, cacheConfiguration);
    }

    @Override
    public boolean setCache(K key, V value) {
        cache.put(key, value);
        return true;
    }

    @Override
    public Optional getCache(K key) {
        return Optional.ofNullable(cache.get(key));
    }
}
