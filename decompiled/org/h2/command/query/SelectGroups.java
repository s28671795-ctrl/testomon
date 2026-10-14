/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.analysis.DataAnalysisOperation;
import org.h2.expression.analysis.PartitionData;
import org.h2.value.Value;
import org.h2.value.ValueRow;

public abstract class SelectGroups {
    final SessionLocal session;
    final ArrayList<Expression> expressions;
    Object[] currentGroupByExprData;
    private final HashMap<Expression, Integer> exprToIndexInGroupByData = new HashMap();
    private final HashMap<DataAnalysisOperation, PartitionData> windowData = new HashMap();
    private final HashMap<DataAnalysisOperation, TreeMap<Value, PartitionData>> windowPartitionData = new HashMap();
    int currentGroupRowId;

    public static SelectGroups getInstance(SessionLocal sessionLocal, ArrayList<Expression> arrayList, boolean bl, int[] nArray) {
        return bl ? new Grouped(sessionLocal, arrayList, nArray) : new Plain(sessionLocal, arrayList);
    }

    SelectGroups(SessionLocal sessionLocal, ArrayList<Expression> arrayList) {
        this.session = sessionLocal;
        this.expressions = arrayList;
    }

    public boolean isCurrentGroup() {
        return this.currentGroupByExprData != null;
    }

    public final Object getCurrentGroupExprData(Expression expression) {
        Integer n = this.exprToIndexInGroupByData.get(expression);
        if (n == null) {
            return null;
        }
        return this.currentGroupByExprData[n];
    }

    public final void setCurrentGroupExprData(Expression expression, Object object) {
        Integer n = this.exprToIndexInGroupByData.get(expression);
        if (n != null) {
            assert (this.currentGroupByExprData[n] == null);
            this.currentGroupByExprData[n.intValue()] = object;
            return;
        }
        n = this.exprToIndexInGroupByData.size();
        this.exprToIndexInGroupByData.put(expression, n);
        if (n >= this.currentGroupByExprData.length) {
            this.currentGroupByExprData = Arrays.copyOf(this.currentGroupByExprData, this.currentGroupByExprData.length * 2);
            this.updateCurrentGroupExprData();
        }
        this.currentGroupByExprData[n.intValue()] = object;
    }

    final Object[] createRow() {
        return new Object[Math.max(this.exprToIndexInGroupByData.size(), this.expressions.size())];
    }

    public final PartitionData getWindowExprData(DataAnalysisOperation dataAnalysisOperation, Value value) {
        if (value == null) {
            return this.windowData.get(dataAnalysisOperation);
        }
        TreeMap<Value, PartitionData> treeMap = this.windowPartitionData.get(dataAnalysisOperation);
        return treeMap != null ? treeMap.get(value) : null;
    }

    public final void setWindowExprData(DataAnalysisOperation dataAnalysisOperation, Value value, PartitionData partitionData) {
        if (value == null) {
            PartitionData partitionData2 = this.windowData.put(dataAnalysisOperation, partitionData);
            assert (partitionData2 == null);
        } else {
            TreeMap<Value, PartitionData> treeMap = this.windowPartitionData.get(dataAnalysisOperation);
            if (treeMap == null) {
                treeMap = new TreeMap(this.session.getDatabase().getCompareMode());
                this.windowPartitionData.put(dataAnalysisOperation, treeMap);
            }
            treeMap.put(value, partitionData);
        }
    }

    abstract void updateCurrentGroupExprData();

    public int getCurrentGroupRowId() {
        return this.currentGroupRowId;
    }

    public void reset() {
        this.currentGroupByExprData = null;
        this.exprToIndexInGroupByData.clear();
        this.windowData.clear();
        this.windowPartitionData.clear();
        this.currentGroupRowId = 0;
    }

    public abstract void nextSource();

    public void done() {
        this.currentGroupRowId = 0;
    }

    public abstract ValueRow next();

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void resetLazy() {
        this.currentGroupByExprData = null;
        this.currentGroupRowId = 0;
    }

