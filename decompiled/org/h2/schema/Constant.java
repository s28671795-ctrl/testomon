/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import org.h2.engine.SessionLocal;
import org.h2.expression.ValueExpression;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.value.Value;

public final class Constant
extends SchemaObject {
    private Value value;
    private ValueExpression expression;

    public Constant(Schema schema, int n, String string) {
        super(schema, n, string, 8);
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("CREATE CONSTANT ");
        this.getSQL(stringBuilder, 0).append(" VALUE ");
        return this.value.getSQL(stringBuilder, 0).toString();
    }

    @Override
    public int getType() {
        return 11;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.database.removeMeta(sessionLocal, this.getId());
        this.invalidate();
    }

    public void setValue(Value value) {
        this.value = value;
        this.expression = ValueExpression.get(value);
    }

    public ValueExpression getValue() {
        return this.expression;
    }
}

