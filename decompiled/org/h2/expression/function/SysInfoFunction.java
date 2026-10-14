/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.Constants;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class SysInfoFunction
extends Operation0
implements NamedExpression {
    public static final int AUTOCOMMIT = 0;
    public static final int DATABASE_PATH = 1;
    public static final int H2VERSION = 2;
    public static final int LOCK_MODE = 3;
    public static final int LOCK_TIMEOUT = 4;
    public static final int MEMORY_FREE = 5;
    public static final int MEMORY_USED = 6;
    public static final int READONLY = 7;
    public static final int SESSION_ID = 8;
    public static final int TRANSACTION_ID = 9;
    private static final int[] TYPES = new int[]{8, 2, 2, 11, 11, 12, 12, 8, 11, 2};
    private static final String[] NAMES = new String[]{"AUTOCOMMIT", "DATABASE_PATH", "H2VERSION", "LOCK_MODE", "LOCK_TIMEOUT", "MEMORY_FREE", "MEMORY_USED", "READONLY", "SESSION_ID", "TRANSACTION_ID"};
    private final int function;
    private final TypeInfo type;

    public static String getName(int n) {
        return NAMES[n];
    }

    public SysInfoFunction(int n) {
        this.function = n;
        this.type = TypeInfo.getTypeInfo(TYPES[n]);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        switch (this.function) {
            case 0: {
                value = ValueBoolean.get(sessionLocal.getAutoCommit());
                break;
            }
            case 1: {
                String string = sessionLocal.getDatabase().getDatabasePath();
                value = string != null ? ValueVarchar.get(string, sessionLocal) : ValueNull.INSTANCE;
                break;
            }
            case 2: {
                value = ValueVarchar.get(Constants.VERSION, sessionLocal);
                break;
            }
            case 3: {
                value = ValueInteger.get(sessionLocal.getDatabase().getLockMode());
                break;
            }
            case 4: {
                value = ValueInteger.get(sessionLocal.getLockTimeout());
                break;
            }
            case 5: {
                sessionLocal.getUser().checkAdmin();
                value = ValueBigint.get(Utils.getMemoryFree());
                break;
            }
            case 6: {
                sessionLocal.getUser().checkAdmin();
                value = ValueBigint.get(Utils.getMemoryUsed());
                break;
            }
            case 7: {
                value = ValueBoolean.get(sessionLocal.getDatabase().isReadOnly());
                break;
            }
            case 8: {
                value = ValueInteger.get(sessionLocal.getId());
                break;
            }
            case 9: {
                value = sessionLocal.getTransactionId();
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.getName()).append("()");
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return true;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

