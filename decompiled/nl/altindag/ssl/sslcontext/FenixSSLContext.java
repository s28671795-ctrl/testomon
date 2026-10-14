/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.sslcontext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import nl.altindag.ssl.sslcontext.FenixSSLContextSpi;

public class FenixSSLContext
extends SSLContext {
    public FenixSSLContext(SSLContext baseSslContext, SSLParameters baseSslParameters) {
        super(new FenixSSLContextSpi(baseSslContext, baseSslParameters), baseSslContext.getProvider(), baseSslContext.getProtocol());
    }
}

