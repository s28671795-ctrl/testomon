/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import liquibase.statement.SqlStatement;

public abstract class AbstractSqlStatement
implements SqlStatement {
    private boolean continueOnError;

    @Override
    public boolean skipOnUnsupported() {
        return false;
    }

    @Override
    public boolean continueOnError() {
        return this.continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }
}

