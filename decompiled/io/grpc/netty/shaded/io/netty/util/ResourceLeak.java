/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

@Deprecated
public interface ResourceLeak {
    public void record();

    public void record(Object var1);

    public boolean close();
}

