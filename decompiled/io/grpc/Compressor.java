/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import java.io.IOException;
import java.io.OutputStream;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
public interface Compressor {
    public String getMessageEncoding();

    public OutputStream compress(OutputStream var1) throws IOException;
}

