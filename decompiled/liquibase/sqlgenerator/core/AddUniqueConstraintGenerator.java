/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.H2Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.AddUniqueConstraintStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringUtil;

public class AddUniqueConstraintGenerator
extends AbstractSqlGenerator<AddUniqueConstraintStatement> {
    @Override
    public boolean supports(AddUniqueConstraintStatement statement, Database database) {
        return !(database instanceof SQLiteDatabase) && !(database instanceof SybaseDatabase) && !(database instanceof SybaseASADatabase) && !(database instanceof InformixDatabase);
    }

    @Override
    public ValidationErrors validate(AddUniqueConstraintStatement addUniqueConstraintStatement, Database database, SqlGeneratorChain<AddUniqueConstraintStatement> sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("columnNames", addUniqueConstraintStatement.getColumnNames());
        validationErrors.checkRequiredField("tableName", addUniqueConstraintStatement.getTableName());
        if (!(database instanceof OracleDatabase) && !(database instanceof PostgresDatabase)) {
            validationErrors.checkDisallowedField("forIndexName", addUniqueConstraintStatement.getForIndexName(), database, new Class[0]);
        }
        if (!(database instanceof MSSQLDatabase) && addUniqueConstraintStatement.isClustered()) {
            validationErrors.checkDisallowedField("clustered", addUniqueConstraintStatement.isClustered(), database, new Class[0]);
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(AddUniqueConstraintStatement statement, Database database, SqlGeneratorChain<AddUniqueConstraintStatement> sqlGeneratorChain) {
        String sql = statement.getConstraintName() == null ? String.format("ALTER TABLE %s ADD UNIQUE" + (statement.isClustered() ? " CLUSTERED " : " ") + "(%s)", database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeColumnNameList(statement.getColumnNames())) : String.format("ALTER TABLE %s ADD CONSTRAINT %s UNIQUE" + (statement.isClustered() ? " CLUSTERED " : " ") + "(%s)", database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeConstraintName(statement.getConstraintName()), database.escapeColumnNameList(statement.getColumnNames()));
        if (database instanceof OracleDatabase || database instanceof PostgresDatabase) {
            if (statement.isDeferrable()) {
                sql = sql + " DEFERRABLE";
            }
            if (statement.isInitiallyDeferred()) {
                sql = sql + " INITIALLY DEFERRED";
            }
        }
        if (database instanceof OracleDatabase && statement.isDisabled()) {
            sql = sql + " DISABLE";
        }
        boolean isInUsingIndexClause = false;
        if (statement.getForIndexName() != null && !(database instanceof H2Database)) {
            sql = sql + " USING INDEX ";
            sql = sql + database.escapeObjectName(statement.getForIndexCatalogName(), statement.getForIndexSchemaName(), statement.getForIndexName(), Index.class);
            isInUsingIndexClause = true;
        }
        if (StringUtil.trimToNull(statement.getTablespace()) != null && database.supportsTablespaces()) {
            if (database instanceof MSSQLDatabase) {
                sql = sql + " ON " + statement.getTablespace();
            } else if (!(database instanceof AbstractDb2Database || database instanceof SybaseASADatabase || database instanceof InformixDatabase)) {
                if (database instanceof OracleDatabase) {
                    if (statement.getForIndexName() == null) {
                        sql = sql + " USING INDEX TABLESPACE " + statement.getTablespace();
                    }
                } else {
                    if (!isInUsingIndexClause) {
                        sql = sql + " USING INDEX";
                    }
                    sql = sql + " TABLESPACE " + statement.getTablespace();
                }
            }
        }
        if (database instanceof OracleDatabase) {
            sql = sql + (!statement.shouldValidate() ? " ENABLE NOVALIDATE " : "");
        }
        return new Sql[]{new UnparsedSql(sql, this.getAffectedUniqueConstraint(statement))};
    }

    protected UniqueConstraint getAffectedUniqueConstraint(AddUniqueConstraintStatement statement) {
        UniqueConstraint uniqueConstraint = new UniqueConstraint().setName(statement.getConstraintName()).setRelation(new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
        int i = 0;
        for (Column column : Column.listFromNames(statement.getColumnNames())) {
            uniqueConstraint.addColumn(i++, column);
        }
        return uniqueConstraint;
    }
}

