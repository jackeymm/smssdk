package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class UserKeyPairRepositoryTest {

    private final KeyPair keyPair = new KeyPair(1L, "abc", "def", "syswin", "a@temail", 0L, 0L);
    @Autowired
    private UserKeypairRepository userKeyPairRepository;

    @Test
    public void registerUserKeyPairSuccessfully(){
        KeyPair keyPair = new KeyPair(1L, "abc", "def", "syswin", "b@temail", 0L, 0L);
        int result = userKeyPairRepository.register(keyPair);

        assertThat(result).isEqualTo(1);

        KeyPair keyPair1 = userKeyPairRepository.getByKeyPair(keyPair);
        assertThat(keyPair1).isEqualToComparingFieldByField(keyPair);
    }

    @Test(expected = DuplicateKeyException.class)
    public void registerUserKeyPairIsExsist(){
        int firstRegisterResult = userKeyPairRepository.register(keyPair);

        assertThat(firstRegisterResult).isEqualTo(1);

        KeyPair keyPair = userKeyPairRepository.getByKeyPair(this.keyPair);
        assertThat(keyPair).isEqualToComparingFieldByField(this.keyPair);
        //第二次插入，触发唯一索引
        userKeyPairRepository.register(keyPair);

    }

    @Test
    public void queryUserKeyPairInputIsNull(){
        KeyPair keyPair = userKeyPairRepository.getByKeyPair(null);
        assertThat(keyPair).isNull();
    }

    @Test
    public void queryUserKeyPairFailed(){
        KeyPair keyPair = userKeyPairRepository.getByKeyPair(this.keyPair);
        assertThat(keyPair).isNull();
    }


}
