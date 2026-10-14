/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.io.Writer;
import liquibase.command.core.helpers.AbstractOutputWriterCommandStep;
import liquibase.database.Database;

public class OutputWriterCommandStep
extends AbstractOutputWriterCommandStep {
    @Override
    public Class<?> getProvidedWriterDependency() {
        return Writer.class;
    }

    @Override
    public Class<?> getDatabaseDependency() {
        return Database.class;
    }
}

