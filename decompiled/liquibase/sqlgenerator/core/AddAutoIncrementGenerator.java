/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.AddAutoIncrementStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class AddAutoIncrementGenerator
extends AbstractSqlGenerator<AddAutoIncrementStatement> {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supports(AddAutoIncrementStatement statement, Database database) {
        return database.supportsAutoIncrement() && !(database instanceof Db2zDatabase) && !(database instanceof DerbyDatabase) && !(database instanceof MSSQLDatabase) && !(database instanceof HsqlDatabase) && !(database instanceof H2Database) && !(database instanceof OracleDatabase);
    }

    @Override
    public ValidationErrors validate(AddAutoIncrementStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("columnName", statement.getColumnName());
        validationErrors.checkRequiredField("tableName", statement.getTableName());
        validationErrors.checkRequiredField("columnDataType", statement.getColumnDataType());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(AddAutoIncrementStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = database instanceof SybaseASADatabase ? "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " SET " + database.getAutoIncrementClause(statement.getStartWith(), statement.getIncrementBy(), null, null) : "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " MODIFY " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType() + "{autoIncrement:true}", database).toDatabaseDataType(database) + " " + database.getAutoIncrementClause(statement.getStartWith(), statement.getIncrementBy(), statement.getGenerationType(), statement.getDefaultOnNull());
        return new Sql[]{new UnparsedSql(sql, this.getAffectedColumn(statement))};
    }

    protected Column getAffectedColumn(AddAutoIncrementStatement statement) {
        return new Column().setRelation(new Table().setName(statement.getTableName()).setSchema(new Schema(statement.getCatalogName(), statement.getSchemaName()))).setName(statement.getColumnName());
    }
}

