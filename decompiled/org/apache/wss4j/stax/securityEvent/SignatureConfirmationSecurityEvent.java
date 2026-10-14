/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.securityEvent;

import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;

public class SignatureConfirmationSecurityEvent
extends SecurityEvent {
    private byte[] signatureValue;

    public SignatureConfirmationSecurityEvent() {
        super(WSSecurityEventConstants.SIGNATURE_CONFIRMATION);
    }

    public byte[] getSignatureValue() {
        return this.signatureValue;
    }

    public void setSignatureValue(byte[] signatureValue) {
        this.signatureValue = signatureValue;
    }
}

