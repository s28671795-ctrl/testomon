/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.List;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DropIndexStatement;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class DropIndexGenerator
extends AbstractSqlGenerator<DropIndexStatement> {
    @Override
    public ValidationErrors validate(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("indexName", statement.getIndexName());
        if (database instanceof MySQLDatabase || database instanceof MSSQLDatabase || database instanceof SybaseASADatabase) {
            validationErrors.checkRequiredField("tableName", statement.getTableName());
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List<String> associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null) {
            if (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint")) {
                return EMPTY_SQL;
            }
            if (associatedWith.contains("foreignKey") && !(database instanceof OracleDatabase) && !(database instanceof MSSQLDatabase)) {
                return EMPTY_SQL;
            }
        }
        String schemaName = statement.getTableSchemaName();
        if (database instanceof MySQLDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(null, null, statement.getIndexName()) + " ON " + database.escapeTableName(statement.getTableCatalogName(), schemaName, statement.getTableName()), this.getAffectedIndex(statement))};
        }
        if (database instanceof MSSQLDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(null, null, statement.getIndexName()) + " ON " + database.escapeTableName(statement.getTableCatalogName(), schemaName, statement.getTableName()), this.getAffectedIndex(statement))};
        }
        if (database instanceof SybaseDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + statement.getTableName() + "." + statement.getIndexName(), this.getAffectedIndex(statement))};
        }
        if (database instanceof SybaseASADatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeTableName(statement.getTableCatalogName(), schemaName, statement.getTableName()) + "." + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()), this.getAffectedIndex(statement))};
        }
        if (database instanceof PostgresDatabase) {
            return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()), this.getAffectedIndex(statement))};
        }
        return new Sql[]{new UnparsedSql("DROP INDEX " + database.escapeIndexName(statement.getTableCatalogName(), schemaName, statement.getIndexName()), this.getAffectedIndex(statement))};
    }

    protected Index getAffectedIndex(DropIndexStatement statement) {
        Table table = null;
        if (statement.getTableName() != null) {
            table = (Table)new Table().setName(statement.getTableName()).setSchema(statement.getTableCatalogName(), statement.getTableSchemaName());
        }
        return new Index().setName(statement.getIndexName()).setRelation(table);
    }
}

