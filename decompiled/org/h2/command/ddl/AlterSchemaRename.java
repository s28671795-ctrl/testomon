/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;

public class AlterSchemaRename
extends DefineCommand {
    private Schema oldSchema;
    private String newSchemaName;

    public AlterSchemaRename(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setOldSchema(Schema schema) {
        this.oldSchema = schema;
    }

    public void setNewName(String string) {
        this.newSchemaName = string;
    }

    @Override
    public long update() {
        this.session.getUser().checkSchemaAdmin();
        Database database = this.getDatabase();
        if (!this.oldSchema.canDrop()) {
            throw DbException.get(90090, this.oldSchema.getName());
        }
        if (database.findSchema(this.newSchemaName) != null || this.newSchemaName.equals(this.oldSchema.getName())) {
            throw DbException.get(90078, this.newSchemaName);
        }
        database.renameDatabaseObject(this.session, this.oldSchema, this.newSchemaName);
        ArrayList<SchemaObject> arrayList = new ArrayList<SchemaObject>();
        for (Schema schema : database.getAllSchemas()) {
            schema.getAll(arrayList);
            for (SchemaObject schemaObject : arrayList) {
                database.updateMeta(this.session, schemaObject);
            }
            arrayList.clear();
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 2;
    }
}

