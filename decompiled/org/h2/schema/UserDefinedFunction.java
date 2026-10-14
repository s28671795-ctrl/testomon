/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;

public abstract class UserDefinedFunction
extends SchemaObject {
    String className;

    UserDefinedFunction(Schema schema, int n, String string, int n2) {
        super(schema, n, string, n2);
    }

    @Override
    public final void checkRename() {
        throw DbException.getUnsupportedException("RENAME");
    }

    public final String getJavaClassName() {
        return this.className;
    }
}

