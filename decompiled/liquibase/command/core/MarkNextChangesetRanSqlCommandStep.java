/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import liquibase.command.CommandDefinition;
import liquibase.command.core.MarkNextChangesetRanCommandStep;

public class MarkNextChangesetRanSqlCommandStep
extends MarkNextChangesetRanCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"markNextChangesetRanSql"};

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Writes the SQL used to mark the next change you apply as executed in your database");
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        ArrayList dependencies = new ArrayList();
        dependencies.add(Writer.class);
        dependencies.addAll(super.requiredDependencies());
        return dependencies;
    }
}

