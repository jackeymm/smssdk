package com.jackeymm.email.kms.service;

import com.jackeymm.email.kms.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsSystemException;
import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import com.jackeymm.email.kms.infrastructure.UserKeypairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KmsService {

    @Autowired
    private CipherAlgorithm algorithm;

    @Autowired
    private KmsRedisService kmsRedisService;

    @Autowired
    private UserKeypairRepository userKeypairRepository;

    public KmsService(CipherAlgorithm algorithm, KmsRedisService kmsRedisService,UserKeypairRepository userKeypairRepository) {
        this.algorithm = algorithm;
        this.kmsRedisService = kmsRedisService;
        this.userKeypairRepository = userKeypairRepository;
    }

    public KeyPair register(String token, String temail) {
        this.checkToken(token);
        KeyPair keyPair = this.algorithm.generateKey(token, temail);

        int result = userKeypairRepository.register(keyPair);

        if(1 == result){
            return keyPair;
        }else{
            throw new KmsSystemException("register failed ~~");
        }
    }

    public Optional<KeyPair> queryKeyPair(String token,String temail) {
        this.checkToken(token);
        KeyPair keyPair = new KeyPair(token, temail);
        KeyPair entry = userKeypairRepository.getByKeyPair(keyPair);
        return Optional.ofNullable(entry);
    }

    private void checkToken(String token){
        if (!"syswin".equals(token)) {
            throw new KmsTenantNoFoundException("token is not exist~");
        }
    }
}
