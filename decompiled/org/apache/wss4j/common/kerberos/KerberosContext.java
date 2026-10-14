/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

import java.security.Key;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KerberosContext {
    private static final Logger LOG = LoggerFactory.getLogger(KerberosContext.class);
    private boolean disposed;
    private GSSContext gssContext;
    private byte[] kerberosToken;
    private Key secretKey;

    public byte[] getKerberosToken() {
        if (this.disposed) {
            throw new IllegalStateException("Kerberos context is disposed.");
        }
        return this.kerberosToken;
    }

    public void setKerberosToken(byte[] kerberosToken) {
        this.kerberosToken = kerberosToken;
    }

    public Key getSecretKey() {
        if (this.disposed) {
            throw new IllegalStateException("Kerberos context is disposed.");
        }
        return this.secretKey;
    }

    public void setSecretKey(Key secretKey) {
        this.secretKey = secretKey;
    }

    public GSSContext getGssContext() {
        if (this.disposed) {
            throw new IllegalStateException("Kerberos context is disposed.");
        }
        return this.gssContext;
    }

    public void setGssContext(GSSContext gssContext) {
        this.gssContext = gssContext;
    }

    public void dispose() {
        if (!this.disposed) {
            if (this.kerberosToken != null) {
                for (int i = 0; i < this.kerberosToken.length; ++i) {
                    this.kerberosToken[i] = 0;
                }
            }
            this.secretKey = null;
            if (this.gssContext != null) {
                try {
                    this.gssContext.dispose();
                }
                catch (GSSException e) {
                    LOG.error("Error disposing of the GSSContext", e);
                }
            }
            this.disposed = true;
        }
    }

    public boolean isDisposed() {
        return this.disposed;
    }
}

