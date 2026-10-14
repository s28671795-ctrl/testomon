/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.BufferHolder;

public interface BufferListOperations {
    public int add_buffer(int var1, BufferHolder var2);

    public int num_buffers();

    public void get_buffer(int var1, BufferHolder var2);

    public void release_buffers();
}

