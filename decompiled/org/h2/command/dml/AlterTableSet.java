/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public class AlterTableSet
extends SchemaCommand {
    private boolean ifTableExists;
    private String tableName;
    private final int type;
    private final boolean value;
    private boolean checkExisting;

    public AlterTableSet(SessionLocal sessionLocal, Schema schema, int n, boolean bl) {
        super(sessionLocal, schema);
        this.type = n;
        this.value = bl;
    }

    public void setCheckExisting(boolean bl) {
        this.checkExisting = bl;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    public void setIfTableExists(boolean bl) {
        this.ifTableExists = bl;
    }

    public void setTableName(String string) {
        this.tableName = string;
    }

    @Override
    public long update() {
        Table table = this.getSchema().resolveTableOrView(this.session, this.tableName);
        if (table == null) {
            if (this.ifTableExists) {
                return 0L;
            }
            throw DbException.get(42102, this.tableName);
        }
        this.session.getUser().checkTableRight(table, 32);
        table.lock(this.session, 2);
        switch (this.type) {
            case 55: {
                table.setCheckForeignKeyConstraints(this.session, this.value, this.value ? this.checkExisting : false);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        return 0L;
    }

    @Override
    public int getType() {
        return this.type;
    }
}

