/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.h2.api.JavaObjectSerializer;
import org.h2.command.Command;
import org.h2.command.CommandInterface;
import org.h2.command.Parser;
import org.h2.command.ParserBase;
import org.h2.command.Prepared;
import org.h2.command.ddl.Analyze;
import org.h2.command.query.Query;
import org.h2.constraint.Constraint;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.DbSettings;
import org.h2.engine.IsolationLevel;
import org.h2.engine.Mode;
import org.h2.engine.Procedure;
import org.h2.engine.Session;
import org.h2.engine.SysProperties;
import org.h2.engine.User;
import org.h2.index.Index;
import org.h2.index.QueryExpressionIndex;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.meta.DatabaseMeta;
import org.h2.jdbc.meta.DatabaseMetaLocal;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceSystem;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.db.MVIndex;
import org.h2.mvstore.db.MVTable;
import org.h2.mvstore.db.Store;
import org.h2.mvstore.tx.Transaction;
import org.h2.mvstore.tx.TransactionStore;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;
import org.h2.store.DataHandler;
import org.h2.store.InDoubtTransaction;
import org.h2.table.Table;
import org.h2.util.DateTimeUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.SmallLRUCache;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueLob;
import org.h2.value.ValueNull;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueVarchar;
import org.h2.value.VersionedValue;
import org.h2.value.lob.LobData;
import org.h2.value.lob.LobDataDatabase;
import org.h2.value.lob.LobDataInMemory;

