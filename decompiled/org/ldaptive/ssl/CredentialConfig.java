/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.security.GeneralSecurityException;
import org.ldaptive.ssl.SSLContextInitializer;

public interface CredentialConfig {
    public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException;
}

