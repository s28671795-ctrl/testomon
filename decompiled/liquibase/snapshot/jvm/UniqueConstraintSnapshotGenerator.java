/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.Scope;
import liquibase.database.Database;
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
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.core.RawParameterizedSqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class UniqueConstraintSnapshotGenerator
extends JdbcSnapshotGenerator {
    public UniqueConstraintSnapshotGenerator() {
        super(UniqueConstraint.class, new Class[]{Table.class});
    }

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof SQLiteDatabase) {
            return -1;
        }
        return super.getPriority(objectType, database);
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException {
        Database database = snapshot.getDatabase();
        UniqueConstraint exampleConstraint = (UniqueConstraint)example;
        Relation table = exampleConstraint.getRelation();
        List<Map<String, ?>> metadata = this.listColumns(exampleConstraint, database, snapshot);
        if (metadata.isEmpty()) {
            return null;
        }
        UniqueConstraint constraint = new UniqueConstraint();
        constraint.setRelation(table);
        constraint.setName(example.getName());
        constraint.setBackingIndex(exampleConstraint.getBackingIndex());
        constraint.setInitiallyDeferred(((UniqueConstraint)example).isInitiallyDeferred());
        constraint.setDeferrable(((UniqueConstraint)example).isDeferrable());
        constraint.setClustered(((UniqueConstraint)example).isClustered());
        for (Map<String, ?> col : metadata) {
            Boolean descending;
            String ascOrDesc = (String)col.get("ASC_OR_DESC");
            Boolean bl = "D".equals(ascOrDesc) ? Boolean.TRUE : (descending = "A".equals(ascOrDesc) ? Boolean.FALSE : null);
            if (database instanceof H2Database) {
                for (String columnName : StringUtil.splitAndTrim((String)col.get("COLUMN_NAME"), ",")) {
                    constraint.getColumns().add(new Column(columnName).setDescending(descending).setRelation(table));
                }
            } else {
                constraint.getColumns().add(new Column((String)col.get("COLUMN_NAME")).setDescending(descending).setRelation(table));
            }
            this.setValidateOptionIfAvailable(database, constraint, col);
        }
        return constraint;
    }

    private void setValidateOptionIfAvailable(Database database, UniqueConstraint uniqueConstraint, Map<String, ?> columnsMetadata) {
        if (!(database instanceof OracleDatabase)) {
            return;
        }
        Object constraintValidate = columnsMetadata.get("CONSTRAINT_VALIDATE");
        String VALIDATE = "VALIDATED";
        if (constraintValidate != null && !constraintValidate.toString().trim().isEmpty()) {
            uniqueConstraint.setShouldValidate("VALIDATED".equals(this.cleanNameFromDatabase(constraintValidate.toString().trim(), database)));
        }
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException {
        if (!snapshot.getSnapshotControl().shouldInclude(UniqueConstraint.class) || !snapshot.getDatabase().supports(UniqueConstraint.class)) {
            return;
        }
        if (foundObject instanceof Table) {
            List<CachedRow> metadata;
            Table table = (Table)foundObject;
            Database database = snapshot.getDatabase();
            Schema schema = table.getSchema();
            try {
                metadata = this.listConstraints(table, snapshot, schema);
            }
            catch (SQLException e) {
                throw new DatabaseException(e);
            }
            HashSet<String> seenConstraints = new HashSet<String>();
            for (CachedRow constraint : metadata) {
                UniqueConstraint uq = new UniqueConstraint().setName(this.cleanNameFromDatabase((String)constraint.get("CONSTRAINT_NAME"), database)).setRelation(table);
                if (constraint.containsColumn("INDEX_NAME")) {
                    uq.setBackingIndex(new Index((String)constraint.get("INDEX_NAME"), (String)constraint.get("INDEX_CATALOG"), (String)constraint.get("INDEX_SCHEMA"), table.getName(), new Column[0]));
                }
                if ("CLUSTERED".equals(constraint.get("TYPE_DESC"))) {
                    uq.setClustered(true);
                }
                if (!seenConstraints.add(uq.getName())) continue;
                table.getUniqueConstraints().add(uq);
            }
        }
    }

    protected List<CachedRow> listConstraints(Table table, DatabaseSnapshot snapshot, Schema schema) throws DatabaseException, SQLException {
        return ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache().getUniqueConstraints(schema.getCatalogName(), schema.getName(), table.getName());
    }

    protected List<Map<String, ?>> listColumns(UniqueConstraint example, Database database, DatabaseSnapshot snapshot) throws DatabaseException {
        Relation table = example.getRelation();
        Schema schema = example.getSchema();
        String name = example.getName();
        String cacheKey = "uniqueConstraints-" + example.getClass().getSimpleName() + "-" + example.getSchema().toCatalogAndSchema().customize(database).toString();
        String queryCountKey = "uniqueConstraints-" + example.getClass().getSimpleName() + "-queryCount";
        ConcurrentHashMap<String, List> columnCache = (ConcurrentHashMap<String, List>)snapshot.getScratchData(cacheKey);
        Integer columnQueryCount = (Integer)snapshot.getScratchData(queryCountKey);
        if (columnQueryCount == null) {
            columnQueryCount = 0;
        }
        if (columnCache == null) {
            ArrayList<String> parameter;
            String sql;
            String sql2;
            AbstractSqlStatement stmt;
            boolean bulkQuery = false;
            if (columnQueryCount > 3) {
                bulkQuery = this.supportsBulkQuery(database);
            }
            snapshot.setScratchData(queryCountKey, columnQueryCount + 1);
            if (database instanceof MySQLDatabase || database instanceof HsqlDatabase) {
                String sql22 = "select const.CONSTRAINT_NAME, const.TABLE_NAME, COLUMN_NAME, const.constraint_schema as CONSTRAINT_CONTAINER from " + database.getSystemSchema() + ".table_constraints const join " + database.getSystemSchema() + ".key_column_usage col on const.constraint_schema=col.constraint_schema and const.table_name=col.table_name and const.constraint_name=col.constraint_name where const.constraint_schema='" + database.correctObjectName(schema.getSchema().getName(), Schema.class) + "' ";
                if (!bulkQuery) {
                    sql22 = sql22 + "and const.table_name='" + database.correctObjectName(example.getRelation().getName(), Table.class) + "' and const.constraint_name='" + database.correctObjectName(name, UniqueConstraint.class) + "'";
                }
                sql22 = sql22 + "order by ordinal_position";
                stmt = new RawSqlStatement(sql22);
            } else if (database instanceof PostgresDatabase) {
                ArrayList<String> conditions = new ArrayList<String>();
                sql2 = "select const.CONSTRAINT_NAME, COLUMN_NAME, const.constraint_schema as CONSTRAINT_CONTAINER from " + database.getSystemSchema() + ".table_constraints const join " + database.getSystemSchema() + ".key_column_usage col on const.constraint_schema=col.constraint_schema and const.table_name=col.table_name and const.constraint_name=col.constraint_name ";
                if (schema.getCatalogName() != null) {
                    conditions.add("const.constraint_catalog='" + database.correctObjectName(schema.getCatalogName(), Catalog.class) + "'");
                }
                if (database instanceof CockroachDatabase) {
                    conditions.add("(select count(*) from (select indexdef from pg_indexes where schemaname='\" + database.correctObjectName(schema.getSchema().getName(), Schema.class) + \"' AND indexname='\" + database.correctObjectName(name, UniqueConstraint.class) + \"' AND (position('DESC,' in indexdef) > 0 OR position('DESC)' in indexdef) > 0))) = 0");
                    conditions.add("const.constraint_name != 'primary'");
                }
                if (schema.getSchema().getName() != null) {
                    conditions.add("const.constraint_schema='" + database.correctObjectName(schema.getSchema().getName(), Schema.class) + "'");
                }
                if (!bulkQuery) {
                    conditions.add("const.table_name='" + database.correctObjectName(example.getRelation().getName(), Table.class) + "'");
                    if (name != null) {
                        conditions.add("const.constraint_name='" + database.correctObjectName(name, UniqueConstraint.class) + "' ");
                    }
                }
                if (!conditions.isEmpty()) {
                    sql2 = sql2 + " WHERE ";
                    sql2 = sql2 + String.join((CharSequence)" AND ", conditions);
                }
                sql2 = sql2 + " order by ordinal_position";
                stmt = new RawSqlStatement(sql2);
            } else if (database.getClass().getName().contains("MaxDB")) {
                sql = "select CONSTRAINTNAME as constraint_name, COLUMNNAME as column_name from CONSTRAINTCOLUMNS WHERE CONSTRAINTTYPE = 'UNIQUE_CONST' AND tablename = '" + database.correctObjectName(example.getRelation().getName(), Table.class) + "' AND constraintname = '" + database.correctObjectName(name, UniqueConstraint.class) + "'";
                stmt = new RawSqlStatement(sql);
            } else if (database instanceof MSSQLDatabase) {
                sql = "SELECT [kc].[name] AS [CONSTRAINT_NAME], s.name AS constraint_container, [c].[name] AS [COLUMN_NAME], CASE [ic].[is_descending_key] WHEN 0 THEN N'A' WHEN 1 THEN N'D' END AS [ASC_OR_DESC] FROM [sys].[schemas] AS [s] INNER JOIN [sys].[tables] AS [t] ON [t].[schema_id] = [s].[schema_id] INNER JOIN [sys].[key_constraints] AS [kc] ON [kc].[parent_object_id] = [t].[object_id] INNER JOIN [sys].[indexes] AS [i] ON [i].[object_id] = [kc].[parent_object_id] AND [i].[index_id] = [kc].[unique_index_id] INNER JOIN [sys].[index_columns] AS [ic] ON [ic].[object_id] = [i].[object_id] AND [ic].[index_id] = [i].[index_id] INNER JOIN [sys].[columns] AS [c] ON [c].[object_id] = [ic].[object_id] AND [c].[column_id] = [ic].[column_id] WHERE [s].[name] = N'" + database.escapeStringForDatabase(database.correctObjectName(schema.getName(), Schema.class)) + "' ";
                if (!bulkQuery) {
                    sql = sql + "AND [t].[name] = N'" + database.escapeStringForDatabase(database.correctObjectName(example.getRelation().getName(), Table.class)) + "' AND [kc].[name] = N'" + database.escapeStringForDatabase(database.correctObjectName(name, UniqueConstraint.class)) + "' ";
                }
                sql = sql + "ORDER BY [ic].[key_ordinal]";
                stmt = new RawSqlStatement(sql);
            } else if (database instanceof OracleDatabase) {
                sql = "select ucc.owner as constraint_container, ucc.constraint_name as constraint_name, ucc.column_name, f.validated as constraint_validate, ucc.table_name from all_cons_columns ucc INNER JOIN all_constraints f ON ucc.owner = f.owner AND ucc.constraint_name = f.constraint_name where " + (bulkQuery ? "" : "ucc.constraint_name='" + database.correctObjectName(name, UniqueConstraint.class) + "' and ") + "ucc.owner='" + database.correctObjectName(schema.getCatalogName(), Catalog.class) + "' and ucc.table_name not like 'BIN$%' order by ucc.position";
                stmt = new RawSqlStatement(sql);
            } else if (database instanceof DB2Database) {
                if (database.getDatabaseProductName().startsWith("DB2 UDB for AS/400")) {
                    sql = "select T1.constraint_name as CONSTRAINT_NAME, T2.COLUMN_NAME as COLUMN_NAME, T1.CONSTRAINT_SCHEMA as CONSTRAINT_CONTAINER from QSYS2.TABLE_CONSTRAINTS T1, QSYS2.SYSCSTCOL T2\nwhere T1.CONSTRAINT_TYPE='UNIQUE' and T1.CONSTRAINT_NAME=T2.CONSTRAINT_NAME\nand T1.CONSTRAINT_SCHEMA='" + database.correctObjectName(schema.getName(), Schema.class) + "'\nand T2.CONSTRAINT_SCHEMA='" + database.correctObjectName(schema.getName(), Schema.class) + "'\norder by T2.COLUMN_NAME\n";
                    stmt = new RawSqlStatement(sql);
                } else {
                    parameter = new ArrayList(2);
                    sql2 = "select k.constname as constraint_name, k.colname as column_name from syscat.keycoluse k, syscat.tabconst t where k.constname = t.constname and k.tabschema = t.tabschema and t.type = 'U' " + (bulkQuery ? "" : "and k.constname = ? ") + "and t.tabschema = ? order by colseq";
                    if (!bulkQuery) {
                        parameter.add(database.correctObjectName(name, UniqueConstraint.class));
                    }
                    parameter.add(database.correctObjectName(schema.getName(), Schema.class));
                    stmt = new RawParameterizedSqlStatement(sql2, parameter.toArray());
                }
            } else if (database instanceof Db2zDatabase) {
                parameter = new ArrayList<String>(2);
                sql2 = "select  KC.colname as column_name  from SYSIBM.SYSKEYCOLUSE KC inner join SYSIBM.SYSTABCONST TC on KC.CONSTNAME = TC.CONSTNAME and KC.TBCREATOR = TC.TBCREATOR and KC.TBNAME = TC.TBNAME where TC.TYPE = 'U'" + (bulkQuery ? "" : " and TC.CONSTNAME = ?") + " and TC.TBCREATOR = ? order by KC.COLSEQ";
                if (!bulkQuery) {
                    parameter.add(database.correctObjectName(name, UniqueConstraint.class));
                }
                parameter.add(database.correctObjectName(schema.getName(), Schema.class));
                stmt = new RawParameterizedSqlStatement(sql2, parameter.toArray());
            } else {
                String tableName;
                Object schemaName;
                String catalogName;
                if (database instanceof DerbyDatabase) {
                    String sql4 = "SELECT cg.descriptor as descriptor, t.tablename FROM sys.sysconglomerates cg JOIN sys.syskeys k ON cg.conglomerateid = k.conglomerateid JOIN sys.sysconstraints c ON c.constraintid = k.constraintid JOIN sys.systables t ON c.tableid = t.tableid WHERE c.constraintname='" + database.correctObjectName(name, UniqueConstraint.class) + "'";
                    List<Map<String, ?>> rows = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(new RawSqlStatement(sql4));
                    ArrayList arrayList = new ArrayList();
                    if (rows.isEmpty()) {
                        return arrayList;
                    }
                    if (rows.size() > 1) {
                        throw new UnexpectedLiquibaseException("Got multiple rows back querying unique constraints");
                    }
                    Map<String, ?> rowData = rows.get(0);
                    String descriptor = rowData.get("DESCRIPTOR").toString();
                    descriptor = descriptor.replaceFirst(".*\\(", "").replaceFirst("\\).*", "");
                    for (String columnNumber : StringUtil.splitAndTrim(descriptor, ",")) {
                        String columnName = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForObject(new RawSqlStatement("select c.columnname from sys.syscolumns c join sys.systables t on t.tableid=c.referenceid where t.tablename='" + rowData.get("TABLENAME") + "' and c.columnnumber=" + columnNumber), String.class);
                        HashMap<String, String> row = new HashMap<String, String>();
                        row.put("COLUMN_NAME", columnName);
                        arrayList.add(row);
                    }
                    return arrayList;
                }
                if (database instanceof FirebirdDatabase) {
                    sql = "SELECT TRIM(RDB$INDEX_SEGMENTS.RDB$FIELD_NAME) AS column_name FROM RDB$INDEX_SEGMENTS LEFT JOIN RDB$INDICES ON RDB$INDICES.RDB$INDEX_NAME = RDB$INDEX_SEGMENTS.RDB$INDEX_NAME WHERE UPPER(TRIM(RDB$INDICES.RDB$INDEX_NAME))='" + database.correctObjectName(name, UniqueConstraint.class) + "' ORDER BY RDB$INDEX_SEGMENTS.RDB$FIELD_POSITION";
                    stmt = new RawSqlStatement(sql);
                } else if (database instanceof SybaseDatabase) {
                    sql = "select soc.name as constraint_name, (select scl.name from dbo.syscolumns scl where scl.id = sc.tableid and scl.colid = sc.colid) as column_name from dbo.sysconstraints sc inner join dbo.sysobjects soc on soc.id = sc.constrid inner join dbo.sysobjects sot on sot.id = sc.tableid where sot.id = OBJECT_ID('" + database.correctObjectName(example.getRelation().getName(), Table.class) + "') and soc.id = OBJECT_ID('" + database.correctObjectName(name, UniqueConstraint.class) + "')";
                    stmt = new RawSqlStatement(sql);
                } else if (database instanceof SybaseASADatabase) {
                    sql = "select sysconstraint.constraint_name, syscolumn.column_name from sysconstraint, syscolumn, systable where sysconstraint.ref_object_id = syscolumn.object_id and sysconstraint.table_object_id = systable.object_id and sysconstraint.constraint_name = '" + database.correctObjectName(name, UniqueConstraint.class) + "' and systable.table_name = '" + database.correctObjectName(example.getRelation().getName(), Table.class) + "'";
                    stmt = new RawSqlStatement(sql);
                } else if (database instanceof Ingres9Database) {
                    sql = "select constraint_name, column_name from iikeys where constraint_name = '" + database.correctObjectName(name, UniqueConstraint.class) + "' and table_name = '" + database.correctObjectName(example.getTable().getName(), Table.class) + "'";
                    stmt = new RawSqlStatement(sql);
                } else if (database instanceof InformixDatabase) {
                    stmt = new RawSqlStatement(this.getUniqueConstraintsSqlInformix((InformixDatabase)database, schema, name));
                } else if (database instanceof H2Database && database.getDatabaseMajorVersion() >= 2) {
                    catalogName = database.correctObjectName(schema.getCatalogName(), Catalog.class);
                    schemaName = database.correctObjectName(schema.getName(), Schema.class);
                    String string = database.correctObjectName(name, UniqueConstraint.class);
                    tableName = database.correctObjectName(table.getName(), Table.class);
                    String sql3 = "select table_constraints.CONSTRAINT_NAME, index_columns.COLUMN_NAME, table_constraints.constraint_schema as CONSTRAINT_CONTAINER from information_schema.table_constraints join information_schema.index_columns on index_columns.index_name=table_constraints.index_name where constraint_type='UNIQUE' ";
                    if (catalogName != null) {
                        sql3 = sql3 + "and constraint_catalog='" + catalogName + "' ";
                    }
                    if (schemaName != null) {
                        sql3 = sql3 + "and constraint_schema='" + (String)schemaName + "' ";
                    }
                    if (!bulkQuery) {
                        if (tableName != null) {
                            sql3 = sql3 + "and table_constraints.table_name='" + tableName + "' ";
                        }
                        if (string != null) {
                            sql3 = sql3 + "and constraint_name='" + string + "'";
                        }
                    }
                    stmt = new RawSqlStatement(sql3);
                } else {
                    catalogName = database.correctObjectName(schema.getCatalogName(), Catalog.class);
                    schemaName = database.correctObjectName(schema.getName(), Schema.class);
                    String string = database.correctObjectName(name, UniqueConstraint.class);
                    tableName = database.correctObjectName(table.getName(), Table.class);
                    String sql3 = "select CONSTRAINT_NAME, COLUMN_LIST as COLUMN_NAME, constraint_schema as CONSTRAINT_CONTAINER from " + database.getSystemSchema() + ".constraints where constraint_type='UNIQUE' ";
                    if (catalogName != null) {
                        sql3 = sql3 + "and constraint_catalog='" + catalogName + "' ";
                    }
                    if (schemaName != null) {
                        sql3 = sql3 + "and constraint_schema='" + (String)schemaName + "' ";
                    }
                    if (!bulkQuery) {
                        if (tableName != null) {
                            sql3 = sql3 + "and table_name='" + tableName + "' ";
                        }
                        if (string != null) {
                            sql3 = sql3 + "and constraint_name='" + string + "'";
                        }
                    }
                    stmt = new RawSqlStatement(sql3);
                }
            }
            List<Map<String, ?>> rows = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(stmt);
            if (bulkQuery) {
                columnCache = new ConcurrentHashMap<String, List>();
                snapshot.setScratchData(cacheKey, columnCache);
                for (Map map : rows) {
                    String key = this.getCacheKey(map, database);
                    List constraintRows = columnCache.computeIfAbsent(key, k -> new ArrayList());
                    constraintRows.add(map);
                }
                return this.listColumns(example, database, snapshot);
            }
            return rows;
        }
        String lookupKey = this.getCacheKey(example, database);
        ArrayList rows = (ArrayList)columnCache.get(lookupKey);
        if (rows == null) {
            rows = new ArrayList();
        }
        return rows;
    }

    protected boolean includeTableNameInCacheKey(Database database) {
        return database instanceof MySQLDatabase;
    }

    protected String getCacheKey(UniqueConstraint example, Database database) {
        if (this.includeTableNameInCacheKey(database)) {
            return example.getSchema().getName() + "_" + example.getRelation() + "_" + example.getName();
        }
        return example.getSchema().getName() + "_" + example.getName();
    }

    protected String getCacheKey(Map<String, ?> row, Database database) {
        if (this.includeTableNameInCacheKey(database)) {
            return row.get("CONSTRAINT_CONTAINER") + "_" + row.get("TABLE_NAME") + "_" + row.get("CONSTRAINT_NAME");
        }
        return row.get("CONSTRAINT_CONTAINER") + "_" + row.get("CONSTRAINT_NAME");
    }

    protected boolean supportsBulkQuery(Database database) {
        return !(database instanceof DerbyDatabase) && !(database instanceof FirebirdDatabase) && !(database instanceof SybaseDatabase) && !(database instanceof SybaseASADatabase) && !(database instanceof Ingres9Database) && !(database instanceof InformixDatabase);
    }

    private String getUniqueConstraintsSqlInformix(InformixDatabase database, Schema schema, String name) {
        StringBuilder sqlBuf = new StringBuilder();
        sqlBuf.append("SELECT * FROM (\n");
        for (int i = 1; i <= 16; ++i) {
            if (i > 1) {
                sqlBuf.append("UNION ALL\n");
            }
            sqlBuf.append(String.format("  SELECT\n    CONS.owner,\n    CONS.constrname AS constraint_name,\n    COL.colname AS column_name,\n    CONS.constrtype,\n    %d               AS column_index\n  FROM informix.sysconstraints CONS\n    JOIN informix.sysindexes IDX ON CONS.idxname = IDX.idxname\n    JOIN informix.syscolumns COL ON COL.tabid = CONS.tabid AND COL.colno = IDX.part%d\n", i, i));
        }
        sqlBuf.append("                ) SUBQ\nWHERE constrtype='U' \n");
        String catalogName = database.correctObjectName(schema.getCatalogName(), Catalog.class);
        String constraintName = database.correctObjectName(name, UniqueConstraint.class);
        if (catalogName != null) {
            sqlBuf.append("AND owner='").append(catalogName).append("'\n");
        }
        if (constraintName != null) {
            sqlBuf.append("AND constraint_name='").append(constraintName).append("'");
        }
        sqlBuf.append("ORDER BY owner, constraint_name, column_index");
        return sqlBuf.toString();
    }
}

