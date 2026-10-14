/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.IOP.Codec;
import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;
import org.omg.IOP.Encoding;

public interface CodecFactoryOperations {
    public Codec create_codec(Encoding var1) throws UnknownEncoding;
}

