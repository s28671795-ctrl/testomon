/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.condition.PredicateWithSubquery;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;

public class ExistsPredicate
extends PredicateWithSubquery {
    public ExistsPredicate(Query query) {
        super(query);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        this.query.setSession(sessionLocal);
        return ValueBoolean.get(this.query.exists());
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return super.getUnenclosedSQL(stringBuilder.append("EXISTS"), n);
    }
}

