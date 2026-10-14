/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.h2.command.ddl.CreateTableData;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.mvstore.FileStore;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.MVStoreTool;
import org.h2.mvstore.db.MVInDoubtTransaction;
import org.h2.mvstore.db.MVTable;
import org.h2.mvstore.db.ValueDataType;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.mvstore.type.MetaType;
import org.h2.store.InDoubtTransaction;
import org.h2.store.fs.FileUtils;
import org.h2.util.HasSQL;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;

public final class Store {
    private final ConcurrentHashMap<String, MVTable> tableMap = new ConcurrentHashMap();
    private final MVStore mvStore;
    private final TransactionStore transactionStore;
    private long statisticsStart;
    private int temporaryMapId;
    private final boolean encrypted;
    private final String fileName;

    static char[] decodePassword(byte[] byArray) {
        char[] cArray = new char[byArray.length / 2];
        for (int i = 0; i < cArray.length; ++i) {
            cArray[i] = (char)((byArray[i + i] & 0xFF) << 16 | byArray[i + i + 1] & 0xFF);
        }
        return cArray;
    }

    public Store(Database database, byte[] byArray) {
        String string = database.getDatabasePath();
        MVStore.Builder builder = new MVStore.Builder();
        boolean bl = false;
        if (string != null) {
            String string2;
            this.fileName = string2 = string + ".mv.db";
            MVStoreTool.compactCleanUp(string2);
            builder.fileName(string2);
            builder.pageSplitSize(database.getPageSize());
            if (database.isReadOnly()) {
                builder.readOnly();
            } else {
                boolean bl2 = FileUtils.exists(string2);
                if (!bl2 || FileUtils.canWrite(string2)) {
                    String string3 = FileUtils.getParent(string2);
                    FileUtils.createDirectories(string3);
                }
                int n = database.getSettings().autoCompactFillRate;
                if (n <= 100) {
                    builder.autoCompactFillRate(n);
                }
            }
            if (byArray != null) {
                bl = true;
                builder.encryptionKey(Store.decodePassword(byArray));
            }
            if (database.getSettings().compressData) {
                builder.compress();
                builder.pageSplitSize(65536);
            }
            builder.backgroundExceptionHandler((thread2, throwable) -> database.setBackgroundException(DbException.convert(throwable)));
            builder.autoCommitDisabled();
        } else {
            this.fileName = null;
        }
        this.encrypted = bl;
        try {
            this.mvStore = builder.open();
            if (!database.getSettings().reuseSpace) {
                this.mvStore.setReuseSpace(false);
            }
            this.mvStore.setVersionsToKeep(0);
            this.transactionStore = new TransactionStore(this.mvStore, new MetaType<Database>(database, this.mvStore.backgroundExceptionHandler), new ValueDataType(database, null), database.getLockTimeout());
        }
        catch (MVStoreException mVStoreException) {
            throw this.convertMVStoreException(mVStoreException);
        }
    }

    DbException convertMVStoreException(MVStoreException mVStoreException) {
        switch (mVStoreException.getErrorCode()) {
            case 4: {
                throw DbException.get(90098, mVStoreException, this.fileName);
            }
            case 5: {
                throw DbException.get(90048, mVStoreException, this.fileName);
            }
            case 6: {
                if (this.encrypted) {
                    throw DbException.get(90049, mVStoreException, this.fileName);
                }
                throw DbException.get(90030, mVStoreException, this.fileName);
            }
            case 7: {
                throw DbException.get(90020, mVStoreException, this.fileName);
            }
            case 1: 
            case 2: {
                throw DbException.get(90028, mVStoreException, this.fileName);
            }
        }
        throw DbException.get(50000, mVStoreException, mVStoreException.getMessage());
    }

    public static DbException getInvalidExpressionTypeException(String string, Typed typed) {
        TypeInfo typeInfo = typed.getType();
        if (typeInfo.getValueType() == -1) {
            return DbException.get(50004, (typed instanceof HasSQL ? (HasSQL)((Object)typed) : typeInfo).getTraceSQL());
        }
        return DbException.get(90008, typeInfo.getTraceSQL(), string);
    }

    public MVStore getMvStore() {
        return this.mvStore;
    }

    public TransactionStore getTransactionStore() {
        return this.transactionStore;
    }

    public MVTable getTable(String string) {
        return this.tableMap.get(string);
    }

