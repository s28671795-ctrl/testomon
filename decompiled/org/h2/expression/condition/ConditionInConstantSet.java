/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.ArrayList;
import java.util.TreeSet;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.Condition;
import org.h2.index.IndexCondition;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.HasSQL;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class ConditionInConstantSet
extends Condition {
    private Expression left;
    private final boolean not;
    private final boolean whenOperand;
    private final ArrayList<Expression> valueList;
    private final TreeSet<Value> valueSet;
    private boolean hasNull;
    private final TypeInfo type;

    ConditionInConstantSet(SessionLocal sessionLocal, Expression expression, boolean bl, boolean bl2, ArrayList<Expression> arrayList) {
        this.left = expression;
        this.not = bl;
        this.whenOperand = bl2;
        this.valueList = arrayList;
        this.valueSet = new TreeSet<Value>(sessionLocal.getDatabase().getCompareMode());
        TypeInfo typeInfo = expression.getType();
        for (Expression expression2 : arrayList) {
            typeInfo = TypeInfo.getHigherType(typeInfo, expression2.getType());
        }
        this.type = typeInfo;
        for (Expression expression2 : arrayList) {
            this.add(expression2.getValue(sessionLocal), sessionLocal);
        }
    }

    private void add(Value value, SessionLocal sessionLocal) {
        if ((value = value.convertTo(this.type, (CastDataProvider)sessionLocal)).containsNull()) {
            this.hasNull = true;
        } else {
            this.valueSet.add(value);
        }
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.getValue(this.left.getValue(sessionLocal), sessionLocal);
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        return this.getValue(value, sessionLocal).isTrue();
    }

    private Value getValue(Value value, SessionLocal sessionLocal) {
        if ((value = value.convertTo(this.type, (CastDataProvider)sessionLocal)).containsNull()) {
            return ValueNull.INSTANCE;
        }
        boolean bl = this.valueSet.contains(value);
        if (!bl && this.hasNull) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(this.not ^ bl);
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        return this;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new ConditionInConstantSet(sessionLocal, this.left, !this.not, false, this.valueList);
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
                ConditionInConstantSet.createIndexConditions(tableFilter, expressionColumn, this.valueList, this.type);
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
            Object object3 = expressionColumn.getType();
            for (HasSQL hasSQL : arrayList) {
                object3 = TypeInfo.getHigherType((TypeInfo)object3, ((Expression)hasSQL).getType());
            }
            ConditionInConstantSet.createIndexConditions(tableFilter, expressionColumn, arrayList, (TypeInfo)object3);
        }
    }

    private static void createIndexConditions(TableFilter tableFilter, ExpressionColumn expressionColumn, ArrayList<Expression> arrayList, TypeInfo typeInfo) {
        TypeInfo typeInfo2 = expressionColumn.getType();
        if (TypeInfo.haveSameOrdering(typeInfo2, TypeInfo.getHigherType(typeInfo2, typeInfo))) {
            tableFilter.addIndexCondition(IndexCondition.getInList(expressionColumn, arrayList));
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
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
        return ConditionInConstantSet.writeExpressions(stringBuilder.append(" IN("), this.valueList, n).append(')');
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost();
    }

    Expression getAdditional(SessionLocal sessionLocal, Comparison comparison) {
        Expression expression;
        if (!this.not && !this.whenOperand && this.left.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR) && (expression = comparison.getIfEquals(this.left)) != null && expression.isConstant()) {
            ArrayList<Expression> arrayList = new ArrayList<Expression>(this.valueList.size() + 1);
            arrayList.addAll(this.valueList);
            arrayList.add(expression);
            return new ConditionInConstantSet(sessionLocal, this.left, false, false, arrayList);
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

