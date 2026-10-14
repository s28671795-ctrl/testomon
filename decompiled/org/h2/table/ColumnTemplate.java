/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.schema.Domain;

public interface ColumnTemplate {
    public Domain getDomain();

    public void setDomain(Domain var1);

    public void setDefaultExpression(SessionLocal var1, Expression var2);

    public Expression getDefaultExpression();

    public Expression getEffectiveDefaultExpression();

    public String getDefaultSQL();

    public void setOnUpdateExpression(SessionLocal var1, Expression var2);

    public Expression getOnUpdateExpression();

    public Expression getEffectiveOnUpdateExpression();

    public String getOnUpdateSQL();

    public void prepareExpressions(SessionLocal var1);
}

