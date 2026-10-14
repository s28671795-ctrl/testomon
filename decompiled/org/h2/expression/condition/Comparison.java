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
import org.h2.expression.aggregate.Aggregate;
import org.h2.expression.aggregate.AggregateType;
import org.h2.expression.condition.Condition;
import org.h2.expression.condition.ConditionIn;
import org.h2.expression.condition.NullPredicate;
import org.h2.index.IndexCondition;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;

public final class Comparison
extends Condition {
    public static final int EQUAL = 0;
    public static final int NOT_EQUAL = 1;
    public static final int SMALLER = 2;
    public static final int BIGGER = 3;
    public static final int SMALLER_EQUAL = 4;
    public static final int BIGGER_EQUAL = 5;
    public static final int EQUAL_NULL_SAFE = 6;
    public static final int NOT_EQUAL_NULL_SAFE = 7;
    public static final int SPATIAL_INTERSECTS = 8;
    static final String[] COMPARE_TYPES = new String[]{"=", "<>", "<", ">", "<=", ">=", "IS NOT DISTINCT FROM", "IS DISTINCT FROM", "&&"};
    public static final int FALSE = 9;
    public static final int IN_LIST = 10;
    public static final int IN_ARRAY = 11;
    public static final int IN_QUERY = 12;
    private int compareType;
    private Expression left;
    private Expression right;
    private final boolean whenOperand;

    public Comparison(int n, Expression expression, Expression expression2, boolean bl) {
        this.left = expression;
        this.right = expression2;
        this.compareType = n;
        this.whenOperand = bl;
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
        stringBuilder.append(' ').append(COMPARE_TYPES[this.compareType]).append(' ');
        return this.right.getSQL(stringBuilder, n, this.right instanceof Aggregate && ((Aggregate)this.right).getAggregateType() == AggregateType.ANY ? 1 : 0);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public Expression optimize(SessionLocal var1_1) {
        this.left = this.left.optimize(var1_1);
        this.right = this.right.optimize(var1_1);
        var2_2 = this.left.getType();
        var3_3 = this.right.getType();
        if (!var1_1.getMode().numericWithBooleanComparison) ** GOTO lbl-1000
        switch (this.compareType) {
            case 0: 
            case 1: 
            case 6: 
            case 7: {
                var4_5 = var2_2.getValueType();
                if (var4_5 == 8 ? DataType.isNumericType(var3_3.getValueType()) != false : DataType.isNumericType(var4_5) != false && var3_3.getValueType() == 8) break;
            }
            default: lbl-1000:
            // 2 sources

            {
                TypeInfo.checkComparable((TypeInfo)var2_2, var3_3);
            }
        }
        if (this.whenOperand) {
            return this;
        }
        if (this.right instanceof ExpressionColumn && (this.left.isConstant() || this.left instanceof Parameter)) {
            var2_2 = this.left;
            this.left = this.right;
            this.right = var2_2;
            this.compareType = Comparison.getReversedCompareType(this.compareType);
        }
        if (this.left instanceof ExpressionColumn) {
            if (this.right.isConstant()) {
                var2_2 = this.right.getValue(var1_1);
                if (var2_2 == ValueNull.INSTANCE && (this.compareType & -2) != 6) {
                    return TypedValueExpression.UNKNOWN;
                }
                var3_3 = this.left.getType();
                var4_6 = var2_2.getType();
                var5_7 = var4_6.getValueType();
                if (!(var5_7 == var3_3.getValueType() && var5_7 < 40 || var5_7 == (var6_8 = TypeInfo.getHigherType(var3_3, var4_6)).getValueType() && var5_7 < 40)) {
                    var7_9 = ((ExpressionColumn)this.left).getColumn();
                    this.right = ValueExpression.get(var2_2.convertTo(var6_8, var1_1, var7_9));
                }
            } else if (this.right instanceof Parameter) {
                ((Parameter)this.right).setColumn(((ExpressionColumn)this.left).getColumn());
            }
        }
        if (this.left.isConstant() && this.right.isConstant()) {
            return ValueExpression.getBoolean(this.getValue(var1_1));
        }
        if (this.left.isNullConstant() || this.right.isNullConstant()) {
            if ((this.compareType & -2) != 6) {
                return TypedValueExpression.UNKNOWN;
            }
            var2_2 = this.left.isNullConstant() != false ? this.right : this.left;
            var3_4 = var2_2.getType().getValueType();
            if (var3_4 != -1 && var3_4 != 41) {
                return new NullPredicate((Expression)var2_2, this.compareType == 7, false);
            }
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE && (this.compareType & 0xFFFFFFFE) != 6) {
            return ValueNull.INSTANCE;
        }
        return Comparison.compare(sessionLocal, value, this.right.getValue(sessionLocal), this.compareType);
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        if (value == ValueNull.INSTANCE && (this.compareType & 0xFFFFFFFE) != 6) {
            return false;
        }
        return Comparison.compare(sessionLocal, value, this.right.getValue(sessionLocal), this.compareType).isTrue();
    }

    static Value compare(SessionLocal sessionLocal, Value value, Value value2, int n) {
        Value value3;
        switch (n) {
            case 0: {
                int n2 = sessionLocal.compareWithNull(value, value2, true);
                if (n2 == 0) {
                    value3 = ValueBoolean.TRUE;
                    break;
                }
                if (n2 == Integer.MIN_VALUE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.FALSE;
                break;
            }
            case 6: {
                value3 = ValueBoolean.get(sessionLocal.areEqual(value, value2));
                break;
            }
            case 1: {
                int n3 = sessionLocal.compareWithNull(value, value2, true);
                if (n3 == 0) {
                    value3 = ValueBoolean.FALSE;
                    break;
                }
                if (n3 == Integer.MIN_VALUE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.TRUE;
                break;
            }
            case 7: {
                value3 = ValueBoolean.get(!sessionLocal.areEqual(value, value2));
                break;
            }
            case 5: {
                int n4 = sessionLocal.compareWithNull(value, value2, false);
                if (n4 >= 0) {
                    value3 = ValueBoolean.TRUE;
                    break;
                }
                if (n4 == Integer.MIN_VALUE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.FALSE;
                break;
            }
            case 3: {
                int n5 = sessionLocal.compareWithNull(value, value2, false);
                if (n5 > 0) {
                    value3 = ValueBoolean.TRUE;
                    break;
                }
                if (n5 == Integer.MIN_VALUE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.FALSE;
                break;
            }
            case 4: {
                int n6 = sessionLocal.compareWithNull(value, value2, false);
                if (n6 == Integer.MIN_VALUE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.get(n6 <= 0);
                break;
            }
            case 2: {
                int n7 = sessionLocal.compareWithNull(value, value2, false);
                if (n7 == Integer.MIN_VALUE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.get(n7 < 0);
                break;
            }
            case 8: {
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    value3 = ValueNull.INSTANCE;
                    break;
                }
                value3 = ValueBoolean.get(value.convertToGeometry(null).intersectsBoundingBox(value2.convertToGeometry(null)));
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + n);
            }
        }
        return value3;
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    private static int getReversedCompareType(int n) {
        switch (n) {
            case 0: 
            case 1: 
            case 6: 
            case 7: 
            case 8: {
                return n;
            }
            case 5: {
                return 4;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 5;
            }
            case 2: {
                return 3;
            }
        }
        throw DbException.getInternalError("type=" + n);
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.compareType == 8 || this.whenOperand) {
            return null;
        }
        return new Comparison(Comparison.getNotCompareType(this.compareType), this.left, this.right, false);
    }

    static int getNotCompareType(int n) {
        switch (n) {
            case 0: {
                return 1;
            }
            case 6: {
                return 7;
            }
            case 1: {
                return 0;
            }
            case 7: {
                return 6;
            }
            case 5: {
                return 2;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 3;
            }
            case 2: {
                return 5;
            }
        }
        throw DbException.getInternalError("type=" + n);
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (!this.whenOperand) {
            Comparison.createIndexConditions(tableFilter, this.left, this.right, this.compareType);
        }
    }

    static void createIndexConditions(TableFilter tableFilter, Expression expression, Expression expression2, int n) {
        if (n == 1 || n == 7) {
            return;
        }
        if (!tableFilter.getTable().isQueryComparable()) {
            return;
        }
        if (n != 8) {
            boolean bl = expression instanceof ExpressionList;
            boolean bl2 = expression2 instanceof ExpressionList;
            if (bl) {
                if (bl2) {
                    Comparison.createIndexConditions(tableFilter, (ExpressionList)expression, (ExpressionList)expression2, n);
                } else if (expression2 instanceof ValueExpression) {
                    Comparison.createIndexConditions(tableFilter, (ExpressionList)expression, (ValueExpression)expression2, n);
                }
            } else if (bl2 && expression instanceof ValueExpression) {
                Comparison.createIndexConditions(tableFilter, (ExpressionList)expression2, (ValueExpression)expression, Comparison.getReversedCompareType(n));
                return;
            }
        }
        ExpressionColumn expressionColumn = null;
        if (expression instanceof ExpressionColumn && tableFilter != (expressionColumn = (ExpressionColumn)expression).getTableFilter()) {
            expressionColumn = null;
        }
        ExpressionColumn expressionColumn2 = null;
        if (expression2 instanceof ExpressionColumn && tableFilter != (expressionColumn2 = (ExpressionColumn)expression2).getTableFilter()) {
            expressionColumn2 = null;
        }
        if (expressionColumn == null == (expressionColumn2 == null)) {
            return;
        }
        if (expressionColumn == null ? !expression.isEverything(ExpressionVisitor.getNotFromResolverVisitor(tableFilter)) : !expression2.isEverything(ExpressionVisitor.getNotFromResolverVisitor(tableFilter))) {
            return;
        }
        switch (n) {
            case 0: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: {
                if (expressionColumn != null) {
                    TypeInfo typeInfo = expressionColumn.getType();
                    if (!TypeInfo.haveSameOrdering(typeInfo, TypeInfo.getHigherType(typeInfo, expression2.getType()))) break;
                    tableFilter.addIndexCondition(IndexCondition.get(n, expressionColumn, expression2));
                    break;
                }
                TypeInfo typeInfo = expressionColumn2.getType();
                if (!TypeInfo.haveSameOrdering(typeInfo, TypeInfo.getHigherType(typeInfo, expression.getType()))) break;
                tableFilter.addIndexCondition(IndexCondition.get(Comparison.getReversedCompareType(n), expressionColumn2, expression));
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + n);
            }
        }
    }

    private static void createIndexConditions(TableFilter tableFilter, ExpressionList expressionList, ExpressionList expressionList2, int n) {
        int n2 = expressionList.getSubexpressionCount();
        if (n2 == 0 || n2 != expressionList2.getSubexpressionCount()) {
            return;
        }
        if (n != 0 && n != 6) {
            if (n2 > 1) {
                if (n == 3) {
                    n = 5;
                } else if (n == 2) {
                    n = 4;
                }
            }
            n2 = 1;
        }
        for (int i = 0; i < n2; ++i) {
            Comparison.createIndexConditions(tableFilter, expressionList.getSubexpression(i), expressionList2.getSubexpression(i), n);
        }
    }

    private static void createIndexConditions(TableFilter tableFilter, ExpressionList expressionList, ValueExpression valueExpression, int n) {
        Value value;
        int n2 = expressionList.getSubexpressionCount();
        if (n2 == 0) {
            return;
        }
        if (n2 == 1) {
            Comparison.createIndexConditions(tableFilter, expressionList.getSubexpression(0), (Expression)valueExpression, n);
        } else if (n2 > 1 && (value = valueExpression.getValue(null)).getValueType() == 41) {
            Value[] valueArray = ((ValueRow)value).getList();
            if (n2 != valueArray.length) {
                return;
            }
            if (n != 0 && n != 6) {
                if (n == 3) {
                    n = 5;
                } else if (n == 2) {
                    n = 4;
                }
                n2 = 1;
            }
            for (int i = 0; i < n2; ++i) {
                Comparison.createIndexConditions(tableFilter, expressionList.getSubexpression(i), (Expression)ValueExpression.get(valueArray[i]), n);
            }
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
        if (this.right != null) {
            this.right.setEvaluatable(tableFilter, bl);
        }
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
        if (this.right != null) {
            this.right.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
        this.right.mapColumns(columnResolver, n, n2);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor) && this.right.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        return this.left.getCost() + this.right.getCost() + 1;
    }

    Expression getIfEquals(Expression expression) {
        if (this.compareType == 0) {
            String string = expression.getSQL(0);
            if (this.left.getSQL(0).equals(string)) {
                return this.right;
            }
            if (this.right.getSQL(0).equals(string)) {
                return this.left;
            }
        }
        return null;
    }

    Expression getAdditionalAnd(SessionLocal sessionLocal, Comparison comparison) {
        if (this.compareType == 0 && comparison.compareType == 0 && !this.whenOperand) {
            boolean bl = this.left.isConstant();
            boolean bl2 = this.right.isConstant();
            boolean bl3 = comparison.left.isConstant();
            boolean bl4 = comparison.right.isConstant();
            String string = this.left.getSQL(0);
            String string2 = comparison.left.getSQL(0);
            String string3 = this.right.getSQL(0);
            String string4 = comparison.right.getSQL(0);
            if (!(bl2 && bl4 || !string.equals(string2))) {
                return new Comparison(0, this.right, comparison.right, false);
            }
            if (!(bl2 && bl3 || !string.equals(string4))) {
                return new Comparison(0, this.right, comparison.left, false);
            }
            if (!(bl && bl4 || !string3.equals(string2))) {
                return new Comparison(0, this.left, comparison.right, false);
            }
            if (!(bl && bl3 || !string3.equals(string4))) {
                return new Comparison(0, this.left, comparison.left, false);
            }
        }
        return null;
    }

    Expression optimizeOr(SessionLocal sessionLocal, Comparison comparison) {
        if (this.compareType == 0 && comparison.compareType == 0) {
            String string;
            Expression expression = comparison.left;
            Expression expression2 = comparison.right;
            String string2 = expression.getSQL(0);
            String string3 = expression2.getSQL(0);
            if (this.left.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR)) {
                string = this.left.getSQL(0);
                if (string.equals(string2)) {
                    return Comparison.getConditionIn(this.left, this.right, expression2);
                }
                if (string.equals(string3)) {
                    return Comparison.getConditionIn(this.left, this.right, expression);
                }
            }
            if (this.right.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR)) {
                string = this.right.getSQL(0);
                if (string.equals(string2)) {
                    return Comparison.getConditionIn(this.right, this.left, expression2);
                }
                if (string.equals(string3)) {
                    return Comparison.getConditionIn(this.right, this.left, expression);
                }
            }
        }
        return null;
    }

    private static ConditionIn getConditionIn(Expression expression, Expression expression2, Expression expression3) {
        ArrayList<Expression> arrayList = new ArrayList<Expression>(2);
        arrayList.add(expression2);
        arrayList.add(expression3);
        return new ConditionIn(expression, false, false, arrayList);
    }

    @Override
    public int getSubexpressionCount() {
        return 2;
    }

    @Override
    public Expression getSubexpression(int n) {
        switch (n) {
            case 0: {
                return this.left;
            }
            case 1: {
                return this.right;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}

