package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsCacheIsNullException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:me6379m:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class KmsCacheTest {

    @Autowired
    private KmsCache kmsCache;

    private KmsEhCache kmsEhCache;

    @Autowired
    private KmsRedisImpl kmsRedis;

    @Before
    public void init(){
        this.kmsEhCache = new KmsEhCache(10, String.class, KeyPair.class,timeToLiveExpiration(Duration.ofSeconds(1)));
    }

    @Test(expected = KmsCacheIsNullException.class)
    public void setKeyPairCacheFailed(){
        kmsCache.setCache(null, null);
    }

    @Test
    public void setKeyPairCacheSuccessfully(){
        KeyPair keyPair = new KeyPair(1L,"pubk", "priK","tokan","a@email", 0L,0L);
        boolean setCacheResult = kmsCache.setCache(keyPair.getEmail(), keyPair);
        assertThat(setCacheResult).isEqualTo(true);

        Optional op = kmsCache.getCache(keyPair.getEmail());
        assertThat(op.isPresent()).isEqualTo(true);
        assertThat(op.get()).isEqualToComparingFieldByField(keyPair);

        Optional opRedis = kmsRedis.getCache(keyPair.getEmail());
        assertThat(opRedis.isPresent()).isEqualTo(true);
        assertThat(opRedis.get()).isEqualToComparingFieldByField(keyPair);

//        Optional opEh = kmsEhCache.get(keyPair.getEmail());
//        assertThat(opEh.isPresent()).isEqualTo(true);
//        assertThat(opEh.get()).isEqualToComparingFieldByField(keyPair);


    }


}
