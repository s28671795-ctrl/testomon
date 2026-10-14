/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.DefineCommand;
import org.h2.constraint.ConstraintActionType;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;

public class DropSchema
extends DefineCommand {
    private String schemaName;
    private boolean ifExists;
    private ConstraintActionType dropAction;

    public DropSchema(SessionLocal sessionLocal) {
        super(sessionLocal);
        this.dropAction = this.getDatabase().getSettings().dropRestrict ? ConstraintActionType.RESTRICT : ConstraintActionType.CASCADE;
    }

    public void setSchemaName(String string) {
        this.schemaName = string;
    }

    @Override
    public long update() {
        Database database = this.getDatabase();
        Schema schema = database.findSchema(this.schemaName);
        if (schema == null) {
            if (!this.ifExists) {
                throw DbException.get(90079, this.schemaName);
            }
        } else {
            ArrayList<SchemaObject> arrayList;
            int n;
            this.session.getUser().checkSchemaOwner(schema);
            if (!schema.canDrop()) {
                throw DbException.get(90090, this.schemaName);
            }
            if (this.dropAction == ConstraintActionType.RESTRICT && !schema.isEmpty() && (n = (arrayList = schema.getAll(null)).size()) > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < n; ++i) {
                    if (i > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(arrayList.get(i).getName());
                }
                throw DbException.get(90107, this.schemaName, stringBuilder.toString());
            }
            database.removeDatabaseObject(this.session, schema);
        }
        return 0L;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setDropAction(ConstraintActionType constraintActionType) {
        this.dropAction = constraintActionType;
    }

    @Override
    public int getType() {
        return 42;
    }
}

