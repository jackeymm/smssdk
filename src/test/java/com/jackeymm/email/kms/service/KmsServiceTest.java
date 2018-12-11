package com.jackeymm.email.kms.service;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class KmsServiceTest {

    private final String token ="kmsToken";
    private final String temail="a@temail";

    @Autowired
    private KmsService kmsService;

    @Test(expected = KmsTenantNoFoundException.class)
    public void blowsUpWhenTokenVerificationFailed() {
        kmsService.register("abc", "a@t.email");
    }

    @Test
    public void shouldRegisterSuccessfully() {
        KeyPair keyPair = kmsService.register(this.token, "av@temail");
        assertThat(keyPair).isNotNull();
    }

    @Test
    public void queryKeyPairNotFound() {
        Optional<KeyPair> entry = kmsService.queryKeyPair(this.token, "a@t.email");
        assertThat(entry).isNotPresent();
    }

    @Test
    public void queryKeyPairSuccessfully() {
        kmsService.register(token, temail);
        Optional<KeyPair> entry = kmsService.queryKeyPair(token, temail);
        assertThat(entry).isPresent();

    }



}
