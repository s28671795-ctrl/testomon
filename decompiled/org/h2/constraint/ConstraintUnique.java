/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.constraint.Constraint;
import org.h2.engine.NullsDistinct;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.util.StringUtils;

public class ConstraintUnique
extends Constraint {
    private Index index;
    private boolean indexOwner;
    private IndexColumn[] columns;
    private final boolean primaryKey;
    private NullsDistinct nullsDistinct;

    public ConstraintUnique(Schema schema, int n, String string, Table table, boolean bl, NullsDistinct nullsDistinct) {
        super(schema, n, string, table);
        this.primaryKey = bl;
        this.nullsDistinct = nullsDistinct;
    }

    @Override
    public Constraint.Type getConstraintType() {
        return this.primaryKey ? Constraint.Type.PRIMARY_KEY : Constraint.Type.UNIQUE;
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        return this.getCreateSQLForCopy(table, string, true);
    }

    private String getCreateSQLForCopy(Table table, String string, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("ALTER TABLE ");
        table.getSQL(stringBuilder, 0).append(" ADD CONSTRAINT ");
        if (table.isHidden()) {
            stringBuilder.append("IF NOT EXISTS ");
        }
        stringBuilder.append(string);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        stringBuilder.append(' ').append(this.getConstraintType().getSqlName()).append('(');
        IndexColumn.writeColumns(stringBuilder, this.columns, 0).append(')');
        if (bl && this.indexOwner && table == this.table) {
            stringBuilder.append(" INDEX ");
            this.index.getSQL(stringBuilder, 0);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCreateSQLWithoutIndexes() {
        return this.getCreateSQLForCopy(this.table, this.getSQL(0), false);
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQLForCopy(this.table, this.getSQL(0));
    }

    public void setColumns(IndexColumn[] indexColumnArray) {
        this.columns = indexColumnArray;
    }

    public IndexColumn[] getColumns() {
        return this.columns;
    }

    public void setIndex(Index index, boolean bl) {
        this.index = index;
        this.indexOwner = bl;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        ArrayList<Constraint> arrayList = this.table.getConstraints();
        if (arrayList != null) {
            arrayList = new ArrayList<Constraint>(this.table.getConstraints());
            for (Constraint constraint : arrayList) {
                if (constraint.getReferencedConstraint() != this) continue;
                this.database.removeSchemaObject(sessionLocal, constraint);
            }
        }
        this.table.removeConstraint(this);
        if (this.indexOwner) {
            this.table.removeIndexOrTransferOwnership(sessionLocal, this.index);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        this.index = null;
        this.columns = null;
        this.table = null;
        this.invalidate();
    }

    @Override
    public void checkRow(SessionLocal sessionLocal, Table table, Row row, Row row2) {
    }

    @Override
    public boolean usesIndex(Index index) {
        return index == this.index;
    }

    @Override
    public void setIndexOwner(Index index) {
        this.indexOwner = true;
    }

    @Override
    public HashSet<Column> getReferencedColumns(Table table) {
        HashSet<Column> hashSet = new HashSet<Column>();
        for (IndexColumn indexColumn : this.columns) {
            hashSet.add(indexColumn.column);
        }
        return hashSet;
    }

    @Override
    public boolean isBefore() {
        return true;
    }

    @Override
    public void checkExistingData(SessionLocal sessionLocal) {
    }

    @Override
    public Index getIndex() {
        return this.index;
    }

    @Override
    public void rebuild() {
    }

    public NullsDistinct getNullsDistinct() {
        return this.nullsDistinct;
    }
}

