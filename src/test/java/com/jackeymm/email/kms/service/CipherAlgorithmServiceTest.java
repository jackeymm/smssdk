package com.jackeymm.email.kms.service;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsGenerateKeyPiarParamCheckException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CipherAlgorithmServiceTest {

    private CipherAlgorithmService cipherAlgorithmService = new CipherAlgorithmService();
    private String RSA = "RSA";

    @Test(expected = KmsGenerateKeyPiarParamCheckException.class)
    public void generateKeyPairFailed(){
        cipherAlgorithmService.generateKey(null, null);
    }

    @Test
    public void generateKeyPairSuccessfully(){
        KeyPair keyPair = cipherAlgorithmService.generateKey("kmsToken", "a@temail");
        assertThat(keyPair).isNotNull();
        assertThat(keyPair.getPrivateKey()).isNotNull();
        assertThat(keyPair.getPublicKey()).isNotNull();
    }

    @Test
    public void generateDifferentKeyPairByDifferentTemail(){
        KeyPair keyPair1 = cipherAlgorithmService.generateKey("kmsToken", "a@temail");
        KeyPair keyPair2 = cipherAlgorithmService.generateKey("kmsToken", "ab@temail");
        assertThat(keyPair1).isNotNull();
        assertThat(keyPair2).isNotNull();
        assertThat(keyPair1.getPrivateKey()).isNotEqualTo(keyPair2.getPrivateKey());
        assertThat(keyPair1.getPublicKey()).isNotEqualTo(keyPair2.getPublicKey());


    }

}
