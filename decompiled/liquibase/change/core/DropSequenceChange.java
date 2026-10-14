/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.DropSequenceStatement;
import liquibase.structure.core.Sequence;

@DatabaseChange(name="dropSequence", description="Drop an existing sequence", priority=1, appliesTo={"sequence"})
public class DropSequenceChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String sequenceName;

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

    @DatabaseChangeProperty(mustEqualExisting="sequence", description="Name of the sequence to drop")
    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        DropSequenceStatement statement = new DropSequenceStatement(this.getCatalogName(), this.getSchemaName(), this.getSequenceName());
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(!SnapshotGeneratorFactory.getInstance().has(new Sequence(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()), database), "Sequence exists");
        }
        catch (Exception e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Sequence " + this.getSequenceName() + " dropped";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

