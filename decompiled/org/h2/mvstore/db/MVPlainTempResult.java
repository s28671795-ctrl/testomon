/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import org.h2.engine.Database;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.db.MVTempResult;
import org.h2.mvstore.db.ValueDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.LongDataType;
import org.h2.result.ResultExternal;
import org.h2.result.RowFactory;
import org.h2.value.Value;
import org.h2.value.ValueRow;

class MVPlainTempResult
extends MVTempResult {
    private final MVMap<Long, ValueRow> map;
    private long counter;
    private Cursor<Long, ValueRow> cursor;

    private MVPlainTempResult(MVPlainTempResult mVPlainTempResult) {
        super(mVPlainTempResult);
        this.map = mVPlainTempResult.map;
    }

    MVPlainTempResult(Database database, Expression[] expressionArray, int n, int n2) {
        super(database, expressionArray, n, n2);
        ValueDataType valueDataType = new ValueDataType(database, new int[n2]);
        valueDataType.setRowFactory(RowFactory.DefaultRowFactory.INSTANCE.createRowFactory(database, database.getCompareMode(), database, expressionArray, null, false));
        MVMap.Builder builder = ((MVMap.Builder)((MVMap.Builder)new MVMap.Builder().keyType((DataType)LongDataType.INSTANCE)).valueType((DataType)valueDataType)).singleWriter();
        this.map = this.store.openMap("tmp", builder);
    }

    @Override
    public int addRow(Value[] valueArray) {
        assert (this.parent == null);
        this.map.append(this.counter++, ValueRow.get(valueArray));
        return ++this.rowCount;
    }

    @Override
    public boolean contains(Value[] valueArray) {
        throw DbException.getUnsupportedException("contains()");
    }

    @Override
    public synchronized ResultExternal createShallowCopy() {
        if (this.parent != null) {
            return this.parent.createShallowCopy();
        }
        if (this.closed) {
            return null;
        }
        ++this.childCount;
        return new MVPlainTempResult(this);
    }

    @Override
    public Value[] next() {
        if (this.cursor == null) {
            this.cursor = this.map.cursor(null);
        }
        if (!this.cursor.hasNext()) {
            return null;
        }
        this.cursor.next();
        return this.cursor.getValue().getList();
    }

    @Override
    public int removeRow(Value[] valueArray) {
        throw DbException.getUnsupportedException("removeRow()");
    }

    @Override
    public void reset() {
        this.cursor = null;
    }
}

