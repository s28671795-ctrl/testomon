/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.jvm.SchemaSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.util.JdbcUtil;

public class SchemaSnapshotGeneratorSnowflake
extends SchemaSnapshotGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof SnowflakeDatabase) {
            return super.getPriority(objectType, database) + 5;
        }
        return -1;
    }

    @Override
    protected String[] getDatabaseSchemaNames(Database database) throws SQLException, DatabaseException {
        ArrayList<String> returnList = new ArrayList<String>();
        try (ResultSet schemas = ((JdbcConnection)database.getConnection()).getMetaData().getSchemas(database.getDefaultCatalogName(), null);){
            while (schemas.next()) {
                returnList.add(JdbcUtil.getValueForColumn(schemas, "TABLE_SCHEM", database));
            }
        }
        return returnList.toArray(new String[0]);
    }
}

