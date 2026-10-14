/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  picocli.CommandLine
 *  picocli.CommandLine$Model$CommandSpec
 */
package liquibase.integration.commandline;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import liquibase.Scope;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.integration.commandline.LiquibaseCommandLine;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.io.OutputFileHandler;
import liquibase.io.OutputFileHandlerFactory;
import liquibase.util.StringUtil;
import picocli.CommandLine;

class CommandRunner
implements Callable<CommandResults> {
    private CommandLine.Model.CommandSpec spec;

    CommandRunner() {
    }

    @Override
    public CommandResults call() throws Exception {
        ArrayList<String> command = new ArrayList<String>();
        command.add(this.spec.commandLine().getCommandName());
        CommandLine parentCommand = this.spec.commandLine().getParent();
        while (!parentCommand.getCommandName().equals("liquibase")) {
            command.add(0, parentCommand.getCommandName());
            parentCommand = parentCommand.getParent();
        }
        String[] commandName = LiquibaseCommandLine.getCommandNames(this.spec.commandLine());
        for (int i = 0; i < commandName.length; ++i) {
            commandName[i] = StringUtil.toCamelCase(commandName[i]);
        }
        CommandScope commandScope = new CommandScope(commandName);
        String outputFile = LiquibaseCommandLineConfiguration.OUTPUT_FILE.getCurrentValue();
        OutputFileHandlerFactory outputFileHandlerFactory = Scope.getCurrentScope().getSingleton(OutputFileHandlerFactory.class);
        try (OutputFileHandler outputFileHandler = outputFileHandlerFactory.getOutputFileHandler(outputFile);){
            if (outputFile != null) {
                outputFileHandler.create(outputFile, commandScope);
            }
            CommandResults commandResults = commandScope.execute();
            return commandResults;
        }
    }

    public void setSpec(CommandLine.Model.CommandSpec spec) {
        this.spec = spec;
    }
}

