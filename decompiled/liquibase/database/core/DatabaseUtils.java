/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.OfflineConnection;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.DatabaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class DatabaseUtils {
    public static void initializeDatabase(String defaultCatalogName, String defaultSchemaName, Database database) throws DatabaseException {
        if (!(defaultCatalogName == null && defaultSchemaName == null || database.getConnection() instanceof OfflineConnection)) {
            Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
            if (database instanceof OracleDatabase) {
                String schema = defaultCatalogName;
                if (schema == null) {
                    schema = defaultSchemaName;
                }
                executor2.execute(new RawSqlStatement("ALTER SESSION SET CURRENT_SCHEMA=" + database.escapeObjectName(schema, Schema.class)));
            } else if (database instanceof PostgresDatabase && defaultSchemaName != null) {
                String searchPath = executor2.queryForObject(new RawSqlStatement("SHOW SEARCH_PATH"), String.class);
                if (!(searchPath.equals(defaultCatalogName) || searchPath.equals(defaultSchemaName) || searchPath.equals("\"" + defaultSchemaName + "\"") || searchPath.startsWith(defaultSchemaName + ",") || searchPath.startsWith("\"" + defaultSchemaName + "\","))) {
                    String finalSearchPath = GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue() != false ? ((PostgresDatabase)database).quoteObject(defaultSchemaName, Schema.class) : defaultSchemaName;
                    if (StringUtil.isNotEmpty(searchPath)) {
                        finalSearchPath = finalSearchPath + ", " + StringUtil.join(StringUtil.splitAndTrim(searchPath, ","), ",", obj -> {
                            if (obj.startsWith("\"")) {
                                return obj;
                            }
                            return ((PostgresDatabase)database).quoteObject((String)obj, (Class<? extends DatabaseObject>)Schema.class);
                        });
                    }
                    executor2.execute(new RawSqlStatement("SET SEARCH_PATH TO " + finalSearchPath));
                }
            } else if (database instanceof AbstractDb2Database) {
                String schema = defaultCatalogName;
                if (schema == null) {
                    schema = defaultSchemaName;
                }
                executor2.execute(new RawSqlStatement("SET CURRENT SCHEMA " + schema));
            } else if (database instanceof MySQLDatabase) {
                String schema = defaultCatalogName;
                if (schema == null) {
                    schema = defaultSchemaName;
                }
                executor2.execute(new RawSqlStatement("USE " + schema));
            } else if (database instanceof MSSQLDatabase && (defaultCatalogName = StringUtil.trimToNull(defaultCatalogName)) != null) {
                executor2.execute(new RawSqlStatement(String.format("USE %s", defaultCatalogName)));
            }
        }
    }

    public static String buildCatalogAndSchemaString(String catalog, String schema) {
        String info = "";
        if (StringUtil.isNotEmpty(catalog)) {
            info = info + catalog;
        }
        if (StringUtil.isNotEmpty(schema)) {
            if (!info.endsWith(".")) {
                info = info + ".";
            }
            info = info + schema;
        }
        return info;
    }
}

