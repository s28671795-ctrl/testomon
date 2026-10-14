/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class CoalesceFunction
extends FunctionN {
    public static final int COALESCE = 0;
    public static final int GREATEST = 1;
    public static final int LEAST = 2;
    private static final String[] NAMES = new String[]{"COALESCE", "GREATEST", "LEAST"};
    private final int function;
    private boolean ignoreNulls;

    public CoalesceFunction(int n) {
        this(n, new Expression[4]);
    }

    public CoalesceFunction(int n, Expression ... expressionArray) {
        super(expressionArray);
        this.function = n;
    }

    public void setIgnoreNulls(boolean bl) {
        this.ignoreNulls = bl;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        block0 : switch (this.function) {
            case 0: {
                value = ValueNull.INSTANCE;
                int n = this.args.length;
                for (int i = 0; i < n; ++i) {
                    Value value2 = this.args[i].getValue(sessionLocal);
                    if (value2 == ValueNull.INSTANCE) continue;
                    value = value2.convertTo(this.type, (CastDataProvider)sessionLocal);
                    break block0;
                }
                break;
            }
            case 1: 
            case 2: {
                value = this.greatestOrLeast(sessionLocal);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private Value greatestOrLeast(SessionLocal sessionLocal) {
        Value value = ValueNull.INSTANCE;
        Value value2 = null;
        int n = this.args.length;
        for (int i = 0; i < n; ++i) {
            Value value3 = this.args[i].getValue(sessionLocal);
            if (value3 != ValueNull.INSTANCE) {
                int n2;
                value3 = value3.convertTo(this.type, (CastDataProvider)sessionLocal);
                if (value == ValueNull.INSTANCE) {
                    if (value2 == null) {
                        value = value3;
                        continue;
                    }
                    n2 = sessionLocal.compareWithNull(value2, value3, false);
                    if (n2 == Integer.MIN_VALUE) {
                        value2 = CoalesceFunction.getWithNull(value2, value3);
                        continue;
                    }
                    if (!this.test(n2)) continue;
                    value = value3;
                    value2 = null;
                    continue;
                }
                n2 = sessionLocal.compareWithNull(value, value3, false);
                if (n2 == Integer.MIN_VALUE) {
                    if (i + 1 == n) {
                        return ValueNull.INSTANCE;
                    }
                    value2 = CoalesceFunction.getWithNull(value, value3);
                    value = ValueNull.INSTANCE;
                    continue;
                }
                if (!this.test(n2)) continue;
                value = value3;
                continue;
            }
            if (this.ignoreNulls) continue;
            return ValueNull.INSTANCE;
        }
        return value;
    }

    private static Value getWithNull(Value value, Value value2) {
        Value value3 = value.getValueWithFirstNull(value2);
        return value3 != null ? value3 : value;
    }

    private boolean test(int n) {
        return this.function == 1 ? n < 0 : n > 0;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        this.type = TypeInfo.getHigherType(this.args);
        if (this.type.getValueType() <= 0) {
            this.type = TypeInfo.TYPE_VARCHAR;
        }
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        super.getUnenclosedSQL(stringBuilder, n);
        if (this.function == 1 || this.function == 2) {
            stringBuilder.append(this.ignoreNulls ? " IGNORE NULLS" : " RESPECT NULLS");
        }
        return stringBuilder;
    }
}

