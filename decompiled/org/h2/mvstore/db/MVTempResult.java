/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.Collection;
import org.h2.engine.Database;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.db.MVPlainTempResult;
import org.h2.mvstore.db.MVSortedTempResult;
import org.h2.result.ResultExternal;
import org.h2.result.SortOrder;
import org.h2.store.fs.FileUtils;
import org.h2.util.TempFileDeleter;
import org.h2.value.Value;

public abstract class MVTempResult
implements ResultExternal {
    private final Database database;
    final MVStore store;
    final Expression[] expressions;
    final int visibleColumnCount;
    final int resultColumnCount;
    int rowCount;
    final MVTempResult parent;
    int childCount;
    boolean closed;
    private final TempFileDeleter tempFileDeleter;
    private final CloseImpl closeable;
    private final Reference<?> fileRef;

    public static ResultExternal of(Database database, Expression[] expressionArray, boolean bl, int[] nArray, int n, int n2, SortOrder sortOrder) {
        return bl || nArray != null || sortOrder != null ? new MVSortedTempResult(database, expressionArray, bl, nArray, n, n2, sortOrder) : new MVPlainTempResult(database, expressionArray, n, n2);
    }

    MVTempResult(MVTempResult mVTempResult) {
        this.parent = mVTempResult;
        this.database = mVTempResult.database;
        this.store = mVTempResult.store;
        this.expressions = mVTempResult.expressions;
        this.visibleColumnCount = mVTempResult.visibleColumnCount;
        this.resultColumnCount = mVTempResult.resultColumnCount;
        this.tempFileDeleter = null;
        this.closeable = null;
        this.fileRef = null;
    }

    MVTempResult(Database database, Expression[] expressionArray, int n, int n2) {
        this.database = database;
        try {
            String string = FileUtils.createTempFile("h2tmp", ".temp.db", true);
            FileStore<?> fileStore = database.getStore().getMvStore().getFileStore().open(string, false);
            MVStore.Builder builder = new MVStore.Builder().adoptFileStore(fileStore).cacheSize(0).autoCommitDisabled();
            this.store = builder.open();
            this.expressions = expressionArray;
            this.visibleColumnCount = n;
            this.resultColumnCount = n2;
            this.tempFileDeleter = database.getTempFileDeleter();
            this.closeable = new CloseImpl(this.store, string);
            this.fileRef = this.tempFileDeleter.addFile(this.closeable, this);
        }
        catch (IOException iOException) {
            throw DbException.convert(iOException);
        }
        this.parent = null;
    }

    @Override
    public int addRows(Collection<Value[]> collection) {
        for (Value[] valueArray : collection) {
            this.addRow(valueArray);
        }
        return this.rowCount;
    }

    @Override
    public synchronized void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.parent != null) {
            this.parent.closeChild();
        } else if (this.childCount == 0) {
            this.delete();
        }
    }

    private synchronized void closeChild() {
        if (--this.childCount == 0 && this.closed) {
            this.delete();
        }
    }

    private void delete() {
        this.tempFileDeleter.deleteFile(this.fileRef, this.closeable);
    }

    private static final class CloseImpl
    implements AutoCloseable {
        private final MVStore store;
        private final String fileName;

        CloseImpl(MVStore mVStore, String string) {
            this.store = mVStore;
            this.fileName = string;
        }

        @Override
        public void close() throws Exception {
            this.store.closeImmediately();
            FileUtils.tryDelete(this.fileName);
        }
    }
}

