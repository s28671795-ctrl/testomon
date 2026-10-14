/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.Arrays;
import java.util.BitSet;
import org.h2.engine.Database;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.mvstore.Cursor;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.db.MVTempResult;
import org.h2.mvstore.db.NullValueDataType;
import org.h2.mvstore.db.ValueDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.LongDataType;
import org.h2.result.ResultExternal;
import org.h2.result.RowFactory;
import org.h2.result.SortOrder;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

class MVSortedTempResult
extends MVTempResult {
    private final boolean distinct;
    private final int[] distinctIndexes;
    private final int[] indexes;
    private final MVMap<ValueRow, Long> map;
    private MVMap<ValueRow, Value> index;
    private ValueDataType orderedDistinctOnType;
    private Cursor<ValueRow, Long> cursor;
    private Value[] current;
    private long valueCount;

    private MVSortedTempResult(MVSortedTempResult mVSortedTempResult) {
        super(mVSortedTempResult);
        this.distinct = mVSortedTempResult.distinct;
        this.distinctIndexes = mVSortedTempResult.distinctIndexes;
        this.indexes = mVSortedTempResult.indexes;
        this.map = mVSortedTempResult.map;
        this.rowCount = mVSortedTempResult.rowCount;
    }

    MVSortedTempResult(Database database, Expression[] expressionArray, boolean bl, int[] nArray, int n, int n2, SortOrder sortOrder) {
        int n3;
        int n4;
        Typed[] typedArray;
        int n5;
        Object object;
        int[] nArray2;
        int[] nArray3;
        block15: {
            super(database, expressionArray, n, n2);
            this.distinct = bl;
            this.distinctIndexes = nArray;
            nArray3 = new int[n2];
            if (sortOrder != null) {
                nArray2 = new int[n2];
                object = sortOrder.getQueryColumnIndexes();
                n5 = ((int[])object).length;
                typedArray = new BitSet();
                for (n4 = 0; n4 < n5; ++n4) {
                    n3 = object[n4];
                    assert (!typedArray.get(n3));
                    typedArray.set(n3);
                    nArray2[n4] = n3;
                    nArray3[n4] = sortOrder.getSortTypes()[n4];
                }
                n4 = 0;
                for (n3 = n5; n3 < n2; ++n3) {
                    n4 = typedArray.nextClearBit(n4);
                    nArray2[n3] = n4++;
                }
                for (n3 = 0; n3 < n2; ++n3) {
                    if (nArray2[n3] == n3) {
                        continue;
                    }
                    break block15;
                }
                nArray2 = null;
            } else {
                nArray2 = null;
            }
        }
        this.indexes = nArray2;
        object = new ValueDataType(database, SortOrder.addNullOrdering(database, nArray3));
        if (nArray2 != null) {
            n5 = nArray2.length;
            typedArray = new TypeInfo[n5];
            for (n4 = 0; n4 < n5; ++n4) {
                typedArray[n4] = expressionArray[nArray2[n4]].getType();
            }
            ((ValueDataType)object).setRowFactory(RowFactory.DefaultRowFactory.INSTANCE.createRowFactory(database, database.getCompareMode(), database, typedArray, null, false));
        } else {
            ((ValueDataType)object).setRowFactory(RowFactory.DefaultRowFactory.INSTANCE.createRowFactory(database, database.getCompareMode(), database, expressionArray, null, false));
        }
        MVMap.BasicBuilder basicBuilder = ((MVMap.Builder)new MVMap.Builder().keyType((DataType)object)).valueType((DataType)LongDataType.INSTANCE);
        this.map = this.store.openMap("tmp", basicBuilder);
        if (bl && n2 != n || nArray != null) {
            Typed[] typedArray2;
            int n6;
            if (nArray != null) {
                n6 = nArray.length;
                typedArray2 = new TypeInfo[n6];
                for (n3 = 0; n3 < n6; ++n3) {
                    typedArray2[n3] = expressionArray[nArray[n3]].getType();
                }
            } else {
                n6 = n;
                typedArray2 = new TypeInfo[n6];
                for (n3 = 0; n3 < n6; ++n3) {
                    typedArray2[n3] = expressionArray[n3].getType();
                }
            }
            ValueDataType valueDataType = new ValueDataType(database, new int[n6]);
            valueDataType.setRowFactory(RowFactory.DefaultRowFactory.INSTANCE.createRowFactory(database, database.getCompareMode(), database, typedArray2, null, false));
            Object object2 = nArray != null && sortOrder != null ? (this.orderedDistinctOnType = object) : NullValueDataType.INSTANCE;
            MVMap.BasicBuilder basicBuilder2 = ((MVMap.Builder)new MVMap.Builder().keyType((DataType)valueDataType)).valueType((DataType)object2);
            this.index = this.store.openMap("idx", basicBuilder2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int addRow(Value[] valueArray) {
        assert (this.parent == null);
        ValueRow valueRow = this.getKey(valueArray);
        if (this.distinct || this.distinctIndexes != null) {
            ValueRow valueRow2;
            if (this.distinctIndexes != null) {
                int n = this.distinctIndexes.length;
                Value[] valueArray2 = new Value[n];
                for (int i = 0; i < n; ++i) {
                    valueArray2[i] = valueArray[this.distinctIndexes[i]];
                }
                ValueRow valueRow3 = ValueRow.get(valueArray2);
                if (this.orderedDistinctOnType == null) {
                    if (this.index.putIfAbsent(valueRow3, ValueNull.INSTANCE) != null) {
                        return this.rowCount;
                    }
                } else {
                    ValueRow valueRow4 = (ValueRow)this.index.get(valueRow3);
                    if (valueRow4 == null) {
                        this.index.put(valueRow3, valueRow);
                    } else {
                        if (this.orderedDistinctOnType.compare(valueRow4, valueRow) <= 0) return this.rowCount;
                        this.map.remove(valueRow4);
                        --this.rowCount;
                        this.index.put(valueRow3, valueRow);
                    }
                }
            } else if (this.visibleColumnCount != this.resultColumnCount && this.index.putIfAbsent(valueRow2 = ValueRow.get(Arrays.copyOf(valueArray, this.visibleColumnCount)), ValueNull.INSTANCE) != null) {
                return this.rowCount;
            }
            if (this.map.putIfAbsent(valueRow, 1L) != null) return this.rowCount;
            ++this.rowCount;
            return this.rowCount;
        } else {
            Long l = this.map.putIfAbsent(valueRow, 1L);
            if (l != null) {
                this.map.put(valueRow, l + 1L);
            }
            ++this.rowCount;
        }
        return this.rowCount;
    }

    @Override
    public boolean contains(Value[] valueArray) {
        if (this.parent != null) {
            return this.parent.contains(valueArray);
        }
        assert (this.distinct);
        if (this.visibleColumnCount != this.resultColumnCount) {
            return this.index.containsKey(ValueRow.get(valueArray));
        }
        return this.map.containsKey(this.getKey(valueArray));
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
        return new MVSortedTempResult(this);
    }

    private ValueRow getKey(Value[] valueArray) {
        if (this.indexes != null) {
            Value[] valueArray2 = new Value[this.indexes.length];
            for (int i = 0; i < this.indexes.length; ++i) {
                valueArray2[i] = valueArray[this.indexes[i]];
            }
            valueArray = valueArray2;
        }
        return ValueRow.get(valueArray);
    }

    private Value[] getValue(Value[] valueArray) {
        if (this.indexes != null) {
            Value[] valueArray2 = new Value[this.indexes.length];
            for (int i = 0; i < this.indexes.length; ++i) {
                valueArray2[this.indexes[i]] = valueArray[i];
            }
            valueArray = valueArray2;
        }
        return valueArray;
    }

    @Override
    public Value[] next() {
        if (this.cursor == null) {
            this.cursor = this.map.cursor(null);
            this.current = null;
            this.valueCount = 0L;
        }
        if (--this.valueCount > 0L) {
            return this.current;
        }
        if (!this.cursor.hasNext()) {
            this.current = null;
            return null;
        }
        this.current = this.getValue(this.cursor.next().getList());
        this.valueCount = this.cursor.getValue();
        return this.current;
    }

    @Override
    public int removeRow(Value[] valueArray) {
        assert (this.parent == null && this.distinct);
        if (this.visibleColumnCount != this.resultColumnCount) {
            throw DbException.getUnsupportedException("removeRow()");
        }
        if (this.map.remove(this.getKey(valueArray)) != null) {
            --this.rowCount;
        }
        return this.rowCount;
    }

    @Override
    public void reset() {
        this.cursor = null;
        this.current = null;
        this.valueCount = 0L;
    }
}

