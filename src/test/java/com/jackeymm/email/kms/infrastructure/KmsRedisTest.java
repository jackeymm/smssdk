package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsCacheIsNullException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:me6379m:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class KmsRedisTest {


    @Autowired
    private KmsRedisImpl kmsRedis;

    @Test(expected = KmsCacheIsNullException.class)
    public void setRedisfaled(){
        kmsRedis.setCache(null, null);
    }

    @Test
    public void setRedisSuccessfully(){
        KeyPair keyPair = new KeyPair(1L,"pubk", "priK","tokan","a@temail", 0L,0L);
        String cacheKey = "test" + Math.random();
        System.out.println("cacheKey : " + cacheKey);
        boolean setCacheResult = kmsRedis.setCache(cacheKey,keyPair);
        assertThat(setCacheResult).isEqualTo(true);

        Optional<KeyPair> optionalKeyPair = kmsRedis.getCache(cacheKey);
        assertThat(optionalKeyPair.isPresent()).isEqualTo(true);
        assertThat(optionalKeyPair.get()).isEqualToComparingFieldByField(keyPair);

    }


}
