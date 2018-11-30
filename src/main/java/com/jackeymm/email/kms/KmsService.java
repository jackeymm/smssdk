package com.jackeymm.email.kms;

import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;

import java.security.KeyPair;
import java.util.Map.Entry;

public class KmsService {

    private ChiperAlgorithm algorithm;
    private KmsRedisService kmsRedisService;

    public KmsService(ChiperAlgorithm algorithm, KmsRedisService kmsRedisService) {
        this.algorithm = algorithm;
        this.kmsRedisService = kmsRedisService;
    }

    public KeyPair register(String token, String temail) {
        if (!"syswin".equals(token)) {
            throw new KmsTenantNoFoundException();
        }
        return this.algorithm.generateKey();
    }

    public Entry queryKeyPair(String temail) {
        Entry entry = kmsRedisService.query(temail);
        return entry;
    }
}
