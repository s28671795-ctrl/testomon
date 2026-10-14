/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.sqlgenerator.core.SetTableRemarksGenerator;
import liquibase.statement.core.SetTableRemarksStatement;

public class SetTableRemarksGeneratorSnowflake
extends SetTableRemarksGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(SetTableRemarksStatement statement, Database database) {
        return database instanceof SnowflakeDatabase;
    }
}

