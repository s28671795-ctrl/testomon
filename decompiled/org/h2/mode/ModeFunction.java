/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import org.h2.engine.Database;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.CurrentDateTimeValueFunction;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.mode.CompatibilityDateTimeValueFunction;
import org.h2.mode.FunctionInfo;
import org.h2.mode.FunctionsDB2Derby;
import org.h2.mode.FunctionsLegacy;
import org.h2.mode.FunctionsMSSQLServer;
import org.h2.mode.FunctionsMySQL;
import org.h2.mode.FunctionsOracle;
import org.h2.mode.FunctionsPostgreSQL;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class ModeFunction
extends FunctionN {
    protected static final int VAR_ARGS = -1;
    protected final FunctionInfo info;

    public static ModeFunction getFunction(Database database, String string) {
        Mode.ModeEnum modeEnum = database.getMode().getEnum();
        if (modeEnum != Mode.ModeEnum.REGULAR) {
            return ModeFunction.getCompatibilityModeFunction(string, modeEnum);
        }
        return null;
    }

    private static ModeFunction getCompatibilityModeFunction(String string, Mode.ModeEnum modeEnum) {
        switch (modeEnum) {
            case LEGACY: {
                return FunctionsLegacy.getFunction(string);
            }
            case DB2: 
            case Derby: {
                return FunctionsDB2Derby.getFunction(string);
            }
            case MSSQLServer: {
                return FunctionsMSSQLServer.getFunction(string);
            }
            case MariaDB: 
            case MySQL: {
                return FunctionsMySQL.getFunction(string);
            }
            case Oracle: {
                return FunctionsOracle.getFunction(string);
            }
            case PostgreSQL: {
                return FunctionsPostgreSQL.getFunction(string);
            }
        }
        return null;
    }

    public static Expression getCompatibilityDateTimeValueFunction(Database database, String string, int n) {
        switch (string) {
            case "SYSDATE": {
                switch (database.getMode().getEnum()) {
                    case LEGACY: 
                    case Oracle: 
                    case HSQLDB: {
                        return new CompatibilityDateTimeValueFunction(0, -1);
                    }
                }
                break;
            }
            case "SYSTIMESTAMP": {
                switch (database.getMode().getEnum()) {
                    case LEGACY: 
                    case Oracle: {
                        return new CompatibilityDateTimeValueFunction(1, n);
                    }
                }
                break;
            }
            case "TODAY": {
                switch (database.getMode().getEnum()) {
                    case LEGACY: 
                    case HSQLDB: {
                        return new CurrentDateTimeValueFunction(0, n);
                    }
                }
            }
        }
        return null;
    }

    ModeFunction(FunctionInfo functionInfo) {
        super(new Expression[functionInfo.parameterCount != -1 ? functionInfo.parameterCount : 4]);
        this.info = functionInfo;
    }

    static Value getNullOrValue(SessionLocal sessionLocal, Expression[] expressionArray, Value[] valueArray, int n) {
        if (n >= expressionArray.length) {
            return null;
        }
        Value value = valueArray[n];
        if (value == null) {
            Expression expression = expressionArray[n];
            if (expression == null) {
                return null;
            }
            value = valueArray[n] = expression.getValue(sessionLocal);
        }
        return value;
    }

    final Value[] getArgumentsValues(SessionLocal sessionLocal, Expression[] expressionArray) {
        Value[] valueArray = new Value[expressionArray.length];
        if (this.info.nullIfParameterIsNull) {
            int n = expressionArray.length;
            for (int i = 0; i < n; ++i) {
                Value value = expressionArray[i].getValue(sessionLocal);
                if (value == ValueNull.INSTANCE) {
                    return null;
                }
                valueArray[i] = value;
            }
        }
        return valueArray;
    }

    void checkParameterCount(int n) {
        throw DbException.getInternalError("type=" + this.info.type);
    }

    @Override
    public void doneWithParameters() {
        int n = this.info.parameterCount;
        if (n == -1) {
            this.checkParameterCount(this.argsCount);
            super.doneWithParameters();
        } else if (n != this.argsCount) {
            throw DbException.get(7001, this.info.name, Integer.toString(this.argsCount));
        }
    }

    final boolean optimizeArguments(SessionLocal sessionLocal) {
        return this.optimizeArguments(sessionLocal, this.info.deterministic);
    }

    @Override
    public String getName() {
        return this.info.name;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!super.isEverything(expressionVisitor)) {
            return false;
        }
        switch (expressionVisitor.getType()) {
            case 2: 
            case 5: 
            case 8: {
                return this.info.deterministic;
            }
        }
        return true;
    }
}

