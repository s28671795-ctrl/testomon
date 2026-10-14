/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import org.h2.command.ddl.DefineCommand;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.MaterializedView;
import org.h2.table.Table;
import org.h2.table.TableView;
import org.h2.util.Utils;

public class DropTable
extends DefineCommand {
    private boolean ifExists;
    private ConstraintActionType dropAction;
    private final ArrayList<SchemaAndTable> tables = Utils.newSmallArrayList();

    public DropTable(SessionLocal sessionLocal) {
        super(sessionLocal);
        this.dropAction = this.getDatabase().getSettings().dropRestrict ? ConstraintActionType.RESTRICT : ConstraintActionType.CASCADE;
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void addTable(Schema schema, String string) {
        this.tables.add(new SchemaAndTable(schema, string));
    }

    private boolean prepareDrop() {
        CopyOnWriteArrayList<TableView> copyOnWriteArrayList;
        Object object;
        HashSet<Object> hashSet = new HashSet<Object>();
        for (SchemaAndTable object2 : this.tables) {
            object = object2.tableName;
            copyOnWriteArrayList = object2.schema.findTableOrView(this.session, (String)object);
            if (copyOnWriteArrayList == null) {
                if (this.ifExists) continue;
                throw DbException.get(42102, (String)object);
            }
            this.session.getUser().checkTableRight((Table)((Object)copyOnWriteArrayList), 32);
            if (!((Table)((Object)copyOnWriteArrayList)).canDrop()) {
                throw DbException.get(90118, (String)object);
            }
            hashSet.add(copyOnWriteArrayList);
        }
        if (hashSet.isEmpty()) {
            return false;
        }
        for (Table table : hashSet) {
            object = new ArrayList();
            if (this.dropAction == ConstraintActionType.RESTRICT) {
                Object object2;
                Object object3;
                Object object4;
                copyOnWriteArrayList = table.getDependentViews();
                if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty()) {
                    object4 = copyOnWriteArrayList.iterator();
                    while (object4.hasNext()) {
                        object3 = (TableView)object4.next();
                        if (hashSet.contains(object3)) continue;
                        ((ArrayList)object).add(((DbObject)object3).getName());
                    }
                }
                if ((object4 = table.getDependentMaterializedViews()) != null && !((CopyOnWriteArrayList)object4).isEmpty()) {
                    object3 = ((CopyOnWriteArrayList)object4).iterator();
                    while (object3.hasNext()) {
                        object2 = (MaterializedView)object3.next();
                        if (hashSet.contains(object2)) continue;
                        ((ArrayList)object).add(((DbObject)object2).getName());
                    }
                }
                if ((object3 = table.getConstraints()) != null && !object3.isEmpty()) {
                    object2 = object3.iterator();
                    while (object2.hasNext()) {
                        Constraint constraint = (Constraint)object2.next();
                        if (hashSet.contains(constraint.getTable())) continue;
                        ((ArrayList)object).add(constraint.getName());
                    }
                }
                if (!((ArrayList)object).isEmpty()) {
                    throw DbException.get(90107, table.getName(), String.join((CharSequence)", ", new HashSet(object)));
                }
            }
            table.lock(this.session, 2);
        }
        return true;
    }

    private void executeDrop() {
        for (SchemaAndTable schemaAndTable : this.tables) {
            Table table = schemaAndTable.schema.findTableOrView(this.session, schemaAndTable.tableName);
            if (table == null) continue;
            table.setModified();
            Database database = this.getDatabase();
            database.lockMeta(this.session);
            database.removeSchemaObject(this.session, table);
        }
    }

    @Override
    public long update() {
        if (this.prepareDrop()) {
            this.executeDrop();
        }
        return 0L;
    }

    public void setDropAction(ConstraintActionType constraintActionType) {
        this.dropAction = constraintActionType;
    }

    @Override
    public int getType() {
        return 44;
    }

    private static final class SchemaAndTable {
        final Schema schema;
        final String tableName;

        SchemaAndTable(Schema schema, String string) {
            this.schema = schema;
            this.tableName = string;
        }
    }
}

