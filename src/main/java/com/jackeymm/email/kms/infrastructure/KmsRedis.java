package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;

import java.util.Optional;

public interface KmsRedis<T> {

    KeyPair query(String temail);

    boolean setCache(String cacheKey, T data);

    Optional<KeyPair> getCache(String cacheKey);
}
