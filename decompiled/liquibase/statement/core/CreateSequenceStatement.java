/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.math.BigInteger;
import liquibase.statement.AbstractSqlStatement;

public class CreateSequenceStatement
extends AbstractSqlStatement {
    private String catalogName;
    private final String schemaName;
    private final String sequenceName;
    private BigInteger startValue;
    private BigInteger incrementBy;
    private BigInteger maxValue;
    private BigInteger minValue;
    private Boolean ordered;
    private Boolean cycle;
    private BigInteger cacheSize;
    private String dataType;

    public CreateSequenceStatement(String catalogName, String schemaName, String sequenceName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.sequenceName = sequenceName;
    }

    @Override
    public boolean skipOnUnsupported() {
        return true;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public BigInteger getStartValue() {
        return this.startValue;
    }

    public CreateSequenceStatement setStartValue(BigInteger startValue) {
        this.startValue = startValue;
        return this;
    }

    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public CreateSequenceStatement setIncrementBy(BigInteger incrementBy) {
        this.incrementBy = incrementBy;
        return this;
    }

    public BigInteger getMaxValue() {
        return this.maxValue;
    }

    public CreateSequenceStatement setMaxValue(BigInteger maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public BigInteger getMinValue() {
        return this.minValue;
    }

    public CreateSequenceStatement setMinValue(BigInteger minValue) {
        this.minValue = minValue;
        return this;
    }

    public Boolean getOrdered() {
        return this.ordered;
    }

    public CreateSequenceStatement setOrdered(Boolean ordered) {
        this.ordered = ordered;
        return this;
    }

    public Boolean getCycle() {
        return this.cycle;
    }

    public CreateSequenceStatement setCycle(Boolean cycle) {
        this.cycle = cycle;
        return this;
    }

    public BigInteger getCacheSize() {
        return this.cacheSize;
    }

    public CreateSequenceStatement setCacheSize(BigInteger cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    public String getDataType() {
        return this.dataType;
    }

    public CreateSequenceStatement setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }
}

