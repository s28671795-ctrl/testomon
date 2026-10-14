/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import org.h2.command.Prepared;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.constraint.Constraint;
import org.h2.engine.CastDataProvider;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.result.DefaultRow;
import org.h2.result.LocalResult;
import org.h2.result.Row;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.result.SimpleRowValue;
import org.h2.result.SortOrder;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.TriggerObject;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.IndexHints;
import org.h2.table.MaterializedView;
import org.h2.table.PlanItem;
import org.h2.table.TableFilter;
import org.h2.table.TableSynonym;
import org.h2.table.TableType;
import org.h2.table.TableView;
import org.h2.util.HasSQL;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public abstract class Table
extends SchemaObject {
    public static final int TYPE_CACHED = 0;
    public static final int TYPE_MEMORY = 1;
    public static final int READ_LOCK = 0;
    public static final int WRITE_LOCK = 1;
    public static final int EXCLUSIVE_LOCK = 2;
    protected Column[] columns;
    protected CompareMode compareMode;
    protected boolean isHidden;
    private final HashMap<String, Column> columnMap;
    private final boolean persistIndexes;
    private final boolean persistData;
    private ArrayList<TriggerObject> triggers;
    private ArrayList<Constraint> constraints;
    private ArrayList<Sequence> sequences;
    private final CopyOnWriteArrayList<TableView> dependentViews = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<MaterializedView> dependentMaterializedViews = new CopyOnWriteArrayList();
    private ArrayList<TableSynonym> synonyms;
    private boolean checkForeignKeyConstraints = true;
    private boolean onCommitDrop;
    private boolean onCommitTruncate;
    private volatile Row nullRow;
    private RowFactory rowFactory = RowFactory.getRowFactory();
    private boolean tableExpression;

    protected Table(Schema schema, int n, String string, boolean bl, boolean bl2) {
        super(schema, n, string, 11);
        this.columnMap = schema.getDatabase().newStringMap();
        this.persistIndexes = bl;
        this.persistData = bl2;
        this.compareMode = schema.getDatabase().getCompareMode();
    }

    @Override
    public void rename(String string) {
        super.rename(string);
        if (this.constraints != null) {
            for (Constraint constraint : this.constraints) {
                constraint.rebuild();
            }
        }
    }

    public boolean isView() {
        return false;
    }

    public boolean lock(SessionLocal sessionLocal, int n) {
        return false;
    }

    public abstract void close(SessionLocal var1);

    public void unlock(SessionLocal sessionLocal) {
    }

    public abstract Index addIndex(SessionLocal var1, String var2, int var3, IndexColumn[] var4, int var5, IndexType var6, boolean var7, String var8);

    public Row getRow(SessionLocal sessionLocal, long l) {
        return null;
    }

    public boolean isInsertable() {
        return true;
    }

    public abstract void removeRow(SessionLocal var1, Row var2);

    public Row lockRow(SessionLocal sessionLocal, Row row, int n) {
        throw DbException.getUnsupportedException("lockRow()");
    }

    public abstract long truncate(SessionLocal var1);

    public abstract void addRow(SessionLocal var1, Row var2);

    public void updateRow(SessionLocal sessionLocal, Row row, Row row2) {
        row2.setKey(row.getKey());
        this.removeRow(sessionLocal, row);
        this.addRow(sessionLocal, row2);
    }

    public abstract void checkSupportAlter();

    public abstract TableType getTableType();

    public String getSQLTableType() {
        if (this.isView()) {
            return "VIEW";
        }
        if (this.isTemporary()) {
            return this.isGlobalTemporary() ? "GLOBAL TEMPORARY" : "LOCAL TEMPORARY";
        }
        return "BASE TABLE";
    }

    public abstract Index getScanIndex(SessionLocal var1);

    public Index getScanIndex(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        return this.getScanIndex(sessionLocal);
    }

    public abstract ArrayList<Index> getIndexes();

    public Index getIndex(String string) {
        ArrayList<Index> arrayList = this.getIndexes();
        if (arrayList != null) {
            for (Index index : arrayList) {
                if (!index.getName().equals(string)) continue;
                return index;
            }
        }
        throw DbException.get(42112, string);
    }

    public boolean isLockedExclusively() {
        return false;
    }

    public abstract long getMaxDataModificationId();

    public abstract boolean isDeterministic();

    public abstract boolean canGetRowCount(SessionLocal var1);

    public boolean canReference() {
        return true;
    }

    public abstract boolean canDrop();

    public abstract long getRowCount(SessionLocal var1);

    public abstract long getRowCountApproximation(SessionLocal var1);

    public long getDiskSpaceUsed() {
        return 0L;
    }

    public Column getRowIdColumn() {
        return null;
    }

    public boolean isQueryComparable() {
        return true;
    }

    public void addDependencies(HashSet<DbObject> hashSet) {
        if (hashSet.contains(this)) {
            return;
        }
        if (this.sequences != null) {
            hashSet.addAll(this.sequences);
        }
        ExpressionVisitor expressionVisitor = ExpressionVisitor.getDependenciesVisitor(hashSet);
        for (Column column : this.columns) {
            column.isEverything(expressionVisitor);
        }
        if (this.constraints != null) {
            for (Constraint constraint : this.constraints) {
                constraint.isEverything(expressionVisitor);
            }
        }
        hashSet.add(this);
    }

    @Override
    public ArrayList<DbObject> getChildren() {
        ArrayList<DbObject> arrayList = Utils.newSmallArrayList();
        ArrayList<Index> arrayList2 = this.getIndexes();
        if (arrayList2 != null) {
            arrayList.addAll(arrayList2);
        }
        if (this.constraints != null) {
            arrayList.addAll(this.constraints);
        }
        if (this.triggers != null) {
            arrayList.addAll(this.triggers);
        }
        if (this.sequences != null) {
            arrayList.addAll(this.sequences);
        }
        arrayList.addAll(this.dependentViews);
        if (this.synonyms != null) {
            arrayList.addAll(this.synonyms);
        }
        ArrayList<Right> arrayList3 = this.database.getAllRights();
        for (Right right : arrayList3) {
            if (right.getGrantedObject() != this) continue;
            arrayList.add(right);
        }
        return arrayList;
    }

    protected void setColumns(Column[] columnArray) {
        if (columnArray.length > 16384) {
            throw DbException.get(54011, "16384");
        }
        this.columns = columnArray;
        if (this.columnMap.size() > 0) {
            this.columnMap.clear();
        }
        for (int i = 0; i < columnArray.length; ++i) {
            Column column = columnArray[i];
            int n = column.getType().getValueType();
            if (n == -1) {
                throw DbException.get(50004, column.getTraceSQL());
            }
            column.setTable(this, i);
            String string = column.getName();
            if (this.columnMap.putIfAbsent(string, column) == null) continue;
            throw DbException.get(42121, string);
        }
        this.rowFactory = this.database.getRowFactory().createRowFactory(this.database, this.database.getCompareMode(), this.database, columnArray, null, false);
    }

    public void renameColumn(Column column, String string) {
        for (Column column2 : this.columns) {
            if (column2 == column || !column2.getName().equals(string)) continue;
            throw DbException.get(42121, string);
        }
        this.columnMap.remove(column.getName());
        column.rename(string);
        this.columnMap.put(string, column);
    }

    public boolean isLockedExclusivelyBy(SessionLocal sessionLocal) {
        return false;
    }

    public void updateRows(Prepared prepared, SessionLocal sessionLocal, LocalResult localResult) {
        Row row;
        SessionLocal.Savepoint savepoint = sessionLocal.setSavepoint();
        int n = 0;
        while (localResult.next()) {
            if ((++n & 0x7F) == 0) {
                prepared.checkCanceled();
            }
            row = localResult.currentRowForTable();
            localResult.next();
            try {
                this.removeRow(sessionLocal, row);
            }
            catch (DbException dbException) {
                if (dbException.getErrorCode() == 90131 || dbException.getErrorCode() == 90112) {
                    sessionLocal.rollbackTo(savepoint);
                }
                throw dbException;
            }
        }
        localResult.reset();
        while (localResult.next()) {
            if ((++n & 0x7F) == 0) {
                prepared.checkCanceled();
            }
            localResult.next();
            row = localResult.currentRowForTable();
            try {
                this.addRow(sessionLocal, row);
            }
            catch (DbException dbException) {
                if (dbException.getErrorCode() == 90131) {
                    sessionLocal.rollbackTo(savepoint);
                }
                throw dbException;
            }
        }
    }

    public CopyOnWriteArrayList<TableView> getDependentViews() {
        return this.dependentViews;
    }

    public CopyOnWriteArrayList<MaterializedView> getDependentMaterializedViews() {
        return this.dependentMaterializedViews;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        Object object;
        while (!this.dependentViews.isEmpty()) {
            object = this.dependentViews.remove(0);
            this.database.removeSchemaObject(sessionLocal, (SchemaObject)object);
        }
        while (this.synonyms != null && !this.synonyms.isEmpty()) {
            object = this.synonyms.remove(0);
            this.database.removeSchemaObject(sessionLocal, (SchemaObject)object);
        }
        while (this.triggers != null && !this.triggers.isEmpty()) {
            object = this.triggers.remove(0);
            this.database.removeSchemaObject(sessionLocal, (SchemaObject)object);
        }
        while (this.constraints != null && !this.constraints.isEmpty()) {
            object = this.constraints.remove(0);
            this.database.removeSchemaObject(sessionLocal, (SchemaObject)object);
        }
        for (Right right : this.database.getAllRights()) {
            if (right.getGrantedObject() != this) continue;
            this.database.removeDatabaseObject(sessionLocal, right);
        }
        this.database.removeMeta(sessionLocal, this.getId());
        while (this.sequences != null && !this.sequences.isEmpty()) {
            object = this.sequences.remove(0);
            if (this.database.getDependentTable((SchemaObject)object, this) != null) continue;
            this.database.removeSchemaObject(sessionLocal, (SchemaObject)object);
        }
    }

    public void dropMultipleColumnsConstraintsAndIndexes(SessionLocal sessionLocal, ArrayList<Column> arrayList) {
        HashSet<Constraint> hashSet = new HashSet<Constraint>();
        if (this.constraints != null) {
            for (Column arrayList22 : arrayList) {
                for (Constraint constraint : this.constraints) {
                    HashSet<Column> hashSet2 = constraint.getReferencedColumns(this);
                    if (!hashSet2.contains(arrayList22)) continue;
                    if (hashSet2.size() == 1) {
                        hashSet.add(constraint);
                        continue;
                    }
                    throw DbException.get(90083, constraint.getTraceSQL());
                }
            }
        }
        HashSet hashSet3 = new HashSet();
        ArrayList<Index> arrayList2 = this.getIndexes();
        if (arrayList2 != null) {
            for (Column column : arrayList) {
                for (Index index : arrayList2) {
                    if (index.getCreateSQL() == null || index.getColumnIndex(column) < 0) continue;
                    if (index.getColumns().length == 1) {
                        hashSet3.add(index);
                        continue;
                    }
                    throw DbException.get(90083, index.getTraceSQL());
                }
            }
        }
        for (Constraint constraint : hashSet) {
            if (!constraint.isValid()) continue;
            sessionLocal.getDatabase().removeSchemaObject(sessionLocal, constraint);
        }
        Iterator<HasSQL> iterator2 = hashSet3.iterator();
        while (iterator2.hasNext()) {
            Index index = (Index)iterator2.next();
            if (!this.getIndexes().contains(index)) continue;
            sessionLocal.getDatabase().removeSchemaObject(sessionLocal, index);
        }
    }

    public RowFactory getRowFactory() {
        return this.rowFactory;
    }

    public Row createRow(Value[] valueArray, int n) {
        return this.rowFactory.createRow(valueArray, n);
    }

    public Row getTemplateRow() {
        return this.createRow(new Value[this.getColumns().length], -1);
    }

    public SearchRow getTemplateSimpleRow(boolean bl) {
        if (bl) {
            return new SimpleRowValue(this.columns.length);
        }
        return new DefaultRow(new Value[this.columns.length]);
    }

    public Row getNullRow() {
        Row row = this.nullRow;
        if (row == null) {
            Object[] objectArray = new Value[this.columns.length];
            Arrays.fill(objectArray, ValueNull.INSTANCE);
            this.nullRow = row = this.createRow((Value[])objectArray, 1);
        }
        return row;
    }

    public Column[] getColumns() {
        return this.columns;
    }

    @Override
    public int getType() {
        return 0;
    }

    public Column getColumn(int n) {
        return this.columns[n];
    }

    public Column getColumn(String string) {
        Column column = this.columnMap.get(string);
        if (column == null) {
            throw DbException.get(42122, string);
        }
        return column;
    }

    public Column getColumn(String string, boolean bl) {
        Column column = this.columnMap.get(string);
        if (column == null && !bl) {
            throw DbException.get(42122, string);
        }
        return column;
    }

    public Column findColumn(String string) {
        return this.columnMap.get(string);
    }

    public boolean doesColumnExist(String string) {
        return this.columnMap.containsKey(string);
    }

    public Column getIdentityColumn() {
        for (Column column : this.columns) {
            if (!column.isIdentity()) continue;
            return column;
        }
        return null;
    }

    public PlanItem getBestPlanItem(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        PlanItem planItem = new PlanItem();
        planItem.setIndex(this.getScanIndex(sessionLocal));
        planItem.cost = planItem.getIndex().getCost(sessionLocal, null, tableFilterArray, n, null, allColumnsForPlan);
        Trace trace = sessionLocal.getTrace();
        if (trace.isDebugEnabled()) {
            trace.debug("Table      :     potential plan item cost {0} index {1}", planItem.cost, planItem.getIndex().getPlanSQL());
        }
        ArrayList<Index> arrayList = this.getIndexes();
        IndexHints indexHints = Table.getIndexHints(tableFilterArray, n);
        if (arrayList != null && nArray != null) {
            int n2 = arrayList.size();
            for (int i = 1; i < n2; ++i) {
                Index index = arrayList.get(i);
                if (Table.isIndexExcludedByHints(indexHints, index)) continue;
                double d = index.getCost(sessionLocal, nArray, tableFilterArray, n, sortOrder, allColumnsForPlan);
                if (trace.isDebugEnabled()) {
                    trace.debug("Table      :     potential plan item cost {0} index {1}", d, index.getPlanSQL());
                }
                if (!(d < planItem.cost)) continue;
                planItem.cost = d;
                planItem.setIndex(index);
            }
        }
        return planItem;
    }

    private static boolean isIndexExcludedByHints(IndexHints indexHints, Index index) {
        return indexHints != null && !indexHints.allowIndex(index);
    }

    private static IndexHints getIndexHints(TableFilter[] tableFilterArray, int n) {
        return tableFilterArray == null ? null : tableFilterArray[n].getIndexHints();
    }

    public Index findPrimaryKey() {
        ArrayList<Index> arrayList = this.getIndexes();
        if (arrayList != null) {
            for (Index index : arrayList) {
                if (!index.getIndexType().isPrimaryKey()) continue;
                return index;
            }
        }
        return null;
    }

    public Index getPrimaryKey() {
        Index index = this.findPrimaryKey();
        if (index != null) {
            return index;
        }
        throw DbException.get(42112, "PRIMARY_KEY_");
    }

    public void convertInsertRow(SessionLocal sessionLocal, Row row, Boolean bl) {
        Value value;
        int n;
        int n2 = this.columns.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            Value value2;
            value = row.getValue(n);
            Column column = this.columns[n];
            if (value == ValueNull.INSTANCE && column.isDefaultOnNull()) {
                value = null;
            }
            if (column.isIdentity()) {
                if (bl != null) {
                    if (!bl.booleanValue()) {
                        value = null;
                    }
                } else if (value != null && column.isGeneratedAlways()) {
                    throw DbException.get(90154, column.getSQLWithTable(new StringBuilder(), 3).toString());
                }
            } else if (column.isGeneratedAlways()) {
                if (value != null) {
                    throw DbException.get(90154, column.getSQLWithTable(new StringBuilder(), 3).toString());
                }
                ++n3;
                continue;
            }
            if ((value2 = column.validateConvertUpdateSequence(sessionLocal, value, row)) == value) continue;
            row.setValue(n, value2);
        }
        if (n3 > 0) {
            for (n = 0; n < n2; ++n) {
                value = row.getValue(n);
                if (value != null) continue;
                row.setValue(n, this.columns[n].validateConvertUpdateSequence(sessionLocal, null, row));
            }
        }
    }

    public void convertUpdateRow(SessionLocal sessionLocal, Row row, boolean bl) {
        Value value;
        int n;
        int n2 = this.columns.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            value = row.getValue(n);
            Column column = this.columns[n];
            if (column.isGenerated()) {
                if (value != null) {
                    if (!bl) {
                        throw DbException.get(90154, column.getSQLWithTable(new StringBuilder(), 3).toString());
                    }
                    row.setValue(n, null);
                }
                ++n3;
                continue;
            }
            Value value2 = column.validateConvertUpdateSequence(sessionLocal, value, row);
            if (value2 == value) continue;
            row.setValue(n, value2);
        }
        if (n3 > 0) {
            for (n = 0; n < n2; ++n) {
                value = row.getValue(n);
                if (value != null) continue;
                row.setValue(n, this.columns[n].validateConvertUpdateSequence(sessionLocal, null, row));
            }
        }
    }

    private static void remove(ArrayList<? extends DbObject> arrayList, DbObject dbObject) {
        if (arrayList != null) {
            arrayList.remove(dbObject);
        }
    }

    public void removeIndex(Index index) {
        ArrayList<Index> arrayList = this.getIndexes();
        if (arrayList != null) {
            Table.remove(arrayList, index);
            if (index.getIndexType().isPrimaryKey()) {
                for (Column column : index.getColumns()) {
                    column.setPrimaryKey(false);
                }
            }
        }
    }

    public void removeDependentView(TableView tableView) {
        this.dependentViews.remove(tableView);
    }

    public void removeDependentMaterializedView(MaterializedView materializedView) {
        this.dependentMaterializedViews.remove(materializedView);
    }

    public void removeSynonym(TableSynonym tableSynonym) {
        Table.remove(this.synonyms, tableSynonym);
    }

    public void removeConstraint(Constraint constraint) {
        Table.remove(this.constraints, constraint);
    }

    public final void removeSequence(Sequence sequence2) {
        Table.remove(this.sequences, sequence2);
    }

    public void removeTrigger(TriggerObject triggerObject) {
        Table.remove(this.triggers, triggerObject);
    }

    public void addDependentView(TableView tableView) {
        this.dependentViews.add(tableView);
    }

    public void addDependentMaterializedView(MaterializedView materializedView) {
        this.dependentMaterializedViews.add(materializedView);
    }

    public void addSynonym(TableSynonym tableSynonym) {
        this.synonyms = Table.add(this.synonyms, tableSynonym);
    }

    public void addConstraint(Constraint constraint) {
        if (this.constraints == null || !this.constraints.contains(constraint)) {
            this.constraints = Table.add(this.constraints, constraint);
        }
    }

    public ArrayList<Constraint> getConstraints() {
        return this.constraints;
    }

    public void addSequence(Sequence sequence2) {
        this.sequences = Table.add(this.sequences, sequence2);
    }

    public void addTrigger(TriggerObject triggerObject) {
        this.triggers = Table.add(this.triggers, triggerObject);
    }

    private static <T> ArrayList<T> add(ArrayList<T> arrayList, T t2) {
        if (arrayList == null) {
            arrayList = Utils.newSmallArrayList();
        }
        arrayList.add(t2);
        return arrayList;
    }

    public void fire(SessionLocal sessionLocal, int n, boolean bl) {
        if (this.triggers != null) {
            for (TriggerObject triggerObject : this.triggers) {
                triggerObject.fire(sessionLocal, n, bl);
            }
        }
    }

    public boolean hasSelectTrigger() {
        if (this.triggers != null) {
            for (TriggerObject triggerObject : this.triggers) {
                if (!triggerObject.isSelectTrigger()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean fireRow() {
        return this.constraints != null && !this.constraints.isEmpty() || this.triggers != null && !this.triggers.isEmpty();
    }

    public boolean fireBeforeRow(SessionLocal sessionLocal, Row row, Row row2) {
        boolean bl = this.fireRow(sessionLocal, row, row2, true, false);
        this.fireConstraints(sessionLocal, row, row2, true);
        return bl;
    }

    private void fireConstraints(SessionLocal sessionLocal, Row row, Row row2, boolean bl) {
        if (this.constraints != null) {
            for (Constraint constraint : this.constraints) {
                if (constraint.isBefore() != bl) continue;
                constraint.checkRow(sessionLocal, this, row, row2);
            }
        }
    }

    public void fireAfterRow(SessionLocal sessionLocal, Row row, Row row2, boolean bl) {
        this.fireRow(sessionLocal, row, row2, false, bl);
        if (!bl) {
            this.fireConstraints(sessionLocal, row, row2, false);
        }
    }

    private boolean fireRow(SessionLocal sessionLocal, Row row, Row row2, boolean bl, boolean bl2) {
        if (this.triggers != null) {
            for (TriggerObject triggerObject : this.triggers) {
                boolean bl3 = triggerObject.fireRow(sessionLocal, this, row, row2, bl, bl2);
                if (!bl3) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isGlobalTemporary() {
        return false;
    }

    public boolean canTruncate() {
        return false;
    }

    public void setCheckForeignKeyConstraints(SessionLocal sessionLocal, boolean bl, boolean bl2) {
        if (bl && bl2 && this.constraints != null) {
            for (Constraint constraint : this.constraints) {
                if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL) continue;
                constraint.checkExistingData(sessionLocal);
            }
        }
        this.checkForeignKeyConstraints = bl;
    }

    public boolean getCheckForeignKeyConstraints() {
        return this.checkForeignKeyConstraints;
    }

    public Index getIndexForColumn(Column column, boolean bl, boolean bl2) {
        ArrayList<Index> arrayList = this.getIndexes();
        Index index = null;
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 1; i < n; ++i) {
                Index index2 = arrayList.get(i);
                if (bl && !index2.canGetFirstOrLast() || bl2 && !index2.canFindNext() || !index2.isFirstColumn(column) || index != null && index.getColumns().length <= index2.getColumns().length) continue;
                index = index2;
            }
        }
        return index;
    }

    public boolean getOnCommitDrop() {
        return this.onCommitDrop;
    }

    public void setOnCommitDrop(boolean bl) {
        this.onCommitDrop = bl;
    }

    public boolean getOnCommitTruncate() {
        return this.onCommitTruncate;
    }

    public void setOnCommitTruncate(boolean bl) {
        this.onCommitTruncate = bl;
    }

    public void removeIndexOrTransferOwnership(SessionLocal sessionLocal, Index index) {
        boolean bl = false;
        if (this.constraints != null) {
            for (Constraint constraint : this.constraints) {
                if (!constraint.usesIndex(index)) continue;
                constraint.setIndexOwner(index);
                this.database.updateMeta(sessionLocal, constraint);
                bl = true;
            }
        }
        if (!bl) {
            this.database.removeSchemaObject(sessionLocal, index);
        }
    }

    public void removeColumnExpressionsDependencies(SessionLocal sessionLocal) {
        for (Column column : this.columns) {
            column.setDefaultExpression(sessionLocal, null);
            column.setOnUpdateExpression(sessionLocal, null);
        }
    }

    public ArrayList<SessionLocal> checkDeadlock(SessionLocal sessionLocal, SessionLocal sessionLocal2, Set<SessionLocal> set) {
        return null;
    }

    public boolean isPersistIndexes() {
        return this.persistIndexes;
    }

    public boolean isPersistData() {
        return this.persistData;
    }

    public int compareValues(CastDataProvider castDataProvider, Value value, Value value2) {
        return value.compareTo(value2, castDataProvider, this.compareMode);
    }

    public CompareMode getCompareMode() {
        return this.compareMode;
    }

    public void checkWritingAllowed() {
        this.database.checkWritingAllowed();
    }

    @Override
    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean bl) {
        this.isHidden = bl;
    }

    public boolean isRowLockable() {
        return false;
    }

    public void setTableExpression(boolean bl) {
        this.tableExpression = bl;
    }

    public boolean isTableExpression() {
        return this.tableExpression;
    }

    public ArrayList<TriggerObject> getTriggers() {
        return this.triggers;
    }

    public int getMainIndexColumn() {
        return -1;
    }
}

