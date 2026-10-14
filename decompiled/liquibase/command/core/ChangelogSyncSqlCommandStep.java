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
import liquibase.command.core.ChangelogSyncCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.database.Database;
import liquibase.util.LoggingExecutorTextUtil;

public class ChangelogSyncSqlCommandStep
extends ChangelogSyncCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"changelogSyncSql"};
    public static final CommandArgumentDefinition<Boolean> OUTPUT_DEFAULT_SCHEMA_ARG;
    public static final CommandArgumentDefinition<Boolean> OUTPUT_DEFAULT_CATALOG_ARG;

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
        Database database = (Database)commandScope.getDependency(Database.class);
        String changelogFile = commandScope.getArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG);
        LoggingExecutorTextUtil.outputHeader("SQL to add all changesets to database history table", database, changelogFile);
        super.run(resultsBuilder);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Output the raw SQL used by Liquibase when running changelogSync");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        OUTPUT_DEFAULT_SCHEMA_ARG = builder.argument("outputDefaultSchema", Boolean.class).description("Control whether names of objects in the default schema are fully qualified or not. If true they are. If false, only objects outside the default schema are fully qualified").defaultValue(true).build();
        OUTPUT_DEFAULT_CATALOG_ARG = builder.argument("outputDefaultCatalog", Boolean.class).description("Control whether names of objects in the default catalog are fully qualified or not. If true they are. If false, only objects outside the default catalog are fully qualified").defaultValue(true).build();
    }
}

