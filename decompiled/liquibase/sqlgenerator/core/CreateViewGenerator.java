/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.parser.LiquibaseSqlParser;
import liquibase.parser.SqlParserFactory;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.sqlgenerator.core.CreateViewGeneratorInformix;
import liquibase.statement.core.CreateViewStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Relation;
import liquibase.structure.core.View;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;

public class CreateViewGenerator
extends AbstractSqlGenerator<CreateViewStatement> {
    @Override
    public ValidationErrors validate(CreateViewStatement createViewStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (database instanceof InformixDatabase) {
            return new CreateViewGeneratorInformix().validate(createViewStatement, database, sqlGeneratorChain);
        }
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("viewName", createViewStatement.getViewName());
        if (createViewStatement.isReplaceIfExists()) {
            if (!this.db2VersionSupportsCreateOrReplace(database)) {
                validationErrors.addError("'replaceIfExists' is not allowed on DB2 version < 10.5");
            } else {
                validationErrors.checkDisallowedField("replaceIfExists", createViewStatement.isReplaceIfExists(), database, Db2zDatabase.class, DerbyDatabase.class, InformixDatabase.class);
            }
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(CreateViewStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (database instanceof InformixDatabase) {
            return new CreateViewGeneratorInformix().generateSql(statement, database, sqlGeneratorChain);
        }
        ArrayList<UnparsedSql> sql = new ArrayList<UnparsedSql>();
        if (database instanceof SybaseASADatabase) {
            sql.add(new UnparsedSql("SET TEMPORARY OPTION force_view_creation='ON'", new DatabaseObject[0]));
        }
        SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
        LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
        StringClauses viewDefinition = sqlParser.parse(statement.getSelectQuery(), true, true);
        if (!statement.isFullDefinition()) {
            viewDefinition.prepend(" ").prepend("AS").prepend(" ").prepend(database.escapeViewName(statement.getCatalogName(), statement.getSchemaName(), statement.getViewName())).prepend(" ").prepend("VIEW").prepend(" ").prepend("CREATE");
        }
        if (statement.isReplaceIfExists()) {
            if (database instanceof FirebirdDatabase) {
                viewDefinition.replace("CREATE", "RECREATE");
            } else if (database instanceof MSSQLDatabase) {
                if (!this.viewDefinitionContains(viewDefinition, "CREATE OR ALTER")) {
                    CatalogAndSchema schema = new CatalogAndSchema(statement.getCatalogName(), statement.getSchemaName()).customize(database);
                    String schemaSpec = this.determineSchemaSpec(schema.getSchemaName());
                    sql.add(new UnparsedSql("IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'" + schemaSpec + "[" + statement.getViewName() + "]'))\n    EXEC sp_executesql N'CREATE VIEW " + schemaSpec + "[" + statement.getViewName() + "] AS SELECT ''This is a code stub which will be replaced by an Alter Statement'' as [code_stub]'", new DatabaseObject[0]));
                    viewDefinition.replace("CREATE", "ALTER");
                }
            } else if (this.shouldPrependDropViewStatement(database)) {
                sql.add(new UnparsedSql("DROP VIEW IF EXISTS " + database.escapeViewName(statement.getCatalogName(), statement.getSchemaName(), statement.getViewName()), new DatabaseObject[0]));
            } else if (!statement.getSelectQuery().toUpperCase().contains("OR REPLACE")) {
                viewDefinition.replace("CREATE", "CREATE OR REPLACE");
            }
        }
        sql.add(new UnparsedSql(viewDefinition.toString(), this.getAffectedView(statement)));
        return sql.toArray(EMPTY_SQL);
    }

    private String determineSchemaSpec(String schemaName) {
        if (StringUtil.isEmpty(schemaName)) {
            return "";
        }
        return "[" + schemaName + "].";
    }

    private boolean viewDefinitionContains(StringClauses viewDefinition, String phrase) {
        return viewDefinition.toString().toUpperCase().replace('\n', ' ').replaceAll("\\s{2,}", " ").contains(phrase.toUpperCase());
    }

    private boolean shouldPrependDropViewStatement(Database database) {
        boolean dropIfCannotReplace = GlobalConfiguration.ALWAYS_DROP_INSTEAD_OF_REPLACE.getDefaultValue();
        if (GlobalConfiguration.ALWAYS_DROP_INSTEAD_OF_REPLACE.getCurrentValue() != null) {
            dropIfCannotReplace = GlobalConfiguration.ALWAYS_DROP_INSTEAD_OF_REPLACE.getCurrentValue();
        }
        return database instanceof HsqlDatabase || database instanceof PostgresDatabase && dropIfCannotReplace;
    }

    private boolean db2VersionSupportsCreateOrReplace(Database database) {
        if (!(database instanceof DB2Database)) {
            return true;
        }
        int majorVersion = this.getMajorVersion(database);
        if (majorVersion < 10) {
            return false;
        }
        int minorVersion = this.getMinorVersion(database);
        return majorVersion > 10 || minorVersion >= 5;
    }

    private int getMajorVersion(Database database) {
        int majorVersion;
        try {
            majorVersion = database.getDatabaseMajorVersion();
        }
        catch (DatabaseException dbe) {
            majorVersion = -1;
        }
        return majorVersion;
    }

    private int getMinorVersion(Database database) {
        int minorVersion;
        try {
            minorVersion = database.getDatabaseMinorVersion();
        }
        catch (DatabaseException dbe) {
            minorVersion = -1;
        }
        return minorVersion;
    }

    protected Relation getAffectedView(CreateViewStatement statement) {
        return new View().setName(statement.getViewName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }
}

