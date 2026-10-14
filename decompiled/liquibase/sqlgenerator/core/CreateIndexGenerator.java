/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import liquibase.change.AddColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MockDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class CreateIndexGenerator
extends AbstractSqlGenerator<CreateIndexStatement> {
    @Override
    public ValidationErrors validate(CreateIndexStatement createIndexStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", createIndexStatement.getTableName());
        validationErrors.checkRequiredField("columns", createIndexStatement.getColumns());
        if (database instanceof HsqlDatabase) {
            validationErrors.checkRequiredField("name", createIndexStatement.getIndexName());
        }
        return validationErrors;
    }

    @Override
    public Warnings warn(CreateIndexStatement createIndexStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Warnings warnings = super.warn(createIndexStatement, database, sqlGeneratorChain);
        if (!(database instanceof MSSQLDatabase || database instanceof OracleDatabase || database instanceof AbstractDb2Database || database instanceof PostgresDatabase || database instanceof MockDatabase || createIndexStatement.isClustered() == null || !createIndexStatement.isClustered().booleanValue())) {
            warnings.addWarning("Creating clustered index not supported with " + database);
        }
        return warnings;
    }

    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List<String> associatedWith;
        if (database instanceof OracleDatabase ? (associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",")) != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint")) : (associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",")) != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint") || associatedWith.contains("foreignKey"))) {
            return EMPTY_SQL;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE ");
        if (statement.isUnique() != null && statement.isUnique().booleanValue()) {
            buffer.append("UNIQUE ");
        }
        if (database instanceof MSSQLDatabase && statement.isClustered() != null) {
            if (statement.isClustered().booleanValue()) {
                buffer.append("CLUSTERED ");
            } else {
                buffer.append("NONCLUSTERED ");
            }
        }
        buffer.append("INDEX ");
        if (statement.getIndexName() != null) {
            String indexSchema = statement.getTableSchemaName();
            buffer.append(database.escapeIndexName(statement.getTableCatalogName(), indexSchema, statement.getIndexName())).append(" ");
        }
        buffer.append("ON ");
        if (database instanceof OracleDatabase && statement.isClustered() != null && statement.isClustered().booleanValue()) {
            buffer.append("CLUSTER ");
        }
        buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append("(");
        Iterator<AddColumnConfig> iterator2 = Arrays.asList(statement.getColumns()).iterator();
        while (iterator2.hasNext()) {
            AddColumnConfig column = iterator2.next();
            if (column.getComputed() == null) {
                buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName(), false));
            } else if (column.getComputed().booleanValue()) {
                buffer.append(column.getName());
            } else {
                buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName()));
            }
            if (column.getDescending() != null && column.getDescending().booleanValue()) {
                buffer.append(" DESC");
            }
            if (!iterator2.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append(")");
        if (StringUtil.trimToNull(statement.getTablespace()) != null && database.supportsTablespaces()) {
            if (database instanceof MSSQLDatabase || database instanceof SybaseASADatabase) {
                buffer.append(" ON ").append(statement.getTablespace());
            } else if (database instanceof AbstractDb2Database || database instanceof InformixDatabase) {
                buffer.append(" IN ").append(statement.getTablespace());
            } else {
                buffer.append(" TABLESPACE ").append(statement.getTablespace());
            }
        }
        if (database instanceof AbstractDb2Database && statement.isClustered() != null && statement.isClustered().booleanValue()) {
            buffer.append(" CLUSTER");
        }
        return new Sql[]{new UnparsedSql(buffer.toString(), this.getAffectedIndex(statement))};
    }

    protected Index getAffectedIndex(CreateIndexStatement statement) {
        return new Index().setName(statement.getIndexName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getTableCatalogName(), statement.getTableSchemaName()));
    }
}

