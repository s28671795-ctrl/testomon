/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddDefaultValueGenerator;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.core.AddDefaultValueStatement;

public class AddDefaultValueGeneratorMySQL
extends AddDefaultValueGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddDefaultValueStatement statement, Database database) {
        return database instanceof MySQLDatabase;
    }

    @Override
    public ValidationErrors validate(AddDefaultValueStatement addDefaultValueStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors2 = super.validate(addDefaultValueStatement, database, sqlGeneratorChain);
        try {
            int majorVersion = database.getDatabaseMajorVersion();
            int minorVersion = database.getDatabaseMinorVersion();
            if (addDefaultValueStatement.getDefaultValue() instanceof DatabaseFunction && (majorVersion < 5 || majorVersion == 5 && minorVersion < 7)) {
                errors2.addError("This version of mysql does not support non-literal default values");
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Can't get default value");
        }
        return errors2;
    }

    @Override
    public Sql[] generateSql(AddDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String finalDefaultValue;
        Object defaultValue = statement.getDefaultValue();
        if (defaultValue instanceof DatabaseFunction) {
            try {
                if (database.getDatabaseMajorVersion() == 5 && database.getDatabaseMinorVersion() >= 7) {
                    String finalDefaultValue2 = defaultValue.toString();
                    return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " MODIFY COLUMN " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " " + DataTypeFactory.getInstance().fromDescription(statement.getColumnDataType(), database).toDatabaseDataType(database) + " DEFAULT " + finalDefaultValue2, this.getAffectedColumn(statement))};
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Can't get database version");
            }
            finalDefaultValue = "(" + defaultValue + ")";
            if (finalDefaultValue.startsWith("((")) {
                finalDefaultValue = defaultValue.toString();
            }
        } else {
            finalDefaultValue = DataTypeFactory.getInstance().fromObject(defaultValue, database).objectToSql(defaultValue, database);
        }
        return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ALTER " + database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), statement.getColumnName()) + " SET DEFAULT " + finalDefaultValue, this.getAffectedColumn(statement))};
    }
}

