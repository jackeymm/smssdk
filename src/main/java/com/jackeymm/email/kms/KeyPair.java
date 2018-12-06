package com.jackeymm.email.kms;

public class KeyPair {
    private final Long id;
    private final String privateKey;
    private final String publicKey;
    private final String token;
    private final String temail;
    private final Long createTime;
    private final Long updateTime;

    KeyPair(){
        id = null;
        privateKey = null;
        updateTime = null;
        publicKey = null;
        token = null;
        temail = null;
        createTime = null;
    }

    public KeyPair(Long id, String publicKey, String privateKey, String token, String temail, Long createTime, Long updateTime) {
        this.id = id;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.token = token;
        this.temail = temail;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getPublic() {
        return publicKey;
    }

    public String getPrivate() {
        return privateKey;
    }

    public String getToken() {
        return token;
    }

    public String getTemail() {
        return temail;
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
}
