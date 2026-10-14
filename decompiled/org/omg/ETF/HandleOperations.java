/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Connection;

public interface HandleOperations {
    public boolean add_input(Connection var1);

    public void signal_data_available(Connection var1);

    public void closed_by_peer(Connection var1);
}

