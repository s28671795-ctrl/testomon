/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions.store;

import org.eclipse.jetty.server.session.DatabaseAdaptor;

public class SqlServerDatabaseAdaptor
extends DatabaseAdaptor {
    @Override
    public String getBlobType() {
        return "varbinary(max)";
    }
}

