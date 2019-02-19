package com.jackeymm.email.kms.interfaces;

public class UserKeyPariInput {

    private final String domain;

    private final String email;


    public UserKeyPariInput(String domain, String email) {
        this.domain = domain;
        this.email = email;
    }

    public String getDomain() {
        return domain;
    }

    public String getEmail() {
        return email;
    }
}
