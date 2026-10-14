/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public interface SSLContextInitializer {
    public SSLContext initSSLContext(String var1) throws GeneralSecurityException;

    public TrustManager[] getTrustManagers() throws GeneralSecurityException;

    public void setTrustManagers(TrustManager ... var1);

    public KeyManager[] getKeyManagers() throws GeneralSecurityException;
}

