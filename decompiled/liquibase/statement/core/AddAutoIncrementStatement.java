/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.math.BigInteger;
import liquibase.statement.AbstractSqlStatement;

public class AddAutoIncrementStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;
    private final String columnDataType;
    private final BigInteger startWith;
    private final BigInteger incrementBy;
    private final Boolean defaultOnNull;
    private final String generationType;

    public AddAutoIncrementStatement(String catalogName, String schemaName, String tableName, String columnName, String columnDataType, BigInteger startWith, BigInteger incrementBy, Boolean defaultOnNull, String generationType) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.startWith = startWith;
        this.incrementBy = incrementBy;
        this.defaultOnNull = defaultOnNull;
        this.generationType = generationType;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnDataType() {
        return this.columnDataType;
    }

    public BigInteger getStartWith() {
        return this.startWith;
    }

    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public Boolean getDefaultOnNull() {
        return this.defaultOnNull;
    }

    public String getGenerationType() {
        return this.generationType;
    }
}

