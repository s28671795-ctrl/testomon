/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.JdbcUtil;

public class SchemaSnapshotGenerator
extends JdbcSnapshotGenerator {
    public SchemaSnapshotGenerator() {
        super(Schema.class);
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        Database database = snapshot.getDatabase();
        Schema match = null;
        String catalogName = ((Schema)example).getCatalogName();
        String schemaName = example.getName();
        if (database.supports(Schema.class)) {
            if (catalogName == null) {
                catalogName = database.getDefaultCatalogName();
            }
            if (schemaName == null) {
                schemaName = database.getDefaultSchemaName();
            }
        } else if (database.supports(Catalog.class)) {
            if (catalogName == null && schemaName != null) {
                catalogName = schemaName;
                schemaName = null;
            }
        } else {
            catalogName = null;
            schemaName = null;
        }
        example = new Schema(catalogName, schemaName);
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            if (database.supports(Schema.class)) {
                for (String tableSchema : this.getDatabaseSchemaNames(database)) {
                    CatalogAndSchema schemaFromJdbcInfo = this.toCatalogAndSchema(tableSchema, database);
                    Catalog catalog = new Catalog(schemaFromJdbcInfo.getCatalogName());
                    Schema schema = new Schema(catalog, tableSchema);
                    if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(schema, example, snapshot.getSchemaComparisons(), database)) continue;
                    if (match == null) {
                        match = schema;
                        continue;
                    }
                    throw new InvalidExampleException("Found multiple catalog/schemas matching " + ((Schema)example).getCatalogName() + "." + example.getName());
                }
            } else if (((Schema)example).getCatalog().isDefault()) {
                match = new Schema(((Schema)example).getCatalog(), catalogName);
            } else {
                String[] dbCatalogNames;
                Catalog catalog = ((Schema)example).getCatalog();
                for (String candidateCatalogName : dbCatalogNames = this.getDatabaseCatalogNames(database)) {
                    if (!catalog.equals(new Catalog(candidateCatalogName))) continue;
                    match = new Schema(catalog, catalogName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
        if (match != null && (match.getName() == null || match.getName().equalsIgnoreCase(database.getDefaultSchemaName()))) {
            match.setDefault(true);
        }
        return match;
    }

    protected CatalogAndSchema toCatalogAndSchema(String tableSchema, Database database) {
        return ((AbstractJdbcDatabase)database).getSchemaFromJdbcInfo(null, tableSchema);
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
    }

    protected String[] getDatabaseSchemaNames(Database database) throws SQLException, DatabaseException {
        ArrayList<String> returnList = new ArrayList<String>();
        try (ResultSet schemas = ((JdbcConnection)database.getConnection()).getMetaData().getSchemas();){
            while (schemas.next()) {
                returnList.add(JdbcUtil.getValueForColumn(schemas, "TABLE_SCHEM", database));
            }
        }
        return returnList.toArray(new String[0]);
    }
}

