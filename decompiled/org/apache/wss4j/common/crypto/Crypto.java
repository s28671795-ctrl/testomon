/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.regex.Pattern;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.ext.WSSecurityException;

public interface Crypto {
    public String getCryptoProvider();

    public void setCryptoProvider(String var1);

    public String getTrustProvider();

    public void setTrustProvider(String var1);

    public String getDefaultX509Identifier() throws WSSecurityException;

    public void setDefaultX509Identifier(String var1);

    public void setCertificateFactory(CertificateFactory var1);

    public CertificateFactory getCertificateFactory() throws WSSecurityException;

    public X509Certificate loadCertificate(InputStream var1) throws WSSecurityException;

    public byte[] getSKIBytesFromCert(X509Certificate var1) throws WSSecurityException;

    public byte[] getBytesFromCertificates(X509Certificate[] var1) throws WSSecurityException;

    public X509Certificate[] getCertificatesFromBytes(byte[] var1) throws WSSecurityException;

    public X509Certificate[] getX509Certificates(CryptoType var1) throws WSSecurityException;

    public String getX509Identifier(X509Certificate var1) throws WSSecurityException;

    public PrivateKey getPrivateKey(X509Certificate var1, CallbackHandler var2) throws WSSecurityException;

    public PrivateKey getPrivateKey(PublicKey var1, CallbackHandler var2) throws WSSecurityException;

    public PrivateKey getPrivateKey(String var1, String var2) throws WSSecurityException;

    public void verifyTrust(X509Certificate[] var1, boolean var2, Collection<Pattern> var3, Collection<Pattern> var4) throws WSSecurityException;

    public void verifyTrust(PublicKey var1) throws WSSecurityException;
}

