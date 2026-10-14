/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.ChangelogSyncSqlCommandStep;

public class ChangelogSyncToTagSqlCommandStep
extends ChangelogSyncSqlCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"changelogSyncToTagSql"};
    public static final CommandArgumentDefinition<String> TAG_ARG;

    @Override
    public List<Class<?>> requiredDependencies() {
        ArrayList dependencies = new ArrayList();
        dependencies.add(Writer.class);
        dependencies.addAll(super.requiredDependencies());
        return dependencies;
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        this.setTag(commandScope.getArgumentValue(TAG_ARG));
        super.run(resultsBuilder);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Output the raw SQL used by Liquibase when running changelogSyncToTag");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        TAG_ARG = builder.argument("tag", String.class).required().description("Tag ID to execute changelogSync to").build();
        builder.addArgument(OUTPUT_DEFAULT_SCHEMA_ARG).build();
        builder.addArgument(OUTPUT_DEFAULT_CATALOG_ARG).build();
    }
}

