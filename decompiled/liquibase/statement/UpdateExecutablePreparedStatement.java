/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.change.ColumnConfig;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.ExecutablePreparedStatementBase;
import liquibase.util.SqlUtil;

public class UpdateExecutablePreparedStatement
extends ExecutablePreparedStatementBase {
    private String whereClause;
    private final List<String> whereColumnNames = new ArrayList<String>();
    private final List<Object> whereParameters = new ArrayList<Object>();

    public UpdateExecutablePreparedStatement(Database database, String catalogName, String schemaName, String tableName, List<ColumnConfig> columns, ChangeSet changeSet, ResourceAccessor resourceAccessor) {
        super(database, catalogName, schemaName, tableName, columns, changeSet, resourceAccessor);
    }

    @Override
    public boolean continueOnError() {
        return false;
    }

    @Override
    protected String generateSql(List<ColumnConfig> cols) {
        StringBuilder sql = new StringBuilder("UPDATE ").append(this.database.escapeTableName(this.getCatalogName(), this.getSchemaName(), this.getTableName()));
        StringBuilder params = new StringBuilder(" SET ");
        for (ColumnConfig columnConfig : this.getColumns()) {
            params.append(this.database.escapeColumnName(this.getCatalogName(), this.getSchemaName(), this.getTableName(), columnConfig.getName()));
            params.append(" = ");
            if (columnConfig.getValueObject() instanceof DatabaseFunction) {
                params.append(columnConfig.getValueObject()).append(", ");
                continue;
            }
            params.append("?, ");
            cols.add(columnConfig);
        }
        params.deleteCharAt(params.lastIndexOf(" "));
        params.deleteCharAt(params.lastIndexOf(","));
        sql.append((CharSequence)params);
        if (this.getWhereClause() != null) {
            sql.append(" WHERE ").append(SqlUtil.replacePredicatePlaceholders(this.database, this.getWhereClause(), this.getWhereColumnNames(), this.getWhereParameters()));
        }
        return sql.toString();
    }

    public String getWhereClause() {
        return this.whereClause;
    }

    public UpdateExecutablePreparedStatement setWhereClause(String whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public UpdateExecutablePreparedStatement addWhereParameter(Object value) {
        this.whereParameters.add(value);
        return this;
    }

    public UpdateExecutablePreparedStatement addWhereParameters(Object ... value) {
        this.whereParameters.addAll(Arrays.asList(value));
        return this;
    }

    public UpdateExecutablePreparedStatement addWhereColumnName(String value) {
        this.whereColumnNames.add(value);
        return this;
    }

    public List<Object> getWhereParameters() {
        return this.whereParameters;
    }

    public List<String> getWhereColumnNames() {
        return this.whereColumnNames;
    }
}

