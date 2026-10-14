/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.h2.command.Command;
import org.h2.command.Parser;
import org.h2.command.Prepared;
import org.h2.command.Token;
import org.h2.command.dml.DataChangeStatement;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.DbSettings;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.Parameter;
import org.h2.expression.ParameterInterface;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.result.ResultWithGeneratedKeys;
import org.h2.table.Column;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.Table;
import org.h2.table.TableView;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.Value;

public class CommandContainer
extends Command {
    private Prepared prepared;
    private boolean readOnlyKnown;
    private boolean readOnly;

    static void clearCTE(SessionLocal sessionLocal, Prepared prepared) {
        List<TableView> list = prepared.getCteCleanups();
        if (list != null) {
            CommandContainer.clearCTE(sessionLocal, list);
        }
    }

    static void clearCTE(SessionLocal sessionLocal, List<TableView> list) {
        for (TableView tableView : list) {
            if (tableView.getName() == null) continue;
            sessionLocal.removeLocalTempTable(tableView);
        }
    }

    public CommandContainer(SessionLocal sessionLocal, String string, Prepared prepared) {
        super(sessionLocal, string);
        prepared.setCommand(this);
        this.prepared = prepared;
    }

    @Override
    public ArrayList<? extends ParameterInterface> getParameters() {
        ArrayList<Parameter> arrayList = this.prepared.getParameters();
        if (arrayList.size() > 0 && this.prepared.isWithParamValues()) {
            arrayList = new ArrayList();
        }
        return arrayList;
    }

    @Override
    public boolean isTransactional() {
        return this.prepared.isTransactional();
    }

    @Override
    public boolean isQuery() {
        return this.prepared.isQuery();
    }

    private void recompileIfRequired() {
        if (this.prepared.needRecompile()) {
            this.prepared.setModificationMetaId(0L);
            String string = this.prepared.getSQL();
            ArrayList<Token> arrayList = this.prepared.getSQLTokens();
            Parser parser = new Parser(this.session);
            parser.setSuppliedParameters(this.prepared.getParameters());
            this.prepared = parser.parse(string, arrayList);
            long l = this.prepared.getModificationMetaId();
            this.prepared.setModificationMetaId(0L);
            this.prepared.prepare();
            this.prepared.setModificationMetaId(l);
        }
    }

    @Override
    public ResultWithGeneratedKeys update(Object object) {
        this.recompileIfRequired();
        Database database = this.getDatabase();
        this.setProgress(database, 5);
        this.start();
        this.prepared.checkParameters();
        ResultWithGeneratedKeys resultWithGeneratedKeys = object != null && !Boolean.FALSE.equals(object) ? (this.prepared instanceof DataChangeStatement && this.prepared.getType() != 58 ? this.executeUpdateWithGeneratedKeys((DataChangeStatement)this.prepared, object) : new ResultWithGeneratedKeys.WithKeys(this.prepared.update(), new LocalResult())) : ResultWithGeneratedKeys.of(this.prepared.update());
        this.prepared.trace(database, this.startTimeNanos, resultWithGeneratedKeys.getUpdateCount());
        this.setProgress(database, 6);
        return resultWithGeneratedKeys;
    }

    /*
     * WARNING - void declaration
     */
    private ResultWithGeneratedKeys executeUpdateWithGeneratedKeys(DataChangeStatement dataChangeStatement, Object object) {
        void var9_18;
        int n;
        Object[] objectArray;
        Object[] objectArray2;
        ArrayList<Object> arrayList;
        Database database = this.getDatabase();
        Table table = dataChangeStatement.getTable();
        if (Boolean.TRUE.equals(object)) {
            arrayList = Utils.newSmallArrayList();
            objectArray2 = table.getColumns();
            objectArray = table.findPrimaryKey();
            for (Column column : objectArray2) {
                Expression expression;
                if (!column.isIdentity() && ((expression = column.getEffectiveDefaultExpression()) == null || expression.isConstant()) && (objectArray == null || objectArray.getColumnIndex(column) < 0)) continue;
                arrayList.add(new ExpressionColumn(database, column));
            }
        } else if (object instanceof int[]) {
            objectArray2 = (int[])object;
            objectArray = table.getColumns();
            int n2 = objectArray.length;
            arrayList = new ArrayList(objectArray2.length);
            for (Column column : objectArray2) {
                if (column < true || column > n2) {
                    throw DbException.get(42122, "Index: " + (int)column);
                }
                arrayList.add(new ExpressionColumn(database, (Column)objectArray[column - true]));
            }
        } else if (object instanceof String[]) {
            void var9_16;
            objectArray2 = (String[])object;
            arrayList = new ArrayList(objectArray2.length);
            objectArray = objectArray2;
            int n2 = objectArray.length;
            boolean localResult = false;
            while (var9_16 < n2) {
                Column column;
                block18: {
                    Column column2 = objectArray[var9_16];
                    column = table.findColumn((String)((Object)column2));
                    if (column == null) {
                        DbSettings dbSettings = database.getSettings();
                        if (dbSettings.databaseToUpper) {
                            column = table.findColumn(StringUtils.toUpperEnglish((String)((Object)column2)));
                        } else if (dbSettings.databaseToLower) {
                            column = table.findColumn(StringUtils.toLowerEnglish((String)((Object)column2)));
                        }
                        if (column == null) {
                            for (Column column3 : table.getColumns()) {
                                if (!column3.getName().equalsIgnoreCase((String)((Object)column2))) continue;
                                column = column3;
                                break block18;
                            }
                            throw DbException.get(42122, (String)((Object)column2));
                        }
                    }
                }
                arrayList.add(new ExpressionColumn(database, column));
                ++var9_16;
            }
        } else {
            throw DbException.getInternalError();
        }
        if ((n = arrayList.size()) == 0) {
            return new ResultWithGeneratedKeys.WithKeys(dataChangeStatement.update(), new LocalResult());
        }
        objectArray = new int[n];
        Expression[] expressionArray = arrayList.toArray(new ExpressionColumn[0]);
        boolean bl = false;
        while (var9_18 < n) {
            objectArray[var9_18] = expressionArray[var9_18].getColumn().getColumnId();
            ++var9_18;
        }
        LocalResult localResult = new LocalResult(this.session, expressionArray, n, n);
        return new ResultWithGeneratedKeys.WithKeys(dataChangeStatement.update(new GeneratedKeysCollector((int[])objectArray, localResult), DataChangeDeltaTable.ResultOption.FINAL), localResult);
    }

    @Override
    public ResultInterface query(long l) {
        this.recompileIfRequired();
        Database database = this.getDatabase();
        this.setProgress(database, 5);
        this.start();
        this.prepared.checkParameters();
        ResultInterface resultInterface = this.prepared.query(l);
        this.prepared.trace(database, this.startTimeNanos, resultInterface.isLazy() ? 0L : resultInterface.getRowCount());
        this.setProgress(database, 6);
        return resultInterface;
    }

    @Override
    public void stop() {
        super.stop();
        CommandContainer.clearCTE(this.session, this.prepared);
    }

    @Override
    public boolean canReuse() {
        return super.canReuse() && this.prepared.getCteCleanups() == null;
    }

    @Override
    public boolean isReadOnly() {
        if (!this.readOnlyKnown) {
            this.readOnly = this.prepared.isReadOnly();
            this.readOnlyKnown = true;
        }
        return this.readOnly;
    }

    @Override
    public ResultInterface queryMeta() {
        return this.prepared.queryMeta();
    }

    @Override
    public boolean isCacheable() {
        return this.prepared.isCacheable();
    }

    @Override
    public int getCommandType() {
        return this.prepared.getType();
    }

    void clearCTE() {
        CommandContainer.clearCTE(this.session, this.prepared);
    }

    @Override
    public Set<DbObject> getDependencies() {
        HashSet<DbObject> hashSet = new HashSet<DbObject>();
        this.prepared.collectDependencies(hashSet);
        return hashSet;
    }

    @Override
    protected boolean isRetryable() {
        return this.prepared.isRetryable();
    }

    private static final class GeneratedKeysCollector
    implements ResultTarget {
        private final int[] indexes;
        private final LocalResult result;

        GeneratedKeysCollector(int[] nArray, LocalResult localResult) {
            this.indexes = nArray;
            this.result = localResult;
        }

        @Override
        public void limitsWereApplied() {
        }

        @Override
        public long getRowCount() {
            return 0L;
        }

        @Override
        public void addRow(Value ... valueArray) {
            int n = this.indexes.length;
            Value[] valueArray2 = new Value[n];
            for (int i = 0; i < n; ++i) {
                valueArray2[i] = valueArray[this.indexes[i]];
            }
            this.result.addRow(valueArray2);
        }
    }
}

