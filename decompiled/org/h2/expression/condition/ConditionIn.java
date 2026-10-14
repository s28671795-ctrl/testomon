/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.Condition;
import org.h2.expression.condition.ConditionInConstantSet;
import org.h2.index.IndexCondition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.HasSQL;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class ConditionIn
extends Condition {
    private Expression left;
    private final boolean not;
    private final boolean whenOperand;
    private final ArrayList<Expression> valueList;

    public ConditionIn(Expression expression, boolean bl, boolean bl2, ArrayList<Expression> arrayList) {
        this.left = expression;
        this.not = bl;
        this.whenOperand = bl2;
        this.valueList = arrayList;
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
        if (value.containsNull()) {
            return ValueNull.INSTANCE;
        }
        boolean bl = false;
        for (Expression expression : this.valueList) {
            Value value2 = expression.getValue(sessionLocal);
            Value value3 = Comparison.compare(sessionLocal, value, value2, 0);
            if (value3 == ValueNull.INSTANCE) {
                bl = true;
                continue;
            }
            if (value3 != ValueBoolean.TRUE) continue;
            return ValueBoolean.get(!this.not);
        }
        if (bl) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(this.not);
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        for (Expression expression : this.valueList) {
            expression.mapColumns(columnResolver, n, n2);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl;
        this.left = this.left.optimize(sessionLocal);
        boolean bl2 = bl = !this.whenOperand && this.left.isConstant();
        if (bl && this.left.isNullConstant()) {
            return TypedValueExpression.UNKNOWN;
        }
        boolean bl3 = true;
        boolean bl4 = true;
        TypeInfo typeInfo = this.left.getType();
        int n = this.valueList.size();
        for (int i = 0; i < n; ++i) {
            Expression expression = this.valueList.get(i);
            expression = expression.optimize(sessionLocal);
            TypeInfo.checkComparable(typeInfo, expression.getType());
            if (expression.isConstant() && !expression.getValue(sessionLocal).containsNull()) {
                bl4 = false;
            }
            if (bl3 && !expression.isConstant()) {
                bl3 = false;
            }
            if (this.left instanceof ExpressionColumn && expression instanceof Parameter) {
                ((Parameter)expression).setColumn(((ExpressionColumn)this.left).getColumn());
            }
            this.valueList.set(i, expression);
        }
        return this.optimize2(sessionLocal, bl, bl3, bl4, this.valueList);
    }

    private Expression optimize2(SessionLocal sessionLocal, boolean bl, boolean bl2, boolean bl3, ArrayList<Expression> arrayList) {
        if (bl && bl2) {
            return ValueExpression.getBoolean(this.getValue(sessionLocal));
        }
        if (arrayList.size() == 1) {
            return new Comparison(this.not ? 1 : 0, this.left, arrayList.get(0), this.whenOperand).optimize(sessionLocal);
        }
        if (bl2 && !bl3) {
            int n = this.left.getType().getValueType();
            if (n == -1) {
                return this;
            }
            if (n == 36 && !(this.left instanceof ExpressionColumn)) {
                return this;
            }
            return new ConditionInConstantSet(sessionLocal, this.left, this.not, this.whenOperand, arrayList).optimize(sessionLocal);
        }
        return this;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new ConditionIn(this.left, !this.not, false, this.valueList);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        ExpressionList expressionList;
        if (this.not || this.whenOperand || !sessionLocal.getDatabase().getSettings().optimizeInList) {
            return;
        }
        if (this.left instanceof ExpressionColumn) {
            ExpressionColumn expressionColumn = (ExpressionColumn)this.left;
            if (tableFilter == expressionColumn.getTableFilter()) {
                ConditionIn.createIndexConditions(tableFilter, expressionColumn, this.valueList);
            }
        } else if (this.left instanceof ExpressionList && !(expressionList = (ExpressionList)this.left).isArray()) {
            this.createIndexConditions(tableFilter, expressionList);
        }
    }

    private void createIndexConditions(TableFilter tableFilter, ExpressionList expressionList) {
        int n = expressionList.getSubexpressionCount();
        for (int i = 0; i < n; ++i) {
            ExpressionColumn expressionColumn;
            Expression expression = expressionList.getSubexpression(i);
            if (!(expression instanceof ExpressionColumn) || tableFilter != (expressionColumn = (ExpressionColumn)expression).getTableFilter()) continue;
            ArrayList<Expression> arrayList = new ArrayList<Expression>(this.valueList.size());
            for (Expression expression2 : this.valueList) {
                HasSQL hasSQL;
                if (expression2 instanceof ExpressionList) {
                    hasSQL = (ExpressionList)expression2;
                    if (((ExpressionList)hasSQL).isArray() || ((ExpressionList)hasSQL).getSubexpressionCount() != n) {
                        return;
                    }
                    arrayList.add(((ExpressionList)hasSQL).getSubexpression(i));
                    continue;
                }
                if (expression2 instanceof ValueExpression) {
                    hasSQL = expression2.getValue(null);
                    if (((Value)hasSQL).getValueType() != 41) {
                        return;
                    }
                    Value[] valueArray = ((ValueRow)hasSQL).getList();
                    if (n != valueArray.length) {
                        return;
                    }
                    arrayList.add(ValueExpression.get(valueArray[i]));
                    continue;
                }
                return;
            }
            ConditionIn.createIndexConditions(tableFilter, expressionColumn, arrayList);
        }
    }

    private static void createIndexConditions(TableFilter tableFilter, ExpressionColumn expressionColumn, ArrayList<Expression> arrayList) {
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getNotFromResolverVisitor(tableFilter);
        TypeInfo typeInfo = expressionColumn.getType();
        for (Expression expression : arrayList) {
            if (expression.isEverything(expressionVisitor) && TypeInfo.haveSameOrdering(typeInfo, TypeInfo.getHigherType(typeInfo, expression.getType()))) continue;
            return;
        }
        tableFilter.addIndexCondition(IndexCondition.getInList(expressionColumn, arrayList));
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        for (Expression expression : this.valueList) {
            expression.setEvaluatable(tableFilter, bl);
        }
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
        if (this.not) {
            stringBuilder.append(" NOT");
        }
        return ConditionIn.writeExpressions(stringBuilder.append(" IN("), this.valueList, n).append(')');
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        for (Expression expression : this.valueList) {
            expression.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!this.left.isEverything(expressionVisitor)) {
            return false;
        }
        return this.areAllValues(expressionVisitor);
    }

    private boolean areAllValues(ExpressionVisitor expressionVisitor) {
        for (Expression expression : this.valueList) {
            if (expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getCost() {
        int n = this.left.getCost();
        for (Expression expression : this.valueList) {
            n += expression.getCost();
        }
        return n;
    }

    Expression getAdditional(Comparison comparison) {
        Expression expression;
        if (!this.not && !this.whenOperand && this.left.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR) && (expression = comparison.getIfEquals(this.left)) != null) {
            ArrayList<Expression> arrayList = new ArrayList<Expression>(this.valueList.size() + 1);
            arrayList.addAll(this.valueList);
            arrayList.add(expression);
            return new ConditionIn(this.left, false, false, arrayList);
        }
        return null;
    }

    @Override
    public int getSubexpressionCount() {
        return 1 + this.valueList.size();
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0) {
            return this.left;
        }
        if (n > 0 && n <= this.valueList.size()) {
            return this.valueList.get(n - 1);
        }
        throw new IndexOutOfBoundsException();
    }
}

