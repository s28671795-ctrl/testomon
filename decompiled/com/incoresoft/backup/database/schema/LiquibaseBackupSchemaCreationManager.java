/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.schema;

import com.google.inject.Inject;
import com.incoresoft.backup.database.exceptions.BackupSchemaCreationException;
import com.incoresoft.backup.database.schema.BackupSchemaCreationManager;
import com.incoresoft.backup.database.schema.LiquibaseChangelogProcessingManager;
import com.incoresoft.middleware.database.DatabaseConnector;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import javax.sql.DataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class LiquibaseBackupSchemaCreationManager
implements BackupSchemaCreationManager {
    private static final String RAW_CHANGELOG_FILE_NAME = "raw_backup_changelog.xml";
    private static final String PROCESSED_CHANGELOG_FILE_NAME = "processed_backup_changelog.xml";
    private final Path path = Paths.get(System.getProperty("user.dir"), new String[0]);
    private final Path rawChangelogPath = this.path.resolve("raw_backup_changelog.xml");
    private final Path processedChangelogPath = this.path.resolve("processed_backup_changelog.xml");
    private final DataSource mainDataSource;
    private final LiquibaseChangelogProcessingManager changelogProcessingManager;

    @Inject
    public LiquibaseBackupSchemaCreationManager(DatabaseConnector connector, LiquibaseChangelogProcessingManager changelogProcessingManager) {
        this.mainDataSource = connector.getDataSource();
        this.changelogProcessingManager = changelogProcessingManager;
    }

    @Override
    public void createBackupSchema(DataSource backupDataSource) {
        try {
            this.createChangelogFile();
            this.changelogProcessingManager.process(this.rawChangelogPath.toFile(), this.processedChangelogPath.toFile());
            this.applyChangelogFile(backupDataSource);
        }
        catch (Exception e) {
            throw new BackupSchemaCreationException(e);
        }
        finally {
            this.deleteChangelogFiles();
        }
    }

    private void createChangelogFile() {
        try (Connection connection = this.mainDataSource.getConnection();
             DirectoryResourceAccessor resourceAccessor = new DirectoryResourceAccessor(this.path);
             Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));){
            CommandScope commandScope = new CommandScope("generateChangeLog");
            commandScope.addArgumentValue("database", database);
            commandScope.addArgumentValue("changeLogFile", this.rawChangelogPath.toString());
            commandScope.addArgumentValue("resourceAccessor", resourceAccessor);
            commandScope.execute();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void applyChangelogFile(DataSource backupDataSource) {
        try (Connection connection = backupDataSource.getConnection();
             DirectoryResourceAccessor resourceAccessor = new DirectoryResourceAccessor(this.path);
             Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));){
            Liquibase liquibase = new Liquibase(this.processedChangelogPath.getFileName().toString(), (ResourceAccessor)resourceAccessor, database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteChangelogFiles() {
        try {
            Files.deleteIfExists(this.rawChangelogPath);
            Files.deleteIfExists(this.processedChangelogPath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

