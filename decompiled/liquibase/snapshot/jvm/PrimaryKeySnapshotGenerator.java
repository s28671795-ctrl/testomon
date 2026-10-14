/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.SQLException;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class PrimaryKeySnapshotGenerator
extends JdbcSnapshotGenerator {
    public PrimaryKeySnapshotGenerator() {
        super(PrimaryKey.class, new Class[]{Table.class});
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        Database database = snapshot.getDatabase();
        Schema schema = example.getSchema();
        String searchTableName = null;
        if (((PrimaryKey)example).getTable() != null) {
            searchTableName = ((PrimaryKey)example).getTable().getName();
            searchTableName = database.correctObjectName(searchTableName, Table.class);
        }
        try {
            JdbcDatabaseSnapshot.CachingDatabaseMetaData metaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
            List<CachedRow> rs = metaData.getPrimaryKeys(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), searchTableName);
            PrimaryKey returnKey = null;
            for (CachedRow row : rs) {
                if (example.getName() != null && !example.getName().equalsIgnoreCase(row.getString("PK_NAME"))) continue;
                String columnName = this.cleanNameFromDatabase(row.getString("COLUMN_NAME"), database);
                short position = row.getShort("KEY_SEQ");
                if (returnKey == null) {
                    returnKey = new PrimaryKey();
                    CatalogAndSchema tableSchema = ((AbstractJdbcDatabase)database).getSchemaFromJdbcInfo(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEM"));
                    returnKey.setTable((Table)new Table().setName(row.getString("TABLE_NAME")).setSchema(new Schema(tableSchema.getCatalogName(), tableSchema.getSchemaName())));
                    returnKey.setName(row.getString("PK_NAME"));
                }
                String ascOrDesc = row.getString("ASC_OR_DESC");
                Boolean descending = null;
                if ("D".equals(ascOrDesc)) {
                    descending = Boolean.TRUE;
                } else if ("A".equals(ascOrDesc)) {
                    descending = Boolean.FALSE;
                }
                boolean computed = descending != null && descending != false;
                returnKey.addColumn(position - 1, new Column(columnName).setDescending(descending).setComputed(computed).setRelation(((PrimaryKey)example).getTable()));
                this.setValidateOptionIfAvailable(database, returnKey, row);
            }
            if (returnKey != null) {
                Index exampleIndex = new Index().setRelation(returnKey.getTable());
                exampleIndex.setColumns(returnKey.getColumns());
                returnKey.setBackingIndex(exampleIndex);
            }
            return returnKey;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private void setValidateOptionIfAvailable(Database database, PrimaryKey primaryKey, CachedRow cachedRow) {
        if (!(database instanceof OracleDatabase)) {
            return;
        }
        String constraintValidate = cachedRow.getString("VALIDATED");
        String VALIDATE = "VALIDATED";
        if (constraintValidate != null && !constraintValidate.isEmpty()) {
            primaryKey.setShouldValidate("VALIDATED".equals(this.cleanNameFromDatabase(constraintValidate.trim(), database)));
        }
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException {
        if (!snapshot.getSnapshotControl().shouldInclude(PrimaryKey.class) || !snapshot.getDatabase().supports(PrimaryKey.class)) {
            return;
        }
        if (foundObject instanceof Table) {
            Table table = (Table)foundObject;
            Database database = snapshot.getDatabase();
            Schema schema = table.getSchema();
            try {
                JdbcDatabaseSnapshot.CachingDatabaseMetaData metaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
                List<CachedRow> rs = metaData.getPrimaryKeys(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), table.getName());
                if (!rs.isEmpty()) {
                    PrimaryKey primaryKey = new PrimaryKey().setName(rs.get(0).getString("PK_NAME"));
                    primaryKey.setTable((Table)foundObject);
                    if (!database.isSystemObject(primaryKey)) {
                        table.setPrimaryKey(primaryKey.setTable(table));
                    }
                }
            }
            catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
    }
}

