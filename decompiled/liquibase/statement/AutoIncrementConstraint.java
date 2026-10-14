/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.math.BigInteger;
import liquibase.statement.ColumnConstraint;

public class AutoIncrementConstraint
implements ColumnConstraint {
    private String columnName;
    private BigInteger startWith;
    private BigInteger incrementBy;
    private String generationType;
    private Boolean defaultOnNull;

    public AutoIncrementConstraint() {
    }

    public AutoIncrementConstraint(String columnName) {
        this.setColumnName(columnName);
    }

    public AutoIncrementConstraint(String columnName, BigInteger startWith, BigInteger incrementBy) {
        this(columnName);
        this.setStartWith(startWith);
        this.setIncrementBy(incrementBy);
    }

    public AutoIncrementConstraint(String columnName, BigInteger startWith, BigInteger incrementBy, String generationType, Boolean defaultOnNull) {
        this(columnName, startWith, incrementBy);
        this.setGenerationType(generationType);
        this.setDefaultOnNull(defaultOnNull);
    }

    public AutoIncrementConstraint setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public AutoIncrementConstraint setStartWith(BigInteger startWith) {
        this.startWith = startWith;
        return this;
    }

    public AutoIncrementConstraint setIncrementBy(BigInteger incrementBy) {
        this.incrementBy = incrementBy;
        return this;
    }

    public AutoIncrementConstraint setGenerationType(String generationType) {
        this.generationType = generationType;
        return this;
    }

    public AutoIncrementConstraint setDefaultOnNull(Boolean defaultOnNull) {
        this.defaultOnNull = defaultOnNull;
        return this;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public BigInteger getStartWith() {
        return this.startWith;
    }

    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public String getGenerationType() {
        return this.generationType;
    }

    public Boolean getDefaultOnNull() {
        return this.defaultOnNull;
    }
}

