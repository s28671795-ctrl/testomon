/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.PredicateWithSubquery;
import org.h2.index.IndexCondition;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class ConditionInQuery
extends PredicateWithSubquery {
    private Expression left;
    private final boolean not;
    private final boolean whenOperand;
    private final boolean all;
    private final int compareType;

    public ConditionInQuery(Expression expression, boolean bl, boolean bl2, Query query, boolean bl3, int n) {
        super(query);
        this.left = expression;
        this.not = bl;
        this.whenOperand = bl2;
        query.setRandomAccessResult(true);
        query.setNeverLazy(true);
        query.setDistinctIfPossible();
        this.all = bl3;
        this.compareType = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.getValue(sessionLocal, this.left.getValue(sessionLocal));
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        return this.getValue(sessionLocal, value).isTrue();
    }

    private Value getValue(SessionLocal sessionLocal, Value value) {
        this.query.setSession(sessionLocal);
        LocalResult localResult = (LocalResult)this.query.query(0L);
        if (!localResult.hasNext()) {
            return ValueBoolean.get(this.not ^ this.all);
        }
        if ((this.compareType & 0xFFFFFFFE) == 6) {
            return this.getNullSafeValueSlow(sessionLocal, localResult, value);
        }
        if (value.containsNull()) {
            return ValueNull.INSTANCE;
        }
        if (this.all || this.compareType != 0 || !sessionLocal.getDatabase().getSettings().optimizeInSelect) {
            return this.getValueSlow(sessionLocal, localResult, value);
        }
        int n = this.query.getColumnCount();
        if (n != 1) {
            Value[] valueArray = value.convertToAnyRow().getList();
            if (n == valueArray.length && localResult.containsDistinct(valueArray)) {
                return ValueBoolean.get(!this.not);
            }
        } else {
            TypeInfo typeInfo = localResult.getColumnType(0);
            if (typeInfo.getValueType() == 0) {
                return ValueNull.INSTANCE;
            }
            if (value.getValueType() == 41) {
                value = ((ValueRow)value).getList()[0];
            }
            if (localResult.containsDistinct(new Value[]{value})) {
                return ValueBoolean.get(!this.not);
            }
        }
        if (localResult.containsNull()) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(this.not);
    }

    private Value getValueSlow(SessionLocal sessionLocal, ResultInterface resultInterface, Value value) {
        boolean bl = value.getValueType() != 41 && this.query.getColumnCount() == 1;
        boolean bl2 = false;
        ValueBoolean valueBoolean = ValueBoolean.get(!this.all);
        while (resultInterface.next()) {
            Value[] valueArray = resultInterface.currentRow();
            Value value2 = Comparison.compare(sessionLocal, value, bl ? valueArray[0] : ValueRow.get(valueArray), this.compareType);
            if (value2 == ValueNull.INSTANCE) {
                bl2 = true;
                continue;
            }
            if (value2 != valueBoolean) continue;
            return ValueBoolean.get(this.not == this.all);
        }
        if (bl2) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(this.not ^ this.all);
    }

    private Value getNullSafeValueSlow(SessionLocal sessionLocal, ResultInterface resultInterface, Value value) {
        boolean bl;
        boolean bl2 = value.getValueType() != 41 && this.query.getColumnCount() == 1;
        boolean bl3 = bl = this.all == (this.compareType == 7);
        while (resultInterface.next()) {
            Value[] valueArray = resultInterface.currentRow();
            if (sessionLocal.areEqual(value, bl2 ? valueArray[0] : ValueRow.get(valueArray)) != bl) continue;
            return ValueBoolean.get(this.not == this.all);
        }
        return ValueBoolean.get(this.not ^ this.all);
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new ConditionInQuery(this.left, !this.not, false, this.query, this.all, this.compareType);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        super.mapColumns(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        this.left = this.left.optimize(sessionLocal);
        TypeInfo.checkComparable(this.left.getType(), this.query.getRowDataType());
        return this;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        super.setEvaluatable(tableFilter, bl);
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        boolean bl;
        boolean bl2 = bl = this.not && (this.all || this.compareType != 0);
        if (bl) {
            stringBuilder.append("NOT (");
        }
        this.left.getSQL(stringBuilder, n, 0);
        this.getWhenSQL(stringBuilder, n);
        if (bl) {
            stringBuilder.append(')');
        }
        return stringBuilder;
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        if (this.all) {
            stringBuilder.append(Comparison.COMPARE_TYPES[this.compareType]).append(" ALL");
        } else if (this.compareType == 0) {
            if (this.not) {
                stringBuilder.append(" NOT");
            }
            stringBuilder.append(" IN");
        } else {
            stringBuilder.append(' ').append(Comparison.COMPARE_TYPES[this.compareType]).append(" ANY");
        }
        return super.getUnenclosedSQL(stringBuilder, n);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        super.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && super.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost() + super.getCost();
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        TypeInfo typeInfo;
        if (this.not || this.whenOperand || this.compareType != 0 || !sessionLocal.getDatabase().getSettings().optimizeInList) {
            return;
        }
        if (this.query.getColumnCount() != 1) {
            return;
        }
        if (!(this.left instanceof ExpressionColumn)) {
            return;
        }
        TypeInfo typeInfo2 = this.left.getType();
        if (!TypeInfo.haveSameOrdering(typeInfo2, TypeInfo.getHigherType(typeInfo2, typeInfo = this.query.getExpressions().get(0).getType()))) {
            return;
        }
        int n = typeInfo2.getValueType();
        if (!DataType.hasTotalOrdering(n) && n != typeInfo.getValueType()) {
            return;
        }
        ExpressionColumn expressionColumn = (ExpressionColumn)this.left;
        if (tableFilter != expressionColumn.getTableFilter()) {
            return;
        }
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getNotFromResolverVisitor(tableFilter);
        if (!this.query.isEverything(expressionVisitor)) {
            return;
        }
        tableFilter.addIndexCondition(IndexCondition.getInQuery(expressionColumn, this.query));
    }
}

