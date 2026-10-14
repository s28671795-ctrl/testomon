/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.compression;

import io.grpc.netty.shaded.io.netty.handler.codec.compression.DeflateOptions;

public final class GzipOptions
extends DeflateOptions {
    static final GzipOptions DEFAULT = new GzipOptions(6, 15, 8);

    GzipOptions(int compressionLevel, int windowBits, int memLevel) {
        super(compressionLevel, windowBits, memLevel);
    }
}

