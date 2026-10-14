/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.util.List;
import liquibase.change.ColumnConfig;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.ExecutablePreparedStatementBase;

public class InsertExecutablePreparedStatement
extends ExecutablePreparedStatementBase {
    public InsertExecutablePreparedStatement(Database database, String catalogName, String schemaName, String tableName, List<? extends ColumnConfig> columns, ChangeSet changeSet, ResourceAccessor resourceAccessor) {
        super(database, catalogName, schemaName, tableName, columns, changeSet, resourceAccessor);
    }

    @Override
    public boolean continueOnError() {
        return false;
    }

    @Override
    protected String generateSql(List<ColumnConfig> cols) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        StringBuilder params = new StringBuilder("VALUES(");
        sql.append(this.database.escapeTableName(this.getCatalogName(), this.getSchemaName(), this.getTableName()));
        sql.append("(");
        for (ColumnConfig columnConfig : this.getColumns()) {
            if (this.database.supportsAutoIncrement() && Boolean.TRUE.equals(columnConfig.isAutoIncrement())) continue;
            sql.append(this.database.escapeColumnName(this.getCatalogName(), this.getSchemaName(), this.getTableName(), columnConfig.getName()));
            sql.append(", ");
            if (columnConfig.getValueObject() instanceof DatabaseFunction) {
                params.append(columnConfig.getValueObject()).append(", ");
                continue;
            }
            params.append("?, ");
            cols.add(columnConfig);
        }
        sql.deleteCharAt(sql.lastIndexOf(" "));
        sql.deleteCharAt(sql.lastIndexOf(","));
        params.deleteCharAt(params.lastIndexOf(" "));
        params.deleteCharAt(params.lastIndexOf(","));
        params.append(")");
        sql.append(") ");
        sql.append((CharSequence)params);
        return sql.toString();
    }
}

