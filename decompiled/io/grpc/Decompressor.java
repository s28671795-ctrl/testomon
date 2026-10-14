/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import java.io.IOException;
import java.io.InputStream;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/1704")
public interface Decompressor {
    public String getMessageEncoding();

    public InputStream decompress(InputStream var1) throws IOException;
}

