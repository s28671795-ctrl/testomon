/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation1;
import org.h2.expression.ValueExpression;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueJson;

public final class Format
extends Operation1 {
    private final FormatEnum format;

    public Format(Expression expression, FormatEnum formatEnum) {
        super(expression);
        this.format = formatEnum;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.getValue(this.arg.getValue(sessionLocal));
    }

    public Value getValue(Value value) {
        switch (value.getValueType()) {
            case 0: {
                return ValueJson.NULL;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                return ValueJson.fromJson(value.getString());
            }
        }
        return value.convertTo(TypeInfo.TYPE_JSON);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        if (this.arg.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        if (this.arg instanceof Format && this.format == ((Format)this.arg).format) {
            return this.arg;
        }
        this.type = TypeInfo.TYPE_JSON;
        return this;
    }

    @Override
    public boolean isIdentity() {
        return this.arg.isIdentity();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.arg.getSQL(stringBuilder, n, 0).append(" FORMAT ").append(this.format.name());
    }

    @Override
    public int getNullable() {
        return this.arg.getNullable();
    }

    @Override
    public String getTableName() {
        return this.arg.getTableName();
    }

    @Override
    public String getColumnName(SessionLocal sessionLocal, int n) {
        return this.arg.getColumnName(sessionLocal, n);
    }

    public static enum FormatEnum {
        JSON;

    }
}

