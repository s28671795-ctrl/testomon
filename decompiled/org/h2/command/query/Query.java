/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.h2.command.Prepared;
import org.h2.command.query.ForUpdate;
import org.h2.command.query.QueryOrderBy;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Alias;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.DerivedTable;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.Utils;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;

public abstract class Query
extends Prepared {
    ArrayList<Expression> expressions;
    Expression[] expressionArray;
    ArrayList<QueryOrderBy> orderList;
    SortOrder sort;
    Expression fetchExpr;
    boolean fetchPercent;
    boolean withTies;
    Expression offsetExpr;
    boolean distinct;
    boolean randomAccessResult;
    int visibleColumnCount;
    int resultColumnCount;
    private boolean noCache;
    private long lastLimit;
    private long lastEvaluated;
    private ResultInterface lastResult;
    private Boolean lastExists;
    private Value[] lastParameters;
    private boolean cacheableChecked;
    private boolean neverLazy;
    boolean checkInit;
    boolean isPrepared;

    Query(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setNeverLazy(boolean bl) {
        this.neverLazy = bl;
    }

    public boolean isNeverLazy() {
        return this.neverLazy;
    }

    public abstract boolean isUnion();

    @Override
    public ResultInterface queryMeta() {
        LocalResult localResult = new LocalResult(this.session, this.expressionArray, this.visibleColumnCount, this.resultColumnCount);
        localResult.done();
        return localResult;
    }

    protected abstract ResultInterface queryWithoutCache(long var1, ResultTarget var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ResultInterface queryWithoutCacheLazyCheck(long l, ResultTarget resultTarget) {
        boolean bl;
        boolean bl2 = bl = this.neverLazy && this.session.isLazyQueryExecution();
        if (bl) {
            this.session.setLazyQueryExecution(false);
        }
        try {
            ResultInterface resultInterface = this.queryWithoutCache(l, resultTarget);
            return resultInterface;
        }
        finally {
            if (bl) {
                this.session.setLazyQueryExecution(true);
            }
        }
    }

    public abstract void init();

    @Override
    public final void prepare() {
        if (!this.checkInit) {
            throw DbException.getInternalError("not initialized");
        }
        if (this.isPrepared) {
            return;
        }
        this.prepareExpressions();
        this.preparePlan();
    }

    public abstract void prepareExpressions();

    public abstract void preparePlan();

    public ArrayList<Expression> getExpressions() {
        return this.expressions;
    }

    public abstract double getCost();

    public int getCostAsExpression() {
        return (int)Math.min(1000000.0, 10.0 + 10.0 * this.getCost());
    }

    public abstract HashSet<Table> getTables();

    public void setOrder(ArrayList<QueryOrderBy> arrayList) {
        this.orderList = arrayList;
    }

    public boolean hasOrder() {
        return this.orderList != null || this.sort != null;
    }

    public ForUpdate getForUpdate() {
        return null;
    }

    public abstract void setForUpdate(ForUpdate var1);

    public int getColumnCount() {
        return this.visibleColumnCount;
    }

    public TypeInfo getRowDataType() {
        if (this.visibleColumnCount == 1) {
            return this.expressionArray[0].getType();
        }
        return TypeInfo.getTypeInfo(41, -1L, -1, new ExtTypeInfoRow(this.expressionArray, this.visibleColumnCount));
    }

    public abstract void mapColumns(ColumnResolver var1, int var2);

    public abstract void setEvaluatable(TableFilter var1, boolean var2);

    public abstract void addGlobalCondition(Parameter var1, int var2, int var3);

    public abstract boolean allowGlobalConditions();

    public abstract boolean isEverything(ExpressionVisitor var1);

    @Override
    public boolean isReadOnly() {
        return this.isEverything(ExpressionVisitor.READONLY_VISITOR);
    }

    public abstract void updateAggregate(SessionLocal var1, int var2);

    public abstract void fireBeforeSelectTriggers();

    public void setDistinctIfPossible() {
        if (!this.isAnyDistinct() && this.offsetExpr == null && this.fetchExpr == null) {
            this.distinct = true;
        }
    }

    public boolean isStandardDistinct() {
        return this.distinct;
    }

    public boolean isAnyDistinct() {
        return this.distinct;
    }

    public boolean isRandomAccessResult() {
        return this.randomAccessResult;
    }

    public void setRandomAccessResult(boolean bl) {
        this.randomAccessResult = bl;
    }

    @Override
    public boolean isQuery() {
        return true;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    public void disableCache() {
        this.noCache = true;
    }

    private boolean sameResultAsLast(Value[] valueArray, Value[] valueArray2, long l) {
        if (!this.cacheableChecked) {
            long l2 = this.getMaxDataModificationId();
            boolean bl = this.noCache = l2 == Long.MAX_VALUE;
            if (!this.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR) || !this.isEverything(ExpressionVisitor.INDEPENDENT_VISITOR)) {
                this.noCache = true;
            }
            this.cacheableChecked = true;
        }
        if (this.noCache) {
            return false;
        }
        for (int i = 0; i < valueArray.length; ++i) {
            Value value = valueArray2[i];
            Value value2 = valueArray[i];
            if (value == null || value.equals(value2)) continue;
            return false;
        }
        return this.getMaxDataModificationId() <= l;
    }

    private Value[] getParameterValues() {
        ArrayList<Parameter> arrayList = this.getParameters();
        if (arrayList == null) {
            return Value.EMPTY_VALUES;
        }
        int n = arrayList.size();
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            Parameter parameter = arrayList.get(i);
            valueArray[i] = parameter != null ? parameter.getParamValue() : null;
        }
        return valueArray;
    }

    @Override
    public final ResultInterface query(long l) {
        return this.query(l, null);
    }

    public final ResultInterface query(long l, ResultTarget resultTarget) {
        ResultInterface resultInterface;
        if (this.isUnion()) {
            return this.queryWithoutCacheLazyCheck(l, resultTarget);
        }
        this.fireBeforeSelectTriggers();
        if (this.noCache || !this.getDatabase().getOptimizeReuseResults() || this.session.isLazyQueryExecution() && !this.neverLazy) {
            return this.queryWithoutCacheLazyCheck(l, resultTarget);
        }
        Value[] valueArray = this.getParameterValues();
        long l2 = this.getDatabase().getModificationDataId();
        if (this.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR) && this.lastResult != null && !this.lastResult.isClosed() && l == this.lastLimit && this.sameResultAsLast(valueArray, this.lastParameters, this.lastEvaluated)) {
            this.lastResult = this.lastResult.createShallowCopy(this.session);
            if (this.lastResult != null) {
                this.lastResult.reset();
                return this.lastResult;
            }
        }
        this.lastParameters = valueArray;
        this.closeLastResult();
        this.lastResult = resultInterface = this.queryWithoutCacheLazyCheck(l, resultTarget);
        this.lastExists = null;
        this.lastEvaluated = l2;
        this.lastLimit = l;
        return resultInterface;
    }

    private void closeLastResult() {
        if (this.lastResult != null) {
            this.lastResult.close();
        }
    }

    public final boolean exists() {
        if (this.isUnion()) {
            return this.executeExists();
        }
        this.fireBeforeSelectTriggers();
        if (this.noCache || !this.getDatabase().getOptimizeReuseResults()) {
            return this.executeExists();
        }
        Value[] valueArray = this.getParameterValues();
        long l = this.getDatabase().getModificationDataId();
        if (this.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR) && this.lastExists != null && this.sameResultAsLast(valueArray, this.lastParameters, this.lastEvaluated)) {
            return this.lastExists;
        }
        this.lastParameters = valueArray;
        boolean bl = this.executeExists();
        this.lastExists = bl;
        this.lastResult = null;
        this.lastEvaluated = l;
        return bl;
    }

    private boolean executeExists() {
        ResultInterface resultInterface = this.queryWithoutCacheLazyCheck(1L, null);
        boolean bl = resultInterface.hasNext();
        resultInterface.close();
        return bl;
    }

    boolean initOrder(ArrayList<String> arrayList, boolean bl, ArrayList<TableFilter> arrayList2) {
        Iterator<QueryOrderBy> iterator2 = this.orderList.iterator();
        while (iterator2.hasNext()) {
            QueryOrderBy queryOrderBy = iterator2.next();
            Expression expression = queryOrderBy.expression;
            if (expression == null) continue;
            if (expression.isConstant()) {
                iterator2.remove();
                continue;
            }
            int n = this.initExpression(arrayList, expression, bl, arrayList2);
            queryOrderBy.columnIndexExpr = ValueExpression.get(ValueInteger.get(n + 1));
            queryOrderBy.expression = this.expressions.get(n).getNonAliasExpression();
        }
        if (this.orderList.isEmpty()) {
            this.orderList = null;
            return false;
        }
        return true;
    }

    int initExpression(ArrayList<String> arrayList, Expression expression, boolean bl, ArrayList<TableFilter> arrayList2) {
        Object object;
        Database database = this.getDatabase();
        if (expression instanceof ExpressionColumn) {
            object = (ExpressionColumn)expression;
            String string = ((ExpressionColumn)object).getOriginalTableAliasName();
            String string2 = ((ExpressionColumn)object).getOriginalColumnName();
            int n = this.getColumnCount();
            for (int i = 0; i < n; ++i) {
                Object object3;
                Expression expression2;
                Expression expression3 = this.expressions.get(i);
                if (expression3 instanceof ExpressionColumn) {
                    expression2 = (ExpressionColumn)expression3;
                    if (!database.equalsIdentifiers(string2, ((ExpressionColumn)expression2).getColumnName(this.session, i))) continue;
                    if (string == null) {
                        return i;
                    }
                    object3 = ((ExpressionColumn)expression2).getOriginalTableAliasName();
                    if (object3 != null) {
                        if (!database.equalsIdentifiers((String)object3, string)) continue;
                        return i;
                    }
                    if (arrayList2 == null) continue;
                    for (TableFilter tableFilter : arrayList2) {
                        if (!database.equalsIdentifiers(tableFilter.getTableAlias(), string)) continue;
                        return i;
                    }
                    continue;
                }
                if (!(expression3 instanceof Alias)) continue;
                if (string == null && database.equalsIdentifiers(string2, expression3.getAlias(this.session, i))) {
                    return i;
                }
                expression2 = expression3.getNonAliasExpression();
                if (!(expression2 instanceof ExpressionColumn)) continue;
                object3 = expression2;
                String string3 = ((Expression)object).getSQL(0, 2);
                String string4 = ((Expression)object3).getSQL(0, 2);
                String string5 = ((ExpressionColumn)object3).getColumnName(this.session, i);
                if (!database.equalsIdentifiers(string2, string5) || !database.equalsIdentifiers(string3, string4)) continue;
                return i;
            }
        } else if (arrayList != null) {
            object = expression.getSQL(0, 2);
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                if (!database.equalsIdentifiers(arrayList.get(i), (String)object)) continue;
                return i;
            }
        }
        if (arrayList == null || bl && !database.getMode().allowUnrelatedOrderByExpressionsInDistinctQueries && !Query.checkOrderOther(this.session, expression, arrayList)) {
            throw DbException.get(90068, expression.getTraceSQL());
        }
        int n = this.expressions.size();
        this.expressions.add(expression);
        arrayList.add(expression.getSQL(0, 2));
        return n;
    }

    private static boolean checkOrderOther(SessionLocal sessionLocal, Expression expression, ArrayList<String> arrayList) {
        if (expression == null || expression.isConstant()) {
            return true;
        }
        String string = expression.getSQL(0, 2);
        for (String string2 : arrayList) {
            if (!sessionLocal.getDatabase().equalsIdentifiers(string, string2)) continue;
            return true;
        }
        int n = expression.getSubexpressionCount();
        if (!expression.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR)) {
            return false;
        }
        if (n <= 0) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (Query.checkOrderOther(sessionLocal, expression.getSubexpression(i), arrayList)) continue;
            return false;
        }
        return true;
    }

    void prepareOrder(ArrayList<QueryOrderBy> arrayList, int n) {
        int n2 = arrayList.size();
        int[] nArray = new int[n2];
        int[] nArray2 = new int[n2];
        for (int i = 0; i < n2; ++i) {
            int n3;
            QueryOrderBy queryOrderBy = arrayList.get(i);
            boolean bl = false;
            Value value = queryOrderBy.columnIndexExpr.getValue(null);
            if (value == ValueNull.INSTANCE) {
                n3 = 0;
            } else {
                n3 = value.getInt();
                if (n3 < 0) {
                    bl = true;
                    n3 = -n3;
                }
                if (--n3 < 0 || n3 >= n) {
                    throw DbException.get(90068, Integer.toString(n3 + 1));
                }
            }
            nArray[i] = n3;
            int n4 = queryOrderBy.sortType;
            if (bl) {
                n4 ^= 1;
            }
            nArray2[i] = n4;
        }
        this.sort = new SortOrder(this.session, nArray, nArray2, arrayList);
        this.orderList = null;
    }

    void cleanupOrder() {
        int n;
        int[] nArray = this.sort.getQueryColumnIndexes();
        int n2 = nArray.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            if (!this.expressions.get(nArray[n]).isConstant()) continue;
            ++n3;
        }
        if (n3 == 0) {
            return;
        }
        if (n3 == n2) {
            this.sort = null;
            return;
        }
        n = n2 - n3;
        int[] nArray2 = new int[n];
        int[] nArray3 = new int[n];
        int[] nArray4 = this.sort.getSortTypes();
        ArrayList<QueryOrderBy> arrayList = this.sort.getOrderList();
        int n4 = 0;
        int n5 = 0;
        while (n5 < n) {
            if (!this.expressions.get(nArray[n4]).isConstant()) {
                nArray2[n5] = nArray[n4];
                nArray3[n5] = nArray4[n4];
                ++n5;
            } else {
                arrayList.remove(n5);
            }
            ++n4;
        }
        this.sort = new SortOrder(this.session, nArray2, nArray3, arrayList);
    }

    @Override
    public int getType() {
        return 66;
    }

    public void setOffset(Expression expression) {
        this.offsetExpr = expression;
    }

    public Expression getOffset() {
        return this.offsetExpr;
    }

    public void setFetch(Expression expression) {
        this.fetchExpr = expression;
    }

    public Expression getFetch() {
        return this.fetchExpr;
    }

    public void setFetchPercent(boolean bl) {
        this.fetchPercent = bl;
    }

    public boolean isFetchPercent() {
        return this.fetchPercent;
    }

    public void setWithTies(boolean bl) {
        this.withTies = bl;
    }

    public boolean isWithTies() {
        return this.withTies;
    }

    void addParameter(Parameter parameter) {
        if (this.parameters == null) {
            this.parameters = Utils.newSmallArrayList();
        }
        this.parameters.add(parameter);
    }

    public final long getMaxDataModificationId() {
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getMaxModificationIdVisitor();
        this.isEverything(expressionVisitor);
        return Math.max(expressionVisitor.getMaxDataModificationId(), this.session.getSnapshotDataModificationId());
    }

    void appendEndOfQueryToSQL(StringBuilder stringBuilder, int n, Expression[] expressionArray) {
        int n2;
        if (this.sort != null) {
            this.sort.getSQL(stringBuilder.append("\nORDER BY "), expressionArray, this.visibleColumnCount, n);
        } else if (this.orderList != null) {
            stringBuilder.append("\nORDER BY ");
            n2 = this.orderList.size();
            for (int i = 0; i < n2; ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                this.orderList.get(i).getSQL(stringBuilder, n);
            }
        }
        if (this.offsetExpr != null) {
            String string = this.offsetExpr.getSQL(n, 2);
            stringBuilder.append("\nOFFSET ").append(string).append("1".equals(string) ? " ROW" : " ROWS");
        }
        if (this.fetchExpr != null) {
            stringBuilder.append("\nFETCH ").append(this.offsetExpr != null ? "NEXT" : "FIRST");
            String string = this.fetchExpr.getSQL(n, 2);
            int n3 = n2 = this.fetchPercent || !"1".equals(string) ? 1 : 0;
            if (n2 != 0) {
                stringBuilder.append(' ').append(string);
                if (this.fetchPercent) {
                    stringBuilder.append(" PERCENT");
                }
            }
            stringBuilder.append(n2 == 0 ? " ROW" : " ROWS").append(this.withTies ? " WITH TIES" : " ONLY");
        }
    }

    OffsetFetch getOffsetFetch(long l) {
        boolean bl;
        long l2;
        long l3;
        if (this.offsetExpr != null) {
            Value value = this.offsetExpr.getValue(this.session);
            if (value == ValueNull.INSTANCE || (l3 = value.getLong()) < 0L) {
                throw DbException.getInvalidValueException("result OFFSET", value);
            }
        } else {
            l3 = 0L;
        }
        long l4 = l2 = l == 0L ? -1L : l;
        if (this.fetchExpr != null) {
            long l5;
            Value value = this.fetchExpr.getValue(this.session);
            if (value == ValueNull.INSTANCE || (l5 = value.getLong()) < 0L) {
                throw DbException.getInvalidValueException("result FETCH", value);
            }
            long l6 = l2 = l2 < 0L ? l5 : Math.min(l5, l2);
        }
        if (bl = this.fetchPercent) {
            if (l2 > 100L) {
                throw DbException.getInvalidValueException("result FETCH PERCENT", l2);
            }
            if (l2 == 0L) {
                bl = false;
            }
        }
        return new OffsetFetch(l3, l2, bl);
    }

    LocalResult finishResult(LocalResult localResult, long l, long l2, boolean bl, ResultTarget resultTarget) {
        if (l != 0L) {
            localResult.setOffset(l);
        }
        if (l2 >= 0L) {
            localResult.setLimit(l2);
            localResult.setFetchPercent(bl);
            if (this.withTies) {
                localResult.setWithTies(this.sort);
            }
        }
        localResult.done();
        if (this.randomAccessResult && !this.distinct) {
            localResult = this.convertToDistinct(localResult);
        }
        if (resultTarget != null) {
            while (localResult.next()) {
                resultTarget.addRow(localResult.currentRow());
            }
            localResult.close();
            return null;
        }
        return localResult;
    }

    LocalResult convertToDistinct(ResultInterface resultInterface) {
        LocalResult localResult = new LocalResult(this.session, this.expressionArray, this.visibleColumnCount, this.resultColumnCount);
        localResult.setDistinct();
        resultInterface.reset();
        while (resultInterface.next()) {
            localResult.addRow(resultInterface.currentRow());
        }
        resultInterface.close();
        localResult.done();
        return localResult;
    }

    public Table toTable(String string, Column[] columnArray, ArrayList<Parameter> arrayList, boolean bl, Query query) {
        this.setParameterList(new ArrayList<Parameter>(arrayList));
        if (!this.checkInit) {
            this.init();
        }
        return new DerivedTable(bl ? this.getDatabase().getSystemSession() : this.session, string, columnArray, this, query);
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        this.isEverything(expressionVisitor);
    }

    public boolean isConstantQuery() {
        return !(this.hasOrder() || this.offsetExpr != null && !this.offsetExpr.isConstant() || this.fetchExpr != null && !this.fetchExpr.isConstant());
    }

    public Expression getIfSingleRow() {
        return null;
    }

    @Override
    public boolean isRetryable() {
        ForUpdate forUpdate = this.getForUpdate();
        return forUpdate == null || forUpdate.getType() == ForUpdate.Type.SKIP_LOCKED;
    }

    static final class OffsetFetch {
        final long offset;
        final long fetch;
        final boolean fetchPercent;

        OffsetFetch(long l, long l2, boolean bl) {
            this.offset = l;
            this.fetch = l2;
            this.fetchPercent = bl;
        }
    }
}

