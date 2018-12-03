package com.jackeymm.email.kms;

import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import com.jackeymm.email.kms.infrastructure.UserKeypairRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map.Entry;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class KmsServiceTest {

    private final CipherAlgorithm algorithm = Mockito.mock(CipherAlgorithm.class);
    private final String publicKey = "publicKey";
    private final String privateKey = "privateKey";
    private final KmsRedisService kmsRedisService = Mockito.mock(KmsRedisService.class);
    private final UserKeypairRepository userKeypairRepository = Mockito.mock(UserKeypairRepository.class);

    private final KmsService kmsService = new KmsService(algorithm, kmsRedisService ,userKeypairRepository);

    @Test(expected = KmsTenantNoFoundException.class)
    public void blowsUpWhenTokenVerificationFailed() {
        kmsService.register("abc", "a@t.email");
    }

    @Test
    public void shouldRegisterSuccessfully() {
        when(algorithm.generateKey()).thenReturn(new KeyPair(1L, publicKey, privateKey, "token", "temail", 0L, 0L));
        KeyPair keyPair = kmsService.register("syswin", "a@t.email");

        assertThat(keyPair).isNotNull();
        assertThat(keyPair.getPublic()).isEqualTo(publicKey);
        assertThat(keyPair.getPrivate()).isEqualTo(privateKey);
    }

    @Test
    public void queryKeyPairNotFound() {
        when(kmsRedisService.query(any(String.class))).thenReturn(null);
        Optional<KeyPair> entry = kmsService.queryKeyPair("a@t.email");
        assertThat(entry).isNotPresent();
    }

    @Test
    public void queryKeyPairSuccessfully() {
        when(kmsRedisService.query(any(String.class))).thenReturn(new KeyPair(1L, publicKey, privateKey, "token", "temail", 0L, 0L));
        Optional<KeyPair> entry = kmsService.queryKeyPair("a@t.email");
        assertThat(entry).isPresent();
        assertThat(entry.get().getPublic()).isEqualTo(publicKey);

    }



}
