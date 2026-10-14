/*
 * Decompiled with CFR 0.152.
 */
package org.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import org.h2.JdbcDriverBackwardsCompat;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;

public class Driver
implements java.sql.Driver,
JdbcDriverBackwardsCompat {
    private static final Driver INSTANCE = new Driver();
    private static final String DEFAULT_URL = "jdbc:default:connection";
    private static final ThreadLocal<Connection> DEFAULT_CONNECTION = new ThreadLocal();
    private static boolean registered;

    @Override
    public Connection connect(String string, Properties properties) throws SQLException {
        if (string == null) {
            throw DbException.getJdbcSQLException(90046, null, "jdbc:h2:{ {.|mem:}[name] | [file:]fileName | {tcp|ssl}:[//]server[:port][,server2[:port]]/name }[;key=value...]", null);
        }
        if (string.startsWith("jdbc:h2:")) {
            return new JdbcConnection(string, properties, null, null, false);
        }
        if (string.equals(DEFAULT_URL)) {
            return DEFAULT_CONNECTION.get();
        }
        return null;
    }

    @Override
    public boolean acceptsURL(String string) throws SQLException {
        if (string == null) {
            throw DbException.getJdbcSQLException(90046, null, "jdbc:h2:{ {.|mem:}[name] | [file:]fileName | {tcp|ssl}:[//]server[:port][,server2[:port]]/name }[;key=value...]", null);
        }
        if (string.startsWith("jdbc:h2:")) {
            return true;
        }
        if (string.equals(DEFAULT_URL)) {
            return DEFAULT_CONNECTION.get() != null;
        }
        return false;
    }

    @Override
    public int getMajorVersion() {
        return 2;
    }

    @Override
    public int getMinorVersion() {
        return 2;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String string, Properties properties) {
        return new DriverPropertyInfo[0];
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public Logger getParentLogger() {
        return null;
    }

    public static synchronized Driver load() {
        try {
            if (!registered) {
                registered = true;
                DriverManager.registerDriver(INSTANCE);
            }
        }
        catch (SQLException sQLException) {
            DbException.traceThrowable(sQLException);
        }
        return INSTANCE;
    }

    public static synchronized void unload() {
        try {
            if (registered) {
                registered = false;
                DriverManager.deregisterDriver(INSTANCE);
            }
        }
        catch (SQLException sQLException) {
            DbException.traceThrowable(sQLException);
        }
    }

    public static void setDefaultConnection(Connection connection) {
        if (connection == null) {
            DEFAULT_CONNECTION.remove();
        } else {
            DEFAULT_CONNECTION.set(connection);
        }
    }

    public static void setThreadContextClassLoader(Thread thread2) {
        try {
            thread2.setContextClassLoader(Driver.class.getClassLoader());
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    static {
        Driver.load();
    }
}

