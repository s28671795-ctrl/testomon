/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.util.HelpUtil;

public class ShowSummaryArgument
extends AbstractCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"showSummary"};
    public static final CommandArgumentDefinition<UpdateSummaryEnum> SHOW_SUMMARY;
    public static final CommandArgumentDefinition<UpdateSummaryOutputEnum> SHOW_SUMMARY_OUTPUT;

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.singletonList(UpdateSummaryEnum.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        UpdateSummaryEnum showSummaryArgument = commandScope.getArgumentValue(SHOW_SUMMARY);
        UpdateSummaryOutputEnum showSummaryOutputArgument = commandScope.getArgumentValue(SHOW_SUMMARY_OUTPUT);
        commandScope.provideDependency(UpdateSummaryEnum.class, (Object)showSummaryArgument);
        commandScope.provideDependency(UpdateSummaryOutputEnum.class, (Object)showSummaryOutputArgument);
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        HelpUtil.hideCommandNameInHelpView(commandDefinition);
    }

    static {
        CommandBuilder commandBuilder = new CommandBuilder(new String[][]{COMMAND_NAME});
        SHOW_SUMMARY = commandBuilder.argument("showSummary", UpdateSummaryEnum.class).description("Type of update results summary to show.  Values can be 'off', 'summary', or 'verbose'.").defaultValue(UpdateSummaryEnum.SUMMARY).setValueHandler(value -> {
            if (value == null) {
                return null;
            }
            if (value instanceof String && !value.equals("")) {
                List<String> validValues = Arrays.asList("OFF", "SUMMARY", "VERBOSE");
                if (!validValues.contains(((String)value).toUpperCase())) {
                    throw new IllegalArgumentException("Illegal value for `showUpdateSummary'.  Valid values are 'OFF', 'SUMMARY', or 'VERBOSE'");
                }
                return UpdateSummaryEnum.valueOf(((String)value).toUpperCase());
            }
            if (value instanceof UpdateSummaryEnum) {
                return (UpdateSummaryEnum)((Object)((Object)value));
            }
            return null;
        }).build();
        SHOW_SUMMARY_OUTPUT = commandBuilder.argument("showSummaryOutput", UpdateSummaryOutputEnum.class).description("Summary output to report update summary results. Values can be 'log', 'console', or 'all'.").defaultValue(UpdateSummaryOutputEnum.ALL).setValueHandler(value -> {
            if (value == null) {
                return null;
            }
            if (value instanceof String && !value.equals("")) {
                List<String> validValues = Arrays.asList("LOG", "CONSOLE", "ALL");
                if (!validValues.contains(((String)value).toUpperCase())) {
                    throw new IllegalArgumentException("Illegal value for `showSummaryOutput'.  Valid values are 'LOG', 'CONSOLE', or 'ALL'");
                }
                return UpdateSummaryOutputEnum.valueOf(((String)value).toUpperCase());
            }
            if (value instanceof UpdateSummaryOutputEnum) {
                return (UpdateSummaryOutputEnum)((Object)((Object)value));
            }
            return null;
        }).build();
    }
}

