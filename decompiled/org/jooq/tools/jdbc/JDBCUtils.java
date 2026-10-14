/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import java.io.Closeable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLXML;
import java.sql.Statement;
import org.jetbrains.annotations.NotNull;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.tools.JooqLogger;

public class JDBCUtils {
    private static final JooqLogger log = JooqLogger.getLogger(JDBCUtils.class);

    @NotNull
    public static final SQLDialect dialect(java.sql.Connection connection) {
        SQLDialect result = SQLDialect.DEFAULT;
        if (connection != null) {
            try {
                DatabaseMetaData m4 = connection.getMetaData();
                String product = m4.getDatabaseProductName();
                if ("ACCESS".equalsIgnoreCase(product)) {
                    return SQLDialect.ACCESS;
                }
                String url2 = m4.getURL();
                int majorVersion = 0;
                int minorVersion = 0;
                String productVersion = "";
                try {
                    majorVersion = m4.getDatabaseMajorVersion();
                }
                catch (SQLException sQLException) {
                    // empty catch block
                }
                try {
                    minorVersion = m4.getDatabaseMinorVersion();
                }
                catch (SQLException sQLException) {
                    // empty catch block
                }
                try {
                    productVersion = m4.getDatabaseProductVersion();
                }
                catch (SQLException sQLException) {
                    // empty catch block
                }
                result = JDBCUtils.dialect(url2, majorVersion, minorVersion, productVersion);
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
        if (result == SQLDialect.DEFAULT) {
            // empty if block
        }
        return result;
    }

    @NotNull
    public static final SQLDialect dialect(ConnectionFactory connection) {
        SQLDialect result = SQLDialect.DEFAULT;
        if (connection != null) {
            result = JDBCUtils.dialectFromProductName(connection.getMetadata().getName());
        }
        if (result == SQLDialect.DEFAULT) {
            // empty if block
        }
        return result;
    }

    @NotNull
    public static final SQLDialect dialect(Connection connection) {
        SQLDialect result = SQLDialect.DEFAULT;
        if (connection != null) {
            result = JDBCUtils.dialectFromProductName(connection.getMetadata().getDatabaseProductName());
        }
        if (result == SQLDialect.DEFAULT) {
            // empty if block
        }
        return result;
    }

    private static SQLDialect dialectFromProductName(String product) {
        String p = product.toLowerCase().replace(" ", "");
        if (p.contains("h2")) {
            return SQLDialect.H2;
        }
        if (p.contains("mariadb")) {
            return SQLDialect.MARIADB;
        }
        if (p.contains("mysql")) {
            return SQLDialect.MYSQL;
        }
        if (p.contains("postgres")) {
            return SQLDialect.POSTGRES;
        }
        if (p.contains("oracle")) {
            return SQLDialect.ORACLE;
        }
        if (p.contains("sqlserver")) {
            return SQLDialect.SQLSERVER;
        }
        return SQLDialect.DEFAULT;
    }

    @NotNull
    private static final SQLDialect dialect(String url2, int majorVersion, int minorVersion, String productVersion) {
        SQLDialect family = JDBCUtils.dialect(url2).family();
        if (majorVersion == 0) {
            return family;
        }
        switch (family) {
            case DB2: {
                return JDBCUtils.db2Dialect(majorVersion);
            }
            case ORACLE: {
                return JDBCUtils.oracleDialect(majorVersion);
            }
            case SQLSERVER: {
                return JDBCUtils.sqlserverDialect(majorVersion);
            }
            case FIREBIRD: {
                return JDBCUtils.firebirdDialect(majorVersion);
            }
            case H2: {
                return JDBCUtils.h2Dialect(majorVersion, minorVersion, productVersion);
            }
            case MARIADB: {
                return JDBCUtils.mariadbDialect(majorVersion, minorVersion);
            }
            case MYSQL: {
                return JDBCUtils.mysqlDialect(majorVersion, minorVersion, productVersion);
            }
            case POSTGRES: {
                return JDBCUtils.postgresDialect(majorVersion, minorVersion);
            }
        }
        return family;
    }

    @Pro
    private static final SQLDialect db2Dialect(int majorVersion) {
        if (majorVersion >= 11) {
            return SQLDialect.DB2_11;
        }
        if (majorVersion >= 10) {
            return SQLDialect.DB2_10;
        }
        if (majorVersion <= 9) {
            return SQLDialect.DB2_9;
        }
        return SQLDialect.DB2;
    }

    @Pro
    private static final SQLDialect oracleDialect(int majorVersion) {
        if (majorVersion >= 21) {
            return SQLDialect.ORACLE21C;
        }
        if (majorVersion >= 20) {
            return SQLDialect.ORACLE20C;
        }
        if (majorVersion >= 18) {
            return SQLDialect.ORACLE18C;
        }
        if (majorVersion >= 12) {
            return SQLDialect.ORACLE12C;
        }
        if (majorVersion >= 11) {
            return SQLDialect.ORACLE11G;
        }
        if (majorVersion <= 10) {
            return SQLDialect.ORACLE10G;
        }
        return SQLDialect.ORACLE;
    }

    @Pro
    private static final SQLDialect sqlserverDialect(int majorVersion) {
        if (majorVersion <= 10) {
            return SQLDialect.SQLSERVER2008;
        }
        if (majorVersion == 11) {
            return SQLDialect.SQLSERVER2012;
        }
        if (majorVersion == 12) {
            return SQLDialect.SQLSERVER2014;
        }
        if (majorVersion == 13) {
            return SQLDialect.SQLSERVER2016;
        }
        if (majorVersion <= 15) {
            return SQLDialect.SQLSERVER2017;
        }
        if (majorVersion > 15) {
            return SQLDialect.SQLSERVER2022;
        }
        return SQLDialect.SQLSERVER;
    }

    private static final SQLDialect postgresDialect(int majorVersion, int minorVersion) {
        if (majorVersion < 9) {
            return SQLDialect.POSTGRES_9_3;
        }
        if (majorVersion == 9) {
            if (minorVersion <= 3) {
                return SQLDialect.POSTGRES_9_3;
            }
            if (minorVersion == 4) {
                return SQLDialect.POSTGRES_9_4;
            }
            if (minorVersion >= 5) {
                return SQLDialect.POSTGRES_9_5;
            }
        }
        if (majorVersion >= 15) {
            return SQLDialect.POSTGRES_15;
        }
        if (majorVersion >= 14) {
            return SQLDialect.POSTGRES_14;
        }
        if (majorVersion >= 13) {
            return SQLDialect.POSTGRES_13;
        }
        if (majorVersion >= 12) {
            return SQLDialect.POSTGRES_12;
        }
        if (majorVersion >= 11) {
            return SQLDialect.POSTGRES_11;
        }
        if (majorVersion >= 10) {
            return SQLDialect.POSTGRES_10;
        }
        return SQLDialect.POSTGRES;
    }

    private static final SQLDialect mariadbDialect(int majorVersion, int minorVersion) {
        if (majorVersion == 10) {
            if (minorVersion == 0) {
                return SQLDialect.MARIADB_10_0;
            }
            if (minorVersion == 1) {
                return SQLDialect.MARIADB_10_1;
            }
            if (minorVersion == 2) {
                return SQLDialect.MARIADB_10_2;
            }
            if (minorVersion == 3) {
                return SQLDialect.MARIADB_10_3;
            }
            if (minorVersion == 4) {
                return SQLDialect.MARIADB_10_4;
            }
            if (minorVersion == 5) {
                return SQLDialect.MARIADB_10_5;
            }
            if (minorVersion == 6) {
                return SQLDialect.MARIADB_10_6;
            }
            if (minorVersion >= 7) {
                return SQLDialect.MARIADB_10_7;
            }
        }
        return SQLDialect.MARIADB;
    }

    private static final SQLDialect mysqlDialect(int majorVersion, int minorVersion, String productVersion) {
        if (majorVersion <= 5) {
            if (minorVersion <= 6) {
                return SQLDialect.MYSQL_5_6;
            }
            return SQLDialect.MYSQL_5_7;
        }
        if (majorVersion >= 8) {
            try {
                if (Integer.parseInt(productVersion.split("\\.")[2]) < 19) {
                    return SQLDialect.MYSQL_8_0;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return SQLDialect.MYSQL_8_0_19;
        }
        return SQLDialect.MYSQL;
    }

    private static final SQLDialect firebirdDialect(int majorVersion) {
        if (majorVersion <= 2) {
            return SQLDialect.FIREBIRD_2_5;
        }
        if (majorVersion >= 3) {
            return SQLDialect.FIREBIRD_3_0;
        }
        if (majorVersion >= 4) {
            return SQLDialect.FIREBIRD_4_0;
        }
        return SQLDialect.FIREBIRD;
    }

    private static final SQLDialect h2Dialect(int majorVersion, int minorVersion, String productVersion) {
        if (majorVersion == 1 && minorVersion == 4) {
            try {
                int patchVersion = Integer.parseInt(productVersion.split(" ")[0].split("\\.")[2]);
                if (patchVersion < 200) {
                    return SQLDialect.H2_1_4_197;
                }
                if (patchVersion < 202) {
                    return SQLDialect.H2_1_4_200;
                }
                if (patchVersion == 202) {
                    return SQLDialect.H2_2_0_202;
                }
            }
            catch (Exception patchVersion) {}
        } else if (majorVersion == 2) {
            try {
                int patchVersion = Integer.parseInt(productVersion.split(" ")[0].split("\\.")[2]);
                if (patchVersion < 214) {
                    return SQLDialect.H2_2_0_202;
                }
                return SQLDialect.H2_2_1_214;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return SQLDialect.H2;
    }

    @NotNull
    public static final SQLDialect dialect(String url2) {
        if (url2 == null) {
            return SQLDialect.DEFAULT;
        }
        if (url2.contains(":redshift:") || url2.contains(":postgresql:") && url2.contains("redshift.amazonaws.com") || url2.contains(":pgsql:") && url2.contains("redshift.amazonaws.com")) {
            return SQLDialect.REDSHIFT;
        }
        if (url2.contains(":cubrid:")) {
            return SQLDialect.CUBRID;
        }
        if (url2.contains(":derby:")) {
            return SQLDialect.DERBY;
        }
        if (url2.contains(":firebirdsql:")) {
            return SQLDialect.FIREBIRD;
        }
        if (url2.contains(":h2:")) {
            return SQLDialect.H2;
        }
        if (url2.contains(":hsqldb:")) {
            return SQLDialect.HSQLDB;
        }
        if (url2.contains(":ignite:")) {
            return SQLDialect.IGNITE;
        }
        if (url2.contains(":mariadb:")) {
            return SQLDialect.MARIADB;
        }
        if (url2.contains(":mysql:") || url2.contains(":google:")) {
            return SQLDialect.MYSQL;
        }
        if (url2.contains(":postgresql:") || url2.contains(":pgsql:")) {
            return SQLDialect.POSTGRES;
        }
        if (url2.contains(":sqlite:") || url2.contains(":sqldroid:")) {
            return SQLDialect.SQLITE;
        }
        if (url2.contains(":jtds:sybase:") || url2.contains(":sybase:Tds:")) {
            return SQLDialect.ASE;
        }
        if (url2.contains(":bigquery:")) {
            return SQLDialect.BIGQUERY;
        }
        if (url2.contains(":db2:")) {
            return SQLDialect.DB2;
        }
        if (url2.contains(":exa:")) {
            return SQLDialect.EXASOL;
        }
        if (url2.contains(":sap:")) {
            return SQLDialect.HANA;
        }
        if (url2.contains(":informix:") || url2.contains(":informix-sqli:")) {
            return SQLDialect.INFORMIX;
        }
        if (url2.contains(":ingres:")) {
            return SQLDialect.INGRES;
        }
        if (url2.contains(":oracle:")) {
            return SQLDialect.ORACLE;
        }
        if (url2.contains(":snowflake:")) {
            return SQLDialect.SNOWFLAKE;
        }
        if (url2.contains(":sqlserver:") || url2.contains(":mssql:")) {
            return SQLDialect.SQLSERVER;
        }
        if (url2.contains(":sybase:")) {
            return SQLDialect.SYBASE;
        }
        if (url2.contains(":teradata:")) {
            return SQLDialect.TERADATA;
        }
        if (url2.contains(":vertica:")) {
            return SQLDialect.VERTICA;
        }
        return SQLDialect.DEFAULT;
    }

    @NotNull
    public static final String driver(SQLDialect dialect) {
        switch (dialect.family()) {
            case CUBRID: {
                return "cubrid.jdbc.driver.CUBRIDDriver";
            }
            case DERBY: {
                return "org.apache.derby.jdbc.ClientDriver";
            }
            case FIREBIRD: {
                return "org.firebirdsql.jdbc.FBDriver";
            }
            case H2: {
                return "org.h2.Driver";
            }
            case HSQLDB: {
                return "org.hsqldb.jdbcDriver";
            }
            case IGNITE: {
                return "org.apache.ignite.IgniteJdbcThinDriver";
            }
            case MARIADB: {
                return "org.mariadb.jdbc.Driver";
            }
            case MYSQL: {
                return "com.mysql.cj.jdbc.Driver";
            }
            case POSTGRES: {
                return "org.postgresql.Driver";
            }
            case SQLITE: {
                return "org.sqlite.JDBC";
            }
            case ACCESS: {
                return "sun.jdbc.odbc.JdbcOdbcDriver";
            }
            case ASE: {
                return "net.sourceforge.jtds.jdbc.Driver";
            }
            case BIGQUERY: {
                return "com.simba.googlebigquery.jdbc42.Driver";
            }
            case DB2: {
                return "com.ibm.db2.jcc.DB2Driver";
            }
            case EXASOL: {
                return "com.exasol.jdbc.EXADriver";
            }
            case HANA: {
                return "com.sap.db.jdbc.Driver";
            }
            case INFORMIX: {
                return "com.informix.jdbc.IfxDriver";
            }
            case INGRES: {
                return "com.ingres.jdbc.IngresDriver";
            }
            case ORACLE: {
                return "oracle.jdbc.OracleDriver";
            }
            case REDSHIFT: {
                return "com.amazon.redshift.jdbc.Driver";
            }
            case SNOWFLAKE: {
                return "net.snowflake.client.jdbc.SnowflakeDriver";
            }
            case SQLSERVER: {
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            }
            case SYBASE: {
                return "com.sybase.jdbc4.jdbc.SybDriver";
            }
            case TERADATA: {
                return "com.teradata.jdbc.TeraDriver";
            }
            case VERTICA: {
                return "com.vertica.jdbc.Driver";
            }
        }
        return "java.sql.Driver";
    }

    @NotNull
    public static final String driver(String url2) {
        return JDBCUtils.driver(JDBCUtils.dialect(url2).family());
    }

    public static final void safeClose(java.sql.Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }
            catch (Exception ignore) {
                log.debug((Object)"Error when closing connection", ignore);
            }
        }
    }

    public static final void safeClose(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            }
            catch (Exception ignore) {
                log.debug((Object)"Error when closing statement", ignore);
            }
        }
    }

    public static final void safeClose(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (Exception ignore) {
                log.debug((Object)"Error when closing result set", ignore);
            }
        }
    }

    public static final void safeClose(ResultSet resultSet, PreparedStatement statement) {
        JDBCUtils.safeClose(resultSet);
        JDBCUtils.safeClose(statement);
    }

    public static final void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception ignore) {
                log.debug((Object)"Error when closing closeable", ignore);
            }
        }
    }

    public static final void safeClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception ignore) {
                log.debug((Object)"Error when closing closeable", ignore);
            }
        }
    }

    public static final void safeFree(Blob blob) {
        if (blob != null) {
            try {
                blob.free();
            }
            catch (Exception e) {
                log.warn((Object)"Error while freeing resource", e);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
    }

    public static final void safeFree(Clob clob) {
        if (clob != null) {
            try {
                clob.free();
            }
            catch (Exception e) {
                log.warn((Object)"Error while freeing resource", e);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
    }

    public static final void safeFree(SQLXML xml) {
        if (xml != null) {
            try {
                xml.free();
            }
            catch (Exception e) {
                log.warn((Object)"Error while freeing resource", e);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
    }

    public static final void safeFree(Array array) {
        if (array != null) {
            try {
                array.free();
            }
            catch (Exception e) {
                log.warn((Object)"Error while freeing resource", e);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
    }

    public static final <T> T wasNull(SQLInput stream, T value) throws SQLException {
        return value == null || stream.wasNull() ? null : (T)value;
    }

    public static final <T extends Number> T wasNull(SQLInput stream, T value) throws SQLException {
        return value == null || value.intValue() == 0 && stream.wasNull() ? null : (T)value;
    }

    public static final Boolean wasNull(SQLInput stream, Boolean value) throws SQLException {
        return value == null || value == false && stream.wasNull() ? null : value;
    }

    public static final <T> T wasNull(ResultSet rs, T value) throws SQLException {
        return value == null || rs.wasNull() ? null : (T)value;
    }

    public static final <T extends Number> T wasNull(ResultSet rs, T value) throws SQLException {
        return value == null || value.intValue() == 0 && rs.wasNull() ? null : (T)value;
    }

    public static final Boolean wasNull(ResultSet rs, Boolean value) throws SQLException {
        return value == null || value == false && rs.wasNull() ? null : value;
    }

    public static final <T> T wasNull(CallableStatement statement, T value) throws SQLException {
        return value == null || statement.wasNull() ? null : (T)value;
    }

    public static final <T extends Number> T wasNull(CallableStatement statement, T value) throws SQLException {
        return value == null || value.intValue() == 0 && statement.wasNull() ? null : (T)value;
    }

    public static final Boolean wasNull(CallableStatement statement, Boolean value) throws SQLException {
        return value == null || value == false && statement.wasNull() ? null : value;
    }

    private JDBCUtils() {
    }
}

