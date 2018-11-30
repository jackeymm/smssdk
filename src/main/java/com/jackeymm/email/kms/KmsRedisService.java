package com.jackeymm.email.kms;


import java.util.Map.Entry;

public interface KmsRedisService {
    Entry<String, String> query(String temail);
}
