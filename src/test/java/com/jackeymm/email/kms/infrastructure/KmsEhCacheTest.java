package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;

@RunWith(SpringRunner.class)
public class KmsEhCacheTest {

    private KmsEhCache<String, KeyPair> kmsEhCache;

    private final String key = "test";

    @Before
    public void init(){
        this.kmsEhCache = new KmsEhCache<>(10, String.class, KeyPair.class,timeToLiveExpiration(Duration.ofSeconds(10)));
    }
    

    @Test
    public void userEhCacheGetValueSuccessfully(){
        KeyPair keyPair = new KeyPair("token","a@t.email");
        kmsEhCache.setCache(this.key, keyPair);


        Optional<KeyPair> ok = kmsEhCache.getCache(this.key);
        assertThat(ok).isPresent();
        assertThat(ok.get()).isEqualToComparingFieldByField(keyPair);

    }
}