public final class SessionLocal
extends Session
implements TransactionStore.RollbackListener {
    private static final String SYSTEM_IDENTIFIER_PREFIX = "_";
    private static int nextSerialId;
    private static final ThreadLocal<Session> THREAD_LOCAL_SESSION;
    private final int serialId = nextSerialId++;
    private Database database;
    private User user;
    private final int id;
    private NetworkConnectionInfo networkConnectionInfo;
    private final ArrayList<Table> locks = Utils.newSmallArrayList();
    private boolean autoCommit = true;
    private Random random;
    private int lockTimeout;
    private HashMap<SequenceAndPrepared, RowNumberAndValue> nextValueFor;
    private WeakHashMap<Sequence, Value> currentValueFor;
    private Value lastIdentity = ValueNull.INSTANCE;
    private HashMap<String, Savepoint> savepoints;
    private HashMap<String, Table> localTempTables;
    private HashMap<String, Index> localTempTableIndexes;
    private HashMap<String, Constraint> localTempTableConstraints;
    private int throttleMs;
    private long lastThrottleNs;
    private Command currentCommand;
    private boolean allowLiterals;
    private String currentSchemaName;
    private String[] schemaSearchPath;
    private Trace trace;
    private HashMap<String, ValueLob> removeLobMap;
    private int systemIdentifier;
    private HashMap<String, Procedure> procedures;
    private boolean autoCommitAtTransactionEnd;
    private String currentTransactionName;
    private volatile long cancelAtNs;
    private final ValueTimestampTimeZone sessionStart;
    private Instant commandStartOrEnd;
    private ValueTimestampTimeZone currentTimestamp;
    private HashMap<String, Value> variables;
    private int queryTimeout;
    private boolean commitOrRollbackDisabled;
    private Table waitForLock;
    private Thread waitForLockThread;
    private int modificationId;
    private int objectId;
    private final int queryCacheSize;
    private SmallLRUCache<String, Command> queryCache;
    private long modificationMetaID = -1L;
    private int createViewLevel;
    private volatile SmallLRUCache<Object, QueryExpressionIndex> viewIndexCache;
    private HashMap<Object, QueryExpressionIndex> derivedTableIndexCache;
    private boolean lazyQueryExecution;
    private BitSet nonKeywords;
    private TimeZoneProvider timeZone;
    private HashSet<Table> tablesToAnalyze;
    private LinkedList<TimeoutValue> temporaryResultLobs;
    private ArrayList<ValueLob> temporaryLobs;
    private Transaction transaction;
    private final AtomicReference<State> state = new AtomicReference<State>(State.INIT);
    private long startStatement = -1L;
    private IsolationLevel isolationLevel = IsolationLevel.READ_COMMITTED;
    private long snapshotDataModificationId;
    private BitSet idsToRelease;
    private boolean truncateLargeLength;
    private boolean variableBinary;
    private boolean oldInformationSchema;
    private boolean quirksMode;

    static Session getThreadLocalSession() {
        Session session = THREAD_LOCAL_SESSION.get();
        if (session == null) {
            THREAD_LOCAL_SESSION.remove();
        }
        return session;
    }

    public SessionLocal(Database database, User user, int n) {
        this.database = database;
        this.queryTimeout = database.getSettings().maxQueryTimeout;
        this.queryCacheSize = database.getSettings().queryCacheSize;
        this.user = user;
        this.id = n;
        this.lockTimeout = database.getLockTimeout();
        Schema schema = database.getMainSchema();
        this.currentSchemaName = schema != null ? schema.getName() : database.sysIdentifier("PUBLIC");
        this.timeZone = DateTimeUtils.getTimeZone();
        this.commandStartOrEnd = Instant.now();
        this.sessionStart = DateTimeUtils.currentTimestamp(this.timeZone, this.commandStartOrEnd);
    }

    public void setLazyQueryExecution(boolean bl) {
        this.lazyQueryExecution = bl;
    }

    public boolean isLazyQueryExecution() {
        return this.lazyQueryExecution;
    }

    public void setParsingCreateView(boolean bl) {
        this.createViewLevel += bl ? 1 : -1;
    }

    public boolean isParsingCreateView() {
        return this.createViewLevel != 0;
    }

    @Override
    public ArrayList<String> getClusterServers() {
        return new ArrayList<String>();
    }

    public boolean setCommitOrRollbackDisabled(boolean bl) {
        boolean bl2 = this.commitOrRollbackDisabled;
        this.commitOrRollbackDisabled = bl;
        return bl2;
    }

    private void initVariables() {
        if (this.variables == null) {
            this.variables = this.newStringsMap();
        }
    }

    public void setVariable(String string, Value value) {
        Value value2;
        this.initVariables();
        ++this.modificationId;
        if (value == ValueNull.INSTANCE) {
            value2 = this.variables.remove(string);
        } else {
            if (value instanceof ValueLob) {
                value = ((ValueLob)value).copy(this.getDatabase(), -1);
            }
            value2 = this.variables.put(string, value);
        }
        if (value2 instanceof ValueLob) {
            ((ValueLob)value2).remove();
        }
    }

    public Value getVariable(String string) {
        this.initVariables();
        Value value = this.variables.get(string);
        return value == null ? ValueNull.INSTANCE : value;
    }

    public String[] getVariableNames() {
        if (this.variables == null) {
            return new String[0];
        }
        return this.variables.keySet().toArray(new String[0]);
    }

    public Table findLocalTempTable(String string) {
        if (this.localTempTables == null) {
            return null;
        }
        return this.localTempTables.get(string);
    }

    public List<Table> getLocalTempTables() {
        if (this.localTempTables == null) {
            return Collections.emptyList();
        }
        return new ArrayList<Table>(this.localTempTables.values());
    }

    public void addLocalTempTable(Table table) {
        if (this.localTempTables == null) {
            this.localTempTables = this.newStringsMap();
        }
        if (this.localTempTables.putIfAbsent(table.getName(), table) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            table.getSQL(stringBuilder, 3).append(" AS ");
            ParserBase.quoteIdentifier(table.getName(), 3);
            throw DbException.get(42101, stringBuilder.toString());
        }
        ++this.modificationId;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeLocalTempTable(Table table) {
        if (this.localTempTables != null && this.localTempTables.remove(table.getName()) != null) {
            ++this.modificationId;
            Database database = this.database;
            if (database != null) {
                Database database2 = database;
                synchronized (database2) {
                    table.removeChildrenAndResources(this);
                }
            }
        }
    }

    public Index findLocalTempTableIndex(String string) {
        if (this.localTempTableIndexes == null) {
            return null;
        }
        return this.localTempTableIndexes.get(string);
    }

    public HashMap<String, Index> getLocalTempTableIndexes() {
        if (this.localTempTableIndexes == null) {
            return new HashMap<String, Index>();
        }
        return this.localTempTableIndexes;
    }

    public void addLocalTempTableIndex(Index index) {
        if (this.localTempTableIndexes == null) {
            this.localTempTableIndexes = this.newStringsMap();
        }
        if (this.localTempTableIndexes.putIfAbsent(index.getName(), index) != null) {
            throw DbException.get(42111, index.getTraceSQL());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeLocalTempTableIndex(Index index) {
        if (this.localTempTableIndexes != null) {
            this.localTempTableIndexes.remove(index.getName());
            Database database = this.database;
            synchronized (database) {
                index.removeChildrenAndResources(this);
            }
        }
    }

    public Constraint findLocalTempTableConstraint(String string) {
        if (this.localTempTableConstraints == null) {
            return null;
        }
        return this.localTempTableConstraints.get(string);
    }

    public HashMap<String, Constraint> getLocalTempTableConstraints() {
        if (this.localTempTableConstraints == null) {
            return new HashMap<String, Constraint>();
        }
        return this.localTempTableConstraints;
    }

    public void addLocalTempTableConstraint(Constraint constraint) {
        String string;
        if (this.localTempTableConstraints == null) {
            this.localTempTableConstraints = this.newStringsMap();
        }
        if (this.localTempTableConstraints.putIfAbsent(string = constraint.getName(), constraint) != null) {
            throw DbException.get(90045, constraint.getTraceSQL());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeLocalTempTableConstraint(Constraint constraint) {
        if (this.localTempTableConstraints != null) {
            this.localTempTableConstraints.remove(constraint.getName());
            Database database = this.database;
            synchronized (database) {
                constraint.removeChildrenAndResources(this);
            }
        }
    }

    @Override
    public boolean getAutoCommit() {
        return this.autoCommit;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public void setAutoCommit(boolean bl) {
        this.autoCommit = bl;
    }

    public int getLockTimeout() {
        return this.lockTimeout;
    }

    public void setLockTimeout(int n) {
        this.lockTimeout = n;
        if (this.hasTransaction()) {
            this.transaction.setTimeoutMillis(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CommandInterface prepareCommand(String string, int n) {
        this.lock();
        try {
            Command command = this.prepareLocal(string);
            return command;
        }
        finally {
            this.unlock();
        }
    }

    public Prepared prepare(String string) {
        return this.prepare(string, false, false);
    }

    public Prepared prepare(String string, boolean bl, boolean bl2) {
        Parser parser = new Parser(this);
        parser.setRightsChecked(bl);
        parser.setLiteralsChecked(bl2);
        return parser.prepare(string);
    }

    public Query prepareQueryExpression(String string) {
        Parser parser = new Parser(this);
        parser.setRightsChecked(true);
        parser.setLiteralsChecked(true);
        return parser.prepareQueryExpression(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Command prepareLocal(String string) {
        Command command;
        if (this.isClosed()) {
            throw DbException.get(90067, "session closed");
        }
        if (this.queryCacheSize > 0) {
            if (this.queryCache == null) {
                this.queryCache = SmallLRUCache.newInstance(this.queryCacheSize);
                this.modificationMetaID = this.getDatabase().getModificationMetaId();
            } else {
                long l = this.getDatabase().getModificationMetaId();
                if (l != this.modificationMetaID) {
                    this.queryCache.clear();
                    this.modificationMetaID = l;
                }
                if ((command = (Command)this.queryCache.get(string)) != null && command.canReuse()) {
                    command.reuse();
                    return command;
                }
            }
        }
        Parser parser = new Parser(this);
        try {
            command = parser.prepareCommand(string);
        }
        finally {
            this.derivedTableIndexCache = null;
        }
        if (this.queryCache != null && command.isCacheable()) {
            this.queryCache.put(string, command);
        }
        return command;
    }

    void scheduleDatabaseObjectIdForRelease(int n) {
        if (this.idsToRelease == null) {
            this.idsToRelease = new BitSet();
        }
        this.idsToRelease.set(n);
    }

    public Database getDatabase() {
        if (this.database == null) {
            throw DbException.get(90098);
        }
        return this.database;
    }

    public void commit(boolean bl) {
        this.beforeCommitOrRollback();
        if (this.hasTransaction()) {
            try {
                this.markUsedTablesAsUpdated();
                this.transaction.commit();
                this.removeTemporaryLobs(true);
                this.endTransaction();
            }
            finally {
                this.transaction = null;
            }
            if (!bl) {
                this.cleanTempTables(false);
                if (this.autoCommitAtTransactionEnd) {
                    this.autoCommit = true;
                    this.autoCommitAtTransactionEnd = false;
                }
            }
            this.analyzeTables();
        }
    }

    private void markUsedTablesAsUpdated() {
        if (!this.locks.isEmpty()) {
            for (Table table : this.locks) {
                if (!(table instanceof MVTable)) continue;
                ((MVTable)table).commit();
            }
        }
    }

    private void analyzeTables() {
        if (this.tablesToAnalyze != null && this.isLockedByCurrentThread()) {
            HashSet<Table> hashSet = this.tablesToAnalyze;
            this.tablesToAnalyze = null;
            int n = this.getDatabase().getSettings().analyzeSample / 10;
            for (Table table : hashSet) {
                Analyze.analyzeTable(this, table, n, false);
            }
            this.getDatabase().unlockMeta(this);
            this.commit(true);
        }
    }

    private void removeTemporaryLobs(boolean bl) {
        if (this.temporaryLobs != null) {
            for (ValueLob valueLob : this.temporaryLobs) {
                if (valueLob.isLinkedToTable()) continue;
                valueLob.remove();
            }
            this.temporaryLobs.clear();
        }
        if (this.temporaryResultLobs != null && !this.temporaryResultLobs.isEmpty()) {
            long l = System.nanoTime() - (long)this.getDatabase().getSettings().lobTimeout * 1000000L;
            while (!this.temporaryResultLobs.isEmpty()) {
                TimeoutValue timeoutValue = this.temporaryResultLobs.getFirst();
                if (bl && timeoutValue.created - l >= 0L) break;
                ValueLob valueLob = this.temporaryResultLobs.removeFirst().value;
                if (valueLob.isLinkedToTable()) continue;
                valueLob.remove();
            }
        }
    }

    private void beforeCommitOrRollback() {
        if (this.commitOrRollbackDisabled && !this.locks.isEmpty()) {
            throw DbException.get(90058);
        }
        this.currentTransactionName = null;
        this.currentTimestamp = null;
        this.getDatabase().throwLastBackgroundException();
    }

    private void endTransaction() {
        if (this.removeLobMap != null && !this.removeLobMap.isEmpty()) {
            for (ValueLob valueLob : this.removeLobMap.values()) {
                valueLob.remove();
            }
            this.removeLobMap = null;
        }
        this.unlockAll();
        if (this.idsToRelease != null) {
            this.getDatabase().releaseDatabaseObjectIds(this.idsToRelease);
            this.idsToRelease = null;
        }
        if (this.hasTransaction() && !this.transaction.allowNonRepeatableRead()) {
            this.snapshotDataModificationId = this.getDatabase().getNextModificationDataId();
        }
    }

    public long getSnapshotDataModificationId() {
        return this.snapshotDataModificationId;
    }

    public void rollback() {
        this.beforeCommitOrRollback();
        if (this.hasTransaction()) {
            this.rollbackTo(null);
        }
        this.idsToRelease = null;
        this.cleanTempTables(false);
        if (this.autoCommitAtTransactionEnd) {
            this.autoCommit = true;
            this.autoCommitAtTransactionEnd = false;
        }
        this.endTransaction();
    }

    public void rollbackTo(Savepoint savepoint) {
        int n;
        int n2 = n = savepoint == null ? 0 : savepoint.logIndex;
        if (this.hasTransaction()) {
            this.markUsedTablesAsUpdated();
            if (savepoint == null) {
                this.transaction.rollback();
                this.transaction = null;
            } else {
                this.transaction.rollbackToSavepoint(savepoint.transactionSavepoint);
            }
        }
        if (this.savepoints != null) {
            String[] stringArray;
            for (String string : stringArray = this.savepoints.keySet().toArray(new String[0])) {
                Savepoint savepoint2 = this.savepoints.get(string);
                int n3 = savepoint2.logIndex;
                if (n3 <= n) continue;
                this.savepoints.remove(string);
            }
        }
        if (this.queryCache != null) {
            this.queryCache.clear();
        }
    }

    @Override
    public boolean hasPendingTransaction() {
        return this.hasTransaction() && this.transaction.hasChanges() && this.transaction.getStatus() != 2;
    }

    public Savepoint setSavepoint() {
        Savepoint savepoint = new Savepoint();
        savepoint.transactionSavepoint = this.getStatementSavepoint();
        return savepoint;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void cancel() {
        this.cancelAtNs = Utils.currentNanoTime();
    }

    void suspend() {
        this.cancel();
        if (this.transitionToState(State.SUSPENDED, false) == State.SLEEP) {
            this.close();
        }
    }

    @Override
    public void close() {
        if (this.state.getAndSet(State.CLOSED) != State.CLOSED) {
            try {
                if (this.queryCache != null) {
                    this.queryCache.clear();
                }
                this.database.throwLastBackgroundException();
                this.database.checkPowerOff();
                if (this.hasPreparedTransaction()) {
                    this.removeLobMap = null;
                    this.endTransaction();
                } else {
                    this.rollback();
                    this.removeTemporaryLobs(false);
                    this.cleanTempTables(true);
                    this.commit(true);
                }
                this.database.unlockMeta(this);
            }
            finally {
                this.database.removeSession(this);
                this.database = null;
                this.user = null;
            }
        }
    }

    public void registerTableAsLocked(Table table) {
        if (SysProperties.CHECK && this.locks.contains(table)) {
            throw DbException.getInternalError(table.toString());
        }
        this.locks.add(table);
    }

    public void registerTableAsUpdated(Table table) {
        if (!this.locks.contains(table)) {
            this.locks.add(table);
        }
    }

    void unlock(Table table) {
        this.locks.remove(table);
    }

    private boolean hasTransaction() {
        return this.transaction != null;
    }

    private void unlockAll() {
        if (!this.locks.isEmpty()) {
            Table[] tableArray;
            for (Table table : tableArray = this.locks.toArray(new Table[0])) {
                if (table == null) continue;
                table.unlock(this);
            }
            this.locks.clear();
        }
        Database.unlockMetaDebug(this);
        this.savepoints = null;
        this.sessionStateChanged = true;
    }

    private void cleanTempTables(boolean bl) {
        if (this.localTempTables != null && !this.localTempTables.isEmpty()) {
            Iterator<Table> iterator2 = this.localTempTables.values().iterator();
            while (iterator2.hasNext()) {
                Table table = iterator2.next();
                if (bl || table.getOnCommitDrop()) {
                    ++this.modificationId;
                    table.setModified();
                    iterator2.remove();
                    this.database.lockMeta(this);
                    table.removeChildrenAndResources(this);
                    if (!bl) continue;
                    this.database.throwLastBackgroundException();
                    continue;
                }
                if (!table.getOnCommitTruncate()) continue;
                table.truncate(this);
            }
        }
    }

    public Random getRandom() {
        if (this.random == null) {
            this.random = new Random();
        }
        return this.random;
    }

    @Override
    public Trace getTrace() {
        if (this.trace != null && !this.isClosed()) {
            return this.trace;
        }
        String string = "jdbc[" + this.id + "]";
        Database database = this.database;
        if (this.isClosed() || database == null) {
            return new TraceSystem(null).getTrace(string);
        }
        this.trace = database.getTraceSystem().getTrace(string);
        return this.trace;
    }

    public Value getNextValueFor(Sequence sequence2, Prepared prepared) {
        WeakHashMap<Sequence, Value> weakHashMap;
        Value value;
        Mode mode = this.getMode();
        if (mode.nextValueReturnsDifferentValues || prepared == null) {
            value = sequence2.getNext(this);
        } else {
            if (this.nextValueFor == null) {
                this.nextValueFor = new HashMap();
            }
            weakHashMap = new SequenceAndPrepared(sequence2, prepared);
            RowNumberAndValue rowNumberAndValue = this.nextValueFor.get(weakHashMap);
            long l = prepared.getCurrentRowNumber();
            if (rowNumberAndValue != null) {
                if (rowNumberAndValue.rowNumber == l) {
                    value = rowNumberAndValue.nextValue;
                } else {
                    rowNumberAndValue.nextValue = value = sequence2.getNext(this);
                    rowNumberAndValue.rowNumber = l;
                }
            } else {
                value = sequence2.getNext(this);
                this.nextValueFor.put((SequenceAndPrepared)((Object)weakHashMap), new RowNumberAndValue(l, value));
            }
        }
        weakHashMap = this.currentValueFor;
        if (weakHashMap == null) {
            weakHashMap = new WeakHashMap<Sequence, Value>();
            this.currentValueFor = weakHashMap;
        }
        weakHashMap.put(sequence2, value);
        if (mode.takeGeneratedSequenceValue) {
            this.lastIdentity = value;
        }
        return value;
    }

    public Value getCurrentValueFor(Sequence sequence2) {
        Value value;
        WeakHashMap<Sequence, Value> weakHashMap = this.currentValueFor;
        if (weakHashMap != null && (value = weakHashMap.get(sequence2)) != null) {
            return value;
        }
        throw DbException.get(90148, sequence2.getTraceSQL());
    }

    public void setLastIdentity(Value value) {
        this.lastIdentity = value;
    }

    public Value getLastIdentity() {
        return this.lastIdentity;
    }

    public boolean containsUncommitted() {
        return this.transaction != null && this.transaction.hasChanges();
    }

    public void addSavepoint(String string) {
        if (this.savepoints == null) {
            this.savepoints = this.newStringsMap();
        }
        this.savepoints.put(string, this.setSavepoint());
    }

    public void rollbackToSavepoint(String string) {
        Savepoint savepoint;
        this.beforeCommitOrRollback();
        if (this.savepoints == null || (savepoint = this.savepoints.get(string)) == null) {
            throw DbException.get(90063, string);
        }
        this.rollbackTo(savepoint);
    }

    public void prepareCommit(String string) {
        if (this.hasPendingTransaction()) {
            this.getDatabase().prepareCommit(this, string);
        }
        this.currentTransactionName = string;
    }

    public boolean hasPreparedTransaction() {
        return this.currentTransactionName != null;
    }

    public void setPreparedTransaction(String string, boolean bl) {
        if (this.hasPreparedTransaction() && this.currentTransactionName.equals(string)) {
            if (bl) {
                this.commit(false);
            } else {
                this.rollback();
            }
        } else {
            ArrayList<InDoubtTransaction> arrayList = this.getDatabase().getInDoubtTransactions();
            int n = bl ? 1 : 2;
            boolean bl2 = false;
            for (InDoubtTransaction inDoubtTransaction : arrayList) {
                if (!inDoubtTransaction.getTransactionName().equals(string)) continue;
                inDoubtTransaction.setState(n);
                bl2 = true;
                break;
            }
            if (!bl2) {
                throw DbException.get(90129, string);
            }
        }
    }

    @Override
    public boolean isClosed() {
        return this.state.get() == State.CLOSED;
    }

    public boolean isOpen() {
        State state = this.state.get();
        this.checkSuspended(state);
        return state != State.CLOSED;
    }

    public void setThrottle(int n) {
        this.throttleMs = n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void throttle() {
        if (this.throttleMs == 0) {
            return;
        }
        long l = System.nanoTime();
        if (this.lastThrottleNs != 0L && l - this.lastThrottleNs < 50000000L) {
            return;
        }
        this.lastThrottleNs = Utils.nanoTimePlusMillis(l, this.throttleMs);
        State state = this.transitionToState(State.THROTTLED, false);
        try {
            Thread.sleep(this.throttleMs);
        }
        catch (InterruptedException interruptedException) {
        }
        finally {
            this.transitionToState(state, false);
        }
    }

    private void setCurrentCommand(Command command) {
        State state = command == null ? State.SLEEP : State.RUNNING;
        this.transitionToState(state, true);
        if (this.isOpen()) {
            this.currentCommand = command;
            this.commandStartOrEnd = Instant.now();
            if (command != null) {
                if (this.queryTimeout > 0) {
                    this.cancelAtNs = Utils.currentNanoTimePlusMillis(this.queryTimeout);
                }
            } else {
                if (this.currentTimestamp != null && !this.getMode().dateTimeValueWithinTransaction) {
                    this.currentTimestamp = null;
                }
                if (this.nextValueFor != null) {
                    this.nextValueFor.clear();
                }
            }
        }
    }

    private State transitionToState(State state, boolean bl) {
        State state2;
        while (!((state2 = this.state.get()) == State.CLOSED || bl && !this.checkSuspended(state2) || this.state.compareAndSet(state2, state))) {
        }
        return state2;
    }

    private boolean checkSuspended(State state) {
        if (state == State.SUSPENDED) {
            this.close();
            throw DbException.get(90135);
        }
        return true;
    }

    public void checkCanceled() {
        this.throttle();
        long l = this.cancelAtNs;
        if (l == 0L) {
            return;
        }
        if (System.nanoTime() - l >= 0L) {
            this.cancelAtNs = 0L;
            throw DbException.get(57014);
        }
    }

    public long getCancel() {
        return this.cancelAtNs;
    }

    public Command getCurrentCommand() {
        return this.currentCommand;
    }

    public ValueTimestampTimeZone getCommandStartOrEnd() {
        return DateTimeUtils.currentTimestamp(this.timeZone, this.commandStartOrEnd);
    }

    public boolean getAllowLiterals() {
        return this.allowLiterals;
    }

    public void setAllowLiterals(boolean bl) {
        this.allowLiterals = bl;
    }

    public void setCurrentSchema(Schema schema) {
        ++this.modificationId;
        if (this.queryCache != null) {
            this.queryCache.clear();
        }
        this.currentSchemaName = schema.getName();
    }

    @Override
    public String getCurrentSchemaName() {
        return this.currentSchemaName;
    }

    @Override
    public void setCurrentSchemaName(String string) {
        Schema schema = this.getDatabase().getSchema(string);
        this.setCurrentSchema(schema);
    }

    public JdbcConnection createConnection(boolean bl) {
        String string = bl ? "jdbc:columnlist:connection" : "jdbc:default:connection";
        return new JdbcConnection(this, this.getUser().getName(), string);
    }

    @Override
    public DataHandler getDataHandler() {
        return this.getDatabase();
    }

    public void removeAtCommit(ValueLob valueLob) {
        if (valueLob.isLinkedToTable()) {
            if (this.removeLobMap == null) {
                this.removeLobMap = new HashMap();
            }
            this.removeLobMap.put(valueLob.toString(), valueLob);
        }
    }

    public void removeAtCommitStop(ValueLob valueLob) {
        if (valueLob.isLinkedToTable() && this.removeLobMap != null) {
            this.removeLobMap.remove(valueLob.toString());
        }
    }

    public String getNextSystemIdentifier(String string) {
        String string2;
        while (string.contains(string2 = SYSTEM_IDENTIFIER_PREFIX + this.systemIdentifier++)) {
        }
        return string2;
    }

    public void addProcedure(Procedure procedure) {
        if (this.procedures == null) {
            this.procedures = this.newStringsMap();
        }
        this.procedures.put(procedure.getName(), procedure);
    }

    public void removeProcedure(String string) {
        if (this.procedures != null) {
            this.procedures.remove(string);
        }
    }

    public Procedure getProcedure(String string) {
        if (this.procedures == null) {
            return null;
        }
        return this.procedures.get(string);
    }

    public void setSchemaSearchPath(String[] stringArray) {
        ++this.modificationId;
        this.schemaSearchPath = stringArray;
    }

    public String[] getSchemaSearchPath() {
        return this.schemaSearchPath;
    }

    public int hashCode() {
        return this.serialId;
    }

    public String toString() {
        return "#" + this.serialId + " (user: " + (this.user == null ? "<null>" : this.user.getName()) + ", " + (Object)((Object)this.state.get()) + ")";
    }

    public void begin() {
        this.autoCommitAtTransactionEnd = true;
        this.autoCommit = false;
    }

    public ValueTimestampTimeZone getSessionStart() {
        return this.sessionStart;
    }

    public Set<Table> getLocks() {
        if (this.getDatabase().getLockMode() == 0 || this.locks.isEmpty()) {
            return Collections.emptySet();
        }
        Object[] objectArray = this.locks.toArray();
        switch (objectArray.length) {
            case 1: {
                Object object = objectArray[0];
                if (object != null) {
                    return Collections.singleton((Table)object);
                }
            }
            case 0: {
                return Collections.emptySet();
            }
        }
        HashSet<Table> hashSet = new HashSet<Table>();
        for (Object object : objectArray) {
            if (object == null) continue;
            hashSet.add((Table)object);
        }
        return hashSet;
    }

    public void waitIfExclusiveModeEnabled() {
        SessionLocal sessionLocal;
        this.transitionToState(State.RUNNING, true);
        if (this.getDatabase().getLobSession() == this) {
            return;
        }
        while (this.isOpen() && (sessionLocal = this.getDatabase().getExclusiveSession()) != null && sessionLocal != this && !sessionLocal.isLockedByCurrentThread()) {
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public Map<Object, QueryExpressionIndex> getViewIndexCache(boolean bl) {
        if (bl) {
            if (this.derivedTableIndexCache == null) {
                this.derivedTableIndexCache = new HashMap();
            }
            return this.derivedTableIndexCache;
        }
        SmallLRUCache<Object, QueryExpressionIndex> smallLRUCache = this.viewIndexCache;
        if (smallLRUCache == null) {
            this.viewIndexCache = smallLRUCache = SmallLRUCache.newInstance(64);
        }
        return smallLRUCache;
    }

    public void setQueryTimeout(int n) {
        int n2 = this.getDatabase().getSettings().maxQueryTimeout;
        if (n2 != 0 && (n2 < n || n == 0)) {
            n = n2;
        }
        this.queryTimeout = n;
        this.cancelAtNs = 0L;
    }

    public int getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setWaitForLock(Table table, Thread thread2) {
        this.waitForLock = table;
        this.waitForLockThread = thread2;
    }

    public Table getWaitForLock() {
        return this.waitForLock;
    }

    public Thread getWaitForLockThread() {
        return this.waitForLockThread;
    }

    public int getModificationId() {
        return this.modificationId;
    }

    public Value getTransactionId() {
        if (this.transaction == null || !this.transaction.hasChanges()) {
            return ValueNull.INSTANCE;
        }
        return ValueVarchar.get(Long.toString(this.transaction.getSequenceNum()));
    }

    public int nextObjectId() {
        return this.objectId++;
    }

    public Transaction getTransaction() {
        if (this.transaction == null) {
            Store store = this.getDatabase().getStore();
            if (store.getMvStore().isClosed()) {
                Throwable throwable = this.getDatabase().getBackgroundException();
                this.getDatabase().shutdownImmediately();
                throw DbException.get(90098, throwable, new String[0]);
            }
            this.transaction = store.getTransactionStore().begin(this, this.lockTimeout, this.id, this.isolationLevel);
            this.startStatement = -1L;
        }
        return this.transaction;
    }

    private long getStatementSavepoint() {
        if (this.startStatement == -1L) {
            this.startStatement = this.getTransaction().setSavepoint();
        }
        return this.startStatement;
    }

    public void startStatementWithinTransaction(Command command) {
        Transaction transaction = this.getTransaction();
        if (transaction != null) {
            HashSet<MVMap<Object, VersionedValue<Object>>> hashSet = new HashSet<MVMap<Object, VersionedValue<Object>>>();
            if (command != null) {
                Set<DbObject> set = command.getDependencies();
                switch (transaction.getIsolationLevel()) {
                    case SNAPSHOT: 
                    case SERIALIZABLE: {
                        if (!transaction.hasStatementDependencies()) {
                            for (Schema schema : this.getDatabase().getAllSchemasNoMeta()) {
                                for (Table table : schema.getAllTablesAndViews(null)) {
                                    if (!(table instanceof MVTable)) continue;
                                    SessionLocal.addTableToDependencies((MVTable)table, hashSet);
                                }
                            }
                            break;
                        }
                    }
                    case READ_COMMITTED: 
                    case READ_UNCOMMITTED: {
                        for (DbObject dbObject : set) {
                            if (!(dbObject instanceof MVTable)) continue;
                            SessionLocal.addTableToDependencies((MVTable)dbObject, hashSet);
                        }
                        break;
                    }
                    case REPEATABLE_READ: {
                        HashSet<MVTable> hashSet2 = new HashSet<MVTable>();
                        for (DbObject dbObject : set) {
                            if (!(dbObject instanceof MVTable)) continue;
                            SessionLocal.addTableToDependencies((MVTable)dbObject, hashSet, hashSet2);
                        }
                        break;
                    }
                }
            }
            transaction.markStatementStart(hashSet);
        }
        this.startStatement = -1L;
        if (command != null) {
            this.setCurrentCommand(command);
        }
    }

    private static void addTableToDependencies(MVTable mVTable, HashSet<MVMap<Object, VersionedValue<Object>>> hashSet) {
        for (Index index : mVTable.getIndexes()) {
            if (!(index instanceof MVIndex)) continue;
            hashSet.add(((MVIndex)index).getMVMap());
        }
    }

    private static void addTableToDependencies(MVTable mVTable, HashSet<MVMap<Object, VersionedValue<Object>>> hashSet, HashSet<MVTable> hashSet2) {
        if (!hashSet2.add(mVTable)) {
            return;
        }
        SessionLocal.addTableToDependencies(mVTable, hashSet);
        ArrayList<Constraint> arrayList = mVTable.getConstraints();
        if (arrayList != null) {
            for (Constraint constraint : arrayList) {
                Table table = constraint.getTable();
                if (table == mVTable || !(table instanceof MVTable)) continue;
                SessionLocal.addTableToDependencies((MVTable)table, hashSet, hashSet2);
            }
        }
    }

    public void endStatement() {
        this.setCurrentCommand(null);
        if (this.hasTransaction()) {
            this.transaction.markStatementEnd();
        }
        this.startStatement = -1L;
    }

    public void clearViewIndexCache() {
        this.viewIndexCache = null;
    }

    @Override
    public ValueLob addTemporaryLob(ValueLob valueLob) {
        LobData lobData = valueLob.getLobData();
        if (lobData instanceof LobDataInMemory) {
            return valueLob;
        }
        int n = ((LobDataDatabase)lobData).getTableId();
        if (n == -3 || n == -2) {
            if (this.temporaryResultLobs == null) {
                this.temporaryResultLobs = new LinkedList();
            }
            this.temporaryResultLobs.add(new TimeoutValue(valueLob));
        } else {
            if (this.temporaryLobs == null) {
                this.temporaryLobs = new ArrayList();
            }
            this.temporaryLobs.add(valueLob);
        }
        return valueLob;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    public void markTableForAnalyze(Table table) {
        if (this.tablesToAnalyze == null) {
            this.tablesToAnalyze = new HashSet();
        }
        this.tablesToAnalyze.add(table);
    }

    public State getState() {
        return this.getBlockingSessionId() != 0 ? State.BLOCKED : this.state.get();
    }

    public int getBlockingSessionId() {
        return this.transaction == null ? 0 : this.transaction.getBlockerId();
    }

    @Override
    public void onRollback(MVMap<Object, VersionedValue<Object>> mVMap, Object object, VersionedValue<Object> versionedValue, VersionedValue<Object> versionedValue2) {
        Store store = this.getDatabase().getStore();
        MVTable mVTable = store.getTable(mVMap.getName());
        if (mVTable != null) {
            Row row = versionedValue == null ? null : (Row)versionedValue.getCurrentValue();
            Row row2 = versionedValue2 == null ? null : (Row)versionedValue2.getCurrentValue();
            mVTable.fireAfterRow(this, row, row2, true);
            if (mVTable.getContainsLargeObject()) {
                Value value;
                int n;
                int n2;
                if (row != null) {
                    n2 = row.getColumnCount();
                    for (n = 0; n < n2; ++n) {
                        value = row.getValue(n);
                        if (!(value instanceof ValueLob)) continue;
                        this.removeAtCommit((ValueLob)value);
                    }
                }
                if (row2 != null) {
                    n2 = row2.getColumnCount();
                    for (n = 0; n < n2; ++n) {
                        value = row2.getValue(n);
                        if (!(value instanceof ValueLob)) continue;
                        this.removeAtCommitStop((ValueLob)value);
                    }
                }
            }
        }
    }

    public NetworkConnectionInfo getNetworkConnectionInfo() {
        return this.networkConnectionInfo;
    }

    @Override
    public void setNetworkConnectionInfo(NetworkConnectionInfo networkConnectionInfo) {
        this.networkConnectionInfo = networkConnectionInfo;
    }

    @Override
    public ValueTimestampTimeZone currentTimestamp() {
        ValueTimestampTimeZone valueTimestampTimeZone = this.currentTimestamp;
        if (valueTimestampTimeZone == null) {
            this.currentTimestamp = valueTimestampTimeZone = DateTimeUtils.currentTimestamp(this.timeZone, this.commandStartOrEnd);
        }
        return valueTimestampTimeZone;
    }

    @Override
    public Mode getMode() {
        return this.getDatabase().getMode();
    }

    @Override
    public JavaObjectSerializer getJavaObjectSerializer() {
        return this.getDatabase().getJavaObjectSerializer();
    }

    @Override
    public IsolationLevel getIsolationLevel() {
        return this.isolationLevel;
    }

    @Override
    public void setIsolationLevel(IsolationLevel isolationLevel) {
        this.commit(false);
        this.isolationLevel = isolationLevel;
    }

    public BitSet getNonKeywords() {
        return this.nonKeywords;
    }

    public void setNonKeywords(BitSet bitSet) {
        this.nonKeywords = bitSet;
    }

    @Override
    public Session.StaticSettings getStaticSettings() {
        Session.StaticSettings staticSettings = this.staticSettings;
        if (staticSettings == null) {
            DbSettings dbSettings = this.getDatabase().getSettings();
            this.staticSettings = staticSettings = new Session.StaticSettings(dbSettings.databaseToUpper, dbSettings.databaseToLower, dbSettings.caseInsensitiveIdentifiers);
        }
        return staticSettings;
    }

    @Override
    public Session.DynamicSettings getDynamicSettings() {
        return new Session.DynamicSettings(this.getMode(), this.timeZone);
    }

    @Override
    public TimeZoneProvider currentTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZoneProvider timeZoneProvider) {
        if (!timeZoneProvider.equals(this.timeZone)) {
            this.timeZone = timeZoneProvider;
            ValueTimestampTimeZone valueTimestampTimeZone = this.currentTimestamp;
            if (valueTimestampTimeZone != null) {
                long l = valueTimestampTimeZone.getDateValue();
                long l2 = valueTimestampTimeZone.getTimeNanos();
                int n = valueTimestampTimeZone.getTimeZoneOffsetSeconds();
                this.currentTimestamp = DateTimeUtils.timestampTimeZoneAtOffset(l, l2, n, timeZoneProvider.getTimeZoneOffsetUTC(DateTimeUtils.getEpochSeconds(l, l2, n)));
            }
            ++this.modificationId;
        }
    }

    public boolean areEqual(Value value, Value value2) {
        return value.compareTo(value2, this, this.getCompareMode()) == 0;
    }

    public int compare(Value value, Value value2) {
        return value.compareTo(value2, this, this.getCompareMode());
    }

    public int compareWithNull(Value value, Value value2, boolean bl) {
        return value.compareWithNull(value2, bl, this, this.getCompareMode());
    }

    public int compareTypeSafe(Value value, Value value2) {
        return value.compareTypeSafe(value2, this.getCompareMode(), this);
    }

    public void setTruncateLargeLength(boolean bl) {
        this.truncateLargeLength = bl;
    }

    public boolean isTruncateLargeLength() {
        return this.truncateLargeLength;
    }

    public void setVariableBinary(boolean bl) {
        this.variableBinary = bl;
    }

    public boolean isVariableBinary() {
        return this.variableBinary;
    }

    public void setOldInformationSchema(boolean bl) {
        this.oldInformationSchema = bl;
    }

    @Override
    public boolean isOldInformationSchema() {
        return this.oldInformationSchema;
    }

    @Override
    public DatabaseMeta getDatabaseMeta() {
        return new DatabaseMetaLocal(this);
    }

    @Override
    public boolean zeroBasedEnums() {
        return this.getDatabase().zeroBasedEnums();
    }

    public void setQuirksMode(boolean bl) {
        this.quirksMode = bl;
    }

    public boolean isQuirksMode() {
        return this.quirksMode || this.getDatabase().isStarting();
    }

    @Override
    public Session setThreadLocalSession() {
        Session session = THREAD_LOCAL_SESSION.get();
        THREAD_LOCAL_SESSION.set(this);
        return session;
    }

    @Override
    public void resetThreadLocalSession(Session session) {
        if (session == null) {
            THREAD_LOCAL_SESSION.remove();
        } else {
            THREAD_LOCAL_SESSION.set(session);
        }
    }

    private CompareMode getCompareMode() {
        return this.getDatabase().getCompareMode();
    }

    private <T> HashMap<String, T> newStringsMap() {
        return this.getDatabase().newStringMap();
    }

    static {
        THREAD_LOCAL_SESSION = new ThreadLocal();
    }

    public static class TimeoutValue {
        final long created = System.nanoTime();
        final ValueLob value;

        TimeoutValue(ValueLob valueLob) {
            this.value = valueLob;
        }
    }

    public static class Savepoint {
        int logIndex;
        long transactionSavepoint;
    }

    private static final class RowNumberAndValue {
        long rowNumber;
        Value nextValue;

        RowNumberAndValue(long l, Value value) {
            this.rowNumber = l;
            this.nextValue = value;
        }
    }

    private static final class SequenceAndPrepared {
        private final Sequence sequence;
        private final Prepared prepared;

        SequenceAndPrepared(Sequence sequence2, Prepared prepared) {
            this.sequence = sequence2;
            this.prepared = prepared;
        }

        public int hashCode() {
            return 31 * (31 + this.prepared.hashCode()) + this.sequence.hashCode();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || object.getClass() != SequenceAndPrepared.class) {
                return false;
            }
            SequenceAndPrepared sequenceAndPrepared = (SequenceAndPrepared)object;
            return this.sequence == sequenceAndPrepared.sequence && this.prepared == sequenceAndPrepared.prepared;
        }
    }

    public static enum State {
        INIT,
        RUNNING,
        BLOCKED,
        SLEEP,
        THROTTLED,
        SUSPENDED,
        CLOSED;

    }
}

