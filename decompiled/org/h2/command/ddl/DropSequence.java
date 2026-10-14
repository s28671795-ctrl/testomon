/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;

public class DropSequence
extends SchemaOwnerCommand {
    private String sequenceName;
    private boolean ifExists;

    public DropSequence(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setSequenceName(String string) {
        this.sequenceName = string;
    }

    @Override
    long update(Schema schema) {
        Sequence sequence2 = schema.findSequence(this.sequenceName);
        if (sequence2 == null) {
            if (!this.ifExists) {
                throw DbException.get(90036, this.sequenceName);
            }
        } else {
            if (sequence2.getBelongsToTable()) {
                throw DbException.get(90082, this.sequenceName);
            }
            this.getDatabase().removeSchemaObject(this.session, sequence2);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 43;
    }
}

