/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.math.BigInteger;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropSequenceChange;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateSequenceStatement;
import liquibase.structure.core.Sequence;

@DatabaseChange(name="createSequence", description="Creates a new database sequence", priority=1)
public class CreateSequenceChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String sequenceName;
    private BigInteger startValue;
    private BigInteger incrementBy;
    private BigInteger maxValue;
    private BigInteger minValue;
    private Boolean ordered;
    private Boolean cycle;
    private BigInteger cacheSize;
    private String dataType;

    @DatabaseChangeProperty(since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(description="Name of the sequence to create")
    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    @DatabaseChangeProperty(description="First sequence number to be generated.", exampleValue="5")
    public BigInteger getStartValue() {
        return this.startValue;
    }

    public void setStartValue(BigInteger startValue) {
        this.startValue = startValue;
    }

    @DatabaseChangeProperty(description="Interval between sequence numbers", exampleValue="2")
    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public void setIncrementBy(BigInteger incrementBy) {
        this.incrementBy = incrementBy;
    }

    @DatabaseChangeProperty(description="Maximum value of the sequence", exampleValue="1000")
    public BigInteger getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(BigInteger maxValue) {
        this.maxValue = maxValue;
    }

    @DatabaseChangeProperty(description="Minimum value of the sequence", exampleValue="10")
    public BigInteger getMinValue() {
        return this.minValue;
    }

    public void setMinValue(BigInteger minValue) {
        this.minValue = minValue;
    }

    @DatabaseChangeProperty(description="Whether the sequence is generated in the requested order")
    public Boolean isOrdered() {
        return this.ordered;
    }

    public void setOrdered(Boolean ordered) {
        this.ordered = ordered;
    }

    @DatabaseChangeProperty(description="Whether the sequence cycles when it hits its max value")
    public Boolean getCycle() {
        return this.cycle;
    }

    public void setCycle(Boolean cycle) {
        this.cycle = cycle;
    }

    @DatabaseChangeProperty(description="Number of values to fetch per query")
    public BigInteger getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(BigInteger cacheSize) {
        this.cacheSize = cacheSize;
    }

    @DatabaseChangeProperty(description="Data type of the sequence")
    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{new CreateSequenceStatement(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()).setIncrementBy(this.getIncrementBy()).setMaxValue(this.getMaxValue()).setMinValue(this.getMinValue()).setOrdered(this.isOrdered()).setStartValue(this.getStartValue()).setCycle(this.getCycle()).setCacheSize(this.getCacheSize()).setDataType(this.getDataType())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            Sequence sequence2 = SnapshotGeneratorFactory.getInstance().createSnapshot(new Sequence(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()), database);
            result.assertComplete(sequence2 != null, "Sequence " + this.getSequenceName() + " does not exist");
            if (sequence2 != null) {
                if (this.getIncrementBy() != null) {
                    result.assertCorrect(this.getIncrementBy().equals(sequence2.getIncrementBy()), "Increment by has a different value");
                }
                if (this.getMinValue() != null) {
                    result.assertCorrect(this.getMinValue().equals(sequence2.getMinValue()), "Min Value is different");
                }
                if (this.getMaxValue() != null) {
                    result.assertCorrect(this.getMaxValue().equals(sequence2.getMaxValue()), "Max Value is different");
                }
                if (this.isOrdered() != null) {
                    result.assertCorrect(this.isOrdered().equals(sequence2.getOrdered()), "Max Value is different");
                }
                if (this.getCycle() != null) {
                    result.assertCorrect(this.getCycle().equals(sequence2.getWillCycle()), "Will Cycle is different");
                }
                if (this.getCacheSize() != null) {
                    result.assertCorrect(this.getCacheSize().equals(sequence2.getCacheSize()), "Cache size is different");
                }
                if (this.getDataType() != null) {
                    result.assertCorrect(this.getDataType().equals(sequence2.getDataType()), "Data type is different");
                }
            }
        }
        catch (Exception e) {
            return result.unknown(e);
        }
        return result;
    }

    @Override
    protected Change[] createInverses() {
        DropSequenceChange inverse = new DropSequenceChange();
        inverse.setSequenceName(this.getSequenceName());
        inverse.setSchemaName(this.getSchemaName());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Sequence " + this.getSequenceName() + " created";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

