/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.h2.command.query.ForUpdate;
import org.h2.command.query.Optimizer;
import org.h2.command.query.Query;
import org.h2.command.query.SelectGroups;
import org.h2.command.query.SelectListColumnResolver;
import org.h2.engine.Database;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Alias;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.expression.Wildcard;
import org.h2.expression.analysis.DataAnalysisOperation;
import org.h2.expression.analysis.Window;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.ConditionAndOr;
import org.h2.expression.condition.ConditionLocalAndGlobal;
import org.h2.expression.function.CoalesceFunction;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.QueryExpressionIndex;
import org.h2.message.DbException;
import org.h2.mode.DefaultNullOrdering;
import org.h2.result.LazyResult;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.table.TableType;
import org.h2.table.TableView;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.Value;
import org.h2.value.ValueRow;

public class Select
extends Query {
    TableFilter topTableFilter;
    private final ArrayList<TableFilter> filters = Utils.newSmallArrayList();
    private final ArrayList<TableFilter> topFilters = Utils.newSmallArrayList();
    private Select parentSelect;
    private Expression condition;
    private Expression having;
    private Expression qualify;
    private Expression[] distinctExpressions;
    private int[] distinctIndexes;
    private ArrayList<Expression> group;
    int[] groupIndex;
    boolean[] groupByExpression;
    SelectGroups groupData;
    private int havingIndex;
    private int qualifyIndex;
    private int[] groupByCopies;
    private boolean isExplicitTable;
    boolean isGroupQuery;
    private boolean isGroupSortedQuery;
    private boolean isWindowQuery;
    private ForUpdate forUpdate;
    private double cost;
    private boolean isQuickAggregateQuery;
    private boolean isDistinctQuery;
    private boolean sortUsingIndex;
    private boolean isGroupWindowStage2;
    private HashMap<String, Window> windows;

    public Select(SessionLocal sessionLocal, Select select) {
        super(sessionLocal);
        this.parentSelect = select;
    }

    @Override
    public boolean isUnion() {
        return false;
    }

    public void addTableFilter(TableFilter tableFilter, boolean bl) {
        this.filters.add(tableFilter);
        if (bl) {
            this.topFilters.add(tableFilter);
        }
    }

    public ArrayList<TableFilter> getTopFilters() {
        return this.topFilters;
    }

    public void setExpressions(ArrayList<Expression> arrayList) {
        this.expressions = arrayList;
    }

    public void setExplicitTable() {
        this.setWildcard();
        this.isExplicitTable = true;
    }

    public void setWildcard() {
        this.expressions = new ArrayList(1);
        this.expressions.add(new Wildcard(null, null));
    }

    public void setGroupQuery() {
        this.isGroupQuery = true;
    }

    public void setWindowQuery() {
        this.isWindowQuery = true;
    }

    public void setGroupBy(ArrayList<Expression> arrayList) {
        this.group = arrayList;
    }

    public ArrayList<Expression> getGroupBy() {
        return this.group;
    }

    public SelectGroups getGroupDataIfCurrent(boolean bl) {
        return this.groupData != null && (bl || this.groupData.isCurrentGroup()) ? this.groupData : null;
    }

    public void setDistinct() {
        if (this.distinctExpressions != null) {
            throw DbException.getUnsupportedException("DISTINCT ON together with DISTINCT");
        }
        this.distinct = true;
    }

    public void setDistinct(Expression[] expressionArray) {
        if (this.distinct) {
            throw DbException.getUnsupportedException("DISTINCT ON together with DISTINCT");
        }
        this.distinctExpressions = expressionArray;
    }

    @Override
    public boolean isAnyDistinct() {
        return this.distinct || this.distinctExpressions != null;
    }

    public boolean addWindow(String string, Window window) {
        if (this.windows == null) {
            this.windows = new HashMap();
        }
        return this.windows.put(string, window) == null;
    }

    public Window getWindow(String string) {
        return this.windows != null ? this.windows.get(string) : null;
    }

    public void addCondition(Expression expression) {
        this.condition = this.condition == null ? expression : new ConditionAndOr(0, expression, this.condition);
    }

    public Expression getCondition() {
        return this.condition;
    }

    private LazyResult queryGroupSorted(int n, ResultTarget resultTarget, long l, boolean bl) {
        LazyResultGroupSorted lazyResultGroupSorted = new LazyResultGroupSorted(this.expressionArray, n);
        Select.skipOffset(lazyResultGroupSorted, l, bl);
        if (resultTarget == null) {
            return lazyResultGroupSorted;
        }
        while (lazyResultGroupSorted.next()) {
            resultTarget.addRow(lazyResultGroupSorted.currentRow());
        }
        return null;
    }

    Value[] createGroupSortedRow(Value[] valueArray, int n) {
        Value[] valueArray2 = this.constructGroupResultRow(valueArray, n);
        if (this.isHavingNullOrFalse(valueArray2)) {
            return null;
        }
        return this.rowForResult(valueArray2, n);
    }

    private Value[] rowForResult(Value[] valueArray, int n) {
        if (n == this.resultColumnCount) {
            return valueArray;
        }
        return Arrays.copyOf(valueArray, this.resultColumnCount);
    }

    private boolean isHavingNullOrFalse(Value[] valueArray) {
        return this.havingIndex >= 0 && !valueArray[this.havingIndex].isTrue();
    }

    private Index getGroupSortedIndex() {
        if (this.groupIndex == null || this.groupByExpression == null) {
            return null;
        }
        ArrayList<Index> arrayList = this.topTableFilter.getTable().getIndexes();
        if (arrayList != null) {
            for (Index index : arrayList) {
                if (index.getIndexType().isScan() || index.getIndexType().isHash() || !this.isGroupSortedIndex(this.topTableFilter, index)) continue;
                return index;
            }
        }
        return null;
    }

    private boolean isGroupSortedIndex(TableFilter tableFilter, Index index) {
        int n;
        Column[] columnArray = index.getColumns();
        boolean[] blArray = new boolean[columnArray.length];
        int n2 = this.expressions.size();
        block0: for (n = 0; n < n2; ++n) {
            if (!this.groupByExpression[n]) continue;
            Expression expression = ((Expression)this.expressions.get(n)).getNonAliasExpression();
            if (!(expression instanceof ExpressionColumn)) {
                return false;
            }
            ExpressionColumn expressionColumn = (ExpressionColumn)expression;
            for (int i = 0; i < columnArray.length; ++i) {
                if (tableFilter != expressionColumn.getTableFilter() || !columnArray[i].equals(expressionColumn.getColumn())) continue;
                blArray[i] = true;
                continue block0;
            }
            return false;
        }
        for (n = 1; n < blArray.length; ++n) {
            if (blArray[n - 1] || !blArray[n]) continue;
            return false;
        }
        return true;
    }

    boolean isConditionMetForUpdate() {
        if (this.isConditionMet()) {
            int n = this.filters.size();
            boolean bl = true;
            for (int i = 0; i < n; ++i) {
                TableFilter tableFilter = this.filters.get(i);
                if (tableFilter.isJoinOuter() || tableFilter.isJoinOuterIndirect()) continue;
                Row row = tableFilter.get();
                Table table = tableFilter.getTable();
                if (!table.isRowLockable()) continue;
                Row row2 = table.lockRow(this.session, row, this.forUpdate.getTimeoutMillis());
                if (row2 == null) {
                    return false;
                }
                if (row.hasSharedData(row2)) continue;
                tableFilter.set(row2);
                bl = false;
            }
            return bl || this.isConditionMet();
        }
        return false;
    }

    boolean isConditionMet() {
        return this.condition == null || this.condition.getBooleanValue(this.session);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void queryWindow(int n, LocalResult localResult, long l, boolean bl) {
        this.initGroupData(n);
        try {
            this.gatherGroup(n, 2);
            this.processGroupResult(n, localResult, l, bl, false);
        }
        finally {
            this.groupData.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void queryGroupWindow(int n, LocalResult localResult, long l, boolean bl) {
        this.initGroupData(n);
        try {
            this.gatherGroup(n, 1);
            try {
                this.isGroupWindowStage2 = true;
                while (this.groupData.next() != null) {
                    if (this.havingIndex < 0 || ((Expression)this.expressions.get(this.havingIndex)).getBooleanValue(this.session)) {
                        this.updateAgg(n, 2);
                        continue;
                    }
                    this.groupData.remove();
                }
                this.groupData.done();
                this.processGroupResult(n, localResult, l, bl, false);
            }
            finally {
                this.isGroupWindowStage2 = false;
            }
        }
        finally {
            this.groupData.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void queryGroup(int n, LocalResult localResult, long l, boolean bl) {
        this.initGroupData(n);
        try {
            this.gatherGroup(n, 1);
            this.processGroupResult(n, localResult, l, bl, true);
        }
        finally {
            this.groupData.reset();
        }
    }

    private void initGroupData(int n) {
        if (this.groupData == null) {
            this.setGroupData(SelectGroups.getInstance(this.session, this.expressions, this.isGroupQuery, this.groupIndex));
        } else {
            this.updateAgg(n, 0);
        }
        this.groupData.reset();
    }

    void setGroupData(SelectGroups selectGroups) {
        this.groupData = selectGroups;
        this.topTableFilter.visit(tableFilter -> {
            Select select = tableFilter.getSelect();
            if (select != null) {
                select.groupData = selectGroups;
            }
        });
    }

    private void gatherGroup(int n, int n2) {
        long l = 0L;
        this.setCurrentRowNumber(0L);
        while (this.topTableFilter.next()) {
            this.setCurrentRowNumber(l + 1L);
            if (!(this.forUpdate != null ? this.isConditionMetForUpdate() : this.isConditionMet())) continue;
            ++l;
            this.groupData.nextSource();
            this.updateAgg(n, n2);
        }
        this.groupData.done();
    }

    void updateAgg(int n, int n2) {
        for (int i = 0; i < n; ++i) {
            if (this.groupByExpression != null && this.groupByExpression[i] || this.groupByCopies != null && this.groupByCopies[i] >= 0) continue;
            Expression expression = (Expression)this.expressions.get(i);
            expression.updateAggregate(this.session, n2);
        }
    }

    private void processGroupResult(int n, LocalResult localResult, long l, boolean bl, boolean bl2) {
        ValueRow valueRow;
        while ((valueRow = this.groupData.next()) != null) {
            Value[] valueArray = this.constructGroupResultRow(valueRow.getList(), n);
            if (bl2 && this.isHavingNullOrFalse(valueArray) || this.qualifyIndex >= 0 && !valueArray[this.qualifyIndex].isTrue()) continue;
            if (bl && l > 0L) {
                --l;
                continue;
            }
            localResult.addRow(this.rowForResult(valueArray, n));
        }
    }

    private Value[] constructGroupResultRow(Value[] valueArray, int n) {
        int n2;
        int n3;
        Value[] valueArray2 = new Value[n];
        if (this.groupIndex != null) {
            n3 = this.groupIndex.length;
            for (n2 = 0; n2 < n3; ++n2) {
                valueArray2[this.groupIndex[n2]] = valueArray[n2];
            }
        }
        for (n2 = 0; n2 < n; ++n2) {
            if (this.groupByExpression != null && this.groupByExpression[n2]) continue;
            valueArray2[n2] = this.groupByCopies != null && (n3 = this.groupByCopies[n2]) >= 0 ? valueArray2[n3] : ((Expression)this.expressions.get(n2)).getValue(this.session);
        }
        return valueArray2;
    }

    private Index getSortIndex() {
        Index index;
        Object object;
        if (this.sort == null) {
            return null;
        }
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        int[] nArray = this.sort.getQueryColumnIndexes();
        int n = nArray.length;
        int[] nArray2 = new int[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = nArray[i];
            if (n3 < 0 || n3 >= this.expressions.size()) {
                throw DbException.getInvalidValueException("ORDER BY", n3 + 1);
            }
            object = (Expression)this.expressions.get(n3);
            if (((Expression)(object = ((Expression)object).getNonAliasExpression())).isConstant()) continue;
            if (!(object instanceof ExpressionColumn)) {
                return null;
            }
            ExpressionColumn expressionColumn = (ExpressionColumn)object;
            if (expressionColumn.getTableFilter() != this.topTableFilter) {
                return null;
            }
            arrayList.add(expressionColumn.getColumn());
            nArray2[n2++] = i;
        }
        Column[] columnArray = arrayList.toArray(new Column[0]);
        if (columnArray.length == 0) {
            return this.topTableFilter.getTable().getScanIndex(this.session);
        }
        ArrayList<Index> arrayList2 = this.topTableFilter.getTable().getIndexes();
        if (arrayList2 != null) {
            int[] nArray3 = this.sort.getSortTypesWithNullOrdering();
            object = this.getDatabase().getDefaultNullOrdering();
            block1: for (Index index2 : arrayList2) {
                IndexColumn[] indexColumnArray;
                if (index2.getCreateSQL() == null || index2.getIndexType().isHash() || (indexColumnArray = index2.getIndexColumns()).length < columnArray.length) continue;
                for (int i = 0; i < columnArray.length; ++i) {
                    IndexColumn indexColumn = indexColumnArray[i];
                    Column column = columnArray[i];
                    if (indexColumn.column != column) continue block1;
                    int n4 = nArray3[nArray2[i]];
                    if (column.isNullable() ? ((DefaultNullOrdering)((Object)object)).addExplicitNullOrdering(indexColumn.sortType) != n4 : (indexColumn.sortType & 1) != (n4 & 1)) continue block1;
                }
                return index2;
            }
        }
        if (columnArray.length == 1 && columnArray[0].getColumnId() == -1 && (index = this.topTableFilter.getTable().getScanIndex(this.session)).isRowIdIndex()) {
            return index;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void queryDistinct(ResultTarget resultTarget, long l, long l2, boolean bl, boolean bl2) {
        if (l2 > 0L && l > 0L && (l2 += l) < 0L) {
            l2 = Long.MAX_VALUE;
        }
        long l3 = 0L;
        this.setCurrentRowNumber(0L);
        Index index = this.topTableFilter.getIndex();
        SearchRow searchRow = null;
        int n = index.getColumns()[0].getColumnId();
        if (!bl2) {
            l = 0L;
        }
        while (true) {
            this.setCurrentRowNumber(++l3);
            Cursor cursor = index.findNext(this.session, searchRow, null);
            if (!cursor.next()) return;
            SearchRow searchRow2 = cursor.getSearchRow();
            Value value = searchRow2.getValue(n);
            if (searchRow == null) {
                searchRow = index.getRowFactory().createRow();
            }
            searchRow.setValue(n, value);
            if (l > 0L) {
                --l;
                continue;
            }
            resultTarget.addRow(value);
            if ((this.sort == null || this.sortUsingIndex) && l2 > 0L && l3 >= l2 && !bl) return;
        }
    }

    private LazyResult queryFlat(int n, ResultTarget resultTarget, long l, long l2, boolean bl, boolean bl2) {
        if (l2 > 0L && l > 0L && !bl2 && (l2 += l) < 0L) {
            l2 = Long.MAX_VALUE;
        }
        LazyResultQueryFlat lazyResultQueryFlat = new LazyResultQueryFlat(this.expressionArray, n, this.forUpdate != null);
        Select.skipOffset(lazyResultQueryFlat, l, bl2);
        if (resultTarget == null) {
            return lazyResultQueryFlat;
        }
        if (l2 < 0L || this.sort != null && !this.sortUsingIndex || bl && !bl2) {
            l2 = Long.MAX_VALUE;
        }
        Value[] valueArray = null;
        while (resultTarget.getRowCount() < l2 && lazyResultQueryFlat.next()) {
            valueArray = lazyResultQueryFlat.currentRow();
            resultTarget.addRow(valueArray);
        }
        if (l2 != Long.MAX_VALUE && bl && this.sort != null && valueArray != null) {
            Value[] valueArray2 = valueArray;
            while (lazyResultQueryFlat.next() && this.sort.compare(valueArray2, valueArray = lazyResultQueryFlat.currentRow()) == 0) {
                resultTarget.addRow(valueArray);
            }
            resultTarget.limitsWereApplied();
        }
        return null;
    }

    private static void skipOffset(LazyResultSelect lazyResultSelect, long l, boolean bl) {
        if (bl) {
            while (l > 0L && lazyResultSelect.skip()) {
                --l;
            }
        }
    }

    private void queryQuick(int n, ResultTarget resultTarget, boolean bl) {
        Value[] valueArray = new Value[n];
        for (int i = 0; i < n; ++i) {
            Expression expression = (Expression)this.expressions.get(i);
            valueArray[i] = expression.getValue(this.session);
        }
        if (!bl) {
            resultTarget.addRow(valueArray);
        }
    }

    @Override
    protected ResultInterface queryWithoutCache(long l, ResultTarget resultTarget) {
        boolean bl;
        this.disableLazyForJoinSubqueries(this.topTableFilter);
        Query.OffsetFetch offsetFetch = this.getOffsetFetch(l);
        long l2 = offsetFetch.offset;
        long l3 = offsetFetch.fetch;
        boolean bl2 = offsetFetch.fetchPercent;
        boolean bl3 = this.session.isLazyQueryExecution() && resultTarget == null && this.forUpdate == null && !this.isQuickAggregateQuery && l3 != 0L && !bl2 && !this.withTies && l2 == 0L && this.isReadOnly();
        int n = this.expressions.size();
        LocalResult localResult = null;
        if (!(bl3 || resultTarget != null && this.getDatabase().getSettings().optimizeInsertFromSelect)) {
            localResult = this.createLocalResult(localResult);
        }
        boolean bl4 = bl = !bl2;
        if (this.sort != null && (!this.sortUsingIndex || this.isAnyDistinct())) {
            localResult = this.createLocalResult(localResult);
            localResult.setSortOrder(this.sort);
            if (!this.sortUsingIndex) {
                bl = false;
            }
        }
        if (this.distinct) {
            localResult = this.createLocalResult(localResult);
            if (!this.isDistinctQuery) {
                bl = false;
                localResult.setDistinct();
            }
        } else if (this.distinctExpressions != null) {
            bl = false;
            localResult = this.createLocalResult(localResult);
            localResult.setDistinct(this.distinctIndexes);
        }
        if (this.isWindowQuery || this.isGroupQuery && !this.isGroupSortedQuery) {
            localResult = this.createLocalResult(localResult);
        }
        if (!(bl3 || l3 < 0L && l2 <= 0L)) {
            localResult = this.createLocalResult(localResult);
        }
        this.topTableFilter.startQuery(this.session);
        this.topTableFilter.reset();
        this.topTableFilter.lock(this.session);
        ResultTarget resultTarget2 = localResult != null ? localResult : resultTarget;
        bl3 &= resultTarget2 == null;
        LazyResult lazyResult = null;
        if (l3 != 0L) {
            long l4;
            long l5 = l4 = bl2 ? -1L : l3;
            if (this.isQuickAggregateQuery) {
                this.queryQuick(n, resultTarget2, bl && l2 > 0L);
            } else if (this.isWindowQuery) {
                if (this.isGroupQuery) {
                    this.queryGroupWindow(n, localResult, l2, bl);
                } else {
                    this.queryWindow(n, localResult, l2, bl);
                }
            } else if (this.isGroupQuery) {
                if (this.isGroupSortedQuery) {
                    lazyResult = this.queryGroupSorted(n, resultTarget2, l2, bl);
                } else {
                    this.queryGroup(n, localResult, l2, bl);
                }
            } else if (this.isDistinctQuery) {
                this.queryDistinct(resultTarget2, l2, l4, this.withTies, bl);
            } else {
                lazyResult = this.queryFlat(n, resultTarget2, l2, l4, this.withTies, bl);
            }
            if (bl) {
                l2 = 0L;
            }
        }
        assert (bl3 == (lazyResult != null)) : bl3;
        if (lazyResult != null) {
            if (l3 > 0L) {
                lazyResult.setLimit(l3);
            }
            if (this.randomAccessResult) {
                return this.convertToDistinct(lazyResult);
            }
            return lazyResult;
        }
        if (localResult != null) {
            return this.finishResult(localResult, l2, l3, bl2, resultTarget);
        }
        return null;
    }

    private void disableLazyForJoinSubqueries(TableFilter tableFilter) {
        if (this.session.isLazyQueryExecution()) {
            tableFilter.visit(tableFilter2 -> {
                QueryExpressionIndex queryExpressionIndex;
                if (tableFilter2 != tableFilter && tableFilter2.getTable().getTableType() == TableType.VIEW && (queryExpressionIndex = (QueryExpressionIndex)tableFilter2.getIndex()) != null && queryExpressionIndex.getQuery() != null) {
                    queryExpressionIndex.getQuery().setNeverLazy(true);
                }
            });
        }
    }

    private LocalResult createLocalResult(LocalResult localResult) {
        return localResult != null ? localResult : new LocalResult(this.session, this.expressionArray, this.visibleColumnCount, this.resultColumnCount);
    }

    private void expandColumnList() {
        int n = 0;
        while (n < this.expressions.size()) {
            Expression expression = (Expression)this.expressions.get(n);
            if (!(expression instanceof Wildcard)) {
                ++n;
                continue;
            }
            this.expressions.remove(n);
            Wildcard wildcard = (Wildcard)expression;
            String string = wildcard.getTableAlias();
            boolean bl = wildcard.getExceptColumns() != null;
            HashMap<Column, ExpressionColumn> hashMap = null;
            if (string == null) {
                if (bl) {
                    for (TableFilter tableFilter : this.filters) {
                        wildcard.mapColumns(tableFilter, 1, 0);
                    }
                    hashMap = wildcard.mapExceptColumns();
                }
                for (TableFilter tableFilter : this.filters) {
                    n = this.expandColumnList(tableFilter, n, false, hashMap);
                }
                continue;
            }
            Iterator<TableFilter> iterator2 = this.getDatabase();
            String string2 = wildcard.getSchemaName();
            TableFilter tableFilter = null;
            for (TableFilter tableFilter2 : this.filters) {
                if (!((Database)((Object)iterator2)).equalsIdentifiers(string, tableFilter2.getTableAlias()) || string2 != null && !((Database)((Object)iterator2)).equalsIdentifiers(string2, tableFilter2.getSchemaName())) continue;
                if (bl) {
                    wildcard.mapColumns(tableFilter2, 1, 0);
                    hashMap = wildcard.mapExceptColumns();
                }
                tableFilter = tableFilter2;
                break;
            }
            if (tableFilter == null) {
                throw DbException.get(42102, string);
            }
            n = this.expandColumnList(tableFilter, n, true, hashMap);
        }
    }

    private int expandColumnList(TableFilter tableFilter, int n, boolean bl, HashMap<Column, ExpressionColumn> hashMap) {
        String string = tableFilter.getSchemaName();
        String string2 = tableFilter.getTableAlias();
        if (bl) {
            for (Column column : tableFilter.getTable().getColumns()) {
                n = this.addExpandedColumn(tableFilter, n, hashMap, string, string2, column);
            }
        } else {
            LinkedHashMap<Column, Column> linkedHashMap = tableFilter.getCommonJoinColumns();
            if (linkedHashMap != null) {
                Column[] columnArray = tableFilter.getCommonJoinColumnsFilter();
                String string3 = columnArray.getSchemaName();
                String string4 = columnArray.getTableAlias();
                for (Map.Entry<Column, Column> entry : linkedHashMap.entrySet()) {
                    Column column = entry.getKey();
                    Column column2 = entry.getValue();
                    if (tableFilter.isCommonJoinColumnToExclude(column2) || hashMap != null && (hashMap.remove(column) != null || hashMap.remove(column2) != null)) continue;
                    Database database = this.getDatabase();
                    Expression expression = column == column2 || DataType.hasTotalOrdering(column.getType().getValueType()) && DataType.hasTotalOrdering(column2.getType().getValueType()) ? new ExpressionColumn(database, string3, string4, columnArray.getColumnName(column2)) : new Alias(new CoalesceFunction(0, new ExpressionColumn(database, string, string2, tableFilter.getColumnName(column)), new ExpressionColumn(database, string3, string4, columnArray.getColumnName(column2))), column.getName(), true);
                    this.expressions.add(n++, expression);
                }
            }
            for (Column column : tableFilter.getTable().getColumns()) {
                if (linkedHashMap != null && linkedHashMap.containsKey(column) || tableFilter.isCommonJoinColumnToExclude(column)) continue;
                n = this.addExpandedColumn(tableFilter, n, hashMap, string, string2, column);
            }
        }
        return n;
    }

    private int addExpandedColumn(TableFilter tableFilter, int n, HashMap<Column, ExpressionColumn> hashMap, String string, String string2, Column column) {
        if ((hashMap == null || hashMap.remove(column) == null) && column.getVisible()) {
            ExpressionColumn expressionColumn = new ExpressionColumn(this.getDatabase(), string, string2, tableFilter.getColumnName(column));
            this.expressions.add(n++, expressionColumn);
        }
        return n;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void init() {
        int n;
        int n2;
        ArrayList<String> arrayList;
        if (this.checkInit) {
            throw DbException.getInternalError();
        }
        this.filters.sort(TableFilter.ORDER_IN_FROM_COMPARATOR);
        this.expandColumnList();
        this.visibleColumnCount = this.expressions.size();
        if (this.visibleColumnCount > 16384) {
            throw DbException.get(54011, "16384");
        }
        if (this.distinctExpressions != null || this.orderList != null || this.group != null) {
            arrayList = new ArrayList<String>(this.visibleColumnCount);
            for (int i = 0; i < this.visibleColumnCount; ++i) {
                Object object = (Expression)this.expressions.get(i);
                object = ((Expression)object).getNonAliasExpression();
                arrayList.add(((Expression)object).getSQL(0, 2));
            }
        } else {
            arrayList = null;
        }
        if (this.distinctExpressions != null) {
            BitSet bitSet = new BitSet();
            for (Expression object2 : this.distinctExpressions) {
                bitSet.set(this.initExpression(arrayList, object2, false, this.filters));
            }
            int n3 = 0;
            n2 = bitSet.cardinality();
            this.distinctIndexes = new int[n2];
            for (n = 0; n < n2; ++n) {
                n3 = bitSet.nextSetBit(n3);
                this.distinctIndexes[n] = n3++;
            }
        }
        if (this.orderList != null) {
            this.initOrder(arrayList, this.isAnyDistinct(), this.filters);
        }
        this.resultColumnCount = this.expressions.size();
        if (this.having != null) {
            this.expressions.add(this.having);
            this.havingIndex = this.expressions.size() - 1;
            this.having = null;
        } else {
            this.havingIndex = -1;
        }
        if (this.qualify != null) {
            this.expressions.add(this.qualify);
            this.qualifyIndex = this.expressions.size() - 1;
            this.qualify = null;
        } else {
            this.qualifyIndex = -1;
        }
        if (this.withTies && !this.hasOrder()) {
            throw DbException.get(90122);
        }
        Database database = this.getDatabase();
        if (this.group != null) {
            block29: {
                void var6_17;
                int n4 = this.group.size();
                n2 = arrayList.size();
                n = this.expressions.size();
                if (n > n2) {
                    arrayList.ensureCapacity(n);
                    int i = n2;
                    while (i < n) {
                        arrayList.add(((Expression)this.expressions.get((int)i)).getSQL(0, 2));
                        ++i;
                    }
                }
                this.groupIndex = new int[n4];
                boolean bl = false;
                while (var6_17 < n4) {
                    Object object;
                    int n3;
                    Expression expression = this.group.get((int)var6_17);
                    String string = expression.getSQL(0, 2);
                    int n5 = -1;
                    for (n3 = 0; n3 < n2; ++n3) {
                        object = arrayList.get(n3);
                        if (!database.equalsIdentifiers((String)object, string)) continue;
                        n5 = this.mergeGroupByExpressions(database, n3, arrayList, false);
                        break;
                    }
                    if (n5 < 0) {
                        for (n3 = 0; n3 < n2; ++n3) {
                            object = (Expression)this.expressions.get(n3);
                            if (database.equalsIdentifiers(string, ((Expression)object).getAlias(this.session, n3))) {
                                n5 = this.mergeGroupByExpressions(database, n3, arrayList, true);
                                break;
                            }
                            string = expression.getAlias(this.session, n3);
                            if (!database.equalsIdentifiers(string, ((Expression)object).getAlias(this.session, n3))) continue;
                            n5 = this.mergeGroupByExpressions(database, n3, arrayList, true);
                            break;
                        }
                    }
                    if (n5 < 0) {
                        this.groupIndex[var6_17] = n3 = this.expressions.size();
                        this.expressions.add(expression);
                    } else {
                        this.groupIndex[var6_17] = n5;
                    }
                    ++var6_17;
                }
                if (this.groupByCopies != null) {
                    for (int n5 : this.groupByCopies) {
                        if (n5 < 0) {
                            continue;
                        }
                        break block29;
                    }
                    this.groupByCopies = null;
                }
            }
            this.groupByExpression = new boolean[this.expressions.size()];
            for (int n5 : this.groupIndex) {
                this.groupByExpression[n5] = true;
            }
            this.group = null;
        }
        for (TableFilter tableFilter : this.filters) {
            this.mapColumns(tableFilter, 0);
        }
        this.mapCondition(this.havingIndex);
        this.mapCondition(this.qualifyIndex);
        this.checkInit = true;
    }

    private void mapCondition(int n) {
        if (n >= 0) {
            Expression expression = (Expression)this.expressions.get(n);
            SelectListColumnResolver selectListColumnResolver = new SelectListColumnResolver(this);
            expression.mapColumns(selectListColumnResolver, 0, 0);
        }
    }

    private int mergeGroupByExpressions(Database database, int n, ArrayList<String> arrayList, boolean bl) {
        int n2;
        if (this.groupByCopies != null) {
            int n3 = this.groupByCopies[n];
            if (n3 >= 0) {
                return n3;
            }
            if (n3 == -2) {
                return n;
            }
        } else {
            this.groupByCopies = new int[arrayList.size()];
            Arrays.fill(this.groupByCopies, -1);
        }
        String string = arrayList.get(n);
        if (bl) {
            for (n2 = 0; n2 < n; ++n2) {
                if (!database.equalsIdentifiers(string, arrayList.get(n2))) continue;
                n = n2;
                break;
            }
        }
        n2 = arrayList.size();
        for (int i = n + 1; i < n2; ++i) {
            if (!database.equalsIdentifiers(string, arrayList.get(i))) continue;
            this.groupByCopies[i] = n;
        }
        this.groupByCopies[n] = -2;
        return n;
    }

    @Override
    public void prepareExpressions() {
        Mode.ExpressionNames expressionNames;
        if (this.orderList != null) {
            this.prepareOrder(this.orderList, this.expressions.size());
        }
        if ((expressionNames = this.session.getMode().expressionNames) == Mode.ExpressionNames.ORIGINAL_SQL || expressionNames == Mode.ExpressionNames.POSTGRESQL_STYLE) {
            this.optimizeExpressionsAndPreserveAliases();
        } else {
            for (int i = 0; i < this.expressions.size(); ++i) {
                this.expressions.set(i, ((Expression)this.expressions.get(i)).optimize(this.session));
            }
        }
        if (this.sort != null) {
            this.cleanupOrder();
        }
        if (this.condition != null) {
            this.condition = this.condition.optimizeCondition(this.session);
        }
        if (this.isGroupQuery && this.groupIndex == null && this.havingIndex < 0 && this.qualifyIndex < 0 && this.condition == null && this.filters.size() == 1) {
            this.isQuickAggregateQuery = this.isEverything(ExpressionVisitor.getOptimizableVisitor(this.filters.get(0).getTable()));
        }
        this.expressionArray = this.expressions.toArray(new Expression[0]);
    }

    @Override
    public void preparePlan() {
        Object object;
        Object object2;
        Object object3;
        if (this.condition != null) {
            object3 = this.filters.iterator();
            while (object3.hasNext()) {
                object2 = object3.next();
                if (((TableFilter)object2).isJoinOuter() || ((TableFilter)object2).isJoinOuterIndirect()) continue;
                this.condition.createIndexConditions(this.session, (TableFilter)object2);
            }
        }
        this.cost = this.preparePlan(this.session.isParsingCreateView());
        if (this.distinct && this.getDatabase().getSettings().optimizeDistinct && !this.isGroupQuery && this.filters.size() == 1 && this.expressions.size() == 1 && this.condition == null) {
            object3 = (Expression)this.expressions.get(0);
            if ((object3 = ((Expression)object3).getNonAliasExpression()) instanceof ExpressionColumn) {
                Index index;
                object2 = ((ExpressionColumn)object3).getColumn();
                int n = ((Column)object2).getSelectivity();
                object = this.topTableFilter.getTable().getIndexForColumn((Column)object2, false, true);
                if (object != null && n != 50 && n < 20 && ((index = this.topTableFilter.getIndex()) == null || index.getIndexType().isScan() || object == index)) {
                    this.topTableFilter.setIndex((Index)object);
                    this.isDistinctQuery = true;
                }
            }
        }
        if (this.sort != null && !this.isQuickAggregateQuery && !this.isGroupQuery) {
            object3 = this.getSortIndex();
            object2 = this.topTableFilter.getIndex();
            if (object3 != null && object2 != null) {
                if (((Index)object2).getIndexType().isScan() || object2 == object3) {
                    this.topTableFilter.setIndex((Index)object3);
                    if (!this.topTableFilter.hasInComparisons()) {
                        this.sortUsingIndex = true;
                    }
                } else if (((Index)object3).getIndexColumns() != null && ((Index)object3).getIndexColumns().length >= ((Index)object2).getIndexColumns().length) {
                    IndexColumn[] indexColumnArray = ((Index)object3).getIndexColumns();
                    object = ((Index)object2).getIndexColumns();
                    boolean bl = false;
                    for (int i = 0; i < ((IndexColumn[])object).length; ++i) {
                        if (indexColumnArray[i].column != object[i].column) {
                            bl = false;
                            break;
                        }
                        if (indexColumnArray[i].sortType == object[i].sortType) continue;
                        bl = true;
                    }
                    if (bl) {
                        this.topTableFilter.setIndex((Index)object3);
                        this.sortUsingIndex = true;
                    }
                }
            }
            if (this.sortUsingIndex && this.forUpdate != null && !this.topTableFilter.getIndex().isRowIdIndex()) {
                this.sortUsingIndex = false;
            }
        }
        if (!this.isQuickAggregateQuery && this.isGroupQuery && (object3 = this.getGroupSortedIndex()) != null && (object2 = this.topTableFilter.getIndex()) != null && (((Index)object2).getIndexType().isScan() || object2 == object3)) {
            this.topTableFilter.setIndex((Index)object3);
            this.isGroupSortedQuery = true;
        }
        this.isPrepared = true;
    }

    private void optimizeExpressionsAndPreserveAliases() {
        for (int i = 0; i < this.expressions.size(); ++i) {
            Expression expression;
            Expression expression2 = (Expression)this.expressions.get(i);
            if (i < this.visibleColumnCount) {
                String string = expression2.getAlias(this.session, i);
                expression = expression2.optimize(this.session);
                if (!expression.getAlias(this.session, i).equals(string)) {
                    expression = new Alias(expression, string, true);
                }
            } else {
                expression = expression2.optimize(this.session);
            }
            this.expressions.set(i, expression);
        }
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public HashSet<Table> getTables() {
        HashSet<Table> hashSet = new HashSet<Table>();
        for (TableFilter tableFilter : this.filters) {
            hashSet.add(tableFilter.getTable());
        }
        return hashSet;
    }

    @Override
    public void fireBeforeSelectTriggers() {
        for (TableFilter tableFilter : this.filters) {
            tableFilter.getTable().fire(this.session, 8, true);
        }
    }

    private double preparePlan(boolean bl) {
        TableFilter[] tableFilterArray = this.topFilters.toArray(new TableFilter[0]);
        for (TableFilter tableFilter : tableFilterArray) {
            tableFilter.createIndexConditions();
            tableFilter.setFullCondition(this.condition);
        }
        Optimizer optimizer = new Optimizer(tableFilterArray, this.condition, this.session);
        optimizer.optimize(bl);
        this.topTableFilter = optimizer.getTopFilter();
        double d = optimizer.getCost();
        this.setEvaluatableRecursive(this.topTableFilter);
        if (!bl) {
            this.topTableFilter.prepare();
        }
        return d;
    }

    private void setEvaluatableRecursive(TableFilter tableFilter) {
        while (tableFilter != null) {
            Expression expression;
            TableFilter tableFilter2;
            tableFilter.setEvaluatable(tableFilter, true);
            if (this.condition != null) {
                this.condition.setEvaluatable(tableFilter, true);
            }
            if ((tableFilter2 = tableFilter.getNestedJoin()) != null) {
                this.setEvaluatableRecursive(tableFilter2);
            }
            if ((expression = tableFilter.getJoinCondition()) != null && !expression.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR)) {
                expression = expression.optimize(this.session);
                if (!tableFilter.isJoinOuter() && !tableFilter.isJoinOuterIndirect()) {
                    tableFilter.removeJoinCondition();
                    this.addCondition(expression);
                }
            }
            if ((expression = tableFilter.getFilterCondition()) != null && !expression.isEverything(ExpressionVisitor.EVALUATABLE_VISITOR)) {
                tableFilter.removeFilterCondition();
                this.addCondition(expression);
            }
            for (Expression expression2 : this.expressions) {
                expression2.setEvaluatable(tableFilter, true);
            }
            tableFilter = tableFilter.getJoin();
        }
    }

    @Override
    public String getPlanSQL(int n) {
        Expression[] expressionArray = this.expressions.toArray(new Expression[0]);
        StringBuilder stringBuilder = new StringBuilder();
        for (TableFilter tableFilter : this.topFilters) {
            Table table = tableFilter.getTable();
            TableView tableView = table instanceof TableView ? (TableView)table : null;
            if (tableView == null || !tableView.isRecursive() || !tableView.isTableExpression() || !tableView.isTemporary()) continue;
            stringBuilder.append("WITH RECURSIVE ");
            table.getSchema().getSQL(stringBuilder, n).append('.');
            ParserUtil.quoteIdentifier(stringBuilder, table.getName(), n).append('(');
            Column.writeColumns(stringBuilder, table.getColumns(), n);
            stringBuilder.append(") AS ");
            table.getSQL(stringBuilder, n).append('\n');
        }
        if (this.isExplicitTable) {
            stringBuilder.append("TABLE ");
            this.filters.get(0).getPlanSQL(stringBuilder, false, n);
        } else {
            block30: {
                stringBuilder.append("SELECT");
                if (this.isAnyDistinct()) {
                    stringBuilder.append(" DISTINCT");
                    if (this.distinctExpressions != null) {
                        Expression.writeExpressions(stringBuilder.append(" ON("), this.distinctExpressions, n).append(')');
                    }
                }
                for (int i = 0; i < this.visibleColumnCount; ++i) {
                    if (i > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append('\n');
                    StringUtils.indent(stringBuilder, expressionArray[i].getSQL(n, 2), 4, false);
                }
                TableFilter tableFilter = this.topTableFilter;
                if (tableFilter == null) {
                    int n2 = this.topFilters.size();
                    if (n2 != 1 || !this.topFilters.get(0).isNoFromClauseFilter()) {
                        stringBuilder.append("\nFROM ");
                        boolean bl = false;
                        for (int i = 0; i < n2; ++i) {
                            bl = Select.getPlanFromFilter(stringBuilder, n, this.topFilters.get(i), bl);
                        }
                    }
                } else if (!tableFilter.isNoFromClauseFilter()) {
                    Select.getPlanFromFilter(stringBuilder.append("\nFROM "), n, tableFilter, false);
                }
                if (this.condition != null) {
                    Select.getFilterSQL(stringBuilder, "\nWHERE ", this.condition, n);
                }
                if (this.groupIndex != null) {
                    stringBuilder.append("\nGROUP BY ");
                    int n3 = this.groupIndex.length;
                    for (int i = 0; i < n3; ++i) {
                        if (i > 0) {
                            stringBuilder.append(", ");
                        }
                        expressionArray[this.groupIndex[i]].getNonAliasExpression().getUnenclosedSQL(stringBuilder, n);
                    }
                } else if (this.group != null) {
                    stringBuilder.append("\nGROUP BY ");
                    int n4 = this.group.size();
                    for (int i = 0; i < n4; ++i) {
                        if (i > 0) {
                            stringBuilder.append(", ");
                        }
                        this.group.get(i).getUnenclosedSQL(stringBuilder, n);
                    }
                } else if (this.isGroupQuery && this.having == null && this.havingIndex < 0) {
                    for (int i = 0; i < this.visibleColumnCount; ++i) {
                        if (!Select.containsAggregate(expressionArray[i])) {
                            continue;
                        }
                        break block30;
                    }
                    stringBuilder.append("\nGROUP BY ()");
                }
            }
            Select.getFilterSQL(stringBuilder, "\nHAVING ", expressionArray, this.having, this.havingIndex, n);
            Select.getFilterSQL(stringBuilder, "\nQUALIFY ", expressionArray, this.qualify, this.qualifyIndex, n);
        }
        this.appendEndOfQueryToSQL(stringBuilder, n, expressionArray);
        if (this.forUpdate != null) {
            this.forUpdate.getSQL(stringBuilder, n);
        }
        if ((n & 8) != 0) {
            if (this.isQuickAggregateQuery) {
                stringBuilder.append("\n/* direct lookup */");
            }
            if (this.isDistinctQuery) {
                stringBuilder.append("\n/* distinct */");
            }
            if (this.sortUsingIndex) {
                stringBuilder.append("\n/* index sorted */");
            }
            if (this.isGroupQuery && this.isGroupSortedQuery) {
                stringBuilder.append("\n/* group sorted */");
            }
        }
        return stringBuilder.toString();
    }

    private static boolean getPlanFromFilter(StringBuilder stringBuilder, int n, TableFilter tableFilter, boolean bl) {
        do {
            if (bl) {
                stringBuilder.append('\n');
            }
            tableFilter.getPlanSQL(stringBuilder, bl, n);
            bl = true;
        } while ((tableFilter = tableFilter.getJoin()) != null);
        return bl;
    }

    private static void getFilterSQL(StringBuilder stringBuilder, String string, Expression[] expressionArray, Expression expression, int n, int n2) {
        if (expression != null) {
            Select.getFilterSQL(stringBuilder, string, expression, n2);
        } else if (n >= 0) {
            Select.getFilterSQL(stringBuilder, string, expressionArray[n], n2);
        }
    }

    private static void getFilterSQL(StringBuilder stringBuilder, String string, Expression expression, int n) {
        expression.getNonAliasExpression().getUnenclosedSQL(stringBuilder.append(string), n);
    }

    private static boolean containsAggregate(Expression expression) {
        if (expression instanceof DataAnalysisOperation && ((DataAnalysisOperation)expression).isAggregate()) {
            return true;
        }
        int n = expression.getSubexpressionCount();
        for (int i = 0; i < n; ++i) {
            if (!Select.containsAggregate(expression.getSubexpression(i))) continue;
            return true;
        }
        return false;
    }

    public void setHaving(Expression expression) {
        this.having = expression;
    }

    public Expression getHaving() {
        return this.having;
    }

    public void setQualify(Expression expression) {
        this.qualify = expression;
    }

    public Expression getQualify() {
        return this.qualify;
    }

    public TableFilter getTopTableFilter() {
        return this.topTableFilter;
    }

    @Override
    public ForUpdate getForUpdate() {
        return this.forUpdate;
    }

    @Override
    public void setForUpdate(ForUpdate forUpdate) {
        if (forUpdate != null && (this.isAnyDistinct() || this.isGroupQuery)) {
            throw DbException.get(90145);
        }
        this.forUpdate = forUpdate;
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n) {
        for (Expression expression : this.expressions) {
            expression.mapColumns(columnResolver, n, 0);
        }
        if (this.condition != null) {
            this.condition.mapColumns(columnResolver, n, 0);
        }
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        for (Expression expression : this.expressions) {
            expression.setEvaluatable(tableFilter, bl);
        }
        if (this.condition != null) {
            this.condition.setEvaluatable(tableFilter, bl);
        }
    }

    public boolean isQuickAggregateQuery() {
        return this.isQuickAggregateQuery;
    }

    public boolean isGroupQuery() {
        return this.isGroupQuery;
    }

    public boolean isWindowQuery() {
        return this.isWindowQuery;
    }

    public boolean isGroupWindowStage2() {
        return this.isGroupWindowStage2;
    }

    @Override
    public void addGlobalCondition(Parameter parameter, int n, int n2) {
        this.addParameter(parameter);
        Expression expression = (Expression)this.expressions.get(n);
        expression = expression.getNonAliasExpression();
        Expression expression2 = expression.isEverything(ExpressionVisitor.QUERY_COMPARABLE_VISITOR) ? new Comparison(n2, expression, parameter, false) : new Comparison(6, parameter, parameter, false);
        expression2 = ((Expression)expression2).optimize(this.session);
        if (this.isWindowQuery) {
            this.qualify = Select.addGlobalCondition(this.qualify, expression2);
        } else if (this.isGroupQuery) {
            for (int i = 0; this.groupIndex != null && i < this.groupIndex.length; ++i) {
                if (this.groupIndex[i] != n) continue;
                this.condition = Select.addGlobalCondition(this.condition, expression2);
                return;
            }
            if (this.havingIndex >= 0) {
                this.having = (Expression)this.expressions.get(this.havingIndex);
            }
            this.having = Select.addGlobalCondition(this.having, expression2);
        } else {
            this.condition = Select.addGlobalCondition(this.condition, expression2);
        }
    }

    private static Expression addGlobalCondition(Expression expression, Expression expression2) {
        Expression expression3;
        Expression expression4;
        if (!(expression instanceof ConditionLocalAndGlobal)) {
            return new ConditionLocalAndGlobal(expression, expression2);
        }
        if (expression.getSubexpressionCount() == 1) {
            expression4 = null;
            expression3 = expression.getSubexpression(0);
        } else {
            expression4 = expression.getSubexpression(0);
            expression3 = expression.getSubexpression(1);
        }
        return new ConditionLocalAndGlobal(expression4, new ConditionAndOr(0, expression3, expression2));
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        for (Expression expression : this.expressions) {
            expression.updateAggregate(sessionLocal, n);
        }
        if (this.condition != null) {
            this.condition.updateAggregate(sessionLocal, n);
        }
        if (this.having != null) {
            this.having.updateAggregate(sessionLocal, n);
        }
        if (this.qualify != null) {
            this.qualify.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                if (this.forUpdate != null) {
                    return false;
                }
                for (TableFilter object : this.filters) {
                    if (object.getTable().isDeterministic()) continue;
                    return false;
                }
                break;
            }
            case 4: {
                for (TableFilter tableFilter : this.filters) {
                    long l = tableFilter.getTable().getMaxDataModificationId();
                    expressionVisitor.addDataModificationId(l);
                }
                break;
            }
            case 3: {
                if (this.getDatabase().getSettings().optimizeEvaluatableSubqueries) break;
                return false;
            }
            case 7: {
                for (TableFilter tableFilter : this.filters) {
                    Table table = tableFilter.getTable();
                    expressionVisitor.addDependency(table);
                    table.addDependencies(expressionVisitor.getDependencies());
                }
                break;
            }
        }
        Iterator<TableFilter> iterator2 = expressionVisitor.incrementQueryLevel(1);
        for (Expression expression : this.expressions) {
            if (expression.isEverything((ExpressionVisitor)((Object)iterator2))) continue;
            return false;
        }
        for (TableFilter tableFilter : this.filters) {
            Expression expression = tableFilter.getJoinCondition();
            if (expression == null || expression.isEverything((ExpressionVisitor)((Object)iterator2))) continue;
            return false;
        }
        if (this.condition != null && !this.condition.isEverything((ExpressionVisitor)((Object)iterator2))) {
            return false;
        }
        if (this.having != null && !this.having.isEverything((ExpressionVisitor)((Object)iterator2))) {
            return false;
        }
        return this.qualify == null || this.qualify.isEverything((ExpressionVisitor)((Object)iterator2));
    }

    @Override
    public boolean isCacheable() {
        return this.forUpdate == null;
    }

    @Override
    public boolean allowGlobalConditions() {
        return this.offsetExpr == null && this.fetchExpr == null && this.distinctExpressions == null;
    }

    public SortOrder getSortOrder() {
        return this.sort;
    }

    public Select getParentSelect() {
        return this.parentSelect;
    }

    @Override
    public boolean isConstantQuery() {
        if (!super.isConstantQuery() || this.distinctExpressions != null || this.condition != null || this.isGroupQuery || this.isWindowQuery || !this.isNoFromClause()) {
            return false;
        }
        for (int i = 0; i < this.visibleColumnCount; ++i) {
            if (((Expression)this.expressions.get(i)).isConstant()) continue;
            return false;
        }
        return true;
    }

    @Override
    public Expression getIfSingleRow() {
        if (this.offsetExpr != null || this.fetchExpr != null || this.condition != null || this.isGroupQuery || this.isWindowQuery || !this.isNoFromClause()) {
            return null;
        }
        if (this.visibleColumnCount == 1) {
            return (Expression)this.expressions.get(0);
        }
        Expression[] expressionArray = new Expression[this.visibleColumnCount];
        for (int i = 0; i < this.visibleColumnCount; ++i) {
            expressionArray[i] = (Expression)this.expressions.get(i);
        }
        return new ExpressionList(expressionArray, false);
    }

    private boolean isNoFromClause() {
        if (this.topTableFilter != null) {
            return this.topTableFilter.isNoFromClauseFilter();
        }
        if (this.topFilters.size() == 1) {
            return this.topFilters.get(0).isNoFromClauseFilter();
        }
        return false;
    }

    private final class LazyResultGroupSorted
    extends LazyResultSelect {
        private Value[] previousKeyValues;

        LazyResultGroupSorted(Expression[] expressionArray, int n) {
            super(expressionArray, n);
            if (Select.this.groupData == null) {
                Select.this.setGroupData(SelectGroups.getInstance(Select.this.getSession(), Select.this.expressions, Select.this.isGroupQuery, Select.this.groupIndex));
            } else {
                Select.this.updateAgg(n, 0);
                Select.this.groupData.resetLazy();
            }
        }

        @Override
        public void reset() {
            super.reset();
            Select.this.groupData.resetLazy();
            this.previousKeyValues = null;
        }

        @Override
        protected Value[] fetchNextRow() {
            while (Select.this.topTableFilter.next()) {
                Select.this.setCurrentRowNumber(this.rowNumber + 1L);
                if (!Select.this.isConditionMet()) continue;
                ++this.rowNumber;
                int n = Select.this.groupIndex.length;
                Value[] valueArray = new Value[n];
                for (int i = 0; i < n; ++i) {
                    int n2 = Select.this.groupIndex[i];
                    Expression expression = (Expression)Select.this.expressions.get(n2);
                    valueArray[i] = expression.getValue(Select.this.getSession());
                }
                Value[] valueArray2 = null;
                if (this.previousKeyValues == null) {
                    this.previousKeyValues = valueArray;
                    Select.this.groupData.nextLazyGroup();
                } else {
                    SessionLocal sessionLocal = Select.this.getSession();
                    for (int i = 0; i < n; ++i) {
                        if (sessionLocal.compare(this.previousKeyValues[i], valueArray[i]) == 0) continue;
                        valueArray2 = Select.this.createGroupSortedRow(this.previousKeyValues, this.columnCount);
                        this.previousKeyValues = valueArray;
                        Select.this.groupData.nextLazyGroup();
                        break;
                    }
                }
                Select.this.groupData.nextLazyRow();
                Select.this.updateAgg(this.columnCount, 1);
                if (valueArray2 == null) continue;
                return valueArray2;
            }
            Value[] valueArray = null;
            if (this.previousKeyValues != null) {
                valueArray = Select.this.createGroupSortedRow(this.previousKeyValues, this.columnCount);
                this.previousKeyValues = null;
            }
            return valueArray;
        }
    }

    private final class LazyResultQueryFlat
    extends LazyResultSelect {
        private boolean forUpdate;

        LazyResultQueryFlat(Expression[] expressionArray, int n, boolean bl) {
            super(expressionArray, n);
            this.forUpdate = bl;
        }

        @Override
        protected Value[] fetchNextRow() {
            while (Select.this.topTableFilter.next()) {
                Select.this.setCurrentRowNumber(this.rowNumber + 1L);
                if (!(this.forUpdate ? Select.this.isConditionMetForUpdate() : Select.this.isConditionMet())) continue;
                ++this.rowNumber;
                Value[] valueArray = new Value[this.columnCount];
                for (int i = 0; i < this.columnCount; ++i) {
                    Expression expression = (Expression)Select.this.expressions.get(i);
                    valueArray[i] = expression.getValue(Select.this.getSession());
                }
                return valueArray;
            }
            return null;
        }

        @Override
        protected boolean skipNextRow() {
            while (Select.this.topTableFilter.next()) {
                Select.this.setCurrentRowNumber(this.rowNumber + 1L);
                if (!Select.this.isConditionMet()) continue;
                ++this.rowNumber;
                return true;
            }
            return false;
        }
    }

    private abstract class LazyResultSelect
    extends LazyResult {
        long rowNumber;
        int columnCount;

        LazyResultSelect(Expression[] expressionArray, int n) {
            super(Select.this.getSession(), expressionArray);
            this.columnCount = n;
            Select.this.setCurrentRowNumber(0L);
        }

        @Override
        public final int getVisibleColumnCount() {
            return Select.this.visibleColumnCount;
        }

        @Override
        public void reset() {
            super.reset();
            Select.this.topTableFilter.reset();
            Select.this.setCurrentRowNumber(0L);
            this.rowNumber = 0L;
        }
    }
}

