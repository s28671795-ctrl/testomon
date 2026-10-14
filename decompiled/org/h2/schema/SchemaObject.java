/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import org.h2.engine.DbObject;
import org.h2.schema.Schema;

public abstract class SchemaObject
extends DbObject {
    private final Schema schema;

    protected SchemaObject(Schema schema, int n, String string, int n2) {
        super(schema.getDatabase(), n, string, n2);
        this.schema = schema;
    }

    public final Schema getSchema() {
        return this.schema;
    }

    @Override
    public String getSQL(int n) {
        return this.getSQL(new StringBuilder(), n).toString();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        this.schema.getSQL(stringBuilder, n).append('.');
        return super.getSQL(stringBuilder, n);
    }

    public boolean isHidden() {
        return false;
    }
}

