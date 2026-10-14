/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.core.AbstractModifyDataChange;
import liquibase.database.Database;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DeleteStatement;

@DatabaseChange(name="delete", description="Deletes data from an existing table", priority=1, appliesTo={"table"})
public class DeleteDataChange
extends AbstractModifyDataChange {
    @Override
    public SqlStatement[] generateStatements(Database database) {
        DeleteStatement statement = new DeleteStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
        statement.setWhere(this.where);
        for (ColumnConfig whereParam : this.whereParams) {
            if (whereParam.getName() != null) {
                statement.addWhereColumnName(whereParam.getName());
            }
            statement.addWhereParameter(whereParam.getValueObject());
        }
        return new SqlStatement[]{statement};
    }

    @Override
    public String getConfirmationMessage() {
        return "Data deleted from " + this.getTableName();
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

