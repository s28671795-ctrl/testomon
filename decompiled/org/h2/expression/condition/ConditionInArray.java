/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.AbstractList;
import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.Condition;
import org.h2.index.IndexCondition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public class ConditionInArray
extends Condition {
    private Expression left;
    private final boolean whenOperand;
    private Expression right;
    private final boolean all;
    private final int compareType;

    public ConditionInArray(Expression expression, boolean bl, Expression expression2, boolean bl2, int n) {
        this.left = expression;
        this.whenOperand = bl;
        this.right = expression2;
        this.all = bl2;
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
        Value value2 = this.right.getValue(sessionLocal);
        if (value2 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        Value[] valueArray = value2.convertToAnyArray(sessionLocal).getList();
        if (valueArray.length == 0) {
            return ValueBoolean.get(this.all);
        }
        if ((this.compareType & 0xFFFFFFFE) == 6) {
            return this.getNullSafeValueSlow(sessionLocal, valueArray, value);
        }
        if (value.containsNull()) {
            return ValueNull.INSTANCE;
        }
        return this.getValueSlow(sessionLocal, valueArray, value);
    }

    private Value getValueSlow(SessionLocal sessionLocal, Value[] valueArray, Value value) {
        boolean bl = false;
        ValueBoolean valueBoolean = ValueBoolean.get(!this.all);
        for (Value value2 : valueArray) {
            Value value3 = Comparison.compare(sessionLocal, value, value2, this.compareType);
            if (value3 == ValueNull.INSTANCE) {
                bl = true;
                continue;
            }
            if (value3 != valueBoolean) continue;
            return ValueBoolean.get(!this.all);
        }
        if (bl) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(this.all);
    }

    private Value getNullSafeValueSlow(SessionLocal sessionLocal, Value[] valueArray, Value value) {
        boolean bl = this.all == (this.compareType == 7);
        for (Value value2 : valueArray) {
            if (sessionLocal.areEqual(value, value2) != bl) continue;
            return ValueBoolean.get(!this.all);
        }
        return ValueBoolean.get(this.all);
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
        return new ConditionInArray(this.left, false, this.right, !this.all, Comparison.getNotCompareType(this.compareType));
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        this.right.mapColumns(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.right = this.right.optimize(sessionLocal);
        this.left = this.left.optimize(sessionLocal);
        if (!this.whenOperand && this.left.isConstant() && this.right.isConstant()) {
            return ValueExpression.getBoolean(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (this.whenOperand || this.all || this.compareType != 0 || !(this.left instanceof ExpressionColumn)) {
            return;
        }
        ExpressionColumn expressionColumn = (ExpressionColumn)this.left;
        if (tableFilter != expressionColumn.getTableFilter()) {
            return;
        }
        if (this.right instanceof Parameter) {
            tableFilter.addIndexCondition(IndexCondition.getInList(expressionColumn, new ParameterList((Parameter)this.right)));
        } else if (this.right.isConstant()) {
            Value value = this.right.getValue(null);
            if (value instanceof ValueArray) {
                Value[] valueArray = ((ValueArray)value).getList();
                int n = valueArray.length;
                if (n == 0) {
                    tableFilter.addIndexCondition(IndexCondition.get(9, expressionColumn, ValueExpression.FALSE));
                } else {
                    TypeInfo typeInfo;
                    TypeInfo typeInfo2 = typeInfo = expressionColumn.getType();
                    for (int i = 0; i < n; ++i) {
                        typeInfo2 = TypeInfo.getHigherType(typeInfo2, valueArray[i].getType());
                    }
                    if (TypeInfo.haveSameOrdering(typeInfo, typeInfo2)) {
                        Expression[] expressionArray = new Expression[n];
                        for (int i = 0; i < n; ++i) {
                            expressionArray[i] = ValueExpression.get(valueArray[i]);
                        }
                        tableFilter.addIndexCondition(IndexCondition.getInList(expressionColumn, Arrays.asList(expressionArray)));
                    }
                }
            }
        } else {
            TypeInfo typeInfo;
            TypeInfo typeInfo3;
            ExpressionVisitor expressionVisitor = ExpressionVisitor.getNotFromResolverVisitor(tableFilter);
            if (this.right.isEverything(expressionVisitor) && (typeInfo3 = this.right.getType()).getValueType() == 40 && TypeInfo.haveSameOrdering(typeInfo = expressionColumn.getType(), TypeInfo.getHigherType(typeInfo, (TypeInfo)typeInfo3.getExtTypeInfo()))) {
                tableFilter.addIndexCondition(IndexCondition.getInArray(expressionColumn, this.right));
            }
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        this.right.setEvaluatable(tableFilter, bl);
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getWhenSQL(this.left.getSQL(stringBuilder, n, 0), n);
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        return this.right.getSQL(stringBuilder.append(' ').append(Comparison.COMPARE_TYPES[this.compareType]).append(this.all ? " ALL(" : " ANY("), n).append(')');
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        this.right.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && this.right.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost() + this.right.getCost() + 10;
    }

    private static final class ParameterList
    extends AbstractList<Expression> {
        private final Parameter parameter;

        ParameterList(Parameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public Expression get(int n) {
            Value value = this.parameter.getParamValue();
            if (value instanceof ValueArray) {
                return ValueExpression.get(((ValueArray)value).getList()[n]);
            }
            if (n != 0) {
                throw new IndexOutOfBoundsException();
            }
            return ValueExpression.get(value);
        }

        @Override
        public int size() {
            if (!this.parameter.isValueSet()) {
                return 0;
            }
            Value value = this.parameter.getParamValue();
            if (value instanceof ValueArray) {
                return ((ValueArray)value).getList().length;
            }
            return 1;
        }
    }
}

