/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.ParameterInterface;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Comparison;
import org.h2.message.DbException;
import org.h2.table.Column;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class Parameter
extends Operation0
implements ParameterInterface {
    private Value value;
    private Column column;
    private final int index;

    public Parameter(int n) {
        this.index = n;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append('?').append(this.index + 1);
    }

    @Override
    public void setValue(Value value, boolean bl) {
        this.value = value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public Value getParamValue() {
        if (this.value == null) {
            return ValueNull.INSTANCE;
        }
        return this.value;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.getParamValue();
    }

    @Override
    public TypeInfo getType() {
        if (this.value != null) {
            return this.value.getType();
        }
        if (this.column != null) {
            return this.column.getType();
        }
        return TypeInfo.TYPE_UNKNOWN;
    }

    @Override
    public void checkSet() {
        if (this.value == null) {
            throw DbException.get(90012, "#" + (this.index + 1));
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        if (sessionLocal.getDatabase().getMode().treatEmptyStringsAsNull && this.value instanceof ValueVarchar && this.value.getString().isEmpty()) {
            this.value = ValueNull.INSTANCE;
        }
        return this;
    }

    @Override
    public boolean isValueSet() {
        return this.value != null;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 0: {
                return this.value != null;
            }
        }
        return true;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        return new Comparison(0, this, ValueExpression.FALSE, false);
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public int getIndex() {
        return this.index;
    }
}

