/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.Random;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.Function0_1;
import org.h2.message.DbException;
import org.h2.util.MathUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueDouble;
import org.h2.value.ValueNull;
import org.h2.value.ValueUuid;
import org.h2.value.ValueVarbinary;

public final class RandFunction
extends Function0_1 {
    public static final int RAND = 0;
    public static final int SECURE_RAND = 1;
    public static final int RANDOM_UUID = 2;
    private static final String[] NAMES = new String[]{"RAND", "SECURE_RAND", "RANDOM_UUID"};
    private final int function;

    public RandFunction(Expression expression, int n) {
        super(expression);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        if (this.arg != null) {
            value = this.arg.getValue(sessionLocal);
            if (value == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
        } else {
            value = null;
        }
        switch (this.function) {
            case 0: {
                Random random = sessionLocal.getRandom();
                if (value != null) {
                    random.setSeed(value.getInt());
                }
                value = ValueDouble.get(random.nextDouble());
                break;
            }
            case 1: {
                value = ValueVarbinary.getNoCopy(MathUtils.secureRandomBytes(value.getInt()));
                break;
            }
            case 2: {
                value = ValueUuid.getNewRandom();
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
        if (this.arg != null) {
            this.arg = this.arg.optimize(sessionLocal);
        }
        switch (this.function) {
            case 0: {
                this.type = TypeInfo.TYPE_DOUBLE;
                break;
            }
            case 1: {
                Value value;
                this.type = this.arg.isConstant() && (value = this.arg.getValue(sessionLocal)) != ValueNull.INSTANCE ? TypeInfo.getTypeInfo(6, Math.max(value.getInt(), 1), 0, null) : TypeInfo.TYPE_VARBINARY;
                break;
            }
            case 2: {
                this.type = TypeInfo.TYPE_UUID;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return super.isEverything(expressionVisitor);
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

