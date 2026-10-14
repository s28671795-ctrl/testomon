/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.SQLException;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

public class TableSnapshotGenerator
extends JdbcSnapshotGenerator {
    public TableSnapshotGenerator() {
        super(Table.class, new Class[]{Schema.class});
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException {
        Database database = snapshot.getDatabase();
        String objectName = example.getName();
        Schema schema = example.getSchema();
        List<CachedRow> rs = null;
        try {
            JdbcDatabaseSnapshot.CachingDatabaseMetaData metaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
            rs = metaData.getTables(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), objectName);
            if (rs.isEmpty()) {
                return null;
            }
            Table table = this.readTable(rs.get(0), database);
            return table;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        if (!snapshot.getSnapshotControl().shouldInclude(Table.class) || !snapshot.getDatabase().supports(Table.class)) {
            return;
        }
        if (foundObject instanceof Schema) {
            Database database = snapshot.getDatabase();
            Schema schema = (Schema)foundObject;
            List<CachedRow> tableMetaDataRs = null;
            try {
                tableMetaDataRs = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache().getTables(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), null);
                for (CachedRow row : tableMetaDataRs) {
                    String tableName = row.getString("TABLE_NAME");
                    Table tableExample = (Table)new Table().setName(this.cleanNameFromDatabase(tableName, database)).setSchema(schema);
                    schema.addDatabaseObject(tableExample);
                }
            }
            catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
    }

    protected Table readTable(CachedRow tableMetadataResultSet, Database database) throws SQLException, DatabaseException {
        String rawTableName = tableMetadataResultSet.getString("TABLE_NAME");
        String rawSchemaName = StringUtil.trimToNull(tableMetadataResultSet.getString("TABLE_SCHEM"));
        String rawCatalogName = StringUtil.trimToNull(tableMetadataResultSet.getString("TABLE_CAT"));
        String remarks = StringUtil.trimToNull(tableMetadataResultSet.getString("REMARKS"));
        String tablespace = StringUtil.trimToNull(tableMetadataResultSet.getString("TABLESPACE_NAME"));
        String defaultTablespaceString = StringUtil.trimToNull(tableMetadataResultSet.getString("DEFAULT_TABLESPACE"));
        if (remarks != null) {
            remarks = remarks.replace("''", "'");
        }
        Table table = new Table().setName(this.cleanNameFromDatabase(rawTableName, database));
        table.setRemarks(remarks);
        table.setTablespace(tablespace);
        table.setDefaultTablespace(BooleanUtil.isTrue(Boolean.parseBoolean(defaultTablespaceString)));
        CatalogAndSchema schemaFromJdbcInfo = ((AbstractJdbcDatabase)database).getSchemaFromJdbcInfo(rawCatalogName, rawSchemaName);
        table.setSchema(new Schema(schemaFromJdbcInfo.getCatalogName(), schemaFromJdbcInfo.getSchemaName()));
        if ("Y".equals(tableMetadataResultSet.getString("TEMPORARY"))) {
            table.setAttribute("temporary", "GLOBAL");
            String duration = tableMetadataResultSet.getString("DURATION");
            if ("SYS$TRANSACTION".equals(duration)) {
                table.setAttribute("duration", "ON COMMIT DELETE ROWS");
            } else if ("SYS$SESSION".equals(duration)) {
                table.setAttribute("duration", "ON COMMIT PRESERVE ROWS");
            }
        }
        return table;
    }
}

