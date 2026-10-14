/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common;

import java.security.cert.X509Certificate;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;

public interface SecurityActionToken {
    public String getUser();

    public byte[] getKey();

    public X509Certificate getCertificate();

    public Crypto getCrypto() throws WSSecurityException;

    public String getCryptoProperties();
}

