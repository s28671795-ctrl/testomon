/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

public class BCLoadStoreParameter
implements KeyStore.LoadStoreParameter {
    private final InputStream in;
    private final OutputStream out;
    private final KeyStore.ProtectionParameter protectionParameter;

    public BCLoadStoreParameter(OutputStream outputStream2, char[] cArray) {
        this(outputStream2, (KeyStore.ProtectionParameter)new KeyStore.PasswordProtection(cArray));
    }

    public BCLoadStoreParameter(InputStream inputStream2, char[] cArray) {
        this(inputStream2, (KeyStore.ProtectionParameter)new KeyStore.PasswordProtection(cArray));
    }

    public BCLoadStoreParameter(InputStream inputStream2, KeyStore.ProtectionParameter protectionParameter) {
        this(inputStream2, null, protectionParameter);
    }

    public BCLoadStoreParameter(OutputStream outputStream2, KeyStore.ProtectionParameter protectionParameter) {
        this(null, outputStream2, protectionParameter);
    }

    BCLoadStoreParameter(InputStream inputStream2, OutputStream outputStream2, KeyStore.ProtectionParameter protectionParameter) {
        this.in = inputStream2;
        this.out = outputStream2;
        this.protectionParameter = protectionParameter;
    }

    public KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }

    public OutputStream getOutputStream() {
        if (this.out == null) {
            throw new UnsupportedOperationException("parameter not configured for storage - no OutputStream");
        }
        return this.out;
    }

    public InputStream getInputStream() {
        if (this.out != null) {
            throw new UnsupportedOperationException("parameter configured for storage OutputStream present");
        }
        return this.in;
    }
}

