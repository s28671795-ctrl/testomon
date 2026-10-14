/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.schema;

import com.google.inject.Inject;
import com.incoresoft.backup.database.BackupDatabaseManager;
import com.incoresoft.backup.database.BackupDatabaseUtils;
import com.incoresoft.backup.database.data.BackupIgnoredTables;
import com.incoresoft.backup.database.data.JooqSchemaManager;
import com.incoresoft.backup.database.exceptions.BackupDatabaseCannotBeCreatedException;
import com.incoresoft.backup.database.exceptions.BackupDatabaseStructureInvalidException;
import com.incoresoft.backup.database.schema.BackupSchemaValidationManager;
import com.incoresoft.middleware.database.DatabaseConnector;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Key;
import org.jooq.Named;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;

public class JooqBackupSchemaValidationManager
implements BackupSchemaValidationManager {
    private static final Map<Class<?>, Set<Class<?>>> H2_TYPE_TO_COMPATIBLE_TYPES_MAP = Map.of(String.class, Set.of(UUID.class), UUID.class, Set.of(String.class), Byte.class, Set.of(Short.class, UByte.class), Short.class, Set.of(Byte.class, UByte.class), Float.class, Set.of(Double.class), Double.class, Set.of(Float.class));
    private final DatabaseConnector databaseConnector;
    private final SQLDialect dialect;
    private final JooqSchemaManager jooqSchemaManager;

    @Override
    public void validateMainDatabaseStructure() {
        try (Connection connection = this.databaseConnector.getDataSource().getConnection();){
            DSLContext context = DSL.using(connection, this.dialect);
            Schema schema = this.jooqSchemaManager.getMainSchema(context);
            Map<Table, Integer> tableToNumberOfSelfReferencingKeysMap = schema.getForeignKeys().stream().filter(fk -> fk.getTable().equals(fk.getKey().getTable())).collect(Collectors.toMap(Key::getTable, fk -> 1, Integer::sum));
            for (Map.Entry<Table, Integer> entry : tableToNumberOfSelfReferencingKeysMap.entrySet()) {
                Table table = entry.getKey();
                Integer numberOfSelfReferencingKeys = entry.getValue();
                if (numberOfSelfReferencingKeys <= 1) continue;
                throw new BackupDatabaseCannotBeCreatedException(String.format("For table %s there are %d self-referencing foreign keys, expected <=1", table.getName(), tableToNumberOfSelfReferencingKeysMap.size()));
            }
        }
        catch (Exception e) {
            throw new BackupDatabaseCannotBeCreatedException(e);
        }
    }

    @Override
    public void validateBackupDatabaseStructure(DataSource backupDataSource) {
        DataSource mainDataSource = this.databaseConnector.getDataSource();
        try (Connection mainConnection = mainDataSource.getConnection();
             Connection backupConnection = backupDataSource.getConnection();){
            DSLContext mainContext = DSL.using(mainConnection, this.dialect);
            DSLContext backupContext = DSL.using(backupConnection, SQLDialect.H2);
            Schema mainSchema = this.jooqSchemaManager.getMainSchema(mainContext);
            Schema backupSchema = this.jooqSchemaManager.getBackupSchema(backupContext);
            BackupIgnoredTables ignoredTables = new BackupIgnoredTables(BackupDatabaseManager.ALWAYS_IGNORED_TABLES_FOR_RESTORE);
            List<Table> mainTables = mainSchema.getTables().stream().filter(table -> !ignoredTables.contains((Table<?>)table)).toList();
            Map<String, Table> backupTablesMap = backupSchema.getTables().stream().collect(Collectors.toMap(Named::getName, table -> table));
            mainTables = mainTables.stream().filter(table -> backupTablesMap.containsKey(BackupDatabaseUtils.formatName(table.getName()))).toList();
            List<Table> backupTables = mainTables.stream().map(table -> (Table)backupTablesMap.get(BackupDatabaseUtils.formatName(table.getName()))).filter(Objects::nonNull).toList();
            for (int i = 0; i < backupTables.size(); ++i) {
                Table mainTable = mainTables.get(i);
                Table backupTable = backupTables.get(i);
                this.validateSameTable(mainTable, backupTable);
            }
        }
        catch (BackupDatabaseStructureInvalidException e) {
            throw e;
        }
        catch (Exception e) {
            throw new BackupDatabaseStructureInvalidException(e);
        }
    }

    private void validateSameTable(Table<?> mainTable, Table<?> backupTable) {
        if (!BackupDatabaseUtils.formatName(mainTable.getName()).equals(backupTable.getName())) {
            throw new BackupDatabaseStructureInvalidException(String.format("Table names are different: %s, %s", mainTable.getName(), backupTable.getName()));
        }
        this.validateSamePrimaryKey(mainTable.getPrimaryKey(), backupTable.getPrimaryKey(), mainTable.getName());
        this.validateSameUniqueConstraints(mainTable, backupTable);
        this.validateSameForeignKeys(backupTable.getReferences().stream().map(key -> key).toList(), backupTable.getReferences().stream().map(key -> key).toList(), mainTable.getName());
        this.validateSameFields(List.of(mainTable.fields()), Arrays.stream(backupTable.fields()).collect(Collectors.toMap(Field::getName, field2 -> field2)), mainTable.getName());
    }

    private void validateSamePrimaryKey(@Nullable UniqueKey<?> mainPrimaryKey, @Nullable UniqueKey<?> backupPrimaryKey, @NotNull String tableName) {
        String backupHash;
        if (mainPrimaryKey == null && backupPrimaryKey == null) {
            return;
        }
        if (mainPrimaryKey == null) {
            throw new BackupDatabaseStructureInvalidException(String.format("Primary key of main table %s is null, backup key: %s", tableName, backupPrimaryKey.getName()));
        }
        if (backupPrimaryKey == null) {
            throw new BackupDatabaseStructureInvalidException(String.format("Primary key of backup table %s is null, main key: %s", tableName, mainPrimaryKey.getName()));
        }
        String mainHash = JooqBackupSchemaValidationManager.hashForKey(mainPrimaryKey);
        if (!mainHash.equals(backupHash = JooqBackupSchemaValidationManager.hashForKey(backupPrimaryKey))) {
            throw new BackupDatabaseStructureInvalidException(String.format("Primary keys are different for table %s: %s, %s", tableName, mainHash, backupHash));
        }
    }

    private void validateSameUniqueConstraints(Table<?> mainTable, Table<?> backupTable) {
        Set<String> mainHashes = JooqBackupSchemaValidationManager.getUniqueConstraintHashes(mainTable);
        Set<String> backupHashes = JooqBackupSchemaValidationManager.getUniqueConstraintHashes(backupTable);
        for (String mainHash : mainHashes) {
            if (backupHashes.contains(mainHash)) continue;
            throw new BackupDatabaseStructureInvalidException(String.format("Unique key is not found in backup table %s: %s", mainTable.getName(), mainHash));
        }
    }

    private void validateSameForeignKeys(@NotNull List<? extends ForeignKey<?, ?>> mainKeys, @NotNull List<? extends ForeignKey<?, ?>> backupKeys, String tableName) {
        if (mainKeys.size() != backupKeys.size()) {
            throw new BackupDatabaseStructureInvalidException(String.format("Number of foreign is different for table %s: %s in main, %s in backup", tableName, mainKeys.size(), backupKeys.size()));
        }
        Map<String, ForeignKey> backupKeysMap = backupKeys.stream().collect(Collectors.toMap(JooqBackupSchemaValidationManager::hashForKey, key -> key, (fk1, fk2) -> fk1));
        for (ForeignKey<?, ?> mainKey : mainKeys) {
            ForeignKey backupKey = backupKeysMap.get(JooqBackupSchemaValidationManager.hashForKey(mainKey));
            if (backupKey != null) continue;
            throw new BackupDatabaseStructureInvalidException(String.format("No foreign key found in backup table %s: %s", tableName, mainKey.getName()));
        }
    }

    private void validateSameFields(List<? extends Field<?>> mainFields, Map<String, ? extends Field<?>> backupFieldsMap, String tableName) {
        for (Field<?> mainField : mainFields) {
            Field<?> backupField = backupFieldsMap.get(BackupDatabaseUtils.formatName(mainField.getName()));
            if (backupField == null) {
                throw new BackupDatabaseStructureInvalidException(String.format("No field found in backup table %s: %s", tableName, mainField.getName()));
            }
            this.validateSameField(mainField, backupField, tableName);
        }
    }

    private void validateSameField(Field<?> mainField, Field<?> backupField, String tableName) {
        if (!BackupDatabaseUtils.formatName(mainField.getName()).equals(backupField.getName())) {
            throw new BackupDatabaseStructureInvalidException(String.format("Field names in table %s are different: %s, %s", tableName, mainField.getName(), backupField.getName()));
        }
        if (!JooqBackupSchemaValidationManager.typesAreCompatible(mainField, backupField)) {
            throw new BackupDatabaseStructureInvalidException(String.format("Field types in table %s are different: %s, %s", tableName, mainField.getDataType(), backupField.getDataType()));
        }
    }

    private static Set<String> getUniqueConstraintHashes(Table<?> table) {
        List<String> uniqueKeyHashes = table.getUniqueKeys().stream().map(JooqBackupSchemaValidationManager::hashForKey).toList();
        List<String> uniqueIndexHashes = table.getIndexes().stream().filter(Index::getUnique).map(index -> JooqBackupSchemaValidationManager.hashForFields(index.getFields())).toList();
        return Set.copyOf(CollectionUtils.union(uniqueKeyHashes, uniqueIndexHashes));
    }

    private static String hashForKey(Key<?> key) {
        return key.getFields().stream().map(it -> BackupDatabaseUtils.formatName(it.getName())).sorted().collect(Collectors.joining("-"));
    }

    private static String hashForFields(List<SortField<?>> fields) {
        return fields.stream().map(it -> BackupDatabaseUtils.formatName(it.getName())).sorted().collect(Collectors.joining("-"));
    }

    private static boolean typesAreCompatible(Field<?> mainField, Field<?> backupField) {
        Class backupFieldType;
        Class mainFieldType = mainField.getDataType().getType();
        if (mainFieldType.equals(backupFieldType = backupField.getDataType().getType())) {
            return true;
        }
        return H2_TYPE_TO_COMPATIBLE_TYPES_MAP.getOrDefault(backupFieldType, Set.of()).contains(mainFieldType);
    }

    @Inject
    public JooqBackupSchemaValidationManager(DatabaseConnector databaseConnector, SQLDialect dialect, JooqSchemaManager jooqSchemaManager) {
        this.databaseConnector = databaseConnector;
        this.dialect = dialect;
        this.jooqSchemaManager = jooqSchemaManager;
    }
}

