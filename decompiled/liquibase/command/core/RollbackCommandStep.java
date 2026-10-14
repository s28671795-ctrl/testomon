/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Arrays;
import java.util.List;
import liquibase.Scope;
import liquibase.TagVersionEnum;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.AfterTagChangeSetFilter;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.AbstractRollbackCommandStep;
import liquibase.database.Database;
import liquibase.logging.mdc.customobjects.ExceptionDetails;
import liquibase.report.RollbackReportParameters;
import liquibase.util.StringUtil;

public class RollbackCommandStep
extends AbstractRollbackCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"rollback"};
    public static final CommandArgumentDefinition<String> TAG_ARG;
    public static final CommandArgumentDefinition<String> TAG_VERSION_ARG;

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        RollbackReportParameters rollbackReportParameters = new RollbackReportParameters();
        rollbackReportParameters.setCommandTitle(StringUtil.upperCaseFirst(Arrays.toString(this.defineCommandNames()[0]).replace("[", "").replace("]", "").replace("rollback", "rollback ").trim()));
        resultsBuilder.addResult("rollbackReport", rollbackReportParameters);
        CommandScope commandScope = resultsBuilder.getCommandScope();
        String tagToRollBackTo = commandScope.getArgumentValue(TAG_ARG);
        Scope.getCurrentScope().addMdcValue("rollbackToTag", tagToRollBackTo);
        Database database = (Database)commandScope.getDependency(Database.class);
        rollbackReportParameters.setupDatabaseInfo(database);
        rollbackReportParameters.setRollbackTag(tagToRollBackTo);
        List<RanChangeSet> ranChangeSetList = database.getRanChangeSetList();
        TagVersionEnum tagVersion = TagVersionEnum.valueOf(commandScope.getArgumentValue(TAG_VERSION_ARG));
        try {
            AfterTagChangeSetFilter afterTagChangeSetFilter = new AfterTagChangeSetFilter(tagToRollBackTo, ranChangeSetList, tagVersion);
            this.doRollback(resultsBuilder, ranChangeSetList, afterTagChangeSetFilter, rollbackReportParameters);
        }
        catch (Exception exception) {
            rollbackReportParameters.setSuccess(false);
            String source2 = ExceptionDetails.findSource(database);
            ExceptionDetails exceptionDetails = new ExceptionDetails(exception, source2);
            rollbackReportParameters.setRollbackException(exceptionDetails);
            throw exception;
        }
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Rollback changes made to the database based on the specific tag");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        TAG_ARG = builder.argument("tag", String.class).required().description("Tag to rollback to").build();
        TAG_VERSION_ARG = builder.argument("tagVersion", String.class).description("Tag version to use for multiple occurrences of a tag").setValueHandler(TagVersionEnum::handleTagVersionInput).defaultValue(TagVersionEnum.OLDEST.name()).build();
        builder.addArgument(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG).build();
    }
}

