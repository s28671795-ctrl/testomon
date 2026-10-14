/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.util.Collection;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.structure.core.Column;

public abstract class JdbcUtil {
    public static final int TYPE_UNKNOWN = Integer.MIN_VALUE;

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    public static void close(ResultSet rs, Statement stmt) {
        JdbcUtil.closeResultSet(rs);
        JdbcUtil.closeStatement(stmt);
    }

    public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
        Object obj = null;
        try {
            obj = rs.getObject(index);
        }
        catch (SQLException e) {
            if ("The conversion from char to SMALLINT is unsupported.".equals(e.getMessage())) {
                obj = rs.getString(index);
            }
            throw e;
        }
        if (obj instanceof Blob) {
            obj = rs.getBytes(index);
        } else if (obj instanceof Clob) {
            obj = rs.getString(index);
        } else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
            obj = rs.getTimestamp(index);
        } else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.DATE")) {
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            obj = "java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName) ? rs.getTimestamp(index) : rs.getDate(index);
        } else if (obj instanceof Date) {
            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
                obj = rs.getTimestamp(index);
            }
        } else if (obj instanceof SQLXML) {
            obj = rs.getSQLXML(index).getString();
        }
        return obj;
    }

    public static boolean isNumeric(int sqlType) {
        return -7 == sqlType || -5 == sqlType || 3 == sqlType || 8 == sqlType || 6 == sqlType || 4 == sqlType || 2 == sqlType || 7 == sqlType || 5 == sqlType || -6 == sqlType;
    }

    public static Object requiredSingleResult(Collection results) throws DatabaseException {
        int size;
        int n = size = results != null ? results.size() : 0;
        if (size == 0) {
            throw new DatabaseException("Empty result set, expected one row");
        }
        if (results.size() > 1) {
            throw new DatabaseException("Result set larger than one row");
        }
        return results.iterator().next();
    }

    public static String getValueForColumn(ResultSet rs, String columnNameToCheck, Database database) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        int numberOfColumns = metadata.getColumnCount();
        String correctedColumnName = database.correctObjectName(columnNameToCheck, Column.class);
        for (int i = 1; i < numberOfColumns + 1; ++i) {
            String columnName = metadata.getColumnLabel(i);
            if (!correctedColumnName.equalsIgnoreCase(columnName)) continue;
            return rs.getString(columnName);
        }
        return null;
    }
}

