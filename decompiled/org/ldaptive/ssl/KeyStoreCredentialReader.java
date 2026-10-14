/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import org.ldaptive.ssl.AbstractCredentialReader;

public class KeyStoreCredentialReader
extends AbstractCredentialReader<KeyStore> {
    @Override
    public KeyStore read(InputStream is, String ... params) throws IOException, GeneralSecurityException {
        char[] password = null;
        if (params.length > 0 && params[0] != null) {
            password = params[0].toCharArray();
        }
        String type = KeyStore.getDefaultType();
        if (params.length > 1 && params[1] != null) {
            type = params[1];
        }
        KeyStore keystore = KeyStore.getInstance(type);
        if (is != null) {
            keystore.load(this.getBufferedInputStream(is), password);
            if (password != null) {
                Arrays.fill(password, '0');
            }
        }
        return keystore;
    }
}

