/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.math.BigInteger;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AlterSequenceStatement;
import liquibase.structure.core.Sequence;

@DatabaseChange(name="alterSequence", description="Alter properties of an existing sequence", priority=1, appliesTo={"sequence"})
public class AlterSequenceChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String sequenceName;
    private BigInteger incrementBy;
    private BigInteger maxValue;
    private BigInteger minValue;
    private Boolean ordered;
    private BigInteger cacheSize;
    private Boolean cycle;
    private String dataType;

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

    @DatabaseChangeProperty(mustEqualExisting="sequence", description="Name of the sequence")
    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    @DatabaseChangeProperty(description="New amount the sequence should increment by at each call")
    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public void setIncrementBy(BigInteger incrementBy) {
        this.incrementBy = incrementBy;
    }

    @DatabaseChangeProperty(description="New maximum value for the sequence")
    public BigInteger getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(BigInteger maxValue) {
        this.maxValue = maxValue;
    }

    @DatabaseChangeProperty(description="New minimum value for the sequence")
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

    @DatabaseChangeProperty(description="Number of values to fetch per query")
    public BigInteger getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(BigInteger cacheSize) {
        this.cacheSize = cacheSize;
    }

    @DatabaseChangeProperty(description="Whether the sequence cycles when it hits its max value")
    public Boolean getCycle() {
        return this.cycle;
    }

    public void setCycle(Boolean cycle) {
        this.cycle = cycle;
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
        return new SqlStatement[]{new AlterSequenceStatement(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()).setIncrementBy(this.getIncrementBy()).setMaxValue(this.getMaxValue()).setMinValue(this.getMinValue()).setCacheSize(this.getCacheSize()).setCycle(this.getCycle()).setOrdered(this.isOrdered()).setDataType(this.getDataType())};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            Sequence sequence2 = SnapshotGeneratorFactory.getInstance().createSnapshot(new Sequence(this.getCatalogName(), this.getSchemaName(), this.getSequenceName()), database);
            if (sequence2 == null) {
                return result.unknown("Sequence " + this.getSequenceName() + " does not exist");
            }
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
            if (this.getCacheSize() != null) {
                result.assertCorrect(this.getCacheSize().equals(sequence2.getCacheSize()), "Cache size is different");
            }
            if (this.getDataType() != null) {
                result.assertCorrect(this.getDataType().equals(sequence2.getDataType()), "Data type is different");
            }
        }
        catch (Exception e) {
            return result.unknown(e);
        }
        return result;
    }

    @Override
    public String getConfirmationMessage() {
        return "Sequence " + this.getSequenceName() + " altered";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

