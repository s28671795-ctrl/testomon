/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.retry;

import java.io.IOException;
import java.nio.channels.FileChannel;
import org.h2.store.fs.FilePathWrapper;
import org.h2.store.fs.retry.FileRetryOnInterrupt;

public class FilePathRetryOnInterrupt
extends FilePathWrapper {
    static final String SCHEME = "retry";

    @Override
    public FileChannel open(String string) throws IOException {
        return new FileRetryOnInterrupt(this.name.substring(this.getScheme().length() + 1), string);
    }

    @Override
    public String getScheme() {
        return SCHEME;
    }
}

