package com.jackeymm.email.kms.interfaces;

public class UserKeyPariInput {

    private final String domain;

    private final String temail;


    public UserKeyPariInput(String domain, String temail) {
        this.domain = domain;
        this.temail = temail;
    }

    public String getDomain() {
        return domain;
    }

    public String getTemail() {
        return temail;
    }
}
