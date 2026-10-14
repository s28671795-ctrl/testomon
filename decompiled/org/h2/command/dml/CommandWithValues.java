/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.ArrayList;
import org.h2.command.dml.DataChangeStatement;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.util.Utils;

public abstract class CommandWithValues
extends DataChangeStatement {
    protected final ArrayList<Expression[]> valuesExpressionList = Utils.newSmallArrayList();

    protected CommandWithValues(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void addRow(Expression[] expressionArray) {
        this.valuesExpressionList.add(expressionArray);
    }
}

