/*
 * Decompiled with CFR 0.152.
 */
package org.h2.compress;

public interface Compressor {
    public static final int NO = 0;
    public static final int LZF = 1;
    public static final int DEFLATE = 2;

    public int getAlgorithm();

    public int compress(byte[] var1, int var2, int var3, byte[] var4, int var5);

    public void expand(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6);

    public void setOptions(String var1);
}

