/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.LiquibaseTableNamesFactory;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.CockroachDatabase;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.Ingres9Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MariaDBDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.executor.jvm.ColumnMapRowMapper;
import liquibase.executor.jvm.RowMapperNotNullConstraintsResultSetExtractor;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.ResultSetCache;
import liquibase.snapshot.SnapshotControl;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public class JdbcDatabaseSnapshot
extends DatabaseSnapshot {
    private boolean warnedAboutDbaRecycleBin;
    private static final boolean ignoreWarnAboutDbaRecycleBin = Boolean.getBoolean("liquibase.ignoreRecycleBinWarning");
    private CachingDatabaseMetaData cachingDatabaseMetaData;
    private Map<String, CachedRow> cachedExpressionMap = null;
    private Set<String> userDefinedTypes;

    public JdbcDatabaseSnapshot(DatabaseObject[] examples, Database database, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        super(examples, database, snapshotControl);
    }

    public JdbcDatabaseSnapshot(DatabaseObject[] examples, Database database) throws DatabaseException, InvalidExampleException {
        super(examples, database);
    }

    public CachingDatabaseMetaData getMetaDataFromCache() throws SQLException {
        if (this.cachingDatabaseMetaData == null) {
            DatabaseMetaData databaseMetaData = null;
            if (this.getDatabase().getConnection() != null) {
                databaseMetaData = ((JdbcConnection)this.getDatabase().getConnection()).getUnderlyingConnection().getMetaData();
            }
            this.cachingDatabaseMetaData = new CachingDatabaseMetaData(this.getDatabase(), databaseMetaData);
        }
        return this.cachingDatabaseMetaData;
    }

    private String getAllCatalogsStringScratchData() {
        return (String)this.getScratchData("DatabaseSnapshot.allCatalogsString");
    }

    private String escapeForLike(String string, Database database) {
        if (string == null) {
            return null;
        }
        if (database instanceof SQLiteDatabase) {
            return string;
        }
        return string.replace("%", "\\%").replace("_", "\\_");
    }

    public class CachingDatabaseMetaData {
        private static final String SQL_FILTER_MATCH_ALL = "%";
        private final DatabaseMetaData databaseMetaData;
        private final Database database;

        public CachingDatabaseMetaData(Database database, DatabaseMetaData metaData) {
            this.databaseMetaData = metaData;
            this.database = database;
        }

        public DatabaseMetaData getDatabaseMetaData() {
            return this.databaseMetaData;
        }

        public List<CachedRow> getForeignKeys(String catalogName, String schemaName, String tableName, String fkName) throws DatabaseException {
            ForeignKeysResultSetCache foreignKeysResultSetCache = new ForeignKeysResultSetCache(this.database, catalogName, schemaName, tableName, fkName);
            ResultSetCache importedKeys = JdbcDatabaseSnapshot.this.getResultSetCache("getImportedKeys");
            importedKeys.setBulkTracking(!(this.database instanceof MSSQLDatabase));
            return importedKeys.get(foreignKeysResultSetCache);
        }

        public List<CachedRow> getIndexInfo(final String catalogName, final String schemaName, final String tableName, final String indexName) throws DatabaseException, SQLException {
            return JdbcDatabaseSnapshot.this.getResultSetCache("getIndexInfo").get(new ResultSetCache.UnionResultSetExtractor(this.database){
                public boolean isBulkFetchMode;

                @Override
                public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                    return new ResultSetCache.RowData(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEM"), CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"), row.getString("INDEX_NAME"));
                }

                @Override
                public ResultSetCache.RowData wantedKeyParameters() {
                    return new ResultSetCache.RowData(catalogName, schemaName, CachingDatabaseMetaData.this.database, tableName, indexName);
                }

                @Override
                public boolean bulkContainsSchema(String schemaKey) {
                    return JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() != null && CachingDatabaseMetaData.this.database instanceof OracleDatabase;
                }

                @Override
                public String getSchemaKey(CachedRow row) {
                    return row.getString("TABLE_SCHEM");
                }

                @Override
                public List<CachedRow> fastFetch() throws SQLException, DatabaseException {
                    ArrayList<CachedRow> returnList = new ArrayList<CachedRow>();
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        CachingDatabaseMetaData.this.warnAboutDbaRecycleBin();
                        String sql = "SELECT c.INDEX_NAME, 3 AS TYPE, c.TABLE_OWNER AS TABLE_SCHEM, c.TABLE_NAME, c.COLUMN_NAME, c.COLUMN_POSITION AS ORDINAL_POSITION, NULL AS FILTER_CONDITION, c.INDEX_OWNER, CASE I.UNIQUENESS WHEN 'UNIQUE' THEN 0 ELSE 1 END AS NON_UNIQUE, CASE c.DESCEND WHEN 'Y' THEN 'D' WHEN 'DESC' THEN 'D' WHEN 'N' THEN 'A' WHEN 'ASC' THEN 'A' END AS ASC_OR_DESC, CASE WHEN tablespace_name = (SELECT default_tablespace FROM user_users) THEN NULL ELSE tablespace_name END AS tablespace_name  FROM ALL_IND_COLUMNS c JOIN ALL_INDEXES i ON i.owner=c.index_owner AND i.index_name = c.index_name and i.table_owner = c.table_owner LEFT OUTER JOIN " + (((OracleDatabase)CachingDatabaseMetaData.this.database).canAccessDbaRecycleBin() ? "dba_recyclebin" : "user_recyclebin") + " d ON d.object_name=c.table_name ";
                        sql = !this.isBulkFetchMode || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sql + "WHERE c.TABLE_OWNER = '" + CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Schema.class) + "' " : sql + "WHERE c.TABLE_OWNER IN ('" + CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Schema.class) + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ")";
                        sql = sql + "AND i.OWNER = c.TABLE_OWNER AND d.object_name IS NULL ";
                        if (!this.isBulkFetchMode && tableName != null) {
                            sql = sql + " AND c.TABLE_NAME='" + tableName + "'";
                        }
                        if (!this.isBulkFetchMode && indexName != null) {
                            sql = sql + " AND c.INDEX_NAME='" + indexName + "'";
                        }
                        sql = sql + " ORDER BY c.INDEX_NAME, ORDINAL_POSITION";
                        returnList.addAll(this.setIndexExpressions(this.executeAndExtract(sql, CachingDatabaseMetaData.this.database)));
                    } else if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        String tableCat = "original_db_name()";
                        if (9 <= CachingDatabaseMetaData.this.database.getDatabaseMajorVersion()) {
                            tableCat = "db_name()";
                        }
                        String sql = "SELECT " + tableCat + " as TABLE_CAT, object_schema_name(i.object_id) as TABLE_SCHEM, object_name(i.object_id) as TABLE_NAME, CASE is_unique WHEN 1 then 0 else 1 end as NON_UNIQUE, object_name(i.object_id) as INDEX_QUALIFIER, i.name as INDEX_NAME, case i.type when 1 then 1 ELSE 3 end as TYPE, key_ordinal as ORDINAL_POSITION, COL_NAME(c.object_id,c.column_id) AS COLUMN_NAME, case is_descending_key when 0 then 'A' else 'D' end as ASC_OR_DESC, null as CARDINALITY, null as PAGES, i.filter_definition as FILTER_CONDITION, o.type AS INTERNAL_OBJECT_TYPE, i.*, c.*, s.* FROM sys.indexes i join sys.index_columns c on i.object_id=c.object_id and i.index_id=c.index_id join sys.stats s on i.object_id=s.object_id and i.name=s.name join sys.objects o on i.object_id=o.object_id WHERE object_schema_name(i.object_id)='" + CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getSchemaName(), Schema.class) + "'";
                        if (!this.isBulkFetchMode && tableName != null) {
                            sql = sql + " AND object_name(i.object_id)='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(tableName) + "'";
                        }
                        if (!this.isBulkFetchMode && indexName != null) {
                            sql = sql + " AND i.name='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(indexName) + "'";
                        }
                        sql = sql + "ORDER BY i.object_id, i.index_id, c.key_ordinal";
                        returnList.addAll(this.executeAndExtract(sql, CachingDatabaseMetaData.this.database));
                    } else if (CachingDatabaseMetaData.this.database instanceof Db2zDatabase) {
                        ArrayList<String> parameters = new ArrayList<String>(3);
                        String sql = "SELECT i.CREATOR AS TABLE_SCHEM, i.TBNAME AS TABLE_NAME, i.NAME AS INDEX_NAME, 3 AS TYPE, k.COLNAME AS COLUMN_NAME, k.COLSEQ AS ORDINAL_POSITION, CASE UNIQUERULE WHEN 'D' then 1 else 0 end as NON_UNIQUE, k.ORDERING AS ORDER, i.CREATOR AS INDEX_QUALIFIER FROM SYSIBM.SYSKEYS k JOIN SYSIBM.SYSINDEXES i ON k.IXNAME = i.NAME AND k.IXCREATOR = i.CREATOR WHERE  i.CREATOR = ?";
                        parameters.add(CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getSchemaName(), Schema.class));
                        if (!this.isBulkFetchMode && tableName != null) {
                            sql = sql + " AND i.TBNAME = ?";
                            parameters.add(CachingDatabaseMetaData.this.database.escapeStringForDatabase(tableName));
                        }
                        if (!this.isBulkFetchMode && indexName != null) {
                            sql = sql + " AND i.NAME = ?";
                            parameters.add(CachingDatabaseMetaData.this.database.escapeStringForDatabase(indexName));
                        }
                        sql = sql + "ORDER BY i.NAME, k.COLSEQ";
                        returnList.addAll(this.executeAndExtract(CachingDatabaseMetaData.this.database, sql, parameters.toArray()));
                    } else if (!(CachingDatabaseMetaData.this.database instanceof MariaDBDatabase) && CachingDatabaseMetaData.this.database instanceof MySQLDatabase) {
                        String filterConditionValue = "NULL";
                        if (CachingDatabaseMetaData.this.database.getDatabaseMajorVersion() > 8 || CachingDatabaseMetaData.this.database.getDatabaseMajorVersion() == 8 && ((MySQLDatabase)CachingDatabaseMetaData.this.database).getDatabasePatchVersion() >= 13) {
                            filterConditionValue = "EXPRESSION";
                        }
                        StringBuilder sql = new StringBuilder("SELECT TABLE_CATALOG AS TABLE_CAT, TABLE_SCHEMA AS TABLE_SCHEM,");
                        sql.append(" TABLE_NAME, NON_UNIQUE, NULL AS INDEX_QUALIFIER, INDEX_NAME,");
                        sql.append(3);
                        sql.append(" AS TYPE, SEQ_IN_INDEX AS ORDINAL_POSITION, COLUMN_NAME,");
                        sql.append("COLLATION AS ASC_OR_DESC, CARDINALITY, 0 AS PAGES, " + filterConditionValue + " AS FILTER_CONDITION FROM INFORMATION_SCHEMA.STATISTICS WHERE");
                        sql.append(" TABLE_SCHEMA = '").append(CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Catalog.class)).append("'");
                        if (!this.isBulkFetchMode && tableName != null) {
                            sql.append(" AND TABLE_NAME = '").append(CachingDatabaseMetaData.this.database.escapeStringForDatabase(tableName)).append("'");
                        }
                        if (!this.isBulkFetchMode && indexName != null) {
                            sql.append(" AND INDEX_NAME='").append(CachingDatabaseMetaData.this.database.escapeStringForDatabase(indexName)).append("'");
                        }
                        sql.append("ORDER BY NON_UNIQUE, INDEX_NAME, SEQ_IN_INDEX");
                        returnList.addAll(this.executeAndExtract(sql.toString(), CachingDatabaseMetaData.this.database));
                    } else {
                        ArrayList<String> tables = new ArrayList<String>();
                        if (tableName == null) {
                            for (CachedRow row : CachingDatabaseMetaData.this.getTables(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema), ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema), null)) {
                                tables.add(row.getString("TABLE_NAME"));
                            }
                        } else {
                            tables.add(tableName);
                        }
                        for (String tableName1 : tables) {
                            ResultSet rs = CachingDatabaseMetaData.this.databaseMetaData.getIndexInfo(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema), ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema), tableName1, false, true);
                            List<CachedRow> rows = this.extract(rs, CachingDatabaseMetaData.this.database instanceof InformixDatabase);
                            returnList.addAll(rows);
                        }
                    }
                    return returnList;
                }

                private List<CachedRow> setIndexExpressions(List<CachedRow> c) throws DatabaseException, SQLException {
                    Map<String, CachedRow> expressionMap = this.getCachedExpressionMap();
                    c.forEach(row -> {
                        row.set("FILTER_CONDITION", null);
                        String key = row.getString("INDEX_OWNER") + "::" + row.getString("INDEX_NAME") + "::" + row.getInt("ORDINAL_POSITION");
                        CachedRow fromMap = (CachedRow)expressionMap.get(key);
                        if (fromMap != null) {
                            row.set("FILTER_CONDITION", fromMap.get("COLUMN_EXPRESSION"));
                        }
                    });
                    return c;
                }

                private Map<String, CachedRow> getCachedExpressionMap() throws DatabaseException, SQLException {
                    if (JdbcDatabaseSnapshot.this.cachedExpressionMap != null) {
                        return JdbcDatabaseSnapshot.this.cachedExpressionMap;
                    }
                    String expSql = "SELECT e.column_expression, e.index_owner, e.index_name, e.column_position FROM all_ind_expressions e";
                    List<CachedRow> ec = this.executeAndExtract(expSql, CachingDatabaseMetaData.this.database);
                    JdbcDatabaseSnapshot.this.cachedExpressionMap = new HashMap();
                    ec.forEach(row -> {
                        String key = row.getString("INDEX_OWNER") + "::" + row.getString("INDEX_NAME") + "::" + row.getInt("COLUMN_POSITION");
                        JdbcDatabaseSnapshot.this.cachedExpressionMap.put(key, row);
                    });
                    return JdbcDatabaseSnapshot.this.cachedExpressionMap;
                }

                @Override
                public List<CachedRow> bulkFetch() throws SQLException, DatabaseException {
                    this.isBulkFetchMode = true;
                    return this.fastFetch();
                }

                @Override
                protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase || CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        return JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() != null || tableName == null && indexName == null || super.shouldBulkSelect(schemaKey, resultSetCache);
                    }
                    return false;
                }
            });
        }

        protected void warnAboutDbaRecycleBin() {
            if (!(ignoreWarnAboutDbaRecycleBin || JdbcDatabaseSnapshot.this.warnedAboutDbaRecycleBin || ((OracleDatabase)this.database).canAccessDbaRecycleBin())) {
                Scope.getCurrentScope().getLog(this.getClass()).warning(((OracleDatabase)this.database).getDbaRecycleBinWarning());
                JdbcDatabaseSnapshot.this.warnedAboutDbaRecycleBin = true;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public List<CachedRow> getColumns(String catalogName, String schemaName, String tableName, String columnName) throws SQLException, DatabaseException {
            if (this.database instanceof MSSQLDatabase && JdbcDatabaseSnapshot.this.userDefinedTypes == null) {
                JdbcDatabaseSnapshot.this.userDefinedTypes = new HashSet();
                DatabaseConnection databaseConnection = this.database.getConnection();
                if (databaseConnection instanceof JdbcConnection) {
                    Statement stmt = null;
                    ResultSet resultSet = null;
                    try {
                        stmt = ((JdbcConnection)databaseConnection).getUnderlyingConnection().createStatement();
                        resultSet = stmt.executeQuery("select name from " + (catalogName == null ? "" : "[" + catalogName + "].") + "sys.types where is_user_defined=1");
                        while (resultSet.next()) {
                            JdbcDatabaseSnapshot.this.userDefinedTypes.add(resultSet.getString("name").toLowerCase());
                        }
                    }
                    catch (Throwable throwable) {
                        JdbcUtil.close(resultSet, stmt);
                        throw throwable;
                    }
                    JdbcUtil.close(resultSet, stmt);
                }
            }
            GetColumnResultSetCache getColumnResultSetCache = new GetColumnResultSetCache(this.database, catalogName, schemaName, tableName, columnName);
            return JdbcDatabaseSnapshot.this.getResultSetCache("getColumns").get(getColumnResultSetCache);
        }

        public List<CachedRow> getNotNullConst(String catalogName, String schemaName, String tableName) throws DatabaseException {
            if (!(this.database instanceof OracleDatabase)) {
                return Collections.emptyList();
            }
            GetNotNullConstraintsResultSetCache getNotNullConstraintsResultSetCache = new GetNotNullConstraintsResultSetCache(this.database, catalogName, schemaName, tableName);
            return JdbcDatabaseSnapshot.this.getResultSetCache("getNotNullConst").get(getNotNullConstraintsResultSetCache);
        }

        public List<CachedRow> getTables(final String catalogName, final String schemaName, final String table) throws DatabaseException {
            return JdbcDatabaseSnapshot.this.getResultSetCache("getTables").get(new ResultSetCache.SingleResultSetExtractor(this.database){

                @Override
                protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                    return table == null || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() != null || super.shouldBulkSelect(schemaKey, resultSetCache);
                }

                @Override
                public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                    return new ResultSetCache.RowData(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEM"), CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"));
                }

                @Override
                public ResultSetCache.RowData wantedKeyParameters() {
                    return new ResultSetCache.RowData(catalogName, schemaName, CachingDatabaseMetaData.this.database, table);
                }

                @Override
                public boolean bulkContainsSchema(String schemaKey) {
                    return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
                }

                @Override
                public String getSchemaKey(CachedRow row) {
                    return row.getString("TABLE_SCHEM");
                }

                @Override
                public List<CachedRow> fastFetchQuery() throws SQLException, DatabaseException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        return this.queryOracle(catalogAndSchema, table);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        return this.queryMssql(catalogAndSchema, table);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof Db2zDatabase) {
                        return this.queryDb2Zos(catalogAndSchema, table);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof PostgresDatabase) {
                        return this.queryPostgres(catalogAndSchema, table);
                    }
                    String catalog = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema);
                    String schema = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    return this.extract(CachingDatabaseMetaData.this.databaseMetaData.getTables(catalog, JdbcDatabaseSnapshot.this.escapeForLike(schema, CachingDatabaseMetaData.this.database), table == null ? CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL : JdbcDatabaseSnapshot.this.escapeForLike(table, CachingDatabaseMetaData.this.database), new String[]{"TABLE"}));
                }

                @Override
                public List<CachedRow> bulkFetchQuery() throws SQLException, DatabaseException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        return this.queryOracle(catalogAndSchema, null);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        return this.queryMssql(catalogAndSchema, null);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof Db2zDatabase) {
                        return this.queryDb2Zos(catalogAndSchema, null);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof PostgresDatabase) {
                        return this.queryPostgres(catalogAndSchema, table);
                    }
                    String catalog = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema);
                    String schema = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    return this.extract(CachingDatabaseMetaData.this.databaseMetaData.getTables(catalog, JdbcDatabaseSnapshot.this.escapeForLike(schema, CachingDatabaseMetaData.this.database), CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL, new String[]{"TABLE"}));
                }

                private List<CachedRow> queryMssql(CatalogAndSchema catalogAndSchema, String tableName) throws DatabaseException, SQLException {
                    String dbIdParam;
                    String databasePrefix;
                    String ownerName = CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getSchemaName(), Schema.class);
                    String databaseName = StringUtil.trimToNull(CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Catalog.class));
                    if (databaseName == null) {
                        databasePrefix = "";
                        dbIdParam = "";
                    } else {
                        dbIdParam = ", db_id('" + databaseName + "')";
                        databasePrefix = "[" + databaseName + "].";
                    }
                    String sql = "select db_name(" + (databaseName == null ? "" : "db_id('" + databaseName + "')") + ") AS TABLE_CAT, convert(sysname,object_schema_name(o.object_id" + dbIdParam + ")) AS TABLE_SCHEM, convert(sysname,o.name) AS TABLE_NAME, 'TABLE' AS TABLE_TYPE, CAST(ep.value as varchar(max)) as REMARKS from " + databasePrefix + "sys.all_objects o left outer join sys.extended_properties ep on ep.name='MS_Description' and major_id=o.object_id and minor_id=0 where o.type in ('U') and has_perms_by_name(" + (databaseName == null ? "" : "quotename('" + databaseName + "') + '.' + ") + "quotename(object_schema_name(o.object_id" + dbIdParam + ")) + '.' + quotename(o.name), 'object', 'select') = 1 and charindex(substring(o.type,1,1),'U') <> 0 and object_schema_name(o.object_id" + dbIdParam + ")='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(ownerName) + "'";
                    if (tableName != null) {
                        sql = sql + " AND o.name='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(tableName) + "' ";
                    }
                    sql = sql + "order by 4, 1, 2, 3";
                    return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                }

                private List<CachedRow> queryOracle(CatalogAndSchema catalogAndSchema, String tableName) throws DatabaseException, SQLException {
                    String ownerName = CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Schema.class);
                    String sql = "SELECT null as TABLE_CAT, a.OWNER as TABLE_SCHEM, a.TABLE_NAME as TABLE_NAME, a.TEMPORARY as TEMPORARY, a.DURATION as DURATION, 'TABLE' as TABLE_TYPE, c.COMMENTS as REMARKS, A.tablespace_name as tablespace_name, CASE WHEN A.tablespace_name = (SELECT DEFAULT_TABLESPACE FROM USER_USERS) THEN 'true' ELSE null END as default_tablespace from ALL_TABLES a join ALL_TAB_COMMENTS c on a.TABLE_NAME=c.table_name and a.owner=c.owner left outer join ALL_QUEUE_TABLES q ON a.TABLE_NAME = q.QUEUE_TABLE and a.OWNER = q.OWNER WHERE q.QUEUE_TABLE is null ";
                    String allCatalogsString = JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData();
                    sql = tableName != null || allCatalogsString == null ? sql + "AND a.OWNER='" + ownerName + "'" : sql + "AND a.OWNER IN ('" + ownerName + "', " + allCatalogsString + ")";
                    if (tableName != null) {
                        sql = sql + " AND a.TABLE_NAME='" + tableName + "'";
                    }
                    return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                }

                private List<CachedRow> queryDb2Zos(CatalogAndSchema catalogAndSchema, String tableName) throws DatabaseException, SQLException {
                    String ownerName = CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Schema.class);
                    String sql = "SELECT CREATOR AS TABLE_SCHEM, NAME AS TABLE_NAME, 'TABLE' AS TABLE_TYPE, REMARKS FROM SYSIBM.SYSTABLES WHERE TYPE = 'T'";
                    ArrayList<String> parameters = new ArrayList<String>(2);
                    if (ownerName != null) {
                        sql = sql + " AND CREATOR = ?";
                        parameters.add(ownerName);
                    }
                    if (tableName != null) {
                        sql = sql + " AND NAME = ?";
                        parameters.add(tableName);
                    }
                    return this.executeAndExtract(CachingDatabaseMetaData.this.database, sql, parameters.toArray());
                }

                private List<CachedRow> queryPostgres(CatalogAndSchema catalogAndSchema, String tableName) throws SQLException {
                    String catalog = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema);
                    String schema = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    return this.extract(CachingDatabaseMetaData.this.databaseMetaData.getTables(catalog, JdbcDatabaseSnapshot.this.escapeForLike(schema, CachingDatabaseMetaData.this.database), tableName == null ? CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL : JdbcDatabaseSnapshot.this.escapeForLike(tableName, CachingDatabaseMetaData.this.database), new String[]{"TABLE", "PARTITIONED TABLE"}));
                }
            });
        }

        public List<CachedRow> getViews(final String catalogName, final String schemaName, String viewName) throws DatabaseException {
            final String view = this.database instanceof DB2Database ? this.database.correctObjectName(viewName, View.class) : viewName;
            return JdbcDatabaseSnapshot.this.getResultSetCache("getViews").get(new ResultSetCache.SingleResultSetExtractor(this.database){

                @Override
                protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                    return view == null || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() != null || super.shouldBulkSelect(schemaKey, resultSetCache);
                }

                @Override
                public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                    return new ResultSetCache.RowData(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEM"), CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"));
                }

                @Override
                public ResultSetCache.RowData wantedKeyParameters() {
                    return new ResultSetCache.RowData(catalogName, schemaName, CachingDatabaseMetaData.this.database, view);
                }

                @Override
                public boolean bulkContainsSchema(String schemaKey) {
                    return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
                }

                @Override
                public String getSchemaKey(CachedRow row) {
                    return row.getString("TABLE_SCHEM");
                }

                @Override
                public List<CachedRow> fastFetchQuery() throws SQLException, DatabaseException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        return this.queryOracle(catalogAndSchema, view);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        return this.queryMssql(catalogAndSchema, view);
                    }
                    String catalog = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema);
                    String schema = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    return this.extract(CachingDatabaseMetaData.this.databaseMetaData.getTables(catalog, JdbcDatabaseSnapshot.this.escapeForLike(schema, CachingDatabaseMetaData.this.database), view == null ? CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL : JdbcDatabaseSnapshot.this.escapeForLike(view, CachingDatabaseMetaData.this.database), new String[]{"VIEW"}));
                }

                @Override
                public List<CachedRow> bulkFetchQuery() throws SQLException, DatabaseException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        return this.queryOracle(catalogAndSchema, null);
                    }
                    if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        return this.queryMssql(catalogAndSchema, null);
                    }
                    String catalog = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema);
                    String schema = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    return this.extract(CachingDatabaseMetaData.this.databaseMetaData.getTables(catalog, JdbcDatabaseSnapshot.this.escapeForLike(schema, CachingDatabaseMetaData.this.database), CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL, new String[]{"VIEW"}));
                }

                private List<CachedRow> queryMssql(CatalogAndSchema catalogAndSchema, String viewName) throws DatabaseException, SQLException {
                    boolean haveDatabaseName;
                    String ownerName = CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getSchemaName(), Schema.class);
                    String databaseName = StringUtil.trimToNull(CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Catalog.class));
                    String dbIdParam = "";
                    String databasePrefix = "";
                    boolean bl = haveDatabaseName = databaseName != null;
                    if (haveDatabaseName) {
                        dbIdParam = ", db_id('" + databaseName + "')";
                        databasePrefix = "[" + databaseName + "].";
                    }
                    String tableCatParam = haveDatabaseName ? "db_id('" + databaseName + "')" : "";
                    String permsParam = haveDatabaseName ? "quotename('" + databaseName + "') + '.' + " : "";
                    String sql = "select db_name(" + tableCatParam + ") AS TABLE_CAT, convert(sysname,object_schema_name(o.object_id" + dbIdParam + ")) AS TABLE_SCHEM, convert(sysname,o.name) AS TABLE_NAME, 'VIEW' AS TABLE_TYPE, CAST(ep.value as varchar(max)) as REMARKS from " + databasePrefix + "sys.all_objects o left join sys.extended_properties ep on ep.name='MS_Description' and major_id=o.object_id and minor_id=0 where o.type in ('V') and has_perms_by_name(" + permsParam + "quotename(object_schema_name(o.object_id" + dbIdParam + ")) + '.' + quotename(o.name), 'object', 'select') = 1 and charindex(substring(o.type,1,1),'V') <> 0 and object_schema_name(o.object_id" + dbIdParam + ")='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(ownerName) + "'";
                    if (viewName != null) {
                        sql = sql + " AND o.name='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(viewName) + "' ";
                    }
                    sql = sql + "order by 4, 1, 2, 3";
                    return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                }

                private List<CachedRow> queryOracle(CatalogAndSchema catalogAndSchema, String viewName) throws DatabaseException, SQLException {
                    String ownerName = CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Schema.class);
                    String sql = "SELECT null as TABLE_CAT, a.OWNER as TABLE_SCHEM, a.VIEW_NAME as TABLE_NAME, 'TABLE' as TABLE_TYPE, c.COMMENTS as REMARKS, TEXT as OBJECT_BODY";
                    if (CachingDatabaseMetaData.this.database.getDatabaseMajorVersion() > 10) {
                        sql = sql + ", EDITIONING_VIEW";
                    }
                    sql = sql + " from ALL_VIEWS a join ALL_TAB_COMMENTS c on a.VIEW_NAME=c.table_name and a.owner=c.owner ";
                    sql = viewName != null || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sql + "WHERE a.OWNER='" + ownerName + "'" : sql + "WHERE a.OWNER IN ('" + ownerName + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ")";
                    if (viewName != null) {
                        sql = sql + " AND a.VIEW_NAME='" + CachingDatabaseMetaData.this.database.correctObjectName(viewName, View.class) + "'";
                    }
                    sql = sql + " AND a.VIEW_NAME not in (select mv.name from all_registered_mviews mv where mv.owner=a.owner)";
                    return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                }
            });
        }

        public List<CachedRow> getPrimaryKeys(final String catalogName, final String schemaName, final String table) throws DatabaseException {
            return JdbcDatabaseSnapshot.this.getResultSetCache("getPrimaryKeys").get(new ResultSetCache.SingleResultSetExtractor(this.database){

                @Override
                public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                    return new ResultSetCache.RowData(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEM"), CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"));
                }

                @Override
                public ResultSetCache.RowData wantedKeyParameters() {
                    return new ResultSetCache.RowData(catalogName, schemaName, CachingDatabaseMetaData.this.database, table);
                }

                @Override
                public boolean bulkContainsSchema(String schemaKey) {
                    return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
                }

                @Override
                public String getSchemaKey(CachedRow row) {
                    return row.getString("TABLE_SCHEM");
                }

                @Override
                public List<CachedRow> fastFetchQuery() throws SQLException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    try {
                        ArrayList<CachedRow> foundPks = new ArrayList<CachedRow>();
                        if (table == null) {
                            List<CachedRow> tables = CachingDatabaseMetaData.this.getTables(catalogName, schemaName, null);
                            for (CachedRow table2 : tables) {
                                List<CachedRow> pkInfo = this.getPkInfo(catalogAndSchema, table2.getString("TABLE_NAME"));
                                if (pkInfo == null) continue;
                                foundPks.addAll(pkInfo);
                            }
                            return foundPks;
                        }
                        List<CachedRow> pkInfo = this.getPkInfo(catalogAndSchema, table);
                        if (pkInfo != null) {
                            foundPks.addAll(pkInfo);
                        }
                        return foundPks;
                    }
                    catch (DatabaseException e) {
                        throw new SQLException(e);
                    }
                }

                private List<CachedRow> getPkInfo(CatalogAndSchema catalogAndSchema, String tableName) throws DatabaseException, SQLException {
                    if (!(CachingDatabaseMetaData.this.database instanceof MSSQLDatabase)) {
                        if (CachingDatabaseMetaData.this.database instanceof Db2zDatabase) {
                            String sql = "SELECT 'NULL' AS TABLE_CAT, SYSTAB.TBCREATOR AS TABLE_SCHEM, SYSTAB.TBNAME AS TABLE_NAME, COLUSE.COLNAME AS COLUMN_NAME, COLUSE.COLSEQ AS KEY_SEQ, SYSTAB.CONSTNAME AS PK_NAME FROM SYSIBM.SYSTABCONST SYSTAB JOIN SYSIBM.SYSKEYCOLUSE COLUSE ON SYSTAB.TBCREATOR = COLUSE.TBCREATOR WHERE SYSTAB.TYPE = 'P' AND SYSTAB.TBNAME = ? AND SYSTAB.TBCREATOR = ? AND SYSTAB.TBNAME=COLUSE.TBNAME AND SYSTAB.CONSTNAME=COLUSE.CONSTNAME ORDER BY COLUSE.COLNAME";
                            try {
                                return this.executeAndExtract(CachingDatabaseMetaData.this.database, sql, table, ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema));
                            }
                            catch (DatabaseException e) {
                                throw new SQLException(e);
                            }
                        }
                        if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                            CachingDatabaseMetaData.this.warnAboutDbaRecycleBin();
                            String sql = "SELECT NULL AS table_cat, c.owner AS table_schem, c.table_name, c.column_name as COLUMN_NAME, c.position AS key_seq, c.constraint_name AS pk_name, k.VALIDATED as VALIDATED FROM all_cons_columns c, all_constraints k LEFT JOIN " + (((OracleDatabase)CachingDatabaseMetaData.this.database).canAccessDbaRecycleBin() ? "dba_recyclebin" : "user_recyclebin") + " d ON d.object_name=k.table_name WHERE k.constraint_type = 'P' AND d.object_name IS NULL AND k.table_name = '" + table + "' AND k.owner = '" + ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema) + "' AND k.constraint_name = c.constraint_name AND k.table_name = c.table_name AND k.owner = c.owner ORDER BY column_name";
                            try {
                                return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                            }
                            catch (DatabaseException e) {
                                throw new SQLException(e);
                            }
                        }
                        if (CachingDatabaseMetaData.this.database instanceof CockroachDatabase) {
                            String sql = "SELECT   result.table_cat,   result.table_schem,   result.table_name,   result.column_name,   result.key_seq,   result.pk_name,   CASE result.indoption[result.key_seq - 1] & 1     WHEN 1 THEN 'D'     ELSE 'A'     END AS asc_or_desc FROM   (    SELECT       NULL AS table_cat,       n.nspname AS table_schem,       ct.relname AS table_name,       a.attname AS column_name,       (information_schema._pg_expandarray(i.indkey)).n         AS key_seq,       ci.relname AS pk_name,       information_schema._pg_expandarray(i.indkey) AS keys,       i.indoption,       a.attnum AS a_attnum     FROM       pg_catalog.pg_class AS ct       JOIN pg_catalog.pg_attribute AS a ON (ct.oid = a.attrelid)       JOIN pg_catalog.pg_namespace AS n ON           (ct.relnamespace = n.oid)       JOIN pg_catalog.pg_index AS i ON (a.attrelid = i.indrelid)       JOIN pg_catalog.pg_class AS ci ON (ci.oid = i.indexrelid)     WHERE       true       AND n.nspname = '" + ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema) + "'       AND ct.relname = '" + table + "'       AND i.indisprimary  )     AS result WHERE   result.a_attnum = (result.keys).x ORDER BY   result.table_name, result.pk_name, result.key_seq";
                            try {
                                return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                            }
                            catch (DatabaseException e) {
                                throw new SQLException(e);
                            }
                        }
                        return this.extract(CachingDatabaseMetaData.this.databaseMetaData.getPrimaryKeys(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema), ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema), table));
                    }
                    String sql = this.mssqlSql(catalogAndSchema, tableName);
                    List<CachedRow> pkInfo = this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                    return pkInfo;
                }

                private String mssqlSql(CatalogAndSchema catalogAndSchema, String tableName) throws DatabaseException {
                    String sql = "SELECT DB_NAME() AS [TABLE_CAT], [s].[name] AS [TABLE_SCHEM], [t].[name] AS [TABLE_NAME], [c].[name] AS [COLUMN_NAME], CASE [ic].[is_descending_key] WHEN 0 THEN N'A' WHEN 1 THEN N'D' END AS [ASC_OR_DESC], [ic].[key_ordinal] AS [KEY_SEQ], [kc].[name] AS [PK_NAME] FROM [sys].[schemas] AS [s] INNER JOIN [sys].[tables] AS [t] ON [t].[schema_id] = [s].[schema_id] INNER JOIN [sys].[key_constraints] AS [kc] ON [kc].[parent_object_id] = [t].[object_id] INNER JOIN [sys].[indexes] AS [i] ON [i].[object_id] = [kc].[parent_object_id] AND [i].[index_id] = [kc].[unique_index_id] INNER JOIN [sys].[index_columns] AS [ic] ON [ic].[object_id] = [i].[object_id] AND [ic].[index_id] = [i].[index_id] INNER JOIN [sys].[columns] AS [c] ON [c].[object_id] = [ic].[object_id] AND [c].[column_id] = [ic].[column_id] WHERE [s].[name] = N'" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(catalogAndSchema.getSchemaName()) + "' " + (tableName == null ? "" : "AND [t].[name] = N'" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(CachingDatabaseMetaData.this.database.correctObjectName(tableName, Table.class)) + "' ") + "AND [kc].[type] = 'PK' AND [ic].[key_ordinal] > 0 ORDER BY [ic].[key_ordinal]";
                    return sql;
                }

                @Override
                public List<CachedRow> bulkFetchQuery() throws SQLException {
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                        CachingDatabaseMetaData.this.warnAboutDbaRecycleBin();
                        try {
                            String sql = "SELECT NULL AS table_cat, c.owner AS table_schem, c.table_name, c.column_name, c.position AS key_seq,c.constraint_name AS pk_name, k.VALIDATED as VALIDATED FROM all_cons_columns c, all_constraints k LEFT JOIN " + (((OracleDatabase)CachingDatabaseMetaData.this.database).canAccessDbaRecycleBin() ? "dba_recyclebin" : "user_recyclebin") + " d ON d.object_name=k.table_name WHERE k.constraint_type = 'P' AND d.object_name IS NULL ";
                            sql = JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sql + "AND k.owner='" + catalogAndSchema.getCatalogName() + "' " : sql + "AND k.owner IN ('" + catalogAndSchema.getCatalogName() + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ")";
                            sql = sql + "AND k.constraint_name = c.constraint_name AND k.table_name = c.table_name AND k.owner = c.owner ORDER BY column_name";
                            return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                        }
                        catch (DatabaseException e) {
                            throw new SQLException(e);
                        }
                    }
                    if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                        try {
                            return this.executeAndExtract(this.mssqlSql(catalogAndSchema, null), CachingDatabaseMetaData.this.database);
                        }
                        catch (DatabaseException e) {
                            throw new SQLException(e);
                        }
                    }
                    return null;
                }

                @Override
                protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase || CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                        return table == null || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() != null || super.shouldBulkSelect(schemaKey, resultSetCache);
                    }
                    return false;
                }
            });
        }

        public List<CachedRow> getUniqueConstraints(final String catalogName, final String schemaName, final String tableName) throws DatabaseException {
            return JdbcDatabaseSnapshot.this.getResultSetCache("getUniqueConstraints").get(new ResultSetCache.SingleResultSetExtractor(this.database){

                @Override
                protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                    return tableName == null || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() != null || super.shouldBulkSelect(schemaKey, resultSetCache);
                }

                @Override
                public boolean bulkContainsSchema(String schemaKey) {
                    return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
                }

                @Override
                public String getSchemaKey(CachedRow row) {
                    return row.getString("CONSTRAINT_SCHEM");
                }

                @Override
                public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                    return new ResultSetCache.RowData(catalogName, schemaName, CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"));
                }

                @Override
                public ResultSetCache.RowData wantedKeyParameters() {
                    return new ResultSetCache.RowData(catalogName, schemaName, CachingDatabaseMetaData.this.database, tableName);
                }

                @Override
                public List<CachedRow> fastFetchQuery() throws SQLException, DatabaseException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    return this.queryDb(catalogAndSchema, tableName);
                }

                @Override
                public List<CachedRow> bulkFetchQuery() throws SQLException, DatabaseException {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(catalogName, schemaName).customize(CachingDatabaseMetaData.this.database);
                    return this.queryDb(catalogAndSchema, null);
                }

                private List<CachedRow> queryDb(CatalogAndSchema catalogAndSchema, String tableName2) throws SQLException, DatabaseException {
                    String jdbcCatalogName = catalogAndSchema.getCatalogName();
                    String jdbcSchemaName = catalogAndSchema.getSchemaName();
                    Database database = JdbcDatabaseSnapshot.this.getDatabase();
                    ArrayList<String> parameters = new ArrayList<String>(3);
                    String sql = null;
                    if (database instanceof Ingres9Database) {
                        sql = "select CONSTRAINT_NAME, TABLE_NAME from iiconstraints where schema_name ='" + schemaName + "' and constraint_type='U'";
                        if (tableName2 != null) {
                            sql = sql + " and table_name='" + tableName2 + "'";
                        }
                    } else if (database instanceof MySQLDatabase || database instanceof HsqlDatabase || database instanceof MariaDBDatabase) {
                        sql = "select CONSTRAINT_NAME, TABLE_NAME from " + database.getSystemSchema() + ".table_constraints where constraint_schema='" + jdbcCatalogName + "' and constraint_type='UNIQUE'";
                        if (tableName2 != null) {
                            sql = sql + " and table_name='" + tableName2 + "'";
                        }
                    } else if (database instanceof PostgresDatabase) {
                        sql = "select CONSTRAINT_NAME, TABLE_NAME from " + database.getSystemSchema() + ".table_constraints where constraint_catalog='" + jdbcCatalogName + "' and constraint_schema='" + jdbcSchemaName + "' and constraint_type='UNIQUE'";
                        if (tableName2 != null) {
                            sql = sql + " and table_name='" + tableName2 + "'";
                        }
                    } else if (database.getClass().getName().contains("MaxDB")) {
                        sql = "select distinct tablename AS TABLE_NAME, constraintname AS CONSTRAINT_NAME from CONSTRAINTCOLUMNS WHERE CONSTRAINTTYPE = 'UNIQUE_CONST'";
                        if (tableName2 != null) {
                            sql = sql + " and tablename='" + tableName2 + "'";
                        }
                    } else if (database instanceof MSSQLDatabase) {
                        sql = "SELECT [TC].[CONSTRAINT_NAME], [TC].[TABLE_NAME], [TC].[CONSTRAINT_CATALOG] AS INDEX_CATALOG, [TC].[CONSTRAINT_SCHEMA] AS INDEX_SCHEMA, [IDX].[TYPE_DESC], [IDX].[name] AS INDEX_NAME FROM [INFORMATION_SCHEMA].[TABLE_CONSTRAINTS] AS [TC] JOIN sys.indexes AS IDX ON IDX.name=[TC].[CONSTRAINT_NAME] AND object_schema_name(object_id)=[TC].[CONSTRAINT_SCHEMA] WHERE [TC].[CONSTRAINT_TYPE] = 'UNIQUE' AND [TC].[CONSTRAINT_CATALOG] = N'" + database.escapeStringForDatabase(jdbcCatalogName) + "' AND [TC].[CONSTRAINT_SCHEMA] = N'" + database.escapeStringForDatabase(jdbcSchemaName) + "'";
                        if (tableName2 != null) {
                            sql = sql + " AND [TC].[TABLE_NAME] = N'" + database.escapeStringForDatabase(database.correctObjectName(tableName2, Table.class)) + "'";
                        }
                    } else if (database instanceof OracleDatabase) {
                        CachingDatabaseMetaData.this.warnAboutDbaRecycleBin();
                        sql = "select uc.owner AS CONSTRAINT_SCHEM, uc.constraint_name, uc.table_name,uc.status,uc.deferrable,uc.deferred,ui.tablespace_name, ui.index_name, ui.owner as INDEX_CATALOG, uc.VALIDATED as VALIDATED, ac.COLUMN_NAME as COLUMN_NAME from all_constraints uc join all_indexes ui on uc.index_name = ui.index_name and uc.owner=ui.table_owner and uc.table_name=ui.table_name LEFT OUTER JOIN " + (((OracleDatabase)database).canAccessDbaRecycleBin() ? "dba_recyclebin" : "user_recyclebin") + " d ON d.object_name=ui.table_name LEFT JOIN all_cons_columns ac ON ac.OWNER = uc.OWNER AND ac.TABLE_NAME = uc.TABLE_NAME AND ac.CONSTRAINT_NAME = uc.CONSTRAINT_NAME where uc.constraint_type='U' ";
                        sql = tableName2 != null || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sql + "and uc.owner = '" + jdbcSchemaName + "'" : sql + "and uc.owner IN ('" + jdbcSchemaName + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ")";
                        sql = sql + "AND d.object_name IS NULL ";
                        if (tableName2 != null) {
                            sql = sql + " and uc.table_name = '" + tableName2 + "'";
                        }
                    } else if (database instanceof DB2Database) {
                        if (database.getDatabaseProductName().startsWith("DB2 UDB for AS/400")) {
                            sql = "select constraint_name as constraint_name, table_name as table_name from QSYS2.TABLE_CONSTRAINTS where table_schema='" + jdbcSchemaName + "' and constraint_type='UNIQUE'";
                            if (tableName2 != null) {
                                sql = sql + " and table_name = '" + tableName2 + "'";
                            }
                        } else {
                            sql = "select distinct k.constname as constraint_name, t.tabname as TABLE_NAME from syscat.keycoluse k inner join syscat.tabconst t on k.constname = t.constname where t.tabschema = ? and t.type = 'U'";
                            parameters.add(jdbcSchemaName);
                            if (tableName2 != null) {
                                sql = sql + " and t.tabname = ?";
                                parameters.add(tableName2);
                            }
                        }
                    } else if (database instanceof Db2zDatabase) {
                        sql = "select k.constname as constraint_name, t.tbname as TABLE_NAME from SYSIBM.SYSKEYCOLUSE k inner join SYSIBM.SYSTABCONST t on k.constname = t.constname and k.TBCREATOR = t.TBCREATOR and k.TBNAME = t.TBNAME where t.TBCREATOR = ? and t.TYPE = 'U'";
                        parameters.add(jdbcSchemaName);
                        if (tableName2 != null) {
                            sql = sql + " and t.TBNAME = ?";
                            parameters.add(tableName2);
                        }
                    } else if (database instanceof FirebirdDatabase) {
                        sql = "SELECT TRIM(RDB$INDICES.RDB$INDEX_NAME) AS CONSTRAINT_NAME, TRIM(RDB$INDICES.RDB$RELATION_NAME) AS TABLE_NAME FROM RDB$INDICES LEFT JOIN RDB$RELATION_CONSTRAINTS ON RDB$RELATION_CONSTRAINTS.RDB$INDEX_NAME = RDB$INDICES.RDB$INDEX_NAME WHERE RDB$INDICES.RDB$UNIQUE_FLAG IS NOT NULL AND (RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_TYPE IS NULL OR TRIM(RDB$RELATION_CONSTRAINTS.RDB$CONSTRAINT_TYPE)='UNIQUE') AND NOT(RDB$INDICES.RDB$INDEX_NAME LIKE 'RDB$%')";
                        if (tableName2 != null) {
                            sql = sql + " AND TRIM(RDB$INDICES.RDB$RELATION_NAME)='" + tableName2 + "'";
                        }
                    } else if (database instanceof DerbyDatabase) {
                        sql = "select c.constraintname as CONSTRAINT_NAME, tablename AS TABLE_NAME from sys.systables t, sys.sysconstraints c, sys.sysschemas s where s.schemaname='" + jdbcCatalogName + "' and t.tableid = c.tableid and t.schemaid=s.schemaid and c.type = 'U'";
                        if (tableName2 != null) {
                            sql = sql + " AND t.tablename = '" + tableName2 + "'";
                        }
                    } else if (database instanceof InformixDatabase) {
                        sql = "select unique sysindexes.idxname as CONSTRAINT_NAME, sysindexes.idxtype, systables.tabname as TABLE_NAME from sysindexes, systables left outer join sysconstraints on sysconstraints.tabid = systables.tabid and sysconstraints.constrtype = 'P' where sysindexes.tabid = systables.tabid and sysindexes.idxtype = 'U' and sysconstraints.idxname != sysindexes.idxname and sysconstraints.tabid = sysindexes.tabid";
                        if (tableName2 != null) {
                            sql = sql + " and systables.tabname = '" + database.correctObjectName(tableName2, Table.class) + "'";
                        }
                    } else if (database instanceof SybaseDatabase) {
                        sql = "select idx.name as CONSTRAINT_NAME, tbl.name as TABLE_NAME from sysindexes idx inner join sysobjects tbl on tbl.id = idx.id where idx.indid between 1 and 254 and (idx.status & 2) = 2 and tbl.type = 'U'";
                        if (tableName2 != null) {
                            sql = sql + " and tbl.name = '" + database.correctObjectName(tableName2, Table.class) + "'";
                        }
                    } else if (database instanceof SybaseASADatabase) {
                        sql = "select sysconstraint.constraint_name, sysconstraint.constraint_type, systable.table_name from sysconstraint, systable where sysconstraint.table_object_id = systable.object_id and sysconstraint.constraint_type = 'U'";
                        if (tableName2 != null) {
                            sql = sql + " and systable.table_name = '" + tableName2 + "'";
                        }
                    } else {
                        if (database instanceof H2Database) {
                            try {
                                if (database.getDatabaseMajorVersion() >= 2) {
                                    sql = "select CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME from " + database.getSystemSchema() + ".table_constraints where constraint_schema='" + jdbcSchemaName + "' and constraint_catalog='" + jdbcCatalogName + "' and constraint_type='UNIQUE'";
                                    if (tableName2 != null) {
                                        sql = sql + " and table_name='" + tableName2 + "'";
                                    }
                                }
                            }
                            catch (DatabaseException e) {
                                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot determine h2 version, using default unique constraint query");
                            }
                        }
                        if (sql == null) {
                            sql = "select CONSTRAINT_NAME, CONSTRAINT_TYPE, TABLE_NAME from " + database.getSystemSchema() + ".constraints where constraint_schema='" + jdbcSchemaName + "' and constraint_catalog='" + jdbcCatalogName + "' and constraint_type='UNIQUE'";
                            if (tableName2 != null) {
                                sql = sql + " and table_name='" + tableName2 + "'";
                            }
                        }
                    }
                    return this.executeAndExtract(database, database instanceof InformixDatabase, sql, parameters.toArray());
                }
            });
        }

        private class ForeignKeysResultSetCache
        extends ResultSetCache.UnionResultSetExtractor {
            final String catalogName;
            final String schemaName;
            final String tableName;
            final String fkName;

            private ForeignKeysResultSetCache(Database database, String catalogName, String schemaName, String tableName, String fkName) {
                super(database);
                this.catalogName = catalogName;
                this.schemaName = schemaName;
                this.tableName = tableName;
                this.fkName = fkName;
            }

            @Override
            public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                return new ResultSetCache.RowData(row.getString("FKTABLE_CAT"), row.getString("FKTABLE_SCHEM"), CachingDatabaseMetaData.this.database, row.getString("FKTABLE_NAME"), row.getString("FK_NAME"));
            }

            @Override
            public ResultSetCache.RowData wantedKeyParameters() {
                return new ResultSetCache.RowData(this.catalogName, this.schemaName, CachingDatabaseMetaData.this.database, this.tableName, this.fkName);
            }

            @Override
            public boolean bulkContainsSchema(String schemaKey) {
                return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
            }

            @Override
            public String getSchemaKey(CachedRow row) {
                return row.getString("FKTABLE_SCHEM");
            }

            @Override
            public List<CachedRow> fastFetch() throws SQLException, DatabaseException {
                CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                String jdbcCatalogName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema);
                String jdbcSchemaName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                if (CachingDatabaseMetaData.this.database instanceof DB2Database) {
                    if (CachingDatabaseMetaData.this.database.getDatabaseProductName().startsWith("DB2 UDB for AS/400")) {
                        this.executeAndExtract(this.getDB2ForAs400Sql(jdbcSchemaName, this.tableName), CachingDatabaseMetaData.this.database);
                    }
                    return this.querytDB2Luw(jdbcSchemaName, this.tableName);
                }
                if (CachingDatabaseMetaData.this.database instanceof Db2zDatabase) {
                    return this.queryDb2Zos(catalogAndSchema, this.tableName);
                }
                ArrayList<String> tables = new ArrayList<String>();
                if (this.tableName == null) {
                    for (CachedRow row : CachingDatabaseMetaData.this.getTables(jdbcCatalogName, jdbcSchemaName, null)) {
                        tables.add(row.getString("TABLE_NAME"));
                    }
                } else {
                    tables.add(this.tableName);
                }
                ArrayList<CachedRow> returnList = new ArrayList<CachedRow>();
                for (String foundTable : tables) {
                    if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                        throw new RuntimeException("Should have bulk selected");
                    }
                    returnList.addAll(this.extract(CachingDatabaseMetaData.this.databaseMetaData.getImportedKeys(jdbcCatalogName, jdbcSchemaName, foundTable)));
                }
                return returnList;
            }

            @Override
            public List<CachedRow> bulkFetch() throws SQLException, DatabaseException {
                if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                    String jdbcSchemaName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    String sql = this.getOracleSql(jdbcSchemaName);
                    return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                }
                if (CachingDatabaseMetaData.this.database instanceof DB2Database) {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                    String jdbcSchemaName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    if (CachingDatabaseMetaData.this.database.getDatabaseProductName().startsWith("DB2 UDB for AS/400")) {
                        this.executeAndExtract(this.getDB2ForAs400Sql(jdbcSchemaName, null), CachingDatabaseMetaData.this.database);
                    }
                    return this.querytDB2Luw(jdbcSchemaName, null);
                }
                if (CachingDatabaseMetaData.this.database instanceof Db2zDatabase) {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                    return this.queryDb2Zos(catalogAndSchema, null);
                }
                if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                    CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                    String jdbcSchemaName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                    String sql = this.getMSSQLSql(jdbcSchemaName, this.tableName);
                    return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                }
                throw new RuntimeException("Cannot bulk select");
            }

            protected String getOracleSql(String jdbcSchemaName) {
                String sql = "SELECT  /*+rule*/  NULL AS pktable_cat,    p.owner as pktable_schem,    p.table_name as pktable_name,    pc.column_name as pkcolumn_name,    NULL as fktable_cat,    f.owner as fktable_schem,    f.table_name as fktable_name,    fc.column_name as fkcolumn_name,    fc.position as key_seq,    NULL as update_rule,    decode (f.delete_rule, 'CASCADE', 0, 'SET NULL', 2, 1) as delete_rule,    f.constraint_name as fk_name,    p.constraint_name as pk_name,    decode(f.deferrable, 'DEFERRABLE', 5, 'NOT DEFERRABLE', 7, 'DEFERRED', 6) deferrability,    f.validated as fk_validate FROM all_cons_columns pc INNER JOIN all_constraints p ON pc.owner = p.owner AND pc.constraint_name = p.constraint_name INNER JOIN all_constraints f ON pc.owner = f.r_owner AND pc.constraint_name = f.r_constraint_name INNER JOIN all_cons_columns fc ON fc.owner = f.owner AND fc.constraint_name = f.constraint_name AND fc.position = pc.position ";
                sql = JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sql + "WHERE f.owner = '" + jdbcSchemaName + "' " : sql + "WHERE f.owner IN ('" + jdbcSchemaName + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ") ";
                sql = sql + "AND p.constraint_type in ('P', 'U') AND f.constraint_type = 'R' AND p.table_name NOT LIKE 'BIN$%' ORDER BY fktable_schem, fktable_name, key_seq";
                return sql;
            }

            protected String getMSSQLSql(String jdbcSchemaName, String tableName) {
                return "select convert(sysname,db_name()) AS PKTABLE_CAT, convert(sysname,schema_name(o1.schema_id)) AS PKTABLE_SCHEM, convert(sysname,o1.name) AS PKTABLE_NAME, convert(sysname,c1.name) AS PKCOLUMN_NAME, convert(sysname,db_name()) AS FKTABLE_CAT, convert(sysname,schema_name(o2.schema_id)) AS FKTABLE_SCHEM, convert(sysname,o2.name) AS FKTABLE_NAME, convert(sysname,c2.name) AS FKCOLUMN_NAME, isnull(convert(smallint,k.constraint_column_id), convert(smallint,0)) AS KEY_SEQ, convert(smallint, case ObjectProperty(f.object_id, 'CnstIsUpdateCascade') when 1 then 0 else 1 end) AS UPDATE_RULE, convert(smallint, case ObjectProperty(f.object_id, 'CnstIsDeleteCascade') when 1 then 0 else 1 end) AS DELETE_RULE, convert(sysname,object_name(f.object_id)) AS FK_NAME, convert(sysname,i.name) AS PK_NAME, convert(smallint, 7) AS DEFERRABILITY from sys.objects o1, sys.objects o2, sys.columns c1, sys.columns c2, sys.foreign_keys f inner join sys.foreign_key_columns k on (k.constraint_object_id = f.object_id) inner join sys.indexes i on (f.referenced_object_id = i.object_id and f.key_index_id = i.index_id) where o1.object_id = f.referenced_object_id and o2.object_id = f.parent_object_id and c1.object_id = f.referenced_object_id and c2.object_id = f.parent_object_id and c1.column_id = k.referenced_column_id and c2.column_id = k.parent_column_id and ((object_schema_name(o1.object_id)='" + jdbcSchemaName + "' and convert(sysname,schema_name(o2.schema_id))='" + jdbcSchemaName + "' and convert(sysname,o2.name)='" + tableName + "' ) or ( convert(sysname,schema_name(o2.schema_id))='" + jdbcSchemaName + "' and convert(sysname,o2.name)='" + tableName + "' )) order by 5, 6, 7, 9, 8";
            }

            private List<CachedRow> querytDB2Luw(String jdbcSchemaName, String tableName) throws DatabaseException, SQLException {
                ArrayList<String> parameters = new ArrayList<String>(2);
                StringBuilder sql = new StringBuilder("SELECT   pk_col.tabschema AS pktable_cat,    pk_col.tabname as pktable_name,    pk_col.colname as pkcolumn_name,   fk_col.tabschema as fktable_cat,    fk_col.tabname as fktable_name,    fk_col.colname as fkcolumn_name,   fk_col.colseq as key_seq,    decode (ref.updaterule, 'A', 3, 'R', 1, 1) as update_rule,    decode (ref.deleterule, 'A', 3, 'C', 0, 'N', 2, 'R', 1, 1) as delete_rule,    ref.constname as fk_name,    ref.refkeyname as pk_name,    7 as deferrability  FROM syscat.references ref join syscat.keycoluse fk_col on ref.constname=fk_col.constname and ref.tabschema=fk_col.tabschema and ref.tabname=fk_col.tabname join syscat.keycoluse pk_col on ref.refkeyname=pk_col.constname and ref.reftabschema=pk_col.tabschema and ref.reftabname=pk_col.tabname and pk_col.colseq=fk_col.colseq WHERE ref.tabschema = ? ");
                parameters.add(jdbcSchemaName);
                if (tableName != null) {
                    sql.append("and fk_col.tabname = ? ");
                    parameters.add(tableName);
                }
                sql.append("ORDER BY fk_col.colseq");
                return this.executeAndExtract(CachingDatabaseMetaData.this.database, sql.toString(), parameters.toArray());
            }

            private String getDB2ForAs400Sql(String jdbcSchemaName, String tableName) {
                return "SELECT pktable_cat, pktable_name, pkcolumn_name, fktable_cat, fktable_name, fkcolumn_name, key_seq, update_rule, delete_rule, fk_name, pk_name, deferrability FROM sysibm.SQLFORKEYS WHERE FKTABLE_SCHEM = '" + jdbcSchemaName + "' AND FKTABLE_NAME = '" + tableName + "'";
            }

            protected List<CachedRow> queryDb2Zos(CatalogAndSchema catalogAndSchema, String tableName) throws DatabaseException, SQLException {
                ArrayList<String> parameters = new ArrayList<String>(2);
                StringBuilder sql = new StringBuilder("SELECT    ref.REFTBCREATOR AS pktable_cat,    ref.REFTBNAME as pktable_name,    pk_col.colname as pkcolumn_name,   ref.CREATOR as fktable_cat,    ref.TBNAME as fktable_name,    fk_col.colname as fkcolumn_name,   fk_col.colseq as key_seq,    decode (ref.deleterule, 'A', 3, 'C', 0, 'N', 2, 'R', 1, 1) as delete_rule,    ref.relname as fk_name,    pk_col.colname as pk_name,    7 as deferrability  FROM SYSIBM.SYSRELS ref join SYSIBM.SYSFOREIGNKEYS fk_col on ref.relname = fk_col.RELNAME and ref.CREATOR = fk_col.CREATOR and ref.TBNAME = fk_col.TBNAME join SYSIBM.SYSKEYCOLUSE pk_col on ref.REFTBCREATOR = pk_col.TBCREATOR and ref.REFTBNAME = pk_col.TBNAME and pk_col.colseq=fk_col.colseq WHERE ref.CREATOR = ? ");
                parameters.add(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema));
                if (tableName != null) {
                    sql.append("AND ref.TBNAME = ? ");
                    parameters.add(tableName);
                }
                sql.append("ORDER BY fk_col.colseq");
                return this.executeAndExtract(CachingDatabaseMetaData.this.database, sql.toString(), parameters.toArray());
            }

            @Override
            protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                if (CachingDatabaseMetaData.this.database instanceof AbstractDb2Database || CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                    return super.shouldBulkSelect(schemaKey, resultSetCache);
                }
                return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
            }
        }

        private class GetColumnResultSetCache
        extends ResultSetCache.SingleResultSetExtractor {
            final String catalogName;
            final String schemaName;
            final String tableName;
            final String columnName;

            private GetColumnResultSetCache(Database database, String catalogName, String schemaName, String tableName, String columnName) {
                super(database);
                this.catalogName = catalogName;
                this.schemaName = schemaName;
                this.tableName = tableName;
                this.columnName = columnName;
            }

            @Override
            public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                return new ResultSetCache.RowData(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEM"), CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"), row.getString("COLUMN_NAME"));
            }

            @Override
            public ResultSetCache.RowData wantedKeyParameters() {
                return new ResultSetCache.RowData(this.catalogName, this.schemaName, CachingDatabaseMetaData.this.database, this.tableName, this.columnName);
            }

            @Override
            public boolean bulkContainsSchema(String schemaKey) {
                String catalogs = JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData();
                return catalogs != null && schemaKey != null && catalogs.contains("'" + schemaKey.toUpperCase() + "'") && CachingDatabaseMetaData.this.database instanceof OracleDatabase;
            }

            @Override
            public String getSchemaKey(CachedRow row) {
                return row.getString("TABLE_SCHEM");
            }

            @Override
            protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                LiquibaseTableNamesFactory liquibaseTableNamesFactory = Scope.getCurrentScope().getSingleton(LiquibaseTableNamesFactory.class);
                List<String> liquibaseTableNames = liquibaseTableNamesFactory.getLiquibaseTableNames(CachingDatabaseMetaData.this.database);
                return liquibaseTableNames.stream().noneMatch(this.tableName::equalsIgnoreCase);
            }

            @Override
            public List<CachedRow> fastFetchQuery() throws SQLException, DatabaseException {
                if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                    return this.oracleQuery(false);
                }
                if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                    return this.mssqlQuery(false);
                }
                CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                try {
                    List<CachedRow> returnList = this.extract(CachingDatabaseMetaData.this.databaseMetaData.getColumns(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema), JdbcDatabaseSnapshot.this.escapeForLike(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema), CachingDatabaseMetaData.this.database), JdbcDatabaseSnapshot.this.escapeForLike(this.tableName, CachingDatabaseMetaData.this.database), CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL));
                    this.determineActualDataTypes(returnList, this.tableName);
                    return returnList;
                }
                catch (SQLException e) {
                    if (this.shouldReturnEmptyColumns(e)) {
                        return new ArrayList<CachedRow>();
                    }
                    throw e;
                }
            }

            @Override
            public List<CachedRow> bulkFetchQuery() throws SQLException, DatabaseException {
                if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                    return this.oracleQuery(true);
                }
                if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase) {
                    return this.mssqlQuery(true);
                }
                CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                try {
                    List<CachedRow> returnList = this.extract(CachingDatabaseMetaData.this.databaseMetaData.getColumns(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcCatalogName(catalogAndSchema), JdbcDatabaseSnapshot.this.escapeForLike(((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema), CachingDatabaseMetaData.this.database), CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL, CachingDatabaseMetaData.SQL_FILTER_MATCH_ALL));
                    this.determineActualDataTypes(returnList, null);
                    return returnList;
                }
                catch (SQLException e) {
                    if (this.shouldReturnEmptyColumns(e)) {
                        return new ArrayList<CachedRow>();
                    }
                    throw e;
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void determineActualDataTypes(List<CachedRow> returnList, String tableName) throws SQLException {
                if (!(CachingDatabaseMetaData.this.database instanceof MariaDBDatabase) && !(CachingDatabaseMetaData.this.database instanceof SybaseASADatabase)) {
                    return;
                }
                if (CachingDatabaseMetaData.this.database instanceof SybaseASADatabase) {
                    String selectStatement = "SELECT table_name, column_name, scale, column_type FROM SYSTABCOL KEY JOIN SYSTAB KEY JOIN SYSUSER WHERE user_name = ? AND ? IS NULL OR table_name = ?";
                    Connection underlyingConnection = ((JdbcConnection)CachingDatabaseMetaData.this.database.getConnection()).getUnderlyingConnection();
                    try (PreparedStatement stmt = underlyingConnection.prepareStatement(selectStatement);){
                        stmt.setString(1, this.schemaName);
                        stmt.setString(2, tableName);
                        stmt.setString(3, tableName);
                        try (ResultSet columnSelectRS = stmt.executeQuery();){
                            block17: while (columnSelectRS.next()) {
                                String selectedTableName = columnSelectRS.getString("table_name");
                                String selectedColumnName = columnSelectRS.getString("column_name");
                                int selectedScale = columnSelectRS.getInt("scale");
                                String selectedColumnType = columnSelectRS.getString("column_type");
                                for (CachedRow row : returnList) {
                                    String rowTableName = row.getString("TABLE_NAME");
                                    String rowColumnName = row.getString("COLUMN_NAME");
                                    if (!rowTableName.equalsIgnoreCase(selectedTableName) || !rowColumnName.equalsIgnoreCase(selectedColumnName)) continue;
                                    int rowDataType = row.getInt("DATA_TYPE");
                                    if (rowDataType == 12 || rowDataType == 1) {
                                        row.set("scale", selectedScale);
                                    }
                                    row.set("IS_GENERATEDCOLUMN", "C".equals(selectedColumnType) ? "YES" : "NO");
                                    continue block17;
                                }
                            }
                        }
                    }
                    catch (SQLException sqle) {
                        throw new RuntimeException(sqle);
                    }
                    return;
                }
                StringBuilder selectStatement = new StringBuilder("SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ?");
                if (tableName != null) {
                    selectStatement.append(" AND TABLE_NAME = ?");
                }
                Connection underlyingConnection = ((JdbcConnection)CachingDatabaseMetaData.this.database.getConnection()).getUnderlyingConnection();
                PreparedStatement statement = underlyingConnection.prepareStatement(selectStatement.toString());
                statement.setString(1, this.schemaName);
                if (tableName != null) {
                    statement.setString(2, tableName);
                }
                try {
                    ResultSet columnSelectRS = statement.executeQuery(selectStatement.toString());
                    block19: while (columnSelectRS.next()) {
                        String selectedTableName = columnSelectRS.getString("TABLE_NAME");
                        String selectedColumnName = columnSelectRS.getString("COLUMN_NAME");
                        String actualDataType = columnSelectRS.getString("DATA_TYPE");
                        for (CachedRow row : returnList) {
                            String rowTableName = row.getString("TABLE_NAME");
                            String rowColumnName = row.getString("COLUMN_NAME");
                            String rowTypeName = row.getString("TYPE_NAME");
                            int rowDataType = row.getInt("DATA_TYPE");
                            if (!rowTableName.equalsIgnoreCase(selectedTableName) || !rowColumnName.equalsIgnoreCase(selectedColumnName) || !rowTypeName.equalsIgnoreCase("datetime") || rowDataType != 1111 || rowTypeName.equalsIgnoreCase(actualDataType)) continue;
                            row.set("TYPE_NAME", actualDataType);
                            row.set("DATA_TYPE", 93);
                            continue block19;
                        }
                    }
                }
                catch (SQLException sQLException) {
                }
                finally {
                    JdbcUtil.closeStatement(statement);
                }
            }

            protected boolean shouldReturnEmptyColumns(SQLException e) {
                return e.getMessage().contains("references invalid table");
            }

            protected List<CachedRow> oracleQuery(boolean bulk) throws DatabaseException, SQLException {
                CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                String jdbcSchemaName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                boolean collectIdentityData = CachingDatabaseMetaData.this.database.getDatabaseMajorVersion() >= 12;
                String sql = "select NULL AS TABLE_CAT, OWNER AS TABLE_SCHEM, 'NO' as IS_AUTOINCREMENT, cc.COMMENTS AS REMARKS,OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE AS DATA_TYPE_NAME, DATA_TYPE_MOD, DATA_TYPE_OWNER, DECODE (c.data_type, 'CHAR', 1, 'VARCHAR2', 12, 'NUMBER', 3, 'LONG', -1, 'DATE', 93, 'RAW', -3, 'LONG RAW', -4, 'BLOB', 2004, 'CLOB', 2005, 'BFILE', -13, 'FLOAT', 6, 'TIMESTAMP(6)', 93, 'TIMESTAMP(6) WITH TIME ZONE', -101, 'TIMESTAMP(6) WITH LOCAL TIME ZONE', -102, 'INTERVAL YEAR(2) TO MONTH', -103, 'INTERVAL DAY(2) TO SECOND(6)', -104, 'BINARY_FLOAT', 100, 'BINARY_DOUBLE', 101, 'XMLTYPE', 2009, 1111) AS data_type, DECODE( CHAR_USED, 'C',CHAR_LENGTH, DATA_LENGTH ) as DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, COLUMN_ID as ORDINAL_POSITION, DEFAULT_LENGTH, DATA_DEFAULT, NUM_BUCKETS, CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH, CHAR_LENGTH, CHAR_USED, VIRTUAL_COLUMN ";
                if (collectIdentityData) {
                    sql = sql + ", DEFAULT_ON_NULL, IDENTITY_COLUMN, ic.GENERATION_TYPE ";
                }
                sql = sql + "FROM ALL_TAB_COLS c JOIN ALL_COL_COMMENTS cc USING ( OWNER, TABLE_NAME, COLUMN_NAME ) ";
                if (collectIdentityData) {
                    sql = sql + "LEFT JOIN ALL_TAB_IDENTITY_COLS ic USING (OWNER, TABLE_NAME, COLUMN_NAME ) ";
                }
                sql = !bulk || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sql + "WHERE OWNER='" + jdbcSchemaName + "' AND hidden_column='NO'" : sql + "WHERE OWNER IN ('" + jdbcSchemaName + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ") AND hidden_column='NO'";
                if (!bulk) {
                    if (this.tableName != null) {
                        sql = sql + " AND TABLE_NAME='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(this.tableName) + "'";
                    }
                    if (this.columnName != null) {
                        sql = sql + " AND COLUMN_NAME='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(this.columnName) + "'";
                    }
                }
                sql = sql + " AND " + ((OracleDatabase)CachingDatabaseMetaData.this.database).getSystemTableWhereClause("TABLE_NAME");
                sql = sql + " ORDER BY OWNER, TABLE_NAME, c.COLUMN_ID";
                return this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
            }

            protected List<CachedRow> mssqlQuery(boolean bulk) throws DatabaseException, SQLException {
                String dbIdParam;
                String databasePrefix;
                CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                String databaseName = StringUtil.trimToNull(CachingDatabaseMetaData.this.database.correctObjectName(catalogAndSchema.getCatalogName(), Catalog.class));
                if (databaseName == null) {
                    databasePrefix = "";
                    dbIdParam = "";
                } else {
                    dbIdParam = ", db_id('" + databaseName + "')";
                    databasePrefix = "[" + databaseName + "].";
                }
                String sql = "select db_name(" + (databaseName == null ? "" : "db_id('" + databaseName + "')") + ") AS TABLE_CAT, object_schema_name(c.object_id" + dbIdParam + ") AS TABLE_SCHEM, object_name(c.object_id" + dbIdParam + ") AS TABLE_NAME, c.name AS COLUMN_NAME, is_filestream AS IS_FILESTREAM, is_rowguidcol AS IS_ROWGUIDCOL, CASE WHEN c.is_identity = 'true' THEN 'YES' ELSE 'NO' END as IS_AUTOINCREMENT, {REMARKS_COLUMN_PLACEHOLDER}t.name AS TYPE_NAME, dc.name as COLUMN_DEF_NAME, dc.definition as COLUMN_DEF, CASE t.name WHEN 'bigint' THEN " + -5 + " WHEN 'binary' THEN " + -2 + " WHEN 'bit' THEN " + -7 + " WHEN 'char' THEN " + 1 + " WHEN 'date' THEN " + 91 + " WHEN 'datetime' THEN " + 93 + " WHEN 'datetime2' THEN " + 93 + " WHEN 'datetimeoffset' THEN -155 WHEN 'decimal' THEN " + 3 + " WHEN 'float' THEN " + 8 + " WHEN 'image' THEN " + -4 + " WHEN 'int' THEN " + 4 + " WHEN 'money' THEN " + 3 + " WHEN 'nchar' THEN " + -15 + " WHEN 'ntext' THEN " + -16 + " WHEN 'numeric' THEN " + 2 + " WHEN 'nvarchar' THEN " + -9 + " WHEN 'real' THEN " + 7 + " WHEN 'smalldatetime' THEN " + 93 + " WHEN 'smallint' THEN " + 5 + " WHEN 'smallmoney' THEN " + 3 + " WHEN 'text' THEN " + -1 + " WHEN 'time' THEN " + 92 + " WHEN 'timestamp' THEN " + -2 + " WHEN 'tinyint' THEN " + -6 + " WHEN 'udt' THEN " + -3 + " WHEN 'uniqueidentifier' THEN " + 1 + " WHEN 'varbinary' THEN " + -3 + " WHEN 'varbinary(max)' THEN " + -3 + " WHEN 'varchar' THEN " + 12 + " WHEN 'varchar(max)' THEN " + 12 + " WHEN 'xml' THEN " + -1 + " WHEN 'LONGNVARCHAR' THEN " + 2009 + " ELSE " + 1111 + " END AS DATA_TYPE, CASE WHEN c.is_nullable = 'true' THEN 1 ELSE 0 END AS NULLABLE, 10 as NUM_PREC_RADIX, c.column_id as ORDINAL_POSITION, c.scale as DECIMAL_DIGITS, c.max_length as COLUMN_SIZE, c.precision as DATA_PRECISION, c.is_computed as IS_COMPUTED FROM " + databasePrefix + "sys.columns c inner join " + databasePrefix + "sys.types t on c.user_type_id=t.user_type_id {REMARKS_JOIN_PLACEHOLDER}left outer join " + databasePrefix + "sys.default_constraints dc on dc.parent_column_id = c.column_id AND dc.parent_object_id=c.object_id AND type_desc='DEFAULT_CONSTRAINT' WHERE object_schema_name(c.object_id" + dbIdParam + ")='" + ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema) + "'";
                if (!bulk) {
                    if (this.tableName != null) {
                        sql = sql + " and object_name(c.object_id" + dbIdParam + ")='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(this.tableName) + "'";
                    }
                    if (this.columnName != null) {
                        sql = sql + " and c.name='" + CachingDatabaseMetaData.this.database.escapeStringForDatabase(this.columnName) + "'";
                    }
                }
                sql = sql + "order by object_schema_name(c.object_id" + dbIdParam + "), object_name(c.object_id" + dbIdParam + "), c.column_id";
                if (!((MSSQLDatabase)CachingDatabaseMetaData.this.database).isAzureDb() || CachingDatabaseMetaData.this.database.getDatabaseMajorVersion() >= 12) {
                    sql = sql.replace("{REMARKS_COLUMN_PLACEHOLDER}", "CAST([ep].[value] AS [nvarchar](MAX)) AS [REMARKS], ");
                    sql = sql.replace("{REMARKS_JOIN_PLACEHOLDER}", "left outer join " + databasePrefix + "[sys].[extended_properties] AS [ep] ON [ep].[class] = 1 AND [ep].[major_id] = c.object_id AND [ep].[minor_id] = column_id AND [ep].[name] = 'MS_Description' ");
                } else {
                    sql = sql.replace("{REMARKS_COLUMN_PLACEHOLDER}", "");
                    sql = sql.replace("{REMARKS_JOIN_PLACEHOLDER}", "");
                }
                List<CachedRow> rows = this.executeAndExtract(sql, CachingDatabaseMetaData.this.database);
                for (CachedRow row : rows) {
                    String typeName = row.getString("TYPE_NAME");
                    if ("nvarchar".equals(typeName) || "nchar".equals(typeName)) {
                        Integer size = row.getInt("COLUMN_SIZE");
                        if (size <= 0) continue;
                        row.set("COLUMN_SIZE", size / 2);
                        continue;
                    }
                    if (row.getInt("DATA_PRECISION") == null || row.getInt("DATA_PRECISION") <= 0) continue;
                    row.set("COLUMN_SIZE", row.getInt("DATA_PRECISION"));
                }
                return rows;
            }

            @Override
            protected List<CachedRow> extract(ResultSet resultSet, boolean informixIndexTrimHint) throws SQLException {
                List<CachedRow> rows = super.extract(resultSet, informixIndexTrimHint);
                if (CachingDatabaseMetaData.this.database instanceof MSSQLDatabase && !JdbcDatabaseSnapshot.this.userDefinedTypes.isEmpty()) {
                    for (CachedRow row : rows) {
                        String dataType = (String)row.get("TYPE_NAME");
                        if (!JdbcDatabaseSnapshot.this.userDefinedTypes.contains(dataType.toLowerCase())) continue;
                        row.set("COLUMN_SIZE", null);
                        row.set("DECIMAL_DIGITS ", null);
                    }
                }
                return rows;
            }
        }

        private class GetNotNullConstraintsResultSetCache
        extends ResultSetCache.SingleResultSetExtractor {
            final String catalogName;
            final String schemaName;
            final String tableName;

            private GetNotNullConstraintsResultSetCache(Database database, String catalogName, String schemaName, String tableName) {
                super(database);
                this.catalogName = catalogName;
                this.schemaName = schemaName;
                this.tableName = tableName;
            }

            @Override
            public ResultSetCache.RowData rowKeyParameters(CachedRow row) {
                return new ResultSetCache.RowData(row.getString("TABLE_CAT"), row.getString("TABLE_SCHEMA"), CachingDatabaseMetaData.this.database, row.getString("TABLE_NAME"));
            }

            @Override
            public ResultSetCache.RowData wantedKeyParameters() {
                return new ResultSetCache.RowData(this.catalogName, this.schemaName, CachingDatabaseMetaData.this.database, this.tableName);
            }

            @Override
            public boolean bulkContainsSchema(String schemaKey) {
                return CachingDatabaseMetaData.this.database instanceof OracleDatabase;
            }

            @Override
            public String getSchemaKey(CachedRow row) {
                return row.getString("TABLE_SCHEMA");
            }

            @Override
            protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
                LiquibaseTableNamesFactory liquibaseTableNamesFactory = Scope.getCurrentScope().getSingleton(LiquibaseTableNamesFactory.class);
                List<String> liquibaseTableNames = liquibaseTableNamesFactory.getLiquibaseTableNames(CachingDatabaseMetaData.this.database);
                return liquibaseTableNames.stream().noneMatch(this.tableName::equalsIgnoreCase);
            }

            @Override
            public List<CachedRow> fastFetchQuery() throws SQLException, DatabaseException {
                if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                    return this.oracleQuery(false);
                }
                return Collections.emptyList();
            }

            @Override
            public List<CachedRow> bulkFetchQuery() throws SQLException, DatabaseException {
                if (CachingDatabaseMetaData.this.database instanceof OracleDatabase) {
                    return this.oracleQuery(true);
                }
                return Collections.emptyList();
            }

            private List<CachedRow> oracleQuery(boolean bulk) throws DatabaseException, SQLException {
                CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.catalogName, this.schemaName).customize(CachingDatabaseMetaData.this.database);
                String jdbcSchemaName = ((AbstractJdbcDatabase)CachingDatabaseMetaData.this.database).getJdbcSchemaName(catalogAndSchema);
                String jdbcTableName = CachingDatabaseMetaData.this.database.escapeStringForDatabase(this.tableName);
                String sqlToSelectNotNullConstraints = "SELECT  NULL AS TABLE_CAT, atc.OWNER AS TABLE_SCHEMA, atc.OWNER, atc.TABLE_NAME, atc.COLUMN_NAME, NULLABLE, ac.VALIDATED as VALIDATED, ac.SEARCH_CONDITION, ac.CONSTRAINT_NAME FROM ALL_TAB_COLS atc JOIN all_cons_columns acc ON atc.OWNER = acc.OWNER AND atc.TABLE_NAME = acc.TABLE_NAME AND atc.COLUMN_NAME = acc.COLUMN_NAME JOIN all_constraints ac ON atc.OWNER = ac.OWNER AND atc.TABLE_NAME = ac.TABLE_NAME AND acc.CONSTRAINT_NAME = ac.CONSTRAINT_NAME ";
                sqlToSelectNotNullConstraints = !bulk || JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() == null ? sqlToSelectNotNullConstraints + " WHERE atc.OWNER='" + jdbcSchemaName + "' AND atc.hidden_column='NO' AND ac.CONSTRAINT_TYPE='C'  and ac.search_condition is not null " : sqlToSelectNotNullConstraints + " WHERE atc.OWNER IN ('" + jdbcSchemaName + "', " + JdbcDatabaseSnapshot.this.getAllCatalogsStringScratchData() + ")  AND atc.hidden_column='NO' AND ac.CONSTRAINT_TYPE='C'  and ac.search_condition is not null ";
                sqlToSelectNotNullConstraints = sqlToSelectNotNullConstraints + (!bulk && this.tableName != null && !this.tableName.isEmpty() ? " AND atc.TABLE_NAME='" + jdbcTableName + "'" : "");
                return this.executeAndExtract(sqlToSelectNotNullConstraints, CachingDatabaseMetaData.this.database);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            protected List<CachedRow> extract(ResultSet resultSet, boolean informixIndexTrimHint) throws SQLException {
                ArrayList<CachedRow> cachedRowList = new ArrayList<CachedRow>();
                if (!(CachingDatabaseMetaData.this.database instanceof OracleDatabase)) {
                    return cachedRowList;
                }
                resultSet.setFetchSize(CachingDatabaseMetaData.this.database.getFetchSize());
                try {
                    List result = (List)new RowMapperNotNullConstraintsResultSetExtractor(new ColumnMapRowMapper(CachingDatabaseMetaData.this.database.isCaseSensitive()){

                        @Override
                        protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
                            Object value = super.getColumnValue(rs, index);
                            if (!(value instanceof String)) {
                                return value;
                            }
                            return value.toString().trim();
                        }
                    }).extractData(resultSet);
                    for (Map row : result) {
                        cachedRowList.add(new CachedRow(row));
                    }
                }
                finally {
                    JdbcUtil.closeResultSet(resultSet);
                }
                return cachedRowList;
            }
        }
    }
}

