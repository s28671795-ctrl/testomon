/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

class ByteBufferStream
extends ByteArrayOutputStream {
    public InputStream inputStream() {
        return new ByteArrayInputStream(this.buf, 0, this.count);
    }
}

