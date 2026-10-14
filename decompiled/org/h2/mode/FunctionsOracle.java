/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.HashMap;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.expression.function.DateTimeFunction;
import org.h2.message.DbException;
import org.h2.mode.FunctionInfo;
import org.h2.mode.ModeFunction;
import org.h2.mode.ToDateParser;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueUuid;

public final class FunctionsOracle
extends ModeFunction {
    private static final int ADD_MONTHS = 2001;
    private static final int SYS_GUID = 2002;
    private static final int TO_DATE = 2003;
    private static final int TO_TIMESTAMP = 2004;
    private static final int TO_TIMESTAMP_TZ = 2005;
    private static final HashMap<String, FunctionInfo> FUNCTIONS = new HashMap();

    public static FunctionsOracle getFunction(String string) {
        FunctionInfo functionInfo = FUNCTIONS.get(string);
        return functionInfo != null ? new FunctionsOracle(functionInfo) : null;
    }

    private FunctionsOracle(FunctionInfo functionInfo) {
        super(functionInfo);
    }

    @Override
    protected void checkParameterCount(int n) {
        int n2 = 0;
        int n3 = Integer.MAX_VALUE;
        switch (this.info.type) {
            case 2004: 
            case 2005: {
                n2 = 1;
                n3 = 2;
                break;
            }
            case 2003: {
                n2 = 1;
                n3 = 3;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        if (n < n2 || n > n3) {
            throw DbException.get(7001, this.info.name, n2 + ".." + n3);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal);
        switch (this.info.type) {
            case 2002: {
                this.type = TypeInfo.getTypeInfo(6, 16L, 0, null);
                break;
            }
            default: {
                this.type = TypeInfo.getTypeInfo(this.info.returnDataType);
            }
        }
        if (bl) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        Value[] valueArray = this.getArgumentsValues(sessionLocal, this.args);
        if (valueArray == null) {
            return ValueNull.INSTANCE;
        }
        Value value2 = FunctionsOracle.getNullOrValue(sessionLocal, this.args, valueArray, 0);
        Value value3 = FunctionsOracle.getNullOrValue(sessionLocal, this.args, valueArray, 1);
        switch (this.info.type) {
            case 2001: {
                value = DateTimeFunction.dateadd(sessionLocal, 1, value3.getInt(), value2);
                break;
            }
            case 2002: {
                value = ValueUuid.getNewRandom().convertTo(TypeInfo.TYPE_VARBINARY);
                break;
            }
            case 2003: {
                value = ToDateParser.toDate(sessionLocal, value2.getString(), value3 == null ? null : value3.getString());
                break;
            }
            case 2004: {
                value = ToDateParser.toTimestamp(sessionLocal, value2.getString(), value3 == null ? null : value3.getString());
                break;
            }
            case 2005: {
                value = ToDateParser.toTimestampTz(sessionLocal, value2.getString(), value3 == null ? null : value3.getString());
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        return value;
    }

    static {
        FUNCTIONS.put("ADD_MONTHS", new FunctionInfo("ADD_MONTHS", 2001, 2, 20, true, true));
        FUNCTIONS.put("SYS_GUID", new FunctionInfo("SYS_GUID", 2002, 0, 6, false, false));
        FUNCTIONS.put("TO_DATE", new FunctionInfo("TO_DATE", 2003, -1, 20, true, true));
        FUNCTIONS.put("TO_TIMESTAMP", new FunctionInfo("TO_TIMESTAMP", 2004, -1, 20, true, true));
        FUNCTIONS.put("TO_TIMESTAMP_TZ", new FunctionInfo("TO_TIMESTAMP_TZ", 2005, -1, 21, true, true));
    }
}

