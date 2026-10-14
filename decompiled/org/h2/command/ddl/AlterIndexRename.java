/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.schema.Schema;

public class AlterIndexRename
extends DefineCommand {
    private boolean ifExists;
    private Schema oldSchema;
    private String oldIndexName;
    private String newIndexName;

    public AlterIndexRename(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setOldSchema(Schema schema) {
        this.oldSchema = schema;
    }

    public void setOldName(String string) {
        this.oldIndexName = string;
    }

    public void setNewName(String string) {
        this.newIndexName = string;
    }

    @Override
    public long update() {
        Database database = this.getDatabase();
        Index index = this.oldSchema.findIndex(this.session, this.oldIndexName);
        if (index == null) {
            if (!this.ifExists) {
                throw DbException.get(42112, this.newIndexName);
            }
            return 0L;
        }
        if (this.oldSchema.findIndex(this.session, this.newIndexName) != null || this.newIndexName.equals(this.oldIndexName)) {
            throw DbException.get(42111, this.newIndexName);
        }
        this.session.getUser().checkTableRight(index.getTable(), 32);
        database.renameSchemaObject(this.session, index, this.newIndexName);
        return 0L;
    }

    @Override
    public int getType() {
        return 1;
    }
}

