/*
 * Decompiled with CFR 0.152.
 */
package org.h2.message;

interface TraceWriter {
    public void setName(String var1);

    public void write(int var1, String var2, String var3, Throwable var4);

    public void write(int var1, int var2, String var3, Throwable var4);

    public boolean isEnabled(int var1);
}

