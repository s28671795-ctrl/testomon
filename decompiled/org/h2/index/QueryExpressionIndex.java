/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.h2.command.Parser;
import org.h2.command.ParserBase;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.command.query.Query;
import org.h2.command.query.SelectUnion;
import org.h2.engine.SessionLocal;
import org.h2.expression.Parameter;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.index.QueryExpressionCursor;
import org.h2.index.SpatialIndex;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.QueryExpressionTable;
import org.h2.table.TableFilter;
import org.h2.table.TableView;
import org.h2.util.IntArray;
import org.h2.value.Value;

public class QueryExpressionIndex
extends Index
implements SpatialIndex {
    private static final long MAX_AGE_NANOS = TimeUnit.MILLISECONDS.toNanos(10000L);
    private final QueryExpressionTable table;
    private final String querySQL;
    private final ArrayList<Parameter> originalParameters;
    private boolean recursive;
    private final int[] indexMasks;
    private Query query;
    private final SessionLocal createSession;
    private final long evaluatedAt;

    public QueryExpressionIndex(QueryExpressionTable queryExpressionTable, String string, ArrayList<Parameter> arrayList, boolean bl) {
        super(queryExpressionTable, 0, null, null, 0, IndexType.createNonUnique(false));
        this.table = queryExpressionTable;
        this.querySQL = string;
        this.originalParameters = arrayList;
        this.recursive = bl;
        this.columns = new Column[0];
        this.createSession = null;
        this.indexMasks = null;
        this.evaluatedAt = Long.MIN_VALUE;
    }

    public QueryExpressionIndex(QueryExpressionTable queryExpressionTable, QueryExpressionIndex queryExpressionIndex, SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder) {
        super(queryExpressionTable, 0, null, null, 0, IndexType.createNonUnique(false));
        this.table = queryExpressionTable;
        this.querySQL = queryExpressionIndex.querySQL;
        this.originalParameters = queryExpressionIndex.originalParameters;
        this.recursive = queryExpressionIndex.recursive;
        this.indexMasks = nArray;
        this.createSession = sessionLocal;
        this.columns = new Column[0];
        if (!this.recursive) {
            this.query = this.getQuery(sessionLocal, nArray);
        }
        if (this.recursive || queryExpressionTable.getTopQuery() != null) {
            this.evaluatedAt = Long.MAX_VALUE;
        } else {
            long l = System.nanoTime();
            if (l == Long.MAX_VALUE) {
                ++l;
            }
            this.evaluatedAt = l;
        }
    }

    public SessionLocal getSession() {
        return this.createSession;
    }

    public boolean isExpired() {
        assert (this.evaluatedAt != Long.MIN_VALUE) : "must not be called for main index of TableView";
        return !this.recursive && this.table.getTopQuery() == null && System.nanoTime() - this.evaluatedAt > MAX_AGE_NANOS;
    }

    @Override
    public String getPlanSQL() {
        return this.query == null ? null : this.query.getPlanSQL(11);
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("VIEW");
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("VIEW");
    }

    @Override
    public double getCost(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return this.recursive ? 1000.0 : this.query.getCost();
    }

    @Override
    public Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        return this.find(sessionLocal, searchRow, searchRow2, null);
    }

    @Override
    public Cursor findByGeometry(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2, SearchRow searchRow3) {
        return this.find(sessionLocal, searchRow, searchRow2, searchRow3);
    }

    private Cursor findRecursive(SearchRow searchRow, SearchRow searchRow2) {
        Value[] valueArray;
        Object object;
        TableView tableView = (TableView)this.table;
        ResultInterface resultInterface = tableView.getRecursiveResult();
        if (resultInterface != null) {
            resultInterface.reset();
            return new QueryExpressionCursor(this, resultInterface, searchRow, searchRow2);
        }
        if (this.query == null) {
            object = new Parser(this.createSession);
            ((Parser)object).setRightsChecked(true);
            ((ParserBase)object).setSuppliedParameters(this.originalParameters);
            this.query = (Query)((Parser)object).prepare(this.querySQL);
            this.query.setNeverLazy(true);
        }
        if (!this.query.isUnion()) {
            throw DbException.get(42001, "recursive queries without UNION");
        }
        object = (SelectUnion)this.query;
        Query query = ((SelectUnion)object).getLeft();
        query.setNeverLazy(true);
        query.disableCache();
        ResultInterface resultInterface2 = query.query(0L);
        LocalResult localResult = ((SelectUnion)object).getEmptyResult();
        localResult.setMaxMemoryRows(Integer.MAX_VALUE);
        while (resultInterface2.next()) {
            valueArray = resultInterface2.currentRow();
            localResult.addRow(valueArray);
        }
        valueArray = ((SelectUnion)object).getRight();
        valueArray.setNeverLazy(true);
        resultInterface2.reset();
        tableView.setRecursiveResult(resultInterface2);
        valueArray.disableCache();
        while ((resultInterface2 = valueArray.query(0L)).hasNext()) {
            while (resultInterface2.next()) {
                Value[] valueArray2 = resultInterface2.currentRow();
                localResult.addRow(valueArray2);
            }
            resultInterface2.reset();
            tableView.setRecursiveResult(resultInterface2);
        }
        tableView.setRecursiveResult(null);
        localResult.done();
        return new QueryExpressionCursor(this, localResult, searchRow, searchRow2);
    }

    public void setupQueryParameters(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2, SearchRow searchRow3) {
        int n;
        ArrayList<Parameter> arrayList = this.query.getParameters();
        if (this.originalParameters != null) {
            for (Parameter parameter : this.originalParameters) {
                if (parameter == null) continue;
                n = parameter.getIndex();
                Value value = parameter.getValue(sessionLocal);
                QueryExpressionIndex.setParameter(arrayList, n, value);
            }
        }
        int n2 = searchRow != null ? searchRow.getColumnCount() : (searchRow2 != null ? searchRow2.getColumnCount() : (searchRow3 != null ? searchRow3.getColumnCount() : 0));
        int n3 = this.table.getParameterOffset(this.originalParameters);
        for (n = 0; n < n2; ++n) {
            int n4 = this.indexMasks[n];
            if ((n4 & 1) != 0) {
                QueryExpressionIndex.setParameter(arrayList, n3++, searchRow.getValue(n));
            }
            if ((n4 & 2) != 0) {
                QueryExpressionIndex.setParameter(arrayList, n3++, searchRow.getValue(n));
            }
            if ((n4 & 4) != 0) {
                QueryExpressionIndex.setParameter(arrayList, n3++, searchRow2.getValue(n));
            }
            if ((n4 & 0x10) == 0) continue;
            QueryExpressionIndex.setParameter(arrayList, n3++, searchRow3.getValue(n));
        }
    }

    private Cursor find(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2, SearchRow searchRow3) {
        if (this.recursive) {
            return this.findRecursive(searchRow, searchRow2);
        }
        this.setupQueryParameters(sessionLocal, searchRow, searchRow2, searchRow3);
        ResultInterface resultInterface = this.query.query(0L);
        return new QueryExpressionCursor(this, resultInterface, searchRow, searchRow2);
    }

    private static void setParameter(ArrayList<Parameter> arrayList, int n, Value value) {
        if (n >= arrayList.size()) {
            return;
        }
        Parameter parameter = arrayList.get(n);
        parameter.setValue(value);
    }

    public Query getQuery() {
        return this.query;
    }

    private Query getQuery(SessionLocal sessionLocal, int[] nArray) {
        int n;
        int n2;
        int n3;
        int n4;
        Query query = sessionLocal.prepareQueryExpression(this.querySQL);
        if (nArray == null || !query.allowGlobalConditions()) {
            query.preparePlan();
            return query;
        }
        int n5 = this.table.getParameterOffset(this.originalParameters);
        IntArray intArray = new IntArray();
        int n6 = 0;
        for (n4 = 0; n4 < nArray.length; ++n4) {
            int n7 = nArray[n4];
            if (n7 == 0) continue;
            ++n6;
            n3 = Integer.bitCount(n7);
            for (n2 = 0; n2 < n3; ++n2) {
                intArray.add(n4);
            }
        }
        n4 = intArray.size();
        ArrayList<Column> arrayList = new ArrayList<Column>(n4);
        n3 = 0;
        while (n3 < n4) {
            Parameter parameter;
            n2 = intArray.get(n3);
            arrayList.add(this.table.getColumn(n2));
            n = nArray[n2];
            if ((n & 1) != 0) {
                parameter = new Parameter(n5 + n3);
                query.addGlobalCondition(parameter, n2, 6);
                ++n3;
            }
            if ((n & 2) != 0) {
                parameter = new Parameter(n5 + n3);
                query.addGlobalCondition(parameter, n2, 5);
                ++n3;
            }
            if ((n & 4) != 0) {
                parameter = new Parameter(n5 + n3);
                query.addGlobalCondition(parameter, n2, 4);
                ++n3;
            }
            if ((n & 0x10) == 0) continue;
            parameter = new Parameter(n5 + n3);
            query.addGlobalCondition(parameter, n2, 8);
            ++n3;
        }
        this.columns = arrayList.toArray(new Column[0]);
        this.indexColumns = new IndexColumn[n6];
        this.columnIds = new int[n6];
        n2 = 0;
        for (n3 = 0; n3 < 2; ++n3) {
            for (n = 0; n < nArray.length; ++n) {
                int n8 = nArray[n];
                if (n8 == 0 || (n3 == 0 ? (n8 & 1) == 0 : (n8 & 1) != 0)) continue;
                Column column = this.table.getColumn(n);
                this.indexColumns[n2] = new IndexColumn(column);
                this.columnIds[n2] = column.getColumnId();
                ++n2;
            }
        }
        String string = query.getPlanSQL(0);
        if (!string.equals(this.querySQL)) {
            query = sessionLocal.prepareQueryExpression(string);
        }
        query.preparePlan();
        return query;
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("VIEW");
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("VIEW");
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("VIEW");
    }

    @Override
    public boolean needRebuild() {
        return false;
    }

    public void setRecursive(boolean bl) {
        this.recursive = bl;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return 0L;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return 0L;
    }

    public boolean isRecursive() {
        return this.recursive;
    }
}

