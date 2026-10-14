/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.engine.SessionRemote;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueToObjectConverter;

public class UpdatableRow {
    private final JdbcConnection conn;
    private final ResultInterface result;
    private final int columnCount;
    private String schemaName;
    private String tableName;
    private ArrayList<String> key;
    private boolean isUpdatable;

    public UpdatableRow(JdbcConnection jdbcConnection, ResultInterface resultInterface) throws SQLException {
        ResultSet resultSet;
        Object object;
        Object object2;
        this.conn = jdbcConnection;
        this.result = resultInterface;
        this.columnCount = resultInterface.getVisibleColumnCount();
        if (this.columnCount == 0) {
            return;
        }
        for (int i = 0; i < this.columnCount; ++i) {
            object2 = resultInterface.getTableName(i);
            object = resultInterface.getSchemaName(i);
            if (object2 == null || object == null) {
                return;
            }
            if (this.tableName == null) {
                this.tableName = object2;
            } else if (!this.tableName.equals(object2)) {
                return;
            }
            if (this.schemaName == null) {
                this.schemaName = object;
                continue;
            }
            if (this.schemaName.equals(object)) continue;
            return;
        }
        String string = "BASE TABLE";
        object2 = jdbcConnection.getSession();
        if (object2 instanceof SessionRemote && ((SessionRemote)object2).getClientVersion() <= 19) {
            string = "TABLE";
        }
        if (!(resultSet = (object = jdbcConnection.getMetaData()).getTables(null, StringUtils.escapeMetaDataPattern(this.schemaName), StringUtils.escapeMetaDataPattern(this.tableName), new String[]{string})).next()) {
            return;
        }
        String string2 = resultSet.getString("TABLE_NAME");
        boolean bl = !string2.equals(this.tableName) && string2.equalsIgnoreCase(this.tableName);
        this.key = Utils.newSmallArrayList();
        resultSet = object.getPrimaryKeys(null, StringUtils.escapeMetaDataPattern(this.schemaName), this.tableName);
        while (resultSet.next()) {
            String string3 = resultSet.getString("COLUMN_NAME");
            this.key.add(bl ? StringUtils.toUpperEnglish(string3) : string3);
        }
        if (this.isIndexUsable(this.key)) {
            this.isUpdatable = true;
            return;
        }
        this.key.clear();
        resultSet = object.getIndexInfo(null, StringUtils.escapeMetaDataPattern(this.schemaName), this.tableName, true, true);
        while (resultSet.next()) {
            short s2 = resultSet.getShort("ORDINAL_POSITION");
            if (s2 == 1) {
                if (this.isIndexUsable(this.key)) {
                    this.isUpdatable = true;
                    return;
                }
                this.key.clear();
            }
            String string4 = resultSet.getString("COLUMN_NAME");
            this.key.add(bl ? StringUtils.toUpperEnglish(string4) : string4);
        }
        if (this.isIndexUsable(this.key)) {
            this.isUpdatable = true;
            return;
        }
        this.key = null;
    }

    private boolean isIndexUsable(ArrayList<String> arrayList) {
        if (arrayList.isEmpty()) {
            return false;
        }
        for (String string : arrayList) {
            if (this.findColumnIndex(string) >= 0) continue;
            return false;
        }
        return true;
    }

    public boolean isUpdatable() {
        return this.isUpdatable;
    }

    private int findColumnIndex(String string) {
        for (int i = 0; i < this.columnCount; ++i) {
            String string2 = this.result.getColumnName(i);
            if (!string2.equals(string)) continue;
            return i;
        }
        return -1;
    }

    private int getColumnIndex(String string) {
        int n = this.findColumnIndex(string);
        if (n < 0) {
            throw DbException.get(42122, string);
        }
        return n;
    }

    private void appendColumnList(StringBuilder stringBuilder, boolean bl) {
        for (int i = 0; i < this.columnCount; ++i) {
            if (i > 0) {
                stringBuilder.append(',');
            }
            String string = this.result.getColumnName(i);
            StringUtils.quoteIdentifier(stringBuilder, string);
            if (!bl) continue;
            stringBuilder.append("=? ");
        }
    }

