/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;

public interface Transport {
    public Connection create(ConnectionConfig var1);

    default public void close() {
    }
}

