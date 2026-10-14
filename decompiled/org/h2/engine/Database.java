/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.h2.api.DatabaseEventListener;
import org.h2.api.JavaObjectSerializer;
import org.h2.api.TableEngine;
import org.h2.command.Prepared;
import org.h2.command.ddl.CreateTableData;
import org.h2.command.dml.SetTypes;
import org.h2.constraint.Constraint;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Comment;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.DbObject;
import org.h2.engine.DbSettings;
import org.h2.engine.DelayedDatabaseCloser;
import org.h2.engine.Engine;
import org.h2.engine.MetaRecord;
import org.h2.engine.Mode;
import org.h2.engine.OnExitDatabaseCloser;
import org.h2.engine.QueryStatisticsData;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.Session;
import org.h2.engine.SessionLocal;
import org.h2.engine.Setting;
import org.h2.engine.SysProperties;
import org.h2.engine.User;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceSystem;
import org.h2.mode.DefaultNullOrdering;
import org.h2.mode.PgCatalogSchema;
import org.h2.mvstore.MVStoreException;
import org.h2.mvstore.db.LobStorageMap;
import org.h2.mvstore.db.Store;
import org.h2.result.Row;
import org.h2.result.RowFactory;
import org.h2.result.SearchRow;
import org.h2.schema.InformationSchema;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.TriggerObject;
import org.h2.security.auth.Authenticator;
import org.h2.store.DataHandler;
import org.h2.store.FileLock;
import org.h2.store.FileLockMethod;
import org.h2.store.FileStore;
import org.h2.store.InDoubtTransaction;
import org.h2.store.LobStorageInterface;
import org.h2.store.fs.FileUtils;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableLinkConnection;
import org.h2.table.TableSynonym;
import org.h2.table.TableType;
import org.h2.table.TableView;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Server;
import org.h2.util.JdbcUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.SmallLRUCache;
import org.h2.util.SourceCompiler;
import org.h2.util.StringUtils;
import org.h2.util.TempFileDeleter;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.value.CaseInsensitiveConcurrentMap;
import org.h2.value.CaseInsensitiveMap;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.ValueInteger;
import org.h2.value.ValueTimestampTimeZone;

