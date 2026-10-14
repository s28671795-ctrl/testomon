/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.internal.tcnative;

public final class SSLSession {
    private SSLSession() {
    }

    public static native long getTime(long var0);

    public static native long getTimeout(long var0);

    public static native long setTimeout(long var0, long var2);

    public static native byte[] getSessionId(long var0);

    public static native boolean upRef(long var0);

    public static native void free(long var0);

    public static native boolean shouldBeSingleUse(long var0);
}

