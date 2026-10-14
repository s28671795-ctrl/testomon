/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Constant;
import org.h2.schema.Schema;

public class DropConstant
extends SchemaOwnerCommand {
    private String constantName;
    private boolean ifExists;

    public DropConstant(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setConstantName(String string) {
        this.constantName = string;
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        Constant constant = schema.findConstant(this.constantName);
        if (constant == null) {
            if (!this.ifExists) {
                throw DbException.get(90115, this.constantName);
            }
        } else {
            database.removeSchemaObject(this.session, constant);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 37;
    }
}

