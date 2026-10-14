/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.h2.command.Prepared;
import org.h2.command.ddl.CreateTableData;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.command.query.Query;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Parameter;
import org.h2.index.Index;
import org.h2.index.QueryExpressionIndex;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.SortOrder;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.table.Column;
import org.h2.table.QueryExpressionTable;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.table.TableType;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public final class TableView
extends QueryExpressionTable {
    private String querySQL;
    private Column[] columnTemplates;
    private boolean allowRecursive;
    private DbException createException;
    private ResultInterface recursiveResult;
    private boolean isRecursiveQueryDetected;
    private boolean isTableExpression;

    public TableView(Schema schema, int n, String string, String string2, ArrayList<Parameter> arrayList, Column[] columnArray, SessionLocal sessionLocal, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super(schema, n, string);
        this.setTemporary(bl4);
        this.init(string2, arrayList, columnArray, sessionLocal, bl, bl2, bl3);
    }

    public void replace(String string, Column[] columnArray, SessionLocal sessionLocal, boolean bl, boolean bl2, boolean bl3) {
        String string2 = this.querySQL;
        Column[] columnArray2 = this.columnTemplates;
        boolean bl4 = this.allowRecursive;
        this.init(string, null, columnArray, sessionLocal, bl, bl3, this.isTableExpression);
        DbException dbException = this.recompile(sessionLocal, bl2, true);
        if (dbException != null) {
            this.init(string2, null, columnArray2, sessionLocal, bl4, bl3, this.isTableExpression);
            this.recompile(sessionLocal, true, false);
            throw dbException;
        }
    }

    private synchronized void init(String string, ArrayList<Parameter> arrayList, Column[] columnArray, SessionLocal sessionLocal, boolean bl, boolean bl2, boolean bl3) {
        this.querySQL = string;
        this.columnTemplates = columnArray;
        this.allowRecursive = bl;
        this.isRecursiveQueryDetected = false;
        this.isTableExpression = bl3;
        this.index = new QueryExpressionIndex(this, string, arrayList, bl);
        this.initColumnsAndTables(sessionLocal, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Query compileViewQuery(SessionLocal sessionLocal, String string, boolean bl) {
        Prepared prepared;
        sessionLocal.setParsingCreateView(true);
        try {
            prepared = sessionLocal.prepare(string, false, bl);
        }
        finally {
            sessionLocal.setParsingCreateView(false);
        }
        if (!(prepared instanceof Query)) {
            throw DbException.getSyntaxError(string, 0);
        }
        Query query = (Query)prepared;
        if (this.isTableExpression && this.allowRecursive) {
            query.setNeverLazy(true);
        }
        return query;
    }

    public synchronized DbException recompile(SessionLocal sessionLocal, boolean bl, boolean bl2) {
        block4: {
            try {
                this.compileViewQuery(sessionLocal, this.querySQL, false);
            }
            catch (DbException dbException) {
                if (bl) break block4;
                return dbException;
            }
        }
        ArrayList<TableView> arrayList = new ArrayList<TableView>(this.getDependentViews());
        this.initColumnsAndTables(sessionLocal, false);
        for (TableView tableView : arrayList) {
            DbException dbException = tableView.recompile(sessionLocal, bl, false);
            if (dbException == null || bl) continue;
            return dbException;
        }
        if (bl2) {
            TableView.clearIndexCaches(this.database);
        }
        return bl ? null : this.createException;
    }

    private void initColumnsAndTables(SessionLocal sessionLocal, boolean bl) {
        Column[] columnArray;
        block6: {
            this.removeCurrentViewFromOtherTables();
            this.setTableExpression(this.isTableExpression);
            try {
                Query query = this.compileViewQuery(sessionLocal, this.querySQL, bl);
                this.querySQL = query.getPlanSQL(0);
                this.tables = new ArrayList<Table>(query.getTables());
                columnArray = this.initColumns(sessionLocal, this.columnTemplates, query, false);
                this.createException = null;
                this.viewQuery = query;
            }
            catch (DbException dbException) {
                if (dbException.getErrorCode() == 90156) {
                    throw dbException;
                }
                dbException.addSQL(this.getCreateSQL());
                this.createException = dbException;
                if (this.isRecursiveQueryExceptionDetected(this.createException)) {
                    this.isRecursiveQueryDetected = true;
                }
                this.tables = Utils.newSmallArrayList();
                columnArray = new Column[]{};
                if (!this.allowRecursive || this.columnTemplates == null) break block6;
                columnArray = new Column[this.columnTemplates.length];
                for (int i = 0; i < this.columnTemplates.length; ++i) {
                    columnArray[i] = this.columnTemplates[i].getClone();
                }
                this.index.setRecursive(true);
                this.createException = null;
            }
        }
        this.setColumns(columnArray);
        if (this.getId() != 0) {
            this.addDependentViewToTables();
        }
    }

    public boolean isInvalid() {
        return this.createException != null;
    }

    @Override
    public Query getTopQuery() {
        return null;
    }

    @Override
    public String getDropSQL() {
        return this.getSQL(new StringBuilder("DROP VIEW IF EXISTS "), 0).append(" CASCADE").toString();
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
        stringBuilder.append("VIEW ");
        if (this.isTableExpression) {
            stringBuilder.append("TABLE_EXPRESSION ");
        }
        stringBuilder.append(string);
        if (this.comment != null) {
            stringBuilder.append(" COMMENT ");
            StringUtils.quoteStringSQL(stringBuilder, this.comment);
        }
        if (this.columns != null && this.columns.length > 0) {
            stringBuilder.append('(');
            Column.writeColumns(stringBuilder, this.columns, 0);
            stringBuilder.append(')');
        } else if (this.columnTemplates != null) {
            stringBuilder.append('(');
            Column.writeColumns(stringBuilder, this.columnTemplates, 0);
            stringBuilder.append(')');
        }
        return stringBuilder.append(" AS\n").append(this.querySQL).toString();
    }

    @Override
    public boolean canDrop() {
        return true;
    }

    @Override
    public TableType getTableType() {
        return TableType.VIEW;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.removeCurrentViewFromOtherTables();
        super.removeChildrenAndResources(sessionLocal);
        this.querySQL = null;
        this.index = null;
        TableView.clearIndexCaches(this.database);
        this.invalidate();
    }

    public static void clearIndexCaches(Database database) {
        for (SessionLocal sessionLocal : database.getSessions(true)) {
            sessionLocal.clearViewIndexCache();
        }
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
    public Index getScanIndex(SessionLocal sessionLocal, int[] nArray, TableFilter[] tableFilterArray, int n, SortOrder sortOrder, AllColumnsForPlan allColumnsForPlan) {
        if (this.createException != null) {
            String string = this.createException.getMessage();
            throw DbException.get(90109, this.createException, this.getTraceSQL(), string);
        }
        return super.getScanIndex(sessionLocal, nArray, tableFilterArray, n, sortOrder, allColumnsForPlan);
    }

    @Override
    public long getMaxDataModificationId() {
        if (this.createException != null || this.viewQuery == null) {
            return Long.MAX_VALUE;
        }
        return super.getMaxDataModificationId();
    }

    private void removeCurrentViewFromOtherTables() {
        if (this.tables != null) {
            for (Table table : this.tables) {
                table.removeDependentView(this);
            }
            this.tables.clear();
        }
    }

    private void addDependentViewToTables() {
        for (Table table : this.tables) {
            table.addDependentView(this);
        }
    }

    public boolean isRecursive() {
        return this.allowRecursive;
    }

    @Override
    public boolean isDeterministic() {
        if (this.allowRecursive || this.viewQuery == null) {
            return false;
        }
        return super.isDeterministic();
    }

    public void setRecursiveResult(ResultInterface resultInterface) {
        if (this.recursiveResult != null) {
            this.recursiveResult.close();
        }
        this.recursiveResult = resultInterface;
    }

    public ResultInterface getRecursiveResult() {
        return this.recursiveResult;
    }

    public boolean isRecursiveQueryDetected() {
        return this.isRecursiveQueryDetected;
    }

    private boolean isRecursiveQueryExceptionDetected(DbException dbException) {
        if (dbException == null) {
            return false;
        }
        int n = dbException.getErrorCode();
        if (n != 42102 && n != 42104 && n != 42103) {
            return false;
        }
        return dbException.getMessage().contains("\"" + this.getName() + "\"");
    }

    public List<Table> getTables() {
        return this.tables;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TableView createTableViewMaybeRecursive(Schema schema, int n, String string, String string2, ArrayList<Parameter> arrayList, Column[] columnArray, SessionLocal sessionLocal, boolean bl, boolean bl2, boolean bl3, Database database) {
        List<Column> list;
        Object object;
        Table table = TableView.createShadowTableForRecursiveTableExpression(bl3, sessionLocal, string, schema, Arrays.asList(columnArray), database);
        String[] stringArray = new String[1];
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (Column column : columnArray) {
            arrayList2.add(column.getName());
        }
        try {
            object = sessionLocal.prepare(string2, false, false);
            if (!bl3) {
                ((Prepared)object).setSession(sessionLocal);
            }
            list = TableView.createQueryColumnTemplateList(arrayList2.toArray(new String[1]), (Query)object, stringArray);
        }
        finally {
            TableView.destroyShadowTableForRecursiveExpression(bl3, sessionLocal, table);
        }
        object = new TableView(schema, n, string, string2, arrayList, list.toArray(columnArray), sessionLocal, true, bl, bl2, bl3);
        if (!((TableView)object).isRecursiveQueryDetected()) {
            if (!bl3) {
                database.addSchemaObject(sessionLocal, (SchemaObject)object);
                ((Table)object).lock(sessionLocal, 2);
                sessionLocal.getDatabase().removeSchemaObject(sessionLocal, (SchemaObject)object);
                ((TableView)object).removeChildrenAndResources(sessionLocal);
            } else {
                sessionLocal.removeLocalTempTable((Table)object);
            }
            object = new TableView(schema, n, string, string2, arrayList, columnArray, sessionLocal, false, bl, bl2, bl3);
        }
        return object;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Table createShadowTableForRecursiveTableExpression(boolean bl, SessionLocal sessionLocal, String string, Schema schema, List<Column> list, Database database) {
        CreateTableData createTableData = new CreateTableData();
        createTableData.id = database.allocateObjectId();
        createTableData.columns = new ArrayList<Column>(list);
        createTableData.tableName = string;
        createTableData.temporary = bl;
        createTableData.persistData = true;
        createTableData.persistIndexes = !bl;
        createTableData.session = sessionLocal;
        Table table = schema.createTable(createTableData);
        if (!bl) {
            database.unlockMeta(sessionLocal);
            SessionLocal sessionLocal2 = sessionLocal;
            synchronized (sessionLocal2) {
                database.addSchemaObject(sessionLocal, table);
            }
        } else {
            sessionLocal.addLocalTempTable(table);
        }
        return table;
    }

    public static void destroyShadowTableForRecursiveExpression(boolean bl, SessionLocal sessionLocal, Table table) {
        if (table != null) {
            if (!bl) {
                table.lock(sessionLocal, 2);
                sessionLocal.getDatabase().removeSchemaObject(sessionLocal, table);
            } else {
                sessionLocal.removeLocalTempTable(table);
            }
            sessionLocal.getDatabase().unlockMeta(sessionLocal);
        }
    }
}

