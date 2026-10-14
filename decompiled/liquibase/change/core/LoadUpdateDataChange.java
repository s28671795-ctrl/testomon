/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.LoadDataChange;
import liquibase.change.core.LoadDataColumnConfig;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.RollbackImpossibleException;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.BatchDmlExecutablePreparedStatement;
import liquibase.statement.ExecutablePreparedStatementBase;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DeleteStatement;
import liquibase.statement.core.InsertOrUpdateStatement;
import liquibase.statement.core.InsertStatement;
import liquibase.util.StringUtil;

@DatabaseChange(name="loadUpdateData", description="Loads or updates data from a CSV file into an existing table. Differs from loadData by issuing a SQL batch that checks for the existence of a record. If found, the record is UPDATEd, else the record is INSERTed. Also, generates DELETE statements for a rollback.\n\nA value of NULL in a cell will be converted to a database NULL rather than the string 'NULL'", priority=1, appliesTo={"table"}, since="2.0")
public class LoadUpdateDataChange
extends LoadDataChange {
    private String primaryKey;
    private Boolean onlyUpdate = Boolean.FALSE;

    @Override
    protected boolean hasPreparedStatementsImplemented() {
        return false;
    }

    @Override
    @DatabaseChangeProperty(description="Name of the table to insert or update data in", requiredForDatabase={"all"})
    public String getTableName() {
        return super.getTableName();
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @DatabaseChangeProperty(description="Comma-delimited list of columns for the primary key", requiredForDatabase={"all"})
    public String getPrimaryKey() {
        return this.primaryKey;
    }

    @DatabaseChangeProperty(description="If true, records with no matching database record should be ignored", since="3.3", supportsDatabase={"all"})
    public Boolean getOnlyUpdate() {
        if (this.onlyUpdate == null) {
            return false;
        }
        return this.onlyUpdate;
    }

    public void setOnlyUpdate(Boolean onlyUpdate) {
        this.onlyUpdate = onlyUpdate == null ? Boolean.FALSE : onlyUpdate;
    }

    @Override
    protected InsertStatement createStatement(String catalogName, String schemaName, String tableName) {
        return new InsertOrUpdateStatement(catalogName, schemaName, tableName, this.primaryKey, this.getOnlyUpdate());
    }

    @Override
    protected ExecutablePreparedStatementBase createPreparedStatement(Database database, String catalogName, String schemaName, String tableName, List<LoadDataColumnConfig> columns, ChangeSet changeSet, ResourceAccessor resourceAccessor) {
        throw new UnsupportedOperationException("Executable Prepared Statements are not supported for LoadUpdateDataChange yet. Very sorry.");
    }

    @Override
    public SqlStatement[] generateRollbackStatements(Database database) throws RollbackImpossibleException {
        ArrayList<DeleteStatement> statements = new ArrayList<DeleteStatement>();
        ArrayList<SqlStatement> finalForwardList = new ArrayList<SqlStatement>();
        for (SqlStatement thisForward : this.generateStatements(database)) {
            if (thisForward instanceof BatchDmlExecutablePreparedStatement) {
                finalForwardList.addAll(((BatchDmlExecutablePreparedStatement)thisForward).getIndividualStatements());
                continue;
            }
            finalForwardList.add(thisForward);
        }
        for (SqlStatement thisForward : finalForwardList) {
            InsertOrUpdateStatement thisInsert = (InsertOrUpdateStatement)thisForward;
            DeleteStatement delete = new DeleteStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
            delete.setWhere(this.getWhere(thisInsert, database));
            statements.add(delete);
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    private String getWhere(InsertOrUpdateStatement insertOrUpdateStatement, Database database) {
        String[] pkColumns;
        StringBuilder where = new StringBuilder();
        for (String thisPkColumn : pkColumns = insertOrUpdateStatement.getPrimaryKey().split(",")) {
            Object newValue = insertOrUpdateStatement.getColumnValues().get(thisPkColumn);
            where.append(database.escapeColumnName(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName(), insertOrUpdateStatement.getTableName(), thisPkColumn)).append(newValue == null || StringUtil.equalsWordNull(newValue.toString()) ? " is " : " = ");
            if (newValue == null || StringUtil.equalsWordNull(newValue.toString())) {
                where.append("NULL");
            } else {
                where.append(DataTypeFactory.getInstance().fromObject(newValue, database).objectToSql(newValue, database));
            }
            where.append(" AND ");
        }
        where.delete(where.lastIndexOf(" AND "), where.lastIndexOf(" AND ") + " AND ".length());
        return where.toString();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Cannot check loadUpdateData status");
    }
}