public final class Database
implements DataHandler,
CastDataProvider {
    private static int initialPowerOffCount;
    private static final boolean ASSERT;
    private static final ThreadLocal<SessionLocal> META_LOCK_DEBUGGING;
    private static final ThreadLocal<Database> META_LOCK_DEBUGGING_DB;
    private static final ThreadLocal<Throwable> META_LOCK_DEBUGGING_STACK;
    private static final SessionLocal[] EMPTY_SESSION_ARRAY;
    private static final String SYSTEM_USER_NAME = "DBA";
    private final boolean persistent;
    private final String databaseName;
    private final String databaseShortName;
    private final String databaseURL;
    private final String cipher;
    private final byte[] filePasswordHash;
    private final ConcurrentHashMap<String, RightOwner> usersAndRoles = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Setting> settings = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Schema> schemas = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Right> rights = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Comment> comments = new ConcurrentHashMap();
    private final HashMap<String, TableEngine> tableEngines = new HashMap();
    private final Set<SessionLocal> userSessions = Collections.synchronizedSet(new HashSet());
    private final AtomicReference<SessionLocal> exclusiveSession = new AtomicReference();
    private final BitSet objectIds = new BitSet();
    private final Object lobSyncObject = new Object();
    private final Schema mainSchema;
    private final Schema infoSchema;
    private final Schema pgCatalogSchema;
    private int nextSessionId;
    private final AtomicInteger nextTempTableId = new AtomicInteger();
    private final User systemUser;
    private SessionLocal systemSession;
    private SessionLocal lobSession;
    private final Table meta;
    private final Index metaIdIndex;
    private FileLock lock;
    private volatile boolean starting;
    private final TraceSystem traceSystem;
    private final Trace trace;
    private final FileLockMethod fileLockMethod;
    private final Role publicRole;
    private final AtomicLong modificationDataId = new AtomicLong();
    private final AtomicLong modificationMetaId = new AtomicLong();
    private final AtomicLong remoteSettingsId = new AtomicLong();
    private CompareMode compareMode;
    private String cluster = "''";
    private boolean readOnly;
    private DatabaseEventListener eventListener;
    private int maxMemoryRows = SysProperties.MAX_MEMORY_ROWS;
    private int lockMode;
    private int maxLengthInplaceLob;
    private int allowLiterals = 2;
    private int powerOffCount = initialPowerOffCount;
    private volatile int closeDelay;
    private DelayedDatabaseCloser delayedCloser;
    private volatile boolean closing;
    private boolean ignoreCase;
    private boolean deleteFilesOnDisconnect;
    private boolean optimizeReuseResults = true;
    private final String cacheType;
    private boolean referentialIntegrity = true;
    private Mode mode = Mode.getRegular();
    private DefaultNullOrdering defaultNullOrdering = DefaultNullOrdering.LOW;
    private int maxOperationMemory = 100000;
    private SmallLRUCache<String, String[]> lobFileListCache;
    private final boolean closeAtVmShutdown;
    private final boolean autoServerMode;
    private final int autoServerPort;
    private Server server;
    private HashMap<TableLinkConnection, TableLinkConnection> linkConnections;
    private final TempFileDeleter tempFileDeleter = TempFileDeleter.getInstance();
    private int compactMode;
    private SourceCompiler compiler;
    private final LobStorageInterface lobStorage;
    private final int pageSize;
    private int defaultTableType = 0;
    private final DbSettings dbSettings;
    private final Store store;
    private boolean allowBuiltinAliasOverride;
    private final AtomicReference<DbException> backgroundException = new AtomicReference();
    private JavaObjectSerializer javaObjectSerializer;
    private String javaObjectSerializerName;
    private volatile boolean javaObjectSerializerInitialized;
    private volatile boolean queryStatistics;
    private int queryStatisticsMaxEntries = 100;
    private QueryStatisticsData queryStatisticsData;
    private RowFactory rowFactory = RowFactory.getRowFactory();
    private boolean ignoreCatalogs;
    private Authenticator authenticator;

    public Database(ConnectionInfo connectionInfo, String string2) {
        String string3;
        if (ASSERT) {
            META_LOCK_DEBUGGING.set(null);
            META_LOCK_DEBUGGING_DB.set(null);
            META_LOCK_DEBUGGING_STACK.set(null);
        }
        String string4 = connectionInfo.getName();
        this.dbSettings = connectionInfo.getDbSettings();
        this.compareMode = CompareMode.getInstance(null, 0);
        this.persistent = connectionInfo.isPersistent();
        this.filePasswordHash = connectionInfo.getFilePasswordHash();
        this.databaseName = string4;
        this.databaseShortName = this.parseDatabaseShortName();
        this.maxLengthInplaceLob = this.persistent ? 256 : 0x7FFFFFF7;
        this.cipher = string2;
        this.autoServerMode = connectionInfo.getProperty("AUTO_SERVER", false);
        this.autoServerPort = connectionInfo.getProperty("AUTO_SERVER_PORT", 0);
        this.pageSize = connectionInfo.getProperty("PAGE_SIZE", 4096);
        if (string2 != null && this.pageSize % 4096 != 0) {
            throw DbException.getUnsupportedException("CIPHER && PAGE_SIZE=" + this.pageSize);
        }
        String string5 = StringUtils.toLowerEnglish(connectionInfo.getProperty("ACCESS_MODE_DATA", "rw"));
        if ("r".equals(string5)) {
            this.readOnly = true;
        }
        this.fileLockMethod = (string3 = connectionInfo.getProperty("FILE_LOCK", null)) != null ? FileLock.getFileLockMethod(string3) : (this.autoServerMode ? FileLockMethod.FILE : FileLockMethod.FS);
        this.databaseURL = connectionInfo.getURL();
        String string6 = connectionInfo.removeProperty("DATABASE_EVENT_LISTENER", null);
        if (string6 != null) {
            this.setEventListenerClass(StringUtils.trim(string6, true, true, '\''));
        }
        if ((string6 = connectionInfo.removeProperty("MODE", null)) != null) {
            this.mode = Mode.getInstance(string6);
            if (this.mode == null) {
                throw DbException.get(90088, string6);
            }
        }
        if ((string6 = connectionInfo.removeProperty("DEFAULT_NULL_ORDERING", null)) != null) {
            try {
                this.defaultNullOrdering = DefaultNullOrdering.valueOf(StringUtils.toUpperEnglish(string6));
            }
            catch (RuntimeException runtimeException) {
                throw DbException.getInvalidValueException("DEFAULT_NULL_ORDERING", string6);
            }
        }
        if ((string6 = connectionInfo.getProperty("JAVA_OBJECT_SERIALIZER", null)) != null) {
            this.javaObjectSerializerName = string6 = StringUtils.trim(string6, true, true, '\'');
        }
        this.allowBuiltinAliasOverride = connectionInfo.getProperty("BUILTIN_ALIAS_OVERRIDE", false);
        if (this.autoServerMode && (this.readOnly || !this.persistent || this.fileLockMethod == FileLockMethod.NO || this.fileLockMethod == FileLockMethod.FS)) {
            throw DbException.getUnsupportedException("AUTO_SERVER=TRUE && (readOnly || inMemory || FILE_LOCK=NO || FILE_LOCK=FS)");
        }
        this.closeAtVmShutdown = connectionInfo.getProperty("DB_CLOSE_ON_EXIT", this.persistent);
        if (this.autoServerMode && !this.closeAtVmShutdown) {
            throw DbException.getUnsupportedException("AUTO_SERVER=TRUE && DB_CLOSE_ON_EXIT=FALSE");
        }
        int n = connectionInfo.getIntProperty(9, 1);
        int n2 = connectionInfo.getIntProperty(8, 0);
        this.cacheType = StringUtils.toUpperEnglish(connectionInfo.removeProperty("CACHE_TYPE", "LRU"));
        this.ignoreCatalogs = connectionInfo.getProperty("IGNORE_CATALOGS", this.dbSettings.ignoreCatalogs);
        this.lockMode = connectionInfo.getProperty("LOCK_MODE", 3);
        String string7 = this.persistent ? (this.readOnly ? (n >= 3 ? Utils.getProperty("java.io.tmpdir", ".") + "/h2_" + System.currentTimeMillis() + ".trace.db" : null) : string4 + ".trace.db") : null;
        this.traceSystem = new TraceSystem(string7);
        this.traceSystem.setLevelFile(n);
        this.traceSystem.setLevelSystemOut(n2);
        this.trace = this.traceSystem.getTrace(2);
        this.trace.info("opening {0} (build {1})", string4, 224);
        try {
            String string8;
            Setting setting;
            Object object;
            if (this.persistent) {
                object = string4 + ".lock.db";
                if (this.readOnly) {
                    if (FileUtils.exists((String)object)) {
                        throw DbException.get(90020, "Lock file exists: " + (String)object);
                    }
                } else if (this.fileLockMethod != FileLockMethod.NO && this.fileLockMethod != FileLockMethod.FS) {
                    this.lock = new FileLock(this.traceSystem, (String)object, 1000);
                    this.lock.lock(this.fileLockMethod);
                    if (this.autoServerMode) {
                        this.startServer(this.lock.getUniqueId());
                    }
                }
                this.deleteOldTempFiles();
            }
            this.starting = true;
            if (!this.dbSettings.mvStore) {
                throw new UnsupportedOperationException();
            }
            this.store = new Store(this, connectionInfo.getFileEncryptionKey());
            this.starting = false;
            this.systemUser = new User(this, 0, SYSTEM_USER_NAME, true);
            this.systemUser.setAdmin(true);
            this.mainSchema = new Schema(this, 0, this.sysIdentifier("PUBLIC"), this.systemUser, true);
            this.infoSchema = new InformationSchema(this, this.systemUser);
            this.schemas.put(this.mainSchema.getName(), this.mainSchema);
            this.schemas.put(this.infoSchema.getName(), this.infoSchema);
            if (this.mode.getEnum() == Mode.ModeEnum.PostgreSQL) {
                this.pgCatalogSchema = new PgCatalogSchema(this, this.systemUser);
                this.schemas.put(this.pgCatalogSchema.getName(), this.pgCatalogSchema);
            } else {
                this.pgCatalogSchema = null;
            }
            this.publicRole = new Role(this, 0, this.sysIdentifier("PUBLIC"), true);
            this.usersAndRoles.put(this.publicRole.getName(), this.publicRole);
            this.systemSession = this.createSession(this.systemUser);
            this.lobSession = this.createSession(this.systemUser);
            object = this.dbSettings.getSettings().keySet();
            this.store.getTransactionStore().init(this.lobSession);
            object.removeIf(string -> string.startsWith("PAGE_STORE_"));
            CreateTableData createTableData = this.createSysTableData();
            this.starting = true;
            this.meta = this.mainSchema.createTable(createTableData);
            IndexColumn[] indexColumnArray = IndexColumn.wrap(new Column[]{createTableData.columns.get(0)});
            this.metaIdIndex = this.meta.addIndex(this.systemSession, "SYS_ID", 0, indexColumnArray, 1, IndexType.createPrimaryKey(false, false), true, null);
            this.systemSession.commit(true);
            this.objectIds.set(0);
            this.executeMeta();
            this.systemSession.commit(true);
            this.store.getTransactionStore().endLeftoverTransactions();
            this.store.removeTemporaryMaps(this.objectIds);
            this.recompileInvalidViews();
            this.starting = false;
            if (!this.readOnly && (setting = this.settings.get(string8 = SetTypes.getTypeName(28))) == null) {
                setting = new Setting(this, this.allocateObjectId(), string8);
                setting.setIntValue(224);
                this.lockMeta(this.systemSession);
                this.addDatabaseObject(this.systemSession, setting);
            }
            this.lobStorage = new LobStorageMap(this);
            this.lobSession.commit(true);
            this.systemSession.commit(true);
            this.trace.info("opened {0}", string4);
            if (this.persistent) {
                int n3 = connectionInfo.getProperty("WRITE_DELAY", 500);
                this.setWriteDelay(n3);
            }
            if (this.closeAtVmShutdown || this.persistent) {
                OnExitDatabaseCloser.register(this);
            }
        }
        catch (Throwable throwable) {
            try {
                if (throwable instanceof OutOfMemoryError) {
                    throwable.fillInStackTrace();
                }
                if (throwable instanceof DbException) {
                    if (((DbException)throwable).getErrorCode() == 90020) {
                        this.stopServer();
                    } else {
                        this.trace.error(throwable, "opening {0}", string4);
                    }
                }
                this.traceSystem.close();
                this.closeOpenFilesAndUnlock();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw DbException.convert(throwable);
        }
    }

    public int getLockTimeout() {
        Setting setting = this.findSetting(SetTypes.getTypeName(5));
        return setting == null ? 2000 : setting.getIntValue();
    }

    public RowFactory getRowFactory() {
        return this.rowFactory;
    }

    public void setRowFactory(RowFactory rowFactory) {
        this.rowFactory = rowFactory;
    }

    public static void setInitialPowerOffCount(int n) {
        initialPowerOffCount = n;
    }

    public void setPowerOffCount(int n) {
        if (this.powerOffCount == -1) {
            return;
        }
        this.powerOffCount = n;
    }

    public Store getStore() {
        return this.store;
    }

    public long getModificationDataId() {
        return this.modificationDataId.get();
    }

    public long getNextModificationDataId() {
        return this.modificationDataId.incrementAndGet();
    }

    public long getModificationMetaId() {
        return this.modificationMetaId.get();
    }

    public long getNextModificationMetaId() {
        this.modificationDataId.incrementAndGet();
        return this.modificationMetaId.incrementAndGet() - 1L;
    }

    public long getRemoteSettingsId() {
        return this.remoteSettingsId.get();
    }

    public long getNextRemoteSettingsId() {
        return this.remoteSettingsId.incrementAndGet();
    }

    public int getPowerOffCount() {
        return this.powerOffCount;
    }

    @Override
    public void checkPowerOff() {
        if (this.powerOffCount != 0) {
            this.checkPowerOff2();
        }
    }

    private void checkPowerOff2() {
        if (this.powerOffCount > 1) {
            --this.powerOffCount;
            return;
        }
        if (this.powerOffCount != -1) {
            try {
                this.powerOffCount = -1;
                this.store.closeImmediately();
                if (this.lock != null) {
                    this.stopServer();
                    this.lock.unlock();
                    this.lock = null;
                }
                if (this.traceSystem != null) {
                    this.traceSystem.close();
                }
            }
            catch (DbException dbException) {
                DbException.traceThrowable(dbException);
            }
        }
        Engine.close(this.databaseName);
        throw DbException.get(90098);
    }

    public Trace getTrace(int n) {
        return this.traceSystem.getTrace(n);
    }

    @Override
    public FileStore openFile(String string, String string2, boolean bl) {
        if (bl && !FileUtils.exists(string)) {
            throw DbException.get(90124, string);
        }
        FileStore fileStore = FileStore.open(this, string, string2, this.cipher, this.filePasswordHash);
        try {
            fileStore.init();
        }
        catch (DbException dbException) {
            fileStore.closeSilently();
            throw dbException;
        }
        return fileStore;
    }

    boolean validateFilePasswordHash(String string, byte[] byArray) {
        if (!Objects.equals(string, this.cipher)) {
            return false;
        }
        return Utils.compareSecure(byArray, this.filePasswordHash);
    }

    private String parseDatabaseShortName() {
        int n;
        String string = this.databaseName;
        int n2 = n = string.length();
        block3: while (--n2 >= 0) {
            char c = string.charAt(n2);
            switch (c) {
                case '/': 
                case ':': 
                case '\\': {
                    break block3;
                }
                default: {
                    continue block3;
                }
            }
        }
        String string2 = string = ++n2 == n ? "UNNAMED" : string.substring(n2);
        return StringUtils.truncateString(this.dbSettings.databaseToUpper ? StringUtils.toUpperEnglish(string) : (this.dbSettings.databaseToLower ? StringUtils.toLowerEnglish(string) : string), 256);
    }

    private CreateTableData createSysTableData() {
        CreateTableData createTableData = new CreateTableData();
        ArrayList<Column> arrayList = createTableData.columns;
        Column column = new Column("ID", TypeInfo.TYPE_INTEGER);
        column.setNullable(false);
        arrayList.add(column);
        arrayList.add(new Column("HEAD", TypeInfo.TYPE_INTEGER));
        arrayList.add(new Column("TYPE", TypeInfo.TYPE_INTEGER));
        arrayList.add(new Column("SQL", TypeInfo.TYPE_VARCHAR));
        createTableData.tableName = "SYS";
        createTableData.id = 0;
        createTableData.temporary = false;
        createTableData.persistData = this.persistent;
        createTableData.persistIndexes = this.persistent;
        createTableData.isHidden = true;
        createTableData.session = this.systemSession;
        return createTableData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeMeta() {
        Object object;
        Cursor cursor = this.metaIdIndex.find(this.systemSession, null, null);
        ArrayList<MetaRecord> arrayList = new ArrayList<MetaRecord>();
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        ArrayList<MetaRecord> arrayList3 = new ArrayList<MetaRecord>();
        ArrayList<Object> arrayList4 = new ArrayList<Object>();
        ArrayList<MetaRecord> arrayList5 = new ArrayList<MetaRecord>();
        block11: while (cursor.next()) {
            object = new MetaRecord(cursor.get());
            this.objectIds.set(((MetaRecord)object).getId());
            switch (((MetaRecord)object).getObjectType()) {
                case 2: 
                case 6: 
                case 9: 
                case 10: {
                    arrayList.add((MetaRecord)object);
                    continue block11;
                }
                case 12: {
                    arrayList2.add(object);
                    continue block11;
                }
                case 0: 
                case 1: 
                case 3: 
                case 11: {
                    arrayList3.add((MetaRecord)object);
                    continue block11;
                }
                case 5: {
                    arrayList4.add(object);
                    continue block11;
                }
            }
            arrayList5.add((MetaRecord)object);
        }
        object = this.systemSession;
        ((Session)object).lock();
        try {
            this.executeMeta(arrayList);
            int n = arrayList2.size();
            if (n > 0) {
                int n2 = 0;
                while (true) {
                    DbException dbException = null;
                    for (int i = 0; i < n; ++i) {
                        MetaRecord metaRecord = (MetaRecord)arrayList2.get(i);
                        try {
                            metaRecord.prepareAndExecute(this, (SessionLocal)object, this.eventListener);
                            continue;
                        }
                        catch (DbException dbException2) {
                            if (dbException == null) {
                                dbException = dbException2;
                            }
                            arrayList2.set(n2++, metaRecord);
                        }
                    }
                    if (dbException == null) break;
                    if (n == n2) {
                        throw dbException;
                    }
                    n = n2;
                }
            }
            this.executeMeta(arrayList3);
            n = arrayList4.size();
            if (n > 0) {
                ArrayList<Prepared> arrayList6 = new ArrayList<Prepared>(n);
                for (int i = 0; i < n; ++i) {
                    Prepared prepared = ((MetaRecord)arrayList4.get(i)).prepare(this, (SessionLocal)object, this.eventListener);
                    if (prepared == null) continue;
                    arrayList6.add(prepared);
                }
                arrayList6.sort(MetaRecord.CONSTRAINTS_COMPARATOR);
                for (Prepared prepared : arrayList6) {
                    MetaRecord.execute(this, prepared, this.eventListener, prepared.getSQL());
                }
            }
            this.executeMeta(arrayList5);
        }
        finally {
            ((Session)object).unlock();
        }
    }

    private void executeMeta(ArrayList<MetaRecord> arrayList) {
        if (!arrayList.isEmpty()) {
            arrayList.sort(null);
            for (MetaRecord metaRecord : arrayList) {
                metaRecord.prepareAndExecute(this, this.systemSession, this.eventListener);
            }
        }
    }

    private void startServer(String string) {
        try {
            this.server = Server.createTcpServer("-tcpPort", Integer.toString(this.autoServerPort), "-tcpAllowOthers", "-tcpDaemon", "-key", string, this.databaseName);
            this.server.start();
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
        String string2 = NetUtils.getLocalAddress();
        String string3 = string2 + ":" + this.server.getPort();
        this.lock.setProperty("server", string3);
        String string4 = NetUtils.getHostName(string2);
        this.lock.setProperty("hostName", string4);
        this.lock.save();
    }

    private void stopServer() {
        if (this.server != null) {
            Server server = this.server;
            this.server = null;
            server.stop();
        }
    }

    private void recompileInvalidViews() {
        boolean bl;
        do {
            bl = false;
            for (Schema schema : this.schemas.values()) {
                for (Table table : schema.getAllTablesAndViews(null)) {
                    TableView tableView;
                    if (!(table instanceof TableView) || !(tableView = (TableView)table).isInvalid()) continue;
                    tableView.recompile(this.systemSession, true, false);
                    if (tableView.isInvalid()) continue;
                    bl = true;
                }
            }
        } while (bl);
        TableView.clearIndexCaches(this);
    }

    private void addMeta(SessionLocal sessionLocal, DbObject dbObject) {
        assert (Thread.holdsLock(this));
        int n = dbObject.getId();
        if (n > 0 && !dbObject.isTemporary() && !this.isReadOnly()) {
            Cursor cursor;
            Row row = this.meta.getTemplateRow();
            MetaRecord.populateRowFromDBObject(dbObject, row);
            assert (this.objectIds.get(n));
            if (SysProperties.CHECK) {
                this.verifyMetaLocked(sessionLocal);
            }
            if (!(cursor = this.metaIdIndex.find(sessionLocal, row, row)).next()) {
                this.meta.addRow(sessionLocal, row);
            } else {
                assert (this.starting);
                Row row2 = cursor.get();
                MetaRecord metaRecord = new MetaRecord(row2);
                assert (metaRecord.getId() == dbObject.getId());
                assert (metaRecord.getObjectType() == dbObject.getType());
                if (!metaRecord.getSQL().equals(dbObject.getCreateSQLForMeta())) {
                    this.meta.updateRow(sessionLocal, row2, row);
                }
            }
        }
    }

    public void verifyMetaLocked(SessionLocal sessionLocal) {
        if (this.lockMode != 0 && this.meta != null && !this.meta.isLockedExclusivelyBy(sessionLocal)) {
            throw DbException.getInternalError();
        }
    }

    public boolean lockMeta(SessionLocal sessionLocal) {
        if (this.meta == null) {
            return true;
        }
        if (ASSERT) {
            this.lockMetaAssertion(sessionLocal);
        }
        return this.meta.lock(sessionLocal, 2);
    }

    private void lockMetaAssertion(SessionLocal sessionLocal) {
        if (META_LOCK_DEBUGGING_DB.get() != null && META_LOCK_DEBUGGING_DB.get() != this) {
            SessionLocal sessionLocal2 = META_LOCK_DEBUGGING.get();
            if (sessionLocal2 == null) {
                META_LOCK_DEBUGGING.set(sessionLocal);
                META_LOCK_DEBUGGING_DB.set(this);
                META_LOCK_DEBUGGING_STACK.set(new Throwable("Last meta lock granted in this stack trace, this is debug information for following IllegalStateException"));
            } else if (sessionLocal2 != sessionLocal) {
                META_LOCK_DEBUGGING_STACK.get().printStackTrace();
                throw new IllegalStateException("meta currently locked by " + sessionLocal2 + ", sessionid=" + sessionLocal2.getId() + " and trying to be locked by different session, " + sessionLocal + ", sessionid=" + sessionLocal.getId() + " on same thread");
            }
        }
    }

    public void unlockMeta(SessionLocal sessionLocal) {
        if (this.meta != null) {
            Database.unlockMetaDebug(sessionLocal);
            this.meta.unlock(sessionLocal);
            sessionLocal.unlock(this.meta);
        }
    }

    static void unlockMetaDebug(SessionLocal sessionLocal) {
        if (ASSERT && META_LOCK_DEBUGGING.get() == sessionLocal) {
            META_LOCK_DEBUGGING.set(null);
            META_LOCK_DEBUGGING_DB.set(null);
            META_LOCK_DEBUGGING_STACK.set(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeMeta(SessionLocal sessionLocal, int n) {
        if (n > 0 && !this.starting) {
            SearchRow searchRow = this.meta.getRowFactory().createRow();
            searchRow.setValue(0, ValueInteger.get(n));
            boolean bl = this.lockMeta(sessionLocal);
            try {
                Cursor cursor = this.metaIdIndex.find(sessionLocal, searchRow, searchRow);
                if (cursor.next()) {
                    Row row = cursor.get();
                    this.meta.removeRow(sessionLocal, row);
                    if (SysProperties.CHECK) {
                        this.checkMetaFree(sessionLocal, n);
                    }
                }
            }
            finally {
                if (!bl) {
                    this.unlockMeta(sessionLocal);
                }
            }
            sessionLocal.scheduleDatabaseObjectIdForRelease(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseDatabaseObjectIds(BitSet bitSet) {
        BitSet bitSet2 = this.objectIds;
        synchronized (bitSet2) {
            this.objectIds.andNot(bitSet);
        }
    }

    private ConcurrentHashMap<String, DbObject> getMap(int n) {
        ConcurrentHashMap<String, DbObject> concurrentHashMap;
        switch (n) {
            case 2: 
            case 7: {
                concurrentHashMap = this.usersAndRoles;
                break;
            }
            case 6: {
                concurrentHashMap = this.settings;
                break;
            }
            case 8: {
                concurrentHashMap = this.rights;
                break;
            }
            case 10: {
                concurrentHashMap = this.schemas;
                break;
            }
            case 13: {
                concurrentHashMap = this.comments;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + n);
            }
        }
        return concurrentHashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addSchemaObject(SessionLocal sessionLocal, SchemaObject schemaObject) {
        int n = schemaObject.getId();
        if (n > 0 && !this.starting) {
            this.checkWritingAllowed();
        }
        this.lockMeta(sessionLocal);
        Database database = this;
        synchronized (database) {
            schemaObject.getSchema().add(schemaObject);
            this.addMeta(sessionLocal, schemaObject);
        }
    }

    public synchronized void addDatabaseObject(SessionLocal sessionLocal, DbObject dbObject) {
        Object object;
        int n = dbObject.getId();
        if (n > 0 && !this.starting) {
            this.checkWritingAllowed();
        }
        ConcurrentHashMap<String, DbObject> concurrentHashMap = this.getMap(dbObject.getType());
        if (dbObject.getType() == 2 && ((User)(object = (User)dbObject)).isAdmin() && this.systemUser.getName().equals(SYSTEM_USER_NAME)) {
            this.systemUser.rename(((DbObject)object).getName());
        }
        object = dbObject.getName();
        if (SysProperties.CHECK && concurrentHashMap.get(object) != null) {
            throw DbException.getInternalError("object already exists");
        }
        this.lockMeta(sessionLocal);
        this.addMeta(sessionLocal, dbObject);
        concurrentHashMap.put((String)object, dbObject);
    }

    public Comment findComment(DbObject dbObject) {
        if (dbObject.getType() == 13) {
            return null;
        }
        String string = Comment.getKey(dbObject);
        return this.comments.get(string);
    }

    public Role findRole(String string) {
        RightOwner rightOwner = this.findUserOrRole(string);
        return rightOwner instanceof Role ? (Role)rightOwner : null;
    }

    public Schema findSchema(String string) {
        if (string == null) {
            return null;
        }
        return this.schemas.get(string);
    }

    public Setting findSetting(String string) {
        return this.settings.get(string);
    }

    public User findUser(String string) {
        RightOwner rightOwner = this.findUserOrRole(string);
        return rightOwner instanceof User ? (User)rightOwner : null;
    }

    public User getUser(String string) {
        User user = this.findUser(string);
        if (user == null) {
            throw DbException.get(90032, string);
        }
        return user;
    }

    public RightOwner findUserOrRole(String string) {
        return this.usersAndRoles.get(StringUtils.toUpperEnglish(string));
    }

    synchronized SessionLocal createSession(User user, NetworkConnectionInfo networkConnectionInfo) {
        if (this.closing) {
            return null;
        }
        if (this.exclusiveSession.get() != null) {
            throw DbException.get(90135);
        }
        SessionLocal sessionLocal = this.createSession(user);
        sessionLocal.setNetworkConnectionInfo(networkConnectionInfo);
        this.userSessions.add(sessionLocal);
        this.trace.info("connecting session #{0} to {1}", sessionLocal.getId(), this.databaseName);
        if (this.delayedCloser != null) {
            this.delayedCloser.reset();
            this.delayedCloser = null;
        }
        return sessionLocal;
    }

    private SessionLocal createSession(User user) {
        int n = ++this.nextSessionId;
        return new SessionLocal(this, user, n);
    }

    public synchronized void removeSession(SessionLocal sessionLocal) {
        if (sessionLocal != null) {
            this.exclusiveSession.compareAndSet(sessionLocal, null);
            if (this.userSessions.remove(sessionLocal)) {
                this.trace.info("disconnecting session #{0}", sessionLocal.getId());
            }
        }
        if (this.isUserSession(sessionLocal)) {
            if (this.userSessions.isEmpty()) {
                if (this.closeDelay == 0) {
                    this.close();
                } else {
                    if (this.closeDelay < 0) {
                        return;
                    }
                    this.delayedCloser = new DelayedDatabaseCloser(this, this.closeDelay * 1000);
                }
            }
            if (sessionLocal != null) {
                this.trace.info("disconnected session #{0}", sessionLocal.getId());
            }
        }
    }

    boolean isUserSession(SessionLocal sessionLocal) {
        return sessionLocal != this.systemSession && sessionLocal != this.lobSession;
    }

    private synchronized void closeAllSessionsExcept(SessionLocal sessionLocal) {
        SessionLocal[] sessionLocalArray = this.userSessions.toArray(EMPTY_SESSION_ARRAY);
        boolean bl = true;
        for (SessionLocal sessionLocal2 : sessionLocalArray) {
            if (sessionLocal2 == sessionLocal) continue;
            sessionLocal2.suspend();
            bl = false;
        }
        if (bl) {
            return;
        }
        int n = this.getLockTimeout();
        long l = Math.max(n / 10, 1);
        long l2 = (long)n * 2000000L;
        long l3 = System.nanoTime();
        do {
            bl = true;
            for (SessionLocal sessionLocal3 : sessionLocalArray) {
                if (sessionLocal3 == sessionLocal || sessionLocal3.isClosed()) continue;
                bl = false;
                break;
            }
            if (bl) {
                return;
            }
            try {
                this.wait(l);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        } while (System.nanoTime() - l3 <= l2);
        for (SessionLocal sessionLocal3 : sessionLocalArray) {
            if (sessionLocal3 == sessionLocal || sessionLocal3.isClosed()) continue;
            try {
                sessionLocal3.close();
            }
            catch (Throwable throwable) {
                this.trace.error(throwable, "disconnecting session #{0}", sessionLocal3.getId());
            }
        }
    }

    void close() {
        this.close(false);
    }

    void onShutdown() {
        if (this.closeAtVmShutdown) {
            this.close(true);
        } else if (this.persistent) {
            this.checkpoint();
        }
    }

    private void close(boolean bl) {
        DbException dbException = this.backgroundException.getAndSet(null);
        try {
            this.closeImpl(bl);
        }
        catch (Throwable throwable) {
            if (dbException != null) {
                throwable.addSuppressed(dbException);
            }
            throw throwable;
        }
        if (dbException != null) {
            throw DbException.get(dbException.getErrorCode(), dbException, dbException.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void closeImpl(boolean bl) {
        Object object2 = this;
        synchronized (object2) {
            if (this.closing || !bl && !this.userSessions.isEmpty()) {
                return;
            }
            this.closing = true;
            this.stopServer();
            if (!this.userSessions.isEmpty()) {
                assert (bl);
                this.trace.info("closing {0} from shutdown hook", this.databaseName);
                this.closeAllSessionsExcept(null);
            }
            this.trace.info("closing {0}", this.databaseName);
            if (this.eventListener != null) {
                this.closing = false;
                DatabaseEventListener object3 = this.eventListener;
                this.eventListener = null;
                object3.closingDatabase();
                this.closing = true;
                if (!this.userSessions.isEmpty()) {
                    this.trace.info("event listener {0} left connection open", object3.getClass().getName());
                    this.closeAllSessionsExcept(null);
                }
            }
        }
        try {
            try {
                if (this.systemSession != null) {
                    if (this.powerOffCount != -1) {
                        for (Schema schema : this.schemas.values()) {
                            for (Table table : schema.getAllTablesAndViews(null)) {
                                if (table.isGlobalTemporary()) {
                                    this.removeSchemaObject(this.systemSession, table);
                                    continue;
                                }
                                table.close(this.systemSession);
                            }
                        }
                        for (Schema schema : this.schemas.values()) {
                            for (Sequence sequence2 : schema.getAllSequences()) {
                                sequence2.close();
                            }
                        }
                    }
                    for (Schema schema : this.schemas.values()) {
                        for (TriggerObject triggerObject : schema.getAllTriggers()) {
                            try {
                                triggerObject.close();
                            }
                            catch (SQLException sQLException) {
                                this.trace.error(sQLException, "close");
                            }
                        }
                    }
                    if (this.powerOffCount != -1) {
                        this.meta.close(this.systemSession);
                        this.systemSession.commit(true);
                    }
                    if (this.lobSession != null) {
                        this.lobSession.close();
                        this.lobSession = null;
                    }
                    this.systemSession.close();
                    this.systemSession = null;
                }
                this.tempFileDeleter.deleteAll();
                this.closeOpenFilesAndUnlock();
            }
            catch (DbException | MVStoreException runtimeException) {
                this.trace.error(runtimeException, "close");
            }
            this.trace.info("closed");
            this.traceSystem.close();
            OnExitDatabaseCloser.unregister(this);
            if (this.deleteFilesOnDisconnect && this.persistent) {
                this.deleteFilesOnDisconnect = false;
                try {
                    object2 = FileUtils.getParent(this.databaseName);
                    String string = FileUtils.getName(this.databaseName);
                    DeleteDbFiles.execute((String)object2, string, true);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        finally {
            Engine.close(this.databaseName);
        }
    }

    private synchronized void closeOpenFilesAndUnlock() {
        try {
            if (this.lobStorage != null) {
                this.lobStorage.close();
            }
            if (this.store != null && !this.store.getMvStore().isClosed()) {
                if (this.compactMode == 81) {
                    this.store.closeImmediately();
                } else {
                    int n = this.compactMode == 82 || this.compactMode == 84 || this.dbSettings.defragAlways ? -1 : this.dbSettings.maxCompactTime;
                    this.store.close(n);
                }
                if (this.persistent && (this.lock != null || this.fileLockMethod == FileLockMethod.NO || this.fileLockMethod == FileLockMethod.FS)) {
                    this.deleteOldTempFiles();
                }
            }
        }
        finally {
            if (this.lock != null) {
                this.lock.unlock();
                this.lock = null;
            }
        }
    }

    private synchronized void closeFiles() {
        try {
            this.store.closeImmediately();
        }
        catch (DbException dbException) {
            this.trace.error(dbException, "close");
        }
    }

    private void checkMetaFree(SessionLocal sessionLocal, int n) {
        SearchRow searchRow = this.meta.getRowFactory().createRow();
        searchRow.setValue(0, ValueInteger.get(n));
        Cursor cursor = this.metaIdIndex.find(sessionLocal, searchRow, searchRow);
        if (cursor.next()) {
            throw DbException.getInternalError();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int allocateObjectId() {
        int n;
        BitSet bitSet = this.objectIds;
        synchronized (bitSet) {
            n = this.objectIds.nextClearBit(0);
            this.objectIds.set(n);
        }
        return n;
    }

    public User getSystemUser() {
        return this.systemUser;
    }

    public Schema getMainSchema() {
        return this.mainSchema;
    }

    public ArrayList<Comment> getAllComments() {
        return new ArrayList<Comment>(this.comments.values());
    }

    public int getAllowLiterals() {
        if (this.starting) {
            return 2;
        }
        return this.allowLiterals;
    }

    public ArrayList<Right> getAllRights() {
        return new ArrayList<Right>(this.rights.values());
    }

    public ArrayList<Table> getAllTablesAndViews() {
        ArrayList<Table> arrayList = new ArrayList<Table>();
        for (Schema schema : this.schemas.values()) {
            arrayList.addAll(schema.getAllTablesAndViews(null));
        }
        return arrayList;
    }

    public ArrayList<TableSynonym> getAllSynonyms() {
        ArrayList<TableSynonym> arrayList = new ArrayList<TableSynonym>();
        for (Schema schema : this.schemas.values()) {
            arrayList.addAll(schema.getAllSynonyms());
        }
        return arrayList;
    }

    public Collection<Schema> getAllSchemas() {
        return this.schemas.values();
    }

    public Collection<Schema> getAllSchemasNoMeta() {
        return this.schemas.values();
    }

    public Collection<Setting> getAllSettings() {
        return this.settings.values();
    }

    public Collection<RightOwner> getAllUsersAndRoles() {
        return this.usersAndRoles.values();
    }

    public String getCacheType() {
        return this.cacheType;
    }

    public String getCluster() {
        return this.cluster;
    }

    @Override
    public CompareMode getCompareMode() {
        return this.compareMode;
    }

    @Override
    public String getDatabasePath() {
        if (this.persistent) {
            return FileUtils.toRealPath(this.databaseName);
        }
        return null;
    }

    public String getShortName() {
        return this.databaseShortName;
    }

    public String getName() {
        return this.databaseName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SessionLocal[] getSessions(boolean bl) {
        ArrayList<SessionLocal> arrayList;
        CastDataProvider castDataProvider = this;
        synchronized (castDataProvider) {
            arrayList = new ArrayList<SessionLocal>(this.userSessions);
        }
        if (bl) {
            castDataProvider = this.systemSession;
            if (castDataProvider != null) {
                arrayList.add((SessionLocal)castDataProvider);
            }
            if ((castDataProvider = this.lobSession) != null) {
                arrayList.add((SessionLocal)castDataProvider);
            }
        }
        return arrayList.toArray(new SessionLocal[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateMeta(SessionLocal sessionLocal, DbObject dbObject) {
        int n = dbObject.getId();
        if (n > 0) {
            Object object;
            if (!this.starting && !dbObject.isTemporary()) {
                object = this.meta.getTemplateRow();
                MetaRecord.populateRowFromDBObject(dbObject, (SearchRow)object);
                Row row = this.metaIdIndex.getRow(sessionLocal, n);
                if (row != null) {
                    this.meta.updateRow(sessionLocal, row, (Row)object);
                }
            }
            object = this.objectIds;
            synchronized (object) {
                this.objectIds.set(n);
            }
        }
    }

    public synchronized void renameSchemaObject(SessionLocal sessionLocal, SchemaObject schemaObject, String string) {
        this.checkWritingAllowed();
        schemaObject.getSchema().rename(schemaObject, string);
        this.updateMetaAndFirstLevelChildren(sessionLocal, schemaObject);
    }

    private synchronized void updateMetaAndFirstLevelChildren(SessionLocal sessionLocal, DbObject dbObject) {
        ArrayList<DbObject> arrayList = dbObject.getChildren();
        Comment comment = this.findComment(dbObject);
        if (comment != null) {
            throw DbException.getInternalError(comment.toString());
        }
        this.updateMeta(sessionLocal, dbObject);
        if (arrayList != null) {
            for (DbObject dbObject2 : arrayList) {
                if (dbObject2.getCreateSQL() == null) continue;
                this.updateMeta(sessionLocal, dbObject2);
            }
        }
    }

    public synchronized void renameDatabaseObject(SessionLocal sessionLocal, DbObject dbObject, String string) {
        this.checkWritingAllowed();
        int n = dbObject.getType();
        ConcurrentHashMap<String, DbObject> concurrentHashMap = this.getMap(n);
        if (SysProperties.CHECK) {
            if (!concurrentHashMap.containsKey(dbObject.getName())) {
                throw DbException.getInternalError("not found: " + dbObject.getName());
            }
            if (dbObject.getName().equals(string) || concurrentHashMap.containsKey(string)) {
                throw DbException.getInternalError("object already exists: " + string);
            }
        }
        dbObject.checkRename();
        concurrentHashMap.remove(dbObject.getName());
        dbObject.rename(string);
        concurrentHashMap.put(string, dbObject);
        this.updateMetaAndFirstLevelChildren(sessionLocal, dbObject);
    }

    private void deleteOldTempFiles() {
        String string = FileUtils.getParent(this.databaseName);
        for (String string2 : FileUtils.newDirectoryStream(string)) {
            if (!string2.endsWith(".temp.db") || !string2.startsWith(this.databaseName)) continue;
            FileUtils.tryDelete(string2);
        }
    }

    public Schema getSchema(String string) {
        Schema schema = this.findSchema(string);
        if (schema == null) {
            throw DbException.get(90079, string);
        }
        return schema;
    }

    public synchronized void removeDatabaseObject(SessionLocal sessionLocal, DbObject dbObject) {
        this.checkWritingAllowed();
        String string = dbObject.getName();
        int n = dbObject.getType();
        ConcurrentHashMap<String, DbObject> concurrentHashMap = this.getMap(n);
        if (SysProperties.CHECK && !concurrentHashMap.containsKey(string)) {
            throw DbException.getInternalError("not found: " + string);
        }
        Comment comment = this.findComment(dbObject);
        this.lockMeta(sessionLocal);
        if (comment != null) {
            this.removeDatabaseObject(sessionLocal, comment);
        }
        int n2 = dbObject.getId();
        dbObject.removeChildrenAndResources(sessionLocal);
        concurrentHashMap.remove(string);
        this.removeMeta(sessionLocal, n2);
    }

    public Table getDependentTable(SchemaObject schemaObject, Table table) {
        switch (schemaObject.getType()) {
            case 1: 
            case 2: 
            case 4: 
            case 5: 
            case 8: 
            case 13: {
                return null;
            }
        }
        HashSet<DbObject> hashSet = new HashSet<DbObject>();
        for (Schema schema : this.schemas.values()) {
            for (Table table2 : schema.getAllTablesAndViews(null)) {
                if (table == table2 || TableType.VIEW == table2.getTableType()) continue;
                hashSet.clear();
                table2.addDependencies(hashSet);
                if (!hashSet.contains(schemaObject)) continue;
                return table2;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeSchemaObject(SessionLocal sessionLocal, SchemaObject schemaObject) {
        DbObject dbObject;
        Object object;
        int n = schemaObject.getType();
        if (n == 0) {
            object = (Table)schemaObject;
            if (((DbObject)object).isTemporary() && !((Table)object).isGlobalTemporary()) {
                sessionLocal.removeLocalTempTable((Table)object);
                return;
            }
        } else if (n == 1) {
            object = (Index)schemaObject;
            dbObject = ((Index)object).getTable();
            if (dbObject.isTemporary() && !dbObject.isGlobalTemporary()) {
                sessionLocal.removeLocalTempTableIndex((Index)object);
                return;
            }
        } else if (n == 5 && ((Constraint)(object = (Constraint)schemaObject)).getConstraintType() != Constraint.Type.DOMAIN && (dbObject = ((Constraint)object).getTable()).isTemporary() && !dbObject.isGlobalTemporary()) {
            sessionLocal.removeLocalTempTableConstraint((Constraint)object);
            return;
        }
        this.checkWritingAllowed();
        this.lockMeta(sessionLocal);
        object = this;
        synchronized (object) {
            dbObject = this.findComment(schemaObject);
            if (dbObject != null) {
                this.removeDatabaseObject(sessionLocal, dbObject);
            }
            schemaObject.getSchema().remove(schemaObject);
            int n2 = schemaObject.getId();
            if (!this.starting) {
                Table table = this.getDependentTable(schemaObject, null);
                if (table != null) {
                    schemaObject.getSchema().add(schemaObject);
                    throw DbException.get(90107, schemaObject.getTraceSQL(), table.getTraceSQL());
                }
                schemaObject.removeChildrenAndResources(sessionLocal);
            }
            this.removeMeta(sessionLocal, n2);
        }
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public TraceSystem getTraceSystem() {
        return this.traceSystem;
    }

    public synchronized void setCacheSize(int n) {
        if (this.starting) {
            int n2 = MathUtils.convertLongToInt(Utils.getMemoryMax()) / 2;
            n = Math.min(n, n2);
        }
        this.store.setCacheSize(Math.max(1, n));
    }

    public synchronized void setMasterUser(User user) {
        this.lockMeta(this.systemSession);
        this.addDatabaseObject(this.systemSession, user);
        this.systemSession.commit(true);
    }

    public Role getPublicRole() {
        return this.publicRole;
    }

    public String getTempTableName(String string, SessionLocal sessionLocal) {
        String string2;
        int n = 227;
        if (string.length() > n) {
            string = string.substring(0, n);
        }
        while (this.mainSchema.findTableOrView(sessionLocal, string2 = string + "_COPY_" + sessionLocal.getId() + '_' + this.nextTempTableId.getAndIncrement()) != null) {
        }
        return string2;
    }

    public void setCompareMode(CompareMode compareMode) {
        this.compareMode = compareMode;
    }

    public void setCluster(String string) {
        this.cluster = string;
    }

    @Override
    public void checkWritingAllowed() {
        if (this.readOnly) {
            throw DbException.get(90097);
        }
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public int getWriteDelay() {
        return this.store.getMvStore().getAutoCommitDelay();
    }

    public void setWriteDelay(int n) {
        this.store.getMvStore().setAutoCommitDelay(n < 0 ? 0 : n);
    }

    public int getRetentionTime() {
        return this.store.getMvStore().getRetentionTime();
    }

    public void setRetentionTime(int n) {
        this.store.getMvStore().setRetentionTime(n);
    }

    public void setAllowBuiltinAliasOverride(boolean bl) {
        this.allowBuiltinAliasOverride = bl;
    }

    public boolean isAllowBuiltinAliasOverride() {
        return this.allowBuiltinAliasOverride;
    }

    public ArrayList<InDoubtTransaction> getInDoubtTransactions() {
        return this.store.getInDoubtTransactions();
    }

    synchronized void prepareCommit(SessionLocal sessionLocal, String string) {
        if (!this.readOnly) {
            this.store.prepareCommit(sessionLocal, string);
        }
    }

    void throwLastBackgroundException() {
        DbException dbException = this.backgroundException.getAndSet(null);
        if (dbException != null) {
            throw DbException.get(dbException.getErrorCode(), dbException, dbException.getMessage());
        }
    }

    public void setBackgroundException(DbException dbException) {
        TraceSystem traceSystem;
        if (this.backgroundException.compareAndSet(null, dbException) && (traceSystem = this.getTraceSystem()) != null) {
            traceSystem.getTrace(2).error(dbException, "flush");
        }
    }

    public Throwable getBackgroundException() {
        MVStoreException mVStoreException = this.store.getMvStore().getPanicException();
        if (mVStoreException != null) {
            return mVStoreException;
        }
        return this.backgroundException.getAndSet(null);
    }

    public synchronized void flush() {
        if (!this.readOnly) {
            try {
                this.store.flush();
            }
            catch (RuntimeException runtimeException) {
                this.backgroundException.compareAndSet(null, DbException.convert(runtimeException));
                throw runtimeException;
            }
        }
    }

    public void setEventListener(DatabaseEventListener databaseEventListener) {
        this.eventListener = databaseEventListener;
    }

    public void setEventListenerClass(String string) {
        if (string == null || string.isEmpty()) {
            this.eventListener = null;
        } else {
            try {
                this.eventListener = (DatabaseEventListener)JdbcUtils.loadUserClass(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                String string2 = this.databaseURL;
                if (this.cipher != null) {
                    string2 = string2 + ";CIPHER=" + this.cipher;
                }
                this.eventListener.init(string2);
            }
            catch (Throwable throwable) {
                throw DbException.get(90099, throwable, string, throwable.toString());
            }
        }
    }

    public void setProgress(int n, String string, long l, long l2) {
        if (this.eventListener != null) {
            try {
                this.eventListener.setProgress(n, string, l, l2);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void exceptionThrown(SQLException sQLException, String string) {
        if (this.eventListener != null) {
            try {
                this.eventListener.exceptionThrown(sQLException, string);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public synchronized void sync() {
        if (this.readOnly) {
            return;
        }
        this.store.sync();
    }

    public int getMaxMemoryRows() {
        return this.maxMemoryRows;
    }

    public void setMaxMemoryRows(int n) {
        this.maxMemoryRows = n;
    }

    public void setLockMode(int n) {
        switch (n) {
            case 0: 
            case 3: {
                break;
            }
            case 1: 
            case 2: {
                n = 3;
                break;
            }
            default: {
                throw DbException.getInvalidValueException("lock mode", n);
            }
        }
        this.lockMode = n;
    }

    public int getLockMode() {
        return this.lockMode;
    }

    public void setCloseDelay(int n) {
        this.closeDelay = n;
    }

    public SessionLocal getSystemSession() {
        return this.systemSession;
    }

    public boolean isClosing() {
        return this.closing;
    }

    public void setMaxLengthInplaceLob(int n) {
        this.maxLengthInplaceLob = n;
    }

    @Override
    public int getMaxLengthInplaceLob() {
        return this.maxLengthInplaceLob;
    }

    public void setIgnoreCase(boolean bl) {
        this.ignoreCase = bl;
    }

    public boolean getIgnoreCase() {
        if (this.starting) {
            return false;
        }
        return this.ignoreCase;
    }

    public void setIgnoreCatalogs(boolean bl) {
        this.ignoreCatalogs = bl;
    }

    public boolean getIgnoreCatalogs() {
        return this.ignoreCatalogs;
    }

    public synchronized void setDeleteFilesOnDisconnect(boolean bl) {
        this.deleteFilesOnDisconnect = bl;
    }

    public void setAllowLiterals(int n) {
        this.allowLiterals = n;
    }

    public boolean getOptimizeReuseResults() {
        return this.optimizeReuseResults;
    }

    public void setOptimizeReuseResults(boolean bl) {
        this.optimizeReuseResults = bl;
    }

    @Override
    public Object getLobSyncObject() {
        return this.lobSyncObject;
    }

    public int getSessionCount() {
        return this.userSessions.size();
    }

    public void setReferentialIntegrity(boolean bl) {
        this.referentialIntegrity = bl;
    }

    public boolean getReferentialIntegrity() {
        return this.referentialIntegrity;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setQueryStatistics(boolean bl) {
        this.queryStatistics = bl;
        Database database = this;
        synchronized (database) {
            if (!bl) {
                this.queryStatisticsData = null;
            }
        }
    }

    public boolean getQueryStatistics() {
        return this.queryStatistics;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setQueryStatisticsMaxEntries(int n) {
        this.queryStatisticsMaxEntries = n;
        if (this.queryStatisticsData != null) {
            Database database = this;
            synchronized (database) {
                if (this.queryStatisticsData != null) {
                    this.queryStatisticsData.setMaxQueryEntries(this.queryStatisticsMaxEntries);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public QueryStatisticsData getQueryStatisticsData() {
        if (!this.queryStatistics) {
            return null;
        }
        if (this.queryStatisticsData == null) {
            Database database = this;
            synchronized (database) {
                if (this.queryStatisticsData == null) {
                    this.queryStatisticsData = new QueryStatisticsData(this.queryStatisticsMaxEntries);
                }
            }
        }
        return this.queryStatisticsData;
    }

    public boolean isStarting() {
        return this.starting;
    }

    void opened() {
        if (this.eventListener != null) {
            this.eventListener.opened();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        this.getNextRemoteSettingsId();
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    public void setDefaultNullOrdering(DefaultNullOrdering defaultNullOrdering) {
        this.defaultNullOrdering = defaultNullOrdering;
    }

    public DefaultNullOrdering getDefaultNullOrdering() {
        return this.defaultNullOrdering;
    }

    public void setMaxOperationMemory(int n) {
        this.maxOperationMemory = n;
    }

    public int getMaxOperationMemory() {
        return this.maxOperationMemory;
    }

    public SessionLocal getExclusiveSession() {
        return this.exclusiveSession.get();
    }

    public boolean setExclusiveSession(SessionLocal sessionLocal, boolean bl) {
        if (this.exclusiveSession.get() != sessionLocal && !this.exclusiveSession.compareAndSet(null, sessionLocal)) {
            return false;
        }
        if (bl) {
            this.closeAllSessionsExcept(sessionLocal);
        }
        return true;
    }

    public boolean unsetExclusiveSession(SessionLocal sessionLocal) {
        return this.exclusiveSession.get() == null || this.exclusiveSession.compareAndSet(sessionLocal, null);
    }

    @Override
    public SmallLRUCache<String, String[]> getLobFileListCache() {
        if (this.lobFileListCache == null) {
            this.lobFileListCache = SmallLRUCache.newInstance(128);
        }
        return this.lobFileListCache;
    }

    public boolean isSysTableLocked() {
        return this.meta == null || this.meta.isLockedExclusively();
    }

    public boolean isSysTableLockedBy(SessionLocal sessionLocal) {
        return this.meta == null || this.meta.isLockedExclusivelyBy(sessionLocal);
    }

    public TableLinkConnection getLinkConnection(String string, String string2, String string3, String string4) {
        if (this.linkConnections == null) {
            this.linkConnections = new HashMap();
        }
        return TableLinkConnection.open(this.linkConnections, string, string2, string3, string4, this.dbSettings.shareLinkedConnections);
    }

    public String toString() {
        return this.databaseShortName + ":" + super.toString();
    }

    public void shutdownImmediately() {
        this.closing = true;
        this.setPowerOffCount(1);
        try {
            this.checkPowerOff();
        }
        catch (DbException dbException) {
            // empty catch block
        }
        this.closeFiles();
        this.powerOffCount = 0;
    }

    @Override
    public TempFileDeleter getTempFileDeleter() {
        return this.tempFileDeleter;
    }

    public Table getFirstUserTable() {
        for (Schema schema : this.schemas.values()) {
            for (Table table : schema.getAllTablesAndViews(null)) {
                if (table.getCreateSQL() == null || table.isHidden() || schema.getId() == -1 && table.getName().equalsIgnoreCase("LOB_BLOCKS")) continue;
                return table;
            }
        }
        return null;
    }

    public void checkpoint() {
        if (this.persistent) {
            this.store.flush();
        }
        this.getTempFileDeleter().deleteUnused();
    }

    public void setReadOnly(boolean bl) {
        this.readOnly = bl;
    }

    public void setCompactMode(int n) {
        this.compactMode = n;
    }

    public SourceCompiler getCompiler() {
        if (this.compiler == null) {
            this.compiler = new SourceCompiler();
        }
        return this.compiler;
    }

    @Override
    public LobStorageInterface getLobStorage() {
        return this.lobStorage;
    }

    public SessionLocal getLobSession() {
        return this.lobSession;
    }

    public int getDefaultTableType() {
        return this.defaultTableType;
    }

    public void setDefaultTableType(int n) {
        this.defaultTableType = n;
    }

    public DbSettings getSettings() {
        return this.dbSettings;
    }

    public <V> HashMap<String, V> newStringMap() {
        return this.dbSettings.caseInsensitiveIdentifiers ? new CaseInsensitiveMap() : new HashMap();
    }

    public <V> HashMap<String, V> newStringMap(int n) {
        return this.dbSettings.caseInsensitiveIdentifiers ? new CaseInsensitiveMap(n) : new HashMap(n);
    }

    public <V> ConcurrentHashMap<String, V> newConcurrentStringMap() {
        return this.dbSettings.caseInsensitiveIdentifiers ? new CaseInsensitiveConcurrentMap() : new ConcurrentHashMap();
    }

    public boolean equalsIdentifiers(String string, String string2) {
        return string.equals(string2) || this.dbSettings.caseInsensitiveIdentifiers && string.equalsIgnoreCase(string2);
    }

    public String sysIdentifier(String string) {
        assert (Database.isUpperSysIdentifier(string));
        return this.dbSettings.databaseToLower ? StringUtils.toLowerEnglish(string) : string;
    }

    private static boolean isUpperSysIdentifier(String string) {
        int n = string.length();
        if (n == 0) {
            return false;
        }
        char c = string.charAt(0);
        if (c < 'A' || c > 'Z') {
            return false;
        }
        --n;
        for (int i = 1; i < n; ++i) {
            c = string.charAt(i);
            if (c >= 'A' && c <= 'Z' || c == '_') continue;
            return false;
        }
        return n <= 0 || (c = string.charAt(n)) >= 'A' && c <= 'Z';
    }

    @Override
    public int readLob(long l, byte[] byArray, long l2, byte[] byArray2, int n, int n2) {
        throw DbException.getInternalError();
    }

    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public JavaObjectSerializer getJavaObjectSerializer() {
        this.initJavaObjectSerializer();
        return this.javaObjectSerializer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initJavaObjectSerializer() {
        if (this.javaObjectSerializerInitialized) {
            return;
        }
        Database database = this;
        synchronized (database) {
            if (this.javaObjectSerializerInitialized) {
                return;
            }
            String string = this.javaObjectSerializerName;
            if (string != null && !(string = string.trim()).isEmpty() && !string.equals("null")) {
                try {
                    this.javaObjectSerializer = (JavaObjectSerializer)JdbcUtils.loadUserClass(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (Exception exception) {
                    throw DbException.convert(exception);
                }
            }
            this.javaObjectSerializerInitialized = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setJavaObjectSerializerName(String string) {
        Database database = this;
        synchronized (database) {
            this.javaObjectSerializerInitialized = false;
            this.javaObjectSerializerName = string;
            this.getNextRemoteSettingsId();
        }
    }

    public TableEngine getTableEngine(String string) {
        assert (Thread.holdsLock(this));
        TableEngine tableEngine = this.tableEngines.get(string);
        if (tableEngine == null) {
            try {
                tableEngine = (TableEngine)JdbcUtils.loadUserClass(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                throw DbException.convert(exception);
            }
            this.tableEngines.put(string, tableEngine);
        }
        return tableEngine;
    }

    public Authenticator getAuthenticator() {
        return this.authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        if (authenticator != null) {
            authenticator.init(this);
        }
        this.authenticator = authenticator;
    }

    @Override
    public ValueTimestampTimeZone currentTimestamp() {
        Session session = SessionLocal.getThreadLocalSession();
        if (session != null) {
            return session.currentTimestamp();
        }
        throw DbException.getUnsupportedException("Unsafe comparison or cast");
    }

    @Override
    public TimeZoneProvider currentTimeZone() {
        Session session = SessionLocal.getThreadLocalSession();
        if (session != null) {
            return session.currentTimeZone();
        }
        throw DbException.getUnsupportedException("Unsafe comparison or cast");
    }

    @Override
    public boolean zeroBasedEnums() {
        return this.dbSettings.zeroBasedEnums;
    }

    static {
        EMPTY_SESSION_ARRAY = new SessionLocal[0];
        boolean bl = false;
        if (!$assertionsDisabled) {
            bl = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        ASSERT = bl;
        if (bl) {
            META_LOCK_DEBUGGING = new ThreadLocal();
            META_LOCK_DEBUGGING_DB = new ThreadLocal();
            META_LOCK_DEBUGGING_STACK = new ThreadLocal();
        } else {
            META_LOCK_DEBUGGING = null;
            META_LOCK_DEBUGGING_DB = null;
            META_LOCK_DEBUGGING_STACK = null;
        }
    }
}

