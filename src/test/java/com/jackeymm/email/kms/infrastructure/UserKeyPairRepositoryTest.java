package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.KeyPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class UserKeyPairRepositoryTest {

    private final KeyPair keyPair = new KeyPair("abc", "def");
    private final String temail = "a@temail";
    private final String token = "syswin";
    @Autowired
    private UserKeypairRepository userKeyPairRepository;

    @Test
    public void registerUserKeyPairSuccessfully(){
        int result = userKeyPairRepository.register(temail, token, keyPair);

        assertThat(result).isEqualTo(1);

        KeyPair keyPair = userKeyPairRepository.getByTemail(temail, token);
        assertThat(keyPair).isEqualToComparingFieldByField(this.keyPair);
    }
}
