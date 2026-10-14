/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

import org.apache.wss4j.common.SignatureEncryptionActionToken;

public class SignatureActionToken
extends SignatureEncryptionActionToken {
    private String c14nAlgorithm;
    private boolean useSingleCert = true;
    private String signatureAlgorithm;

    public String getC14nAlgorithm() {
        return this.c14nAlgorithm;
    }

    public void setC14nAlgorithm(String c14nAlgorithm) {
        this.c14nAlgorithm = c14nAlgorithm;
    }

    public boolean isUseSingleCert() {
        return this.useSingleCert;
    }

    public void setUseSingleCert(boolean useSingleCert) {
        this.useSingleCert = useSingleCert;
    }

    public String getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }
}

