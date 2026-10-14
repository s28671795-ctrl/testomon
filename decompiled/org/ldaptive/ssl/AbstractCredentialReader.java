/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import org.ldaptive.io.ResourceUtils;
import org.ldaptive.ssl.CredentialReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCredentialReader<T>
implements CredentialReader<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public T read(String path, String ... params) throws IOException, GeneralSecurityException {
        try (InputStream is = ResourceUtils.getResource(path);){
            this.logger.trace("found resource at {}", (Object)path);
            Object credential = this.read(is, params);
            this.logger.debug("Successfully loaded credential {} from path {}", credential, (Object)path);
            Object t2 = credential;
            return t2;
        }
    }

    protected InputStream getBufferedInputStream(InputStream is) {
        if (is.markSupported()) {
            return is;
        }
        return new BufferedInputStream(is);
    }
}

