/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.TableSynonym;

public class DropSynonym
extends SchemaOwnerCommand {
    private String synonymName;
    private boolean ifExists;

    public DropSynonym(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setSynonymName(String string) {
        this.synonymName = string;
    }

    @Override
    long update(Schema schema) {
        TableSynonym tableSynonym = schema.getSynonym(this.synonymName);
        if (tableSynonym == null) {
            if (!this.ifExists) {
                throw DbException.get(42102, this.synonymName);
            }
        } else {
            this.getDatabase().removeSchemaObject(this.session, tableSynonym);
        }
        return 0L;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    @Override
    public int getType() {
        return 89;
    }
}

