/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.LdapException;

public interface ConnectionFactory {
    public Connection getConnection() throws LdapException;

    public ConnectionConfig getConnectionConfig();

    public void close();
}

