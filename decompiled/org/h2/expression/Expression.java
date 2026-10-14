/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.List;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.HasSQL;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;
import org.h2.value.Value;

public abstract class Expression
implements HasSQL,
Typed {
    public static final int MAP_INITIAL = 0;
    public static final int MAP_IN_WINDOW = 1;
    public static final int MAP_IN_AGGREGATE = 2;
    public static final int AUTO_PARENTHESES = 0;
    public static final int WITH_PARENTHESES = 1;
    public static final int WITHOUT_PARENTHESES = 2;
    private boolean addedToFilter;

    public static StringBuilder writeExpressions(StringBuilder stringBuilder, List<? extends Expression> list, int n) {
        int n2 = list.size();
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            list.get(i).getUnenclosedSQL(stringBuilder, n);
        }
        return stringBuilder;
    }

    public static StringBuilder writeExpressions(StringBuilder stringBuilder, Expression[] expressionArray, int n) {
        int n2 = expressionArray.length;
        for (int i = 0; i < n2; ++i) {
            Expression expression;
            if (i > 0) {
                stringBuilder.append(", ");
            }
            if ((expression = expressionArray[i]) == null) {
                stringBuilder.append("DEFAULT");
                continue;
            }
            expression.getUnenclosedSQL(stringBuilder, n);
        }
        return stringBuilder;
    }

    public abstract Value getValue(SessionLocal var1);

    @Override
    public abstract TypeInfo getType();

    public abstract void mapColumns(ColumnResolver var1, int var2, int var3);

    public abstract Expression optimize(SessionLocal var1);

    public final Expression optimizeCondition(SessionLocal sessionLocal) {
        Expression expression = this.optimize(sessionLocal);
        if (expression.isConstant()) {
            return expression.getBooleanValue(sessionLocal) ? null : ValueExpression.FALSE;
        }
        return expression;
    }

    public abstract void setEvaluatable(TableFilter var1, boolean var2);

    @Override
    public final String getSQL(int n) {
        return this.getSQL(new StringBuilder(), n, 0).toString();
    }

    @Override
    public final StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return this.getSQL(stringBuilder, n, 0);
    }

    public final String getSQL(int n, int n2) {
        return this.getSQL(new StringBuilder(), n, n2).toString();
    }

    public final StringBuilder getSQL(StringBuilder stringBuilder, int n, int n2) {
        return n2 == 1 || n2 != 2 && this.needParentheses() ? this.getUnenclosedSQL(stringBuilder.append('('), n).append(')') : this.getUnenclosedSQL(stringBuilder, n);
    }

    public boolean needParentheses() {
        return false;
    }

    public final StringBuilder getEnclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getUnenclosedSQL(stringBuilder.append('('), n).append(')');
    }

    public abstract StringBuilder getUnenclosedSQL(StringBuilder var1, int var2);

    public abstract void updateAggregate(SessionLocal var1, int var2);

    public abstract boolean isEverything(ExpressionVisitor var1);

    public abstract int getCost();

    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        return null;
    }

    public boolean isConstant() {
        return false;
    }

    public boolean isNullConstant() {
        return false;
    }

    public boolean isValueSet() {
        return false;
    }

    public boolean isIdentity() {
        return false;
    }

    public boolean getBooleanValue(SessionLocal sessionLocal) {
        return this.getValue(sessionLocal).isTrue();
    }

    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
    }

    public String getColumnName(SessionLocal sessionLocal, int n) {
        return this.getAlias(sessionLocal, n);
    }

    public String getSchemaName() {
        return null;
    }

    public String getTableName() {
        return null;
    }

    public int getNullable() {
        return 2;
    }

    public String getTableAlias() {
        return null;
    }

    public String getAlias(SessionLocal sessionLocal, int n) {
        switch (sessionLocal.getMode().expressionNames) {
            default: {
                String string = this.getSQL(5, 2);
                if (string.length() <= 256) {
                    return string;
                }
            }
            case C_NUMBER: {
                return "C" + (n + 1);
            }
            case EMPTY: {
                return "";
            }
            case NUMBER: {
                return Integer.toString(n + 1);
            }
            case POSTGRESQL_STYLE: 
        }
        if (this instanceof NamedExpression) {
            return StringUtils.toLowerEnglish(((NamedExpression)((Object)this)).getName());
        }
        return "?column?";
    }

    public String getColumnNameForView(SessionLocal sessionLocal, int n) {
        switch (sessionLocal.getMode().viewExpressionNames) {
            default: {
                return this.getAlias(sessionLocal, n);
            }
            case EXCEPTION: {
                throw DbException.get(90156, this.getTraceSQL());
            }
            case MYSQL_STYLE: 
        }
        String string = this.getSQL(5, 2);
        if (string.length() > 64) {
            string = "Name_exp_" + (n + 1);
        }
        return string;
    }

    public Expression getNonAliasExpression() {
        return this;
    }

    public void addFilterConditions(TableFilter tableFilter) {
        if (!this.addedToFilter && this.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR)) {
            tableFilter.addFilterCondition(this, false);
            this.addedToFilter = true;
        }
    }

    public String toString() {
        return this.getTraceSQL();
    }

    public int getSubexpressionCount() {
        return 0;
    }

    public Expression getSubexpression(int n) {
        throw new IndexOutOfBoundsException();
    }

    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        return sessionLocal.compareWithNull(value, this.getValue(sessionLocal), true) == 0;
    }

    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        return this.getUnenclosedSQL(stringBuilder.append(' '), n);
    }

    public boolean isWhenConditionOperand() {
        return false;
    }
}

