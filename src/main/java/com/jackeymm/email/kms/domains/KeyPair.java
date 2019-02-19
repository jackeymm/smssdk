package com.jackeymm.email.kms.domains;

import java.io.Serializable;

public class KeyPair implements Serializable {
    private Long id;
    private final String privateKey;
    private final String publicKey;
    private final String token;
    private final String email;
    private final Long createTime;
    private final Long updateTime;

    KeyPair(){
        this.id = null;
        this.privateKey = null;
        this.updateTime = null;
        this.publicKey = null;
        this.token = null;
        this.email = null;
        this.createTime = null;
    }

    public KeyPair(String token, String email){
        this.id = null;
        this.privateKey = null;
        this.updateTime = null;
        this.publicKey = null;
        this.token = token;
        this.email = email;
        this.createTime = null;
    }

    public KeyPair(Long id, String publicKey, String privateKey, String token, String email, Long createTime, Long updateTime) {
        this.id = id;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.token = token;
        this.email = email;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public KeyPair(String publicKey, String privateKey, String token, String email, Long createTime, Long updateTime) {
        this.id = null;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.token = token;
        this.email = email;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public Long getId() {
        return id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
