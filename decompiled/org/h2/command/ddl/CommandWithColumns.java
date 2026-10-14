/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.AlterTableAddConstraint;
import org.h2.command.ddl.CreateIndex;
import org.h2.command.ddl.DefineCommand;
import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;
import org.h2.table.Column;
import org.h2.table.IndexColumn;

public abstract class CommandWithColumns
extends SchemaCommand {
    private ArrayList<DefineCommand> constraintCommands;
    private AlterTableAddConstraint primaryKey;

    protected CommandWithColumns(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public abstract void addColumn(Column var1);

    public void addConstraintCommand(DefineCommand defineCommand) {
        AlterTableAddConstraint alterTableAddConstraint;
        if (!(defineCommand instanceof CreateIndex) && (alterTableAddConstraint = (AlterTableAddConstraint)defineCommand).getType() == 6 && this.setPrimaryKey(alterTableAddConstraint)) {
            return;
        }
        this.getConstraintCommands().add(defineCommand);
    }

    protected void changePrimaryKeysToNotNull(ArrayList<Column> arrayList) {
        if (this.primaryKey != null) {
            IndexColumn[] indexColumnArray = this.primaryKey.getIndexColumns();
            for (Column column : arrayList) {
                for (IndexColumn indexColumn : indexColumnArray) {
                    if (!column.getName().equals(indexColumn.columnName)) continue;
                    column.setNullable(false);
                }
            }
        }
    }

    protected void createConstraints() {
        if (this.constraintCommands != null) {
            for (DefineCommand defineCommand : this.constraintCommands) {
                defineCommand.setTransactional(this.transactional);
                defineCommand.update();
            }
        }
    }

    protected ArrayList<Sequence> generateSequences(ArrayList<Column> arrayList, boolean bl) {
        ArrayList<Sequence> arrayList2 = new ArrayList<Sequence>(arrayList == null ? 0 : arrayList.size());
        if (arrayList != null) {
            for (Column column : arrayList) {
                Sequence sequence2;
                if (column.hasIdentityOptions()) {
                    int n = this.getDatabase().allocateObjectId();
                    column.initializeSequence(this.session, this.getSchema(), n, bl);
                    if (!"''".equals(this.getDatabase().getCluster())) {
                        throw DbException.getUnsupportedException("CLUSTERING && identity columns");
                    }
                }
                if ((sequence2 = column.getSequence()) == null) continue;
                arrayList2.add(sequence2);
            }
        }
        return arrayList2;
    }

    private ArrayList<DefineCommand> getConstraintCommands() {
        if (this.constraintCommands == null) {
            this.constraintCommands = new ArrayList();
        }
        return this.constraintCommands;
    }

    private boolean setPrimaryKey(AlterTableAddConstraint alterTableAddConstraint) {
        if (this.primaryKey != null) {
            IndexColumn[] indexColumnArray = this.primaryKey.getIndexColumns();
            IndexColumn[] indexColumnArray2 = alterTableAddConstraint.getIndexColumns();
            int n = indexColumnArray2.length;
            if (n != indexColumnArray.length) {
                throw DbException.get(90017);
            }
            for (int i = 0; i < n; ++i) {
                if (indexColumnArray2[i].columnName.equals(indexColumnArray[i].columnName)) continue;
                throw DbException.get(90017);
            }
            if (this.primaryKey.getConstraintName() != null) {
                return true;
            }
            this.constraintCommands.remove(this.primaryKey);
        }
        this.primaryKey = alterTableAddConstraint;
        return false;
    }

    public AlterTableAddConstraint getPrimaryKey() {
        return this.primaryKey;
    }
}

