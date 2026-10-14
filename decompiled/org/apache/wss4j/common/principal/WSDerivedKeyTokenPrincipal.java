/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.io.Serializable;
import java.security.Principal;

public class WSDerivedKeyTokenPrincipal
implements Serializable,
Principal {
    private static final long serialVersionUID = -8576876885462234466L;
    private String algorithm;
    private String id;
    private String nonce;
    private String label;
    private int length;
    private int offset;
    private String basetokenId;
    private transient byte[] secret;

    public WSDerivedKeyTokenPrincipal(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getSecret() {
        return this.secret;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getNonce() {
        return this.nonce;
    }

    @Override
    public String getName() {
        return this.id;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBasetokenId() {
        return this.basetokenId;
    }

    public void setBasetokenId(String basetokenId) {
        this.basetokenId = basetokenId;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}

