/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.command.query.ForUpdate;
import org.h2.command.query.Query;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.message.DbException;
import org.h2.result.LazyResult;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class SelectUnion
extends Query {
    private final UnionType unionType;
    final Query left;
    final Query right;
    private ForUpdate forUpdate;

    public SelectUnion(SessionLocal sessionLocal, UnionType unionType, Query query, Query query2) {
        super(sessionLocal);
        this.unionType = unionType;
        this.left = query;
        this.right = query2;
    }

    @Override
    public boolean isUnion() {
        return true;
    }

    public UnionType getUnionType() {
        return this.unionType;
    }

    public Query getLeft() {
        return this.left;
    }

    public Query getRight() {
        return this.right;
    }

    private Value[] convert(Value[] valueArray, int n) {
        Value[] valueArray2 = n == valueArray.length ? valueArray : new Value[n];
        for (int i = 0; i < n; ++i) {
            Expression expression = (Expression)this.expressions.get(i);
            valueArray2[i] = valueArray[i].convertTo(expression.getType(), (CastDataProvider)this.session);
        }
        return valueArray2;
    }

    public LocalResult getEmptyResult() {
        int n = this.left.getColumnCount();
        return this.createLocalResult(n);
    }

    @Override
    protected ResultInterface queryWithoutCache(long l, ResultTarget resultTarget) {
        Query.OffsetFetch offsetFetch = this.getOffsetFetch(l);
        long l2 = offsetFetch.offset;
        long l3 = offsetFetch.fetch;
        boolean bl = offsetFetch.fetchPercent;
        Database database = this.session.getDatabase();
        if (database.getSettings().optimizeInsertFromSelect && this.unionType == UnionType.UNION_ALL && resultTarget != null && this.sort == null && !this.distinct && l3 < 0L && l2 == 0L) {
            this.left.query(0L, resultTarget);
            this.right.query(0L, resultTarget);
            return null;
        }
        int n = this.left.getColumnCount();
        if (this.session.isLazyQueryExecution() && this.unionType == UnionType.UNION_ALL && !this.distinct && this.sort == null && !this.randomAccessResult && this.forUpdate == null && l2 == 0L && !bl && !this.withTies && this.isReadOnly() && l3 != 0L) {
            LazyResultUnion lazyResultUnion = new LazyResultUnion(this.expressionArray, n);
            if (l3 > 0L) {
                lazyResultUnion.setLimit(l3);
            }
            return lazyResultUnion;
        }
        LocalResult localResult = this.createLocalResult(n);
        if (this.sort != null) {
            localResult.setSortOrder(this.sort);
        }
        if (this.distinct) {
            this.left.setDistinctIfPossible();
            this.right.setDistinctIfPossible();
            localResult.setDistinct();
        }
        switch (this.unionType) {
            case UNION: 
            case EXCEPT: {
                this.left.setDistinctIfPossible();
                this.right.setDistinctIfPossible();
                localResult.setDistinct();
                break;
            }
            case UNION_ALL: {
                break;
            }
            case INTERSECT: {
                this.left.setDistinctIfPossible();
                this.right.setDistinctIfPossible();
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.unionType));
            }
        }
        ResultInterface resultInterface = this.left.query(0L);
        ResultInterface resultInterface2 = this.right.query(0L);
        resultInterface.reset();
        resultInterface2.reset();
        switch (this.unionType) {
            case UNION: 
            case UNION_ALL: {
                while (resultInterface.next()) {
                    localResult.addRow(this.convert(resultInterface.currentRow(), n));
                }
                while (resultInterface2.next()) {
                    localResult.addRow(this.convert(resultInterface2.currentRow(), n));
                }
                break;
            }
            case EXCEPT: {
                while (resultInterface.next()) {
                    localResult.addRow(this.convert(resultInterface.currentRow(), n));
                }
                while (resultInterface2.next()) {
                    localResult.removeDistinct(this.convert(resultInterface2.currentRow(), n));
                }
                break;
            }
            case INTERSECT: {
                LocalResult localResult2 = this.createLocalResult(n);
                localResult2.setDistinct();
                while (resultInterface.next()) {
                    localResult2.addRow(this.convert(resultInterface.currentRow(), n));
                }
                while (resultInterface2.next()) {
                    Value[] valueArray = this.convert(resultInterface2.currentRow(), n);
                    if (!localResult2.containsDistinct(valueArray)) continue;
                    localResult.addRow(valueArray);
                }
                localResult2.close();
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.unionType));
            }
        }
        resultInterface.close();
        resultInterface2.close();
        return this.finishResult(localResult, l2, l3, bl, resultTarget);
    }

    private LocalResult createLocalResult(int n) {
        return new LocalResult(this.session, this.expressionArray, n, n);
    }

    @Override
    public void init() {
        if (this.checkInit) {
            throw DbException.getInternalError();
        }
        this.checkInit = true;
        this.left.init();
        this.right.init();
        int n = this.left.getColumnCount();
        if (n != this.right.getColumnCount()) {
            throw DbException.get(21002);
        }
        ArrayList<Expression> arrayList = this.left.getExpressions();
        this.expressions = new ArrayList(n);
        for (int i = 0; i < n; ++i) {
            Expression expression = arrayList.get(i);
            this.expressions.add(expression);
        }
        this.visibleColumnCount = n;
        if (this.withTies && !this.hasOrder()) {
            throw DbException.get(90122);
        }
    }

    @Override
    public void prepareExpressions() {
        this.left.prepareExpressions();
        this.right.prepareExpressions();
        int n = this.left.getColumnCount();
        this.expressions = new ArrayList(n);
        ArrayList<Expression> arrayList = this.left.getExpressions();
        ArrayList<Expression> arrayList2 = this.right.getExpressions();
        for (int i = 0; i < n; ++i) {
            Expression expression = arrayList.get(i);
            Expression expression2 = arrayList2.get(i);
            Column column = new Column(expression.getAlias(this.session, i), TypeInfo.getHigherType(expression.getType(), expression2.getType()));
            ExpressionColumn expressionColumn = new ExpressionColumn(this.session.getDatabase(), column);
            this.expressions.add(expressionColumn);
        }
        if (this.orderList != null && this.initOrder(null, true, null)) {
            this.prepareOrder(this.orderList, this.expressions.size());
            this.cleanupOrder();
        }
        this.resultColumnCount = this.expressions.size();
        this.expressionArray = this.expressions.toArray(new Expression[0]);
    }

    @Override
    public void preparePlan() {
        this.left.preparePlan();
        this.right.preparePlan();
        this.isPrepared = true;
    }

    @Override
    public double getCost() {
        return this.left.getCost() + this.right.getCost();
    }

    @Override
    public HashSet<Table> getTables() {
        HashSet<Table> hashSet = this.left.getTables();
        hashSet.addAll(this.right.getTables());
        return hashSet;
    }

    @Override
    public ForUpdate getForUpdate() {
        return this.forUpdate;
    }

    @Override
    public void setForUpdate(ForUpdate forUpdate) {
        this.left.setForUpdate(forUpdate);
        this.right.setForUpdate(forUpdate);
        this.forUpdate = forUpdate;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n) {
        this.left.mapColumns(columnResolver, n);
        this.right.mapColumns(columnResolver, n);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        this.right.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void addGlobalCondition(Parameter parameter, int n, int n2) {
        this.addParameter(parameter);
        switch (this.unionType) {
            case UNION: 
            case UNION_ALL: 
            case INTERSECT: {
                this.left.addGlobalCondition(parameter, n, n2);
                this.right.addGlobalCondition(parameter, n, n2);
                break;
            }
            case EXCEPT: {
                this.left.addGlobalCondition(parameter, n, n2);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.unionType));
            }
        }
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder().append('(').append(this.left.getPlanSQL(n)).append(')');
        switch (this.unionType) {
            case UNION_ALL: {
                stringBuilder.append("\nUNION ALL\n");
                break;
            }
            case UNION: {
                stringBuilder.append("\nUNION\n");
                break;
            }
            case INTERSECT: {
                stringBuilder.append("\nINTERSECT\n");
                break;
            }
            case EXCEPT: {
                stringBuilder.append("\nEXCEPT\n");
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.unionType));
            }
        }
        stringBuilder.append('(').append(this.right.getPlanSQL(n)).append(')');
        this.appendEndOfQueryToSQL(stringBuilder, n, this.expressions.toArray(new Expression[0]));
        if (this.forUpdate != null) {
            this.forUpdate.getSQL(stringBuilder, n);
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && this.right.isEverything(expressionVisitor);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        this.right.updateAggregate(sessionLocal, n);
    }

    @Override
    public void fireBeforeSelectTriggers() {
        this.left.fireBeforeSelectTriggers();
        this.right.fireBeforeSelectTriggers();
    }

    @Override
    public boolean allowGlobalConditions() {
        return this.left.allowGlobalConditions() && this.right.allowGlobalConditions();
    }

    @Override
    public boolean isConstantQuery() {
        return super.isConstantQuery() && this.left.isConstantQuery() && this.right.isConstantQuery();
    }

    private final class LazyResultUnion
    extends LazyResult {
        int columnCount;
        ResultInterface l;
        ResultInterface r;
        boolean leftDone;
        boolean rightDone;

        LazyResultUnion(Expression[] expressionArray, int n) {
            super(SelectUnion.this.getSession(), expressionArray);
            this.columnCount = n;
        }

        @Override
        public int getVisibleColumnCount() {
            return this.columnCount;
        }

        @Override
        protected Value[] fetchNextRow() {
            if (this.rightDone) {
                return null;
            }
            if (!this.leftDone) {
                if (this.l == null) {
                    this.l = SelectUnion.this.left.query(0L);
                    this.l.reset();
                }
                if (this.l.next()) {
                    return this.l.currentRow();
                }
                this.leftDone = true;
            }
            if (this.r == null) {
                this.r = SelectUnion.this.right.query(0L);
                this.r.reset();
            }
            if (this.r.next()) {
                return this.r.currentRow();
            }
            this.rightDone = true;
            return null;
        }

        @Override
        public void close() {
            super.close();
            if (this.l != null) {
                this.l.close();
            }
            if (this.r != null) {
                this.r.close();
            }
        }

        @Override
        public void reset() {
            super.reset();
            if (this.l != null) {
                this.l.reset();
            }
            if (this.r != null) {
                this.r.reset();
            }
            this.leftDone = false;
            this.rightDone = false;
        }
    }

    public static enum UnionType {
        UNION,
        UNION_ALL,
        EXCEPT,
        INTERSECT;

    }
}

