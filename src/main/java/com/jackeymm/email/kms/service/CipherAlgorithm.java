package com.jackeymm.email.kms.service;


import com.jackeymm.email.kms.KeyPair;
import org.springframework.stereotype.Service;

@Service
public class CipherAlgorithm {
    public KeyPair generateKey(){

        System.out.println("generateKey~");

        return new KeyPair(1L, "pubk","prik", "token", "temail",0L, 0L);
    }
}
