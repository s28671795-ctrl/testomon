/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.HashMap;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.mode.FunctionInfo;
import org.h2.mode.ModeFunction;
import org.h2.value.ExtTypeInfoNumeric;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class FunctionsDB2Derby
extends ModeFunction {
    private static final int IDENTITY_VAL_LOCAL = 5001;
    private static final HashMap<String, FunctionInfo> FUNCTIONS = new HashMap();
    private static final TypeInfo IDENTITY_VAL_LOCAL_TYPE = TypeInfo.getTypeInfo(13, 31L, 0, ExtTypeInfoNumeric.DECIMAL);

    public static FunctionsDB2Derby getFunction(String string) {
        FunctionInfo functionInfo = FUNCTIONS.get(string);
        return functionInfo != null ? new FunctionsDB2Derby(functionInfo) : null;
    }

    private FunctionsDB2Derby(FunctionInfo functionInfo) {
        super(functionInfo);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        switch (this.info.type) {
            case 5001: {
                return sessionLocal.getLastIdentity().convertTo(this.type);
            }
        }
        throw DbException.getInternalError("type=" + this.info.type);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        switch (this.info.type) {
            case 5001: {
                this.type = IDENTITY_VAL_LOCAL_TYPE;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        return this;
    }

    static {
        FUNCTIONS.put("IDENTITY_VAL_LOCAL", new FunctionInfo("IDENTITY_VAL_LOCAL", 5001, 0, 12, true, false));
    }
}

