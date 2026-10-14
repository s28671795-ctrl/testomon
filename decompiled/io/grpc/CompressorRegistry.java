/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Codec;
import io.grpc.Compressor;
import io.grpc.ExperimentalApi;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
@ThreadSafe
public final class CompressorRegistry {
    private static final CompressorRegistry DEFAULT_INSTANCE = new CompressorRegistry(new Codec.Gzip(), Codec.Identity.NONE);
    private final ConcurrentMap<String, Compressor> compressors = new ConcurrentHashMap<String, Compressor>();

    public static CompressorRegistry getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static CompressorRegistry newEmptyInstance() {
        return new CompressorRegistry(new Compressor[0]);
    }

    @VisibleForTesting
    CompressorRegistry(Compressor ... cs) {
        for (Compressor c : cs) {
            this.compressors.put(c.getMessageEncoding(), c);
        }
    }

    @Nullable
    public Compressor lookupCompressor(String compressorName) {
        return (Compressor)this.compressors.get(compressorName);
    }

    public void register(Compressor c) {
        String encoding = c.getMessageEncoding();
        Preconditions.checkArgument(!encoding.contains(","), "Comma is currently not allowed in message encoding");
        this.compressors.put(encoding, c);
    }
}

