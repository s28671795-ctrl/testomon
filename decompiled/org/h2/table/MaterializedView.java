/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.command.query.Query;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SortOrder;
import org.h2.schema.Schema;
import org.h2.table.IndexColumn;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.table.TableType;
import org.h2.util.StringUtils;

public class MaterializedView
extends Table {
    private Table table;
    private String querySQL;
    private Query query;

    public MaterializedView(Schema schema, int n, String string, Table table, Query query, String string2) {
        super(schema, n, string, false, true);
        this.table = table;
        this.query = query;
        this.querySQL = string2;
    }

    public void replace(Table table, Query query, String string) {
        this.table = table;
        this.query = query;
        this.querySQL = string;
    }

    public Table getUnderlyingTable() {
        return this.table;
    }

    public Query getSelect() {
        return this.query;
    }

    @Override
    public final void close(SessionLocal sessionLocal) {
        this.table.close(sessionLocal);
    }

    @Override
    public final Index addIndex(SessionLocal sessionLocal, String string, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType, boolean bl, String string2) {
        return this.table.addIndex(sessionLocal, string, n, indexColumnArray, n2, indexType, bl, string2);
    }

    @Override
    public final boolean isView() {
        return true;
    }

    @Override
    public final PlanItem getBestPlanItem(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return this.table.getBestPlanItem(sessionLocal, nArray, tableFilterArray, n, sortOrder, allColumnsForPlan);
    }

    @Override
    public boolean isQueryComparable() {
        return this.table.isQueryComparable();
    }

    @Override
    public final boolean isInsertable() {
        return false;
    }

    @Override
    public final void removeRow(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException(this.getClass().getSimpleName() + ".removeRow");
    }

    @Override
    public final void addRow(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException(this.getClass().getSimpleName() + ".addRow");
    }

    @Override
    public final void checkSupportAlter() {
        throw DbException.getUnsupportedException(this.getClass().getSimpleName() + ".checkSupportAlter");
    }

    @Override
    public final long truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException(this.getClass().getSimpleName() + ".truncate");
    }

    @Override
    public final long getRowCount(SessionLocal sessionLocal) {
        return this.table.getRowCount(sessionLocal);
    }

    @Override
    public final boolean canGetRowCount(SessionLocal sessionLocal) {
        return this.table.canGetRowCount(sessionLocal);
    }

    @Override
    public final long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.table.getRowCountApproximation(sessionLocal);
    }

    @Override
    public final boolean canReference() {
        return false;
    }

    @Override
    public final ArrayList<Index> getIndexes() {
        return this.table.getIndexes();
    }

    @Override
    public final Index getScanIndex(SessionLocal sessionLocal) {
        return this.getBestPlanItem(sessionLocal, null, null, -1, null, null).getIndex();
    }

    @Override
    public Index getScanIndex(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return this.getBestPlanItem(sessionLocal, nArray, tableFilterArray, n, sortOrder, allColumnsForPlan).getIndex();
    }

    @Override
    public boolean isDeterministic() {
        return this.table.isDeterministic();
    }

    @Override
    public final void addDependencies(HashSet<DbObject> hashSet) {
        this.table.addDependencies(hashSet);
    }

    @Override
    public String getDropSQL() {
        return this.getSQL(new StringBuilder("DROP MATERIALIZED VIEW IF EXISTS "), 0).toString();
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        return this.getCreateSQL(false, true, string);
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQL(false, true);
    }

    public String getCreateSQL(boolean bl, boolean bl2) {
        return this.getCreateSQL(bl, bl2, this.getSQL(0));
    }

    private String getCreateSQL(boolean bl, boolean bl2, String string) {
        StringBuilder stringBuilder = new StringBuilder("CREATE ");
        if (bl) {
            stringBuilder.append("OR REPLACE ");
        }
        if (bl2) {
            stringBuilder.append("FORCE ");
        }
        stringBuilder.append("MATERIALIZED VIEW ");
        stringBuilder.append(string);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        return stringBuilder.append(" AS\n").append(this.querySQL).toString();
    }

    @Override
    public boolean canDrop() {
        return true;
    }

    @Override
    public TableType getTableType() {
        return TableType.MATERIALIZED_VIEW;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.table.removeChildrenAndResources(sessionLocal);
        this.database.removeMeta(sessionLocal, this.getId());
        this.querySQL = null;
        this.invalidate();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if (this.isTemporary() && this.querySQL != null) {
            stringBuilder.append("(\n");
            return StringUtils.indent(stringBuilder, this.querySQL, 4, true).append(')');
        }
        return super.getSQL(stringBuilder, n);
    }

    public String getQuerySQL() {
        return this.querySQL;
    }

    @Override
    public long getMaxDataModificationId() {
        return this.table.getMaxDataModificationId();
    }
}

