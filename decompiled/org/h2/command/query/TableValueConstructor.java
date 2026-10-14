/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.h2.command.query.ForUpdate;
import org.h2.command.query.Query;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.table.TableValueConstructorTable;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class TableValueConstructor
extends Query {
    private final ArrayList<ArrayList<Expression>> rows;
    TableValueConstructorTable table;
    private TableValueColumnResolver columnResolver;
    private double cost;

    public TableValueConstructor(SessionLocal sessionLocal, ArrayList<ArrayList<Expression>> arrayList) {
        super(sessionLocal);
        this.rows = arrayList;
        this.visibleColumnCount = arrayList.get(0).size();
        if (this.visibleColumnCount > 16384) {
            throw DbException.get(54011, "16384");
        }
        for (ArrayList<Expression> arrayList2 : arrayList) {
            for (Expression expression : arrayList2) {
                if (expression.isConstant()) continue;
                return;
            }
        }
        this.createTable();
    }

    public static void getVisibleResult(SessionLocal sessionLocal, ResultTarget resultTarget, Column[] columnArray, ArrayList<ArrayList<Expression>> arrayList) {
        int n = columnArray.length;
        for (ArrayList<Expression> arrayList2 : arrayList) {
            Value[] valueArray = new Value[n];
            for (int i = 0; i < n; ++i) {
                valueArray[i] = arrayList2.get(i).getValue(sessionLocal).convertTo(columnArray[i].getType(), (CastDataProvider)sessionLocal);
            }
            resultTarget.addRow(valueArray);
        }
    }

    public static void getValuesSQL(StringBuilder stringBuilder, int n, ArrayList<ArrayList<Expression>> arrayList) {
        stringBuilder.append("VALUES ");
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            Expression.writeExpressions(stringBuilder.append('('), (List<? extends Expression>)arrayList.get(i), n).append(')');
        }
    }

    @Override
    public boolean isUnion() {
        return false;
    }

    @Override
    protected ResultInterface queryWithoutCache(long l, ResultTarget resultTarget) {
        Query.OffsetFetch offsetFetch = this.getOffsetFetch(l);
        long l2 = offsetFetch.offset;
        long l3 = offsetFetch.fetch;
        boolean bl = offsetFetch.fetchPercent;
        int n = this.visibleColumnCount;
        int n2 = this.resultColumnCount;
        LocalResult localResult = new LocalResult(this.session, this.expressionArray, n, n2);
        if (this.sort != null) {
            localResult.setSortOrder(this.sort);
        }
        if (this.distinct) {
            localResult.setDistinct();
        }
        Column[] columnArray = this.table.getColumns();
        if (n == n2) {
            TableValueConstructor.getVisibleResult(this.session, localResult, columnArray, this.rows);
        } else {
            for (ArrayList<Expression> arrayList : this.rows) {
                int n3;
                Value[] valueArray = new Value[n2];
                for (n3 = 0; n3 < n; ++n3) {
                    valueArray[n3] = arrayList.get(n3).getValue(this.session).convertTo(columnArray[n3].getType(), (CastDataProvider)this.session);
                }
                this.columnResolver.currentRow = valueArray;
                for (n3 = n; n3 < n2; ++n3) {
                    valueArray[n3] = this.expressionArray[n3].getValue(this.session);
                }
                localResult.addRow(valueArray);
            }
            this.columnResolver.currentRow = null;
        }
        return this.finishResult(localResult, l2, l3, bl, resultTarget);
    }

    @Override
    public void init() {
        if (this.checkInit) {
            throw DbException.getInternalError();
        }
        this.checkInit = true;
        if (this.withTies && !this.hasOrder()) {
            throw DbException.get(90122);
        }
    }

    @Override
    public void prepareExpressions() {
        int n;
        if (this.columnResolver == null) {
            this.createTable();
        }
        if (this.orderList != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Expression expression : this.expressions) {
                arrayList.add(expression.getSQL(0, 2));
            }
            if (this.initOrder(arrayList, false, null)) {
                this.prepareOrder(this.orderList, this.expressions.size());
            }
        }
        this.resultColumnCount = this.expressions.size();
        for (n = 0; n < this.resultColumnCount; ++n) {
            ((Expression)this.expressions.get(n)).mapColumns(this.columnResolver, 0, 0);
        }
        for (n = this.visibleColumnCount; n < this.resultColumnCount; ++n) {
            this.expressions.set(n, ((Expression)this.expressions.get(n)).optimize(this.session));
        }
        if (this.sort != null) {
            this.cleanupOrder();
        }
        this.expressionArray = this.expressions.toArray(new Expression[0]);
    }

    @Override
    public void preparePlan() {
        double d = 0.0;
        int n = this.visibleColumnCount;
        for (ArrayList<Expression> arrayList : this.rows) {
            for (int i = 0; i < n; ++i) {
                d += (double)arrayList.get(i).getCost();
            }
        }
        this.cost = d + (double)this.rows.size();
        this.isPrepared = true;
    }

    private void createTable() {
        Object object;
        int n;
        int n2 = this.rows.size();
        ArrayList<Expression> arrayList = this.rows.get(0);
        int n3 = arrayList.size();
        TypeInfo[] typeInfoArray = new TypeInfo[n3];
        for (n = 0; n < n3; ++n) {
            Expression expression = arrayList.get(n).optimize(this.session);
            arrayList.set(n, expression);
            object = expression.getType();
            if (((TypeInfo)object).getValueType() == -1) {
                object = TypeInfo.TYPE_VARCHAR;
            }
            typeInfoArray[n] = object;
        }
        for (n = 1; n < n2; ++n) {
            arrayList = this.rows.get(n);
            for (int i = 0; i < n3; ++i) {
                object = arrayList.get(i).optimize(this.session);
                arrayList.set(i, (Expression)object);
                typeInfoArray[i] = TypeInfo.getHigherType(typeInfoArray[i], ((Expression)object).getType());
            }
        }
        Column[] columnArray = new Column[n3];
        int n4 = 0;
        while (n4 < n3) {
            object = typeInfoArray[n4];
            columnArray[n4++] = new Column("C" + n4, (TypeInfo)object);
        }
        Database database = this.session.getDatabase();
        object = new ArrayList(n3);
        for (int i = 0; i < n3; ++i) {
            ((ArrayList)object).add(new ExpressionColumn(database, null, null, columnArray[i].getName()));
        }
        this.expressions = object;
        this.table = new TableValueConstructorTable(database.getMainSchema(), this.session, columnArray, this.rows);
        this.columnResolver = new TableValueColumnResolver();
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public HashSet<Table> getTables() {
        HashSet<Table> hashSet = new HashSet<Table>(1, 1.0f);
        hashSet.add(this.table);
        return hashSet;
    }

    @Override
    public void setForUpdate(ForUpdate forUpdate) {
        throw DbException.get(90140);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n) {
        int n2 = this.visibleColumnCount;
        for (ArrayList<Expression> arrayList : this.rows) {
            for (int i = 0; i < n2; ++i) {
                arrayList.get(i).mapColumns(columnResolver, n, 0);
            }
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        int n = this.visibleColumnCount;
        for (ArrayList<Expression> arrayList : this.rows) {
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).setEvaluatable(tableFilter, bl);
            }
        }
    }

    @Override
    public void addGlobalCondition(Parameter parameter, int n, int n2) {
    }

    @Override
    public boolean allowGlobalConditions() {
        return false;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        ExpressionVisitor expressionVisitor2 = expressionVisitor.incrementQueryLevel(1);
        for (Expression expression : this.expressionArray) {
            if (expression.isEverything(expressionVisitor2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        int n2 = this.visibleColumnCount;
        for (ArrayList<Expression> arrayList : this.rows) {
            for (int i = 0; i < n2; ++i) {
                arrayList.get(i).updateAggregate(sessionLocal, n);
            }
        }
    }

    @Override
    public void fireBeforeSelectTriggers() {
    }

    @Override
    public String getPlanSQL(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        TableValueConstructor.getValuesSQL(stringBuilder, n, this.rows);
        this.appendEndOfQueryToSQL(stringBuilder, n, this.expressionArray);
        return stringBuilder.toString();
    }

    @Override
    public Table toTable(String string, Column[] columnArray, ArrayList<Parameter> arrayList, boolean bl, Query query) {
        if (!this.hasOrder() && this.offsetExpr == null && this.fetchExpr == null && this.table != null) {
            return this.table;
        }
        return super.toTable(string, columnArray, arrayList, bl, query);
    }

    @Override
    public boolean isConstantQuery() {
        if (!super.isConstantQuery()) {
            return false;
        }
        for (ArrayList<Expression> arrayList : this.rows) {
            for (int i = 0; i < this.visibleColumnCount; ++i) {
                if (arrayList.get(i).isConstant()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public Expression getIfSingleRow() {
        if (this.offsetExpr != null || this.fetchExpr != null || this.rows.size() != 1) {
            return null;
        }
        ArrayList<Expression> arrayList = this.rows.get(0);
        if (this.visibleColumnCount == 1) {
            return arrayList.get(0);
        }
        Expression[] expressionArray = new Expression[this.visibleColumnCount];
        for (int i = 0; i < this.visibleColumnCount; ++i) {
            expressionArray[i] = arrayList.get(i);
        }
        return new ExpressionList(expressionArray, false);
    }

    private final class TableValueColumnResolver
    implements ColumnResolver {
        Value[] currentRow;

        TableValueColumnResolver() {
        }

        @Override
        public Column[] getColumns() {
            return TableValueConstructor.this.table.getColumns();
        }

        @Override
        public Column findColumn(String string) {
            return TableValueConstructor.this.table.findColumn(string);
        }

        @Override
        public Value getValue(Column column) {
            return this.currentRow[column.getColumnId()];
        }

        @Override
        public Expression optimize(ExpressionColumn expressionColumn, Column column) {
            return (Expression)TableValueConstructor.this.expressions.get(column.getColumnId());
        }
    }
}