    public MVTable createTable(CreateTableData createTableData) {
        try {
            MVTable mVTable = new MVTable(createTableData, this);
            this.tableMap.put(mVTable.getMapName(), mVTable);
            return mVTable;
        }
        catch (MVStoreException mVStoreException) {
            throw this.convertMVStoreException(mVStoreException);
        }
    }

    public void removeTable(MVTable mVTable) {
        try {
            this.tableMap.remove(mVTable.getMapName());
        }
        catch (MVStoreException mVStoreException) {
            throw this.convertMVStoreException(mVStoreException);
        }
    }

    public void flush() {
        if (this.mvStore.isPersistent() && !this.mvStore.isReadOnly()) {
            this.mvStore.commit();
        }
    }

    public void closeImmediately() {
        this.mvStore.closeImmediately();
    }

    public void removeTemporaryMaps(BitSet bitSet) {
        for (String string : this.mvStore.getMapNames()) {
            int n;
            if (string.startsWith("temp.")) {
                this.mvStore.removeMap(string);
                continue;
            }
            if (!string.startsWith("table.") && !string.startsWith("index.") || bitSet.get(n = StringUtils.parseUInt31(string, string.indexOf(46) + 1, string.length()))) continue;
            this.mvStore.removeMap(string);
        }
    }

    public synchronized String nextTemporaryMapName() {
        return "temp." + this.temporaryMapId++;
    }

    public void prepareCommit(SessionLocal sessionLocal, String string) {
        Transaction transaction = sessionLocal.getTransaction();
        transaction.setName(string);
        transaction.prepare();
        this.mvStore.commit();
    }

    public ArrayList<InDoubtTransaction> getInDoubtTransactions() {
        List<Transaction> list = this.transactionStore.getOpenTransactions();
        ArrayList<InDoubtTransaction> arrayList = Utils.newSmallArrayList();
        for (Transaction transaction : list) {
            if (transaction.getStatus() != 2) continue;
            arrayList.add(new MVInDoubtTransaction(this.mvStore, transaction));
        }
        return arrayList;
    }

    public void setCacheSize(int n) {
        this.mvStore.setCacheSize(n);
    }

    public void sync() {
        this.flush();
        this.mvStore.sync();
    }

    public void compactFile(int n) {
        this.mvStore.compactFile(n);
    }

    public void close(int n) {
        try {
            FileStore<?> fileStore = this.mvStore.getFileStore();
            if (!this.mvStore.isClosed() && fileStore != null) {
                boolean bl;
                boolean bl2 = bl = n == -1;
                if (fileStore.isReadOnly()) {
                    bl = false;
                } else {
                    this.transactionStore.close();
                }
                if (bl) {
                    n = 0;
                }
                String string = null;
                FileStore<?> fileStore2 = null;
                if (bl) {
                    string = fileStore.getFileName();
                    String string2 = string + ".tempFile";
                    FileUtils.delete(string2);
                    fileStore2 = fileStore.open(string2, false);
                }
                this.mvStore.close(n);
                if (bl && FileUtils.exists(string)) {
                    Store.compact(string, fileStore2);
                }
            }
        }
        catch (MVStoreException mVStoreException) {
            this.mvStore.closeImmediately();
            throw DbException.get(90028, mVStoreException, "Closing");
        }
    }

    private static void compact(String string, FileStore<?> fileStore) {
        MVStore.Builder builder = new MVStore.Builder().compress().adoptFileStore(fileStore);
        try (MVStore mVStore = builder.open();){
            FileStore<?> fileStore2 = fileStore.open(string, true);
            MVStore.Builder builder2 = new MVStore.Builder();
            builder2.readOnly().adoptFileStore(fileStore2);
            try (MVStore mVStore2 = builder2.open();){
                MVStoreTool.compact(mVStore2, mVStore);
            }
        }
        MVStoreTool.moveAtomicReplace(fileStore.getFileName(), string);
    }

    public void statisticsStart() {
        FileStore<?> fileStore = this.mvStore.getFileStore();
        this.statisticsStart = fileStore == null ? 0L : fileStore.getReadCount();
    }

    public Map<String, Integer> statisticsEnd() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        FileStore<?> fileStore = this.mvStore.getFileStore();
        int n = fileStore == null ? 0 : (int)(fileStore.getReadCount() - this.statisticsStart);
        hashMap.put("reads", n);
        return hashMap;
    }
}

