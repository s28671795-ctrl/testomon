/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.h2.command.Command;
import org.h2.command.Token;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Parameter;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.result.ResultInterface;
import org.h2.table.TableView;

public abstract class Prepared {
    protected SessionLocal session;
    protected String sqlStatement;
    protected ArrayList<Token> sqlTokens;
    protected boolean create = true;
    protected ArrayList<Parameter> parameters;
    private boolean withParamValues;
    protected boolean prepareAlways;
    private long modificationMetaId;
    private Command command;
    private int persistedObjectId;
    private long currentRowNumber;
    private int rowScanCount;
    private List<TableView> cteCleanups;

    public Prepared(SessionLocal sessionLocal) {
        this.session = sessionLocal;
        this.modificationMetaId = this.getDatabase().getModificationMetaId();
    }

    public abstract boolean isTransactional();

    public abstract ResultInterface queryMeta();

    public abstract int getType();

    public boolean isReadOnly() {
        return false;
    }

    public boolean needRecompile() {
        Database database = this.getDatabase();
        if (database == null) {
            throw DbException.get(90067, "database closed");
        }
        return this.prepareAlways || this.modificationMetaId < database.getModificationMetaId() || database.getSettings().recompileAlways;
    }

    long getModificationMetaId() {
        return this.modificationMetaId;
    }

    void setModificationMetaId(long l) {
        this.modificationMetaId = l;
    }

    public void setParameterList(ArrayList<Parameter> arrayList) {
        this.parameters = arrayList;
    }

    public ArrayList<Parameter> getParameters() {
        return this.parameters;
    }

    public boolean isWithParamValues() {
        return this.withParamValues;
    }

    public void setWithParamValues(boolean bl) {
        this.withParamValues = bl;
    }

    protected void checkParameters() {
        if (this.persistedObjectId < 0) {
            this.persistedObjectId ^= 0xFFFFFFFF;
        }
        if (this.parameters != null) {
            for (Parameter parameter : this.parameters) {
                parameter.checkSet();
            }
        }
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public boolean isQuery() {
        return false;
    }

    public void prepare() {
    }

    public long update() {
        throw DbException.get(90001);
    }

    public ResultInterface query(long l) {
        throw DbException.get(90002);
    }

    public final void setSQL(String string, ArrayList<Token> arrayList) {
        this.sqlStatement = string;
        this.sqlTokens = arrayList;
    }

    public final String getSQL() {
        return this.sqlStatement;
    }

    public final ArrayList<Token> getSQLTokens() {
        return this.sqlTokens;
    }

    public int getPersistedObjectId() {
        int n = this.persistedObjectId;
        return n >= 0 ? n : 0;
    }

    protected int getObjectId() {
        int n = this.persistedObjectId;
        if (n == 0) {
            n = this.getDatabase().allocateObjectId();
        } else if (n < 0) {
            throw DbException.getInternalError("Prepared.getObjectId() was called before");
        }
        this.persistedObjectId ^= 0xFFFFFFFF;
        return n;
    }

    public String getPlanSQL(int n) {
        return null;
    }

    public void checkCanceled() {
        Command command;
        this.session.checkCanceled();
        Command command2 = command = this.command != null ? this.command : this.session.getCurrentCommand();
        if (command != null) {
            command.checkCanceled();
        }
    }

    public void setPersistedObjectId(int n) {
        this.persistedObjectId = n;
        this.create = false;
    }

    public void setSession(SessionLocal sessionLocal) {
        this.session = sessionLocal;
    }

    void trace(Database database, long l, long l2) {
        long l3;
        if (this.session.getTrace().isInfoEnabled() && l > 0L) {
            l3 = System.nanoTime() - l;
            String string = Trace.formatParams(this.parameters);
            this.session.getTrace().infoSQL(this.sqlStatement, string, l2, l3 / 1000000L);
        }
        if (database != null && database.getQueryStatistics() && l != 0L) {
            l3 = System.nanoTime() - l;
            database.getQueryStatisticsData().update(this.toString(), l3, l2);
        }
    }

    public void setPrepareAlways(boolean bl) {
        this.prepareAlways = bl;
    }

    public void setCurrentRowNumber(long l) {
        if ((++this.rowScanCount & 0x7F) == 0) {
            this.checkCanceled();
        }
        this.currentRowNumber = l;
        this.setProgress();
    }

    public long getCurrentRowNumber() {
        return this.currentRowNumber;
    }

    private void setProgress() {
        if ((this.currentRowNumber & 0x7FL) == 0L) {
            this.getDatabase().setProgress(7, this.sqlStatement, this.currentRowNumber, 0L);
        }
    }

    public String toString() {
        return this.sqlStatement;
    }

    public static String getSimpleSQL(Expression[] expressionArray) {
        return Expression.writeExpressions(new StringBuilder(), expressionArray, 3).toString();
    }

    protected final DbException setRow(DbException dbException, long l, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.sqlStatement != null) {
            stringBuilder.append(this.sqlStatement);
        }
        stringBuilder.append(" -- ");
        if (l > 0L) {
            stringBuilder.append("row #").append(l + 1L).append(' ');
        }
        stringBuilder.append('(').append(string).append(')');
        return dbException.addSQL(stringBuilder.toString());
    }

    public boolean isCacheable() {
        return false;
    }

    public List<TableView> getCteCleanups() {
        return this.cteCleanups;
    }

    public void setCteCleanups(List<TableView> list) {
        this.cteCleanups = list;
    }

    public final SessionLocal getSession() {
        return this.session;
    }

    public void collectDependencies(HashSet<DbObject> hashSet) {
    }

    protected final Database getDatabase() {
        return this.session.getDatabase();
    }

    public boolean isRetryable() {
        return true;
    }
}

