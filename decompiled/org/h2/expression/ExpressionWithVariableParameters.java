/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.expression.Expression;
import org.h2.message.DbException;

public interface ExpressionWithVariableParameters {
    public void addParameter(Expression var1);

    public void doneWithParameters() throws DbException;
}

