/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.ForeignKeyConstraintType;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class ForeignKeySnapshotGenerator
extends JdbcSnapshotGenerator {
    protected static final String METADATA_DEFERRABILITY = "DEFERRABILITY";
    public static final String METADATA_FKTABLE_CAT = "FKTABLE_CAT";
    public static final String METADATA_FKTABLE_SCHEM = "FKTABLE_SCHEM";
    public static final String METADATA_FKTABLE_NAME = "FKTABLE_NAME";
    public static final String METADATA_FKCOLUMN_NAME = "FKCOLUMN_NAME";
    public static final String METADATA_PKTABLE_CAT = "PKTABLE_CAT";
    public static final String METADATA_PKTABLE_SCHEM = "PKTABLE_SCHEM";
    public static final String METADATA_PKTABLE_NAME = "PKTABLE_NAME";
    public static final String METADATA_PKCOLUMN_NAME = "PKCOLUMN_NAME";
    public static final String METADATA_UPDATE_RULE = "UPDATE_RULE";
    public static final String METADATA_DELETE_RULE = "DELETE_RULE";

    public ForeignKeySnapshotGenerator() {
        super(ForeignKey.class, new Class[]{Table.class});
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        if (!snapshot.getSnapshotControl().shouldInclude(ForeignKey.class) || !snapshot.getDatabase().supports(ForeignKey.class)) {
            return;
        }
        if (foundObject instanceof Table) {
            Table table = (Table)foundObject;
            Database database = snapshot.getDatabase();
            Schema schema = table.getSchema();
            HashSet<String> seenFks = new HashSet<String>();
            try {
                List<CachedRow> importedKeyMetadataResultSet = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache().getForeignKeys(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), database.correctObjectName(table.getName(), Table.class), null);
                for (CachedRow row : importedKeyMetadataResultSet) {
                    ForeignKey fk = new ForeignKey().setName(row.getString("FK_NAME")).setForeignKeyTable(table);
                    if (!seenFks.add(fk.getName())) continue;
                    table.getOutgoingForeignKeys().add(fk);
                }
            }
            catch (Exception e) {
                throw new DatabaseException(e);
            }
        }
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        Database database = snapshot.getDatabase();
        try {
            Table fkTable = ((ForeignKey)example).getForeignKeyTable();
            String searchCatalog = ((AbstractJdbcDatabase)database).getJdbcCatalogName(fkTable.getSchema());
            String searchSchema = ((AbstractJdbcDatabase)database).getJdbcSchemaName(fkTable.getSchema());
            String searchTableName = database.correctObjectName(fkTable.getName(), Table.class);
            List<CachedRow> importedKeyMetadataResultSet = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache().getForeignKeys(searchCatalog, searchSchema, searchTableName, example.getName());
            ForeignKey foreignKey = null;
            for (CachedRow row : importedKeyMetadataResultSet) {
                String fk_name = this.cleanNameFromDatabase(row.getString("FK_NAME"), database);
                if (snapshot.getDatabase().isCaseSensitive() && (!fk_name.equals(example.getName()) || !fk_name.equalsIgnoreCase(example.getName()))) continue;
                if (foreignKey == null) {
                    foreignKey = new ForeignKey();
                }
                foreignKey.setName(fk_name);
                String fkTableCatalog = this.cleanNameFromDatabase(row.getString(METADATA_FKTABLE_CAT), database);
                String fkTableSchema = this.cleanNameFromDatabase(row.getString(METADATA_FKTABLE_SCHEM), database);
                String fkTableName = this.cleanNameFromDatabase(row.getString(METADATA_FKTABLE_NAME), database);
                Table foreignKeyTable = new Table().setName(fkTableName);
                foreignKeyTable.setSchema(new Schema(new Catalog(fkTableCatalog), fkTableSchema));
                foreignKey.setForeignKeyTable(foreignKeyTable);
                Column fkColumn = new Column(this.cleanNameFromDatabase(row.getString(METADATA_FKCOLUMN_NAME), database)).setRelation(foreignKeyTable);
                boolean alreadyAdded = false;
                for (Column existing : foreignKey.getForeignKeyColumns()) {
                    if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(existing, fkColumn, snapshot.getSchemaComparisons(), database)) continue;
                    alreadyAdded = true;
                }
                if (alreadyAdded) break;
                CatalogAndSchema pkTableSchema = ((AbstractJdbcDatabase)database).getSchemaFromJdbcInfo(row.getString(METADATA_PKTABLE_CAT), row.getString(METADATA_PKTABLE_SCHEM));
                Table tempPkTable = (Table)new Table().setName(row.getString(METADATA_PKTABLE_NAME)).setSchema(new Schema(pkTableSchema.getCatalogName(), pkTableSchema.getSchemaName()));
                foreignKey.setPrimaryKeyTable(tempPkTable);
                Column pkColumn = new Column(this.cleanNameFromDatabase(row.getString(METADATA_PKCOLUMN_NAME), database)).setRelation(tempPkTable);
                foreignKey.addForeignKeyColumn(fkColumn);
                foreignKey.addPrimaryKeyColumn(pkColumn);
                if (!(database instanceof Db2zDatabase)) {
                    ForeignKeyConstraintType updateRule = this.convertToForeignKeyConstraintType(row.getInt(METADATA_UPDATE_RULE), database);
                    foreignKey.setUpdateRule(updateRule);
                }
                ForeignKeyConstraintType deleteRule = this.convertToForeignKeyConstraintType(row.getInt(METADATA_DELETE_RULE), database);
                foreignKey.setDeleteRule(deleteRule);
                int deferrability = row.getShort(METADATA_DEFERRABILITY).shortValue();
                if (database instanceof SybaseASADatabase && deferrability == 6) {
                    deferrability = 7;
                }
                if (deferrability == 0 || deferrability == 7) {
                    foreignKey.setDeferrable(false);
                    foreignKey.setInitiallyDeferred(false);
                } else if (deferrability == 5) {
                    foreignKey.setDeferrable(true);
                    foreignKey.setInitiallyDeferred(true);
                } else if (deferrability == 6) {
                    foreignKey.setDeferrable(true);
                    foreignKey.setInitiallyDeferred(false);
                } else {
                    throw new RuntimeException("Unknown deferrability result: " + deferrability);
                }
                this.setValidateOptionIfAvailable(database, foreignKey, row);
                Index exampleIndex = new Index().setRelation(foreignKey.getForeignKeyTable());
                exampleIndex.getColumns().addAll(foreignKey.getForeignKeyColumns());
                exampleIndex.addAssociatedWith("foreignKey");
                foreignKey.setBackingIndex(exampleIndex);
            }
            if (snapshot.get(ForeignKey.class).contains(foreignKey)) {
                return null;
            }
            return foreignKey;
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    private void setValidateOptionIfAvailable(Database database, ForeignKey foreignKey, CachedRow cachedRow) {
        if (!(database instanceof OracleDatabase)) {
            return;
        }
        String constraintValidate = cachedRow.getString("FK_VALIDATE");
        String VALIDATE = "VALIDATED";
        if (constraintValidate != null && !constraintValidate.isEmpty()) {
            foreignKey.setShouldValidate("VALIDATED".equals(this.cleanNameFromDatabase(constraintValidate.trim(), database)));
        }
    }

    protected ForeignKeyConstraintType convertToForeignKeyConstraintType(Integer jdbcType, Database database) throws DatabaseException {
        if (jdbcType == null) {
            return ForeignKeyConstraintType.importedKeyRestrict;
        }
        if (this.driverUsesSpFkeys(database)) {
            if (jdbcType == 0) {
                return ForeignKeyConstraintType.importedKeyCascade;
            }
            if (jdbcType == 1) {
                return ForeignKeyConstraintType.importedKeyNoAction;
            }
            if (jdbcType == 2) {
                return ForeignKeyConstraintType.importedKeySetNull;
            }
            if (jdbcType == 3) {
                return ForeignKeyConstraintType.importedKeySetDefault;
            }
            throw new DatabaseException("Unknown constraint type: " + jdbcType);
        }
        if (database instanceof SybaseDatabase) {
            return null;
        }
        if (jdbcType == 0) {
            return ForeignKeyConstraintType.importedKeyCascade;
        }
        if (jdbcType == 3) {
            if (database instanceof SybaseASADatabase) {
                return ForeignKeyConstraintType.importedKeySetDefault;
            }
            return ForeignKeyConstraintType.importedKeyNoAction;
        }
        if (jdbcType == 1) {
            if (database instanceof MSSQLDatabase) {
                return ForeignKeyConstraintType.importedKeyNoAction;
            }
            return ForeignKeyConstraintType.importedKeyRestrict;
        }
        if (jdbcType == 4) {
            return ForeignKeyConstraintType.importedKeySetDefault;
        }
        if (jdbcType == 2) {
            return ForeignKeyConstraintType.importedKeySetNull;
        }
        throw new DatabaseException("Unknown constraint type: " + jdbcType);
    }

    private boolean driverUsesSpFkeys(Database database) throws DatabaseException {
        if (!(database instanceof MSSQLDatabase)) {
            return false;
        }
        DatabaseConnection connection = database.getConnection();
        if (!(connection instanceof JdbcConnection)) {
            return false;
        }
        try {
            DatabaseMetaData metaData = ((JdbcConnection)connection).getMetaData();
            int driverMajorVersion = metaData.getDriverMajorVersion();
            int driverMinorVersion = metaData.getDriverMinorVersion();
            String driverName = metaData.getDriverName();
            if (!driverName.startsWith("Microsoft")) {
                return false;
            }
            return driverMajorVersion <= 6 && (driverMajorVersion != 6 || driverMinorVersion < 3);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}

