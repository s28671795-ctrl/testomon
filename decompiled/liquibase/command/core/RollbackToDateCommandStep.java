/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ExecutedAfterChangeSetFilter;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.AbstractRollbackCommandStep;
import liquibase.database.Database;
import liquibase.report.RollbackReportParameters;
import liquibase.util.StringUtil;

public class RollbackToDateCommandStep
extends AbstractRollbackCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"rollbackToDate"};
    public static final CommandArgumentDefinition<Date> DATE_ARG;

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Date dateToRollBackTo = commandScope.getArgumentValue(DATE_ARG);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String stringDateToRollbackTo = formatter.format(dateToRollBackTo);
        Scope.getCurrentScope().addMdcValue("rollbackToDate", stringDateToRollbackTo);
        RollbackReportParameters rollbackReportParameters = new RollbackReportParameters();
        rollbackReportParameters.setCommandTitle(StringUtil.upperCaseFirst(StringUtil.toKabobCase(Arrays.toString(this.defineCommandNames()[0])).replace("[", "").replace("]", "").trim()));
        resultsBuilder.addResult("rollbackReport", rollbackReportParameters);
        Database database = (Database)commandScope.getDependency(Database.class);
        rollbackReportParameters.setupDatabaseInfo(database);
        rollbackReportParameters.setRollbackDate(stringDateToRollbackTo);
        List<RanChangeSet> ranChangeSetList = database.getRanChangeSetList();
        Scope.child(Collections.singletonMap("rollbackReport", rollbackReportParameters), () -> this.doRollback(resultsBuilder, ranChangeSetList, new ExecutedAfterChangeSetFilter(dateToRollBackTo, ranChangeSetList), rollbackReportParameters));
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Rollback changes made to the database based on the specific date");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        DATE_ARG = builder.argument("date", Date.class).required().description("Date to rollback changes to").build();
        builder.addArgument(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG).build();
    }
}

