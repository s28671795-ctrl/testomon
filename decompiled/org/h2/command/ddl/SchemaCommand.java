/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.SessionLocal;
import org.h2.schema.Schema;

public abstract class SchemaCommand
extends DefineCommand {
    private final Schema schema;

    public SchemaCommand(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal);
        this.schema = schema;
    }

    protected final Schema getSchema() {
        return this.schema;
    }
}

