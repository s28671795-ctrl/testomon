/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.codec.AbstractBufferCodec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BufferCodec
extends AbstractBufferCodec<Buffer> {
    public static final BufferCodec INSTANCE = new BufferCodec();

    @Override
    protected Buffer createBuffer(byte[] data) {
        return new Buffer(data);
    }
}

