/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import org.jooq.impl.DefaultConnectionProvider;
import org.jooq.tools.jdbc.JDBCUtils;

class DefaultCloseableConnectionProvider
extends DefaultConnectionProvider {
    DefaultCloseableConnectionProvider(Connection connection) {
        super(connection);
    }

    protected void finalize() throws Throwable {
        JDBCUtils.safeClose(this.connection);
        super.finalize();
    }
}

