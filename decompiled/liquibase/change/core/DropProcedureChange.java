/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropProcedureStatement;

@DatabaseChange(name="dropProcedure", description="Drops an existing procedure", priority=101, appliesTo={"storedProcedure"})
public class DropProcedureChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String procedureName;

    @DatabaseChangeProperty(mustEqualExisting="storedProcedure.catalog", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="storedProcedure.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="storedProcedure", description="Name of the stored procedure to drop", exampleValue="new_customer")
    public String getProcedureName() {
        return this.procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    @Override
    public String getConfirmationMessage() {
        return "Stored Procedure " + this.getProcedureName() + " dropped";
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new DropProcedureStatement(this.getCatalogName(), this.getSchemaName(), this.getProcedureName())};
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

