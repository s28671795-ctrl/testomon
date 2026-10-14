/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import liquibase.statement.DatabaseFunction;

public class SequenceNextValueFunction
extends DatabaseFunction {
    public SequenceNextValueFunction() {
        this("UNSET");
    }

    public SequenceNextValueFunction(String sequenceName) {
        super(sequenceName);
    }

    public SequenceNextValueFunction(String sequenceSchemaName, String sequenceName) {
        super(sequenceSchemaName, sequenceName);
    }

    public String getSequenceSchemaName() {
        return this.getSchemaName();
    }

    public void setSequenceSchemaName(String sequenceSchemaName) {
        super.setSchemaName(sequenceSchemaName);
    }
}

