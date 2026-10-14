/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.ValidationErrors;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddColumnGenerator;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.core.AddColumnStatement;
import liquibase.util.StringUtil;

public class AddColumnGeneratorDefaultClauseBeforeNotNull
extends AddColumnGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddColumnStatement statement, Database database) {
        return database instanceof OracleDatabase || database instanceof HsqlDatabase || database instanceof H2Database || database instanceof DerbyDatabase || database instanceof AbstractDb2Database || database instanceof FirebirdDatabase || database instanceof SybaseDatabase || database instanceof SybaseASADatabase || database instanceof InformixDatabase;
    }

    @Override
    public ValidationErrors validate(AddColumnStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = super.validate(statement, database, sqlGeneratorChain);
        if (statement.isMultiple()) {
            for (AddColumnStatement column : statement.getColumns()) {
                this.validateSingleColumn(column, database, validationErrors);
            }
        } else {
            this.validateSingleColumn(statement, database, validationErrors);
        }
        return validationErrors;
    }

    private void validateSingleColumn(AddColumnStatement statement, Database database, ValidationErrors validationErrors) {
        if (database instanceof DerbyDatabase && statement.isAutoIncrement()) {
            validationErrors.addError("Cannot add an identity column to derby");
        }
    }

    @Override
    protected String generateSingleColumnSQL(AddColumnStatement statement, Database database) {
        String alterTable = " ADD " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnType() + (statement.isAutoIncrement() ? "{autoIncrement:true}" : ""), database).toDatabaseDataType(database);
        alterTable = alterTable + this.getDefaultClauseBeforeNotNull(statement, database);
        if (this.primaryKeyBeforeNotNull(database) && statement.isPrimaryKey()) {
            alterTable = alterTable + " PRIMARY KEY";
        }
        if (statement.isAutoIncrement()) {
            AutoIncrementConstraint autoIncrementConstraint = statement.getAutoIncrementConstraint();
            alterTable = alterTable + " " + database.getAutoIncrementClause(autoIncrementConstraint.getStartWith(), autoIncrementConstraint.getIncrementBy(), autoIncrementConstraint.getGenerationType(), autoIncrementConstraint.getDefaultOnNull());
        }
        if (!statement.isNullable()) {
            alterTable = alterTable + " NOT NULL";
        } else if (database instanceof SybaseDatabase || database instanceof SybaseASADatabase) {
            alterTable = alterTable + " NULL";
        }
        if (!this.primaryKeyBeforeNotNull(database) && statement.isPrimaryKey()) {
            alterTable = alterTable + " PRIMARY KEY";
        }
        if (!StringUtil.isEmpty(statement.getAddBeforeColumn())) {
            alterTable = alterTable + " BEFORE " + database.escapeColumnName(statement.getSchemaName(), statement.getSchemaName(), statement.getTableName(), statement.getAddBeforeColumn()) + " ";
        }
        if (!StringUtil.isEmpty(statement.getAddAfterColumn())) {
            alterTable = alterTable + " AFTER " + database.escapeColumnName(statement.getSchemaName(), statement.getSchemaName(), statement.getTableName(), statement.getAddAfterColumn());
        }
        return alterTable;
    }

    private String getDefaultClauseBeforeNotNull(AddColumnStatement statement, Database database) {
        String clause = "";
        Object defaultValue = statement.getDefaultValue();
        if (defaultValue != null) {
            clause = clause + " DEFAULT " + DataTypeFactory.getInstance().fromObject(defaultValue, database).objectToSql(defaultValue, database);
        }
        return clause;
    }

    private boolean primaryKeyBeforeNotNull(Database database) {
        return !(database instanceof HsqlDatabase) && !(database instanceof H2Database);
    }
}

