/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RenameSequenceStatement;

@DatabaseChange(name="renameSequence", description="Renames an existing sequence", priority=1, appliesTo={"sequence"})
public class RenameSequenceChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String oldSequenceName;
    private String newSequenceName;

    @DatabaseChangeProperty(mustEqualExisting="sequence.catalog", since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="sequence.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="sequence", description="Name of the existing sequence to rename")
    public String getOldSequenceName() {
        return this.oldSequenceName;
    }

    public void setOldSequenceName(String oldSequenceName) {
        this.oldSequenceName = oldSequenceName;
    }

    @DatabaseChangeProperty(description="New name for the sequence")
    public String getNewSequenceName() {
        return this.newSequenceName;
    }

    public void setNewSequenceName(String newSequenceName) {
        this.newSequenceName = newSequenceName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ArrayList<RenameSequenceStatement> statements = new ArrayList<RenameSequenceStatement>();
        statements.add(new RenameSequenceStatement(this.getCatalogName(), this.getSchemaName(), this.getOldSequenceName(), this.getNewSequenceName()));
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    protected Change[] createInverses() {
        RenameSequenceChange inverse = new RenameSequenceChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setOldSequenceName(this.getNewSequenceName());
        inverse.setNewSequenceName(this.getOldSequenceName());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Sequence " + this.oldSequenceName + " renamed to " + this.newSequenceName;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

