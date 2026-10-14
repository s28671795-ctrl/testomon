/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.AlterTable;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.constraint.ConstraintCheck;
import org.h2.constraint.ConstraintReferential;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.Database;
import org.h2.engine.NullsDistinct;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.value.DataType;

public class AlterTableAddConstraint
extends AlterTable {
    private final int type;
    private String constraintName;
    private NullsDistinct nullsDistinct;
    private IndexColumn[] indexColumns;
    private ConstraintActionType deleteAction = ConstraintActionType.RESTRICT;
    private ConstraintActionType updateAction = ConstraintActionType.RESTRICT;
    private Schema refSchema;
    private String refTableName;
    private IndexColumn[] refIndexColumns;
    private Expression checkExpression;
    private Index index;
    private Index refIndex;
    private String comment;
    private boolean checkExisting;
    private boolean primaryKeyHash;
    private final boolean ifNotExists;
    private final ArrayList<Index> createdIndexes = new ArrayList();
    private ConstraintUnique createdUniqueConstraint;

    public AlterTableAddConstraint(SessionLocal sessionLocal, Schema schema, int n, boolean bl) {
        super(sessionLocal, schema);
        this.ifNotExists = bl;
        this.type = n;
    }

    private String generateConstraintName(Table table) {
        if (this.constraintName == null) {
            this.constraintName = this.getSchema().getUniqueConstraintName(this.session, table);
        }
        return this.constraintName;
    }

    @Override
    public long update(Table table) {
        try {
            long l = this.tryUpdate(table);
            return l;
        }
        catch (DbException dbException) {
            try {
                if (this.createdUniqueConstraint != null) {
                    Index index = this.createdUniqueConstraint.getIndex();
                    this.getDatabase().removeSchemaObject(this.session, this.createdUniqueConstraint);
                    this.createdIndexes.remove(index);
                }
                for (Index index : this.createdIndexes) {
                    this.getDatabase().removeSchemaObject(this.session, index);
                }
            }
            catch (Throwable throwable) {
                dbException.addSuppressed(throwable);
            }
            throw dbException;
        }
        finally {
            this.getSchema().freeUniqueName(this.constraintName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int tryUpdate(Table table) {
        Constraint object;
        if (this.constraintName != null && this.getSchema().findConstraint(this.session, this.constraintName) != null) {
            if (this.ifNotExists) {
                return 0;
            }
            if (!this.session.isQuirksMode()) {
                throw DbException.get(90045, this.constraintName);
            }
            this.constraintName = null;
        }
        Database database = this.getDatabase();
        database.lockMeta(this.session);
        table.lock(this.session, 2);
        switch (this.type) {
            case 6: {
                Object object2;
                Object object3;
                IndexColumn.mapColumns(this.indexColumns, table);
                this.index = table.findPrimaryKey();
                ArrayList<Constraint> arrayList = table.getConstraints();
                for (int i = 0; arrayList != null && i < arrayList.size(); ++i) {
                    object3 = arrayList.get(i);
                    if (Constraint.Type.PRIMARY_KEY != ((Constraint)object3).getConstraintType()) continue;
                    throw DbException.get(90017);
                }
                if (this.index != null) {
                    object2 = this.index.getIndexColumns();
                    if (((IndexColumn[])object2).length != this.indexColumns.length) {
                        throw DbException.get(90017);
                    }
                    for (int i = 0; i < ((IndexColumn[])object2).length; ++i) {
                        if (object2[i].column == this.indexColumns[i].column) continue;
                        throw DbException.get(90017);
                    }
                } else {
                    object2 = IndexType.createPrimaryKey(table.isPersistIndexes(), this.primaryKeyHash);
                    object3 = table.getSchema().getUniqueIndexName(this.session, table, "PRIMARY_KEY_");
                    int n = this.getDatabase().allocateObjectId();
                    try {
                        this.index = table.addIndex(this.session, (String)object3, n, this.indexColumns, this.indexColumns.length, (IndexType)object2, true, null);
                    }
                    finally {
                        this.getSchema().freeUniqueName((String)object3);
                    }
                }
                this.index.getIndexType().setBelongsToConstraint(true);
                int n = this.getObjectId();
                String string = this.generateConstraintName(table);
                ConstraintUnique constraintUnique = new ConstraintUnique(this.getSchema(), n, string, table, true, null);
                constraintUnique.setColumns(this.indexColumns);
                constraintUnique.setIndex(this.index, true);
                object = constraintUnique;
                break;
            }
            case 4: {
                if (this.indexColumns == null) {
                    Column[] columnArray = table.getColumns();
                    int n = columnArray.length;
                    ArrayList<IndexColumn> arrayList = new ArrayList<IndexColumn>(n);
                    for (int i = 0; i < n; ++i) {
                        Column column = columnArray[i];
                        if (!column.getVisible()) continue;
                        IndexColumn indexColumn = new IndexColumn(column.getName());
                        indexColumn.column = column;
                        arrayList.add(indexColumn);
                    }
                    if (arrayList.isEmpty()) {
                        throw DbException.get(42000, "UNIQUE(VALUE) on table without columns");
                    }
                    this.indexColumns = arrayList.toArray(new IndexColumn[0]);
                } else {
                    IndexColumn.mapColumns(this.indexColumns, table);
                }
                object = this.createUniqueConstraint(table, this.index, this.indexColumns, false);
                break;
            }
            case 3: {
                int n = this.getObjectId();
                String string = this.generateConstraintName(table);
                ConstraintCheck constraintCheck = new ConstraintCheck(this.getSchema(), n, string, table);
                TableFilter tableFilter = new TableFilter(this.session, table, null, false, null, 0, null);
                this.checkExpression.mapColumns(tableFilter, 0, 0);
                this.checkExpression = this.checkExpression.optimize(this.session);
                constraintCheck.setExpression(this.checkExpression);
                constraintCheck.setTableFilter(tableFilter);
                object = constraintCheck;
                if (!this.checkExisting) break;
                constraintCheck.checkExistingData(this.session);
                break;
            }
            case 5: {
                Table table2 = this.refSchema.resolveTableOrView(this.session, this.refTableName);
                if (table2 == null) {
                    throw DbException.get(42102, this.refTableName);
                }
                if (table2 != table) {
                    this.session.getUser().checkTableRight(table2, 32);
                }
                if (!table2.canReference()) {
                    StringBuilder stringBuilder = new StringBuilder("Reference ");
                    table2.getSQL(stringBuilder, 3);
                    throw DbException.getUnsupportedException(stringBuilder.toString());
                }
                boolean bl = false;
                IndexColumn.mapColumns(this.indexColumns, table);
                if (this.refIndexColumns == null) {
                    this.refIndexColumns = table2.getPrimaryKey().getIndexColumns();
                } else {
                    IndexColumn.mapColumns(this.refIndexColumns, table2);
                }
                int n = this.indexColumns.length;
                if (this.refIndexColumns.length != n) {
                    throw DbException.get(21002);
                }
                for (IndexColumn object42 : this.indexColumns) {
                    Column column = object42.column;
                    if (!column.isGeneratedAlways()) continue;
                    switch (this.deleteAction) {
                        case SET_DEFAULT: 
                        case SET_NULL: {
                            throw DbException.get(90155, column.getSQLWithTable(new StringBuilder(), 3).toString(), "ON DELETE " + this.deleteAction.getSqlName());
                        }
                    }
                    switch (this.updateAction) {
                        case SET_DEFAULT: 
                        case SET_NULL: 
                        case CASCADE: {
                            throw DbException.get(90155, column.getSQLWithTable(new StringBuilder(), 3).toString(), "ON UPDATE " + this.updateAction.getSqlName());
                        }
                    }
                }
                for (int i = 0; i < n; ++i) {
                    Column column = this.indexColumns[i].column;
                    Column column2 = this.refIndexColumns[i].column;
                    if (DataType.areStableComparable(column.getType(), column2.getType())) continue;
                    throw DbException.get(90153, column.getCreateSQL(), column2.getCreateSQL());
                }
                ConstraintUnique constraintUnique = AlterTableAddConstraint.getUniqueConstraint(table2, this.refIndexColumns);
                if (constraintUnique == null && !this.session.isQuirksMode() && !this.session.getMode().createUniqueConstraintForReferencedColumns) {
                    throw DbException.get(90057, IndexColumn.writeColumns(new StringBuilder("PRIMARY KEY | UNIQUE ("), this.refIndexColumns, 3).append(')').toString());
                }
                if (this.index != null && AlterTableAddConstraint.canUseIndex(this.index, table, this.indexColumns, null)) {
                    bl = true;
                    this.index.getIndexType().setBelongsToConstraint(true);
                } else {
                    this.index = AlterTableAddConstraint.getIndex(table, this.indexColumns, null);
                    if (this.index == null) {
                        this.index = this.createIndex(table, this.indexColumns, null);
                        bl = true;
                    }
                }
                int n2 = this.getObjectId();
                String string = this.generateConstraintName(table);
                ConstraintReferential constraintReferential = new ConstraintReferential(this.getSchema(), n2, string, table);
                constraintReferential.setColumns(this.indexColumns);
                constraintReferential.setIndex(this.index, bl);
                constraintReferential.setRefTable(table2);
                constraintReferential.setRefColumns(this.refIndexColumns);
                if (constraintUnique == null) {
                    constraintUnique = this.createUniqueConstraint(table2, this.refIndex, this.refIndexColumns, true);
                    this.addConstraintToTable(database, table2, constraintUnique);
                    this.createdUniqueConstraint = constraintUnique;
                }
                constraintReferential.setRefConstraint(constraintUnique);
                if (this.checkExisting) {
                    constraintReferential.checkExistingData(this.session);
                }
                table2.addConstraint(constraintReferential);
                constraintReferential.setDeleteAction(this.deleteAction);
                constraintReferential.setUpdateAction(this.updateAction);
                object = constraintReferential;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        object.setComment(this.comment);
        this.addConstraintToTable(database, table, object);
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ConstraintUnique createUniqueConstraint(Table table, Index index, IndexColumn[] indexColumnArray, boolean bl) {
        String string;
        int n;
        NullsDistinct nullsDistinct;
        boolean bl2 = false;
        NullsDistinct nullsDistinct2 = nullsDistinct = this.nullsDistinct != null ? this.nullsDistinct : NullsDistinct.DISTINCT;
        if (index != null && AlterTableAddConstraint.canUseIndex(index, table, indexColumnArray, nullsDistinct)) {
            bl2 = true;
            index.getIndexType().setBelongsToConstraint(true);
        } else {
            index = AlterTableAddConstraint.getIndex(table, indexColumnArray, nullsDistinct);
            if (index == null) {
                index = this.createIndex(table, indexColumnArray, this.nullsDistinct != null ? this.nullsDistinct : this.session.getMode().nullsDistinct);
                bl2 = true;
            }
        }
        Schema schema = table.getSchema();
        if (bl) {
            n = this.getDatabase().allocateObjectId();
            try {
                schema.reserveUniqueName(this.constraintName);
                string = schema.getUniqueConstraintName(this.session, table);
            }
            finally {
                schema.freeUniqueName(this.constraintName);
            }
        } else {
            n = this.getObjectId();
            string = this.generateConstraintName(table);
        }
        if (indexColumnArray.length == 1 && nullsDistinct == NullsDistinct.ALL_DISTINCT) {
            nullsDistinct = NullsDistinct.DISTINCT;
        }
        ConstraintUnique constraintUnique = new ConstraintUnique(schema, n, string, table, false, nullsDistinct);
        constraintUnique.setColumns(indexColumnArray);
        constraintUnique.setIndex(index, bl2);
        return constraintUnique;
    }

    private void addConstraintToTable(Database database, Table table, Constraint constraint) {
        if (table.isTemporary() && !table.isGlobalTemporary()) {
            this.session.addLocalTempTableConstraint(constraint);
        } else {
            database.addSchemaObject(this.session, constraint);
        }
        table.addConstraint(constraint);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Index createIndex(Table table, IndexColumn[] indexColumnArray, NullsDistinct nullsDistinct) {
        int n = this.getDatabase().allocateObjectId();
        IndexType indexType = nullsDistinct != null ? IndexType.createUnique(table.isPersistIndexes(), false, indexColumnArray.length, nullsDistinct) : IndexType.createNonUnique(table.isPersistIndexes());
        indexType.setBelongsToConstraint(true);
        String string = this.constraintName == null ? "CONSTRAINT" : this.constraintName;
        String string2 = table.getSchema().getUniqueIndexName(this.session, table, string + "_INDEX_");
        try {
            Index index = table.addIndex(this.session, string2, n, indexColumnArray, nullsDistinct != null ? indexColumnArray.length : 0, indexType, true, null);
            this.createdIndexes.add(index);
            Index index2 = index;
            return index2;
        }
        finally {
            this.getSchema().freeUniqueName(string2);
        }
    }

    public void setDeleteAction(ConstraintActionType constraintActionType) {
        this.deleteAction = constraintActionType;
    }

    public void setUpdateAction(ConstraintActionType constraintActionType) {
        this.updateAction = constraintActionType;
    }

    private static ConstraintUnique getUniqueConstraint(Table table, IndexColumn[] indexColumnArray) {
        ArrayList<Constraint> arrayList = table.getConstraints();
        if (arrayList != null) {
            for (Constraint constraint : arrayList) {
                Constraint.Type type;
                if (constraint.getTable() != table || (type = constraint.getConstraintType()) != Constraint.Type.PRIMARY_KEY && type != Constraint.Type.UNIQUE || !AlterTableAddConstraint.canUseIndex(constraint.getIndex(), table, indexColumnArray, NullsDistinct.DISTINCT)) continue;
                return (ConstraintUnique)constraint;
            }
        }
        return null;
    }

    private static Index getIndex(Table table, IndexColumn[] indexColumnArray, NullsDistinct nullsDistinct) {
        ArrayList<Index> arrayList = table.getIndexes();
        Index index = null;
        if (arrayList != null) {
            for (Index index2 : arrayList) {
                if (!AlterTableAddConstraint.canUseIndex(index2, table, indexColumnArray, nullsDistinct) || index != null && index2.getIndexColumns().length >= index.getIndexColumns().length) continue;
                index = index2;
            }
        }
        return index;
    }

    private static boolean canUseIndex(Index index, Table table, IndexColumn[] indexColumnArray, NullsDistinct nullsDistinct) {
        int n;
        if (index.getTable() != table) {
            return false;
        }
        if (nullsDistinct != null) {
            n = index.getUniqueColumnCount();
            if (n != indexColumnArray.length) {
                return false;
            }
            if (index.getIndexType().getEffectiveNullsDistinct().compareTo(nullsDistinct) < 0) {
                return false;
            }
        } else if (index.getCreateSQL() == null || (n = index.getColumns().length) != indexColumnArray.length) {
            return false;
        }
        for (IndexColumn indexColumn : indexColumnArray) {
            int n2 = index.getColumnIndex(indexColumn.column);
            if (n2 >= 0 && n2 < n) continue;
            return false;
        }
        return true;
    }

    public void setConstraintName(String string) {
        this.constraintName = string;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setNullsDistinct(NullsDistinct nullsDistinct) {
        this.nullsDistinct = nullsDistinct;
    }

    public void setCheckExpression(Expression expression) {
        this.checkExpression = expression;
    }

    public void setIndexColumns(IndexColumn[] indexColumnArray) {
        this.indexColumns = indexColumnArray;
    }

    public IndexColumn[] getIndexColumns() {
        return this.indexColumns;
    }

    public void setRefTableName(Schema schema, String string) {
        this.refSchema = schema;
        this.refTableName = string;
    }

    public void setRefIndexColumns(IndexColumn[] indexColumnArray) {
        this.refIndexColumns = indexColumnArray;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public void setRefIndex(Index index) {
        this.refIndex = index;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setCheckExisting(boolean bl) {
        this.checkExisting = bl;
    }

    public void setPrimaryKeyHash(boolean bl) {
        this.primaryKeyHash = bl;
    }
}

