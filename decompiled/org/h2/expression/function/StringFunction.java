/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class StringFunction
extends FunctionN {
    public static final int LOCATE = 0;
    public static final int INSERT = 1;
    public static final int REPLACE = 2;
    public static final int LPAD = 3;
    public static final int RPAD = 4;
    public static final int TRANSLATE = 5;
    private static final String[] NAMES = new String[]{"LOCATE", "INSERT", "REPLACE", "LPAD", "RPAD", "TRANSLATE"};
    private final int function;

    public StringFunction(Expression expression, Expression expression2, Expression expression3, int n) {
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

    public StringFunction(Expression expression, Expression expression2, Expression expression3, Expression expression4, int n) {
        super(new Expression[]{expression, expression2, expression3, expression4});
        this.function = n;
    }

    public StringFunction(Expression[] expressionArray, int n) {
        super(expressionArray);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.args[0].getValue(sessionLocal);
        Value value2 = this.args[1].getValue(sessionLocal);
        switch (this.function) {
            case 0: {
                Value value3;
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                Value value4 = value3 = this.args.length >= 3 ? this.args[2].getValue(sessionLocal) : null;
                if (value3 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                value = ValueInteger.get(StringFunction.locate(value.getString(), value2.getString(), value3 == null ? 1 : value3.getInt()));
                break;
            }
            case 1: {
                Value value5 = this.args[2].getValue(sessionLocal);
                Value value6 = this.args[3].getValue(sessionLocal);
                if (value2 == ValueNull.INSTANCE || value5 == ValueNull.INSTANCE) break;
                String string = StringFunction.insert(value.getString(), value2.getInt(), value5.getInt(), value6.getString());
                value = string != null ? ValueVarchar.get(string, sessionLocal) : ValueNull.INSTANCE;
                break;
            }
            case 2: {
                String string;
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                if (this.args.length >= 3) {
                    Value value7 = this.args[2].getValue(sessionLocal);
                    if (value7 == ValueNull.INSTANCE && sessionLocal.getMode().getEnum() != Mode.ModeEnum.Oracle) {
                        return ValueNull.INSTANCE;
                    }
                    string = value7.getString();
                    if (string == null) {
                        string = "";
                    }
                } else {
                    string = "";
                }
                value = ValueVarchar.get(StringUtils.replaceAll(value.getString(), value2.getString(), string), sessionLocal);
                break;
            }
            case 3: 
            case 4: {
                String string;
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                if (this.args.length >= 3) {
                    Value value8 = this.args[2].getValue(sessionLocal);
                    if (value8 == ValueNull.INSTANCE) {
                        return ValueNull.INSTANCE;
                    }
                    string = value8.getString();
                } else {
                    string = null;
                }
                value = ValueVarchar.get(StringUtils.pad(value.getString(), value2.getInt(), string, this.function == 4), sessionLocal);
                break;
            }
            case 5: {
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                Value value9 = this.args[2].getValue(sessionLocal);
                if (value9 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                String string = value2.getString();
                String string2 = value9.getString();
                if (sessionLocal.getMode().getEnum() == Mode.ModeEnum.DB2) {
                    String string3 = string;
                    string = string2;
                    string2 = string3;
                }
                value = ValueVarchar.get(StringFunction.translate(value.getString(), string, string2), sessionLocal);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private static int locate(String string, String string2, int n) {
        if (n < 0) {
            return string2.lastIndexOf(string, string2.length() + n) + 1;
        }
        return string2.indexOf(string, n == 0 ? 0 : n - 1) + 1;
    }

    private static String insert(String string, int n, int n2, String string2) {
        if (string == null) {
            return string2;
        }
        if (string2 == null) {
            return string;
        }
        int n3 = string.length();
        int n4 = string2.length();
        if (--n < 0 || n2 <= 0 || n4 == 0 || n > n3) {
            return string;
        }
        if (n + n2 > n3) {
            n2 = n3 - n;
        }
        return string.substring(0, n) + string2 + string.substring(n + n2);
    }

    private static String translate(String string, String string2, String string3) {
        if (StringUtils.isNullOrEmpty(string) || StringUtils.isNullOrEmpty(string2)) {
            return string;
        }
        StringBuilder stringBuilder = null;
        int n = string3 == null ? 0 : string3.length();
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            int n3 = string2.indexOf(c);
            if (n3 >= 0) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder(n2);
                    if (i > 0) {
                        stringBuilder.append(string, 0, i);
                    }
                }
                if (n3 < n) {
                    c = string3.charAt(n3);
                }
            }
            if (stringBuilder == null) continue;
            stringBuilder.append(c);
        }
        return stringBuilder == null ? string : stringBuilder.toString();
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        switch (this.function) {
            case 0: {
                this.type = TypeInfo.TYPE_INTEGER;
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                this.type = TypeInfo.TYPE_VARCHAR;
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

