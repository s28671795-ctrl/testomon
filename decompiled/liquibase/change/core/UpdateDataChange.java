/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.Scope;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.AbstractModifyDataChange;
import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.UpdateExecutablePreparedStatement;
import liquibase.statement.core.UpdateStatement;

@DatabaseChange(name="update", description="Updates data in an existing table", priority=1, appliesTo={"table"})
public class UpdateDataChange
extends AbstractModifyDataChange
implements ChangeWithColumns<ColumnConfig> {
    private List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = super.validate(database);
        validate.checkRequiredField("columns", this.getColumns());
        return validate;
    }

    @Override
    @DatabaseChangeProperty(description="Data to update", requiredForDatabase={"all"})
    public List<ColumnConfig> getColumns() {
        return this.columns;
    }

    @Override
    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    @Override
    public void addColumn(ColumnConfig column) {
        this.columns.add(column);
    }

    public void removeColumn(ColumnConfig column) {
        this.columns.remove(column);
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        SqlStatement statement;
        boolean needsPreparedStatement = false;
        for (ColumnConfig column : this.getColumns()) {
            Object valueObject;
            if (column.getValueBlobFile() != null) {
                needsPreparedStatement = true;
            }
            if (column.getValueClobFile() != null) {
                needsPreparedStatement = true;
            }
            if (database instanceof OracleDatabase && column.getType() != null && "CLOB".equalsIgnoreCase(column.getType()) && column.getValue() != null && column.getValue().length() >= 4000) {
                needsPreparedStatement = true;
            }
            if ((valueObject = column.getValueObject()) instanceof SequenceNextValueFunction) {
                ((SequenceNextValueFunction)valueObject).setSchemaName(this.getSchemaName());
            }
            if (!(valueObject instanceof SequenceCurrentValueFunction)) continue;
            ((SequenceCurrentValueFunction)valueObject).setSchemaName(this.getSchemaName());
        }
        if (needsPreparedStatement) {
            statement = new UpdateExecutablePreparedStatement(database, this.catalogName, this.schemaName, this.tableName, this.columns, this.getChangeSet(), Scope.getCurrentScope().getResourceAccessor());
            ((UpdateExecutablePreparedStatement)statement).setWhereClause(this.where);
            for (ColumnConfig whereParam : this.whereParams) {
                if (whereParam.getName() != null) {
                    ((UpdateExecutablePreparedStatement)statement).addWhereColumnName(whereParam.getName());
                }
                ((UpdateExecutablePreparedStatement)statement).addWhereParameter(whereParam.getValueObject());
            }
            return new SqlStatement[]{statement};
        }
        statement = new UpdateStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
        for (ColumnConfig column : this.getColumns()) {
            ((UpdateStatement)statement).addNewColumnValue(column.getName(), column.getValueObject());
        }
        ((UpdateStatement)statement).setWhereClause(this.where);
        for (ColumnConfig whereParam : this.whereParams) {
            if (whereParam.getName() != null) {
                ((UpdateStatement)statement).addWhereColumnName(whereParam.getName());
            }
            ((UpdateStatement)statement).addWhereParameter(whereParam.getValueObject());
        }
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Cannot check updateData status");
    }

    @Override
    public String getConfirmationMessage() {
        return "Data updated in " + this.getTableName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    protected void customLoadLogic(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        ParsedNode whereParams = parsedNode.getChild(null, "whereParams");
        if (whereParams != null) {
            for (ParsedNode param2 : whereParams.getChildren(null, "param")) {
                ColumnConfig columnConfig = new ColumnConfig();
                try {
                    columnConfig.load(param2, resourceAccessor);
                }
                catch (ParsedNodeException e) {
                    e.printStackTrace();
                }
                this.addWhereParam(columnConfig);
            }
        }
    }
}

