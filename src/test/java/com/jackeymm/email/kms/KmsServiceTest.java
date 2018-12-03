package com.jackeymm.email.kms;

import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map.Entry;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class KmsServiceTest {

    private final CipherAlgorithm algorithm = Mockito.mock(CipherAlgorithm.class);
    private final String publicKey = "publicKey";
    private final String privateKey = "privateKey";
    private final KmsRedisService kmsRedisService = Mockito.mock(KmsRedisService.class);
    private final KmsService kmsService = new KmsService(algorithm, kmsRedisService);
    private final Entry entry = Mockito.mock(Entry.class);

    @Test(expected = KmsTenantNoFoundException.class)
    public void blowsUpWhenTokenVerificationFailed() {
        kmsService.register("abc", "a@t.email");
    }

    @Test
    public void shouldRegisterSuccessfully() {
        when(algorithm.generateKey()).thenReturn(new KeyPair(publicKey, privateKey));
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
        when(kmsRedisService.query(any(String.class))).thenReturn(new KeyPair(publicKey, privateKey));
        Optional<KeyPair> entry = kmsService.queryKeyPair("a@t.email");
        assertThat(entry).isPresent();
        assertThat(entry.get().getPublic()).isEqualTo(publicKey);

    }



}
