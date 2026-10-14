/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.statement.AbstractSqlStatement;

public class DeleteStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private String where;
    private final List<Object> whereParameters = new ArrayList<Object>();
    private final List<String> whereColumnNames = new ArrayList<String>();

    public DeleteStatement(String catalogName, String schemaName, String tableName) {
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

    public String getWhere() {
        return this.where;
    }

    public DeleteStatement setWhere(String where) {
        this.where = where;
        return this;
    }

    public String getWhereClause() {
        return this.getWhere();
    }

    public DeleteStatement setWhereClause(String whereClause) {
        return this.setWhere(whereClause);
    }

    public DeleteStatement addWhereParameter(Object value) {
        this.whereParameters.add(value);
        return this;
    }

    public DeleteStatement addWhereParameters(Object ... value) {
        this.whereParameters.addAll(Arrays.asList(value));
        return this;
    }

    public DeleteStatement addWhereColumnName(String value) {
        this.whereColumnNames.add(value);
        return this;
    }

    public List<Object> getWhereParameters() {
        return this.whereParameters;
    }

    public List<String> getWhereColumnNames() {
        return this.whereColumnNames;
    }
}

