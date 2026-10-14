/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.BufferHolder;
import org.omg.ETF.Profile;

public interface ConnectionOperations {
    public void write(boolean var1, boolean var2, byte[] var3, int var4, int var5, long var6);

    public int read(BufferHolder var1, int var2, int var3, int var4, long var5);

    public void flush();

    public void connect(Profile var1, long var2);

    public void close();

    public boolean is_connected();

    public Profile get_server_profile();

    public boolean is_data_available();

    public boolean wait_next_data(long var1);

    public boolean supports_callback();

    public boolean use_handle_time_out();
}

