/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.ChangelogSyncCommandStep;

public class ChangelogSyncToTagCommandStep
extends ChangelogSyncCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"changelogSyncToTag"};
    public static final CommandArgumentDefinition<String> TAG_ARG;

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
        commandDefinition.setShortDescription("Marks all undeployed changesets as executed, up to a tag");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        TAG_ARG = builder.argument("tag", String.class).required().description("Tag ID to execute changelogSync to").build();
    }
}

