/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.List;
import java.util.Map;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.ExecutorService;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetColumnRemarksGenerator;
import liquibase.statement.core.RawSqlStatement;
import liquibase.statement.core.SetColumnRemarksStatement;
import liquibase.util.ColumnParentType;

public class SetColumnRemarksGeneratorSnowflake
extends SetColumnRemarksGenerator {
    public static final String SET_COLUMN_REMARKS_NOT_SUPPORTED_ON_VIEW_MSG = "setColumnRemarks change type isn't supported on Snowflake for a 'view'";

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(SetColumnRemarksStatement statement, Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public ValidationErrors validate(SetColumnRemarksStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = super.validate(statement, database, sqlGeneratorChain);
        if (database instanceof SnowflakeDatabase) {
            if (statement.getColumnParentType() != null) {
                if (statement.getColumnParentType() == ColumnParentType.VIEW) {
                    validationErrors.addError(SET_COLUMN_REMARKS_NOT_SUPPORTED_ON_VIEW_MSG);
                }
            } else {
                try {
                    List<Map<String, ?>> viewList = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(new RawSqlStatement(String.format("SHOW VIEWS LIKE '%s'", statement.getTableName())));
                    if (!viewList.isEmpty()) {
                        validationErrors.addError(SET_COLUMN_REMARKS_NOT_SUPPORTED_ON_VIEW_MSG);
                    }
                }
                catch (DatabaseException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).severe("Failed to query Snowflake to determine if object is a table or view.", e);
                }
            }
        }
        return validationErrors;
    }
}

