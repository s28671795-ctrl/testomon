/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.MaterializedView;
import org.h2.table.Table;
import org.h2.table.TableType;

public class DropMaterializedView
extends SchemaCommand {
    private String viewName;
    private boolean ifExists;

    public DropMaterializedView(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setViewName(String string) {
        this.viewName = string;
    }

    @Override
    public long update() {
        Table table = this.getSchema().findTableOrView(this.session, this.viewName);
        if (table == null) {
            if (!this.ifExists) {
                throw DbException.get(90037, this.viewName);
            }
        } else {
            if (TableType.MATERIALIZED_VIEW != table.getTableType()) {
                throw DbException.get(90037, this.viewName);
            }
            this.session.getUser().checkSchemaOwner(table.getSchema());
            MaterializedView materializedView = (MaterializedView)table;
            for (Table table2 : materializedView.getSelect().getTables()) {
                table2.removeDependentMaterializedView(materializedView);
            }
            Database database = this.getDatabase();
            database.lockMeta(this.session);
            database.removeSchemaObject(this.session, table);
            database.unlockMeta(this.session);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 104;
    }
}

