/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.command.Prepared;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Parameter;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.util.HasSQL;
import org.h2.util.StringUtils;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public class ConstraintReferential
extends Constraint {
    private IndexColumn[] columns;
    private IndexColumn[] refColumns;
    private ConstraintActionType deleteAction = ConstraintActionType.RESTRICT;
    private ConstraintActionType updateAction = ConstraintActionType.RESTRICT;
    private Table refTable;
    private Index index;
    private ConstraintUnique refConstraint;
    private boolean indexOwner;
    private String deleteSQL;
    private String updateSQL;
    private boolean skipOwnTable;

    public ConstraintReferential(Schema schema, int n, String string, Table table) {
        super(schema, n, string, table);
    }

    @Override
    public Constraint.Type getConstraintType() {
        return Constraint.Type.REFERENTIAL;
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        return this.getCreateSQLForCopy(table, this.refTable, string, true);
    }

    public String getCreateSQLForCopy(Table table, Table table2, String string, boolean bl) {
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
        IndexColumn[] indexColumnArray = this.columns;
        IndexColumn[] indexColumnArray2 = this.refColumns;
        stringBuilder.append(" FOREIGN KEY(");
        IndexColumn.writeColumns(stringBuilder, indexColumnArray, 0);
        stringBuilder.append(')');
        if (bl && this.indexOwner && table == this.table) {
            stringBuilder.append(" INDEX ");
            this.index.getSQL(stringBuilder, 0);
        }
        stringBuilder.append(" REFERENCES ");
        if (this.table == this.refTable) {
            table.getSQL(stringBuilder, 0);
        } else {
            table2.getSQL(stringBuilder, 0);
        }
        stringBuilder.append('(');
        IndexColumn.writeColumns(stringBuilder, indexColumnArray2, 0);
        stringBuilder.append(')');
        if (this.deleteAction != ConstraintActionType.RESTRICT) {
            stringBuilder.append(" ON DELETE ").append(this.deleteAction.getSqlName());
        }
        if (this.updateAction != ConstraintActionType.RESTRICT) {
            stringBuilder.append(" ON UPDATE ").append(this.updateAction.getSqlName());
        }
        return stringBuilder.append(" NOCHECK").toString();
    }

    private String getShortDescription(Index index, SearchRow searchRow) {
        StringBuilder stringBuilder = new StringBuilder(this.getName()).append(": ");
        this.table.getSQL(stringBuilder, 3).append(" FOREIGN KEY(");
        IndexColumn.writeColumns(stringBuilder, this.columns, 3);
        stringBuilder.append(") REFERENCES ");
        this.refTable.getSQL(stringBuilder, 3).append('(');
        IndexColumn.writeColumns(stringBuilder, this.refColumns, 3);
        stringBuilder.append(')');
        if (index != null && searchRow != null) {
            stringBuilder.append(" (");
            Column[] columnArray = index.getColumns();
            int n = Math.min(this.columns.length, columnArray.length);
            for (int i = 0; i < n; ++i) {
                int n2 = columnArray[i].getColumnId();
                Value value = searchRow.getValue(n2);
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(value == null ? "" : value.toString());
            }
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }

    @Override
    public String getCreateSQLWithoutIndexes() {
        return this.getCreateSQLForCopy(this.table, this.refTable, this.getSQL(0), false);
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

    @Override
    public HashSet<Column> getReferencedColumns(Table table) {
        HashSet<Column> hashSet;
        block3: {
            block2: {
                hashSet = new HashSet<Column>();
                if (table != this.table) break block2;
                for (IndexColumn indexColumn : this.columns) {
                    hashSet.add(indexColumn.column);
                }
                break block3;
            }
            if (table != this.refTable) break block3;
            for (IndexColumn indexColumn : this.refColumns) {
                hashSet.add(indexColumn.column);
            }
        }
        return hashSet;
    }

    public void setRefColumns(IndexColumn[] indexColumnArray) {
        this.refColumns = indexColumnArray;
    }

    public IndexColumn[] getRefColumns() {
        return this.refColumns;
    }

    public void setRefTable(Table table) {
        this.refTable = table;
        if (table.isTemporary()) {
            this.setTemporary(true);
        }
    }

    public void setIndex(Index index, boolean bl) {
        this.index = index;
        this.indexOwner = bl;
    }

    public void setRefConstraint(ConstraintUnique constraintUnique) {
        this.refConstraint = constraintUnique;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.table.removeConstraint(this);
        this.refTable.removeConstraint(this);
        if (this.indexOwner) {
            this.table.removeIndexOrTransferOwnership(sessionLocal, this.index);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        this.refTable = null;
        this.index = null;
        this.refConstraint = null;
        this.columns = null;
        this.refColumns = null;
        this.deleteSQL = null;
        this.updateSQL = null;
        this.table = null;
        this.invalidate();
    }

    @Override
    public void checkRow(SessionLocal sessionLocal, Table table, Row row, Row row2) {
        if (!this.database.getReferentialIntegrity()) {
            return;
        }
        if (!this.table.getCheckForeignKeyConstraints() || !this.refTable.getCheckForeignKeyConstraints()) {
            return;
        }
        if (table == this.table && !this.skipOwnTable) {
            this.checkRowOwnTable(sessionLocal, row, row2);
        }
        if (table == this.refTable) {
            this.checkRowRefTable(sessionLocal, row, row2);
        }
    }

    private void checkRowOwnTable(SessionLocal sessionLocal, Row row, Row row2) {
        int n;
        int n2;
        int n3;
        HasSQL hasSQL;
        if (row2 == null) {
            return;
        }
        boolean bl = row != null;
        for (IndexColumn indexColumn : this.columns) {
            int n4 = indexColumn.column.getColumnId();
            hasSQL = row2.getValue(n4);
            if (hasSQL == ValueNull.INSTANCE) {
                return;
            }
            if (!bl || sessionLocal.areEqual((Value)hasSQL, row.getValue(n4))) continue;
            bl = false;
        }
        if (bl) {
            return;
        }
        if (this.refTable == this.table) {
            boolean bl2 = true;
            n3 = this.columns.length;
            for (n2 = 0; n2 < n3; ++n2) {
                int n5 = this.columns[n2].column.getColumnId();
                Value value = row2.getValue(n5);
                hasSQL = this.refColumns[n2].column;
                n = ((Column)hasSQL).getColumnId();
                Value value2 = row2.getValue(n);
                if (sessionLocal.areEqual(value2, value)) continue;
                bl2 = false;
                break;
            }
            if (bl2) {
                return;
            }
        }
        Row row3 = this.refTable.getTemplateRow();
        n3 = this.columns.length;
        for (n2 = 0; n2 < n3; ++n2) {
            int n6 = this.columns[n2].column.getColumnId();
            Value value = row2.getValue(n6);
            hasSQL = this.refColumns[n2].column;
            n = ((Column)hasSQL).getColumnId();
            row3.setValue(n, ((Column)hasSQL).convert(sessionLocal, value));
        }
        Index index = this.refConstraint.getIndex();
        if (!this.existsRow(sessionLocal, index, row3, null)) {
            throw DbException.get(23506, this.getShortDescription(index, row3));
        }
    }

    private boolean existsRow(SessionLocal sessionLocal, Index index, SearchRow searchRow, Row row) {
        Table table = index.getTable();
        table.lock(sessionLocal, 0);
        Cursor cursor = index.find(sessionLocal, searchRow, searchRow);
        while (cursor.next()) {
            SearchRow searchRow2 = cursor.getSearchRow();
            if (row != null && searchRow2.getKey() == row.getKey()) continue;
            Column[] columnArray = index.getColumns();
            boolean bl = true;
            int n = Math.min(this.columns.length, columnArray.length);
            for (int i = 0; i < n; ++i) {
                Value value;
                int n2 = columnArray[i].getColumnId();
                Value value2 = searchRow.getValue(n2);
                if (table.compareValues(sessionLocal, value2, value = searchRow2.getValue(n2)) == 0) continue;
                bl = false;
                break;
            }
            if (!bl) continue;
            return true;
        }
        return false;
    }

    private boolean isEqual(Row row, Row row2) {
        return this.refConstraint.getIndex().compareRows(row, row2) == 0;
    }

    private void checkRow(SessionLocal sessionLocal, Row row) {
        Row row2;
        SearchRow searchRow = this.table.getRowFactory().createRow();
        int n = this.columns.length;
        for (int i = 0; i < n; ++i) {
            Column column = this.columns[i].column;
            Column column2 = this.refColumns[i].column;
            int n2 = column2.getColumnId();
            Value value = column.convert(sessionLocal, row.getValue(n2));
            if (value == ValueNull.INSTANCE) {
                return;
            }
            searchRow.setValue(column.getColumnId(), value);
        }
        Row row3 = row2 = this.refTable == this.table ? row : null;
        if (this.existsRow(sessionLocal, this.index, searchRow, row2)) {
            throw DbException.get(23503, this.getShortDescription(this.index, searchRow));
        }
    }

    private void checkRowRefTable(SessionLocal sessionLocal, Row row, Row row2) {
        if (row == null) {
            return;
        }
        if (row2 != null && this.isEqual(row, row2)) {
            return;
        }
        if (row2 == null) {
            if (this.deleteAction == ConstraintActionType.RESTRICT) {
                this.checkRow(sessionLocal, row);
            } else {
                int n = this.deleteAction == ConstraintActionType.CASCADE ? 0 : this.columns.length;
                Prepared prepared = this.getDelete(sessionLocal);
                this.setWhere(prepared, n, row);
                this.updateWithSkipCheck(prepared);
            }
        } else if (this.updateAction == ConstraintActionType.RESTRICT) {
            this.checkRow(sessionLocal, row);
        } else {
            Prepared prepared = this.getUpdate(sessionLocal);
            if (this.updateAction == ConstraintActionType.CASCADE) {
                ArrayList<Parameter> arrayList = prepared.getParameters();
                int n = this.columns.length;
                for (int i = 0; i < n; ++i) {
                    Parameter parameter = arrayList.get(i);
                    Column column = this.refColumns[i].column;
                    parameter.setValue(row2.getValue(column.getColumnId()));
                }
            }
            this.setWhere(prepared, this.columns.length, row);
            this.updateWithSkipCheck(prepared);
        }
    }

    private void updateWithSkipCheck(Prepared prepared) {
        try {
            this.skipOwnTable = true;
            prepared.update();
        }
        finally {
            this.skipOwnTable = false;
        }
    }

    private void setWhere(Prepared prepared, int n, Row row) {
        int n2 = this.refColumns.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = this.refColumns[i].column.getColumnId();
            Value value = row.getValue(n3);
            ArrayList<Parameter> arrayList = prepared.getParameters();
            Parameter parameter = arrayList.get(n + i);
            parameter.setValue(value);
        }
    }

    public ConstraintActionType getDeleteAction() {
        return this.deleteAction;
    }

    public void setDeleteAction(ConstraintActionType constraintActionType) {
        if (constraintActionType == this.deleteAction && this.deleteSQL == null) {
            return;
        }
        if (this.deleteAction != ConstraintActionType.RESTRICT) {
            throw DbException.get(90045, "ON DELETE");
        }
        this.deleteAction = constraintActionType;
        this.buildDeleteSQL();
    }

    public void updateOnTableColumnRename() {
        if (this.deleteAction != null) {
            this.deleteSQL = null;
            this.buildDeleteSQL();
        }
        if (this.updateAction != null) {
            this.updateSQL = null;
            this.buildUpdateSQL();
        }
    }

    private void buildDeleteSQL() {
        if (this.deleteAction == ConstraintActionType.RESTRICT) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (this.deleteAction == ConstraintActionType.CASCADE) {
            stringBuilder.append("DELETE FROM ");
            this.table.getSQL(stringBuilder, 0);
        } else {
            this.appendUpdate(stringBuilder);
        }
        this.appendWhere(stringBuilder);
        this.deleteSQL = stringBuilder.toString();
    }

    private Prepared getUpdate(SessionLocal sessionLocal) {
        return this.prepare(sessionLocal, this.updateSQL, this.updateAction);
    }

    private Prepared getDelete(SessionLocal sessionLocal) {
        return this.prepare(sessionLocal, this.deleteSQL, this.deleteAction);
    }

    public ConstraintActionType getUpdateAction() {
        return this.updateAction;
    }

    public void setUpdateAction(ConstraintActionType constraintActionType) {
        if (constraintActionType == this.updateAction && this.updateSQL == null) {
            return;
        }
        if (this.updateAction != ConstraintActionType.RESTRICT) {
            throw DbException.get(90045, "ON UPDATE");
        }
        this.updateAction = constraintActionType;
        this.buildUpdateSQL();
    }

    private void buildUpdateSQL() {
        if (this.updateAction == ConstraintActionType.RESTRICT) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.appendUpdate(stringBuilder);
        this.appendWhere(stringBuilder);
        this.updateSQL = stringBuilder.toString();
    }

    @Override
    public void rebuild() {
        this.buildUpdateSQL();
        this.buildDeleteSQL();
    }

    private Prepared prepare(SessionLocal sessionLocal, String string, ConstraintActionType constraintActionType) {
        Prepared prepared = sessionLocal.prepare(string);
        if (constraintActionType != ConstraintActionType.CASCADE) {
            ArrayList<Parameter> arrayList = prepared.getParameters();
            int n = this.columns.length;
            for (int i = 0; i < n; ++i) {
                Value value;
                Column column = this.columns[i].column;
                Parameter parameter = arrayList.get(i);
                if (constraintActionType == ConstraintActionType.SET_NULL) {
                    value = ValueNull.INSTANCE;
                } else {
                    Expression expression = column.getEffectiveDefaultExpression();
                    if (expression == null) {
                        throw DbException.get(23507, column.getName());
                    }
                    value = expression.getValue(sessionLocal);
                }
                parameter.setValue(value);
            }
        }
        return prepared;
    }

    private void appendUpdate(StringBuilder stringBuilder) {
        stringBuilder.append("UPDATE ");
        this.table.getSQL(stringBuilder, 0).append(" SET ");
        IndexColumn.writeColumns(stringBuilder, this.columns, ", ", "=?", Integer.MIN_VALUE);
    }

    private void appendWhere(StringBuilder stringBuilder) {
        stringBuilder.append(" WHERE ");
        IndexColumn.writeColumns(stringBuilder, this.columns, " AND ", "=?", Integer.MIN_VALUE);
    }

    @Override
    public Table getRefTable() {
        return this.refTable;
    }

    @Override
    public boolean usesIndex(Index index) {
        return index == this.index;
    }

    @Override
    public void setIndexOwner(Index index) {
        if (this.index != index) {
            throw DbException.getInternalError(index + " " + this.toString());
        }
        this.indexOwner = true;
    }

    @Override
    public boolean isBefore() {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void checkExistingData(SessionLocal sessionLocal) {
        if (sessionLocal.getDatabase().isStarting()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("SELECT 1 FROM (SELECT ");
        IndexColumn.writeColumns(stringBuilder, this.columns, Integer.MIN_VALUE);
        stringBuilder.append(" FROM ");
        this.table.getSQL(stringBuilder, 0).append(" WHERE ");
        IndexColumn.writeColumns(stringBuilder, this.columns, " AND ", " IS NOT NULL ", Integer.MIN_VALUE);
        stringBuilder.append(" ORDER BY ");
        IndexColumn.writeColumns(stringBuilder, this.columns, 0);
        stringBuilder.append(") C WHERE NOT EXISTS(SELECT 1 FROM ");
        this.refTable.getSQL(stringBuilder, 0).append(" P WHERE ");
        int n = this.columns.length;
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append(" AND ");
            }
            stringBuilder.append("C.");
            this.columns[i].column.getSQL(stringBuilder, 0).append('=').append("P.");
            this.refColumns[i].column.getSQL(stringBuilder, 0);
        }
        stringBuilder.append(')');
        sessionLocal.startStatementWithinTransaction(null);
        try {
            ResultInterface resultInterface = sessionLocal.prepare(stringBuilder.toString()).query(1L);
            if (resultInterface.next()) {
                throw DbException.get(23506, this.getShortDescription(null, null));
            }
        }
        finally {
            sessionLocal.endStatement();
        }
    }

    @Override
    public Index getIndex() {
        return this.index;
    }

    @Override
    public ConstraintUnique getReferencedConstraint() {
        return this.refConstraint;
    }
}

