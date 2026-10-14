/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class CreateProcedureStatement
extends AbstractSqlStatement {
    private final String catalogName;
    private final String schemaName;
    private final String procedureName;
    private final String procedureText;
    private final String endDelimiter;
    private Boolean replaceIfExists;

    public CreateProcedureStatement(String catalogName, String schemaName, String procedureName, String procedureText, String endDelimiter) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.procedureName = procedureName;
        this.procedureText = procedureText;
        this.endDelimiter = endDelimiter;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getProcedureName() {
        return this.procedureName;
    }

    public String getProcedureText() {
        return this.procedureText;
    }

    public String getEndDelimiter() {
        return this.endDelimiter;
    }

    public Boolean getReplaceIfExists() {
        return this.replaceIfExists;
    }

    public void setReplaceIfExists(Boolean replaceIfExists) {
        this.replaceIfExists = replaceIfExists;
    }
}

