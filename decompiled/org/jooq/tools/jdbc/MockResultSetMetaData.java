/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Named;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockResultSet;
import org.jooq.types.UNumber;

public class MockResultSetMetaData
implements ResultSetMetaData,
Serializable {
    private final MockResultSet rs;

    public MockResultSetMetaData(MockResultSet rs) {
        this.rs = rs;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (this.isWrapperFor(iface)) {
            return (T)this;
        }
        throw new SQLException("MockResultSetMetaData does not implement " + String.valueOf(iface));
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    @Override
    public int getColumnCount() throws SQLException {
        this.rs.checkNotClosed();
        return this.rs.result.fieldsRow().size();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        this.rs.checkNotClosed();
        return false;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        this.rs.checkNotClosed();
        return true;
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        this.rs.checkNotClosed();
        return true;
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        this.rs.checkNotClosed();
        return false;
    }

    @Override
    public int isNullable(int column) throws SQLException {
        this.rs.checkNotClosed();
        return 2;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        this.rs.checkNotClosed();
        Field<?> field2 = this.rs.result.field(column - 1);
        Class type = field2.getType();
        return Number.class.isAssignableFrom(type) && !UNumber.class.isAssignableFrom(type);
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return this.getColumnName(column);
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        this.rs.checkNotClosed();
        return this.rs.result.field(column - 1).getName();
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        Schema schema;
        TableField f;
        Table table;
        this.rs.checkNotClosed();
        Field<?> field2 = this.rs.result.field(column - 1);
        if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null && (schema = table.getSchema()) != null) {
            Configuration configuration = this.rs.result.configuration();
            Named mapped = null;
            if (configuration != null) {
                mapped = DSL.using(configuration).map(schema);
            }
            if (mapped != null) {
                return mapped.getName();
            }
            return schema.getName();
        }
        return "";
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        this.rs.checkNotClosed();
        return 0;
    }

    @Override
    public int getScale(int column) throws SQLException {
        this.rs.checkNotClosed();
        return 0;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        TableField f;
        Table table;
        this.rs.checkNotClosed();
        Field<?> field2 = this.rs.result.field(column - 1);
        if (field2 instanceof TableField && (table = (f = (TableField)field2).getTable()) != null) {
            return table.getName();
        }
        return "";
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        this.rs.checkNotClosed();
        return "";
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        this.rs.checkNotClosed();
        return this.rs.result.field(column - 1).getDataType().getSQLType();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        this.rs.checkNotClosed();
        return this.rs.result.field(column - 1).getDataType().getTypeName();
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        this.rs.checkNotClosed();
        return true;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        this.rs.checkNotClosed();
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        this.rs.checkNotClosed();
        return false;
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        this.rs.checkNotClosed();
        return this.rs.result.field(column - 1).getType().getName();
    }
}

