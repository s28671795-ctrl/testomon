/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.io.IOException;
import java.util.ArrayList;
import org.h2.engine.SessionRemote;
import org.h2.engine.SysProperties;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.result.FetchedResult;
import org.h2.result.ResultColumn;
import org.h2.value.Transfer;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class ResultRemote
extends FetchedResult {
    private int fetchSize;
    private SessionRemote session;
    private Transfer transfer;
    private int id;
    private final ResultColumn[] columns;
    private long rowCount;
    private long rowOffset;
    private ArrayList<Value[]> result;
    private final Trace trace;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResultRemote(SessionRemote sessionRemote, Transfer transfer, int n, int n2, int n3) throws IOException {
        this.session = sessionRemote;
        this.trace = sessionRemote.getTrace();
        this.transfer = transfer;
        this.id = n;
        this.columns = new ResultColumn[n2];
        this.rowCount = transfer.readRowCount();
        for (int i = 0; i < n2; ++i) {
            this.columns[i] = new ResultColumn(transfer);
        }
        this.rowId = -1L;
        this.fetchSize = n3;
        if (this.rowCount >= 0L) {
            n3 = (int)Math.min(this.rowCount, (long)n3);
            this.result = new ArrayList(n3);
        } else {
            this.result = new ArrayList();
        }
        sessionRemote.lock();
        try {
            try {
                if (this.fetchRows(n3)) {
                    this.rowCount = this.result.size();
                }
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, null);
            }
        }
        finally {
            sessionRemote.unlock();
        }
    }

    @Override
    public boolean isLazy() {
        return this.rowCount < 0L;
    }

    @Override
    public String getAlias(int n) {
        return this.columns[n].alias;
    }

    @Override
    public String getSchemaName(int n) {
        return this.columns[n].schemaName;
    }

    @Override
    public String getTableName(int n) {
        return this.columns[n].tableName;
    }

    @Override
    public String getColumnName(int n) {
        return this.columns[n].columnName;
    }

    @Override
    public TypeInfo getColumnType(int n) {
        return this.columns[n].columnType;
    }

    @Override
    public boolean isIdentity(int n) {
        return this.columns[n].identity;
    }

    @Override
    public int getNullable(int n) {
        return this.columns[n].nullable;
    }

    @Override
    public void reset() {
        if (this.rowCount < 0L || this.rowOffset > 0L) {
            throw DbException.get(90128);
        }
        this.rowId = -1L;
        this.currentRow = null;
        this.nextRow = null;
        this.afterLast = false;
        SessionRemote sessionRemote = this.session;
        if (sessionRemote == null) {
            return;
        }
        sessionRemote.lock();
        try {
            sessionRemote.checkClosed();
            try {
                sessionRemote.traceOperation("RESULT_RESET", this.id);
                this.transfer.writeInt(6).writeInt(this.id).flush();
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, null);
            }
        }
        finally {
            sessionRemote.unlock();
        }
    }

    @Override
    public int getVisibleColumnCount() {
        return this.columns.length;
    }

    @Override
    public long getRowCount() {
        if (this.rowCount < 0L) {
            throw DbException.getUnsupportedException("Row count is unknown for lazy result.");
        }
        return this.rowCount;
    }

    @Override
    public boolean hasNext() {
        if (this.afterLast) {
            return false;
        }
        if (this.nextRow == null && (this.rowCount < 0L || this.rowId < this.rowCount - 1L)) {
            int n;
            long l = this.rowId + 1L;
            if (this.session != null) {
                this.remapIfOld();
                if (l - this.rowOffset >= (long)this.result.size()) {
                    this.fetchAdditionalRows();
                }
            }
            this.nextRow = (n = (int)(l - this.rowOffset)) < this.result.size() ? this.result.get(n) : null;
        }
        return this.nextRow != null;
    }

    private void sendClose() {
        SessionRemote sessionRemote = this.session;
        if (sessionRemote == null) {
            return;
        }
        sessionRemote.lock();
        try {
            sessionRemote.traceOperation("RESULT_CLOSE", this.id);
            this.transfer.writeInt(7).writeInt(this.id);
        }
        catch (IOException iOException) {
            this.trace.error(iOException, "close");
        }
        finally {
            sessionRemote.unlock();
            this.transfer = null;
            this.session = null;
        }
    }

    @Override
    public void close() {
        this.result = null;
        this.sendClose();
    }

    private void remapIfOld() {
        try {
            if (this.id <= this.session.getCurrentId() - SysProperties.SERVER_CACHED_OBJECTS / 2) {
                int n = this.session.getNextId();
                this.session.traceOperation("CHANGE_ID", this.id);
                this.transfer.writeInt(9).writeInt(this.id).writeInt(n);
                this.id = n;
            }
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    private void fetchAdditionalRows() {
        SessionRemote sessionRemote = this.session;
        sessionRemote.lock();
        try {
            sessionRemote.checkClosed();
            try {
                this.rowOffset += (long)this.result.size();
                this.result.clear();
                int n = this.fetchSize;
                if (this.rowCount >= 0L) {
                    n = (int)Math.min((long)n, this.rowCount - this.rowOffset);
                } else if (n == Integer.MAX_VALUE) {
                    n = SysProperties.SERVER_RESULT_SET_FETCH_SIZE;
                }
                sessionRemote.traceOperation("RESULT_FETCH_ROWS", this.id);
                this.transfer.writeInt(5).writeInt(this.id).writeInt(n);
                sessionRemote.done(this.transfer);
                this.fetchRows(n);
            }
            catch (IOException iOException) {
                throw DbException.convertIOException(iOException, null);
            }
        }
        finally {
            sessionRemote.unlock();
        }
    }

    private boolean fetchRows(int n) throws IOException {
        int n2 = this.columns.length;
        block5: for (int i = 0; i < n; ++i) {
            switch (this.transfer.readByte()) {
                case 1: {
                    Value[] valueArray = new Value[n2];
                    for (int j = 0; j < n2; ++j) {
                        valueArray[j] = this.transfer.readValue(this.columns[j].columnType);
                    }
                    this.result.add(valueArray);
                    continue block5;
                }
                case 0: {
                    this.sendClose();
                    return true;
                }
                case -1: {
                    throw SessionRemote.readException(this.transfer);
                }
                default: {
                    throw DbException.getInternalError();
                }
            }
        }
        if (this.rowCount >= 0L && this.rowOffset + (long)this.result.size() >= this.rowCount) {
            this.sendClose();
        }
        return false;
    }

    public String toString() {
        return "columns: " + this.columns.length + (this.rowCount < 0L ? " lazy" : " rows: " + this.rowCount) + " pos: " + this.rowId;
    }

    @Override
    public int getFetchSize() {
        return this.fetchSize;
    }

    @Override
    public void setFetchSize(int n) {
        this.fetchSize = n;
    }

    @Override
    public boolean isClosed() {
        return this.result == null;
    }
}

