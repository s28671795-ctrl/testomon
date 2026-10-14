/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public interface CredentialReader<T> {
    public T read(String var1, String ... var2) throws IOException, GeneralSecurityException;

    public T read(InputStream var1, String ... var2) throws IOException, GeneralSecurityException;
}

