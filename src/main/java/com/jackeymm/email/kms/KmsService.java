package com.jackeymm.email.kms;

import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;

import java.util.Optional;

public class KmsService {

    private CipherAlgorithm algorithm;
    private KmsRedisService kmsRedisService;

    public KmsService(CipherAlgorithm algorithm, KmsRedisService kmsRedisService) {
        this.algorithm = algorithm;
        this.kmsRedisService = kmsRedisService;
    }

    public KeyPair register(String token, String temail) {
        if (!"syswin".equals(token)) {
            throw new KmsTenantNoFoundException();
        }
        return this.algorithm.generateKey();
    }

    public Optional<KeyPair> queryKeyPair(String temail) {
        KeyPair entry = kmsRedisService.query(temail);
        return Optional.ofNullable(entry);
    }
}
