/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import org.h2.command.query.QueryOrderBy;
import org.h2.command.query.Select;
import org.h2.command.query.SelectGroups;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.analysis.PartitionData;
import org.h2.expression.analysis.Window;
import org.h2.expression.analysis.WindowFrame;
import org.h2.expression.analysis.WindowFrameBound;
import org.h2.expression.analysis.WindowFrameUnits;
import org.h2.message.DbException;
import org.h2.result.SortOrder;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;
import org.h2.value.ValueInteger;

public abstract class DataAnalysisOperation
extends Expression {
    public static final int STAGE_RESET = 0;
    public static final int STAGE_GROUP = 1;
    public static final int STAGE_WINDOW = 2;
    protected final Select select;
    protected Window over;
    protected SortOrder overOrderBySort;
    private int numFrameExpressions;
    private int lastGroupRowId;

    protected static SortOrder createOrder(SessionLocal sessionLocal, ArrayList<QueryOrderBy> arrayList, int n) {
        int n2 = arrayList.size();
        int[] nArray = new int[n2];
        int[] nArray2 = new int[n2];
        for (int i = 0; i < n2; ++i) {
            QueryOrderBy queryOrderBy = arrayList.get(i);
            nArray[i] = i + n;
            nArray2[i] = queryOrderBy.sortType;
        }
        return new SortOrder(sessionLocal, nArray, nArray2, null);
    }

    protected DataAnalysisOperation(Select select) {
        this.select = select;
    }

    public Window getOverCondition() {
        return this.over;
    }

    public void setOverCondition(Window window) {
        this.over = window;
    }

    public abstract boolean isAggregate();

    protected SortOrder getOverOrderBySort() {
        return this.overOrderBySort;
    }

    @Override
    public final void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (this.over != null) {
            if (n2 != 0) {
                throw DbException.get(90054, this.getTraceSQL());
            }
            n2 = 1;
        } else {
            if (n2 == 2) {
                throw DbException.get(90054, this.getTraceSQL());
            }
            n2 = 2;
        }
        this.mapColumnsAnalysis(columnResolver, n, n2);
    }

    protected void mapColumnsAnalysis(ColumnResolver columnResolver, int n, int n2) {
        if (this.over != null) {
            this.over.mapColumns(columnResolver, n);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        if (this.over != null) {
            this.over.optimize(sessionLocal);
            ArrayList<QueryOrderBy> arrayList = this.over.getOrderBy();
            if (arrayList != null) {
                this.overOrderBySort = DataAnalysisOperation.createOrder(sessionLocal, arrayList, this.getNumExpressions());
            } else if (!this.isAggregate()) {
                this.overOrderBySort = new SortOrder(sessionLocal, new int[this.getNumExpressions()]);
            }
            WindowFrame windowFrame = this.over.getWindowFrame();
            if (windowFrame != null) {
                int n = this.getNumExpressions();
                int n2 = 0;
                if (arrayList != null) {
                    n2 = arrayList.size();
                    n += n2;
                }
                int n3 = 0;
                WindowFrameBound windowFrameBound = windowFrame.getStarting();
                if (windowFrameBound.isParameterized()) {
                    this.checkOrderBy(windowFrame.getUnits(), n2);
                    if (windowFrameBound.isVariable()) {
                        windowFrameBound.setExpressionIndex(n);
                        ++n3;
                    }
                }
                if ((windowFrameBound = windowFrame.getFollowing()) != null && windowFrameBound.isParameterized()) {
                    this.checkOrderBy(windowFrame.getUnits(), n2);
                    if (windowFrameBound.isVariable()) {
                        windowFrameBound.setExpressionIndex(n + n3);
                        ++n3;
                    }
                }
                this.numFrameExpressions = n3;
            }
        }
        return this;
    }

    private void checkOrderBy(WindowFrameUnits windowFrameUnits, int n) {
        switch (windowFrameUnits) {
            case RANGE: {
                if (n == 1) break;
                String string = this.getTraceSQL();
                throw DbException.getSyntaxError(string, string.length() - 1, "exactly one sort key is required for RANGE units");
            }
            case GROUPS: {
                if (n >= 1) break;
                String string = this.getTraceSQL();
                throw DbException.getSyntaxError(string, string.length() - 1, "a sort key is required for GROUPS units");
            }
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        if (this.over != null) {
            this.over.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public final void updateAggregate(SessionLocal sessionLocal, int n) {
        if (n == 0) {
            this.updateGroupAggregates(sessionLocal, 0);
            this.lastGroupRowId = 0;
            return;
        }
        boolean bl = n == 2;
        if (bl != (this.over != null)) {
            if (!bl && this.select.isWindowQuery()) {
                this.updateGroupAggregates(sessionLocal, n);
            }
            return;
        }
        SelectGroups selectGroups = this.select.getGroupDataIfCurrent(bl);
        if (selectGroups == null) {
            return;
        }
        int n2 = selectGroups.getCurrentGroupRowId();
        if (this.lastGroupRowId == n2) {
            return;
        }
        this.lastGroupRowId = n2;
        if (this.over != null && !this.select.isGroupQuery()) {
            this.over.updateAggregate(sessionLocal, n);
        }
        this.updateAggregate(sessionLocal, selectGroups, n2);
    }

    protected abstract void updateAggregate(SessionLocal var1, SelectGroups var2, int var3);

    protected void updateGroupAggregates(SessionLocal sessionLocal, int n) {
        if (this.over != null) {
            this.over.updateAggregate(sessionLocal, n);
        }
    }

    protected abstract int getNumExpressions();

    private int getNumFrameExpressions() {
        return this.numFrameExpressions;
    }

    protected abstract void rememberExpressions(SessionLocal var1, Value[] var2);

    protected Object getWindowData(SessionLocal sessionLocal, SelectGroups selectGroups, boolean bl) {
        Object object;
        Value value = this.over.getCurrentKey(sessionLocal);
        PartitionData partitionData = selectGroups.getWindowExprData(this, value);
        if (partitionData == null) {
            object = bl ? new ArrayList() : this.createAggregateData();
            selectGroups.setWindowExprData(this, value, new PartitionData(object));
        } else {
            object = partitionData.getData();
        }
        return object;
    }

    protected Object getGroupData(SelectGroups selectGroups, boolean bl) {
        Object object = selectGroups.getCurrentGroupExprData(this);
        if (object == null) {
            if (bl) {
                return null;
            }
            object = this.createAggregateData();
            selectGroups.setCurrentGroupExprData(this, object);
        }
        return object;
    }

    protected abstract Object createAggregateData();

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (this.over == null) {
            return true;
        }
        switch (expressionVisitor.getType()) {
            case 0: 
            case 1: 
            case 2: 
            case 8: 
            case 11: {
                return false;
            }
        }
        return true;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        SelectGroups selectGroups = this.select.getGroupDataIfCurrent(this.over != null);
        if (selectGroups == null) {
            throw DbException.get(90054, this.getTraceSQL());
        }
        return this.over == null ? this.getAggregatedValue(sessionLocal, this.getGroupData(selectGroups, true)) : this.getWindowResult(sessionLocal, selectGroups);
    }

    private Value getWindowResult(SessionLocal sessionLocal, SelectGroups selectGroups) {
        Object object;
        boolean bl = this.over.isOrdered();
        Value value = this.over.getCurrentKey(sessionLocal);
        PartitionData partitionData = selectGroups.getWindowExprData(this, value);
        if (partitionData == null) {
            object = bl ? new ArrayList() : this.createAggregateData();
            partitionData = new PartitionData(object);
            selectGroups.setWindowExprData(this, value, partitionData);
        } else {
            object = partitionData.getData();
        }
        if (bl || !this.isAggregate()) {
            Value value2 = this.getOrderedResult(sessionLocal, selectGroups, partitionData, object);
            if (value2 == null) {
                return this.getAggregatedValue(sessionLocal, null);
            }
            return value2;
        }
        Value value3 = partitionData.getResult();
        if (value3 == null) {
            value3 = this.getAggregatedValue(sessionLocal, object);
            partitionData.setResult(value3);
        }
        return value3;
    }

    protected abstract Value getAggregatedValue(SessionLocal var1, Object var2);

    protected void updateOrderedAggregate(SessionLocal sessionLocal, SelectGroups selectGroups, int n, ArrayList<QueryOrderBy> arrayList) {
        Object object;
        int n2 = this.getNumExpressions();
        int n3 = arrayList != null ? arrayList.size() : 0;
        int n4 = this.getNumFrameExpressions();
        Value[] valueArray = new Value[n2 + n3 + n4 + 1];
        this.rememberExpressions(sessionLocal, valueArray);
        for (int i = 0; i < n3; ++i) {
            object = arrayList.get(i);
            valueArray[n2++] = ((QueryOrderBy)object).expression.getValue(sessionLocal);
        }
        if (n4 > 0) {
            WindowFrame windowFrame = this.over.getWindowFrame();
            object = windowFrame.getStarting();
            if (((WindowFrameBound)object).isVariable()) {
                valueArray[n2++] = ((WindowFrameBound)object).getValue().getValue(sessionLocal);
            }
            if ((object = windowFrame.getFollowing()) != null && ((WindowFrameBound)object).isVariable()) {
                valueArray[n2++] = ((WindowFrameBound)object).getValue().getValue(sessionLocal);
            }
        }
        valueArray[n2] = ValueInteger.get(n);
        ArrayList arrayList2 = (ArrayList)this.getWindowData(sessionLocal, selectGroups, true);
        arrayList2.add(valueArray);
    }

    private Value getOrderedResult(SessionLocal sessionLocal, SelectGroups selectGroups, PartitionData partitionData, Object object) {
        HashMap<Integer, Value> hashMap = partitionData.getOrderedResult();
        if (hashMap == null) {
            hashMap = new HashMap();
            ArrayList arrayList = (ArrayList)object;
            int n = this.getNumExpressions();
            ArrayList<QueryOrderBy> arrayList2 = this.over.getOrderBy();
            if (arrayList2 != null) {
                n += arrayList2.size();
                arrayList.sort(this.overOrderBySort);
            }
            this.getOrderedResultLoop(sessionLocal, hashMap, arrayList, n += this.getNumFrameExpressions());
            partitionData.setOrderedResult(hashMap);
        }
        return hashMap.get(selectGroups.getCurrentGroupRowId());
    }

    protected abstract void getOrderedResultLoop(SessionLocal var1, HashMap<Integer, Value> var2, ArrayList<Value[]> var3, int var4);

    protected StringBuilder appendTailConditions(StringBuilder stringBuilder, int n, boolean bl) {
        if (this.over != null) {
            stringBuilder.append(' ');
            this.over.getSQL(stringBuilder, n, bl);
        }
        return stringBuilder;
    }
}

