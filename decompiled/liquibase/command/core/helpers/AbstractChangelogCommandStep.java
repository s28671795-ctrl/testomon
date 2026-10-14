/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import liquibase.Scope;
import liquibase.change.ChangeFactory;
import liquibase.change.ReplaceIfExists;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandScope;
import liquibase.exception.CommandValidationException;

public abstract class AbstractChangelogCommandStep
extends AbstractCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"abstractChangelogCommandStep"};
    public static final CommandArgumentDefinition<String> RUN_ON_CHANGE_TYPES_ARG;
    public static final CommandArgumentDefinition<String> REPLACE_IF_EXISTS_TYPES_ARG;
    public static final CommandArgumentDefinition<Boolean> SKIP_OBJECT_SORTING;

    protected static void validateRunOnChangeTypes(CommandScope commandScope) throws CommandValidationException {
        ArrayList<String> runOnChangeTypes = new ArrayList<String>(Arrays.asList(commandScope.getArgumentValue(RUN_ON_CHANGE_TYPES_ARG).split("\\s*,\\s*")));
        Collection supportedRunOnChangeTypes = AbstractChangelogCommandStep.supportedRunOnChangeTypes().collect(Collectors.toList());
        supportedRunOnChangeTypes.add("none");
        runOnChangeTypes.removeAll(supportedRunOnChangeTypes);
        if (!runOnChangeTypes.isEmpty()) {
            throw new CommandValidationException("Invalid types for --run-on-change-types: " + runOnChangeTypes.stream().collect(Collectors.joining(", ")));
        }
    }

    protected static void validateReplaceIfExistsTypes(CommandScope commandScope) throws CommandValidationException {
        ArrayList<String> replaceIfExistsTypes = new ArrayList<String>(Arrays.asList(commandScope.getArgumentValue(REPLACE_IF_EXISTS_TYPES_ARG).split("\\s*,\\s*")));
        Collection supportedReplaceIfExistsTypes = AbstractChangelogCommandStep.supportedReplaceIfExistsTypes().collect(Collectors.toList());
        supportedReplaceIfExistsTypes.add("none");
        replaceIfExistsTypes.removeAll(supportedReplaceIfExistsTypes);
        if (!replaceIfExistsTypes.isEmpty()) {
            throw new CommandValidationException("Invalid types for --replace-if-exists-types: " + replaceIfExistsTypes.stream().collect(Collectors.joining(", ")));
        }
    }

    protected static Stream<String> supportedRunOnChangeTypes() {
        ChangeFactory changeFactory = Scope.getCurrentScope().getSingleton(ChangeFactory.class);
        return changeFactory.getDefinedChanges().stream();
    }

    protected static Stream<String> supportedReplaceIfExistsTypes() {
        ChangeFactory changeFactory = Scope.getCurrentScope().getSingleton(ChangeFactory.class);
        return changeFactory.getDefinedChanges().stream().filter(changeType -> changeFactory.create((String)changeType) instanceof ReplaceIfExists);
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        RUN_ON_CHANGE_TYPES_ARG = builder.argument("runOnChangeTypes", String.class).defaultValue("none").description("Sets runOnChange=\"true\" for changesets containing solely changes of these types (e. g. createView, createProcedure, ...).").build();
        String replaceIfExistsTypeNames = AbstractChangelogCommandStep.supportedReplaceIfExistsTypes().collect(Collectors.joining(", "));
        REPLACE_IF_EXISTS_TYPES_ARG = builder.argument("replaceIfExistsTypes", String.class).defaultValue("none").description(String.format("Sets replaceIfExists=\"true\" for changes of these types (supported types: %s)", replaceIfExistsTypeNames)).build();
        SKIP_OBJECT_SORTING = builder.argument("skipObjectSorting", Boolean.class).defaultValue(false).description("When true will skip object sorting. This can be useful on databases that have a lot of packages/procedures that are linked to each other").build();
    }
}

