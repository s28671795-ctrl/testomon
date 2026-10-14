/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.h2.api.Trigger;
import org.h2.engine.SessionLocal;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.Row;
import org.h2.result.SimpleResult;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.tools.TriggerAdapter;
import org.h2.util.JdbcUtils;
import org.h2.util.SourceCompiler;
import org.h2.util.StringUtils;
import org.h2.value.Value;
import org.h2.value.ValueToObjectConverter;

public final class TriggerObject
extends SchemaObject {
    public static final int DEFAULT_QUEUE_SIZE = 1024;
    private boolean insteadOf;
    private boolean before;
    private int typeMask;
    private boolean rowBased;
    private boolean onRollback;
    private int queueSize = 1024;
    private boolean noWait;
    private Table table;
    private String triggerClassName;
    private String triggerSource;
    private Trigger triggerCallback;

    public TriggerObject(Schema schema, int n, String string, Table table) {
        super(schema, n, string, 12);
        this.table = table;
        this.setTemporary(table.isTemporary());
    }

    public void setBefore(boolean bl) {
        this.before = bl;
    }

    public boolean isInsteadOf() {
        return this.insteadOf;
    }

    public void setInsteadOf(boolean bl) {
        this.insteadOf = bl;
    }

    private synchronized void load() {
        if (this.triggerCallback != null) {
            return;
        }
        try {
            SessionLocal sessionLocal = this.database.getSystemSession();
            JdbcConnection jdbcConnection = sessionLocal.createConnection(false);
            Trigger trigger = this.triggerClassName != null ? JdbcUtils.loadUserClass(this.triggerClassName).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]) : this.loadFromSource();
            this.triggerCallback = trigger;
            this.triggerCallback.init(jdbcConnection, this.getSchema().getName(), this.getName(), this.table.getName(), this.before, this.typeMask);
        }
        catch (Throwable throwable) {
            this.triggerCallback = null;
            throw DbException.get(90043, throwable, this.getName(), this.triggerClassName != null ? this.triggerClassName : "..source..", throwable.toString());
        }
    }

    private Trigger loadFromSource() {
        SourceCompiler sourceCompiler;
        SourceCompiler sourceCompiler2 = sourceCompiler = this.database.getCompiler();
        synchronized (sourceCompiler2) {
            String string = "org.h2.dynamic.trigger." + this.getName();
            sourceCompiler.setSource(string, this.triggerSource);
            try {
                if (SourceCompiler.isJavaxScriptSource(this.triggerSource)) {
                    return (Trigger)sourceCompiler.getCompiledScript(string).eval();
                }
                Method method2 = sourceCompiler.getMethod(string);
                if (method2.getParameterTypes().length > 0) {
                    throw new IllegalStateException("No parameters are allowed for a trigger");
                }
                return (Trigger)method2.invoke(null, new Object[0]);
            }
            catch (DbException dbException) {
                throw dbException;
            }
            catch (Exception exception) {
                throw DbException.get(42000, exception, this.triggerSource);
            }
        }
    }

    public void setTriggerClassName(String string, boolean bl) {
        this.setTriggerAction(string, null, bl);
    }

    public void setTriggerSource(String string, boolean bl) {
        this.setTriggerAction(null, string, bl);
    }

    private void setTriggerAction(String string, String string2, boolean bl) {
        block2: {
            this.triggerClassName = string;
            this.triggerSource = string2;
            try {
                this.load();
            }
            catch (DbException dbException) {
                if (bl) break block2;
                throw dbException;
            }
        }
    }

    public void fire(SessionLocal sessionLocal, int n, boolean bl) {
        if (this.rowBased || this.before != bl || (this.typeMask & n) == 0) {
            return;
        }
        this.load();
        JdbcConnection jdbcConnection = sessionLocal.createConnection(false);
        boolean bl2 = false;
        if (n != 8) {
            bl2 = sessionLocal.setCommitOrRollbackDisabled(true);
        }
        Value value = sessionLocal.getLastIdentity();
        try {
            if (this.triggerCallback instanceof TriggerAdapter) {
                ((TriggerAdapter)this.triggerCallback).fire((Connection)jdbcConnection, (ResultSet)null, (ResultSet)null);
            } else {
                this.triggerCallback.fire(jdbcConnection, null, null);
            }
        }
        catch (Throwable throwable) {
            throw this.getErrorExecutingTrigger(throwable);
        }
        finally {
            sessionLocal.setLastIdentity(value);
            if (n != 8) {
                sessionLocal.setCommitOrRollbackDisabled(bl2);
            }
        }
    }

    private static Object[] convertToObjectList(Row row, JdbcConnection jdbcConnection) {
        if (row == null) {
            return null;
        }
        int n = row.getColumnCount();
        Object[] objectArray = new Object[n];
        for (int i = 0; i < n; ++i) {
            objectArray[i] = ValueToObjectConverter.valueToDefaultObject(row.getValue(i), jdbcConnection, false);
        }
        return objectArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean fireRow(SessionLocal sessionLocal, Table table, Row row, Row row2, boolean bl, boolean bl2) {
        block23: {
            if (!this.rowBased || this.before != bl) {
                return false;
            }
            if (bl2 && !this.onRollback) {
                return false;
            }
            this.load();
            boolean bl3 = false;
            if ((this.typeMask & 1) != 0 && row == null && row2 != null) {
                bl3 = true;
            }
            if ((this.typeMask & 2) != 0 && row != null && row2 != null) {
                bl3 = true;
            }
            if ((this.typeMask & 4) != 0 && row != null && row2 == null) {
                bl3 = true;
            }
            if (!bl3) {
                return false;
            }
            JdbcConnection jdbcConnection = sessionLocal.createConnection(false);
            boolean bl4 = sessionLocal.getAutoCommit();
            boolean bl5 = sessionLocal.setCommitOrRollbackDisabled(true);
            Value value = sessionLocal.getLastIdentity();
            try {
                sessionLocal.setAutoCommit(false);
                if (this.triggerCallback instanceof TriggerAdapter) {
                    Value[] valueArray;
                    JdbcResultSet jdbcResultSet = row != null ? TriggerObject.createResultSet(jdbcConnection, table, row, false) : null;
                    JdbcResultSet jdbcResultSet2 = row2 != null ? TriggerObject.createResultSet(jdbcConnection, table, row2, this.before) : null;
                    try {
                        ((TriggerAdapter)this.triggerCallback).fire((Connection)jdbcConnection, jdbcResultSet, jdbcResultSet2);
                    }
                    catch (Throwable throwable) {
                        throw this.getErrorExecutingTrigger(throwable);
                    }
                    if (jdbcResultSet2 != null && (valueArray = jdbcResultSet2.getUpdateRow()) != null) {
                        boolean bl6 = false;
                        int n = valueArray.length;
                        for (int i = 0; i < n; ++i) {
                            Value value2 = valueArray[i];
                            if (value2 == null) continue;
                            bl6 = true;
                            row2.setValue(i, value2);
                        }
                        if (bl6) {
                            table.convertUpdateRow(sessionLocal, row2, true);
                        }
                    }
                    break block23;
                }
                Object[] objectArray = TriggerObject.convertToObjectList(row, jdbcConnection);
                Object[] objectArray2 = TriggerObject.convertToObjectList(row2, jdbcConnection);
                Object[] objectArray3 = this.before && objectArray2 != null ? Arrays.copyOf(objectArray2, objectArray2.length) : null;
                try {
                    this.triggerCallback.fire(jdbcConnection, objectArray, objectArray2);
                }
                catch (Throwable throwable) {
                    throw this.getErrorExecutingTrigger(throwable);
                }
                if (objectArray3 != null) {
                    boolean bl7 = false;
                    for (int i = 0; i < objectArray2.length; ++i) {
                        Object object = objectArray2[i];
                        if (object == objectArray3[i]) continue;
                        bl7 = true;
                        row2.setValue(i, ValueToObjectConverter.objectToValue(sessionLocal, object, -1));
                    }
                    if (bl7) {
                        table.convertUpdateRow(sessionLocal, row2, true);
                    }
                }
            }
            catch (Exception exception) {
                if (this.onRollback) {
                    break block23;
                }
                throw DbException.convert(exception);
            }
            finally {
                sessionLocal.setLastIdentity(value);
                sessionLocal.setCommitOrRollbackDisabled(bl5);
                sessionLocal.setAutoCommit(bl4);
            }
        }
        return this.insteadOf;
    }

    private static JdbcResultSet createResultSet(JdbcConnection jdbcConnection, Table table, Row row, boolean bl) throws SQLException {
        SimpleResult simpleResult = new SimpleResult(table.getSchema().getName(), table.getName());
        for (Column column : table.getColumns()) {
            simpleResult.addColumn(column.getName(), column.getType());
        }
        simpleResult.addRow(row.getValueList());
        simpleResult.addRow(row.getValueList());
        JdbcResultSet jdbcResultSet = new JdbcResultSet(jdbcConnection, null, null, (ResultInterface)simpleResult, -1, false, false, bl);
        jdbcResultSet.next();
        return jdbcResultSet;
    }

    private DbException getErrorExecutingTrigger(Throwable throwable) {
        if (throwable instanceof DbException) {
            return (DbException)throwable;
        }
        if (throwable instanceof SQLException) {
            return DbException.convert(throwable);
        }
        return DbException.get(90044, throwable, this.getName(), this.triggerClassName != null ? this.triggerClassName : "..source..", throwable.toString());
    }

    public int getTypeMask() {
        return this.typeMask;
    }

    public void setTypeMask(int n) {
        this.typeMask = n;
    }

    public void setRowBased(boolean bl) {
        this.rowBased = bl;
    }

    public boolean isRowBased() {
        return this.rowBased;
    }

    public void setQueueSize(int n) {
        this.queueSize = n;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

    public void setNoWait(boolean bl) {
        this.noWait = bl;
    }

    public boolean isNoWait() {
        return this.noWait;
    }

    public void setOnRollback(boolean bl) {
        this.onRollback = bl;
    }

    public boolean isOnRollback() {
        return this.onRollback;
    }

    @Override
    public String getCreateSQLForCopy(Table table, String string) {
        StringBuilder stringBuilder = new StringBuilder("CREATE FORCE TRIGGER ");
        stringBuilder.append(string);
        if (this.insteadOf) {
            stringBuilder.append(" INSTEAD OF ");
        } else if (this.before) {
            stringBuilder.append(" BEFORE ");
        } else {
            stringBuilder.append(" AFTER ");
        }
        this.getTypeNameList(stringBuilder).append(" ON ");
        table.getSQL(stringBuilder, 0);
        if (this.rowBased) {
            stringBuilder.append(" FOR EACH ROW");
        }
        if (this.noWait) {
            stringBuilder.append(" NOWAIT");
        } else {
            stringBuilder.append(" QUEUE ").append(this.queueSize);
        }
        if (this.triggerClassName != null) {
            StringUtils.quoteStringSQL(stringBuilder.append(" CALL "), this.triggerClassName);
        } else {
            StringUtils.quoteStringSQL(stringBuilder.append(" AS "), this.triggerSource);
        }
        return stringBuilder.toString();
    }

    public StringBuilder getTypeNameList(StringBuilder stringBuilder) {
        boolean bl = false;
        if ((this.typeMask & 1) != 0) {
            bl = true;
            stringBuilder.append("INSERT");
        }
        if ((this.typeMask & 2) != 0) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            stringBuilder.append("UPDATE");
        }
        if ((this.typeMask & 4) != 0) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            stringBuilder.append("DELETE");
        }
        if ((this.typeMask & 8) != 0) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            stringBuilder.append("SELECT");
        }
        if (this.onRollback) {
            if (bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("ROLLBACK");
        }
        return stringBuilder;
    }

    @Override
    public String getCreateSQL() {
        return this.getCreateSQLForCopy(this.table, this.getSQL(0));
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.table.removeTrigger(this);
        this.database.removeMeta(sessionLocal, this.getId());
        if (this.triggerCallback != null) {
            try {
                this.triggerCallback.remove();
            }
            catch (SQLException sQLException) {
                throw DbException.convert(sQLException);
            }
        }
        this.table = null;
        this.triggerClassName = null;
        this.triggerSource = null;
        this.triggerCallback = null;
        this.invalidate();
    }

    public Table getTable() {
        return this.table;
    }

    public boolean isBefore() {
        return this.before;
    }

    public String getTriggerClassName() {
        return this.triggerClassName;
    }

    public String getTriggerSource() {
        return this.triggerSource;
    }

    public void close() throws SQLException {
        if (this.triggerCallback != null) {
            this.triggerCallback.close();
        }
    }

    public boolean isSelectTrigger() {
        return (this.typeMask & 8) != 0;
    }
}

