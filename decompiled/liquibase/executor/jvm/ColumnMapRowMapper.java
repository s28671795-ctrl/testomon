/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import liquibase.executor.jvm.RowMapper;
import liquibase.util.JdbcUtil;

public class ColumnMapRowMapper
implements RowMapper {
    private final boolean caseSensitiveDatabase;

    public ColumnMapRowMapper(boolean caseSensitiveDatabase) {
        this.caseSensitiveDatabase = caseSensitiveDatabase;
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map mapOfColValues = this.createColumnMap(columnCount);
        for (int i = 1; i <= columnCount; ++i) {
            String key = this.getColumnKey(rsmd.getColumnLabel(i));
            Object obj = this.getColumnValue(rs, i);
            mapOfColValues.put(key, obj);
        }
        return mapOfColValues;
    }

    protected Map createColumnMap(int columnCount) {
        return new LinkedHashMap(columnCount);
    }

    protected String getColumnKey(String columnName) {
        if (this.caseSensitiveDatabase) {
            return columnName;
        }
        return columnName.toUpperCase(Locale.US);
    }

    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
        return JdbcUtil.getResultSetValue(rs, index);
    }
}

