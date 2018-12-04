package com.jackeymm.email.kms.service;

import com.jackeymm.email.kms.KeyPair;
import org.springframework.stereotype.Service;

@Service
public class KmsRedisServiceImpl implements KmsRedisService {
    @Override
    public KeyPair query(String temail) {
        System.out.println("querey ~");
        return null;
    }
}
