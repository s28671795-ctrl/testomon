/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import liquibase.statement.DatabaseFunction;

public class SequenceCurrentValueFunction
extends DatabaseFunction {
    public SequenceCurrentValueFunction(String sequenceName) {
        super(sequenceName);
    }

    public SequenceCurrentValueFunction(String sequenceSchemaName, String sequenceName) {
        super(sequenceSchemaName, sequenceName);
    }

    public String getSequenceSchemaName() {
        return this.getSchemaName();
    }

    public void setSequenceSchemaName(String sequenceSchemaName) {
        super.setSchemaName(sequenceSchemaName);
    }
}

