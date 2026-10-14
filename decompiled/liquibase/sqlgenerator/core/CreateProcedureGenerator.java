/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.EnterpriseDBDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.jvm.JdbcExecutor;
import liquibase.parser.ChangeLogParserConfiguration;
import liquibase.parser.LiquibaseSqlParser;
import liquibase.parser.SqlParserFactory;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.CreateProcedureStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.StoredProcedure;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;

public class CreateProcedureGenerator
extends AbstractSqlGenerator<CreateProcedureStatement> {
    @Override
    public boolean supports(CreateProcedureStatement statement, Database database) {
        return !(database instanceof SQLiteDatabase);
    }

    @Override
    public ValidationErrors validate(CreateProcedureStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("procedureText", statement.getProcedureText());
        if (statement.getReplaceIfExists() != null) {
            if (database instanceof MSSQLDatabase || database instanceof MySQLDatabase || database instanceof DB2Database || database instanceof Db2zDatabase) {
                if (statement.getReplaceIfExists().booleanValue() && statement.getProcedureName() == null) {
                    validationErrors.addError("procedureName is required if replaceIfExists = true");
                }
            } else {
                validationErrors.checkDisallowedField("replaceIfExists", statement.getReplaceIfExists(), null, new Class[0]);
            }
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(CreateProcedureStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ArrayList<Sql> sql = new ArrayList<Sql>();
        String schemaName = statement.getSchemaName();
        if (schemaName == null && GlobalConfiguration.ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA.getCurrentValue().booleanValue()) {
            schemaName = database.getDefaultSchemaName();
        }
        String procedureText = CreateProcedureGenerator.addSchemaToText(statement.getProcedureText(), schemaName, "PROCEDURE", database);
        SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
        LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
        if (statement.getReplaceIfExists() != null && statement.getReplaceIfExists().booleanValue()) {
            String fullyQualifiedName;
            if (database instanceof MSSQLDatabase) {
                fullyQualifiedName = database.escapeObjectName(statement.getProcedureName(), StoredProcedure.class);
                if (schemaName != null) {
                    fullyQualifiedName = database.escapeObjectName(schemaName, Schema.class) + "." + fullyQualifiedName;
                }
                sql.add(new UnparsedSql("if object_id('" + fullyQualifiedName + "', 'p') is null exec ('create procedure " + fullyQualifiedName + " as select 1 a')", new DatabaseObject[0]));
                StringClauses parsedSql = sqlParser.parse(procedureText, true, true);
                StringClauses.ClauseIterator clauseIterator = parsedSql.getClauseIterator();
                Object next = "START";
                while (next != null && !"create".equalsIgnoreCase(next.toString()) && !"alter".equalsIgnoreCase(next.toString()) && clauseIterator.hasNext()) {
                    next = clauseIterator.nextNonWhitespace();
                }
                clauseIterator.replace("ALTER");
                procedureText = parsedSql.toString();
            } else if (!(database instanceof DB2Database) && !(database instanceof Db2zDatabase)) {
                fullyQualifiedName = database.escapeObjectName(statement.getProcedureName(), StoredProcedure.class);
                sql.add(new UnparsedSql("DROP PROCEDURE IF EXISTS " + fullyQualifiedName, new DatabaseObject[0]));
            }
        }
        if ((procedureText = CreateProcedureGenerator.removeTrailingDelimiter(procedureText, statement.getEndDelimiter())) == null) {
            return sql.toArray(EMPTY_SQL);
        }
        if (database instanceof MSSQLDatabase && procedureText.toLowerCase().contains("merge") && !procedureText.endsWith(";")) {
            StringClauses parsed = sqlParser.parse(procedureText);
            StringClauses.ClauseIterator clauseIterator = parsed.getClauseIterator();
            boolean reallyMerge = false;
            while (clauseIterator.hasNext()) {
                Object clause = clauseIterator.nextNonWhitespace();
                if (!"merge".equalsIgnoreCase((String)clause)) continue;
                reallyMerge = true;
            }
            if (reallyMerge) {
                procedureText = procedureText + ";";
            }
        }
        sql.add(new UnparsedSql(procedureText, statement.getEndDelimiter(), new DatabaseObject[0]));
        CreateProcedureGenerator.surroundWithSchemaSets(sql, statement.getSchemaName(), database);
        return sql.toArray(EMPTY_SQL);
    }

    public static String removeTrailingDelimiter(String procedureText, String endDelimiter) {
        String trimmedDelimiter;
        if (procedureText == null) {
            return null;
        }
        if (endDelimiter == null) {
            return procedureText;
        }
        endDelimiter = endDelimiter.replace("\\r", "\r").replace("\\n", "\n");
        String endCommentsTrimmedText = StringUtil.stripSqlCommentsAndWhitespacesFromTheEnd(procedureText);
        if (endCommentsTrimmedText.endsWith(trimmedDelimiter = StringUtil.trimRight(endDelimiter))) {
            return endCommentsTrimmedText.substring(0, endCommentsTrimmedText.length() - trimmedDelimiter.length());
        }
        return procedureText;
    }

    public static void surroundWithSchemaSets(List<Sql> sql, String schemaName, Database database) {
        if (StringUtil.trimToNull(schemaName) != null && !ChangeLogParserConfiguration.USE_PROCEDURE_SCHEMA.getCurrentValue().booleanValue()) {
            String defaultSchema = database.getDefaultSchemaName();
            if (database instanceof OracleDatabase) {
                sql.add(0, new UnparsedSql("ALTER SESSION SET CURRENT_SCHEMA=" + database.escapeObjectName(schemaName, Schema.class), new DatabaseObject[0]));
                sql.add(new UnparsedSql("ALTER SESSION SET CURRENT_SCHEMA=" + database.escapeObjectName(defaultSchema, Schema.class), new DatabaseObject[0]));
            } else if (database instanceof AbstractDb2Database) {
                sql.add(0, new UnparsedSql("SET CURRENT SCHEMA " + schemaName, new DatabaseObject[0]));
                sql.add(new UnparsedSql("SET CURRENT SCHEMA " + defaultSchema, new DatabaseObject[0]));
            } else if (database instanceof PostgresDatabase) {
                Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                String originalSearchPath = null;
                if (executor2 instanceof JdbcExecutor) {
                    try {
                        originalSearchPath = executor2.queryForObject(new RawSqlStatement("SHOW SEARCH_PATH"), String.class);
                    }
                    catch (Throwable e) {
                        Scope.getCurrentScope().getLog(CreateProcedureGenerator.class).warning("Cannot get search_path", e);
                    }
                }
                if (originalSearchPath == null) {
                    originalSearchPath = defaultSchema;
                }
                if (!(originalSearchPath.equals(schemaName) || originalSearchPath.startsWith(schemaName + ",") || originalSearchPath.startsWith("\"" + schemaName + "\","))) {
                    if (database instanceof EnterpriseDBDatabase) {
                        sql.add(0, new UnparsedSql("ALTER SESSION SET SEARCH_PATH TO " + database.escapeObjectName(defaultSchema, Schema.class) + ", " + originalSearchPath, new DatabaseObject[0]));
                        sql.add(new UnparsedSql("ALTER SESSION SET CURRENT SCHEMA " + originalSearchPath, new DatabaseObject[0]));
                    } else {
                        sql.add(0, new UnparsedSql("SET SEARCH_PATH TO " + database.escapeObjectName(schemaName, Schema.class) + ", " + originalSearchPath, new DatabaseObject[0]));
                        sql.add(new UnparsedSql("SET CURRENT SCHEMA " + originalSearchPath, new DatabaseObject[0]));
                    }
                }
            }
        }
    }

    public static String addSchemaToText(String procedureText, String schemaName, String keywordBeforeName, Database database) {
        if (schemaName == null) {
            return procedureText;
        }
        if (StringUtil.trimToNull(schemaName) != null && ChangeLogParserConfiguration.USE_PROCEDURE_SCHEMA.getCurrentValue().booleanValue()) {
            SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
            LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
            StringClauses parsedSql = sqlParser.parse(procedureText, true, true);
            StringClauses.ClauseIterator clauseIterator = parsedSql.getClauseIterator();
            Object next = "START";
            while (next != null && !next.toString().equalsIgnoreCase(keywordBeforeName) && clauseIterator.hasNext()) {
                if ("PACKAGE".equalsIgnoreCase((String)next) && !"PACKAGE".equalsIgnoreCase(keywordBeforeName) && !"BODY".equalsIgnoreCase(keywordBeforeName)) {
                    return procedureText;
                }
                next = clauseIterator.nextNonWhitespace();
            }
            if (next != null && clauseIterator.hasNext()) {
                Object procNameClause = clauseIterator.nextNonWhitespace();
                if (procNameClause instanceof String) {
                    String[] nameParts = ((String)procNameClause).split("\\.");
                    String finalName = nameParts.length == 1 ? database.escapeObjectName(schemaName, Schema.class) + "." + nameParts[0] : (nameParts.length == 2 ? database.escapeObjectName(schemaName, Schema.class) + "." + nameParts[1] : (nameParts.length == 3 ? nameParts[0] + "." + database.escapeObjectName(schemaName, Schema.class) + "." + nameParts[2] : (String)procNameClause));
                    clauseIterator.replace(finalName);
                }
                procedureText = parsedSql.toString();
            }
        }
        return procedureText;
    }
}

