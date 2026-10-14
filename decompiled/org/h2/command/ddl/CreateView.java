/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.command.query.Query;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.table.TableView;
import org.h2.value.TypeInfo;

public class CreateView
extends SchemaOwnerCommand {
    private Query select;
    private String viewName;
    private boolean ifNotExists;
    private String selectSQL;
    private String[] columnNames;
    private String comment;
    private boolean orReplace;
    private boolean force;
    private boolean isTableExpression;

    public CreateView(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setViewName(String string) {
        this.viewName = string;
    }

    public void setSelect(Query query) {
        this.select = query;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setSelectSQL(String string) {
        this.selectSQL = string;
    }

    public void setColumnNames(String[] stringArray) {
        this.columnNames = stringArray;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setOrReplace(boolean bl) {
        this.orReplace = bl;
    }

    public void setForce(boolean bl) {
        this.force = bl;
    }

    public void setTableExpression(boolean bl) {
        this.isTableExpression = bl;
    }

    @Override
    long update(Schema schema) {
        Column[] columnArray;
        String string;
        Database database = this.getDatabase();
        TableView tableView = null;
        Table table = schema.findTableOrView(this.session, this.viewName);
        if (table != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            if (!this.orReplace || TableType.VIEW != table.getTableType()) {
                throw DbException.get(90038, this.viewName);
            }
            tableView = (TableView)table;
        }
        int n = this.getObjectId();
        if (this.select == null) {
            string = this.selectSQL;
        } else {
            columnArray = this.select.getParameters();
            if (columnArray != null && !columnArray.isEmpty()) {
                throw DbException.getUnsupportedException("parameters in views");
            }
            string = this.select.getPlanSQL(0);
        }
        columnArray = null;
        Column[] columnArray2 = null;
        if (this.columnNames != null) {
            columnArray = new Column[this.columnNames.length];
            columnArray2 = new Column[this.columnNames.length];
            for (int i = 0; i < this.columnNames.length; ++i) {
                columnArray[i] = new Column(this.columnNames[i], TypeInfo.TYPE_UNKNOWN);
                columnArray2[i] = new Column(this.columnNames[i], TypeInfo.TYPE_VARCHAR);
            }
        }
        if (tableView == null) {
            tableView = this.isTableExpression ? TableView.createTableViewMaybeRecursive(schema, n, this.viewName, string, null, columnArray2, this.session, false, this.isTableExpression, false, database) : new TableView(schema, n, this.viewName, string, null, columnArray, this.session, false, false, this.isTableExpression, false);
        } else {
            tableView.replace(string, columnArray, this.session, false, this.force, false);
            tableView.setModified();
        }
        if (this.comment != null) {
            tableView.setComment(this.comment);
        }
        if (table == null) {
            database.addSchemaObject(this.session, tableView);
            database.unlockMeta(this.session);
        } else {
            database.updateMeta(this.session, tableView);
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 34;
    }
}

