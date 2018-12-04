package com.jackeymm.email.kms.service;

import com.jackeymm.email.kms.KeyPair;
import org.springframework.stereotype.Service;

public interface KmsRedisService {
    KeyPair query(String temail);
}
