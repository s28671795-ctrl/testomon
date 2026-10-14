/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterTable;
import org.h2.constraint.ConstraintReferential;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;

public class AlterTableRenameColumn
extends AlterTable {
    private boolean ifExists;
    private String oldName;
    private String newName;

    public AlterTableRenameColumn(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setOldColumnName(String string) {
        this.oldName = string;
    }

    public void setNewColumnName(String string) {
        this.newName = string;
    }

    @Override
    public long update(Table table) {
        Column column = table.getColumn(this.oldName, this.ifExists);
        if (column == null) {
            return 0L;
        }
        table.checkSupportAlter();
        table.renameColumn(column, this.newName);
        table.setModified();
        Database database = this.getDatabase();
        database.updateMeta(this.session, table);
        for (DbObject dbObject : table.getChildren()) {
            if (!(dbObject instanceof ConstraintReferential)) continue;
            ConstraintReferential constraintReferential = (ConstraintReferential)dbObject;
            constraintReferential.updateOnTableColumnRename();
        }
        for (DbObject dbObject : table.getChildren()) {
            if (dbObject.getCreateSQL() == null) continue;
            database.updateMeta(this.session, dbObject);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 16;
    }
}

