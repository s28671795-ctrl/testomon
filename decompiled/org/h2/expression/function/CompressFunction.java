/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.tools.CompressTool;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueVarbinary;

public final class CompressFunction
extends Function1_2 {
    public static final int COMPRESS = 0;
    public static final int EXPAND = 1;
    private static final String[] NAMES = new String[]{"COMPRESS", "EXPAND"};
    private final int function;

    public CompressFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        switch (this.function) {
            case 0: {
                value = ValueVarbinary.getNoCopy(CompressTool.getInstance().compress(value.getBytesNoCopy(), value2 != null ? value2.getString() : null));
                break;
            }
            case 1: {
                value = ValueVarbinary.getNoCopy(CompressTool.getInstance().expand(value.getBytesNoCopy()));
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        this.type = TypeInfo.TYPE_VARBINARY;
        if (this.left.isConstant() && (this.right == null || this.right.isConstant())) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

