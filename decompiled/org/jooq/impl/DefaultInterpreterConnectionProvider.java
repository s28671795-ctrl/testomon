/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;

final class DefaultInterpreterConnectionProvider
implements ConnectionProvider {
    private final Configuration configuration;

    DefaultInterpreterConnectionProvider(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    @NotNull
    public Connection acquire() throws DataAccessException {
        SQLDialect family = StringUtils.defaultIfNull(this.configuration.settings().getInterpreterDialect(), SQLDialect.DEFAULT).family();
        try {
            switch (family) {
                case DERBY: {
                    return DriverManager.getConnection("jdbc:derby:memory:db;create=true");
                }
                case H2: 
                case DEFAULT: {
                    return DriverManager.getConnection("jdbc:h2:mem:jooq-ddl-interpretation-" + String.valueOf(UUID.randomUUID()), "sa", "");
                }
                case HSQLDB: {
                    return DriverManager.getConnection("jdbc:hsqldb:.");
                }
                case SQLITE: {
                    return DriverManager.getConnection("jdbc:sqlite::memory:");
                }
            }
            throw new DataAccessException("Unsupported interpretation dialect family: " + String.valueOf((Object)family));
        }
        catch (SQLException e) {
            if ("08001".equals(e.getSQLState())) {
                throw new DataAccessException("The JDBC driver's JAR file was not found on the classpath, which is required for this feature", e);
            }
            throw new DataAccessException("Error while exporting schema", e);
        }
    }

    @Override
    public void release(Connection connection) throws DataAccessException {
        JDBCUtils.safeClose(connection);
    }
}

