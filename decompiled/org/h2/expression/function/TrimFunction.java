/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1_2;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueVarchar;

public final class TrimFunction
extends Function1_2 {
    public static final int LEADING = 1;
    public static final int TRAILING = 2;
    public static final int MULTI_CHARACTER = 4;
    private int flags;

    public TrimFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.flags = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        return ValueVarchar.get(StringUtils.trim(value.getString(), (this.flags & 1) != 0, (this.flags & 2) != 0, value2 != null ? value2.getString() : " "), sessionLocal);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        this.type = TypeInfo.getTypeInfo(2, this.left.getType().getPrecision(), 0, null);
        if (this.left.isConstant() && (this.right == null || this.right.isConstant())) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.getName()).append('(');
        if ((this.flags & 4) != 0) {
            this.left.getUnenclosedSQL(stringBuilder, n);
            if (this.right != null) {
                this.right.getUnenclosedSQL(stringBuilder.append(", "), n);
            }
        } else {
            boolean bl = false;
            switch (this.flags) {
                case 1: {
                    stringBuilder.append("LEADING ");
                    bl = true;
                    break;
                }
                case 2: {
                    stringBuilder.append("TRAILING ");
                    bl = true;
                }
            }
            if (this.right != null) {
                this.right.getUnenclosedSQL(stringBuilder, n);
                bl = true;
            }
            if (bl) {
                stringBuilder.append(" FROM ");
            }
            this.left.getUnenclosedSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public String getName() {
        switch (this.flags) {
            case 5: {
                return "LTRIM";
            }
            case 6: {
                return "RTRIM";
            }
            case 7: {
                return "BTRIM";
            }
        }
        return "TRIM";
    }
}

