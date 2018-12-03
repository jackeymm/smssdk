package com.jackeymm.email.kms;

public class KeyPair {
    private final String publicKey;
    private final String privateKey;

    public KeyPair(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublic() {
        return publicKey;
    }

    public String getPrivate() {
        return privateKey;
    }
}
