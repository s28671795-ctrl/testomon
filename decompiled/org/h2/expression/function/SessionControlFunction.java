/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.command.Command;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.Function1;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public final class SessionControlFunction
extends Function1 {
    public static final int ABORT_SESSION = 0;
    public static final int CANCEL_SESSION = 1;
    private static final String[] NAMES = new String[]{"ABORT_SESSION", "CANCEL_SESSION"};
    private final int function;

    public SessionControlFunction(Expression expression, int n) {
        super(expression);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.arg.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        int n = value.getInt();
        sessionLocal.getUser().checkAdmin();
        block4: for (SessionLocal sessionLocal2 : sessionLocal.getDatabase().getSessions(false)) {
            if (sessionLocal2.getId() != n) continue;
            Command command = sessionLocal2.getCurrentCommand();
            switch (this.function) {
                case 0: {
                    if (command != null) {
                        command.cancel();
                    }
                    sessionLocal2.close();
                    return ValueBoolean.TRUE;
                }
                case 1: {
                    if (command == null) break block4;
                    command.cancel();
                    return ValueBoolean.TRUE;
                }
                default: {
                    throw DbException.getInternalError("function=" + this.function);
                }
            }
        }
        return ValueBoolean.FALSE;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        this.type = TypeInfo.TYPE_BOOLEAN;
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: 
            case 5: 
            case 8: {
                return false;
            }
        }
        return super.isEverything(expressionVisitor);
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

