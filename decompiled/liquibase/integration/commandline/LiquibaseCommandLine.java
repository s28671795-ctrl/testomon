/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  picocli.CommandLine
 *  picocli.CommandLine$IFactory
 *  picocli.CommandLine$ITypeConverter
 *  picocli.CommandLine$IVersionProvider
 *  picocli.CommandLine$Model$ArgSpec
 *  picocli.CommandLine$Model$CommandSpec
 *  picocli.CommandLine$Model$OptionSpec
 *  picocli.CommandLine$Model$OptionSpec$Builder
 *  picocli.CommandLine$ParameterException
 *  picocli.CommandLine$ParseResult
 *  picocli.CommandLine$UnmatchedArgumentException
 */
package liquibase.integration.commandline;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandFactory;
import liquibase.command.CommandFailedException;
import liquibase.command.core.CalculateChecksumCommandStep;
import liquibase.command.core.ChangelogSyncToTagCommandStep;
import liquibase.command.core.ChangelogSyncToTagSqlCommandStep;
import liquibase.command.core.DbDocCommandStep;
import liquibase.command.core.FutureRollbackCountSqlCommandStep;
import liquibase.command.core.FutureRollbackFromTagSqlCommandStep;
import liquibase.command.core.RollbackCommandStep;
import liquibase.command.core.RollbackCountCommandStep;
import liquibase.command.core.RollbackCountSqlCommandStep;
import liquibase.command.core.RollbackSqlCommandStep;
import liquibase.command.core.RollbackToDateCommandStep;
import liquibase.command.core.RollbackToDateSqlCommandStep;
import liquibase.command.core.TagCommandStep;
import liquibase.command.core.TagExistsCommandStep;
import liquibase.command.core.UpdateCountCommandStep;
import liquibase.command.core.UpdateCountSqlCommandStep;
import liquibase.command.core.UpdateToTagCommandStep;
import liquibase.command.core.UpdateToTagSqlCommandStep;
import liquibase.configuration.ConfigurationDefinition;
import liquibase.configuration.ConfigurationValueProvider;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.configuration.core.DefaultsFileValueProvider;
import liquibase.exception.CommandLineParsingException;
import liquibase.exception.CommandValidationException;
import liquibase.exception.ExitCodeException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.integration.IntegrationDetails;
import liquibase.integration.commandline.Banner;
import liquibase.integration.commandline.CommandLineArgumentValueProvider;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.integration.commandline.CommandRunner;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.integration.commandline.Main;
import liquibase.integration.commandline.VersionUtils;
import liquibase.license.LicenseInfo;
import liquibase.license.LicenseService;
import liquibase.license.LicenseServiceFactory;
import liquibase.logging.LogService;
import liquibase.logging.Logger;
import liquibase.logging.core.JavaLogService;
import liquibase.logging.core.LogServiceFactory;
import liquibase.logging.mdc.MdcManager;
import liquibase.logging.mdc.MdcObject;
import liquibase.logging.mdc.customobjects.ChangesetsUpdated;
import liquibase.logging.mdc.customobjects.Version;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.OpenOptions;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.SearchPathResourceAccessor;
import liquibase.ui.CompositeUIService;
import liquibase.ui.ConsoleUIService;
import liquibase.ui.LoggerUIService;
import liquibase.ui.UIService;
import liquibase.util.BooleanUtil;
import liquibase.util.ExceptionUtil;
import liquibase.util.ISODateFormat;
import liquibase.util.LiquibaseUtil;
import liquibase.util.LogUtil;
import liquibase.util.NetUtil;
import liquibase.util.StringUtil;
import liquibase.util.SystemUtil;
import picocli.CommandLine;

public class LiquibaseCommandLine {
    public static final String COMMAND_ARGUMENTS = "commandArguments";
    private final Map<String, String> legacyPositionalArguments;
    private final Set<String> legacyNoLongerGlobalArguments;
    private final Set<String> legacyNoLongerCommandArguments;
    private Level configuredLogLevel;
    private final CommandLine commandLine;
    private Handler fileHandler;
    private final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    private CommandLine.IFactory defaultFactory = new CommandLine.IFactory(){

        public <K> K create(Class<K> cls) throws Exception {
            return cls.newInstance();
        }
    };

    public static void main(String[] args2) {
        System.setProperty("org.fusesource.jansi.Ansi.disable", "true");
        LiquibaseCommandLine cli = new LiquibaseCommandLine();
        int returnCode = cli.execute(args2);
        System.exit(returnCode);
    }

    private void cleanup() {
        if (this.fileHandler != null) {
            this.fileHandler.flush();
        }
    }

