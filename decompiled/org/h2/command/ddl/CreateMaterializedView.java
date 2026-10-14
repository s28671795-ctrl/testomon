/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.CreateTable;
import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.command.query.Query;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.MaterializedView;
import org.h2.table.Table;
import org.h2.table.TableType;

public class CreateMaterializedView
extends SchemaOwnerCommand {
    private final CreateTable createTable;
    private boolean orReplace;
    private boolean ifNotExists;
    private String viewName;
    private String comment;
    private Query select;
    private String selectSQL;

    public CreateMaterializedView(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
        this.createTable = new CreateTable(sessionLocal, schema);
    }

    public void setViewName(String string) {
        this.viewName = string;
        this.createTable.setTableName(string + "$1");
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setSelectSQL(String string) {
        this.selectSQL = string;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
        this.createTable.setIfNotExists(bl);
    }

    public void setSelect(Query query) {
        this.select = query;
        this.createTable.setQuery(query);
    }

    public void setOrReplace(boolean bl) {
        this.orReplace = bl;
    }

    @Override
    long update(Schema schema) {
        Database database = this.getDatabase();
        Table table = schema.findTableOrView(this.session, this.viewName);
        MaterializedView materializedView = null;
        if (table != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            if (!this.orReplace || TableType.MATERIALIZED_VIEW != table.getTableType()) {
                throw DbException.get(90038, this.viewName);
            }
            materializedView = (MaterializedView)table;
        }
        int n = this.getObjectId();
        this.createTable.update();
        Table table2 = schema.getTableOrView(this.session, this.viewName + "$1");
        if (materializedView == null) {
            materializedView = new MaterializedView(schema, n, this.viewName, table2, this.select, this.selectSQL);
        } else {
            materializedView.replace(table2, this.select, this.selectSQL);
            materializedView.setModified();
        }
        if (this.comment != null) {
            materializedView.setComment(this.comment);
        }
        for (Table table3 : this.select.getTables()) {
            table3.addDependentMaterializedView(materializedView);
        }
        if (table == null) {
            database.addSchemaObject(this.session, materializedView);
            database.unlockMeta(this.session);
        } else {
            database.updateMeta(this.session, materializedView);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 102;
    }
}

