/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.DecompressorRegistry;
import io.grpc.Internal;

@Internal
public final class InternalDecompressorRegistry {
    private InternalDecompressorRegistry() {
    }

    @Internal
    public static byte[] getRawAdvertisedMessageEncodings(DecompressorRegistry reg) {
        return reg.getRawAdvertisedMessageEncodings();
    }
}

