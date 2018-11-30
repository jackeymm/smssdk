package com.jackeymm.email.kms;

import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map.Entry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class KmsServiceTest {

    private final ChiperAlgorithm algorithm = Mockito.mock(ChiperAlgorithm.class);
    private final PublicKey publicKey = Mockito.mock(PublicKey.class);
    private final PrivateKey privateKey = Mockito.mock(PrivateKey.class);
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
        Entry<String, String> entry = kmsService.queryKeyPair("a@t.email");
        assertThat(entry).isNull();
    }

    @Test
    public void queryKeyPairSuccessfully() {
        when(kmsRedisService.query(any(String.class))).thenReturn(new Entry<String, String>() {
            public String getKey() {
                return null;
            }

            public String getValue() {
                return publicKey.toString();
            }

            public String setValue(String value) {
                return null;
            }
        });
        KeyPairEntry entry = kmsService.queryKeyPair("a@t.email");
        assertThat(entry).isNotNull();
        assertThat(entry.getValue()).isEqualTo(publicKey.toString());

    }



}
