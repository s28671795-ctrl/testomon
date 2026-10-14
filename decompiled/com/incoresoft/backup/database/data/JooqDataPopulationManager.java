/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.google.inject.Inject;
import com.incoresoft.backup.database.BackupDatabaseUtils;
import com.incoresoft.backup.database.data.BackupDataPopulationManager;
import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.backup.database.data.DataPopulationDirection;
import com.incoresoft.backup.database.data.JooqSchemaManager;
import com.incoresoft.backup.database.data.JooqSelfReferencingTableInsertionManager;
import com.incoresoft.backup.database.data.JooqSimpleTableInsertionManager;
import com.incoresoft.backup.database.data.JooqTableInserter;
import com.incoresoft.backup.database.data.JooqTableOrderManager;
import com.incoresoft.backup.database.data.PostgresJooqTableInserter;
import com.incoresoft.backup.database.data.SQLServerJooqTableInserter;
import com.incoresoft.backup.database.exceptions.BackupDataPopulationException;
import com.incoresoft.backup.database.listeners.DatabaseTablesPopulationListener;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.plugin.commons.utils.ListenersUtils;
import com.incoresoft.utils.ConcurrencyUtils;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JooqDataPopulationManager
implements BackupDataPopulationManager {
    private static final Logger log = LoggerFactory.getLogger(JooqDataPopulationManager.class);
    public static final int BATCH_SIZE = 1000;
    private final DatabaseConnector databaseConnector;
    private final SQLDialect dialect;
    private final JooqTableOrderManager tableOrderManager;
    private final JooqSchemaManager jooqSchemaManager;
    private final JooqSelfReferencingTableInsertionManager selfReferencingTableInsertionManager;
    private final JooqSimpleTableInsertionManager simpleTableInsertionManager;

    @Override
    public void populateBackupDatabase(DataSource backupDataSource, BackupIgnoredTables ignoredTables, Collection<? extends DatabaseTablesPopulationListener> listeners) throws InterruptedException {
        DataSource mainDataSource = this.databaseConnector.getDataSource();
        try (Connection mainConnection = mainDataSource.getConnection();
             Connection backupConnection = backupDataSource.getConnection();){
            DSLContext mainContext = DSL.using(mainConnection, this.dialect);
            DSLContext backupContext = DSL.using(backupConnection, SQLDialect.H2);
            this.populate(mainContext, backupContext, DataPopulationDirection.FROM_MAIN_TO_BACKUP, ignoredTables, listeners);
        }
        catch (InterruptedException e) {
            throw e;
        }
        catch (Exception e) {
            throw new BackupDataPopulationException(e);
        }
    }

    @Override
    public void populateMainDatabase(DataSource backupDataSource, BackupIgnoredTables ignoredTables, Collection<? extends DatabaseTablesPopulationListener> listeners) throws InterruptedException {
        DataSource mainDataSource = this.databaseConnector.getDataSource();
        try (Connection mainConnection = mainDataSource.getConnection();
             Connection backupConnection = backupDataSource.getConnection();){
            DSLContext mainContext = DSL.using(mainConnection, this.dialect);
            DSLContext backupContext = DSL.using(backupConnection, SQLDialect.H2);
            this.populate(mainContext, backupContext, DataPopulationDirection.FROM_BACKUP_TO_MAIN, ignoredTables, listeners);
        }
        catch (InterruptedException e) {
            throw e;
        }
        catch (Exception e) {
            throw new BackupDataPopulationException(e);
        }
    }

    private void populate(DSLContext mainContext, DSLContext backupContext, DataPopulationDirection direction, BackupIgnoredTables ignoredTables, Collection<? extends DatabaseTablesPopulationListener> listeners) throws InterruptedException {
        ConcurrencyUtils.ensureNotInterrupted();
        Schema mainSchema = this.jooqSchemaManager.getMainSchema(mainContext);
        Schema backupSchema = this.jooqSchemaManager.getBackupSchema(backupContext);
        List<Table<?>> tables = this.getTablesForDataPopulation(mainSchema, backupSchema, direction, ignoredTables);
        ConcurrencyUtils.ensureNotInterrupted();
        for (Table<?> mainTable : tables) {
            this.populateTable(mainContext, backupContext, direction, mainTable, mainSchema, backupSchema, listeners);
        }
    }

    private List<Table<?>> getTablesForDataPopulation(Schema mainSchema, Schema backupSchema, DataPopulationDirection direction, BackupIgnoredTables ignoredTables) {
        List<Table<?>> mainSchemaTables = this.tableOrderManager.getTablesOrderedForInserting(mainSchema, ignoredTables);
        return switch (direction) {
            default -> throw new MatchException(null, null);
            case DataPopulationDirection.FROM_MAIN_TO_BACKUP -> mainSchemaTables;
            case DataPopulationDirection.FROM_BACKUP_TO_MAIN -> {
                Set backupTables = backupSchema.getTables().stream().map(it -> BackupDatabaseUtils.formatName(it.getName())).collect(Collectors.toSet());
                yield mainSchemaTables.stream().filter(it -> backupTables.contains(BackupDatabaseUtils.formatName(it.getName()))).collect(Collectors.toList());
            }
        };
    }

    private void populateTable(DSLContext mainContext, DSLContext backupContext, DataPopulationDirection direction, Table<?> mainTable, Schema mainSchema, Schema backupSchema, Collection<? extends DatabaseTablesPopulationListener> listeners) throws InterruptedException {
        Table<?> backupTable = backupSchema.getTable(BackupDatabaseUtils.formatName(mainTable.getName()));
        List<Field<?>> mainFields = List.of(mainTable.fields());
        List<Field> backupFields = mainFields.stream().map(field2 -> DSL.field(DSL.name(BackupDatabaseUtils.formatName(field2.getName())), field2.getDataType())).toList();
        Optional<ForeignKey<?, ?>> selfReferencingForeignKeyOptional = this.findSelfReferencingForeignKey(mainTable, backupTable, mainSchema, backupSchema, direction);
        JooqTableInserter tableInserter = this.createTableInserter(mainContext, backupContext, mainTable, backupTable, mainFields, backupFields, mainSchema, direction);
        if (selfReferencingForeignKeyOptional.isPresent()) {
            switch (direction) {
                case FROM_MAIN_TO_BACKUP: {
                    this.selfReferencingTableInsertionManager.insert(mainContext, mainTable, mainFields, selfReferencingForeignKeyOptional.get(), tableInserter);
                    break;
                }
                case FROM_BACKUP_TO_MAIN: {
                    this.selfReferencingTableInsertionManager.insert(backupContext, backupTable, backupFields, selfReferencingForeignKeyOptional.get(), tableInserter);
                }
            }
        } else {
            switch (direction) {
                case FROM_MAIN_TO_BACKUP: {
                    this.simpleTableInsertionManager.insert(mainContext, mainTable, mainFields, tableInserter);
                    break;
                }
                case FROM_BACKUP_TO_MAIN: {
                    this.simpleTableInsertionManager.insert(backupContext, backupTable, backupFields, tableInserter);
                }
            }
        }
        tableInserter.close();
        ListenersUtils.triggerListenersInParallel(listener -> listener.onTablePopulated(mainTable.getName()), listeners);
    }

    private Optional<ForeignKey<?, ?>> findSelfReferencingForeignKey(Table<?> mainTable, Table<?> backupTable, Schema mainSchema, Schema backupSchema, DataPopulationDirection direction) {
        List<ForeignKey> foreignKeys = switch (direction) {
            default -> throw new MatchException(null, null);
            case DataPopulationDirection.FROM_MAIN_TO_BACKUP -> mainSchema.getForeignKeys().stream().filter(fk -> fk.getTable().getName().equals(mainTable.getName())).toList();
            case DataPopulationDirection.FROM_BACKUP_TO_MAIN -> backupSchema.getForeignKeys().stream().filter(fk -> fk.getTable().getName().equals(backupTable.getName())).toList();
        };
        return foreignKeys.stream().filter(fk -> fk.getTable().equals(fk.getKey().getTable())).findFirst();
    }

    private JooqTableInserter createTableInserter(DSLContext mainContext, DSLContext backupContext, Table<?> mainTable, Table<?> backupTable, List<? extends Field<?>> mainFields, List<? extends Field<?>> backupFields, Schema mainSchema, DataPopulationDirection direction) {
        if (direction == DataPopulationDirection.FROM_MAIN_TO_BACKUP) {
            return new JooqTableInserter(backupContext, backupTable, backupFields, 1000);
        }
        if (mainContext.dialect().equals((Object)SQLDialect.SQLSERVER)) {
            return new SQLServerJooqTableInserter(mainContext, mainTable, mainFields, 1000);
        }
        if (mainContext.dialect().equals((Object)SQLDialect.POSTGRES)) {
            return new PostgresJooqTableInserter(mainContext, mainTable, mainFields, 1000, mainSchema);
        }
        return new JooqTableInserter(mainContext, mainTable, mainFields, 1000);
    }

    @Inject
    public JooqDataPopulationManager(DatabaseConnector databaseConnector, SQLDialect dialect, JooqTableOrderManager tableOrderManager, JooqSchemaManager jooqSchemaManager, JooqSelfReferencingTableInsertionManager selfReferencingTableInsertionManager, JooqSimpleTableInsertionManager simpleTableInsertionManager) {
        this.databaseConnector = databaseConnector;
        this.dialect = dialect;
        this.tableOrderManager = tableOrderManager;
        this.jooqSchemaManager = jooqSchemaManager;
        this.selfReferencingTableInsertionManager = selfReferencingTableInsertionManager;
        this.simpleTableInsertionManager = simpleTableInsertionManager;
    }
}