    public LiquibaseCommandLine() {
        this.legacyPositionalArguments = new HashMap<String, String>();
        this.legacyPositionalArguments.put("calculatechecksum", CalculateChecksumCommandStep.CHANGESET_IDENTIFIER_ARG.getName());
        this.legacyPositionalArguments.put("changelogsynctotag", ChangelogSyncToTagCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("changelogsynctotagsql", ChangelogSyncToTagSqlCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("dbdoc", DbDocCommandStep.OUTPUT_DIRECTORY_ARG.getName());
        this.legacyPositionalArguments.put("futurerollbackcountsql", FutureRollbackCountSqlCommandStep.COUNT_ARG.getName());
        this.legacyPositionalArguments.put("futurerollbackfromtagsql", FutureRollbackFromTagSqlCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("tag", TagCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("tagexists", TagExistsCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("rollback", RollbackCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("rollbacksql", RollbackSqlCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("rollbacktodate", RollbackToDateCommandStep.DATE_ARG.getName());
        this.legacyPositionalArguments.put("rollbacktodatesql", RollbackToDateSqlCommandStep.DATE_ARG.getName());
        this.legacyPositionalArguments.put("rollbackcount", RollbackCountCommandStep.COUNT_ARG.getName());
        this.legacyPositionalArguments.put("rollbackcountsql", RollbackCountSqlCommandStep.COUNT_ARG.getName());
        this.legacyPositionalArguments.put("updatecount", UpdateCountCommandStep.COUNT_ARG.getName());
        this.legacyPositionalArguments.put("updatecountsql", UpdateCountSqlCommandStep.COUNT_ARG.getName());
        this.legacyPositionalArguments.put("updatetotag", UpdateToTagCommandStep.TAG_ARG.getName());
        this.legacyPositionalArguments.put("updatetotagsql", UpdateToTagSqlCommandStep.TAG_ARG.getName());
        this.legacyNoLongerGlobalArguments = Stream.of("username", "password", "url", "outputDefaultSchema", "outputDefaultCatalog", "changelogFile", "contexts", "labels", "diffTypes", "changesetAuthor", "changesetContext", "dataOutputDirectory", "referenceDriver", "referenceUrl", "referenceUsername", "referencePassword", "referenceDefaultCatalogName", "referenceDefaultSchemaName", "excludeObjects", "includeCatalog", "includeObjects", "includeSchema", "includeTablespace", "outputSchemasAs", "referenceSchemas", "schemas", "snapshotFormat", "sqlFile", "delimiter", "rollbackScript", "overwriteOutputFile", "changeExecListenerClass", "changeExecListenerPropertiesFile", "defaultSchemaName", "defaultCatalogName").collect(Collectors.toSet());
        this.legacyNoLongerCommandArguments = Stream.of("databaseClass", "liquibaseCatalogName", "liquibaseSchemaName", "databaseChangeLogTableName", "databaseChangeLogLockTableName", "classpath", "propertyProviderClass", "promptForNonLocalDatabase", "includeSystemClasspath", "defaultsFile", "currentDateTimeFunction", "logLevel", "logFile", "outputFile", "liquibaseProLicenseKey", "outputFileEncoding", "outputLineSeparator").collect(Collectors.toSet());
        this.commandLine = this.buildPicoCommandLine();
    }

    private CommandLine buildPicoCommandLine() {
        CommandLine.Model.CommandSpec rootCommandSpec = CommandLine.Model.CommandSpec.wrapWithoutInspection(null, (CommandLine.IFactory)this.defaultFactory);
        rootCommandSpec.name("liquibase");
        this.configureHelp(rootCommandSpec, true);
        rootCommandSpec.subcommandsCaseInsensitive(true);
        rootCommandSpec.usageMessage().customSynopsis(new String[]{"liquibase [GLOBAL OPTIONS] [COMMAND] [COMMAND OPTIONS]\nCommand-specific help: \"liquibase <command-name> --help\""}).optionListHeading("\nGlobal Options\n").commandListHeading("\nCommands\n");
        CommandLine commandLine = new CommandLine((Object)rootCommandSpec, this.defaultFactory).setCaseInsensitiveEnumValuesAllowed(true).setOptionsCaseInsensitive(true).setUsageHelpAutoWidth(true);
        this.addGlobalArguments(commandLine);
        for (CommandDefinition commandDefinition : this.getCommands()) {
            this.addSubcommand(commandDefinition, commandLine);
        }
        commandLine.setExecutionExceptionHandler((ex, commandLine1, parseResult) -> this.handleException(ex));
        commandLine.setUsageHelpAutoWidth(true);
        return commandLine;
    }

    protected int handleException(Throwable exception) {
        Integer exitCode;
        String uiMessage = "";
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            String newMessage = StringUtil.trimToNull(this.cleanExceptionMessage(cause.getMessage()));
            if (newMessage == null || uiMessage.contains(newMessage)) continue;
            if (!uiMessage.equals("")) {
                uiMessage = uiMessage + System.lineSeparator() + "  - Caused by: ";
            }
            uiMessage = uiMessage + newMessage;
        }
        if (StringUtil.isEmpty(uiMessage)) {
            uiMessage = exception.getClass().getName();
        }
        Level level = this.determineLogLevel(exception);
        Scope.getCurrentScope().getLog(this.getClass()).log(level, uiMessage, exception);
        boolean printUsage = false;
        try (StringWriter suggestionWriter = new StringWriter();
             PrintWriter suggestionsPrintWriter = new PrintWriter(suggestionWriter);){
            if (exception instanceof CommandLine.ParameterException) {
                if (exception instanceof CommandLine.UnmatchedArgumentException) {
                    System.err.println("Unexpected argument(s): " + StringUtil.join(((CommandLine.UnmatchedArgumentException)exception).getUnmatched(), ", "));
                } else {
                    System.err.println("Error parsing command line: " + uiMessage);
                }
                CommandLine.UnmatchedArgumentException.printSuggestions((CommandLine.ParameterException)((CommandLine.ParameterException)exception), (PrintWriter)suggestionsPrintWriter);
                printUsage = true;
            } else if (exception instanceof IllegalArgumentException || exception instanceof CommandValidationException || exception instanceof CommandLineParsingException) {
                System.err.println("Error parsing command line: " + uiMessage);
                printUsage = true;
            } else if (exception.getCause() != null && exception.getCause() instanceof CommandFailedException) {
                System.err.println(uiMessage);
            } else {
                System.err.println("\nUnexpected error running Liquibase: " + uiMessage);
                System.err.println();
                if (Level.OFF.equals(this.configuredLogLevel)) {
                    System.err.println("For more information, please use the --log-level flag");
                } else if (LiquibaseCommandLineConfiguration.LOG_FILE.getCurrentValue() == null) {
                    exception.printStackTrace(System.err);
                }
            }
            if (printUsage) {
                System.err.println();
                System.err.println("For detailed help, try 'liquibase --help' or 'liquibase <command-name> --help'");
            }
            suggestionsPrintWriter.flush();
            String suggestions = suggestionWriter.toString();
            if (suggestions.length() > 0) {
                System.err.println();
                System.err.println(suggestions);
            }
        }
        catch (IOException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Error closing stream: " + e.getMessage(), e);
        }
        Throwable exitCodeException = ExceptionUtil.findExceptionInCauseChain(exception, ExitCodeException.class);
        if (exitCodeException != null && (exitCode = ((ExitCodeException)((Object)exitCodeException.getCause())).getExitCode()) != null) {
            return exitCode;
        }
        return 1;
    }

    private Level determineLogLevel(Throwable throwable) {
        if (throwable == null) {
            return Level.SEVERE;
        }
        Level returnLevel = Level.SEVERE;
        for (Throwable t2 = throwable; t2 != null; t2 = t2.getCause()) {
            if (!(t2 instanceof LiquibaseException) || ((LiquibaseException)t2).getLogLevel() == null) continue;
            returnLevel = ((LiquibaseException)t2).getLogLevel();
        }
        return returnLevel;
    }

    protected String cleanExceptionMessage(String message) {
        String originalMessage;
        if (message == null) {
            return null;
        }
        do {
            originalMessage = message;
            message = message.replaceFirst("^[\\w.]*Exception: ", "");
        } while (!originalMessage.equals(message = message.replaceFirst("^[\\w.]*Error: ", "")));
        message = message.replace("Unexpected error running Liquibase: ", "");
        return message;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int execute(String[] args2) {
        try {
            String[] finalArgs = this.adjustLegacyArgs(args2);
            this.configureLogging(Level.OFF, null);
            Main.runningFromNewCli = true;
            List<ConfigurationValueProvider> valueProviders = this.registerValueProviders(finalArgs);
            LogService newLogService = Scope.child(Collections.singletonMap("REGISTERED_VALUE_PROVIDERS", true), () -> Scope.getCurrentScope().getSingleton(LogServiceFactory.class).getDefaultLogService());
            int n = Scope.child(Collections.singletonMap(Scope.Attr.logService.name(), newLogService), () -> {
                this.addEmptyMdcValues();
                try {
                    Integer n = Scope.child(this.configureScope(args2), () -> {
                        if (!LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentValue().booleanValue()) {
                            Scope.getCurrentScope().getUI().sendErrorMessage(String.format(this.coreBundle.getString("did.not.run.because.param.was.set.to.false"), LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentConfiguredValue().getProvidedValue().getActualKey()));
                            return 0;
                        }
                        this.configureVersionInfo();
                        if (!this.wasHelpOrVersionRequested()) {
                            Scope.getCurrentScope().getUI().sendMessage(CommandLineUtils.getBanner());
                            Scope.getCurrentScope().getUI().sendMessage(String.format(this.coreBundle.getString("version.number"), LiquibaseUtil.getBuildVersionInfo()));
                            LicenseService licenseService = Scope.getCurrentScope().getSingleton(LicenseServiceFactory.class).getLicenseService();
                            if (licenseService == null) {
                                Scope.getCurrentScope().getUI().sendMessage("WARNING: License service not loaded, cannot determine Liquibase Pro license status. Please consider re-installing Liquibase to include all dependencies. Continuing operation without Pro license.");
                            } else {
                                Scope.getCurrentScope().getUI().sendMessage(licenseService.getLicenseInfo());
                            }
                        }
                        this.enableMonitoring();
                        this.logMdcData();
                        int response = this.commandLine.execute(finalArgs);
                        if (!this.wasHelpOrVersionRequested()) {
                            ConfiguredValue<String> outputFile;
                            ConfiguredValue<String> logFile = LiquibaseCommandLineConfiguration.LOG_FILE.getCurrentConfiguredValue();
                            if (logFile.found()) {
                                Scope.getCurrentScope().getUI().sendMessage("Logs saved to " + logFile.getValue());
                            }
                            if ((outputFile = LiquibaseCommandLineConfiguration.OUTPUT_FILE.getCurrentConfiguredValue()).found()) {
                                Scope.getCurrentScope().getUI().sendMessage("Output saved to " + outputFile.getValue());
                            }
                            if (response == 0) {
                                List commandList = this.commandLine.getParseResult().asCommandLineList();
                                String commandName = StringUtil.join(LiquibaseCommandLine.getCommandNames((CommandLine)commandList.get(commandList.size() - 1)), " ");
                                Scope.getCurrentScope().getUI().sendMessage("Liquibase command '" + commandName + "' was executed successfully.");
                            }
                        }
                        return response;
                    });
                    return n;
                }
                finally {
                    LiquibaseConfiguration liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
                    for (ConfigurationValueProvider provider : valueProviders) {
                        liquibaseConfiguration.unregisterProvider(provider);
                    }
                    LogUtil.setPersistedMdcKeysToEmptyString();
                }
            });
            return n;
        }
        catch (Throwable e) {
            this.handleException(e);
            int n = 1;
            return n;
        }
        finally {
            this.cleanup();
        }
    }

    private void addEmptyMdcValues() {
        if (LiquibaseCommandLineConfiguration.ADD_EMPTY_MDC_VALUES.getCurrentValue().booleanValue()) {
            Scope.getCurrentScope().addMdcValue("deploymentId", "");
            Scope.getCurrentScope().addMdcValue("deploymentOutcome", "NOOP");
            Scope.getCurrentScope().addMdcValue("deploymentOutcomeCount", "0");
            Scope.getCurrentScope().addMdcValue("rowsAffected", "0");
            Scope.getCurrentScope().addMdcValue("changelogFile", "");
            LogUtil.setPersistedMdcKeysToEmptyString();
            Scope.getCurrentScope().addMdcValue("changesetOutcome", "NOOP");
            Scope.getCurrentScope().addMdcValue("changesetsUpdated", new ChangesetsUpdated());
            Scope.getCurrentScope().addMdcValue("operationStart", "");
            Scope.getCurrentScope().addMdcValue("operationStop", "");
            Scope.getCurrentScope().addMdcValue("liquibaseSystemName", "");
            Scope.getCurrentScope().addMdcValue("liquibaseSystemUser", "", false);
            Scope.getCurrentScope().addMdcValue("liquibaseTargetUrl", "");
            Scope.getCurrentScope().addMdcValue("liquibaseVersion", "");
            Scope.getCurrentScope().addMdcValue("liquibaseSchemaName", "");
            Scope.getCurrentScope().addMdcValue("liquibaseCatalogName", "");
        }
    }

    private void logMdcData() throws IOException {
        MdcManager mdcManager = Scope.getCurrentScope().getMdcManager();
        String localHostName = NetUtil.getLocalHostName();
        Scope.getCurrentScope().addMdcValue("liquibaseSystemUser", System.getProperty("user.name"), false);
        try (MdcObject version = mdcManager.put("liquibaseVersion", LiquibaseUtil.getBuildVersion());
             MdcObject systemName = mdcManager.put("liquibaseSystemName", localHostName);
             MdcObject hostName = mdcManager.put("liquibaseHostName", localHostName);){
            Scope.getCurrentScope().getLog(this.getClass()).info("Starting command execution.");
        }
    }

    protected void enableMonitoring() {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        try {
            String monitorPerformanceValue = LiquibaseCommandLineConfiguration.MONITOR_PERFORMANCE.getCurrentValue();
            if (monitorPerformanceValue == null || monitorPerformanceValue.equalsIgnoreCase("false")) {
                log.fine("Performance monitoring disabled");
                return;
            }
            if (!SystemUtil.isAtLeastJava11()) {
                Scope.getCurrentScope().getUI().sendMessage("Performance monitoring requires Java 11 or greater. Version " + SystemUtil.getJavaVersion() + " is not supported.");
                return;
            }
            String filename = monitorPerformanceValue;
            if (filename.equalsIgnoreCase("true")) {
                filename = "liquibase-" + new ISODateFormat().format(new Date()).replaceAll("\\W", "_") + ".jfr";
            }
            if (!filename.endsWith(".jfr")) {
                filename = filename + ".jfr";
            }
            Class<?> configurationClass = Class.forName("jdk.jfr.Configuration");
            Class<?> recordingClass = Class.forName("jdk.jfr.Recording");
            Object configuration = configurationClass.getMethod("getConfiguration", String.class).invoke(null, "profile");
            Object recording = recordingClass.getConstructor(configurationClass).newInstance(configuration);
            recordingClass.getMethod("setMaxSize", Long.TYPE).invoke(recording, 0L);
            recordingClass.getMethod("setMaxAge", Duration.class).invoke(recording, new Object[]{null});
            recordingClass.getMethod("setDumpOnExit", Boolean.TYPE).invoke(recording, true);
            recordingClass.getMethod("setToDisk", Boolean.TYPE).invoke(recording, true);
            recordingClass.getMethod("disable", String.class).invoke(recording, "jdk.InitialEnvironmentVariable");
            recordingClass.getMethod("disable", String.class).invoke(recording, "jdk.InitialSystemProperty");
            recordingClass.getMethod("disable", String.class).invoke(recording, "jdk.SystemProcess");
            recordingClass.getMethod("disable", String.class).invoke(recording, "jdk.JVMInformation");
            File filePath = new File(filename).getAbsoluteFile();
            filePath.getParentFile().mkdirs();
            recordingClass.getMethod("setDestination", Path.class).invoke(recording, filePath.toPath());
            recordingClass.getMethod("start", new Class[0]).invoke(recording, new Object[0]);
            Scope.getCurrentScope().getUI().sendMessage("Saving performance data to " + filePath.getAbsolutePath());
        }
        catch (Throwable e) {
            String message = "Error enabling performance monitoring: " + e.getMessage();
            Scope.getCurrentScope().getUI().sendMessage(message);
            log.warning(message, e);
        }
    }

    private boolean wasHelpOrVersionRequested() {
        for (CommandLine.ParseResult parseResult = this.commandLine.getParseResult(); parseResult != null; parseResult = parseResult.subcommand()) {
            if (!parseResult.isUsageHelpRequested() && !parseResult.isVersionHelpRequested()) continue;
            return true;
        }
        return false;
    }

    private Map<String, String> addJavaPropertiesToChangelogParameters() {
        CommandLine.ParseResult subcommandParseResult = this.commandLine.getParseResult();
        while (subcommandParseResult.hasSubcommand()) {
            subcommandParseResult = subcommandParseResult.subcommand();
        }
        Map changelogParameters = (Map)subcommandParseResult.matchedOptionValue("-D", new HashMap());
        if (changelogParameters.size() != 0) {
            Main.newCliChangelogParameters = changelogParameters;
        }
        return changelogParameters;
    }

    protected String[] adjustLegacyArgs(String[] args2) {
        ArrayList<String> returnArgs = new ArrayList<String>();
        ListIterator<String> iterator2 = Arrays.asList(args2).listIterator();
        while (iterator2.hasNext()) {
            String arg = iterator2.next();
            String argAsKey = arg.replace("-", "").toLowerCase();
            if (arg.startsWith("-")) {
                returnArgs.add(arg);
                continue;
            }
            String legacyTag = this.legacyPositionalArguments.get(argAsKey);
            if (legacyTag == null) {
                returnArgs.add(arg);
                continue;
            }
            returnArgs.add(arg);
            String value = " ";
            while (iterator2.hasNext()) {
                arg = iterator2.next();
                if (arg.startsWith("-")) {
                    iterator2.previous();
                    break;
                }
                value = value + arg + " ";
            }
            if ((value = StringUtil.trimToNull(value)) == null) continue;
            returnArgs.add("--" + legacyTag);
            returnArgs.add(value);
        }
        return returnArgs.toArray(new String[0]);
    }

    static String[] getCommandNames(CommandLine parseResult) {
        ArrayList<String> returnList = new ArrayList<String>();
        while (!parseResult.getCommandName().equals("liquibase")) {
            returnList.add(0, parseResult.getCommandName());
            parseResult = parseResult.getParent();
        }
        return returnList.toArray(new String[0]);
    }

    /*
     * Unable to fully structure code
     */
    private List<ConfigurationValueProvider> registerValueProviders(String[] args) throws IOException {
        liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
        returnList = new ArrayList<ConfigurationValueProvider>();
        argumentProvider = new CommandLineArgumentValueProvider(this.commandLine.parseArgs(args));
        liquibaseConfiguration.registerProvider(argumentProvider);
        returnList.add(argumentProvider);
        defaultsFileConfig = LiquibaseCommandLineConfiguration.DEFAULTS_FILE.getCurrentConfiguredValue();
        licenseServiceFactory = Scope.getCurrentScope().getSingleton(LicenseServiceFactory.class);
        if (licenseServiceFactory != null && (licenseService = licenseServiceFactory.getLicenseService()) != null) {
            licenseService.reset();
        }
        if ((resource = (pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class)).getResource(defaultsFileConfigValue = defaultsFileConfig.getValue())).exists()) {
            defaultsStream = resource.openInputStream();
            try {
                if (defaultsStream == null) ** GOTO lbl36
                fileProvider = new DefaultsFileValueProvider(defaultsStream, "File exists at path " + defaultsFileConfigValue);
                liquibaseConfiguration.registerProvider(fileProvider);
                returnList.add(fileProvider);
            }
            finally {
                if (defaultsStream != null) {
                    defaultsStream.close();
                }
            }
        } else {
            inputStreamOnClasspath = Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultsFileConfigValue);
            if (inputStreamOnClasspath == null) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot find defaultsFile " + defaultsFileConfigValue);
                if (!defaultsFileConfig.wasDefaultValueUsed()) {
                    if (GlobalConfiguration.STRICT.getCurrentValue().booleanValue()) {
                        throw new UnexpectedLiquibaseException("ERROR: The file '" + defaultsFileConfigValue + "' was not found. The global argument 'strict' is enabled, which validates the existence of files specified in liquibase files, such as changelogs, flowfiles, checks packages files, and more. To prevent this message, check your configurations, or disable the 'strict' setting.");
                    }
                    System.err.println("Could not find defaults file " + defaultsFileConfigValue);
                }
            } else {
                fileProvider = new DefaultsFileValueProvider(inputStreamOnClasspath, "File in classpath " + defaultsFileConfigValue);
                liquibaseConfiguration.registerProvider(fileProvider);
                returnList.add(fileProvider);
            }
        }
lbl36:
        // 4 sources

        if ((localDefaultsFile = new File((defaultsFile = new File(defaultsFileConfigValue)).getAbsolutePath().replaceFirst(".properties$", ".local.properties"))).exists()) {
            fileProvider = new DefaultsFileValueProvider(localDefaultsFile){

                @Override
                public int getPrecedence() {
                    return super.getPrecedence() + 1;
                }
            };
            liquibaseConfiguration.registerProvider(fileProvider);
            returnList.add(fileProvider);
        } else {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot find local defaultsFile " + defaultsFile.getAbsolutePath());
        }
        return returnList;
    }

    private Map<String, Object> configureScope(String[] args2) throws Exception {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(COMMAND_ARGUMENTS, args2);
        IntegrationDetails integrationDetails = new IntegrationDetails();
        integrationDetails.setName("cli");
        returnMap.put("integrationDetails", integrationDetails);
        ClassLoader classLoader = this.configureClassLoader();
        returnMap.putAll(this.configureLogging());
        Map<String, String> javaProperties = this.addJavaPropertiesToChangelogParameters();
        Scope.child(new HashMap<String, Object>(javaProperties), () -> returnMap.putAll(this.configureResourceAccessor(classLoader)));
        UIService defaultUiService = LiquibaseCommandLine.getDefaultUiService();
        if (defaultUiService instanceof ConsoleUIService) {
            defaultUiService.setAllowPrompt(true);
            ((ConsoleUIService)defaultUiService).setOutputStream(System.err);
            ArrayList<UIService> outputServices = new ArrayList<UIService>();
            outputServices.add(defaultUiService);
            if (BooleanUtil.isTrue(LiquibaseCommandLineConfiguration.MIRROR_CONSOLE_MESSAGES_TO_LOG.getCurrentValue())) {
                outputServices.add(new LoggerUIService());
            }
            CompositeUIService compositeUIService = new CompositeUIService(defaultUiService, outputServices);
            returnMap.put(Scope.Attr.ui.name(), compositeUIService);
        } else {
            returnMap.put(Scope.Attr.ui.name(), defaultUiService);
        }
        returnMap.put(LiquibaseCommandLineConfiguration.ARGUMENT_CONVERTER.getKey(), argument -> "--" + StringUtil.toKabobCase(argument));
        returnMap.put("javaProperties", javaProperties);
        return returnMap;
    }

    private static UIService getDefaultUiService() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<UIService> uiServices = Scope.getCurrentScope().getServiceLocator().findInstances(UIService.class);
        Class<? extends UIService> configuredUiServiceClass = GlobalConfiguration.UI_SERVICE.getCurrentValue().getUiServiceClass();
        Optional<UIService> optionalDefaultUiService = uiServices.stream().filter(uiService -> configuredUiServiceClass.isAssignableFrom(uiService.getClass())).findFirst();
        if (optionalDefaultUiService.isPresent()) {
            return optionalDefaultUiService.get();
        }
        return GlobalConfiguration.UI_SERVICE.getCurrentValue().getUiServiceClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    }

    private void configureVersionInfo() {
        this.getRootCommand(this.commandLine).getCommandSpec().versionProvider((CommandLine.IVersionProvider)new LiquibaseVersionProvider());
    }

    protected Map<String, Object> configureLogging() throws IOException {
        HashMap<String, Object> returnMap = new HashMap<String, Object>(1);
        ConfiguredValue<Level> currentConfiguredValue = LiquibaseCommandLineConfiguration.LOG_LEVEL.getCurrentConfiguredValue();
        String logFile = LiquibaseCommandLineConfiguration.LOG_FILE.getCurrentValue();
        Level logLevel = Level.OFF;
        if (!currentConfiguredValue.wasDefaultValueUsed()) {
            logLevel = currentConfiguredValue.getValue();
        }
        this.configureLogging(logLevel, logFile, currentConfiguredValue.wasDefaultValueUsed());
        return returnMap;
    }

    private void configureLogging(Level logLevel, String logFile) throws IOException {
        this.configureLogging(logLevel, logFile, false);
    }

    private void configureLogging(Level logLevel, String logFile, boolean wasDefaultLogLevelUsed) throws IOException {
        List<String> channels;
        String configuredChannels;
        this.configuredLogLevel = logLevel;
        LogService logService = (LogService)((Object)Scope.getCurrentScope().get((Enum)Scope.Attr.logService, LogService.class));
        java.util.logging.Logger liquibaseLogger = java.util.logging.Logger.getLogger("liquibase");
        if (logService instanceof JavaLogService) {
            ((JavaLogService)logService).setParent(liquibaseLogger);
        }
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] %4$s [%2$s] %5$s%6$s%n");
        java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
        Level cliLogLevel = logLevel;
        Level fileLogLevelOverride = null;
        if (logFile != null) {
            if (this.fileHandler == null) {
                PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
                OutputStream outputStream2 = pathHandlerFactory.openResourceOutputStream(logFile, new OpenOptions().setAppend(true));
                this.fileHandler = new StreamHandler(outputStream2, new SimpleFormatter());
                JavaLogService.setFormatterOnHandler(logService, this.fileHandler);
                rootLogger.addHandler(this.fileHandler);
            }
            this.fileHandler.setLevel(logLevel);
            if (logLevel == Level.OFF && wasDefaultLogLevelUsed) {
                fileLogLevelOverride = Level.SEVERE;
                this.fileHandler.setLevel(fileLogLevelOverride);
            }
            cliLogLevel = Level.OFF;
        }
        if ((configuredChannels = LiquibaseCommandLineConfiguration.LOG_CHANNELS.getCurrentValue()).equalsIgnoreCase("all")) {
            channels = new ArrayList<String>(Arrays.asList("", "liquibase"));
        } else {
            channels = StringUtil.splitAndTrim(configuredChannels, ",");
            if (logLevel == Level.OFF) {
                channels.add("");
            }
        }
        for (String channel : channels) {
            if (channel.equalsIgnoreCase("all")) {
                channel = "";
            }
            if (fileLogLevelOverride != null) {
                java.util.logging.Logger.getLogger(channel).setLevel(fileLogLevelOverride);
                continue;
            }
            java.util.logging.Logger.getLogger(channel).setLevel(logLevel);
        }
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(cliLogLevel);
            }
            JavaLogService.setFormatterOnHandler(logService, handler);
        }
    }

