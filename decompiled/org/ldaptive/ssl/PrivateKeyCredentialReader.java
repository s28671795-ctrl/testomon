/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import org.ldaptive.LdapUtils;
import org.ldaptive.ssl.AbstractCredentialReader;

public class PrivateKeyCredentialReader
extends AbstractCredentialReader<PrivateKey> {
    @Override
    public PrivateKey read(InputStream is, String ... params) throws IOException, GeneralSecurityException {
        String algorithm = "RSA";
        if (params.length > 0 && params[0] != null) {
            algorithm = params[0];
        }
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(LdapUtils.readInputStream(this.getBufferedInputStream(is)));
        return kf.generatePrivate(spec);
    }
}

