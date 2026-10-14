/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import liquibase.statement.AbstractSqlStatement;

public class UpdateStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final SortedMap<String, Object> newColumnValues = new TreeMap<String, Object>();
    private String whereClause;
    private final List<String> whereColumnNames = new ArrayList<String>();
    private final List<Object> whereParameters = new ArrayList<Object>();

    public UpdateStatement(String catalogName, String schemaName, String tableName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
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

    public UpdateStatement addNewColumnValue(String columnName, Object newValue) {
        this.newColumnValues.put(columnName, newValue);
        return this;
    }

    public String getWhereClause() {
        return this.whereClause;
    }

    public UpdateStatement setWhereClause(String whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public UpdateStatement addWhereParameter(Object value) {
        this.whereParameters.add(value);
        return this;
    }

    public UpdateStatement addWhereParameters(Object ... value) {
        this.whereParameters.addAll(Arrays.asList(value));
        return this;
    }

    public UpdateStatement addWhereColumnName(String value) {
        this.whereColumnNames.add(value);
        return this;
    }

    public Map<String, Object> getNewColumnValues() {
        return this.newColumnValues;
    }

    public List<Object> getWhereParameters() {
        return this.whereParameters;
    }

    public List<String> getWhereColumnNames() {
        return this.whereColumnNames;
    }
}

