/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection;

import com.google.inject.AbstractModule;
import com.incoresoft.audit.AuditEventsFactory;
import com.incoresoft.audit.BasicAuditor;
import com.incoresoft.audit.BatchedAuditor;
import com.incoresoft.backup.database.BackupDatabaseManager;
import com.incoresoft.backup.database.availability.BackupDatabaseAvailabilityManager;
import com.incoresoft.backup.database.data.BackupDataPopulationManager;
import com.incoresoft.backup.database.data.DatabaseDataDeletionManager;
import com.incoresoft.backup.database.data.JooqDataPopulationManager;
import com.incoresoft.backup.database.data.JooqDatabaseDataDeletionManager;
import com.incoresoft.backup.database.data.JooqSchemaManager;
import com.incoresoft.backup.database.data.JooqSelfReferencingTableInsertionManager;
import com.incoresoft.backup.database.data.JooqSimpleTableInsertionManager;
import com.incoresoft.backup.database.data.JooqTableOrderManager;
import com.incoresoft.backup.database.schema.BackupSchemaCreationManager;
import com.incoresoft.backup.database.schema.BackupSchemaValidationManager;
import com.incoresoft.backup.database.schema.JooqBackupSchemaValidationManager;
import com.incoresoft.backup.database.schema.LiquibaseBackupSchemaCreationManager;
import com.incoresoft.backup.database.schema.LiquibaseChangelogProcessingManager;
import com.incoresoft.cleaner.CleanerTasksExecutor;
import com.incoresoft.cleaner.CleanerTasksRepositoryImpl;
import com.incoresoft.cleaner.StorageDirectoryEnumerator;
import com.incoresoft.clients.ClientsManager;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.core.ProjectPropertiesReader;
import com.incoresoft.database.CleaningSettingsRepository;
import com.incoresoft.files.ZipFileManager;
import com.incoresoft.middleware.analytics.models.alpr.countrypatterns.CountriesManager;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.cleaner.Cleaner;
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.notifications.sounds.SoundsManager;
import com.incoresoft.settings.SettingsManager;
import com.incoresoft.system.IpAddressesManager;
import com.incoresoft.system.compute.ComputeResourcesManager;
import com.incoresoft.system.disk.DiskSpaceManager;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.tempfilescleaner.TemporaryFilesCleaner;

public class ManagersModule
extends AbstractModule {
    private final Class<? extends CleanerTasksRepositoryImpl> cleanerImplClass;

    public ManagersModule() {
        this(CleanerTasksRepositoryImpl.class);
    }

    public ManagersModule(Class<? extends CleanerTasksRepositoryImpl> cleanerImplClass) {
        this.cleanerImplClass = cleanerImplClass;
    }

    @Override
    protected void configure() {
        this.bind(SettingsManager.class).asEagerSingleton();
        this.bind(GlobalSettings.class).to(SettingsManager.class);
        this.bind(InterferingProcessesRegistry.class).asEagerSingleton();
        this.bind(ClientsManager.class).asEagerSingleton();
        this.bind(CountriesManager.class).asEagerSingleton();
        this.bind(IpAddressesManager.class).asEagerSingleton();
        this.bind(StorageDirectoryEnumerator.class).asEagerSingleton();
        this.bind(CleaningSettingsRepository.class).asEagerSingleton();
        this.bind(Cleaner.class).to(this.cleanerImplClass);
        if (!this.cleanerImplClass.equals(CleanerTasksRepositoryImpl.class)) {
            this.bind(CleanerTasksRepositoryImpl.class).to(this.cleanerImplClass);
        }
        this.bind(this.cleanerImplClass).asEagerSingleton();
        this.bind(CleanerTasksExecutor.class).asEagerSingleton();
        this.bind(AuditEventsFactory.class).asEagerSingleton();
        this.bind(BasicAuditor.class).asEagerSingleton();
        this.bind(BatchedAuditor.class).asEagerSingleton();
        this.bind(Auditor.class).to(BatchedAuditor.class);
        this.bind(SoundsManager.class).asEagerSingleton();
        this.bind(DiskSpaceManager.class).asEagerSingleton();
        this.bind(ComputeResourcesManager.class).asEagerSingleton();
        this.bind(TemporaryFilesCleaner.class).asEagerSingleton();
        this.bind(ProjectPropertiesReader.class).asEagerSingleton();
        this.bind(ApplicationReloadManager.class).asEagerSingleton();
        this.bind(ZipFileManager.class).asEagerSingleton();
        this.bind(BackupDatabaseManager.class).asEagerSingleton();
        this.bind(LiquibaseChangelogProcessingManager.class).asEagerSingleton();
        this.bind(BackupSchemaCreationManager.class).to(LiquibaseBackupSchemaCreationManager.class).asEagerSingleton();
        this.bind(BackupDataPopulationManager.class).to(JooqDataPopulationManager.class).asEagerSingleton();
        this.bind(DatabaseDataDeletionManager.class).to(JooqDatabaseDataDeletionManager.class).asEagerSingleton();
        this.bind(BackupSchemaValidationManager.class).to(JooqBackupSchemaValidationManager.class).asEagerSingleton();
        this.bind(JooqSelfReferencingTableInsertionManager.class).asEagerSingleton();
        this.bind(JooqSimpleTableInsertionManager.class).asEagerSingleton();
        this.bind(JooqTableOrderManager.class).asEagerSingleton();
        this.bind(JooqSchemaManager.class).asEagerSingleton();
        this.bind(BackupDatabaseAvailabilityManager.class).asEagerSingleton();
    }
}

