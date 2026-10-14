/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.h2.command.ddl.CommandWithColumns;
import org.h2.command.ddl.CreateTableData;
import org.h2.command.dml.Insert;
import org.h2.command.query.Query;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.table.Column;
import org.h2.table.Table;

public class CreateTable
extends CommandWithColumns {
    private final CreateTableData data = new CreateTableData();
    private boolean ifNotExists;
    private boolean onCommitDrop;
    private boolean onCommitTruncate;
    private Query asQuery;
    private String comment;
    private boolean withNoData;

    public CreateTable(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
        this.data.persistIndexes = true;
        this.data.persistData = true;
    }

    public void setQuery(Query query) {
        this.asQuery = query;
    }

    public void setTemporary(boolean bl) {
        this.data.temporary = bl;
    }

    public void setTableName(String string) {
        this.data.tableName = string;
    }

    @Override
    public void addColumn(Column column) {
        this.data.columns.add(column);
    }

    public ArrayList<Column> getColumns() {
        return this.data.columns;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    @Override
    public long update() {
        Object object;
        Object object2;
        Schema schema = this.getSchema();
        boolean bl = this.data.temporary && !this.data.globalTemporary;
        Database database = this.getDatabase();
        String string = this.data.tableEngine;
        if (string != null || database.getSettings().defaultTableEngine != null) {
            this.session.getUser().checkAdmin();
        } else if (!bl) {
            this.session.getUser().checkSchemaOwner(schema);
        }
        if (!database.isPersistent()) {
            this.data.persistIndexes = false;
        }
        if (!bl) {
            database.lockMeta(this.session);
        }
        if (schema.resolveTableOrView(this.session, this.data.tableName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(42101, this.data.tableName);
        }
        if (this.asQuery != null) {
            this.asQuery.prepare();
            if (this.data.columns.isEmpty()) {
                this.generateColumnsFromQuery();
            } else {
                if (this.data.columns.size() != this.asQuery.getColumnCount()) {
                    throw DbException.get(21002);
                }
                object2 = this.data.columns;
                for (int i = 0; i < ((ArrayList)object2).size(); ++i) {
                    object = (Column)((ArrayList)object2).get(i);
                    if (((Column)object).getType().getValueType() != -1) continue;
                    ((ArrayList)object2).set(i, new Column(((Column)object).getName(), this.asQuery.getExpressions().get(i).getType()));
                }
            }
        }
        this.changePrimaryKeysToNotNull(this.data.columns);
        this.data.id = this.getObjectId();
        this.data.session = this.session;
        object2 = schema.createTable(this.data);
        ArrayList<Sequence> arrayList = this.generateSequences(this.data.columns, this.data.temporary);
        ((DbObject)object2).setComment(this.comment);
        if (bl) {
            if (this.onCommitDrop) {
                ((Table)object2).setOnCommitDrop(true);
            }
            if (this.onCommitTruncate) {
                ((Table)object2).setOnCommitTruncate(true);
            }
            this.session.addLocalTempTable((Table)object2);
        } else {
            database.lockMeta(this.session);
            database.addSchemaObject(this.session, (SchemaObject)object2);
        }
        try {
            for (Column object32 : this.data.columns) {
                object32.prepareExpressions(this.session);
            }
            for (Sequence sequence2 : arrayList) {
                ((Table)object2).addSequence(sequence2);
            }
            this.createConstraints();
            object = new HashSet();
            ((Table)object2).addDependencies((HashSet<DbObject>)object);
            Iterator iterator2 = ((HashSet)object).iterator();
            while (iterator2.hasNext()) {
                Table table;
                DbObject dbObject = (DbObject)iterator2.next();
                if (dbObject == object2 || dbObject.getType() != 0 || !(dbObject instanceof Table) || (table = (Table)dbObject).getId() <= ((DbObject)object2).getId()) continue;
                throw DbException.get(50100, "Table depends on another table with a higher ID: " + table + ", this is currently not supported, as it would prevent the database from being re-opened");
            }
            if (this.asQuery != null && !this.withNoData) {
                this.insertAsData(bl, database, (Table)object2);
            }
        }
        catch (DbException dbException) {
            try {
                database.checkPowerOff();
                database.removeSchemaObject(this.session, (SchemaObject)object2);
                if (!this.transactional) {
                    this.session.commit(true);
                }
            }
            catch (Throwable throwable) {
                dbException.addSuppressed(throwable);
            }
            throw dbException;
        }
        return 0L;
    }

    void insertAsData(Table table) {
        this.insertAsData(false, this.getDatabase(), table);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void insertAsData(boolean bl, Database database, Table table) {
        Sequence sequence2;
        boolean bl2 = false;
        if (!bl) {
            database.unlockMeta(this.session);
            for (Column object : table.getColumns()) {
                sequence2 = object.getSequence();
                if (sequence2 == null) continue;
                bl2 = true;
                sequence2.setTemporary(true);
            }
        }
        try {
            this.session.startStatementWithinTransaction(null);
            Insert insert = new Insert(this.session);
            insert.setQuery(this.asQuery);
            insert.setTable(table);
            insert.setInsertFromSelect(true);
            insert.prepare();
            insert.update();
        }
        finally {
            this.session.endStatement();
        }
        if (bl2) {
            database.lockMeta(this.session);
            for (Column column : table.getColumns()) {
                sequence2 = column.getSequence();
                if (sequence2 == null) continue;
                sequence2.setTemporary(false);
                sequence2.flush(this.session);
            }
        }
    }

    private void generateColumnsFromQuery() {
        int n = this.asQuery.getColumnCount();
        ArrayList<Expression> arrayList = this.asQuery.getExpressions();
        for (int i = 0; i < n; ++i) {
            Expression expression = arrayList.get(i);
            this.addColumn(new Column(expression.getColumnNameForView(this.session, i), expression.getType()));
        }
    }

    public void setPersistIndexes(boolean bl) {
        this.data.persistIndexes = bl;
    }

    public void setGlobalTemporary(boolean bl) {
        this.data.globalTemporary = bl;
    }

    public void setOnCommitDrop() {
        this.onCommitDrop = true;
    }

    public void setOnCommitTruncate() {
        this.onCommitTruncate = true;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setPersistData(boolean bl) {
        this.data.persistData = bl;
        if (!bl) {
            this.data.persistIndexes = false;
        }
    }

    public void setWithNoData(boolean bl) {
        this.withNoData = bl;
    }

    public void setTableEngine(String string) {
        this.data.tableEngine = string;
    }

    public void setTableEngineParams(ArrayList<String> arrayList) {
        this.data.tableEngineParams = arrayList;
    }

    public void setHidden(boolean bl) {
        this.data.isHidden = bl;
    }

    @Override
    public int getType() {
        return 30;
    }
}

