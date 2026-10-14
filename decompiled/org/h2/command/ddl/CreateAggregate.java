/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.UserAggregate;

public class CreateAggregate
extends SchemaCommand {
    private String name;
    private String javaClassMethod;
    private boolean ifNotExists;
    private boolean force;

    public CreateAggregate(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        Schema schema = this.getSchema();
        if (schema.findFunctionOrAggregate(this.name) != null) {
            if (!this.ifNotExists) {
                throw DbException.get(90076, this.name);
            }
        } else {
            int n = this.getObjectId();
            UserAggregate userAggregate = new UserAggregate(schema, n, this.name, this.javaClassMethod, this.force);
            database.addSchemaObject(this.session, userAggregate);
        }
        return 0L;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setJavaClassMethod(String string) {
        this.javaClassMethod = string;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setForce(boolean bl) {
        this.force = bl;
    }

    @Override
    public int getType() {
        return 22;
    }
}

