/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public abstract class AlterTable
extends SchemaCommand {
    String tableName;
    boolean ifTableExists;

    AlterTable(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public final void setTableName(String string) {
        this.tableName = string;
    }

    public final void setIfTableExists(boolean bl) {
        this.ifTableExists = bl;
    }

    @Override
    public final long update() {
        Table table = this.getSchema().findTableOrView(this.session, this.tableName);
        if (table == null) {
            if (this.ifTableExists) {
                return 0L;
            }
            throw DbException.get(42102, this.tableName);
        }
        this.session.getUser().checkTableRight(table, 32);
        return this.update(table);
    }

    abstract long update(Table var1);
}

