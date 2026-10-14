/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.Arrays;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueCollectionBase;
import org.h2.value.ValueNull;

public final class ArrayFunction
extends FunctionN {
    public static final int TRIM_ARRAY = 0;
    public static final int ARRAY_CONTAINS = 1;
    public static final int ARRAY_SLICE = 2;
    private static final String[] NAMES = new String[]{"TRIM_ARRAY", "ARRAY_CONTAINS", "ARRAY_SLICE"};
    private final int function;

    public ArrayFunction(Expression expression, Expression expression2, Expression expression3, int n) {
        Expression[] expressionArray;
        if (expression3 == null) {
            Expression[] expressionArray2 = new Expression[2];
            expressionArray2[0] = expression;
            expressionArray = expressionArray2;
            expressionArray2[1] = expression2;
        } else {
            Expression[] expressionArray3 = new Expression[3];
            expressionArray3[0] = expression;
            expressionArray3[1] = expression2;
            expressionArray = expressionArray3;
            expressionArray3[2] = expression3;
        }
        super(expressionArray);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.args[0].getValue(sessionLocal);
        Value value2 = this.args[1].getValue(sessionLocal);
        block0 : switch (this.function) {
            case 0: {
                if (value2 == ValueNull.INSTANCE) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                int n = value2.getInt();
                if (n < 0) {
                    throw DbException.get(22034, Integer.toString(n), "0..CARDINALITY(array)");
                }
                if (value == ValueNull.INSTANCE) break;
                ValueArray valueArray = value.convertToAnyArray(sessionLocal);
                Value[] valueArray2 = valueArray.getList();
                int n2 = valueArray2.length;
                if (n > n2) {
                    throw DbException.get(22034, Integer.toString(n), "0.." + n2);
                }
                if (n == 0) {
                    value = valueArray;
                    break;
                }
                value = ValueArray.get(valueArray.getComponentType(), Arrays.copyOf(valueArray2, n2 - n), sessionLocal);
                break;
            }
            case 1: {
                int n = value.getValueType();
                if (n == 40 || n == 41) {
                    Value[] valueArray = ((ValueCollectionBase)value).getList();
                    value = ValueBoolean.FALSE;
                    for (Value value3 : valueArray) {
                        if (!sessionLocal.areEqual(value3, value2)) continue;
                        value = ValueBoolean.TRUE;
                        break block0;
                    }
                    break;
                }
                value = ValueNull.INSTANCE;
                break;
            }
            case 2: {
                boolean bl;
                Value value4;
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE || (value4 = this.args[2].getValue(sessionLocal)) == ValueNull.INSTANCE) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                ValueArray valueArray = value.convertToAnyArray(sessionLocal);
                int n = value2.getInt() - 1;
                int n3 = value4.getInt();
                boolean bl2 = bl = sessionLocal.getMode().getEnum() == Mode.ModeEnum.PostgreSQL;
                if (n > n3) {
                    value = bl ? ValueArray.get(valueArray.getComponentType(), Value.EMPTY_VALUES, sessionLocal) : ValueNull.INSTANCE;
                    break;
                }
                if (n < 0) {
                    if (bl) {
                        n = 0;
                    } else {
                        value = ValueNull.INSTANCE;
                        break;
                    }
                }
                if (n3 > valueArray.getList().length) {
                    if (bl) {
                        n3 = valueArray.getList().length;
                    } else {
                        value = ValueNull.INSTANCE;
                        break;
                    }
                }
                value = ValueArray.get(valueArray.getComponentType(), Arrays.copyOfRange(valueArray.getList(), n, n3), sessionLocal);
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
        boolean bl = this.optimizeArguments(sessionLocal, true);
        switch (this.function) {
            case 0: 
            case 2: {
                Expression expression = this.args[0];
                this.type = expression.getType();
                int n = this.type.getValueType();
                if (n == 40 || n == 0) break;
                throw Store.getInvalidExpressionTypeException(this.getName() + " array argument", expression);
            }
            case 1: {
                this.type = TypeInfo.TYPE_BOOLEAN;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
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
}

