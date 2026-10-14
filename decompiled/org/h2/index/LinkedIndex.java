/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.LinkedCursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.TableFilter;
import org.h2.table.TableLink;
import org.h2.util.HasSQL;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class LinkedIndex
extends Index {
    private final TableLink link;
    private final String targetTableName;
    private long rowCount;
    private final int sqlFlags = 1;

    public LinkedIndex(TableLink tableLink, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType) {
        super(tableLink, n, null, indexColumnArray, n2, indexType);
        this.link = tableLink;
        this.targetTableName = this.link.getQualifiedTable();
    }

    @Override
    public String getCreateSQL() {
        return null;
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    private static boolean isNull(Value value) {
        return value == null || value == ValueNull.INSTANCE;
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        ArrayList<Value> arrayList = Utils.newSmallArrayList();
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(this.targetTableName).append(" VALUES(");
        for (int i = 0; i < row.getColumnCount(); ++i) {
            Value value = row.getValue(i);
            if (i > 0) {
                stringBuilder.append(", ");
            }
            if (value == null) {
                stringBuilder.append("DEFAULT");
                continue;
            }
            if (LinkedIndex.isNull(value)) {
                stringBuilder.append("NULL");
                continue;
            }
            stringBuilder.append('?');
            arrayList.add(value);
        }
        stringBuilder.append(')');
        String string = stringBuilder.toString();
        try {
            this.link.execute(string, arrayList, true, sessionLocal);
            ++this.rowCount;
        }
        catch (Exception exception) {
            throw TableLink.wrapException(string, exception);
        }
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        Object object;
        Object object2;
        int n;
        ArrayList<Value> arrayList = Utils.newSmallArrayList();
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ").append(this.targetTableName).append(" T");
        boolean bl = false;
        for (n = 0; searchRow != null && n < searchRow.getColumnCount(); ++n) {
            object2 = searchRow.getValue(n);
            if (object2 == null) continue;
            stringBuilder.append(bl ? " AND " : " WHERE ");
            bl = true;
            object = this.table.getColumn(n);
            this.addColumnName(stringBuilder, (Column)object);
            if (object2 == ValueNull.INSTANCE) {
                stringBuilder.append(" IS NULL");
                continue;
            }
            stringBuilder.append(">=");
            this.addParameter(stringBuilder, (Column)object);
            arrayList.add((Value)object2);
        }
        for (n = 0; searchRow2 != null && n < searchRow2.getColumnCount(); ++n) {
            object2 = searchRow2.getValue(n);
            if (object2 == null) continue;
            stringBuilder.append(bl ? " AND " : " WHERE ");
            bl = true;
            object = this.table.getColumn(n);
            this.addColumnName(stringBuilder, (Column)object);
            if (object2 == ValueNull.INSTANCE) {
                stringBuilder.append(" IS NULL");
                continue;
            }
            stringBuilder.append("<=");
            this.addParameter(stringBuilder, (Column)object);
            arrayList.add((Value)object2);
        }
        String string = stringBuilder.toString();
        try {
            object2 = this.link.execute(string, arrayList, false, sessionLocal);
            object = object2.getResultSet();
            return new LinkedCursor(this.link, (ResultSet)object, sessionLocal, string, (PreparedStatement)object2);
        }
        catch (Exception exception) {
            throw TableLink.wrapException(string, exception);
        }
    }

    private void addColumnName(StringBuilder stringBuilder, Column column) {
        String string = this.link.getIdentifierQuoteString();
        String string2 = column.getName();
        if (string == null || string.isEmpty() || string.equals(" ")) {
            stringBuilder.append(string2);
        } else if (string.equals("\"")) {
            stringBuilder.append('\"');
            int n = string2.indexOf(34);
            if (n < 0) {
                stringBuilder.append(string2);
            } else {
                stringBuilder.append(string2, 0, ++n).append('\"');
                int n2 = string2.length();
                while (n < n2) {
                    char c = string2.charAt(n);
                    if (c == '\"') {
                        stringBuilder.append('\"');
                    }
                    stringBuilder.append(c);
                    ++n;
                }
            }
            stringBuilder.append('\"');
        } else {
            stringBuilder.append(string).append(string2).append(string);
        }
    }

    private void addParameter(StringBuilder stringBuilder, Column column) {
        TypeInfo typeInfo = column.getType();
        if (typeInfo.getValueType() == 1 && this.link.isOracle()) {
            stringBuilder.append("CAST(? AS CHAR(").append(typeInfo.getPrecision()).append("))");
        } else {
            stringBuilder.append('?');
        }
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return 100L + this.getCostRangeIndex(nArray, this.rowCount + 1000L, tableFilterArray, n, sortOrder, false, allColumnsForPlan);
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("LINKED");
    }

    @Override
    public boolean needRebuild() {
        return false;
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        Object object;
        ArrayList<Value> arrayList = Utils.newSmallArrayList();
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ").append(this.targetTableName).append(" WHERE ");
        for (int i = 0; i < row.getColumnCount(); ++i) {
            if (i > 0) {
                stringBuilder.append("AND ");
            }
            object = this.table.getColumn(i);
            this.addColumnName(stringBuilder, (Column)object);
            Value value = row.getValue(i);
            if (LinkedIndex.isNull(value)) {
                stringBuilder.append(" IS NULL ");
                continue;
            }
            stringBuilder.append('=');
            this.addParameter(stringBuilder, (Column)object);
            arrayList.add(value);
            stringBuilder.append(' ');
        }
        String string = stringBuilder.toString();
        try {
            object = this.link.execute(string, arrayList, false, sessionLocal);
            int n = object.executeUpdate();
            this.link.reusePreparedStatement((PreparedStatement)object, string);
            this.rowCount -= (long)n;
        }
        catch (Exception exception) {
            throw TableLink.wrapException(string, exception);
        }
    }

    public void update(Row row, Row row2, SessionLocal sessionLocal) {
        HasSQL hasSQL;
        int n;
        ArrayList<Value> arrayList = Utils.newSmallArrayList();
        StringBuilder stringBuilder = new StringBuilder("UPDATE ").append(this.targetTableName).append(" SET ");
        for (n = 0; n < row2.getColumnCount(); ++n) {
            if (n > 0) {
                stringBuilder.append(", ");
            }
            this.table.getColumn(n).getSQL(stringBuilder, 1).append('=');
            hasSQL = row2.getValue(n);
            if (hasSQL == null) {
                stringBuilder.append("DEFAULT");
                continue;
            }
            stringBuilder.append('?');
            arrayList.add((Value)hasSQL);
        }
        stringBuilder.append(" WHERE ");
        for (n = 0; n < row.getColumnCount(); ++n) {
            hasSQL = this.table.getColumn(n);
            if (n > 0) {
                stringBuilder.append(" AND ");
            }
            this.addColumnName(stringBuilder, (Column)hasSQL);
            Value value = row.getValue(n);
            if (LinkedIndex.isNull(value)) {
                stringBuilder.append(" IS NULL");
                continue;
            }
            stringBuilder.append('=');
            arrayList.add(value);
            this.addParameter(stringBuilder, (Column)hasSQL);
        }
        String string = stringBuilder.toString();
        try {
            this.link.execute(string, arrayList, true, sessionLocal);
        }
        catch (Exception exception) {
            throw TableLink.wrapException(string, exception);
        }
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.rowCount;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.rowCount;
    }
}

