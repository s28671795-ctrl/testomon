/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import org.h2.command.dml.Update;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.message.DbException;
import org.h2.table.Column;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class OnDuplicateKeyValues
extends Operation0 {
    private final Column column;
    private final Update update;

    public OnDuplicateKeyValues(Column column, Update update) {
        this.column = column;
        this.update = update;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.update.getOnDuplicateKeyInsert().getOnDuplicateKeyValue(this.column.getColumnId());
        if (value == null) {
            throw DbException.getUnsupportedException(this.getTraceSQL());
        }
        return value;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.column.getSQL(stringBuilder.append("VALUES("), n).append(')');
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
        return this.column.getType();
    }

    @Override
    public int getCost() {
        return 1;
    }
}

