package com.jackeymm.email.kms.infrastructure;

import java.util.Optional;

public interface KCache<K, T> {

    public boolean setCache(K key, T data);

    public Optional getCache(K key);


}
