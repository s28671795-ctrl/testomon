/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.h2.command.query.Select;
import org.h2.command.query.SelectGroups;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.expression.analysis.DataAnalysisOperation;
import org.h2.expression.analysis.WindowFrame;
import org.h2.expression.analysis.WindowFunctionType;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueDouble;
import org.h2.value.ValueNull;

public class WindowFunction
extends DataAnalysisOperation {
    private final WindowFunctionType type;
    private final Expression[] args;
    private boolean fromLast;
    private boolean ignoreNulls;

    public static int getMinArgumentCount(WindowFunctionType windowFunctionType) {
        switch (windowFunctionType) {
            case NTILE: 
            case LEAD: 
            case LAG: 
            case FIRST_VALUE: 
            case LAST_VALUE: 
            case RATIO_TO_REPORT: {
                return 1;
            }
            case NTH_VALUE: {
                return 2;
            }
        }
        return 0;
    }

    public static int getMaxArgumentCount(WindowFunctionType windowFunctionType) {
        switch (windowFunctionType) {
            case NTILE: 
            case FIRST_VALUE: 
            case LAST_VALUE: 
            case RATIO_TO_REPORT: {
                return 1;
            }
            case LEAD: 
            case LAG: {
                return 3;
            }
            case NTH_VALUE: {
                return 2;
            }
        }
        return 0;
    }

    private static Value getNthValue(Iterator<Value[]> iterator2, int n, boolean bl) {
        Value value = ValueNull.INSTANCE;
        int n2 = 0;
        while (iterator2.hasNext()) {
            Value value2 = iterator2.next()[0];
            if (bl && value2 == ValueNull.INSTANCE || n2++ != n) continue;
            value = value2;
            break;
        }
        return value;
    }

    public WindowFunction(WindowFunctionType windowFunctionType, Select select, Expression[] expressionArray) {
        super(select);
        this.type = windowFunctionType;
        this.args = expressionArray;
    }

    public WindowFunctionType getFunctionType() {
        return this.type;
    }

    public void setFromLast(boolean bl) {
        this.fromLast = bl;
    }

    public void setIgnoreNulls(boolean bl) {
        this.ignoreNulls = bl;
    }

    @Override
    public boolean isAggregate() {
        return false;
    }

    @Override
    protected void updateAggregate(SessionLocal sessionLocal, SelectGroups selectGroups, int n) {
        this.updateOrderedAggregate(sessionLocal, selectGroups, n, this.over.getOrderBy());
    }

    @Override
    protected void updateGroupAggregates(SessionLocal sessionLocal, int n) {
        super.updateGroupAggregates(sessionLocal, n);
        if (this.args != null) {
            for (Expression expression : this.args) {
                expression.updateAggregate(sessionLocal, n);
            }
        }
    }

    @Override
    protected int getNumExpressions() {
        return this.args != null ? this.args.length : 0;
    }

    @Override
    protected void rememberExpressions(SessionLocal sessionLocal, Value[] valueArray) {
        if (this.args != null) {
            int n = this.args.length;
            for (int i = 0; i < n; ++i) {
                valueArray[i] = this.args[i].getValue(sessionLocal);
            }
        }
    }

    @Override
    protected Object createAggregateData() {
        throw DbException.getUnsupportedException("Window function");
    }

    @Override
    protected void getOrderedResultLoop(SessionLocal sessionLocal, HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        switch (this.type) {
            case ROW_NUMBER: {
                int n2 = 0;
                int n3 = arrayList.size();
                while (n2 < n3) {
                    hashMap.put(arrayList.get(n2)[n].getInt(), ValueBigint.get(++n2));
                }
                break;
            }
            case RANK: 
            case DENSE_RANK: 
            case PERCENT_RANK: {
                this.getRank(hashMap, arrayList, n);
                break;
            }
            case CUME_DIST: {
                this.getCumeDist(hashMap, arrayList, n);
                break;
            }
            case NTILE: {
                WindowFunction.getNtile(hashMap, arrayList, n);
                break;
            }
            case LEAD: 
            case LAG: {
                this.getLeadLag(hashMap, arrayList, n, sessionLocal);
                break;
            }
            case FIRST_VALUE: 
            case LAST_VALUE: 
            case NTH_VALUE: {
                this.getNth(sessionLocal, hashMap, arrayList, n);
                break;
            }
            case RATIO_TO_REPORT: {
                WindowFunction.getRatioToReport(hashMap, arrayList, n);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.type));
            }
        }
    }

    private void getRank(HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        int n2 = arrayList.size();
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4;
            Value[] valueArray = arrayList.get(i);
            if (i == 0) {
                n3 = 1;
            } else if (this.getOverOrderBySort().compare(arrayList.get(i - 1), valueArray) != 0) {
                n3 = this.type == WindowFunctionType.DENSE_RANK ? ++n3 : i + 1;
            }
            Value value = this.type == WindowFunctionType.PERCENT_RANK ? ((n4 = n3 - 1) == 0 ? ValueDouble.ZERO : ValueDouble.get((double)n4 / (double)(n2 - 1))) : ValueBigint.get(n3);
            hashMap.put(valueArray[n].getInt(), value);
        }
    }

    private void getCumeDist(HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        int n2 = arrayList.size();
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            Value[] valueArray = arrayList.get(n3);
            for (n4 = n3 + 1; n4 < n2 && this.overOrderBySort.compare(valueArray, arrayList.get(n4)) == 0; ++n4) {
            }
            ValueDouble valueDouble = ValueDouble.get((double)n4 / (double)n2);
            for (int i = n3; i < n4; ++i) {
                int n5 = arrayList.get(i)[n].getInt();
                hashMap.put(n5, valueDouble);
            }
            n3 = n4;
        }
    }

    private static void getNtile(HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            Value[] valueArray = arrayList.get(i);
            long l = valueArray[0].getLong();
            if (l <= 0L) {
                throw DbException.getInvalidValueException("number of tiles", l);
            }
            long l2 = (long)n2 / l;
            long l3 = (long)n2 - l2 * l;
            long l4 = l3 * (l2 + 1L);
            long l5 = (long)i >= l4 ? ((long)i - l4) / l2 + l3 + 1L : (long)i / (l2 + 1L) + 1L;
            hashMap.put(arrayList.get(i)[n].getInt(), ValueBigint.get(l5));
        }
    }

    private void getLeadLag(HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n, SessionLocal sessionLocal) {
        int n2 = arrayList.size();
        int n3 = this.getNumExpressions();
        TypeInfo typeInfo = this.args[0].getType();
        for (int i = 0; i < n2; ++i) {
            int n4;
            int n5;
            Value[] valueArray = arrayList.get(i);
            int n6 = valueArray[n].getInt();
            if (n3 >= 2) {
                n5 = valueArray[1].getInt();
                if (n5 < 0) {
                    throw DbException.getInvalidValueException("nth row", n5);
                }
            } else {
                n5 = 1;
            }
            Value value = null;
            if (n5 == 0) {
                value = arrayList.get(i)[0];
            } else if (this.type == WindowFunctionType.LEAD) {
                if (this.ignoreNulls) {
                    for (n4 = i + 1; n5 > 0 && n4 < n2; ++n4) {
                        value = arrayList.get(n4)[0];
                        if (value == ValueNull.INSTANCE) continue;
                        --n5;
                    }
                    if (n5 > 0) {
                        value = null;
                    }
                } else if (n5 <= n2 - i - 1) {
                    value = arrayList.get(i + n5)[0];
                }
            } else if (this.ignoreNulls) {
                for (n4 = i - 1; n5 > 0 && n4 >= 0; --n4) {
                    value = arrayList.get(n4)[0];
                    if (value == ValueNull.INSTANCE) continue;
                    --n5;
                }
                if (n5 > 0) {
                    value = null;
                }
            } else if (n5 <= i) {
                value = arrayList.get(i - n5)[0];
            }
            if (value == null) {
                value = n3 >= 3 ? valueArray[2].convertTo(typeInfo, (CastDataProvider)sessionLocal) : ValueNull.INSTANCE;
            }
            hashMap.put(n6, value);
        }
    }

    private void getNth(SessionLocal sessionLocal, HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            Value value;
            Value[] valueArray = arrayList.get(i);
            int n3 = valueArray[n].getInt();
            switch (this.type) {
                case FIRST_VALUE: {
                    value = WindowFunction.getNthValue(WindowFrame.iterator(this.over, sessionLocal, arrayList, this.getOverOrderBySort(), i, false), 0, this.ignoreNulls);
                    break;
                }
                case LAST_VALUE: {
                    value = WindowFunction.getNthValue(WindowFrame.iterator(this.over, sessionLocal, arrayList, this.getOverOrderBySort(), i, true), 0, this.ignoreNulls);
                    break;
                }
                case NTH_VALUE: {
                    int n4 = valueArray[1].getInt();
                    if (n4 <= 0) {
                        throw DbException.getInvalidValueException("nth row", n4);
                    }
                    Iterator<Value[]> iterator2 = WindowFrame.iterator(this.over, sessionLocal, arrayList, this.getOverOrderBySort(), i, this.fromLast);
                    value = WindowFunction.getNthValue(iterator2, --n4, this.ignoreNulls);
                    break;
                }
                default: {
                    throw DbException.getInternalError("type=" + (Object)((Object)this.type));
                }
            }
            hashMap.put(n3, value);
        }
    }

    private static void getRatioToReport(HashMap<Integer, Value> hashMap, ArrayList<Value[]> arrayList, int n) {
        Value[] valueArray;
        int n2;
        int n3 = arrayList.size();
        Value value = null;
        for (n2 = 0; n2 < n3; ++n2) {
            valueArray = arrayList.get(n2)[0];
            if (valueArray == ValueNull.INSTANCE) continue;
            value = value == null ? valueArray.convertToDouble() : value.add(valueArray.convertToDouble());
        }
        if (value != null && value.getSignum() == 0) {
            value = null;
        }
        for (n2 = 0; n2 < n3; ++n2) {
            Value value2;
            valueArray = arrayList.get(n2);
            if (value == null) {
                value2 = ValueNull.INSTANCE;
            } else {
                value2 = valueArray[0];
                if (value2 != ValueNull.INSTANCE) {
                    value2 = value2.convertToDouble().divide(value, TypeInfo.TYPE_DOUBLE);
                }
            }
            hashMap.put(valueArray[n].getInt(), value2);
        }
    }

    @Override
    protected Value getAggregatedValue(SessionLocal sessionLocal, Object object) {
        throw DbException.getUnsupportedException("Window function");
    }

    @Override
    public void mapColumnsAnalysis(ColumnResolver columnResolver, int n, int n2) {
        if (this.args != null) {
            for (Expression expression : this.args) {
                expression.mapColumns(columnResolver, n, n2);
            }
        }
        super.mapColumnsAnalysis(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        if (this.over.getWindowFrame() != null) {
            switch (this.type) {
                case FIRST_VALUE: 
                case LAST_VALUE: 
                case NTH_VALUE: {
                    break;
                }
                default: {
                    String string = this.getTraceSQL();
                    throw DbException.getSyntaxError(string, string.length() - 1);
                }
            }
        }
        if (this.over.getOrderBy() == null) {
            if (this.type.requiresWindowOrdering()) {
                String string = this.getTraceSQL();
                throw DbException.getSyntaxError(string, string.length() - 1, "ORDER BY");
            }
        } else if (this.type == WindowFunctionType.RATIO_TO_REPORT) {
            String string = this.getTraceSQL();
            throw DbException.getSyntaxError(string, string.length() - 1);
        }
        super.optimize(sessionLocal);
        if (this.over.getOrderBy() == null) {
            switch (this.type) {
                case RANK: 
                case DENSE_RANK: {
                    return ValueExpression.get(ValueBigint.get(1L));
                }
                case PERCENT_RANK: {
                    return ValueExpression.get(ValueDouble.ZERO);
                }
                case CUME_DIST: {
                    return ValueExpression.get(ValueDouble.ONE);
                }
            }
        }
        if (this.args != null) {
            for (int i = 0; i < this.args.length; ++i) {
                this.args[i] = this.args[i].optimize(sessionLocal);
            }
        }
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        if (this.args != null) {
            for (Expression expression : this.args) {
                expression.setEvaluatable(tableFilter, bl);
            }
        }
        super.setEvaluatable(tableFilter, bl);
    }

    @Override
    public TypeInfo getType() {
        switch (this.type) {
            case NTILE: 
            case ROW_NUMBER: 
            case RANK: 
            case DENSE_RANK: {
                return TypeInfo.TYPE_BIGINT;
            }
            case RATIO_TO_REPORT: 
            case PERCENT_RANK: 
            case CUME_DIST: {
                return TypeInfo.TYPE_DOUBLE;
            }
            case LEAD: 
            case LAG: 
            case FIRST_VALUE: 
            case LAST_VALUE: 
            case NTH_VALUE: {
                return this.args[0].getType();
            }
        }
        throw DbException.getInternalError("type=" + (Object)((Object)this.type));
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.type.getSQL()).append('(');
        if (this.args != null) {
            WindowFunction.writeExpressions(stringBuilder, this.args, n);
        }
        stringBuilder.append(')');
        if (this.fromLast && this.type == WindowFunctionType.NTH_VALUE) {
            stringBuilder.append(" FROM LAST");
        }
        if (this.ignoreNulls) {
            switch (this.type) {
                case LEAD: 
                case LAG: 
                case FIRST_VALUE: 
                case LAST_VALUE: 
                case NTH_VALUE: {
                    stringBuilder.append(" IGNORE NULLS");
                }
            }
        }
        return this.appendTailConditions(stringBuilder, n, this.type.requiresWindowOrdering());
    }

    @Override
    public int getCost() {
        int n = 1;
        if (this.args != null) {
            for (Expression expression : this.args) {
                n += expression.getCost();
            }
        }
        return n;
    }
}

