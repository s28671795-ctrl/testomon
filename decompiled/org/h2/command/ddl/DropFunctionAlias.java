/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.FunctionAlias;
import org.h2.schema.Schema;

public class DropFunctionAlias
extends SchemaOwnerCommand {
    private String aliasName;
    private boolean ifExists;

    public DropFunctionAlias(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        FunctionAlias functionAlias = schema.findFunction(this.aliasName);
        if (functionAlias == null) {
            if (!this.ifExists) {
                throw DbException.get(90077, this.aliasName);
            }
        } else {
            database.removeSchemaObject(this.session, functionAlias);
        }
        return 0L;
    }

    public void setAliasName(String string) {
        this.aliasName = string;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    @Override
    public int getType() {
        return 39;
    }
}

