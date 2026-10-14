/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.util.ParserUtil;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class Variable
extends Operation0 {
    private final String name;
    private Value lastValue;

    public Variable(SessionLocal sessionLocal, String string) {
        this.name = string;
        this.lastValue = sessionLocal.getVariable(string);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return ParserUtil.quoteIdentifier(stringBuilder.append('@'), this.name, n);
    }

    @Override
    public TypeInfo getType() {
        return this.lastValue.getType();
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        this.lastValue = sessionLocal.getVariable(this.name);
        return this.lastValue;
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

    public String getName() {
        return this.name;
    }
}

