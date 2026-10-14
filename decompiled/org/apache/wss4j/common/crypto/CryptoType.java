/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.math.BigInteger;

public class CryptoType {
    private TYPE type;
    private String issuer;
    private BigInteger serial;
    private byte[] bytes;
    private String subjectDN;
    private String alias;
    private String endpoint;

    public CryptoType() {
    }

    public CryptoType(TYPE type) {
        this.type = type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public TYPE getType() {
        return this.type;
    }

    public void setIssuerSerial(String issuer, BigInteger serial) {
        this.issuer = issuer;
        this.serial = serial;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public BigInteger getSerial() {
        return this.serial;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setSubjectDN(String subjectDN) {
        this.subjectDN = subjectDN;
    }

    public String getSubjectDN() {
        return this.subjectDN;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public static enum TYPE {
        ISSUER_SERIAL,
        THUMBPRINT_SHA1,
        SKI_BYTES,
        SUBJECT_DN,
        ALIAS,
        ENDPOINT;

    }
}

