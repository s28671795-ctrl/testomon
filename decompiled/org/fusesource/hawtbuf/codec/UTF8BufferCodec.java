/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.hawtbuf.codec.AbstractBufferCodec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class UTF8BufferCodec
extends AbstractBufferCodec<UTF8Buffer> {
    public static final UTF8BufferCodec INSTANCE = new UTF8BufferCodec();

    @Override
    protected UTF8Buffer createBuffer(byte[] data) {
        return new UTF8Buffer(data);
    }
}

