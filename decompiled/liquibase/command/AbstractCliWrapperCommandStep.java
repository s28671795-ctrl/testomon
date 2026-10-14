/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.exception.CommandExecutionException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.integration.commandline.Main;

@Deprecated
public abstract class AbstractCliWrapperCommandStep
extends AbstractCommandStep {
    @Override
    public final void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope;
        String[] args2;
        int statusCode;
        this.preRunCheck(resultsBuilder);
        OutputStream outputStream2 = resultsBuilder.getOutputStream();
        PrintStream printStream = null;
        if (outputStream2 != null) {
            printStream = new PrintStream(outputStream2);
            Main.setOutputStream(printStream);
        }
        if ((statusCode = Main.run(args2 = this.collectArguments(commandScope = resultsBuilder.getCommandScope()))) != 0) {
            throw new CommandExecutionException("Unexpected error running liquibase");
        }
        resultsBuilder.addResult("statusCode", (Object)statusCode);
        if (printStream != null) {
            printStream.close();
        }
    }

    protected void preRunCheck(CommandResultsBuilder resultsBuilder) {
    }

    protected abstract String[] collectArguments(CommandScope var1) throws CommandExecutionException;

    protected String[] collectArguments(CommandScope commandScope, List<String> commandArguments, String positionalArgumentName) throws CommandExecutionException {
        if (commandArguments == null) {
            commandArguments = Collections.emptyList();
        }
        List<String> finalLegacyCommandArguments = commandArguments;
        ArrayList<String> argsList = new ArrayList<String>();
        SortedMap<String, CommandArgumentDefinition<?>> arguments = commandScope.getCommand().getArguments();
        arguments.forEach((key, value) -> {
            String argValue;
            if (value.getHidden() && !LiquibaseCommandLineConfiguration.SHOW_HIDDEN_ARGS.getCurrentValue().booleanValue()) {
                return;
            }
            if (finalLegacyCommandArguments.contains(key)) {
                return;
            }
            if (positionalArgumentName != null && positionalArgumentName.equalsIgnoreCase((String)key)) {
                return;
            }
            Object argumentValue = commandScope.getArgumentValue(value);
            String string = argValue = argumentValue != null ? argumentValue.toString() : null;
            if (argValue != null) {
                argsList.add("--" + key);
                argsList.add(argumentValue.toString());
            }
        });
        argsList.add(commandScope.getCommand().getName()[0]);
        arguments.forEach((key, value) -> {
            String argValue;
            if (key.equalsIgnoreCase(positionalArgumentName)) {
                String argValue2;
                Object argumentValue = commandScope.getArgumentValue(value);
                String string = argValue2 = argumentValue != null ? argumentValue.toString() : null;
                if (argValue2 != null) {
                    argsList.add(argValue2);
                }
                return;
            }
            if (!finalLegacyCommandArguments.contains(key)) {
                return;
            }
            Object argumentValue = commandScope.getArgumentValue(value);
            String string = argValue = argumentValue != null ? argumentValue.toString() : null;
            if (argValue != null) {
                argsList.add("--" + key);
                argsList.add(argumentValue.toString());
            }
        });
        String[] args2 = new String[argsList.size()];
        argsList.toArray(args2);
        return args2;
    }

    protected String[] removeArgumentValues(String[] allArguments, String ... argumentsThatTakeNoValue) {
        ArrayList<String> returnArgs = new ArrayList<String>();
        HashSet<String> argsToStrip = new HashSet<String>(Arrays.asList(argumentsThatTakeNoValue));
        Iterator<String> iterator2 = Arrays.asList(allArguments).iterator();
        while (iterator2.hasNext()) {
            String arg = iterator2.next();
            returnArgs.add(arg);
            if (!argsToStrip.contains(arg.replace("--", "")) || !iterator2.hasNext()) continue;
            iterator2.next();
        }
        return returnArgs.toArray(new String[0]);
    }
}

