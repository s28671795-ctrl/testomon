/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.MetaIndex;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;
import org.h2.value.ValueVarcharIgnoreCase;

public abstract class MetaTable
extends Table {
    public static final long ROW_COUNT_APPROXIMATION = 1000L;
    protected final int type;
    protected int indexColumn;
    protected MetaIndex metaIndex;

    protected MetaTable(Schema schema, int n, int n2) {
        super(schema, n, null, true, true);
        this.type = n2;
    }

    protected final void setMetaTableName(String string) {
        this.setObjectName(this.database.sysIdentifier(string));
    }

    final Column column(String string) {
        return new Column(this.database.sysIdentifier(string), this.database.getSettings().caseInsensitiveIdentifiers ? TypeInfo.TYPE_VARCHAR_IGNORECASE : TypeInfo.TYPE_VARCHAR);
    }

    protected final Column column(String string, TypeInfo typeInfo) {
        return new Column(this.database.sysIdentifier(string), typeInfo);
    }

    @Override
    public final String getCreateSQL() {
        return null;
    }

    @Override
    public final Index addIndex(SessionLocal sessionLocal, String string, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType, boolean bl, String string2) {
        throw DbException.getUnsupportedException("META");
    }

    protected final String identifier(String string) {
        if (this.database.getSettings().databaseToLower) {
            string = string == null ? null : StringUtils.toLowerEnglish(string);
        }
        return string;
    }

    protected final boolean checkIndex(SessionLocal sessionLocal, String string, Value value, Value value2) {
        if (string == null || value == null && value2 == null) {
            return true;
        }
        Value value3 = this.database.getSettings().caseInsensitiveIdentifiers ? ValueVarcharIgnoreCase.get(string) : ValueVarchar.get(string);
        if (value != null && sessionLocal.compare(value3, value) < 0) {
            return false;
        }
        return value2 == null || sessionLocal.compare(value3, value2) <= 0;
    }

    protected final boolean hideTable(Table table, SessionLocal sessionLocal) {
        return table.isHidden() && sessionLocal != this.database.getSystemSession();
    }

    public abstract ArrayList<Row> generateRows(SessionLocal var1, SearchRow var2, SearchRow var3);

    @Override
    public boolean isInsertable() {
        return false;
    }

    @Override
    public final void removeRow(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public final void addRow(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public final void removeChildrenAndResources(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public final void close(SessionLocal sessionLocal) {
    }

    protected final void add(SessionLocal sessionLocal, ArrayList<Row> arrayList, Object ... objectArray) {
        Value[] valueArray = new Value[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            Object object = objectArray[i];
            ValueNull valueNull = object == null ? ValueNull.INSTANCE : (object instanceof String ? ValueVarchar.get((String)object) : (Value)object);
            valueArray[i] = this.columns[i].convert(sessionLocal, valueNull);
        }
        arrayList.add(Row.get(valueArray, 1, arrayList.size()));
    }

    @Override
    public final void checkRename() {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public final void checkSupportAlter() {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public final long truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("META");
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return false;
    }

    @Override
    public final boolean canDrop() {
        return false;
    }

    @Override
    public final TableType getTableType() {
        return TableType.SYSTEM_TABLE;
    }

    @Override
    public final Index getScanIndex(SessionLocal sessionLocal) {
        return new MetaIndex(this, IndexColumn.wrap(this.columns), true);
    }

    @Override
    public final ArrayList<Index> getIndexes() {
        ArrayList<Index> arrayList = new ArrayList<Index>(2);
        if (this.metaIndex == null) {
            return arrayList;
        }
        arrayList.add(new MetaIndex(this, IndexColumn.wrap(this.columns), true));
        arrayList.add(this.metaIndex);
        return arrayList;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return 1000L;
    }

    @Override
    public final boolean isDeterministic() {
        return true;
    }

    @Override
    public final boolean canReference() {
        return false;
    }
}

