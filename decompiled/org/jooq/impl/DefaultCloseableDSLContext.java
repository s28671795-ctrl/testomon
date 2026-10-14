/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.CloseableDSLContext;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultCloseableConnectionProvider;
import org.jooq.impl.DefaultConnectionFactory;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.R2DBC;
import org.jooq.tools.jdbc.JDBCUtils;

public class DefaultCloseableDSLContext
extends DefaultDSLContext
implements CloseableDSLContext {
    public DefaultCloseableDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
        super(connectionProvider, dialect, settings);
    }

    public DefaultCloseableDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect) {
        super(connectionProvider, dialect);
    }

    public DefaultCloseableDSLContext(ConnectionFactory connectionFactory, SQLDialect dialect, Settings settings) {
        super(connectionFactory, dialect, settings);
    }

    public DefaultCloseableDSLContext(ConnectionFactory connectionFactory, SQLDialect dialect) {
        super(connectionFactory, dialect);
    }

    @Override
    public void close() {
        ConnectionProvider cp = this.configuration().connectionProvider();
        ConnectionFactory cf = this.configuration().connectionFactory();
        if (cp instanceof DefaultCloseableConnectionProvider) {
            DefaultCloseableConnectionProvider dcp = (DefaultCloseableConnectionProvider)cp;
            JDBCUtils.safeClose(dcp.connection);
            dcp.connection = null;
        }
        if (cf instanceof DefaultConnectionFactory) {
            DefaultConnectionFactory dcf = (DefaultConnectionFactory)cf;
            if (dcf.finalize) {
                R2DBC.block(dcf.connection.close());
                dcf.connection = null;
            }
        }
    }
}