    public void nextLazyGroup() {
        this.currentGroupByExprData = new Object[Math.max(this.exprToIndexInGroupByData.size(), this.expressions.size())];
    }

    public void nextLazyRow() {
        ++this.currentGroupRowId;
    }

    private static final class Plain
    extends SelectGroups {
        private ArrayList<Object[]> rows;
        private Iterator<Object[]> cursor;

        Plain(SessionLocal sessionLocal, ArrayList<Expression> arrayList) {
            super(sessionLocal, arrayList);
        }

        @Override
        public void reset() {
            super.reset();
            this.rows = new ArrayList();
            this.cursor = null;
        }

        @Override
        public void nextSource() {
            Object[] objectArray = this.createRow();
            this.rows.add(objectArray);
            this.currentGroupByExprData = objectArray;
            ++this.currentGroupRowId;
        }

        @Override
        void updateCurrentGroupExprData() {
            this.rows.set(this.rows.size() - 1, this.currentGroupByExprData);
        }

        @Override
        public void done() {
            super.done();
            this.cursor = this.rows.iterator();
        }

        @Override
        public ValueRow next() {
            if (this.cursor.hasNext()) {
                this.currentGroupByExprData = this.cursor.next();
                ++this.currentGroupRowId;
                return ValueRow.EMPTY;
            }
            return null;
        }
    }

    private static final class Grouped
    extends SelectGroups {
        private final int[] groupIndex;
        private TreeMap<ValueRow, Object[]> groupByData;
        private ValueRow currentGroupsKey;
        private Iterator<Map.Entry<ValueRow, Object[]>> cursor;

        Grouped(SessionLocal sessionLocal, ArrayList<Expression> arrayList, int[] nArray) {
            super(sessionLocal, arrayList);
            this.groupIndex = nArray;
        }

        @Override
        public void reset() {
            super.reset();
            this.groupByData = new TreeMap(this.session.getDatabase().getCompareMode());
            this.currentGroupsKey = null;
            this.cursor = null;
        }

        @Override
        public void nextSource() {
            Object[] objectArray;
            if (this.groupIndex == null) {
                this.currentGroupsKey = ValueRow.EMPTY;
            } else {
                objectArray = new Value[this.groupIndex.length];
                for (int i = 0; i < this.groupIndex.length; ++i) {
                    int n = this.groupIndex[i];
                    Expression expression = (Expression)this.expressions.get(n);
                    objectArray[i] = expression.getValue(this.session);
                }
                this.currentGroupsKey = ValueRow.get((Value[])objectArray);
            }
            objectArray = this.groupByData.get(this.currentGroupsKey);
            if (objectArray == null) {
                objectArray = this.createRow();
                this.groupByData.put(this.currentGroupsKey, objectArray);
            }
            this.currentGroupByExprData = objectArray;
            ++this.currentGroupRowId;
        }

        @Override
        void updateCurrentGroupExprData() {
            if (this.currentGroupsKey != null) {
                this.groupByData.put(this.currentGroupsKey, this.currentGroupByExprData);
            }
        }

        @Override
        public void done() {
            super.done();
            if (this.groupIndex == null && this.groupByData.size() == 0) {
                this.groupByData.put(ValueRow.EMPTY, this.createRow());
            }
            this.cursor = this.groupByData.entrySet().iterator();
        }

        @Override
        public ValueRow next() {
            if (this.cursor.hasNext()) {
                Map.Entry<ValueRow, Object[]> entry = this.cursor.next();
                this.currentGroupByExprData = entry.getValue();
                ++this.currentGroupRowId;
                return entry.getKey();
            }
            return null;
        }

        @Override
        public void remove() {
            this.cursor.remove();
            this.currentGroupByExprData = null;
            --this.currentGroupRowId;
        }

        @Override
        public void resetLazy() {
            super.resetLazy();
            this.currentGroupsKey = null;
        }
    }
}

