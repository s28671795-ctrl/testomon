/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.structure.core.View;

public class GetViewDefinitionGenerator
extends AbstractSqlGenerator<GetViewDefinitionStatement> {
    @Override
    public ValidationErrors validate(GetViewDefinitionStatement getViewDefinitionStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("viewName", getViewDefinitionStatement.getViewName());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(GetViewDefinitionStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        CatalogAndSchema schema = new CatalogAndSchema(statement.getCatalogName(), statement.getSchemaName()).customize(database);
        String sql = database instanceof MSSQLDatabase ? "select VIEW_DEFINITION from INFORMATION_SCHEMA.VIEWS where TABLE_NAME='" + database.correctObjectName(statement.getViewName(), View.class) + "'" : "select view_definition from information_schema.views where table_name='" + database.correctObjectName(statement.getViewName(), View.class) + "'";
        if (database instanceof MySQLDatabase) {
            sql = sql + " and table_schema='" + schema.getCatalogName() + "'";
        } else {
            String catalogName;
            String schemaName;
            if (database.supports(Schema.class) && (schemaName = schema.getSchemaName()) != null) {
                sql = database instanceof MSSQLDatabase ? sql + " and TABLE_SCHEMA='" + schemaName + "'" : sql + " and table_schema='" + schemaName + "'";
            }
            if (database.supports(Catalog.class) && (catalogName = schema.getCatalogName()) != null) {
                sql = database instanceof MSSQLDatabase ? sql + " and TABLE_CATALOG='" + catalogName + "'" : sql + " and table_catalog='" + catalogName + "'";
            }
        }
        return new Sql[]{new UnparsedSql(sql, new DatabaseObject[0])};
    }
}

