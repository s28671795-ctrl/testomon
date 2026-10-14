/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.TriggerObject;
import org.h2.table.Table;

public class DropTrigger
extends SchemaCommand {
    private String triggerName;
    private boolean ifExists;

    public DropTrigger(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setTriggerName(String string) {
        this.triggerName = string;
    }

    @Override
    public long update() {
        Database database = this.getDatabase();
        TriggerObject triggerObject = this.getSchema().findTrigger(this.triggerName);
        if (triggerObject == null) {
            if (!this.ifExists) {
                throw DbException.get(90042, this.triggerName);
            }
        } else {
            Table table = triggerObject.getTable();
            this.session.getUser().checkTableRight(table, 32);
            database.removeSchemaObject(this.session, triggerObject);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 45;
    }
}

