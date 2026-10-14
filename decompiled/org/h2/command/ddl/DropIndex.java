/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.SchemaCommand;
import org.h2.constraint.Constraint;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public class DropIndex
extends SchemaCommand {
    private String indexName;
    private boolean ifExists;

    public DropIndex(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setIndexName(String string) {
        this.indexName = string;
    }

    @Override
    public long update() {
        Database database = this.getDatabase();
        Index index = this.getSchema().findIndex(this.session, this.indexName);
        if (index == null) {
            if (!this.ifExists) {
                throw DbException.get(42112, this.indexName);
            }
        } else {
            Table table = index.getTable();
            this.session.getUser().checkTableRight(index.getTable(), 32);
            Constraint constraint = null;
            ArrayList<Constraint> arrayList = table.getConstraints();
            for (int i = 0; arrayList != null && i < arrayList.size(); ++i) {
                Constraint constraint2 = arrayList.get(i);
                if (!constraint2.usesIndex(index)) continue;
                if (Constraint.Type.PRIMARY_KEY == constraint2.getConstraintType()) {
                    for (Constraint constraint3 : arrayList) {
                        if (constraint3.getReferencedConstraint() != constraint2) continue;
                        throw DbException.get(90085, this.indexName, constraint2.getName());
                    }
                    constraint = constraint2;
                    continue;
                }
                throw DbException.get(90085, this.indexName, constraint2.getName());
            }
            index.getTable().setModified();
            if (constraint != null) {
                database.removeSchemaObject(this.session, constraint);
            } else {
                database.removeSchemaObject(this.session, index);
            }
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 40;
    }
}