    private CommandLine getRootCommand(CommandLine commandLine) {
        while (commandLine.getParent() != null) {
            commandLine = commandLine.getParent();
        }
        return commandLine;
    }

    private Map<String, Object> configureResourceAccessor(ClassLoader classLoader) throws IOException {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(Scope.Attr.resourceAccessor.name(), new SearchPathResourceAccessor(new DirectoryResourceAccessor(Paths.get(".", new String[0]).toAbsolutePath().toFile()), new ClassLoaderResourceAccessor(classLoader)));
        return returnMap;
    }

    protected ClassLoader configureClassLoader() throws IllegalArgumentException {
        String classpath = LiquibaseCommandLineConfiguration.CLASSPATH.getCurrentValue();
        ArrayList<URL> urls2 = new ArrayList<URL>();
        if (classpath != null) {
            String[] classpathSoFar = SystemUtil.isWindows() ? classpath.split(";") : classpath.split(":");
            for (String classpathEntry : classpathSoFar) {
                File classPathFile = new File(classpathEntry);
                if (!classPathFile.exists()) {
                    throw new IllegalArgumentException(classPathFile.getAbsolutePath() + " does not exist");
                }
                try {
                    URL newUrl = new File(classpathEntry).toURI().toURL();
                    Scope.getCurrentScope().getLog(this.getClass()).fine(newUrl.toExternalForm() + " added to class loader");
                    urls2.add(newUrl);
                }
                catch (MalformedURLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        ClassLoader classLoader = LiquibaseCommandLineConfiguration.INCLUDE_SYSTEM_CLASSPATH.getCurrentValue() != false ? (ClassLoader)AccessController.doPrivileged(() -> new URLClassLoader(urls2.toArray(new URL[0]), Thread.currentThread().getContextClassLoader())) : (ClassLoader)AccessController.doPrivileged(() -> new URLClassLoader(urls2.toArray(new URL[0]), null));
        Thread.currentThread().setContextClassLoader(classLoader);
        return classLoader;
    }

    private void addSubcommand(CommandDefinition commandDefinition, CommandLine rootCommand) {
        List<String[]> commandNames = this.expandCommandNames(commandDefinition);
        boolean showCommand = true;
        for (String[] commandName : commandNames) {
            CommandRunner commandRunner = new CommandRunner();
            CommandLine.Model.CommandSpec subCommandSpec = CommandLine.Model.CommandSpec.wrapWithoutInspection((Object)commandRunner, (CommandLine.IFactory)this.defaultFactory);
            commandRunner.setSpec(subCommandSpec);
            this.configureHelp(subCommandSpec, false);
            if (commandDefinition.getHelpFooter() != null) {
                String[] usageMessageFooter = subCommandSpec.usageMessage().footer();
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(usageMessageFooter));
                list.add(commandDefinition.getHelpFooter());
                subCommandSpec.usageMessage().footer(list.toArray(new String[0]));
            }
            String shortDescription = commandDefinition.getShortDescription();
            subCommandSpec.usageMessage().header(new String[]{StringUtil.trimToEmpty(shortDescription) + "\n"}).description(new String[]{StringUtil.trimToEmpty(commandDefinition.getLongDescription())});
            subCommandSpec.optionsCaseInsensitive(true);
            subCommandSpec.subcommandsCaseInsensitive(true);
            if (!showCommand) {
                subCommandSpec.usageMessage().hidden(true);
            } else {
                subCommandSpec.usageMessage().hidden(commandDefinition.getHidden());
            }
            showCommand = false;
            for (CommandArgumentDefinition<?> def : commandDefinition.getArguments().values()) {
                String[] argNames = LiquibaseCommandLine.toArgNames(def);
                for (int i = 0; i < argNames.length; ++i) {
                    String description;
                    String envStringToPresent;
                    String propertyStringToPresent;
                    CommandLine.Model.OptionSpec.Builder builder = this.createArgBuilder(def, argNames[i]);
                    String argDisplaySuffix = "";
                    String argName = argNames[i];
                    if (commandDefinition.getName().length > 1) {
                        propertyStringToPresent = "\n(defaults file: 'liquibase.command." + StringUtil.join(commandDefinition.getName(), ".") + "." + def.getName() + "'";
                        envStringToPresent = this.toEnvVariable("environment variable: 'liquibase.command." + StringUtil.join(commandDefinition.getName(), ".") + "." + def.getName()) + "')" + argDisplaySuffix;
                        description = propertyStringToPresent + " " + envStringToPresent;
                    } else {
                        propertyStringToPresent = "\n(defaults file: 'liquibase.command." + def.getName() + "' OR 'liquibase.command." + StringUtil.join(commandDefinition.getName(), ".") + "." + def.getName() + "'";
                        envStringToPresent = ", environment variable: '" + this.toEnvVariable("liquibase.command." + def.getName()) + "' OR '" + this.toEnvVariable("liquibase.command." + StringUtil.join(commandDefinition.getName(), ".") + "." + def.getName()) + "')" + argDisplaySuffix;
                        description = propertyStringToPresent + " " + envStringToPresent;
                    }
                    if (def.getDefaultValue() != null) {
                        description = def.getDefaultValueDescription() == null ? "\nDEFAULT: " + def.getDefaultValue() + description : "\nDEFAULT: " + def.getDefaultValueDescription() + description;
                    }
                    if (def.getDescription() != null) {
                        description = def.getDescription() + description;
                    }
                    if (def.isRequired()) {
                        description = "[REQUIRED] " + description;
                    }
                    builder.description(new String[]{description + "\n"});
                    if (def.getDataType().equals(Boolean.class)) {
                        builder.arity("0..1");
                    }
                    if (i > 0) {
                        builder.hidden(true);
                    } else {
                        builder.hidden(def.getHidden() && LiquibaseCommandLineConfiguration.SHOW_HIDDEN_ARGS.getCurrentValue() == false);
                    }
                    subCommandSpec.addOption(builder.build());
                    if (!argName.equals("--changelog-file")) continue;
                    CommandLine.Model.OptionSpec.Builder paramBuilder = (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)"-D", (String[])new String[0]).required(false)).type(HashMap.class)).description(new String[]{"Pass a name/value pair for substitution in the changelog(s)\nPass as -D<property.name>=<property.value>\n[deprecated: set changelog properties in defaults file or environment variables]"})).mapFallbackValue("");
                    subCommandSpec.add((CommandLine.Model.ArgSpec)paramBuilder.build());
                }
            }
            for (String legacyArg : this.legacyNoLongerCommandArguments) {
                CommandLine.Model.OptionSpec.Builder builder = (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)("--" + legacyArg), (String[])new String[0]).required(false)).type(String.class)).description(new String[]{"Legacy CLI argument"})).hidden(true);
                subCommandSpec.addOption(builder.build());
                String kabobArg = StringUtil.toKabobCase(legacyArg);
                if (kabobArg.equals(legacyArg)) continue;
                CommandLine.Model.OptionSpec.Builder kabobOptionBuilder = (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)("--" + kabobArg), (String[])new String[0]).required(false)).type(String.class)).hidden(true)).description(new String[]{"Legacy CLI argument"});
                subCommandSpec.addOption(kabobOptionBuilder.build());
            }
            subCommandSpec.aliases(commandDefinition.getAliases().stream().map(cn -> cn[((String[])cn).length - 1]).collect(Collectors.toList()).toArray(new String[0]));
            this.getParentCommandSpec(commandDefinition, rootCommand).addSubcommand(commandName[commandName.length - 1], new CommandLine((Object)subCommandSpec, this.defaultFactory));
        }
    }

    private CommandLine.Model.OptionSpec.Builder createArgBuilder(CommandArgumentDefinition<?> def, String argName) {
        return (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)argName, (String[])new String[0]).required(false)).converters(new CommandLine.ITypeConverter[]{value -> {
            if (def.getDataType().equals(Boolean.class) && value.equals("")) {
                return "true";
            }
            return value;
        }})).type(String.class);
    }

    private List<String[]> expandCommandNames(CommandDefinition commandDefinition) {
        ArrayList<String[]> returnList = new ArrayList<String[]>();
        String[] standardName = (String[])commandDefinition.getName().clone();
        for (int i = 0; i < standardName.length; ++i) {
            standardName[i] = StringUtil.toKabobCase(commandDefinition.getName()[i]);
        }
        returnList.add(standardName);
        if (!StringUtil.join(standardName, " ").equals(StringUtil.join(commandDefinition.getName(), " "))) {
            returnList.add(commandDefinition.getName());
        }
        return returnList;
    }

    private CommandLine.Model.CommandSpec getParentCommandSpec(CommandDefinition commandDefinition, CommandLine rootCommand) {
        String[] commandName = commandDefinition.getName();
        CommandLine.Model.CommandSpec parent = rootCommand.getCommandSpec();
        for (int i = 0; i < commandName.length - 1; ++i) {
            CommandLine commandGroup = (CommandLine)parent.subcommands().get(commandName[i]);
            String[] groupName = Arrays.copyOfRange(commandName, 0, i + 1);
            if (commandGroup == null) {
                parent = this.addSubcommandGroup(groupName, commandDefinition, parent);
            } else {
                parent = commandGroup.getCommandSpec();
                if (commandDefinition.getGroupHelpFooter() != null) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(commandDefinition.getHelpFooter());
                    parent.usageMessage().footer(list.toArray(new String[0]));
                }
            }
            this.configureSubcommandGroup(parent, groupName, commandDefinition);
        }
        return parent;
    }

    private void configureSubcommandGroup(CommandLine.Model.CommandSpec groupSpec, String[] groupName, CommandDefinition commandDefinition) {
        String header = StringUtil.trimToEmpty(commandDefinition.getGroupShortDescription(groupName));
        String description = StringUtil.trimToEmpty(commandDefinition.getGroupLongDescription(groupName));
        if (!header.equals("")) {
            groupSpec.usageMessage().header(new String[]{"< " + header + " >\n"});
        }
        if (!description.equals("")) {
            groupSpec.usageMessage().description(new String[]{description + "\n"});
        }
    }

    private CommandLine.Model.CommandSpec addSubcommandGroup(String[] groupName, CommandDefinition commandDefinition, CommandLine.Model.CommandSpec parent) {
        CommandLine.Model.CommandSpec groupSpec = CommandLine.Model.CommandSpec.wrapWithoutInspection(null, (CommandLine.IFactory)this.defaultFactory);
        this.configureHelp(groupSpec, false);
        if (commandDefinition.getHelpFooter() != null) {
            String[] usageMessageFooter = groupSpec.usageMessage().footer();
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(usageMessageFooter));
            list.add(commandDefinition.getHelpFooter());
            groupSpec.usageMessage().footer(list.toArray(new String[0]));
        }
        groupSpec.optionsCaseInsensitive(true);
        groupSpec.subcommandsCaseInsensitive(true);
        parent.addSubcommand(groupName[groupName.length - 1], groupSpec);
        return groupSpec;
    }

    private String toEnvVariable(String property) {
        return StringUtil.toKabobCase(property).replace(".", "_").replace("-", "_").toUpperCase();
    }

    private SortedSet<CommandDefinition> getCommands() {
        CommandFactory commandFactory = Scope.getCurrentScope().getSingleton(CommandFactory.class);
        return commandFactory.getCommands(false);
    }

    private void addGlobalArguments(CommandLine commandLine) {
        CommandLine.Model.CommandSpec rootCommandSpec = commandLine.getCommandSpec();
        SortedSet<ConfigurationDefinition<?>> globalConfigurations = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getRegisteredDefinitions(false);
        for (ConfigurationDefinition configurationDefinition : globalConfigurations) {
            String[] argNames = LiquibaseCommandLine.toArgNames(configurationDefinition);
            for (int i = 0; i < argNames.length; ++i) {
                CommandLine.Model.OptionSpec.Builder optionBuilder = (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)argNames[i], (String[])new String[0]).required(false)).type(String.class);
                String description = "(defaults file: '" + configurationDefinition.getKey() + "', environment variable: '" + this.toEnvVariable(configurationDefinition.getKey()) + "')";
                if (configurationDefinition.getDefaultValue() != null) {
                    description = configurationDefinition.getDefaultValueDescription() == null ? "DEFAULT: " + configurationDefinition.getDefaultValue() + "\n" + description : "DEFAULT: " + configurationDefinition.getDefaultValueDescription() + "\n" + description;
                }
                if (configurationDefinition.getDescription() != null) {
                    description = configurationDefinition.getDescription() + "\n" + description;
                }
                optionBuilder.description(new String[]{description + "\n"});
                if (configurationDefinition.getDataType().equals(Boolean.class)) {
                    optionBuilder.arity("1");
                }
                if (i > 0 || configurationDefinition.isHidden() && !LiquibaseCommandLineConfiguration.SHOW_HIDDEN_ARGS.getCurrentValue().booleanValue()) {
                    optionBuilder.hidden(true);
                }
                CommandLine.Model.OptionSpec optionSpec = optionBuilder.build();
                rootCommandSpec.addOption(optionSpec);
            }
        }
        for (String string : this.legacyNoLongerGlobalArguments) {
            CommandLine.Model.OptionSpec.Builder optionBuilder = (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)("--" + string), (String[])new String[0]).required(false)).type(String.class)).hidden(true)).description(new String[]{"Legacy global argument"});
            rootCommandSpec.addOption(optionBuilder.build());
            String kabobArg = StringUtil.toKabobCase(string);
            if (kabobArg.equals(string)) continue;
            CommandLine.Model.OptionSpec.Builder kabobOptionBuilder = (CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)("--" + kabobArg), (String[])new String[0]).required(false)).type(String.class)).hidden(true)).description(new String[]{"Legacy global argument"});
            rootCommandSpec.addOption(kabobOptionBuilder.build());
        }
    }

    private void configureHelp(CommandLine.Model.CommandSpec commandSpec, boolean includeVersion) {
        String footer = "Each argument contains the corresponding 'configuration key' in parentheses. As an alternative to passing values on the command line, these keys can be used as a basis for configuration settings in other locations.\n\nAvailable configuration locations, in order of priority:\n- Command line arguments (argument name in --help)\n- Java system properties (configuration key listed above)\n- Environment values (env variable listed above)\n- Defaults file (configuration key OR argument name)\n\nFull documentation is available at\nhttps://docs.liquibase.com";
        commandSpec.addOption(((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)"--help", (String[])new String[]{"-h"}).description(new String[]{"Show this help message and exit\n"})).usageHelp(true).build());
        if (includeVersion) {
            commandSpec.addOption(((CommandLine.Model.OptionSpec.Builder)CommandLine.Model.OptionSpec.builder((String)"--version", (String[])new String[]{"-v"}).description(new String[]{"Print version information and exit\n"})).versionHelp(true).build());
        }
        commandSpec.usageMessage().showDefaultValues(false).sortOptions(true).abbreviateSynopsis(true).footer(new String[]{"\n" + footer});
    }

    protected static String[] toArgNames(CommandArgumentDefinition<?> def) {
        LinkedHashSet<String> returnList = new LinkedHashSet<String>();
        LinkedHashSet<String> baseNames = new LinkedHashSet<String>();
        baseNames.add(def.getName());
        baseNames.addAll(def.getAliases());
        for (String baseName : baseNames) {
            returnList.add("--" + StringUtil.toKabobCase(baseName).replace(".", "-"));
            returnList.add("--" + baseName.replace("\\.", ""));
        }
        return returnList.toArray(new String[0]);
    }

    protected static String[] toArgNames(ConfigurationDefinition<?> def) {
        ArrayList<String> keys2 = new ArrayList<String>();
        keys2.add(def.getKey());
        keys2.addAll(def.getAliasKeys());
        CaseInsensitiveList returns = new CaseInsensitiveList();
        for (String key : keys2) {
            LiquibaseCommandLine.insertWithoutDuplicates(returns, "--" + StringUtil.toKabobCase(key.replaceFirst("^liquibase.", "")).replace(".", "-"));
            LiquibaseCommandLine.insertWithoutDuplicates(returns, "--" + StringUtil.toKabobCase(key.replace(".", "-")));
            LiquibaseCommandLine.insertWithoutDuplicates(returns, "--" + key.replaceFirst("^liquibase.", "").replaceAll("\\.", ""));
            LiquibaseCommandLine.insertWithoutDuplicates(returns, "--" + key.replaceAll("\\.", ""));
        }
        return returns.toArray(new String[0]);
    }

    private static void insertWithoutDuplicates(List<String> returnList, String key) {
        if (returnList.contains(key)) {
            return;
        }
        returnList.add(key);
    }

    public static class LiquibaseVersionProvider
    implements CommandLine.IVersionProvider {
        public String[] getVersion() throws Exception {
            String liquibaseHome;
            Version mdcVersion = new Version();
            LicenseService licenseService = Scope.getCurrentScope().getSingleton(LicenseServiceFactory.class).getLicenseService();
            String licenseInfo = "";
            licenseInfo = licenseService == null ? "WARNING: License service not loaded, cannot determine Liquibase Pro license status. Please consider re-installing Liquibase to include all dependencies. Continuing operation without Pro license." : licenseService.getLicenseInfo();
            Path workingDirectory = Paths.get(".", new String[0]).toAbsolutePath();
            Path liquibaseHomePath = null;
            try {
                liquibaseHomePath = VersionUtils.getLiquibaseHomePath(workingDirectory);
                liquibaseHome = liquibaseHomePath.toString();
            }
            catch (IOException e) {
                liquibaseHome = "Cannot resolve LIQUIBASE_HOME: " + e.getMessage();
            }
            Map<String, VersionUtils.LibraryInfo> libraryInfo = VersionUtils.getLibraryInfoMap();
            StringBuilder libraryDescription = new StringBuilder("Libraries:\n");
            if (libraryInfo.size() == 0) {
                libraryDescription.append("- UNKNOWN");
            } else {
                List<String> libraries = VersionUtils.listLibraries(libraryInfo, liquibaseHomePath, workingDirectory, mdcVersion);
                for (String library : libraries) {
                    libraryDescription.append("- ").append(library).append("\n");
                }
            }
            String javaHome = System.getProperties().getProperty("java.home");
            String javaVersion = System.getProperty("java.version");
            this.addMdc(mdcVersion, licenseService, liquibaseHome, javaHome, javaVersion);
            return new String[]{CommandLineUtils.getBanner(), String.format("Liquibase Home: %s", liquibaseHome), String.format("Java Home %s (Version %s)", javaHome, javaVersion), libraryDescription.toString(), "", "Liquibase Version: " + LiquibaseUtil.getBuildVersionInfo(), licenseInfo};
        }

        private void addMdc(Version mdcVersion, LicenseService licenseService, String liquibaseHome, String javaHome, String javaVersion) {
            mdcVersion.setJavaHome(new Version.JavaHome(javaHome, javaVersion));
            Banner banner = new Banner();
            LicenseInfo licenseInfoObject = null;
            if (licenseService != null) {
                licenseInfoObject = licenseService.getLicenseInfoObject();
            }
            if (licenseInfoObject != null) {
                banner.setLicensee(licenseInfoObject.getIssuedTo());
                banner.setLicenseEndDate(licenseInfoObject.formatExpirationDate());
            }
            banner.setPath(liquibaseHome);
            mdcVersion.setLiquibaseVersion(banner);
            try (MdcObject version = Scope.getCurrentScope().addMdcValue("version", mdcVersion);){
                Scope.getCurrentScope().getLog(this.getClass()).info("Generated version info");
            }
        }
    }

    private static class CaseInsensitiveList
    extends ArrayList<String> {
        private CaseInsensitiveList() {
        }

        @Override
        public boolean contains(Object o) {
            String paramStr = (String)o;
            for (String s2 : this) {
                if (!paramStr.equalsIgnoreCase(s2)) continue;
                return true;
            }
            return false;
        }
    }
}

