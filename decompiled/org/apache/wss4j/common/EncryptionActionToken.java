/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

import org.apache.wss4j.common.SignatureEncryptionActionToken;

public class EncryptionActionToken
extends SignatureEncryptionActionToken {
    private boolean encSymmetricEncryptionKey = true;
    private String mgfAlgorithm;
    private String symmetricAlgorithm;
    private String keyTransportAlgorithm;
    private boolean getSymmetricKeyFromCallbackHandler;

    public boolean isEncSymmetricEncryptionKey() {
        return this.encSymmetricEncryptionKey;
    }

    public void setEncSymmetricEncryptionKey(boolean encSymmetricEncryptionKey) {
        this.encSymmetricEncryptionKey = encSymmetricEncryptionKey;
    }

    public String getMgfAlgorithm() {
        return this.mgfAlgorithm;
    }

    public void setMgfAlgorithm(String mgfAlgorithm) {
        this.mgfAlgorithm = mgfAlgorithm;
    }

    public String getSymmetricAlgorithm() {
        return this.symmetricAlgorithm;
    }

    public void setSymmetricAlgorithm(String symmetricAlgorithm) {
        this.symmetricAlgorithm = symmetricAlgorithm;
    }

    public String getKeyTransportAlgorithm() {
        return this.keyTransportAlgorithm;
    }

    public void setKeyTransportAlgorithm(String keyTransportAlgorithm) {
        this.keyTransportAlgorithm = keyTransportAlgorithm;
    }

    public boolean isGetSymmetricKeyFromCallbackHandler() {
        return this.getSymmetricKeyFromCallbackHandler;
    }

    public void setGetSymmetricKeyFromCallbackHandler(boolean getSymmetricKeyFromCallbackHandler) {
        this.getSymmetricKeyFromCallbackHandler = getSymmetricKeyFromCallbackHandler;
    }
}

