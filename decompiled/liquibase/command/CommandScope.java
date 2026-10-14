/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandFactory;
import liquibase.command.CommandResults;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandStep;
import liquibase.configuration.AbstractMapConfigurationValueProvider;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.ConfigurationValueProvider;
import liquibase.configuration.ConfigurationValueUtils;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.CommandValidationException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.listener.LiquibaseListener;
import liquibase.logging.mdc.MdcManager;
import liquibase.logging.mdc.MdcObject;
import liquibase.logging.mdc.customobjects.ExceptionDetails;
import liquibase.util.StringUtil;

public class CommandScope {
    private static final String NO_PREFIX_REGEX = ".*\\.";
    public static final Pattern NO_PREFIX_PATTERN = Pattern.compile(".*\\.");
    private final CommandDefinition commandDefinition;
    private final SortedMap<String, Object> argumentValues = new TreeMap<String, Object>();
    private final Map<Class<?>, Object> dependencies = new HashMap();
    private final String completeConfigPrefix;
    private final String shortConfigPrefix;
    private OutputStream outputStream;
    private Date operationStartTime;

    public CommandScope(String ... commandName) throws CommandExecutionException {
        this.setOutput(System.out);
        CommandFactory commandFactory = Scope.getCurrentScope().getSingleton(CommandFactory.class);
        this.commandDefinition = commandFactory.getCommandDefinition(commandName);
        this.completeConfigPrefix = "liquibase.command." + StringUtil.join(Arrays.asList(this.getCommand().getName()), ".");
        this.shortConfigPrefix = "liquibase.command";
    }

    public CommandDefinition getCommand() {
        return this.commandDefinition;
    }

    public String getCompleteConfigPrefix() {
        return this.completeConfigPrefix;
    }

    public CommandScope addArgumentValue(String argument, Object value) {
        this.argumentValues.put(argument, value);
        return this;
    }

    public <T> CommandScope addArgumentValue(CommandArgumentDefinition<T> argument, T value) {
        this.argumentValues.put(argument.getName(), value);
        return this;
    }

    public <T> ConfiguredValue<T> getConfiguredValue(CommandArgumentDefinition<T> argument) {
        ConfigurationDefinition<T> noCommandConfigDef;
        ConfiguredValue<T> noCommandNameProvidedValue;
        ConfigurationDefinition<T> configDef = this.createConfigurationDefinition(argument, true);
        ConfiguredValue<T> providedValue = configDef.getCurrentConfiguredValue(new CommandScopeValueProvider());
        if ((!providedValue.found() || providedValue.wasDefaultValueUsed()) && (noCommandNameProvidedValue = (noCommandConfigDef = this.createConfigurationDefinition(argument, false)).getCurrentConfiguredValue()).found() && !noCommandNameProvidedValue.wasDefaultValueUsed()) {
            providedValue = noCommandNameProvidedValue;
        }
        return providedValue;
    }

    public <T> T getArgumentValue(CommandArgumentDefinition<T> argument) {
        T value = this.getConfiguredValue(argument).getValue();
        return ConfigurationValueUtils.convertDataType(argument.getName(), value, argument.getValueConverter());
    }

    public CommandScope provideDependency(Class<?> clazz, Object value) {
        this.dependencies.put(clazz, value);
        return this;
    }

    public <T> Object getDependency(Class<T> clazz) {
        return this.dependencies.get(clazz);
    }

    public CommandScope setOutput(OutputStream outputStream2) {
        this.outputStream = outputStream2 != null ? new UnclosableOutputStream(outputStream2) : null;
        return this;
    }

    public void validate() throws CommandValidationException {
        for (ConfigurationValueProvider configurationValueProvider : Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getProviders()) {
            configurationValueProvider.validate(this);
        }
        for (CommandArgumentDefinition commandArgumentDefinition : this.commandDefinition.getArguments().values()) {
            commandArgumentDefinition.validate(this);
        }
        List<CommandStep> pipeline = this.commandDefinition.getPipeline();
        Scope.getCurrentScope().getLog(this.getClass()).fine("Pipeline for command '" + StringUtil.join(this.commandDefinition.getName(), " ") + ": " + StringUtil.join(pipeline, " then ", obj -> obj.getClass().getName()));
        for (CommandStep step : pipeline) {
            step.validate(this);
        }
    }

