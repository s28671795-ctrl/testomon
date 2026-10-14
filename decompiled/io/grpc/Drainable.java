/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import java.io.IOException;
import java.io.OutputStream;

public interface Drainable {
    public int drainTo(OutputStream var1) throws IOException;
}

