/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.BufferList;
import org.omg.ETF.BufferListHolder;
import org.omg.ETF.ConnectionOperations;

public interface ConnectionZeroCopyOperations
extends ConnectionOperations {
    public BufferList create_buffer_list();

    public void write_zc(BufferListHolder var1, long var2);

    public void read_zc(BufferListHolder var1, int var2, long var3);
}