    private void appendKeyCondition(StringBuilder stringBuilder) {
        stringBuilder.append(" WHERE ");
        for (int i = 0; i < this.key.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(" AND ");
            }
            StringUtils.quoteIdentifier(stringBuilder, this.key.get(i)).append("=?");
        }
    }

    private void setKey(PreparedStatement preparedStatement, int n, Value[] valueArray) throws SQLException {
        int n2 = this.key.size();
        for (int i = 0; i < n2; ++i) {
            String string = this.key.get(i);
            int n3 = this.getColumnIndex(string);
            Value value = valueArray[n3];
            if (value == null || value == ValueNull.INSTANCE) {
                throw DbException.get(2000);
            }
            JdbcUtils.set(preparedStatement, n + i, value, this.conn);
        }
    }

    private void appendTableName(StringBuilder stringBuilder) {
        if (this.schemaName != null && this.schemaName.length() > 0) {
            StringUtils.quoteIdentifier(stringBuilder, this.schemaName).append('.');
        }
        StringUtils.quoteIdentifier(stringBuilder, this.tableName);
    }

    public Value[] readRow(Value[] valueArray) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        this.appendColumnList(stringBuilder, false);
        stringBuilder.append(" FROM ");
        this.appendTableName(stringBuilder);
        this.appendKeyCondition(stringBuilder);
        PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuilder.toString());
        this.setKey(preparedStatement, 1, valueArray);
        JdbcResultSet jdbcResultSet = (JdbcResultSet)preparedStatement.executeQuery();
        if (!jdbcResultSet.next()) {
            throw DbException.get(2000);
        }
        Value[] valueArray2 = new Value[this.columnCount];
        for (int i = 0; i < this.columnCount; ++i) {
            valueArray2[i] = ValueToObjectConverter.readValue(this.conn.getSession(), jdbcResultSet, i + 1);
        }
        return valueArray2;
    }

    public void deleteRow(Value[] valueArray) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ");
        this.appendTableName(stringBuilder);
        this.appendKeyCondition(stringBuilder);
        PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuilder.toString());
        this.setKey(preparedStatement, 1, valueArray);
        int n = preparedStatement.executeUpdate();
        if (n != 1) {
            throw DbException.get(2000);
        }
    }

    public void updateRow(Value[] valueArray, Value[] valueArray2) throws SQLException {
        int n;
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");
        this.appendTableName(stringBuilder);
        stringBuilder.append(" SET ");
        this.appendColumnList(stringBuilder, true);
        this.appendKeyCondition(stringBuilder);
        PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuilder.toString());
        int n2 = 1;
        for (n = 0; n < this.columnCount; ++n) {
            Value value = valueArray2[n];
            if (value == null) {
                value = valueArray[n];
            }
            JdbcUtils.set(preparedStatement, n2++, value, this.conn);
        }
        this.setKey(preparedStatement, n2, valueArray);
        n = preparedStatement.executeUpdate();
        if (n != 1) {
            throw DbException.get(2000);
        }
    }

    public void insertRow(Value[] valueArray) throws SQLException {
        int n;
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        this.appendTableName(stringBuilder);
        stringBuilder.append('(');
        this.appendColumnList(stringBuilder, false);
        stringBuilder.append(")VALUES(");
        for (int i = 0; i < this.columnCount; ++i) {
            Value value;
            if (i > 0) {
                stringBuilder.append(',');
            }
            if ((value = valueArray[i]) == null) {
                stringBuilder.append("DEFAULT");
                continue;
            }
            stringBuilder.append('?');
        }
        stringBuilder.append(')');
        PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuilder.toString());
        int n2 = 0;
        for (n = 0; n < this.columnCount; ++n) {
            Value value = valueArray[n];
            if (value == null) continue;
            JdbcUtils.set(preparedStatement, n2++ + 1, value, this.conn);
        }
        n = preparedStatement.executeUpdate();
        if (n != 1) {
            throw DbException.get(2000);
        }
    }
}

