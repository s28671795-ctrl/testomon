/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.schema.Sequence;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class SequenceValue
extends Operation0 {
    private final Sequence sequence;
    private final boolean current;
    private final Prepared prepared;

    public SequenceValue(Sequence sequence2, Prepared prepared) {
        this.sequence = sequence2;
        this.current = false;
        this.prepared = prepared;
    }

    public SequenceValue(Sequence sequence2) {
        this.sequence = sequence2;
        this.current = true;
        this.prepared = null;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.current ? sessionLocal.getCurrentValueFor(this.sequence) : sessionLocal.getNextValueFor(this.sequence, this.prepared);
    }

    @Override
    public TypeInfo getType() {
        return this.sequence.getDataType();
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.current ? "CURRENT" : "NEXT").append(" VALUE FOR ");
        return this.sequence.getSQL(stringBuilder, n);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 0: 
            case 2: 
            case 8: {
                return false;
            }
            case 4: {
                expressionVisitor.addDataModificationId(this.sequence.getModificationId());
                return true;
            }
            case 7: {
                expressionVisitor.addDependency(this.sequence);
                return true;
            }
            case 5: {
                return this.current;
            }
        }
        return true;
    }

    @Override
    public int getCost() {
        return 1;
    }
}

