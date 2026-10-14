/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.CountChangeSetFilter;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.AbstractRollbackCommandStep;
import liquibase.database.Database;
import liquibase.report.RollbackReportParameters;
import liquibase.util.StringUtil;

public class RollbackCountCommandStep
extends AbstractRollbackCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"rollbackCount"};
    public static final CommandArgumentDefinition<Integer> COUNT_ARG;

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Integer changesToRollback = commandScope.getArgumentValue(COUNT_ARG);
        Scope.getCurrentScope().addMdcValue("rollbackCount", String.valueOf(changesToRollback));
        RollbackReportParameters rollbackReportParameters = new RollbackReportParameters();
        rollbackReportParameters.setCommandTitle(StringUtil.upperCaseFirst(StringUtil.toKabobCase(Arrays.toString(this.defineCommandNames()[0])).replace("[", "").replace("]", "").trim()));
        resultsBuilder.addResult("rollbackReport", rollbackReportParameters);
        Database database = (Database)commandScope.getDependency(Database.class);
        rollbackReportParameters.setupDatabaseInfo(database);
        rollbackReportParameters.setRollbackCount(changesToRollback);
        List<RanChangeSet> ranChangeSetList = database.getRanChangeSetList();
        Scope.child(Collections.singletonMap("rollbackReport", rollbackReportParameters), () -> this.doRollback(resultsBuilder, ranChangeSetList, new CountChangeSetFilter(changesToRollback), rollbackReportParameters));
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Rollback the specified number of changes made to the database");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        COUNT_ARG = builder.argument("count", Integer.class).required().description("The number of changes to rollback").build();
        builder.addArgument(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG).build();
    }
}

