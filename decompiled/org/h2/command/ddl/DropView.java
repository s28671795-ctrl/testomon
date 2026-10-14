/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.SchemaCommand;
import org.h2.constraint.ConstraintActionType;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.table.TableView;

public class DropView
extends SchemaCommand {
    private String viewName;
    private boolean ifExists;
    private ConstraintActionType dropAction;

    public DropView(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
        this.dropAction = this.getDatabase().getSettings().dropRestrict ? ConstraintActionType.RESTRICT : ConstraintActionType.CASCADE;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setDropAction(ConstraintActionType constraintActionType) {
        this.dropAction = constraintActionType;
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
            if (TableType.VIEW != table.getTableType()) {
                throw DbException.get(90037, this.viewName);
            }
            this.session.getUser().checkSchemaOwner(table.getSchema());
            if (this.dropAction == ConstraintActionType.RESTRICT) {
                for (DbObject object2 : table.getChildren()) {
                    if (!(object2 instanceof TableView)) continue;
                    throw DbException.get(90107, this.viewName, object2.getName());
                }
            }
            TableView tableView = (TableView)table;
            ArrayList<Table> arrayList = new ArrayList<Table>(tableView.getTables());
            table.lock(this.session, 2);
            Database database = this.getDatabase();
            database.removeSchemaObject(this.session, table);
            for (Table table2 : arrayList) {
                TableView tableView2;
                if (TableType.VIEW != table2.getTableType() || !(tableView2 = (TableView)table2).isTableExpression() || tableView2.getName() == null) continue;
                database.removeSchemaObject(this.session, tableView2);
            }
            database.unlockMeta(this.session);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 48;
    }
}

