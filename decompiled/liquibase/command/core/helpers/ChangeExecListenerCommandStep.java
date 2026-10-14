/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.AbstractHelperCommandStep;
import liquibase.database.Database;
import liquibase.integration.commandline.ChangeExecListenerUtils;

public class ChangeExecListenerCommandStep
extends AbstractHelperCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"changeExecListener"};
    public static final CommandArgumentDefinition<String> CHANGE_EXEC_LISTENER_CLASS_ARG;
    public static final CommandArgumentDefinition<String> CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG;
    public static final CommandArgumentDefinition<ChangeExecListener> CHANGE_EXEC_LISTENER_ARG;

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.singletonList(ChangeExecListener.class);
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.singletonList(Database.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        commandScope.provideDependency(ChangeExecListener.class, commandScope.getArgumentValue(CHANGE_EXEC_LISTENER_ARG) != null ? commandScope.getArgumentValue(CHANGE_EXEC_LISTENER_ARG) : this.getChangeExecListener(commandScope, database));
    }

    private DefaultChangeExecListener getChangeExecListener(CommandScope commandScope, Database database) throws Exception {
        DefaultChangeExecListener changeExecListener = new DefaultChangeExecListener(new ChangeExecListener[0]);
        ChangeExecListener listener = ChangeExecListenerUtils.getChangeExecListener(database, Scope.getCurrentScope().getResourceAccessor(), commandScope.getArgumentValue(CHANGE_EXEC_LISTENER_CLASS_ARG), commandScope.getArgumentValue(CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG));
        changeExecListener.addListener(listener);
        return changeExecListener;
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        CHANGE_EXEC_LISTENER_CLASS_ARG = builder.argument("changeExecListenerClass", String.class).description("Fully-qualified class which specifies a ChangeExecListener").build();
        CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG = builder.argument("changeExecListenerPropertiesFile", String.class).description("Path to a properties file for the ChangeExecListenerClass").build();
        CHANGE_EXEC_LISTENER_ARG = builder.argument("changeExecListener", ChangeExecListener.class).hidden().build();
    }
}

