/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database;

import com.google.inject.Inject;
import com.incoresoft.backup.database.availability.BackupDatabaseAvailabilityManager;
import com.incoresoft.backup.database.data.BackupDataPopulationManager;
import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.backup.database.data.DatabaseDataDeletionManager;
import com.incoresoft.backup.database.listeners.BackupDatabaseCreationListener;
import com.incoresoft.backup.database.listeners.BackupDatabaseRestoreListener;
import com.incoresoft.backup.database.schema.BackupSchemaCreationManager;
import com.incoresoft.backup.database.schema.BackupSchemaValidationManager;
import com.incoresoft.generated.Tables;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import com.incoresoft.utils.ConcurrencyUtils;
import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupDatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(BackupDatabaseManager.class);
    private final BackupSchemaCreationManager schemaCreationManager;
    private final BackupDataPopulationManager dataPopulationManager;
    private final DatabaseDataDeletionManager databaseDataDeletionManager;
    private final BackupDatabaseAvailabilityManager databaseAvailabilityManager;
    private final BackupSchemaValidationManager schemaValidationManager;
    public static final String BACKUP_DB_NAME = "backup_database";
    public static final String BACKUP_DB_SCHEMA_NAME = "vms";
    private static final String BACKUP_DB_USER = "sa";
    private static final String BACKUP_DB_PASSWORD = "";
    public static final String LIQUIBASE_CHANGELOG_TABLE_NAME = "databasechangelog";
    public static final String LIQUIBASE_CHANGELOG_LOCK_TABLE_NAME = "databasechangeloglock";
    private static final String BACKUPS_TABLE_NAME = "backups";
    private static final String JETTY_SESSIONS_TABLE_NAME = "jettysessions";
    private static final Set<String> ALWAYS_IGNORED_TABLES_FOR_CREATION = Set.of("databasechangelog", "databasechangeloglock", Tables.PLUGIN_CONFIGURATIONS.getName(), "backups", "jettysessions");
    public static final Set<String> ALWAYS_IGNORED_TABLES_FOR_RESTORE = Set.of(Tables.PLUGIN_CONFIGURATIONS.getName(), "backups", "jettysessions");

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createBackupDatabase(Path backupDirectory, BackupIgnoredTables ignoredTables, Collection<? extends BackupDatabaseCreationListener> listeners) throws InterruptedException {
        this.schemaValidationManager.validateMainDatabaseStructure();
        ALWAYS_IGNORED_TABLES_FOR_CREATION.forEach(ignoredTables::add);
        this.databaseAvailabilityManager.setUnavailableDuringBackupCreation();
        try {
            DataSource dataSource = this.getBackupDataSource(backupDirectory);
            ListenersUtils.triggerListenersInParallel(BackupDatabaseCreationListener::onBackupDatabaseSchemaCreationStage, listeners);
            ConcurrencyUtils.ensureNotInterrupted();
            this.schemaCreationManager.createBackupSchema(dataSource);
            ListenersUtils.triggerListenersInParallel(BackupDatabaseCreationListener::onBackupDatabasePopulationStage, listeners);
            ConcurrencyUtils.ensureNotInterrupted();
            this.dataPopulationManager.populateBackupDatabase(dataSource, ignoredTables, listeners);
        }
        finally {
            this.databaseAvailabilityManager.setAvailable();
        }
    }

    public void restoreBackupDatabase(Path backupDirectory, BackupIgnoredTables ignoredTables, List<BackupDatabaseRestoreListener> listeners) throws InterruptedException {
        ALWAYS_IGNORED_TABLES_FOR_RESTORE.forEach(ignoredTables::add);
        this.databaseAvailabilityManager.setUnavailableDuringBackupRestore();
        try {
            DataSource dataSource = this.getBackupDataSource(backupDirectory);
            ListenersUtils.triggerListenersInParallel(BackupDatabaseRestoreListener::onDatabaseDataDeletingStage, listeners);
            this.databaseDataDeletionManager.deleteData(new BackupIgnoredTables(ALWAYS_IGNORED_TABLES_FOR_RESTORE));
            ListenersUtils.triggerListenersInParallel(BackupDatabaseRestoreListener::onDatabasePopulationStage, listeners);
            this.dataPopulationManager.populateMainDatabase(dataSource, ignoredTables, listeners);
        }
        catch (InterruptedException | RuntimeException e) {
            this.databaseDataDeletionManager.deleteData(new BackupIgnoredTables(ALWAYS_IGNORED_TABLES_FOR_RESTORE));
            throw e;
        }
        finally {
            this.databaseAvailabilityManager.setAvailable();
        }
    }

    public void deleteData() {
        this.databaseDataDeletionManager.deleteData();
    }

    public void validateBackupSchema(Path backupDirectory) {
        DataSource dataSource = this.getBackupDataSource(backupDirectory);
        this.schemaValidationManager.validateBackupDatabaseStructure(dataSource);
    }

    public File resolveBackupDatabaseFile(Path backupDirectory) {
        return backupDirectory.resolve("backup_database.mv.db").toFile();
    }

    private DataSource getBackupDataSource(Path backupDirectory) {
        JdbcDataSource backupDataSource = new JdbcDataSource();
        backupDataSource.setURL(String.format("jdbc:h2:file:./%s/%s;AUTO_SERVER=TRUE;INIT=CREATE SCHEMA IF NOT EXISTS %s\\;SET SCHEMA %s;", backupDirectory.toString(), BACKUP_DB_NAME, BACKUP_DB_SCHEMA_NAME, BACKUP_DB_SCHEMA_NAME));
        backupDataSource.setUser(BACKUP_DB_USER);
        backupDataSource.setPassword(BACKUP_DB_PASSWORD);
        return backupDataSource;
    }

    @Inject
    public BackupDatabaseManager(BackupSchemaCreationManager schemaCreationManager, BackupDataPopulationManager dataPopulationManager, DatabaseDataDeletionManager databaseDataDeletionManager, BackupDatabaseAvailabilityManager databaseAvailabilityManager, BackupSchemaValidationManager schemaValidationManager) {
        this.schemaCreationManager = schemaCreationManager;
        this.dataPopulationManager = dataPopulationManager;
        this.databaseDataDeletionManager = databaseDataDeletionManager;
        this.databaseAvailabilityManager = databaseAvailabilityManager;
        this.schemaValidationManager = schemaValidationManager;
    }
}

