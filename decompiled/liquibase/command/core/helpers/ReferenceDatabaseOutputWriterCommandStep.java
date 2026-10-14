/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import liquibase.command.core.helpers.AbstractOutputWriterCommandStep;
import liquibase.command.providers.ReferenceDatabase;

public class ReferenceDatabaseOutputWriterCommandStep
extends AbstractOutputWriterCommandStep {
    @Override
    public Class<?> getProvidedWriterDependency() {
        return ReferenceDatabaseWriter.class;
    }

    @Override
    public Class<?> getDatabaseDependency() {
        return ReferenceDatabase.class;
    }

    public static class ReferenceDatabaseWriter {
    }
}

