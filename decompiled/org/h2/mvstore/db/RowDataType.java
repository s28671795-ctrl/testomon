/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Database;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.db.ValueDataType;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.MetaType;
import org.h2.mvstore.type.StatefulDataType;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.store.DataHandler;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class RowDataType
extends BasicDataType<SearchRow>
implements StatefulDataType<Database> {
    private final ValueDataType valueDataType;
    private final int[] sortTypes;
    private final int[] indexes;
    private final int columnCount;
    private final boolean storeKeys;
    private static final Factory FACTORY = new Factory();

    public RowDataType(CastDataProvider castDataProvider, CompareMode compareMode, DataHandler dataHandler, int[] nArray, int[] nArray2, int n, boolean bl) {
        this.valueDataType = new ValueDataType(castDataProvider, compareMode, dataHandler, nArray);
        this.sortTypes = nArray;
        this.indexes = nArray2;
        this.columnCount = n;
        this.storeKeys = bl;
        assert (nArray2 == null || nArray.length == nArray2.length);
    }

    public int[] getIndexes() {
        return this.indexes;
    }

    public RowFactory getRowFactory() {
        return this.valueDataType.getRowFactory();
    }

    public void setRowFactory(RowFactory rowFactory) {
        this.valueDataType.setRowFactory(rowFactory);
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public boolean isStoreKeys() {
        return this.storeKeys;
    }

    public SearchRow[] createStorage(int n) {
        return new SearchRow[n];
    }

    @Override
    public int compare(SearchRow searchRow, SearchRow searchRow2) {
        if (searchRow == searchRow2) {
            return 0;
        }
        if (this.indexes == null) {
            int n = searchRow.getColumnCount();
            assert (n == searchRow2.getColumnCount()) : n + " != " + searchRow2.getColumnCount();
            for (int i = 0; i < n; ++i) {
                int n2 = this.valueDataType.compareValues(searchRow.getValue(i), searchRow2.getValue(i), this.sortTypes[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return 0;
        }
        return this.compareSearchRows(searchRow, searchRow2);
    }

    private int compareSearchRows(SearchRow searchRow, SearchRow searchRow2) {
        for (int i = 0; i < this.indexes.length; ++i) {
            int n = this.indexes[i];
            Value value = searchRow.getValue(n);
            Value value2 = searchRow2.getValue(n);
            if (value == null || value2 == null) break;
            int n2 = this.valueDataType.compareValues(value, value2, this.sortTypes[i]);
            if (n2 == 0) continue;
            return n2;
        }
        long l = searchRow.getKey();
        long l2 = searchRow2.getKey();
        return l == SearchRow.MATCH_ALL_ROW_KEY || l2 == SearchRow.MATCH_ALL_ROW_KEY ? 0 : Long.compare(l, l2);
    }

    @Override
    public int binarySearch(SearchRow searchRow, Object object, int n, int n2) {
        return this.binarySearch(searchRow, (SearchRow[])object, n, n2);
    }

    public int binarySearch(SearchRow searchRow, SearchRow[] searchRowArray, int n, int n2) {
        int n3 = 0;
        int n4 = n - 1;
        int n5 = n2 - 1;
        if (n5 < 0 || n5 > n4) {
            n5 = n4 >>> 1;
        }
        while (n3 <= n4) {
            int n6 = this.compareSearchRows(searchRow, searchRowArray[n5]);
            if (n6 > 0) {
                n3 = n5 + 1;
            } else if (n6 < 0) {
                n4 = n5 - 1;
            } else {
                return n5;
            }
            n5 = n3 + n4 >>> 1;
        }
        return -(n3 + 1);
    }

    @Override
    public int getMemory(SearchRow searchRow) {
        return searchRow.getMemory();
    }

    @Override
    public SearchRow read(ByteBuffer byteBuffer) {
        RowFactory rowFactory = this.valueDataType.getRowFactory();
        SearchRow searchRow = rowFactory.createRow();
        if (this.storeKeys) {
            searchRow.setKey(DataUtils.readVarLong(byteBuffer));
        }
        TypeInfo[] typeInfoArray = rowFactory.getColumnTypes();
        if (this.indexes == null) {
            int n = searchRow.getColumnCount();
            for (int i = 0; i < n; ++i) {
                searchRow.setValue(i, this.valueDataType.readValue(byteBuffer, typeInfoArray != null ? typeInfoArray[i] : null));
            }
        } else {
            for (int n : this.indexes) {
                searchRow.setValue(n, this.valueDataType.readValue(byteBuffer, typeInfoArray != null ? typeInfoArray[n] : null));
            }
        }
        return searchRow;
    }

    @Override
    public void write(WriteBuffer writeBuffer, SearchRow searchRow) {
        if (this.storeKeys) {
            writeBuffer.putVarLong(searchRow.getKey());
        }
        if (this.indexes == null) {
            int n = searchRow.getColumnCount();
            for (int i = 0; i < n; ++i) {
                this.valueDataType.write(writeBuffer, searchRow.getValue(i));
            }
        } else {
            for (int n : this.indexes) {
                this.valueDataType.write(writeBuffer, searchRow.getValue(n));
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != RowDataType.class) {
            return false;
        }
        RowDataType rowDataType = (RowDataType)object;
        return this.columnCount == rowDataType.columnCount && Arrays.equals(this.indexes, rowDataType.indexes) && Arrays.equals(this.sortTypes, rowDataType.sortTypes) && this.valueDataType.equals(rowDataType.valueDataType);
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + this.columnCount;
        n = n * 31 + Arrays.hashCode(this.indexes);
        n = n * 31 + Arrays.hashCode(this.sortTypes);
        n = n * 31 + this.valueDataType.hashCode();
        return n;
    }

    @Override
    public void save(WriteBuffer writeBuffer, MetaType<Database> metaType) {
        writeBuffer.putVarInt(this.columnCount);
        RowDataType.writeIntArray(writeBuffer, this.sortTypes);
        RowDataType.writeIntArray(writeBuffer, this.indexes);
        writeBuffer.put(this.storeKeys ? (byte)1 : 0);
    }

    private static void writeIntArray(WriteBuffer writeBuffer, int[] nArray) {
        if (nArray == null) {
            writeBuffer.putVarInt(0);
        } else {
            writeBuffer.putVarInt(nArray.length + 1);
            for (int n : nArray) {
                writeBuffer.putVarInt(n);
            }
        }
    }

    public Factory getFactory() {
        return FACTORY;
    }

    public static final class Factory
    implements StatefulDataType.Factory<Database> {
        public RowDataType create(ByteBuffer byteBuffer, MetaType<Database> metaType, Database database) {
            int n = DataUtils.readVarInt(byteBuffer);
            int[] nArray = Factory.readIntArray(byteBuffer);
            int[] nArray2 = Factory.readIntArray(byteBuffer);
            boolean bl = byteBuffer.get() != 0;
            CompareMode compareMode = database == null ? CompareMode.getInstance(null, 0) : database.getCompareMode();
            RowFactory rowFactory = RowFactory.getDefaultRowFactory().createRowFactory(database, compareMode, database, nArray, nArray2, null, n, bl);
            return rowFactory.getRowDataType();
        }

        private static int[] readIntArray(ByteBuffer byteBuffer) {
            int n = DataUtils.readVarInt(byteBuffer) - 1;
            if (n < 0) {
                return null;
            }
            int[] nArray = new int[n];
            for (int i = 0; i < nArray.length; ++i) {
                nArray[i] = DataUtils.readVarInt(byteBuffer);
            }
            return nArray;
        }
    }
}

