/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Connection;
import org.omg.ETF.Handle;
import org.omg.ETF.Profile;

public interface ListenerOperations {
    public void set_handle(Handle var1);

    public Connection accept();

    public void listen();

    public void destroy();

    public void completed_data(Connection var1);

    public Profile endpoint();
}

