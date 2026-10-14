/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterTable;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public class AlterTableRename
extends AlterTable {
    private String newTableName;
    private boolean hidden;

    public AlterTableRename(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setNewTableName(String string) {
        this.newTableName = string;
    }

    @Override
    public long update(Table table) {
        Database database = this.getDatabase();
        Table table2 = this.getSchema().findTableOrView(this.session, this.newTableName);
        if (table2 != null && this.hidden && this.newTableName.equals(table.getName())) {
            if (!table2.isHidden()) {
                table2.setHidden(this.hidden);
                table.setHidden(true);
                database.updateMeta(this.session, table);
            }
            return 0L;
        }
        if (table2 != null || this.newTableName.equals(table.getName())) {
            throw DbException.get(42101, this.newTableName);
        }
        if (table.isTemporary()) {
            throw DbException.getUnsupportedException("temp table");
        }
        database.renameSchemaObject(this.session, table, this.newTableName);
        return 0L;
    }

    @Override
    public int getType() {
        return 15;
    }

    public void setHidden(boolean bl) {
        this.hidden = bl;
    }
}

