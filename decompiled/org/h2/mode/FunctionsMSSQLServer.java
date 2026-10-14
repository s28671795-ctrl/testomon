/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.HashMap;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.CoalesceFunction;
import org.h2.expression.function.CurrentDateTimeValueFunction;
import org.h2.expression.function.RandFunction;
import org.h2.expression.function.StringFunction;
import org.h2.message.DbException;
import org.h2.mode.FunctionInfo;
import org.h2.mode.ModeFunction;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

public final class FunctionsMSSQLServer
extends ModeFunction {
    private static final HashMap<String, FunctionInfo> FUNCTIONS = new HashMap();
    private static final int CHARINDEX = 4001;
    private static final int GETDATE = 4002;
    private static final int ISNULL = 4003;
    private static final int LEN = 4004;
    private static final int NEWID = 4005;
    private static final int NEWSEQUENTIALID = 4006;
    private static final int SCOPE_IDENTITY = 4007;
    private static final TypeInfo SCOPE_IDENTITY_TYPE = TypeInfo.getTypeInfo(13, 38L, 0, null);

    public static FunctionsMSSQLServer getFunction(String string) {
        FunctionInfo functionInfo = FUNCTIONS.get(string);
        if (functionInfo != null) {
            return new FunctionsMSSQLServer(functionInfo);
        }
        return null;
    }

    private FunctionsMSSQLServer(FunctionInfo functionInfo) {
        super(functionInfo);
    }

    @Override
    protected void checkParameterCount(int n) {
        int n2;
        int n3;
        switch (this.info.type) {
            case 4001: {
                n3 = 2;
                n2 = 3;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        if (n < n3 || n > n2) {
            throw DbException.get(7001, this.info.name, n3 + ".." + n2);
        }
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value[] valueArray = this.getArgumentsValues(sessionLocal, this.args);
        if (valueArray == null) {
            return ValueNull.INSTANCE;
        }
        Value value = FunctionsMSSQLServer.getNullOrValue(sessionLocal, this.args, valueArray, 0);
        switch (this.info.type) {
            case 4004: {
                long l;
                if (value.getValueType() == 1) {
                    int n;
                    String string = value.getString();
                    for (n = string.length(); n > 0 && string.charAt(n - 1) == ' '; --n) {
                    }
                    l = n;
                } else {
                    l = value.charLength();
                }
                return ValueBigint.get(l);
            }
            case 4007: {
                return sessionLocal.getLastIdentity().convertTo(this.type);
            }
        }
        throw DbException.getInternalError("type=" + this.info.type);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        switch (this.info.type) {
            case 4001: {
                return new StringFunction(this.args, 0).optimize(sessionLocal);
            }
            case 4002: {
                return new CurrentDateTimeValueFunction(4, 3).optimize(sessionLocal);
            }
            case 4003: {
                return new CoalesceFunction(0, this.args).optimize(sessionLocal);
            }
            case 4005: 
            case 4006: {
                return new RandFunction(null, 2).optimize(sessionLocal);
            }
            case 4007: {
                this.type = SCOPE_IDENTITY_TYPE;
                break;
            }
            default: {
                this.type = TypeInfo.getTypeInfo(this.info.returnDataType);
                if (!this.optimizeArguments(sessionLocal)) break;
                return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
            }
        }
        return this;
    }

    static {
        FUNCTIONS.put("CHARINDEX", new FunctionInfo("CHARINDEX", 4001, -1, 11, true, true));
        FUNCTIONS.put("GETDATE", new FunctionInfo("GETDATE", 4002, 0, 20, false, true));
        FUNCTIONS.put("LEN", new FunctionInfo("LEN", 4004, 1, 11, true, true));
        FUNCTIONS.put("NEWID", new FunctionInfo("NEWID", 4005, 0, 39, true, false));
        FUNCTIONS.put("NEWSEQUENTIALID", new FunctionInfo("NEWSEQUENTIALID", 4006, 0, 39, true, false));
        FUNCTIONS.put("ISNULL", new FunctionInfo("ISNULL", 4003, 2, 0, false, true));
        FUNCTIONS.put("SCOPE_IDENTITY", new FunctionInfo("SCOPE_IDENTITY", 4007, 0, 13, true, false));
    }
}

