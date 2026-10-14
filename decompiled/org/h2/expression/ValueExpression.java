/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.TypedValueExpression;
import org.h2.index.IndexCondition;
import org.h2.table.TableFilter;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public class ValueExpression
extends Operation0 {
    public static final ValueExpression NULL = new ValueExpression(ValueNull.INSTANCE);
    public static final ValueExpression DEFAULT = new ValueExpression(ValueNull.INSTANCE);
    public static final ValueExpression TRUE = new ValueExpression(ValueBoolean.TRUE);
    public static final ValueExpression FALSE = new ValueExpression(ValueBoolean.FALSE);
    final Value value;

    ValueExpression(Value value) {
        this.value = value;
    }

    public static ValueExpression get(Value value) {
        if (value == ValueNull.INSTANCE) {
            return NULL;
        }
        if (value.getValueType() == 8) {
            return ValueExpression.getBoolean(value.getBoolean());
        }
        return new ValueExpression(value);
    }

    public static ValueExpression getBoolean(Value value) {
        if (value == ValueNull.INSTANCE) {
            return TypedValueExpression.UNKNOWN;
        }
        return ValueExpression.getBoolean(value.getBoolean());
    }

    public static ValueExpression getBoolean(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.value;
    }

    @Override
    public TypeInfo getType() {
        return this.value.getType();
    }

    @Override
    public void createIndexConditions(SessionLocal sessionLocal, TableFilter tableFilter) {
        if (this.value.getValueType() == 8 && !this.value.getBoolean()) {
            tableFilter.addIndexCondition(IndexCondition.get(9, null, this));
        }
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.value == ValueNull.INSTANCE) {
            return TypedValueExpression.UNKNOWN;
        }
        return ValueExpression.getBoolean(!this.value.getBoolean());
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public boolean isNullConstant() {
        return this == NULL;
    }

    @Override
    public boolean isValueSet() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        if (this == DEFAULT) {
            stringBuilder.append("DEFAULT");
        } else {
            this.value.getSQL(stringBuilder, n);
        }
        return stringBuilder;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return true;
    }

    @Override
    public int getCost() {
        return 0;
    }
}

