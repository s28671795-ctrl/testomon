/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.table.Column;
import org.h2.util.DateTimeTemplate;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class CastSpecification
extends Function1_2 {
    private Domain domain;

    public CastSpecification(Expression expression, Column column, Expression expression2) {
        super(expression, expression2);
        this.type = column.getType();
        this.domain = column.getDomain();
    }

    public CastSpecification(Expression expression, Column column) {
        super(expression, null);
        this.type = column.getType();
        this.domain = column.getDomain();
    }

    public CastSpecification(Expression expression, TypeInfo typeInfo) {
        super(expression, null);
        this.type = typeInfo;
    }

    @Override
    protected Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        if (value2 != null) {
            value = this.getValueWithTemplate(value, value2, sessionLocal);
        }
        value = value.castTo(this.type, sessionLocal);
        if (this.domain != null) {
            this.domain.checkConstraints(sessionLocal, value);
        }
        return value;
    }

    private Value getValueWithTemplate(Value value, Value value2, SessionLocal sessionLocal) {
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        int n = value.getValueType();
        if (DataType.isDateTimeType(n)) {
            if (DataType.isCharacterStringType(this.type.getValueType())) {
                return ValueVarchar.get(DateTimeTemplate.of(value2.getString()).format(value), sessionLocal);
            }
        } else if (DataType.isCharacterStringType(n) && DataType.isDateTimeType(this.type.getValueType())) {
            return DateTimeTemplate.of(value2.getString()).parse(value.getString(), this.type, sessionLocal);
        }
        throw DbException.getUnsupportedException(this.type.getSQL(value.getType().getSQL(new StringBuilder("CAST with template from "), 3).append(" to "), 0).toString());
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        Value value;
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        if (this.left.isConstant() && (this.right == null || this.right.isConstant()) && ((value = this.getValue(sessionLocal)) == ValueNull.INSTANCE || CastSpecification.canOptimizeCast(this.left.getType().getValueType(), this.type.getValueType()))) {
            return TypedValueExpression.get(value, this.type);
        }
        return this;
    }

    @Override
    public boolean isConstant() {
        return this.left instanceof ValueExpression && (this.right == null || this.right.isConstant()) && CastSpecification.canOptimizeCast(this.left.getType().getValueType(), this.type.getValueType());
    }

    private static boolean canOptimizeCast(int n, int n2) {
        switch (n) {
            case 18: {
                switch (n2) {
                    case 19: 
                    case 20: 
                    case 21: {
                        return false;
                    }
                }
                break;
            }
            case 19: {
                switch (n2) {
                    case 18: 
                    case 20: 
                    case 21: {
                        return false;
                    }
                }
                break;
            }
            case 17: {
                if (n2 != 21) break;
                return false;
            }
            case 20: {
                switch (n2) {
                    case 19: 
                    case 21: {
                        return false;
                    }
                }
                break;
            }
            case 21: {
                switch (n2) {
                    case 17: 
                    case 18: 
                    case 20: {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("CAST(");
        this.left.getUnenclosedSQL(stringBuilder, this.left instanceof ValueExpression ? n | 4 : n).append(" AS ");
        (this.domain != null ? this.domain : this.type).getSQL(stringBuilder, n);
        if (this.right != null) {
            this.right.getSQL(stringBuilder.append(" FORMAT "), n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public String getName() {
        return "CAST";
    }
}

