package com.jackeymm.email.kms.domains;

public class KeyPair {
    private Long id;
    private String privateKey;
    private String publicKey;
    private String token;
    private String temail;
    private Long createTime;
    private Long updateTime;

    public KeyPair(Long id, String publicKey, String privateKey, String token, String temail, Long createTime, Long updateTime) {
        this.id = id;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.token = token;
        this.temail = temail;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
