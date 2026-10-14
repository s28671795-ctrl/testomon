/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.UserAggregate;

public class DropAggregate
extends SchemaOwnerCommand {
    private String name;
    private boolean ifExists;

    public DropAggregate(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        UserAggregate userAggregate = schema.findAggregate(this.name);
        if (userAggregate == null) {
            if (!this.ifExists) {
                throw DbException.get(90132, this.name);
            }
        } else {
            database.removeSchemaObject(this.session, userAggregate);
        }
        return 0L;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    @Override
    public int getType() {
        return 36;
    }
}

