/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.ConnectionFactory;

public interface ConnectionFactoryManager {
    public ConnectionFactory getConnectionFactory();

    public void setConnectionFactory(ConnectionFactory var1);
}

