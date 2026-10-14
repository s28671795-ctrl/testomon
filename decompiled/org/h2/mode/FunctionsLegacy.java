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
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class FunctionsLegacy
extends ModeFunction {
    private static final HashMap<String, FunctionInfo> FUNCTIONS = new HashMap();
    private static final int IDENTITY = 6001;
    private static final int SCOPE_IDENTITY = 6002;

    public static FunctionsLegacy getFunction(String string) {
        FunctionInfo functionInfo = FUNCTIONS.get(string);
        if (functionInfo != null) {
            return new FunctionsLegacy(functionInfo);
        }
        return null;
    }

    private FunctionsLegacy(FunctionInfo functionInfo) {
        super(functionInfo);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        switch (this.info.type) {
            case 6001: 
            case 6002: {
                return sessionLocal.getLastIdentity().convertTo(this.type);
            }
        }
        throw DbException.getInternalError("type=" + this.info.type);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.type = TypeInfo.getTypeInfo(this.info.returnDataType);
        return this;
    }

    static {
        FUNCTIONS.put("IDENTITY", new FunctionInfo("IDENTITY", 6001, 0, 12, true, false));
        FUNCTIONS.put("SCOPE_IDENTITY", new FunctionInfo("SCOPE_IDENTITY", 6002, 0, 12, true, false));
    }
}

