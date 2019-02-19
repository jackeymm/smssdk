package com.jackeymm.email.kms.exceptions;

public class KmsTenantNoFoundException extends RuntimeException {
    public KmsTenantNoFoundException(String message) {
        super(message);
    }
}
