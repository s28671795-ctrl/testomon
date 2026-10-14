/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.engine.CastDataProvider;
import org.h2.mvstore.db.RowDataType;
import org.h2.result.DefaultRow;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SimpleRowValue;
import org.h2.result.Sparse;
import org.h2.store.DataHandler;
import org.h2.table.IndexColumn;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;
import org.h2.value.Value;

public abstract class RowFactory {
    public static DefaultRowFactory getDefaultRowFactory() {
        return DefaultRowFactory.INSTANCE;
    }

    public static RowFactory getRowFactory() {
        return Holder.EFFECTIVE;
    }

    public RowFactory createRowFactory(CastDataProvider castDataProvider, CompareMode compareMode, DataHandler dataHandler, Typed[] typedArray, IndexColumn[] indexColumnArray, boolean bl) {
        return this;
    }

    public abstract Row createRow(Value[] var1, int var2);

    public abstract SearchRow createRow();

    public abstract RowDataType getRowDataType();

    public abstract int[] getIndexes();

    public abstract TypeInfo[] getColumnTypes();

    public abstract int getColumnCount();

    public abstract boolean getStoreKeys();

    public static final class DefaultRowFactory
    extends RowFactory {
        private final RowDataType dataType;
        private final int columnCount;
        private final int[] indexes;
        private TypeInfo[] columnTypes;
        private final int[] map;
        public static final DefaultRowFactory INSTANCE = new DefaultRowFactory();

        DefaultRowFactory() {
            this(new RowDataType(null, CompareMode.getInstance(null, 0), null, null, null, 0, true), 0, null, null);
        }

        private DefaultRowFactory(RowDataType rowDataType, int n, int[] nArray, TypeInfo[] typeInfoArray) {
            this.dataType = rowDataType;
            this.columnCount = n;
            this.indexes = nArray;
            if (nArray == null) {
                this.map = null;
            } else {
                this.map = new int[n];
                int n2 = 0;
                int n3 = nArray.length;
                while (n2 < n3) {
                    this.map[nArray[n2]] = ++n2;
                }
            }
            this.columnTypes = typeInfoArray;
        }

        @Override
        public RowFactory createRowFactory(CastDataProvider castDataProvider, CompareMode compareMode, DataHandler dataHandler, Typed[] typedArray, IndexColumn[] indexColumnArray, boolean bl) {
            int[] nArray = null;
            int[] nArray2 = null;
            TypeInfo[] typeInfoArray = null;
            int n = 0;
            if (typedArray != null) {
                int n2;
                n = typedArray.length;
                if (indexColumnArray == null) {
                    nArray2 = new int[n];
                    for (n2 = 0; n2 < n; ++n2) {
                        nArray2[n2] = 0;
                    }
                } else {
                    n2 = indexColumnArray.length;
                    nArray = new int[n2];
                    nArray2 = new int[n2];
                    for (int i = 0; i < n2; ++i) {
                        IndexColumn indexColumn = indexColumnArray[i];
                        nArray[i] = indexColumn.column.getColumnId();
                        nArray2[i] = indexColumn.sortType;
                    }
                }
                typeInfoArray = new TypeInfo[n];
                for (n2 = 0; n2 < n; ++n2) {
                    typeInfoArray[n2] = typedArray[n2].getType();
                }
            }
            return this.createRowFactory(castDataProvider, compareMode, dataHandler, nArray2, nArray, typeInfoArray, n, bl);
        }

        public RowFactory createRowFactory(CastDataProvider castDataProvider, CompareMode compareMode, DataHandler dataHandler, int[] nArray, int[] nArray2, TypeInfo[] typeInfoArray, int n, boolean bl) {
            RowDataType rowDataType = new RowDataType(castDataProvider, compareMode, dataHandler, nArray, nArray2, n, bl);
            DefaultRowFactory defaultRowFactory = new DefaultRowFactory(rowDataType, n, nArray2, typeInfoArray);
            rowDataType.setRowFactory(defaultRowFactory);
            return defaultRowFactory;
        }

        @Override
        public Row createRow(Value[] valueArray, int n) {
            return new DefaultRow(valueArray, n);
        }

        @Override
        public SearchRow createRow() {
            if (this.indexes == null) {
                return new DefaultRow(this.columnCount);
            }
            if (this.indexes.length == 1) {
                return new SimpleRowValue(this.columnCount, this.indexes[0]);
            }
            return new Sparse(this.columnCount, this.indexes.length, this.map);
        }

        @Override
        public RowDataType getRowDataType() {
            return this.dataType;
        }

        @Override
        public int[] getIndexes() {
            return this.indexes;
        }

        @Override
        public TypeInfo[] getColumnTypes() {
            return this.columnTypes;
        }

        @Override
        public int getColumnCount() {
            return this.columnCount;
        }

        @Override
        public boolean getStoreKeys() {
            return this.dataType.isStoreKeys();
        }
    }

    private static final class Holder {
        static final RowFactory EFFECTIVE = DefaultRowFactory.INSTANCE;

        private Holder() {
        }
    }
}

