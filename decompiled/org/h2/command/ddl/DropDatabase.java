/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import java.util.Collection;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.value.ValueNull;

public class DropDatabase
extends DefineCommand {
    private boolean dropAllObjects;
    private boolean deleteFiles;

    public DropDatabase(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public long update() {
        if (this.dropAllObjects) {
            this.dropAllObjects();
        }
        if (this.deleteFiles) {
            this.getDatabase().setDeleteFilesOnDisconnect(true);
        }
        return 0L;
    }

    private void dropAllObjects() {
        ArrayList<SchemaObject> arrayList;
        Collection<DbObject> collection;
        boolean bl;
        User user = this.session.getUser();
        user.checkAdmin();
        Database database = this.getDatabase();
        database.lockMeta(this.session);
        do {
            collection = database.getAllTablesAndViews();
            arrayList = new ArrayList<Table>(((ArrayList)collection).size());
            for (Table table : collection) {
                if (table.getName() == null || TableType.VIEW != table.getTableType()) continue;
                arrayList.add(table);
            }
            for (Table table : collection) {
                if (table.getName() == null || TableType.TABLE_LINK != table.getTableType()) continue;
                arrayList.add(table);
            }
            for (Table table : collection) {
                if (table.getName() == null || TableType.TABLE != table.getTableType() || table.isHidden()) continue;
                arrayList.add(table);
            }
            for (Table table : collection) {
                if (table.getName() == null || TableType.EXTERNAL_TABLE_ENGINE != table.getTableType() || table.isHidden()) continue;
                arrayList.add(table);
            }
            bl = false;
            for (Table table : arrayList) {
                if (table.getName() == null) continue;
                if (database.getDependentTable(table, table) == null) {
                    database.removeSchemaObject(this.session, table);
                    continue;
                }
                bl = true;
            }
        } while (bl);
        collection = database.getAllSchemasNoMeta();
        for (Schema schema : collection) {
            if (!schema.canDrop()) continue;
            database.removeDatabaseObject(this.session, schema);
        }
        arrayList = new ArrayList<SchemaObject>();
        for (Schema schema : collection) {
            for (Sequence sequence2 : schema.getAllSequences()) {
                if (sequence2.getBelongsToTable()) continue;
                arrayList.add(sequence2);
            }
        }
        DropDatabase.addAll(collection, 5, arrayList);
        DropDatabase.addAll(collection, 4, arrayList);
        DropDatabase.addAll(collection, 11, arrayList);
        DropDatabase.addAll(collection, 9, arrayList);
        DropDatabase.addAll(collection, 12, arrayList);
        for (SchemaObject schemaObject : arrayList) {
            if (!schemaObject.getSchema().isValid() || schemaObject.isHidden()) continue;
            database.removeSchemaObject(this.session, schemaObject);
        }
        Role role = database.getPublicRole();
        for (RightOwner rightOwner : database.getAllUsersAndRoles()) {
            if (rightOwner == user || rightOwner == role) continue;
            database.removeDatabaseObject(this.session, rightOwner);
        }
        for (Right right : database.getAllRights()) {
            database.removeDatabaseObject(this.session, right);
        }
        for (SessionLocal sessionLocal : database.getSessions(false)) {
            sessionLocal.setLastIdentity(ValueNull.INSTANCE);
        }
    }

    private static void addAll(Collection<Schema> collection, int n, ArrayList<SchemaObject> arrayList) {
        for (Schema schema : collection) {
            schema.getAll(n, arrayList);
        }
    }

    public void setDropAllObjects(boolean bl) {
        this.dropAllObjects = bl;
    }

    public void setDeleteFiles(boolean bl) {
        this.deleteFiles = bl;
    }

    @Override
    public int getType() {
        return 38;
    }
}

