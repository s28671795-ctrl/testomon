/*
 * Decompiled with CFR 0.152.
 */
package liquibase.lockservice;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.DB2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;
import liquibase.executor.jvm.ChangelogJdbcMdcListener;
import liquibase.lockservice.DatabaseChangeLogLock;
import liquibase.lockservice.LockService;
import liquibase.logging.mdc.MdcObject;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateDatabaseChangeLogLockTableStatement;
import liquibase.statement.core.DropTableStatement;
import liquibase.statement.core.InitializeDatabaseChangeLogLockTableStatement;
import liquibase.statement.core.LockDatabaseChangeLogStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.statement.core.SelectFromDatabaseChangeLogLockStatement;
import liquibase.statement.core.UnlockDatabaseChangeLogStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;

public class StandardLockService
implements LockService {
    protected static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    protected Database database;
    protected boolean hasChangeLogLock;
    protected Long changeLogLockPollRate;
    protected Long changeLogLockRecheckTime;
    protected Boolean hasDatabaseChangeLogLockTable;
    protected boolean isDatabaseChangeLogLockTableInitialized;
    protected ObjectQuotingStrategy quotingStrategy;
    protected final SecureRandom random = new SecureRandom();

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supports(Database database) {
        return true;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    protected Long getChangeLogLockWaitTime() {
        if (this.changeLogLockPollRate != null) {
            return this.changeLogLockPollRate;
        }
        return GlobalConfiguration.CHANGELOGLOCK_WAIT_TIME.getCurrentValue();
    }

    @Override
    public void setChangeLogLockWaitTime(long changeLogLockWaitTime) {
        this.changeLogLockPollRate = changeLogLockWaitTime;
    }

    protected Long getChangeLogLockRecheckTime() {
        if (this.changeLogLockRecheckTime != null) {
            return this.changeLogLockRecheckTime;
        }
        return GlobalConfiguration.CHANGELOGLOCK_POLL_RATE.getCurrentValue();
    }

    @Override
    public void setChangeLogLockRecheckTime(long changeLogLockRecheckTime) {
        this.changeLogLockRecheckTime = changeLogLockRecheckTime;
    }

    @Override
    public void init() throws DatabaseException {
        boolean createdTable = false;
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.database);
        int maxIterations = 10;
        if (executor2 instanceof LoggingExecutor) {
            maxIterations = this.isDatabaseChangeLogLockTableCreated() ? 0 : 1;
        }
        for (int i = 0; i < maxIterations; ++i) {
            try {
                if (!this.isDatabaseChangeLogLockTableCreated(true)) {
                    executor2.comment("Create Database Lock Table");
                    CreateDatabaseChangeLogLockTableStatement createLockTableStatement = new CreateDatabaseChangeLogLockTableStatement();
                    ChangelogJdbcMdcListener.execute(this.database, ex -> ex.execute(createLockTableStatement));
                    this.database.commit();
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Created database lock table with name: " + this.database.escapeTableName(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName()));
                    this.hasDatabaseChangeLogLockTable = true;
                    createdTable = true;
                    this.hasDatabaseChangeLogLockTable = true;
                }
                if (!this.isDatabaseChangeLogLockTableInitialized(createdTable, true)) {
                    executor2.comment("Initialize Database Lock Table");
                    InitializeDatabaseChangeLogLockTableStatement initializeLockTableStatement = new InitializeDatabaseChangeLogLockTableStatement();
                    ChangelogJdbcMdcListener.execute(this.database, ex -> ex.execute(initializeLockTableStatement));
                    this.database.commit();
                }
                if (executor2 instanceof LoggingExecutor) break;
                this.handleOldChangelogTableFormat(executor2);
                break;
            }
            catch (Exception e) {
                if (i == maxIterations - 1) {
                    throw e;
                }
                Scope.getCurrentScope().getLog(this.getClass()).fine("Failed to create or initialize the lock table, trying again, iteration " + (i + 1) + " of " + maxIterations, e);
                this.database.rollback();
                try {
                    Thread.sleep(this.random.nextInt(1000));
                }
                catch (InterruptedException ex2) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning("Lock table retry loop thread sleep interrupted", ex2);
                    Thread.currentThread().interrupt();
                }
                continue;
            }
        }
    }

    private void handleOldChangelogTableFormat(Executor executor2) throws DatabaseException {
        if (executor2.updatesDatabase() && this.database instanceof DerbyDatabase && ((DerbyDatabase)this.database).supportsBooleanDataType() || DB2Database.class.isAssignableFrom(this.database.getClass()) && ((DB2Database)this.database).supportsBooleanDataType()) {
            String lockTable = this.database.escapeTableName(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName());
            Object obj = executor2.queryForObject(new RawSqlStatement("SELECT MIN(locked) AS test FROM " + lockTable + " FETCH FIRST ROW ONLY"), Object.class);
            if (!(obj instanceof Boolean)) {
                executor2.execute(new DropTableStatement(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName(), false));
                executor2.execute(new CreateDatabaseChangeLogLockTableStatement());
                executor2.execute(new InitializeDatabaseChangeLogLockTableStatement());
            }
        }
    }

    public boolean isDatabaseChangeLogLockTableInitialized(boolean tableJustCreated) {
        return this.isDatabaseChangeLogLockTableInitialized(tableJustCreated, false);
    }

    protected boolean isDatabaseChangeLogLockTableInitialized(boolean tableJustCreated, boolean forceRecheck) {
        if (!this.isDatabaseChangeLogLockTableInitialized || forceRecheck) {
            Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.database);
            try {
                RawSqlStatement lockTableInitializedStatement = new RawSqlStatement("SELECT COUNT(*) FROM " + this.database.escapeTableName(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName(), this.database.getDatabaseChangeLogLockTableName()));
                this.isDatabaseChangeLogLockTableInitialized = ChangelogJdbcMdcListener.query(this.database, ex -> ex.queryForInt(lockTableInitializedStatement)) > 0;
            }
            catch (LiquibaseException e) {
                if (executor2.updatesDatabase()) {
                    throw new UnexpectedLiquibaseException(e);
                }
                this.isDatabaseChangeLogLockTableInitialized = !tableJustCreated;
            }
        }
        return this.isDatabaseChangeLogLockTableInitialized;
    }

    @Override
    public boolean hasChangeLogLock() {
        return this.hasChangeLogLock;
    }

    protected boolean isDatabaseChangeLogLockTableCreated(boolean forceRecheck) {
        if (forceRecheck || this.hasDatabaseChangeLogLockTable == null) {
            try {
                this.hasDatabaseChangeLogLockTable = SnapshotGeneratorFactory.getInstance().hasDatabaseChangeLogLockTable(this.database);
            }
            catch (LiquibaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return this.hasDatabaseChangeLogLockTable;
    }

    protected boolean isDatabaseChangeLogLockTableCreated() throws DatabaseException {
        return this.isDatabaseChangeLogLockTableCreated(false);
    }

    @Override
    public void waitForLock() throws LockException {
        boolean locked = false;
        long timeToGiveUp = new Date().getTime() + this.getChangeLogLockWaitTime() * 1000L * 60L;
        locked = this.acquireLock();
        do {
            if (locked) continue;
            Scope.getCurrentScope().getLog(this.getClass()).info("Waiting for changelog lock....");
            try {
                Thread.sleep(this.getChangeLogLockRecheckTime() * 1000L);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (!(locked = this.acquireLock()) && new Date().getTime() < timeToGiveUp);
        if (!locked) {
            String lockedBy;
            DatabaseChangeLogLock[] locks = this.listLocks();
            if (locks.length > 0) {
                DatabaseChangeLogLock lock = locks[0];
                lockedBy = lock.getLockedBy() + " since " + DateFormat.getDateTimeInstance(3, 3).format(lock.getLockGranted());
            } else {
                lockedBy = "UNKNOWN";
            }
            throw new LockException("Could not acquire change log lock.  Currently locked by " + lockedBy);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean acquireLock() throws LockException {
        if (this.hasChangeLogLock) {
            return true;
        }
        this.quotingStrategy = this.database.getObjectQuotingStrategy();
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.database);
        try {
            this.database.rollback();
            this.init();
            SelectFromDatabaseChangeLogLockStatement lockedStatement = new SelectFromDatabaseChangeLogLockStatement("LOCKED");
            Boolean locked = ChangelogJdbcMdcListener.query(this.database, ex -> ex.queryForObject(lockedStatement, Boolean.class));
            if (locked.booleanValue()) {
                boolean bl = false;
                return bl;
            }
            executor2.comment("Lock Database");
            LockDatabaseChangeLogStatement lockDatabaseStatement = new LockDatabaseChangeLogStatement();
            int rowsUpdated = ChangelogJdbcMdcListener.query(this.database, ex -> ex.update(lockDatabaseStatement));
            if (rowsUpdated == -1 && this.database instanceof MSSQLDatabase) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Database did not return a proper row count (Might have NOCOUNT enabled)");
                this.database.rollback();
                Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(new LockDatabaseChangeLogStatement(), this.database);
                if (sql.length != 1) {
                    throw new UnexpectedLiquibaseException("Did not expect " + sql.length + " statements");
                }
                RawSqlStatement noCountStatement = new RawSqlStatement("EXEC sp_executesql N'SET NOCOUNT OFF " + sql[0].toSql().replace("'", "''") + "'");
                rowsUpdated = ChangelogJdbcMdcListener.query(this.database, ex -> ex.update(noCountStatement));
            }
            if (rowsUpdated > 1) {
                throw new LockException("Did not update change log lock correctly");
            }
            if (rowsUpdated == 0) {
                boolean bl = false;
                return bl;
            }
            this.database.commit();
            Scope.getCurrentScope().getLog(this.getClass()).info(coreBundle.getString("successfully.acquired.change.log.lock"));
            this.hasChangeLogLock = true;
            Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).resetAll();
            this.database.setCanCacheLiquibaseTableInfo(true);
            boolean bl = true;
            return bl;
        }
        catch (Exception e) {
            throw new LockException(e);
        }
        finally {
            try {
                this.database.rollback();
            }
            catch (DatabaseException databaseException) {}
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void releaseLock() throws LockException {
        ObjectQuotingStrategy incomingQuotingStrategy = null;
        if (this.quotingStrategy != null) {
            incomingQuotingStrategy = this.database.getObjectQuotingStrategy();
            this.database.setObjectQuotingStrategy(this.quotingStrategy);
        }
        boolean success = false;
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.database);
        try {
            if (this.isDatabaseChangeLogLockTableCreated()) {
                executor2.comment("Release Database Lock");
                this.database.rollback();
                UnlockDatabaseChangeLogStatement unlockStatement = new UnlockDatabaseChangeLogStatement();
                int updatedRows = ChangelogJdbcMdcListener.query(this.database, ex -> ex.update(unlockStatement));
                if (updatedRows == 0 && this.database instanceof MySQLDatabase) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Database did not return a proper row count (Might have useAffectedRows enabled.)");
                    if (((MySQLDatabase)this.database).getUseAffectedRows()) {
                        updatedRows = 1;
                    }
                }
                if (updatedRows == -1 && this.database instanceof MSSQLDatabase) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Database did not return a proper row count (Might have NOCOUNT enabled.)");
                    this.database.rollback();
                    Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(new UnlockDatabaseChangeLogStatement(), this.database);
                    if (sql.length != 1) {
                        throw new UnexpectedLiquibaseException("Did not expect " + sql.length + " statements");
                    }
                    RawSqlStatement noCountStatement = new RawSqlStatement("EXEC sp_executesql N'SET NOCOUNT OFF " + sql[0].toSql().replace("'", "''") + "'");
                    updatedRows = ChangelogJdbcMdcListener.query(this.database, ex -> ex.update(noCountStatement));
                }
                if (updatedRows != 1) {
                    RawSqlStatement countStatement = new RawSqlStatement("SELECT COUNT(*) FROM " + this.database.getDatabaseChangeLogLockTableName());
                    throw new LockException("Did not update change log lock correctly.\n\n" + updatedRows + " rows were updated instead of the expected 1 row using executor " + executor2.getClass().getName() + " there are " + ChangelogJdbcMdcListener.query(this.database, ex -> ex.queryForList(countStatement)) + " rows in the table");
                }
                this.database.commit();
                success = true;
            }
        }
        catch (Exception e) {
            try {
                throw new LockException(e);
            }
            catch (Throwable throwable) {
                try {
                    this.hasChangeLogLock = false;
                    this.database.setCanCacheLiquibaseTableInfo(false);
                    try (MdcObject releaseLocksOutcome = Scope.getCurrentScope().addMdcValue("releaseLocksOutcome", success ? "success" : "fail");){
                        Scope.getCurrentScope().getLog(this.getClass()).log(success ? Level.INFO : Level.WARNING, (success ? "Successfully released" : "Failed to release") + " change log lock", null);
                    }
                    this.database.rollback();
                }
                catch (DatabaseException databaseException) {
                    // empty catch block
                }
                if (incomingQuotingStrategy == null) throw throwable;
                this.database.setObjectQuotingStrategy(incomingQuotingStrategy);
                throw throwable;
            }
        }
        try {
            this.hasChangeLogLock = false;
            this.database.setCanCacheLiquibaseTableInfo(false);
            try (MdcObject releaseLocksOutcome = Scope.getCurrentScope().addMdcValue("releaseLocksOutcome", success ? "success" : "fail");){
                Scope.getCurrentScope().getLog(this.getClass()).log(success ? Level.INFO : Level.WARNING, (success ? "Successfully released" : "Failed to release") + " change log lock", null);
            }
            this.database.rollback();
        }
        catch (DatabaseException releaseLocksOutcome) {
            // empty catch block
        }
        if (incomingQuotingStrategy == null) return;
        this.database.setObjectQuotingStrategy(incomingQuotingStrategy);
    }

    @Override
    public DatabaseChangeLogLock[] listLocks() throws LockException {
        try {
            if (!this.isDatabaseChangeLogLockTableCreated()) {
                return new DatabaseChangeLogLock[0];
            }
            ArrayList<DatabaseChangeLogLock> allLocks = new ArrayList<DatabaseChangeLogLock>();
            SelectFromDatabaseChangeLogLockStatement sqlStatement = new SelectFromDatabaseChangeLogLockStatement("ID", "LOCKED", "LOCKGRANTED", "LOCKEDBY");
            List rows = ChangelogJdbcMdcListener.query(this.database, executor2 -> executor2.queryForList(sqlStatement));
            for (Map columnMap : rows) {
                Object lockedValue = columnMap.get("LOCKED");
                Boolean locked = lockedValue instanceof Number ? Boolean.valueOf(((Number)lockedValue).intValue() == 1) : (Boolean)lockedValue;
                if (locked == null || !locked.booleanValue()) continue;
                Object lockGranted = columnMap.get("LOCKGRANTED");
                Date castedLockGranted = lockGranted instanceof LocalDateTime ? Date.from(((LocalDateTime)lockGranted).atZone(ZoneId.systemDefault()).toInstant()) : (Date)lockGranted;
                allLocks.add(new DatabaseChangeLogLock(((Number)columnMap.get("ID")).intValue(), castedLockGranted, (String)columnMap.get("LOCKEDBY")));
            }
            return allLocks.toArray(new DatabaseChangeLogLock[0]);
        }
        catch (Exception e) {
            throw new LockException(e);
        }
    }

    @Override
    public void forceReleaseLock() throws LockException, DatabaseException {
        this.init();
        this.releaseLock();
    }

    @Override
    public void reset() {
        this.hasChangeLogLock = false;
        this.hasDatabaseChangeLogLockTable = null;
        this.isDatabaseChangeLogLockTableInitialized = false;
        if (this.database != null) {
            ChangeLogHistoryService changelogService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this.database);
            changelogService.reset();
        }
    }

    @Override
    public void destroy() throws DatabaseException {
        try {
            Relation example = new Table().setName(this.database.getDatabaseChangeLogLockTableName()).setSchema(this.database.getLiquibaseCatalogName(), this.database.getLiquibaseSchemaName());
            if (SnapshotGeneratorFactory.getInstance().has(example, this.database)) {
                Relation table = SnapshotGeneratorFactory.getInstance().createSnapshot(example, this.database);
                DiffOutputControl diffOutputControl = new DiffOutputControl(true, true, false, null);
                Change[] change = ChangeGeneratorFactory.getInstance().fixUnexpected(table, diffOutputControl, this.database, this.database);
                SqlStatement[] sqlStatement = change[0].generateStatements(this.database);
                ChangelogJdbcMdcListener.execute(this.database, executor2 -> executor2.execute(sqlStatement[0]));
            }
            this.reset();
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

