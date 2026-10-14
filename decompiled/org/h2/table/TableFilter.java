/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.command.query.Select;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.condition.ConditionAndOr;
import org.h2.index.Index;
import org.h2.index.IndexCondition;
import org.h2.index.IndexCursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.DualTable;
import org.h2.table.IndexHints;
import org.h2.table.PlanItem;
import org.h2.table.Table;
import org.h2.table.TableView;
import org.h2.table.VirtualTable;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTinyint;

public class TableFilter
implements ColumnResolver {
    private static final int BEFORE_FIRST = 0;
    private static final int FOUND = 1;
    private static final int AFTER_LAST = 2;
    private static final int NULL_ROW = 3;
    public static final Comparator<TableFilter> ORDER_IN_FROM_COMPARATOR = Comparator.comparing(TableFilter::getOrderInFrom);
    private static final TableFilterVisitor JOI_VISITOR = tableFilter -> {
        tableFilter.joinOuterIndirect = true;
    };
    protected boolean joinOuterIndirect;
    private SessionLocal session;
    private final Table table;
    private final Select select;
    private String alias;
    private Index index;
    private final IndexHints indexHints;
    private int[] masks;
    private int scanCount;
    private boolean evaluatable;
    private boolean used;
    private final IndexCursor cursor;
    private final ArrayList<IndexCondition> indexConditions = Utils.newSmallArrayList();
    private Expression filterCondition;
    private Expression joinCondition;
    private SearchRow currentSearchRow;
    private Row current;
    private int state;
    private TableFilter join;
    private boolean joinOuter;
    private TableFilter nestedJoin;
    private LinkedHashMap<Column, Column> commonJoinColumns;
    private TableFilter commonJoinColumnsFilter;
    private ArrayList<Column> commonJoinColumnsToExclude;
    private boolean foundOne;
    private Expression fullCondition;
    private final int hashCode;
    private final int orderInFrom;
    private LinkedHashMap<Column, String> derivedColumnMap;

    public TableFilter(SessionLocal sessionLocal, Table table, String string, boolean bl, Select select, int n, IndexHints indexHints) {
        this.session = sessionLocal;
        this.table = table;
        this.alias = string;
        this.select = select;
        this.cursor = new IndexCursor();
        if (!bl) {
            sessionLocal.getUser().checkTableRight(table, 1);
        }
        this.hashCode = sessionLocal.nextObjectId();
        this.orderInFrom = n;
        this.indexHints = indexHints;
    }

    public int getOrderInFrom() {
        return this.orderInFrom;
    }

    public IndexCursor getIndexCursor() {
        return this.cursor;
    }

    @Override
    public Select getSelect() {
        return this.select;
    }

    public Table getTable() {
        return this.table;
    }

    public void lock(SessionLocal sessionLocal) {
        this.table.lock(sessionLocal, 0);
        if (this.join != null) {
            this.join.lock(sessionLocal);
        }
    }

    public PlanItem getBestPlanItem(SessionLocal sessionLocal, TableFilter[] tableFilterArray, int n, AllColumnsForPlan allColumnsForPlan) {
        PlanItem planItem = null;
        SortOrder sortOrder = null;
        if (this.select != null) {
            sortOrder = this.select.getSortOrder();
        }
        if (this.indexConditions.isEmpty()) {
            planItem = new PlanItem();
            planItem.setIndex(this.table.getScanIndex(sessionLocal, null, tableFilterArray, n, sortOrder, allColumnsForPlan));
            planItem.cost = planItem.getIndex().getCost(sessionLocal, null, tableFilterArray, n, sortOrder, allColumnsForPlan);
        }
        int n2 = this.table.getColumns().length;
        int[] nArray = new int[n2];
        for (IndexCondition indexCondition : this.indexConditions) {
            if (!indexCondition.isEvaluatable()) continue;
            if (indexCondition.isAlwaysFalse()) {
                nArray = null;
                break;
            }
            int n3 = indexCondition.getColumn().getColumnId();
            if (n3 < 0) continue;
            int n4 = n3;
            nArray[n4] = nArray[n4] | indexCondition.getMask(this.indexConditions);
        }
        Object object = this.table.getBestPlanItem(sessionLocal, nArray, tableFilterArray, n, sortOrder, allColumnsForPlan);
        ((PlanItem)object).setMasks(nArray);
        ((PlanItem)object).cost -= ((PlanItem)object).cost * (double)this.indexConditions.size() / 100.0 / (double)(n + 1);
        if (planItem != null && planItem.cost < ((PlanItem)object).cost) {
            object = planItem;
        }
        if (this.nestedJoin != null) {
            this.setEvaluatable(true);
            ((PlanItem)object).setNestedJoinPlan(this.nestedJoin.getBestPlanItem(sessionLocal, tableFilterArray, n, allColumnsForPlan));
            ((PlanItem)object).cost += ((PlanItem)object).cost * ((PlanItem)object).getNestedJoinPlan().cost;
        }
        if (this.join != null) {
            this.setEvaluatable(true);
            while (tableFilterArray[++n] != this.join) {
            }
            ((PlanItem)object).setJoinPlan(this.join.getBestPlanItem(sessionLocal, tableFilterArray, n, allColumnsForPlan));
            ((PlanItem)object).cost += ((PlanItem)object).cost * ((PlanItem)object).getJoinPlan().cost;
        }
        return object;
    }

    public void setPlanItem(PlanItem planItem) {
        if (planItem == null) {
            return;
        }
        this.setIndex(planItem.getIndex());
        this.masks = planItem.getMasks();
        if (this.nestedJoin != null) {
            if (planItem.getNestedJoinPlan() != null) {
                this.nestedJoin.setPlanItem(planItem.getNestedJoinPlan());
            } else {
                this.nestedJoin.setScanIndexes();
            }
        }
        if (this.join != null) {
            if (planItem.getJoinPlan() != null) {
                this.join.setPlanItem(planItem.getJoinPlan());
            } else {
                this.join.setScanIndexes();
            }
        }
    }

    private void setScanIndexes() {
        if (this.index == null) {
            this.setIndex(this.table.getScanIndex(this.session));
        }
        if (this.join != null) {
            this.join.setScanIndexes();
        }
        if (this.nestedJoin != null) {
            this.nestedJoin.setScanIndexes();
        }
    }

    public void prepare() {
        for (int i = 0; i < this.indexConditions.size(); ++i) {
            Column column;
            IndexCondition indexCondition = this.indexConditions.get(i);
            if (indexCondition.isAlwaysFalse() || (column = indexCondition.getColumn()).getColumnId() < 0 || this.index.getColumnIndex(column) >= 0) continue;
            this.indexConditions.remove(i);
            --i;
        }
        if (this.nestedJoin != null) {
            if (this.nestedJoin == this) {
                throw DbException.getInternalError("self join");
            }
            this.nestedJoin.prepare();
        }
        if (this.join != null) {
            if (this.join == this) {
                throw DbException.getInternalError("self join");
            }
            this.join.prepare();
        }
        if (this.filterCondition != null) {
            this.filterCondition = this.filterCondition.optimizeCondition(this.session);
        }
        if (this.joinCondition != null) {
            this.joinCondition = this.joinCondition.optimizeCondition(this.session);
        }
    }

    public void startQuery(SessionLocal sessionLocal) {
        this.session = sessionLocal;
        this.scanCount = 0;
        if (this.nestedJoin != null) {
            this.nestedJoin.startQuery(sessionLocal);
        }
        if (this.join != null) {
            this.join.startQuery(sessionLocal);
        }
    }

    public void reset() {
        if (this.nestedJoin != null) {
            this.nestedJoin.reset();
        }
        if (this.join != null) {
            this.join.reset();
        }
        this.state = 0;
        this.foundOne = false;
    }

    public boolean next() {
        if (this.state == 2) {
            return false;
        }
        if (this.state == 0) {
            this.cursor.find(this.session, this.indexConditions);
            if (!this.cursor.isAlwaysFalse()) {
                if (this.nestedJoin != null) {
                    this.nestedJoin.reset();
                }
                if (this.join != null) {
                    this.join.reset();
                }
            }
        } else if (this.join != null && this.join.next()) {
            return true;
        }
        while (this.state != 3) {
            if (this.cursor.isAlwaysFalse()) {
                this.state = 2;
            } else if (this.nestedJoin != null) {
                if (this.state == 0) {
                    this.state = 1;
                }
            } else {
                if ((++this.scanCount & 0xFFF) == 0) {
                    this.checkTimeout();
                }
                if (this.cursor.next()) {
                    this.currentSearchRow = this.cursor.getSearchRow();
                    this.current = null;
                    this.state = 1;
                } else {
                    this.state = 2;
                }
            }
            if (this.nestedJoin != null && this.state == 1 && !this.nestedJoin.next()) {
                this.state = 2;
                if (!this.joinOuter || this.foundOne) continue;
            }
            if (this.state == 2) {
                if (!this.joinOuter || this.foundOne) break;
                this.setNullRow();
            }
            if (!this.isOk(this.filterCondition)) continue;
            boolean bl = this.isOk(this.joinCondition);
            if (this.state == 1) {
                if (!bl) continue;
                this.foundOne = true;
            }
            if (this.join != null) {
                this.join.reset();
                if (!this.join.next()) continue;
            }
            if (this.state != 3 && !bl) continue;
            return true;
        }
        this.state = 2;
        return false;
    }

    public boolean isNullRow() {
        return this.state == 3;
    }

    protected void setNullRow() {
        this.state = 3;
        this.current = this.table.getNullRow();
        this.currentSearchRow = this.current;
        if (this.nestedJoin != null) {
            this.nestedJoin.visit(TableFilter::setNullRow);
        }
    }

    private void checkTimeout() {
        this.session.checkCanceled();
    }

    boolean isOk(Expression expression) {
        return expression == null || expression.getBooleanValue(this.session);
    }

    public Row get() {
        if (this.current == null && this.currentSearchRow != null) {
            this.current = this.cursor.get();
        }
        return this.current;
    }

    public void set(Row row) {
        this.current = row;
        this.currentSearchRow = row;
    }

    @Override
    public String getTableAlias() {
        if (this.alias != null) {
            return this.alias;
        }
        return this.table.getName();
    }

    public void addIndexCondition(IndexCondition indexCondition) {
        this.indexConditions.add(indexCondition);
    }

    public void addFilterCondition(Expression expression, boolean bl) {
        if (bl) {
            this.joinCondition = this.joinCondition == null ? expression : new ConditionAndOr(0, this.joinCondition, expression);
        } else {
            this.filterCondition = this.filterCondition == null ? expression : new ConditionAndOr(0, this.filterCondition, expression);
        }
    }

    public void addJoin(TableFilter tableFilter, boolean bl, Expression expression) {
        if (expression != null) {
            expression.mapColumns(this, 0, 0);
            MapColumnsVisitor mapColumnsVisitor = new MapColumnsVisitor(expression);
            this.visit(mapColumnsVisitor);
            tableFilter.visit(mapColumnsVisitor);
        }
        if (this.join == null) {
            this.join = tableFilter;
            tableFilter.joinOuter = bl;
            if (bl) {
                tableFilter.visit(JOI_VISITOR);
            }
            if (expression != null) {
                tableFilter.mapAndAddFilter(expression);
            }
        } else {
            this.join.addJoin(tableFilter, bl, expression);
        }
    }

    public void setNestedJoin(TableFilter tableFilter) {
        this.nestedJoin = tableFilter;
    }

    public void mapAndAddFilter(Expression expression) {
        expression.mapColumns(this, 0, 0);
        this.addFilterCondition(expression, true);
        if (this.nestedJoin != null) {
            expression.mapColumns(this.nestedJoin, 0, 0);
        }
        if (this.join != null) {
            this.join.mapAndAddFilter(expression);
        }
    }

    public void createIndexConditions() {
        if (this.joinCondition != null) {
            this.joinCondition = this.joinCondition.optimizeCondition(this.session);
            if (this.joinCondition != null) {
                this.joinCondition.createIndexConditions(this.session, this);
                if (this.nestedJoin != null) {
                    this.joinCondition.createIndexConditions(this.session, this.nestedJoin);
                }
            }
        }
        if (this.join != null) {
            this.join.createIndexConditions();
        }
        if (this.nestedJoin != null) {
            this.nestedJoin.createIndexConditions();
        }
    }

    public TableFilter getJoin() {
        return this.join;
    }

    public boolean isJoinOuter() {
        return this.joinOuter;
    }

    public boolean isJoinOuterIndirect() {
        return this.joinOuterIndirect;
    }

    public StringBuilder getPlanSQL(StringBuilder stringBuilder, boolean bl, int n) {
        boolean bl2;
        if (bl) {
            if (this.joinOuter) {
                stringBuilder.append("LEFT OUTER JOIN ");
            } else {
                stringBuilder.append("INNER JOIN ");
            }
        }
        if (this.nestedJoin != null) {
            boolean bl3;
            StringBuilder stringBuilder2 = new StringBuilder();
            TableFilter tableFilter = this.nestedJoin;
            do {
                tableFilter.getPlanSQL(stringBuilder2, tableFilter != this.nestedJoin, n).append('\n');
            } while ((tableFilter = tableFilter.getJoin()) != null);
            String string = stringBuilder2.toString();
            boolean bl4 = bl3 = !string.startsWith("(");
            if (bl3) {
                stringBuilder.append("(\n");
            }
            StringUtils.indent(stringBuilder, string, 4, false);
            if (bl3) {
                stringBuilder.append(')');
            }
            if (bl) {
                stringBuilder.append(" ON ");
                if (this.joinCondition == null) {
                    stringBuilder.append("1=1");
                } else {
                    this.joinCondition.getUnenclosedSQL(stringBuilder, n);
                }
            }
            return stringBuilder;
        }
        if (this.table instanceof TableView && ((TableView)this.table).isRecursive()) {
            this.table.getSchema().getSQL(stringBuilder, n).append('.');
            ParserUtil.quoteIdentifier(stringBuilder, this.table.getName(), n);
        } else {
            this.table.getSQL(stringBuilder, n);
        }
        if (this.table instanceof TableView && ((TableView)this.table).isInvalid()) {
            throw DbException.get(90109, this.table.getName(), "not compiled");
        }
        if (this.alias != null) {
            stringBuilder.append(' ');
            ParserUtil.quoteIdentifier(stringBuilder, this.alias, n);
            if (this.derivedColumnMap != null) {
                stringBuilder.append('(');
                bl2 = false;
                for (String string : this.derivedColumnMap.values()) {
                    if (bl2) {
                        stringBuilder.append(", ");
                    }
                    bl2 = true;
                    ParserUtil.quoteIdentifier(stringBuilder, string, n);
                }
                stringBuilder.append(')');
            }
        }
        if (this.indexHints != null) {
            stringBuilder.append(" USE INDEX (");
            bl2 = true;
            for (String string : this.indexHints.getAllowedIndexes()) {
                if (!bl2) {
                    stringBuilder.append(", ");
                } else {
                    bl2 = false;
                }
                ParserUtil.quoteIdentifier(stringBuilder, string, n);
            }
            stringBuilder.append(")");
        }
        if (this.index != null && (n & 8) != 0) {
            stringBuilder.append('\n');
            StringBuilder stringBuilder3 = new StringBuilder().append("/* ").append(this.index.getPlanSQL());
            if (!this.indexConditions.isEmpty()) {
                stringBuilder3.append(": ");
                int n2 = this.indexConditions.size();
                for (int i = 0; i < n2; ++i) {
                    if (i > 0) {
                        stringBuilder3.append("\n    AND ");
                    }
                    stringBuilder3.append(this.indexConditions.get(i).getSQL(11));
                }
            }
            if (stringBuilder3.indexOf("\n", 3) >= 0) {
                stringBuilder3.append('\n');
            }
            StringUtils.indent(stringBuilder, stringBuilder3.append(" */").toString(), 4, false);
        }
        if (bl) {
            stringBuilder.append("\n    ON ");
            if (this.joinCondition == null) {
                stringBuilder.append("1=1");
            } else {
                this.joinCondition.getUnenclosedSQL(stringBuilder, n);
            }
        }
        if ((n & 8) != 0) {
            if (this.filterCondition != null) {
                stringBuilder.append('\n');
                String string = this.filterCondition.getSQL(11, 2);
                string = "/* WHERE " + string + "\n*/";
                StringUtils.indent(stringBuilder, string, 4, false);
            }
            if (this.scanCount > 0) {
                stringBuilder.append("\n    /* scanCount: ").append(this.scanCount).append(" */");
            }
        }
        return stringBuilder;
    }

    void removeUnusableIndexConditions() {
        for (int i = 0; i < this.indexConditions.size(); ++i) {
            IndexCondition indexCondition = this.indexConditions.get(i);
            if (indexCondition.getMask(this.indexConditions) != 0 && indexCondition.isEvaluatable()) continue;
            this.indexConditions.remove(i--);
        }
    }

    public int[] getMasks() {
        return this.masks;
    }

    public ArrayList<IndexCondition> getIndexConditions() {
        return this.indexConditions;
    }

    public Index getIndex() {
        return this.index;
    }

    public void setIndex(Index index) {
        this.index = index;
        this.cursor.setIndex(index);
    }

    public void setUsed(boolean bl) {
        this.used = bl;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void removeJoin() {
        this.join = null;
    }

    public Expression getJoinCondition() {
        return this.joinCondition;
    }

    public void removeJoinCondition() {
        this.joinCondition = null;
    }

    public Expression getFilterCondition() {
        return this.filterCondition;
    }

    public void removeFilterCondition() {
        this.filterCondition = null;
    }

    public void setFullCondition(Expression expression) {
        this.fullCondition = expression;
        if (this.join != null) {
            this.join.setFullCondition(expression);
        }
    }

    void optimizeFullCondition() {
        if (!this.joinOuter && this.fullCondition != null) {
            this.fullCondition.addFilterConditions(this);
            if (this.nestedJoin != null) {
                this.nestedJoin.optimizeFullCondition();
            }
            if (this.join != null) {
                this.join.optimizeFullCondition();
            }
        }
    }

    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        tableFilter.setEvaluatable(bl);
        if (this.filterCondition != null) {
            this.filterCondition.setEvaluatable(tableFilter, bl);
        }
        if (this.joinCondition != null) {
            this.joinCondition.setEvaluatable(tableFilter, bl);
        }
        if (this.nestedJoin != null && this == tableFilter) {
            this.nestedJoin.setEvaluatable(this.nestedJoin, bl);
        }
        if (this.join != null) {
            this.join.setEvaluatable(tableFilter, bl);
        }
    }

    public void setEvaluatable(boolean bl) {
        this.evaluatable = bl;
    }

    @Override
    public String getSchemaName() {
        if (this.alias == null && !(this.table instanceof VirtualTable)) {
            return this.table.getSchema().getName();
        }
        return null;
    }

    @Override
    public Column[] getColumns() {
        return this.table.getColumns();
    }

    @Override
    public Column findColumn(String string) {
        LinkedHashMap<Column, String> linkedHashMap = this.derivedColumnMap;
        if (linkedHashMap != null) {
            Database database = this.session.getDatabase();
            for (Map.Entry<Column, String> entry : this.derivedColumnMap.entrySet()) {
                if (!database.equalsIdentifiers(entry.getValue(), string)) continue;
                return entry.getKey();
            }
            return null;
        }
        return this.table.findColumn(string);
    }

    @Override
    public String getColumnName(Column column) {
        LinkedHashMap<Column, String> linkedHashMap = this.derivedColumnMap;
        return linkedHashMap != null ? (String)((HashMap)linkedHashMap).get(column) : column.getName();
    }

    @Override
    public boolean hasDerivedColumnList() {
        return this.derivedColumnMap != null;
    }

    public Column getColumn(String string, boolean bl) {
        LinkedHashMap<Column, String> linkedHashMap = this.derivedColumnMap;
        if (linkedHashMap != null) {
            Database database = this.session.getDatabase();
            for (Map.Entry entry : ((HashMap)linkedHashMap).entrySet()) {
                if (!database.equalsIdentifiers(string, (String)entry.getValue())) continue;
                return (Column)entry.getKey();
            }
            if (bl) {
                return null;
            }
            throw DbException.get(42122, string);
        }
        return this.table.getColumn(string, bl);
    }

    @Override
    public Column[] getSystemColumns() {
        if (!this.session.getDatabase().getMode().systemColumns) {
            return null;
        }
        Column[] columnArray = new Column[]{new Column("oid", TypeInfo.TYPE_INTEGER, this.table, 0), new Column("ctid", TypeInfo.TYPE_VARCHAR, this.table, 0)};
        return columnArray;
    }

    @Override
    public Column getRowIdColumn() {
        return this.table.getRowIdColumn();
    }

    @Override
    public Value getValue(Column column) {
        if (this.currentSearchRow == null) {
            return null;
        }
        int n = column.getColumnId();
        if (n == -1) {
            return ValueBigint.get(this.currentSearchRow.getKey());
        }
        if (this.current == null) {
            Value value = this.currentSearchRow.getValue(n);
            if (value != null) {
                return value;
            }
            if (n == column.getTable().getMainIndexColumn()) {
                return this.getDelegatedValue(column);
            }
            this.current = this.cursor.get();
            if (this.current == null) {
                return ValueNull.INSTANCE;
            }
        }
        return this.current.getValue(n);
    }

    private Value getDelegatedValue(Column column) {
        long l = this.currentSearchRow.getKey();
        switch (column.getType().getValueType()) {
            case 9: {
                return ValueTinyint.get((byte)l);
            }
            case 10: {
                return ValueSmallint.get((short)l);
            }
            case 11: {
                return ValueInteger.get((int)l);
            }
            case 12: {
                return ValueBigint.get(l);
            }
        }
        throw DbException.getInternalError();
    }

    @Override
    public TableFilter getTableFilter() {
        return this;
    }

    public void setAlias(String string) {
        this.alias = string;
    }

    public void setDerivedColumns(ArrayList<String> arrayList) {
        Column[] columnArray = this.getColumns();
        int n = columnArray.length;
        if (n != arrayList.size()) {
            throw DbException.get(21002);
        }
        LinkedHashMap<Column, String> linkedHashMap = new LinkedHashMap<Column, String>();
        for (int i = 0; i < n; ++i) {
            String string = arrayList.get(i);
            for (int j = 0; j < i; ++j) {
                if (!string.equals(arrayList.get(j))) continue;
                throw DbException.get(42121, string);
            }
            linkedHashMap.put(columnArray[i], string);
        }
        this.derivedColumnMap = linkedHashMap;
    }

    public String toString() {
        return this.alias != null ? this.alias : this.table.toString();
    }

    public void addCommonJoinColumns(Column column, Column column2, TableFilter tableFilter) {
        if (this.commonJoinColumns == null) {
            this.commonJoinColumns = new LinkedHashMap();
            this.commonJoinColumnsFilter = tableFilter;
        } else assert (this.commonJoinColumnsFilter == tableFilter);
        this.commonJoinColumns.put(column, column2);
    }

    public void addCommonJoinColumnToExclude(Column column) {
        if (this.commonJoinColumnsToExclude == null) {
            this.commonJoinColumnsToExclude = Utils.newSmallArrayList();
        }
        this.commonJoinColumnsToExclude.add(column);
    }

    public LinkedHashMap<Column, Column> getCommonJoinColumns() {
        return this.commonJoinColumns;
    }

    public TableFilter getCommonJoinColumnsFilter() {
        return this.commonJoinColumnsFilter;
    }

    public boolean isCommonJoinColumnToExclude(Column column) {
        return this.commonJoinColumnsToExclude != null && this.commonJoinColumnsToExclude.contains(column);
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean hasInComparisons() {
        for (IndexCondition indexCondition : this.indexConditions) {
            int n = indexCondition.getCompareType();
            switch (n) {
                case 10: 
                case 11: 
                case 12: {
                    return true;
                }
            }
        }
        return false;
    }

    public TableFilter getNestedJoin() {
        return this.nestedJoin;
    }

    public void visit(TableFilterVisitor tableFilterVisitor) {
        TableFilter tableFilter = this;
        do {
            tableFilterVisitor.accept(tableFilter);
            TableFilter tableFilter2 = tableFilter.nestedJoin;
            if (tableFilter2 == null) continue;
            tableFilter2.visit(tableFilterVisitor);
        } while ((tableFilter = tableFilter.join) != null);
    }

    public boolean isEvaluatable() {
        return this.evaluatable;
    }

    public SessionLocal getSession() {
        return this.session;
    }

    public IndexHints getIndexHints() {
        return this.indexHints;
    }

    public boolean isNoFromClauseFilter() {
        return this.table instanceof DualTable && this.join == null && this.nestedJoin == null && this.joinCondition == null && this.filterCondition == null;
    }

    private static final class MapColumnsVisitor
    implements TableFilterVisitor {
        private final Expression on;

        MapColumnsVisitor(Expression expression) {
            this.on = expression;
        }

        @Override
        public void accept(TableFilter tableFilter) {
            this.on.mapColumns(tableFilter, 0, 0);
        }
    }

    public static interface TableFilterVisitor {
        public void accept(TableFilter var1);
    }
}

