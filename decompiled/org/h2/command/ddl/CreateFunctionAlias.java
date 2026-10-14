/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.FunctionAlias;
import org.h2.schema.Schema;
import org.h2.util.StringUtils;

public class CreateFunctionAlias
extends SchemaCommand {
    private String aliasName;
    private String javaClassMethod;
    private boolean deterministic;
    private boolean ifNotExists;
    private boolean force;
    private String source;

    public CreateFunctionAlias(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        Schema schema = this.getSchema();
        if (schema.findFunctionOrAggregate(this.aliasName) != null) {
            if (!this.ifNotExists) {
                throw DbException.get(90076, this.aliasName);
            }
        } else {
            int n = this.getObjectId();
            FunctionAlias functionAlias = this.javaClassMethod != null ? FunctionAlias.newInstance(schema, n, this.aliasName, this.javaClassMethod, this.force) : FunctionAlias.newInstanceFromSource(schema, n, this.aliasName, this.source, this.force);
            functionAlias.setDeterministic(this.deterministic);
            database.addSchemaObject(this.session, functionAlias);
        }
        return 0L;
    }

    public void setAliasName(String string) {
        this.aliasName = string;
    }

    public void setJavaClassMethod(String string) {
        this.javaClassMethod = StringUtils.replaceAll(string, " ", "");
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setForce(boolean bl) {
        this.force = bl;
    }

    public void setDeterministic(boolean bl) {
        this.deterministic = bl;
    }

    public void setSource(String string) {
        this.source = string;
    }

    @Override
    public int getType() {
        return 24;
    }
}

