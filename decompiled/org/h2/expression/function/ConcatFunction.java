/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class ConcatFunction
extends FunctionN {
    public static final int CONCAT = 0;
    public static final int CONCAT_WS = 1;
    private static final String[] NAMES = new String[]{"CONCAT", "CONCAT_WS"};
    private final int function;

    public ConcatFunction(int n) {
        this(n, new Expression[4]);
    }

    public ConcatFunction(int n, Expression ... expressionArray) {
        super(expressionArray);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        int n = 0;
        String string = null;
        if (this.function == 1) {
            n = 1;
            string = this.args[0].getValue(sessionLocal).getString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        int n2 = this.args.length;
        while (n < n2) {
            Value value = this.args[n].getValue(sessionLocal);
            if (value != ValueNull.INSTANCE) {
                if (string != null) {
                    if (bl) {
                        stringBuilder.append(string);
                    }
                    bl = true;
                }
                stringBuilder.append(value.getString());
            }
            ++n;
        }
        return ValueVarchar.get(stringBuilder.toString(), sessionLocal);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        int n = 0;
        long l = 0L;
        if (this.function == 1) {
            n = 1;
            l = this.getPrecision(0);
        }
        long l2 = 0L;
        int n2 = this.args.length;
        boolean bl2 = false;
        while (n < n2) {
            if (!this.args[n].isNullConstant()) {
                l2 = DataType.addPrecision(l2, this.getPrecision(n));
                if (l != 0L && bl2) {
                    l2 = DataType.addPrecision(l2, l);
                }
                bl2 = true;
            }
            ++n;
        }
        this.type = TypeInfo.getTypeInfo(2, l2, 0, null);
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    private long getPrecision(int n) {
        TypeInfo typeInfo = this.args[n].getType();
        int n2 = typeInfo.getValueType();
        if (n2 == 0) {
            return 0L;
        }
        if (DataType.isCharacterStringType(n2)) {
            return typeInfo.getPrecision();
        }
        return Long.MAX_VALUE;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

