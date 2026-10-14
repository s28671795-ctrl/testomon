/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.SessionLocal;
import org.h2.schema.Schema;

abstract class SchemaOwnerCommand
extends SchemaCommand {
    SchemaOwnerCommand(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    @Override
    public final long update() {
        Schema schema = this.getSchema();
        this.session.getUser().checkSchemaOwner(schema);
        return this.update(schema);
    }

    abstract long update(Schema var1);
}