    public CommandResults execute() throws CommandExecutionException {
        this.operationStartTime = new Date();
        Scope.getCurrentScope().addMdcValue("operationStart", Instant.ofEpochMilli(this.operationStartTime.getTime()).toString());
        if (!Scope.getCurrentScope().isMdcKeyPresent("liquibaseCommandName")) {
            Scope.getCurrentScope().addMdcValue("liquibaseCommandName", String.join((CharSequence)" ", this.commandDefinition.getName()));
        }
        CommandResultsBuilder resultsBuilder = new CommandResultsBuilder(this, this.outputStream);
        List<CommandStep> pipeline = this.commandDefinition.getPipeline();
        ArrayList<CommandStep> executedCommands = new ArrayList<CommandStep>();
        Optional<Object> thrownException = Optional.empty();
        this.validate();
        try {
            this.addOutputFileToMdc();
            for (CommandStep command : pipeline) {
                try {
                    Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", this.getCommandStepName(command));
                    Scope.getCurrentScope().getLog(CommandScope.class).fine(String.format("Executing internal command %s", this.getCommandStepName(command)));
                    command.run(resultsBuilder);
                }
                catch (Exception runException) {
                    thrownException = Optional.of(runException);
                    Scope.getCurrentScope().addMdcValue("operationOutcome", "fail", false);
                    break;
                }
                executedCommands.add(command);
            }
            Database database = (Database)this.getDependency(Database.class);
            String source2 = null;
            if (database != null) {
                source2 = ExceptionDetails.findSource(database);
            }
            for (int i = executedCommands.size() - 1; i >= 0; --i) {
                CommandStep command = pipeline.get(i);
                if (!(command instanceof CleanUpCommandStep)) continue;
                ((CleanUpCommandStep)((Object)command)).cleanUp(resultsBuilder);
            }
            if (thrownException.isPresent()) {
                if (!executedCommands.isEmpty()) {
                    this.logPrimaryExceptionToMdc((Throwable)thrownException.get(), source2);
                }
                throw (Exception)thrownException.get();
            }
            Scope.getCurrentScope().addMdcValue("operationOutcome", "success", false);
        }
        catch (Exception e) {
            if (e instanceof CommandExecutionException) {
                throw (CommandExecutionException)e;
            }
            throw new CommandExecutionException(e);
        }
        finally {
            try (MdcObject operationStopTime = Scope.getCurrentScope().addMdcValue("operationStop", Instant.ofEpochMilli(new Date().getTime()).toString());){
                Scope.getCurrentScope().getLog(this.getClass()).info("Command execution complete");
            }
            try {
                if (this.outputStream != null) {
                    this.outputStream.flush();
                }
            }
            catch (Exception e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Error flushing command output stream: " + e.getMessage(), e);
            }
        }
        return resultsBuilder.build();
    }

    private void logPrimaryExceptionToMdc(Throwable exception, String source2) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(exception, source2);
        if (exceptionDetails.getPrimaryException() != null) {
            MdcManager mdcManager = Scope.getCurrentScope().getMdcManager();
            try (MdcObject primaryExceptionObject = mdcManager.put("exceptionDetails", exceptionDetails);){
                Scope.getCurrentScope().getLog(this.getClass()).info("Logging exception.");
            }
            Scope.getCurrentScope().getUI().sendMessage("ERROR: Exception Details");
            Scope.getCurrentScope().getUI().sendMessage(exceptionDetails.getFormattedPrimaryException());
            Scope.getCurrentScope().getUI().sendMessage(exceptionDetails.getFormattedPrimaryExceptionReason());
            Scope.getCurrentScope().getUI().sendMessage(exceptionDetails.getFormattedPrimaryExceptionSource());
        }
    }

    private void addOutputFileToMdc() throws Exception {
        Scope.child((LiquibaseListener)null, () -> {
            String outputFileEncoding;
            String outputFilePath = LiquibaseCommandLineConfiguration.OUTPUT_FILE.getCurrentValue();
            if (outputFilePath != null) {
                Scope.getCurrentScope().addMdcValue("outputFile", outputFilePath);
            }
            if ((outputFileEncoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()) != null) {
                Scope.getCurrentScope().addMdcValue("outputFileEncoding", outputFileEncoding);
            }
            if (outputFilePath != null) {
                Scope.getCurrentScope().getLog(CommandScope.class).fine("Writing output to '" + outputFilePath + "' with encoding '" + outputFileEncoding + "'");
            } else {
                Scope.getCurrentScope().getLog(CommandScope.class).fine("Writing output with encoding '" + outputFileEncoding + "'");
            }
        });
    }

    private <T> ConfigurationDefinition<T> createConfigurationDefinition(CommandArgumentDefinition<T> argument, boolean includeCommandName) {
        String key = includeCommandName ? this.completeConfigPrefix : this.shortConfigPrefix;
        return new ConfigurationDefinition.Builder(key).define(argument.getName(), argument.getDataType()).addAliases(argument.getAliases()).setDefaultValue(argument.getDefaultValue()).setDescription(argument.getDescription()).setValueHandler(argument.getValueConverter()).setValueObfuscator(argument.getValueObfuscator()).buildTemporary();
    }

    private String getCommandStepName(CommandStep commandStep) {
        StringBuilder commandStepName = new StringBuilder();
        String[][] commandDefinition = commandStep.defineCommandNames();
        if (commandDefinition != null) {
            for (CharSequence[] charSequenceArray : commandDefinition) {
                if (commandStepName.length() != 0) {
                    commandStepName.append(" ");
                }
                commandStepName.append(String.join((CharSequence)" ", charSequenceArray));
            }
        }
        return commandStepName.toString();
    }

    public Date getOperationStartTime() {
        return this.operationStartTime;
    }

    private class CommandScopeValueProvider
    extends AbstractMapConfigurationValueProvider {
        private CommandScopeValueProvider() {
        }

        @Override
        public int getPrecedence() {
            return -1;
        }

        @Override
        protected Map<?, ?> getMap() {
            return CommandScope.this.argumentValues;
        }

        @Override
        protected String getSourceDescription() {
            return "Command argument";
        }

        @Override
        protected boolean keyMatches(String wantedKey, String storedKey) {
            if (wantedKey.contains(".")) {
                return super.keyMatches(NO_PREFIX_PATTERN.matcher(wantedKey).replaceFirst(""), storedKey);
            }
            return super.keyMatches(wantedKey, storedKey);
        }
    }

    private static class UnclosableOutputStream
    extends FilterOutputStream {
        public UnclosableOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.out.write(b, off, len);
        }

        @Override
        public void close() throws IOException {
            this.out.flush();
        }
    }
}

