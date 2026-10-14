/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Operation1;
import org.h2.expression.ValueExpression;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;

public final class Regclass
extends Operation1 {
    public Regclass(Expression expression) {
        super(expression);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.arg.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        int n = value.getValueType();
        if (n >= 9 && n <= 11) {
            return value.convertToInt(null);
        }
        if (n == 12) {
            return ValueInteger.get((int)value.getLong());
        }
        String string = value.getString();
        for (Schema schema : sessionLocal.getDatabase().getAllSchemas()) {
            Table table = schema.findTableOrView(sessionLocal, string);
            if (table != null && !table.isHidden()) {
                return ValueInteger.get(table.getId());
            }
            Index index = schema.findIndex(sessionLocal, string);
            if (index == null || index.getCreateSQL() == null) continue;
            return ValueInteger.get(index.getId());
        }
        throw DbException.get(42102, string);
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_INTEGER;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        if (this.arg.isConstant()) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.arg.getSQL(stringBuilder, n, 0).append("::REGCLASS");
    }

    @Override
    public int getCost() {
        return this.arg.getCost() + 100;
    }
}

