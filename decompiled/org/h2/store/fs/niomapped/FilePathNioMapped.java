/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store.fs.niomapped;

import java.io.IOException;
import java.nio.channels.FileChannel;
import org.h2.store.fs.FilePathWrapper;
import org.h2.store.fs.niomapped.FileNioMapped;

public class FilePathNioMapped
extends FilePathWrapper {
    @Override
    public FileChannel open(String string) throws IOException {
        return new FileNioMapped(this.name.substring(this.getScheme().length() + 1), string);
    }

    @Override
    public String getScheme() {
        return "nioMapped";
    }
}

