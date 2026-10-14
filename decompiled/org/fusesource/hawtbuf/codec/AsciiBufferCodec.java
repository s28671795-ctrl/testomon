/*
 * Decompiled with CFR 0.152.
 */
package org.fusesource.hawtbuf.codec;

import org.fusesource.hawtbuf.AsciiBuffer;
import org.fusesource.hawtbuf.codec.AbstractBufferCodec;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AsciiBufferCodec
extends AbstractBufferCodec<AsciiBuffer> {
    public static final AsciiBufferCodec INSTANCE = new AsciiBufferCodec();

    @Override
    protected AsciiBuffer createBuffer(byte[] data) {
        return new AsciiBuffer(data);
    }
}

