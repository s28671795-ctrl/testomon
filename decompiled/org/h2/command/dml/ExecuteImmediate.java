/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;

public class ExecuteImmediate
extends Prepared {
    private Expression statement;

    public ExecuteImmediate(SessionLocal sessionLocal, Expression expression) {
        super(sessionLocal);
        this.statement = expression.optimize(sessionLocal);
    }

    @Override
    public long update() {
        String string = this.statement.getValue(this.session).getString();
        if (string == null) {
            throw DbException.getInvalidValueException("SQL command", null);
        }
        Prepared prepared = this.session.prepare(string);
        if (prepared.isQuery()) {
            throw DbException.get(42001, string, "<not a query>");
        }
        return prepared.update();
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public int getType() {
        return 91;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }
}

