/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.command.query.Query;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.QueryExpressionIndex;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SortOrder;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.table.TableType;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;

public abstract class QueryExpressionTable
extends Table {
    private static final long ROW_COUNT_APPROXIMATION = 100L;
    Query viewQuery;
    QueryExpressionIndex index;
    ArrayList<Table> tables;
    private long lastModificationCheck;
    private long maxDataModificationId;

    public static List<Column> createQueryColumnTemplateList(String[] stringArray, Query query, String[] stringArray2) {
        ArrayList<Column> arrayList = new ArrayList<Column>();
        query.prepare();
        stringArray2[0] = StringUtils.cache(query.getPlanSQL(8));
        SessionLocal sessionLocal = query.getSession();
        ArrayList<Expression> arrayList2 = query.getExpressions();
        for (int i = 0; i < arrayList2.size(); ++i) {
            Expression expression = arrayList2.get(i);
            String string = stringArray != null && stringArray.length > i ? stringArray[i] : expression.getColumnNameForView(sessionLocal, i);
            arrayList.add(new Column(string, expression.getType()));
        }
        return arrayList;
    }

    static int getMaxParameterIndex(ArrayList<Parameter> arrayList) {
        int n = -1;
        for (Parameter parameter : arrayList) {
            if (parameter == null) continue;
            n = Math.max(n, parameter.getIndex());
        }
        return n;
    }

    QueryExpressionTable(Schema schema, int n, String string) {
        super(schema, n, string, false, true);
    }

    Column[] initColumns(SessionLocal sessionLocal, Column[] columnArray, Query query, boolean bl) {
        ArrayList<Expression> arrayList = query.getExpressions();
        int n = query.getColumnCount();
        ArrayList<Column> arrayList2 = new ArrayList<Column>(n);
        for (int i = 0; i < n; ++i) {
            Expression expression = arrayList.get(i);
            String string = null;
            TypeInfo typeInfo = TypeInfo.TYPE_UNKNOWN;
            if (columnArray != null && columnArray.length > i) {
                string = columnArray[i].getName();
                typeInfo = columnArray[i].getType();
            }
            if (string == null) {
                String string2 = string = bl ? expression.getAlias(sessionLocal, i) : expression.getColumnNameForView(sessionLocal, i);
            }
            if (typeInfo.getValueType() == -1) {
                typeInfo = expression.getType();
            }
            arrayList2.add(new Column(string, typeInfo, this, i));
        }
        return arrayList2.toArray(new Column[0]);
    }

    public final Query getQuery() {
        return this.viewQuery;
    }

    public abstract Query getTopQuery();

    @Override
    public final void close(SessionLocal sessionLocal) {
    }

    @Override
    public final Index addIndex(SessionLocal sessionLocal, String string, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType, boolean bl, String string2) {
        throw DbException.getUnsupportedException(this.getClass().getSimpleName() + ".addIndex");
    }

    @Override
    public final boolean isView() {
        return true;
    }

    @Override
    public final PlanItem getBestPlanItem(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        CacheKey cacheKey = new CacheKey(nArray, this);
        Map<Object, QueryExpressionIndex> map = sessionLocal.getViewIndexCache(this.getTableType() == null);
        QueryExpressionIndex queryExpressionIndex = map.get(cacheKey);
        if (queryExpressionIndex == null || queryExpressionIndex.isExpired()) {
            queryExpressionIndex = new QueryExpressionIndex(this, this.index, sessionLocal, nArray, tableFilterArray, n, sortOrder);
            map.put(cacheKey, queryExpressionIndex);
        }
        PlanItem planItem = new PlanItem();
        planItem.cost = queryExpressionIndex.getCost(sessionLocal, nArray, tableFilterArray, n, sortOrder, allColumnsForPlan);
        planItem.setIndex(queryExpressionIndex);
        return planItem;
    }

    @Override
    public boolean isQueryComparable() {
        for (Table table : this.tables) {
            if (table.isQueryComparable()) continue;
            return false;
        }
        return true;
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
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public final boolean canGetRowCount(SessionLocal sessionLocal) {
        return false;
    }

    @Override
    public final long getRowCountApproximation(SessionLocal sessionLocal) {
        return 100L;
    }

    public final int getParameterOffset(ArrayList<Parameter> arrayList) {
        int n;
        Query query = this.getTopQuery();
        int n2 = n = query == null ? -1 : QueryExpressionTable.getMaxParameterIndex(query.getParameters());
        if (arrayList != null) {
            n = Math.max(n, QueryExpressionTable.getMaxParameterIndex(arrayList));
        }
        return n + 1;
    }

    @Override
    public final boolean canReference() {
        return false;
    }

    @Override
    public final ArrayList<Index> getIndexes() {
        return null;
    }

    @Override
    public long getMaxDataModificationId() {
        long l = this.database.getModificationDataId();
        if (l > this.lastModificationCheck && this.maxDataModificationId <= l) {
            this.maxDataModificationId = this.viewQuery.getMaxDataModificationId();
            this.lastModificationCheck = l;
        }
        return this.maxDataModificationId;
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
        return this.viewQuery.isEverything(ExpressionVisitor.DETERMINISTIC_VISITOR);
    }

    @Override
    public final void addDependencies(HashSet<DbObject> hashSet) {
        super.addDependencies(hashSet);
        if (this.tables != null) {
            for (Table table : this.tables) {
                if (TableType.VIEW == table.getTableType()) continue;
                table.addDependencies(hashSet);
            }
        }
    }

    static final class CacheKey {
        private final int[] masks;
        private final QueryExpressionTable queryExpressionTable;

        CacheKey(int[] nArray, QueryExpressionTable queryExpressionTable) {
            this.masks = nArray;
            this.queryExpressionTable = queryExpressionTable;
        }

        public int hashCode() {
            int n = 1;
            n = 31 * n + Arrays.hashCode(this.masks);
            n = 31 * n + this.queryExpressionTable.hashCode();
            return n;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            CacheKey cacheKey = (CacheKey)object;
            if (this.queryExpressionTable != cacheKey.queryExpressionTable) {
                return false;
            }
            return Arrays.equals(this.masks, cacheKey.masks);
        }
    }
}

