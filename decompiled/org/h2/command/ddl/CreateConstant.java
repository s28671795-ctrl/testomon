/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Constant;
import org.h2.schema.Schema;
import org.h2.value.Value;

public class CreateConstant
extends SchemaOwnerCommand {
    private String constantName;
    private Expression expression;
    private boolean ifNotExists;

    public CreateConstant(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        if (schema.findConstant(this.constantName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(90114, this.constantName);
        }
        int n = this.getObjectId();
        Constant constant = new Constant(schema, n, this.constantName);
        this.expression = this.expression.optimize(this.session);
        Value value = this.expression.getValue(this.session);
        constant.setValue(value);
        database.addSchemaObject(this.session, constant);
        return 0L;
    }

    public void setConstantName(String string) {
        this.constantName = string;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int getType() {
        return 23;
    }
}

