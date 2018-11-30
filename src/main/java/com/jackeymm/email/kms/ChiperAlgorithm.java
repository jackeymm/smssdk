package com.jackeymm.email.kms;

import java.security.KeyPair;

public interface ChiperAlgorithm {
    KeyPair generateKey();
}
