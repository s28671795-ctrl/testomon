/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.util.ArrayList;
import java.util.HashMap;
import org.h2.command.query.Select;
import org.h2.command.query.SelectGroups;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.analysis.DataAnalysisOperation;
import org.h2.expression.analysis.WindowFrame;
import org.h2.expression.analysis.WindowFrameBound;
import org.h2.expression.analysis.WindowFrameBoundType;
import org.h2.expression.analysis.WindowFrameExclusion;
import org.h2.expression.analysis.WindowFrameUnits;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public abstract class AbstractAggregate
extends DataAnalysisOperation {
    protected final boolean distinct;
    protected final Expression[] args;
    protected Expression filterCondition;
    protected TypeInfo type;

    AbstractAggregate(Select select, Expression[] expressionArray, boolean bl) {
        super(select);
        this.args = expressionArray;
        this.distinct = bl;
    }

    @Override
    public final boolean isAggregate() {
        return true;
    }

    public Expression getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(Expression expression) {
        this.filterCondition = expression;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public void mapColumnsAnalysis(ColumnResolver columnResolver, int n, int n2) {
        for (Expression expression : this.args) {
            expression.mapColumns(columnResolver, n, n2);
        }
        if (this.filterCondition != null) {
            this.filterCondition.mapColumns(columnResolver, n, n2);
        }
        super.mapColumnsAnalysis(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        for (int i = 0; i < this.args.length; ++i) {
            this.args[i] = this.args[i].optimize(sessionLocal);
        }
        if (this.filterCondition != null) {
            this.filterCondition = this.filterCondition.optimizeCondition(sessionLocal);
        }
        return super.optimize(sessionLocal);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        for (Expression expression : this.args) {
            expression.setEvaluatable(tableFilter, bl);
        }
        if (this.filterCondition != null) {
            this.filterCondition.setEvaluatable(tableFilter, bl);
        }
        super.setEvaluatable(tableFilter, bl);
    }

    @Override
    protected void getOrderedResultLoop(SessionLocal sessionLocal, HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        int n2;
        boolean bl;
        WindowFrame windowFrame = this.over.getWindowFrame();
        boolean bl2 = bl = windowFrame == null || windowFrame.getUnits() != WindowFrameUnits.ROWS && windowFrame.getExclusion().isGroupOrNoOthers();
        if (windowFrame == null) {
            this.aggregateFastPartition(sessionLocal, hashMap, arrayList, n, bl);
            return;
        }
        boolean bl3 = windowFrame.isVariableBounds();
        if (bl3) {
            bl3 = AbstractAggregate.checkVariableBounds(windowFrame, arrayList);
        }
        if (bl3) {
            bl = false;
        } else if (windowFrame.getExclusion() == WindowFrameExclusion.EXCLUDE_NO_OTHERS) {
            WindowFrameBound windowFrameBound = windowFrame.getFollowing();
            int n3 = n2 = windowFrameBound != null && windowFrameBound.getType() == WindowFrameBoundType.UNBOUNDED_FOLLOWING ? 1 : 0;
            if (windowFrame.getStarting().getType() == WindowFrameBoundType.UNBOUNDED_PRECEDING) {
                if (n2 != 0) {
                    this.aggregateWholePartition(sessionLocal, hashMap, arrayList, n);
                } else {
                    this.aggregateFastPartition(sessionLocal, hashMap, arrayList, n, bl);
                }
                return;
            }
            if (n2 != 0) {
                this.aggregateFastPartitionInReverse(sessionLocal, hashMap, arrayList, n, bl);
                return;
            }
        }
        int n4 = arrayList.size();
        n2 = 0;
        while (n2 < n4) {
            Object object = this.createAggregateData();
            Object object2 = WindowFrame.iterator(this.over, sessionLocal, arrayList, this.getOverOrderBySort(), n2, false);
            while (object2.hasNext()) {
                this.updateFromExpressions(sessionLocal, object, object2.next());
            }
            object2 = this.getAggregatedValue(sessionLocal, object);
            n2 = this.processGroup(hashMap, (Value)object2, arrayList, n, n2, n4, bl);
        }
    }

    private static boolean checkVariableBounds(WindowFrame windowFrame, ArrayList<Value[]> arrayList) {
        int n;
        Value value;
        int n2;
        int n3 = arrayList.size();
        WindowFrameBound windowFrameBound = windowFrame.getStarting();
        if (windowFrameBound.isVariable()) {
            n2 = windowFrameBound.getExpressionIndex();
            value = arrayList.get(0)[n2];
            for (n = 1; n < n3; ++n) {
                if (value.equals(arrayList.get(n)[n2])) continue;
                return true;
            }
        }
        if ((windowFrameBound = windowFrame.getFollowing()) != null && windowFrameBound.isVariable()) {
            n2 = windowFrameBound.getExpressionIndex();
            value = arrayList.get(0)[n2];
            for (n = 1; n < n3; ++n) {
                if (value.equals(arrayList.get(n)[n2])) continue;
                return true;
            }
        }
        return false;
    }

    private void aggregateFastPartition(SessionLocal sessionLocal, HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n, boolean bl) {
        Object object = this.createAggregateData();
        int n2 = arrayList.size();
        int n3 = -1;
        Value value = null;
        int n4 = 0;
        while (n4 < n2) {
            int n5 = WindowFrame.getEndIndex(this.over, sessionLocal, arrayList, this.getOverOrderBySort(), n4);
            assert (n5 >= n3);
            if (n5 > n3) {
                for (int i = n3 + 1; i <= n5; ++i) {
                    this.updateFromExpressions(sessionLocal, object, arrayList.get(i));
                }
                n3 = n5;
                value = this.getAggregatedValue(sessionLocal, object);
            } else if (value == null) {
                value = this.getAggregatedValue(sessionLocal, object);
            }
            n4 = this.processGroup(hashMap, value, arrayList, n, n4, n2, bl);
        }
    }

    private void aggregateFastPartitionInReverse(SessionLocal sessionLocal, HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n, boolean bl) {
        Object object = this.createAggregateData();
        int n2 = arrayList.size();
        Value value = null;
        int n3 = n2 - 1;
        while (n3 >= 0) {
            Value[] valueArray;
            int n4 = this.over.getWindowFrame().getStartIndex(sessionLocal, arrayList, this.getOverOrderBySort(), n3);
            assert (n4 <= n2);
            if (n4 < n2) {
                for (int i = n2 - 1; i >= n4; --i) {
                    this.updateFromExpressions(sessionLocal, object, arrayList.get(i));
                }
                n2 = n4;
                value = this.getAggregatedValue(sessionLocal, object);
            } else if (value == null) {
                value = this.getAggregatedValue(sessionLocal, object);
            }
            Value[] valueArray2 = valueArray = arrayList.get(n3);
            do {
                hashMap.put(valueArray2[n].getInt(), value);
            } while (--n3 >= 0 && bl && this.overOrderBySort.compare(valueArray, valueArray2 = arrayList.get(n3)) == 0);
        }
    }

    private int processGroup(HashMap<Integer, Value> hashMap, Value value, ArrayList<Value[]> arrayList, int n, int n2, int n3, boolean bl) {
        Value[] valueArray;
        Value[] valueArray2 = valueArray = arrayList.get(n2);
        do {
            hashMap.put(valueArray2[n].getInt(), value);
        } while (++n2 < n3 && bl && this.overOrderBySort.compare(valueArray, valueArray2 = arrayList.get(n2)) == 0);
        return n2;
    }

    private void aggregateWholePartition(SessionLocal sessionLocal, HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        Object object = this.createAggregateData();
        for (Value[] object2 : arrayList) {
            this.updateFromExpressions(sessionLocal, object, object2);
        }
        Value value = this.getAggregatedValue(sessionLocal, object);
        for (Value[] valueArray : arrayList) {
            hashMap.put(valueArray[n].getInt(), value);
        }
    }

    protected abstract void updateFromExpressions(SessionLocal var1, Object var2, Value[] var3);

    @Override
    protected void updateAggregate(SessionLocal sessionLocal, SelectGroups selectGroups, int n) {
        if (this.filterCondition == null || this.filterCondition.getBooleanValue(sessionLocal)) {
            if (this.over != null) {
                if (this.over.isOrdered()) {
                    this.updateOrderedAggregate(sessionLocal, selectGroups, n, this.over.getOrderBy());
                } else {
                    this.updateAggregate(sessionLocal, this.getWindowData(sessionLocal, selectGroups, false));
                }
            } else {
                this.updateAggregate(sessionLocal, this.getGroupData(selectGroups, false));
            }
        } else if (this.over != null && this.over.isOrdered()) {
            this.updateOrderedAggregate(sessionLocal, selectGroups, n, this.over.getOrderBy());
        }
    }

    protected abstract void updateAggregate(SessionLocal var1, Object var2);

    @Override
    protected void updateGroupAggregates(SessionLocal sessionLocal, int n) {
        if (this.filterCondition != null) {
            this.filterCondition.updateAggregate(sessionLocal, n);
        }
        super.updateGroupAggregates(sessionLocal, n);
    }

    @Override
    protected StringBuilder appendTailConditions(StringBuilder stringBuilder, int n, boolean bl) {
        if (this.filterCondition != null) {
            stringBuilder.append(" FILTER (WHERE ");
            this.filterCondition.getUnenclosedSQL(stringBuilder, n).append(')');
        }
        return super.appendTailConditions(stringBuilder, n, bl);
    }

    @Override
    public int getSubexpressionCount() {
        return this.args.length;
    }

    @Override
    public Expression getSubexpression(int n) {
        return this.args[n];
    }
}

