/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.commandline;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.AccessController;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.GlobalConfiguration;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.command.core.AbstractRollbackCommandStep;
import liquibase.command.core.CalculateChecksumCommandStep;
import liquibase.command.core.DiffChangelogCommandStep;
import liquibase.command.core.DiffCommandStep;
import liquibase.command.core.DropAllCommandStep;
import liquibase.command.core.ExecuteSqlCommandStep;
import liquibase.command.core.GenerateChangelogCommandStep;
import liquibase.command.core.HistoryFormat;
import liquibase.command.core.InternalHistoryCommandStep;
import liquibase.command.core.InternalSnapshotCommandStep;
import liquibase.command.core.ReleaseLocksCommandStep;
import liquibase.command.core.RollbackCommandStep;
import liquibase.command.core.RollbackSqlCommandStep;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.ChangeExecListenerCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.DiffOutputControlCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.core.helpers.ReferenceDbUrlConnectionCommandStep;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.configuration.core.DeprecatedConfigurationValueProvider;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.CommandLineParsingException;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationFailedException;
import liquibase.integration.IntegrationDetails;
import liquibase.integration.commandline.ChangeExecListenerUtils;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.io.WriterOutputStream;
import liquibase.license.LicenseInstallResult;
import liquibase.license.LicenseService;
import liquibase.license.LicenseServiceFactory;
import liquibase.license.Location;
import liquibase.logging.LogService;
import liquibase.logging.Logger;
import liquibase.logging.core.JavaLogService;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.ui.CompositeUIService;
import liquibase.ui.ConsoleUIService;
import liquibase.ui.LoggerUIService;
import liquibase.ui.UIService;
import liquibase.util.ISODateFormat;
import liquibase.util.LiquibaseUtil;
import liquibase.util.StringUtil;
import liquibase.util.SystemUtil;

public class Main {
    public static boolean runningFromNewCli;
    public static Map<String, String> newCliChangelogParameters;
    private static PrintStream outputStream;
    private static final String ERRORMSG_UNEXPECTED_PARAMETERS = "unexpected.command.parameters";
    private static final Logger LOG;
    private static final ResourceBundle coreBundle;
    protected ClassLoader classLoader;
    protected String driver;
    protected String username;
    protected String password;
    protected String url;
    protected String databaseClass;
    protected String defaultSchemaName;
    protected String outputDefaultSchema;
    protected String outputDefaultCatalog;
    protected String liquibaseCatalogName;
    protected String liquibaseSchemaName;
    protected String databaseChangeLogTableName;
    protected String databaseChangeLogLockTableName;
    protected String databaseChangeLogTablespaceName;
    protected String defaultCatalogName;
    protected String changeLogFile;
    protected String overwriteOutputFile;
    protected String classpath;
    protected String contexts;
    protected String labels;
    protected String labelFilter;
    protected String driverPropertiesFile;
    protected String propertyProviderClass;
    protected String changeExecListenerClass;
    protected String changeExecListenerPropertiesFile;
    protected Boolean promptForNonLocalDatabase;
    protected Boolean includeSystemClasspath;
    protected String defaultsFile = "liquibase.properties";
    protected String diffTypes;
    protected String changeSetAuthor;
    protected String changeSetContext;
    protected String dataOutputDirectory;
    protected String referenceDriver;
    protected String referenceUrl;
    protected String referenceUsername;
    protected String referencePassword;
    protected String referenceDefaultCatalogName;
    protected String referenceDefaultSchemaName;
    protected String currentDateTimeFunction;
    protected String command;
    protected Set<String> commandParams = new LinkedHashSet<String>();
    protected String logLevel;
    protected String logFile;
    protected Map<String, Object> changeLogParameters = new HashMap<String, Object>();
    protected String outputFile;
    protected String excludeObjects;
    protected Boolean includeCatalog;
    protected String includeObjects;
    protected Boolean includeSchema;
    protected Boolean includeTablespace;
    protected Boolean deactivate;
    protected String outputSchemasAs;
    protected String referenceSchemas;
    protected String schemas;
    protected String snapshotFormat;
    protected String liquibaseProLicenseKey;
    private static final Boolean managingLogConfig;
    private static final boolean outputsLogMessages = false;
    protected String sqlFile;
    protected String delimiter;
    protected String rollbackScript;
    protected Boolean rollbackOnError = false;
    protected List<CatalogAndSchema> schemaList = new ArrayList<CatalogAndSchema>();
    protected String format;
    protected String showSummary;
    private static final int[] suspiciousCodePoints;

    public static void main(String[] args2) {
        int errorLevel = 0;
        try {
            errorLevel = Main.run(args2);
        }
        catch (Throwable e) {
            System.exit(-1);
        }
        System.exit(errorLevel);
    }

    public static int run(final String[] args2) throws Exception {
        HashMap<String, Object> scopeObjects = new HashMap<String, Object>();
        IntegrationDetails integrationDetails = new IntegrationDetails();
        integrationDetails.setName("cli");
        ListIterator<String> argIterator = Arrays.asList(args2).listIterator();
        while (argIterator.hasNext()) {
            String arg = argIterator.next();
            if (!arg.startsWith("--")) continue;
            if (arg.contains("=")) {
                String[] splitArg = arg.split("=", 2);
                String argKey = "argument__" + splitArg[0].replaceFirst("^--", "");
                if (splitArg.length == 2) {
                    integrationDetails.setParameter(argKey, splitArg[1]);
                    continue;
                }
                integrationDetails.setParameter(argKey, "true");
                continue;
            }
            String argKey = "argument__" + arg.replaceFirst("^--", "");
            if (argIterator.hasNext()) {
                String next = argIterator.next();
                if (next.startsWith("--") || Main.isCommand(next)) {
                    integrationDetails.setParameter(argKey, "true");
                    argIterator.previous();
                    continue;
                }
                integrationDetails.setParameter(argKey, next);
                continue;
            }
            integrationDetails.setParameter(argKey, "true");
        }
        scopeObjects.put("integrationDetails", integrationDetails);
        if (!runningFromNewCli) {
            ArrayList<UIService> uiOutputServices = new ArrayList<UIService>();
            ConsoleUIService console = new ConsoleUIService();
            console.setAllowPrompt(true);
            uiOutputServices.add(console);
            if (LiquibaseCommandLineConfiguration.MIRROR_CONSOLE_MESSAGES_TO_LOG.getCurrentValue().booleanValue()) {
                uiOutputServices.add(new LoggerUIService());
            }
            CompositeUIService compositeUIService = new CompositeUIService(console, uiOutputServices);
            scopeObjects.put(Scope.Attr.ui.name(), compositeUIService);
        }
        return Scope.child(scopeObjects, new Scope.ScopedRunnerWithReturn<Integer>(){

            @Override
            public Integer run() throws Exception {
                Main main = new Main();
                try {
                    List<String> setupMessages;
                    LicenseService licenseService;
                    if (args2.length == 0 || args2.length == 1 && "--help".equals(args2[0])) {
                        main.printHelp(outputStream);
                        return 0;
                    }
                    if ("--version".equals(args2[0])) {
                        main.command = "";
                        main.parseDefaultPropertyFiles();
                        Scope.getCurrentScope().getUI().sendMessage(CommandLineUtils.getBanner());
                        Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("version.number"), LiquibaseUtil.getBuildVersionInfo()));
                        LicenseService licenseService2 = Scope.getCurrentScope().getSingleton(LicenseServiceFactory.class).getLicenseService();
                        if (licenseService2 != null && main.liquibaseProLicenseKey != null) {
                            Location licenseKeyLocation = new Location("property liquibaseProLicenseKey", main.liquibaseProLicenseKey);
                            LicenseInstallResult result = licenseService2.installLicense(licenseKeyLocation);
                            if (result.code != 0) {
                                String allMessages = String.join((CharSequence)"\n", result.messages);
                                Scope.getCurrentScope().getUI().sendErrorMessage(allMessages);
                            }
                        }
                        if (licenseService2 != null) {
                            Scope.getCurrentScope().getUI().sendMessage(licenseService2.getLicenseInfo());
                        }
                        Scope.getCurrentScope().getUI().sendMessage(String.format("Running Java under %s (Version %s)", System.getProperties().getProperty("java.home"), SystemUtil.getJavaVersion()));
                        return 0;
                    }
                    for (int i = 0; i < args2.length; ++i) {
                        CodePointCheck codePointCheck = Main.checkArg(args2[i]);
                        if (codePointCheck == null) continue;
                        String message = "A non-standard character '" + codePointCheck.ch + "' was detected on the command line at position " + (codePointCheck.position + 1) + " of argument number " + (i + 1) + ".\nIf problems occur, please remove the character and try again.";
                        LOG.warning(message);
                        System.err.println(message);
                    }
                    try {
                        main.parseOptions(args2);
                        if (main.command == null) {
                            main.printHelp(outputStream);
                            return 0;
                        }
                        Scope.getCurrentScope().addMdcValue("liquibaseCommandName", main.command);
                    }
                    catch (CommandLineParsingException e) {
                        Scope.getCurrentScope().getUI().sendMessage(CommandLineUtils.getBanner());
                        Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("how.to.display.help"));
                        throw e;
                    }
                    if (!runningFromNewCli) {
                        UIService ui = Scope.getCurrentScope().getUI();
                        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] %4$s [%2$s] %5$s%6$s%n");
                        java.util.logging.Logger rootLogger = java.util.logging.Logger.getLogger("");
                        java.util.logging.Logger liquibaseLogger = java.util.logging.Logger.getLogger("liquibase");
                        liquibaseLogger.setParent(rootLogger);
                        LogService logService = (LogService)((Object)Scope.getCurrentScope().get((Enum)Scope.Attr.logService, LogService.class));
                        if (logService instanceof JavaLogService) {
                            ((JavaLogService)logService).setParent(liquibaseLogger);
                        }
                        if (main.logLevel == null) {
                            String defaultLogLevel = System.getProperty("liquibase.log.level");
                            if (defaultLogLevel == null) {
                                Main.setLogLevel(logService, rootLogger, liquibaseLogger, Level.OFF);
                            } else {
                                Main.setLogLevel(logService, rootLogger, liquibaseLogger, Main.parseLogLevel(defaultLogLevel, ui));
                            }
                        } else {
                            Main.setLogLevel(logService, rootLogger, liquibaseLogger, Main.parseLogLevel(main.logLevel, ui));
                        }
                        if (main.logFile != null) {
                            FileHandler fileHandler = new FileHandler(main.logFile, true);
                            fileHandler.setFormatter(new SimpleFormatter());
                            if (liquibaseLogger.getLevel() == Level.OFF) {
                                fileHandler.setLevel(Level.FINE);
                            }
                            rootLogger.addHandler(fileHandler);
                            for (Handler handler : rootLogger.getHandlers()) {
                                if (!(handler instanceof ConsoleHandler)) continue;
                                handler.setLevel(Level.OFF);
                            }
                        }
                        if (main.command != null && main.command.toLowerCase().endsWith("sql")) {
                            if (ui instanceof CompositeUIService) {
                                ((CompositeUIService)ui).getOutputServices().stream().filter(service2 -> service2 instanceof ConsoleUIService).forEach(console -> ((ConsoleUIService)console).setOutputStream(System.err));
                            } else if (ui instanceof ConsoleUIService) {
                                ((ConsoleUIService)ui).setOutputStream(System.err);
                            }
                        }
                    }
                    if ((licenseService = Scope.getCurrentScope().getSingleton(LicenseServiceFactory.class).getLicenseService()) != null) {
                        if (main.liquibaseProLicenseKey == null) {
                            if (!runningFromNewCli) {
                                Scope.getCurrentScope().getLog(this.getClass()).info("No Liquibase Pro license key supplied. Please set liquibaseProLicenseKey on command line or in liquibase.properties to use Liquibase Pro features.");
                            }
                        } else {
                            Location licenseKeyLocation = new Location("property liquibaseProLicenseKey", main.liquibaseProLicenseKey);
                            LicenseInstallResult result = licenseService.installLicense(licenseKeyLocation);
                            if (result.code != 0) {
                                String allMessages = String.join((CharSequence)"\n", result.messages);
                                if (!runningFromNewCli) {
                                    Scope.getCurrentScope().getUI().sendMessage(allMessages);
                                }
                            }
                        }
                        if (!runningFromNewCli) {
                            Scope.getCurrentScope().getUI().sendMessage(licenseService.getLicenseInfo());
                        }
                    }
                    if (!runningFromNewCli) {
                        Scope.getCurrentScope().getUI().sendMessage(CommandLineUtils.getBanner());
                    }
                    if (!LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentValue().booleanValue()) {
                        Scope.getCurrentScope().getUI().sendErrorMessage(String.format(coreBundle.getString("did.not.run.because.param.was.set.to.false"), LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentConfiguredValue().getProvidedValue().getActualKey()));
                        return 0;
                    }
                    if (Main.setupNeeded(main) && !(setupMessages = main.checkSetup()).isEmpty()) {
                        main.printHelp(setupMessages, Main.isStandardOutputRequired(main.command) ? System.err : outputStream);
                        return 1;
                    }
                    main.applyDefaults();
                    HashMap<String, Object> innerScopeObjects = new HashMap<String, Object>();
                    innerScopeObjects.put("defaultsFile", LiquibaseCommandLineConfiguration.DEFAULTS_FILE.getCurrentValue());
                    if (!runningFromNewCli) {
                        innerScopeObjects.put(Scope.Attr.resourceAccessor.name(), new CompositeResourceAccessor(new DirectoryResourceAccessor(Paths.get(".", new String[0]).toAbsolutePath().toFile()), new ClassLoaderResourceAccessor(main.configureClassLoader())));
                    }
                    Scope.child(innerScopeObjects, () -> {
                        main.doMigration();
                        if (!runningFromNewCli) {
                            if ("update".equals(main.command)) {
                                Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("update.successful"));
                            } else if (main.command.startsWith("rollback")) {
                                Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("rollback.successful"));
                            } else {
                                Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("command.successful"), main.command));
                            }
                        }
                    });
                    Scope.getCurrentScope().getMdcManager().clear();
                }
                catch (Throwable e) {
                    String message = e.getMessage();
                    if (e.getCause() != null) {
                        message = e.getCause().getMessage();
                    }
                    if (message == null) {
                        message = coreBundle.getString("unknown.reason");
                    }
                    try {
                        if (e.getCause() instanceof ValidationFailedException) {
                            ((ValidationFailedException)e.getCause()).printDescriptiveError(outputStream);
                        } else if (!runningFromNewCli) {
                            Scope.getCurrentScope().getUI().sendMessage(String.format(coreBundle.getString("unexpected.error"), message));
                            Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("for.more.information.use.loglevel.flag"));
                            Scope.getCurrentScope().getLog(this.getClass()).severe(String.format(coreBundle.getString("unexpected.error"), message), e);
                        }
                    }
                    catch (IllegalFormatException e1) {
                        if (runningFromNewCli) {
                            throw e1;
                        }
                        e1.printStackTrace();
                    }
                    if (runningFromNewCli) {
                        throw e;
                    }
                    throw new LiquibaseException(String.format(coreBundle.getString("unexpected.error"), message), e);
                }
                return 0;
            }
        });
    }

    private static boolean setupNeeded(Main main) throws CommandLineParsingException {
        if (!main.commandParams.contains("--help")) {
            return true;
        }
        return !main.command.toLowerCase().startsWith("rollbackOneChangeSet".toLowerCase()) && !main.command.toLowerCase().startsWith("rollbackOneUpdate".toLowerCase()) && (!main.command.toLowerCase().startsWith("diff".toLowerCase()) || !main.isFormattedDiff());
    }

    protected static void setLogLevel(LogService logService, java.util.logging.Logger rootLogger, java.util.logging.Logger liquibaseLogger, Level level) {
        if (runningFromNewCli) {
            return;
        }
        if (level.intValue() < Level.INFO.intValue()) {
            rootLogger.setLevel(Level.INFO);
        } else {
            rootLogger.setLevel(level);
        }
        liquibaseLogger.setLevel(level);
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(level);
        }
    }

    private static Level parseLogLevel(String logLevelName, UIService ui) {
        Level logLevel;
        switch (logLevelName = logLevelName.toUpperCase()) {
            case "DEBUG": {
                logLevel = Level.FINE;
                break;
            }
            case "WARN": {
                logLevel = Level.WARNING;
                break;
            }
            case "ERROR": {
                logLevel = Level.SEVERE;
                break;
            }
            default: {
                try {
                    logLevel = Level.parse(logLevelName);
                    break;
                }
                catch (IllegalArgumentException e) {
                    ui.sendErrorMessage("Unknown log level " + logLevelName);
                    logLevel = Level.OFF;
                }
            }
        }
        return logLevel;
    }

    private static String[] splitArg(String arg) throws CommandLineParsingException {
        String[] splitArg = arg.split("=", 2);
        if (splitArg.length < 2) {
            throw new CommandLineParsingException(String.format(coreBundle.getString("could.not.parse.expression"), arg));
        }
        splitArg[0] = splitArg[0].replaceFirst("--", "");
        return splitArg;
    }

    private static boolean isStandardOutputRequired(String command) {
        return "snapshot".equalsIgnoreCase(command) || "snapshotReference".equalsIgnoreCase(command) || "changelogSyncSQL".equalsIgnoreCase(command) || "changelogSyncToTagSQL".equalsIgnoreCase(command) || "markNextChangeSetRanSQL".equalsIgnoreCase(command) || "updateCountSQL".equalsIgnoreCase(command) || "updateToTagSQL".equalsIgnoreCase(command) || "updateSQL".equalsIgnoreCase(command) || "rollbackSQL".equalsIgnoreCase(command) || "rollbackToDateSQL".equalsIgnoreCase(command) || "rollbackCountSQL".equalsIgnoreCase(command) || "futureRollbackSQL".equalsIgnoreCase(command) || "futureRollbackCountSQL".equalsIgnoreCase(command) || "futureRollbackFromTagSQL".equalsIgnoreCase(command);
    }

    private static boolean isChangeLogRequired(String command) {
        return command.toLowerCase().startsWith("update") || command.toLowerCase().startsWith("rollback") && !command.equalsIgnoreCase("rollbackOneChangeSet") && !command.equalsIgnoreCase("rollbackOneUpdate") || "calculateCheckSum".equalsIgnoreCase(command) || "status".equalsIgnoreCase(command) || "validate".equalsIgnoreCase(command) || "changelogSync".equalsIgnoreCase(command) || "changelogSyncSQL".equalsIgnoreCase(command) || "changelogSyncToTag".equalsIgnoreCase(command) || "changelogSyncToTagSQL".equalsIgnoreCase(command) || "generateChangeLog".equalsIgnoreCase(command) || "unexpectedChangeSets".equalsIgnoreCase(command) || "diffChangeLog".equalsIgnoreCase(command) || "rollbackOneChangeSet".equalsIgnoreCase(command) || "rollbackOneUpdate".equalsIgnoreCase(command);
    }

    private static boolean isCommand(String arg) {
        return "migrate".equals(arg) || "migrateSQL".equalsIgnoreCase(arg) || "update".equalsIgnoreCase(arg) || "updateSQL".equalsIgnoreCase(arg) || "updateCount".equalsIgnoreCase(arg) || "updateCountSQL".equalsIgnoreCase(arg) || "updateToTag".equalsIgnoreCase(arg) || "updateToTagSQL".equalsIgnoreCase(arg) || "rollback".equalsIgnoreCase(arg) || "rollbackToDate".equalsIgnoreCase(arg) || "rollbackCount".equalsIgnoreCase(arg) || "rollbackSQL".equalsIgnoreCase(arg) || "rollbackToDateSQL".equalsIgnoreCase(arg) || "rollbackCountSQL".equalsIgnoreCase(arg) || "futureRollbackSQL".equalsIgnoreCase(arg) || "futureRollbackCountSQL".equalsIgnoreCase(arg) || "futureRollbackFromTagSQL".equalsIgnoreCase(arg) || "updateTestingRollback".equalsIgnoreCase(arg) || "tag".equalsIgnoreCase(arg) || "tagExists".equalsIgnoreCase(arg) || "listLocks".equalsIgnoreCase(arg) || "history".equalsIgnoreCase(arg) || "dropAll".equalsIgnoreCase(arg) || "releaseLocks".equalsIgnoreCase(arg) || "status".equalsIgnoreCase(arg) || "unexpectedChangeSets".equalsIgnoreCase(arg) || "validate".equalsIgnoreCase(arg) || "help".equalsIgnoreCase(arg) || "diff".equalsIgnoreCase(arg) || "diffChangeLog".equalsIgnoreCase(arg) || "generateChangeLog".equalsIgnoreCase(arg) || "snapshot".equalsIgnoreCase(arg) || "snapshotReference".equalsIgnoreCase(arg) || "executeSql".equalsIgnoreCase(arg) || "calculateCheckSum".equalsIgnoreCase(arg) || "clearCheckSums".equalsIgnoreCase(arg) || "dbDoc".equalsIgnoreCase(arg) || "changelogSync".equalsIgnoreCase(arg) || "changelogSyncSQL".equalsIgnoreCase(arg) || "changelogSyncToTag".equalsIgnoreCase(arg) || "changelogSyncToTagSQL".equalsIgnoreCase(arg) || "markNextChangeSetRan".equalsIgnoreCase(arg) || "markNextChangeSetRanSQL".equalsIgnoreCase(arg) || "rollbackOneChangeSet".equalsIgnoreCase(arg) || "rollbackOneChangeSetSQL".equalsIgnoreCase(arg) || "rollbackOneUpdate".equalsIgnoreCase(arg) || "rollbackOneUpdateSQL".equalsIgnoreCase(arg);
    }

    private static boolean isNoArgCommand(String arg) {
        return "migrate".equals(arg) || "migrateSQL".equalsIgnoreCase(arg) || "update".equalsIgnoreCase(arg) || "updateSQL".equalsIgnoreCase(arg) || "updateTestingRollback".equalsIgnoreCase(arg) || "listLocks".equalsIgnoreCase(arg) || "releaseLocks".equalsIgnoreCase(arg) || "validate".equalsIgnoreCase(arg) || "help".equalsIgnoreCase(arg) || "clearCheckSums".equalsIgnoreCase(arg) || "changelogSync".equalsIgnoreCase(arg) || "changelogSyncSQL".equalsIgnoreCase(arg);
    }

    private static void addWarFileClasspathEntries(File classPathFile, List<URL> urls2) throws IOException {
        URL jarUrl = new URL("jar:" + classPathFile.toURI().toURL() + "!/WEB-INF/classes/");
        LOG.info("adding '" + jarUrl + "' to classpath");
        urls2.add(jarUrl);
        try (JarFile warZip = new JarFile(classPathFile);){
            Enumeration<JarEntry> entries = warZip.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.getName().startsWith("WEB-INF/lib") || !entry.getName().toLowerCase().endsWith(".jar")) continue;
                File jar = Main.extract(warZip, entry);
                URL newUrl = new URL("jar:" + jar.toURI().toURL() + "!/");
                LOG.info("adding '" + newUrl + "' to classpath");
                urls2.add(newUrl);
                jar.deleteOnExit();
            }
        }
    }

    private static File extract(JarFile jar, JarEntry entry) throws IOException {
        File tempFile = File.createTempFile("liquibase.tmp", null);
        try (BufferedInputStream inStream = new BufferedInputStream(jar.getInputStream(entry));
             BufferedOutputStream outStream = new BufferedOutputStream(Files.newOutputStream(tempFile.toPath(), new OpenOption[0]));){
            int status;
            while ((status = inStream.read()) != -1) {
                outStream.write(status);
            }
        }
        return tempFile;
    }

    protected void parseDefaultPropertyFiles() throws CommandLineParsingException {
        LinkedHashSet<File> potentialPropertyFiles = new LinkedHashSet<File>();
        potentialPropertyFiles.add(new File(this.defaultsFile));
        String localDefaultsPathName = this.defaultsFile.replaceFirst("(\\.[^\\.]+)$", ".local$1");
        potentialPropertyFiles.add(new File(localDefaultsPathName));
        ConfiguredValue<String> currentConfiguredValue = LiquibaseCommandLineConfiguration.DEFAULTS_FILE.getCurrentConfiguredValue();
        if (currentConfiguredValue.found()) {
            potentialPropertyFiles.add(new File(currentConfiguredValue.getValue()));
        }
        for (File potentialPropertyFile : potentialPropertyFiles) {
            try {
                if (potentialPropertyFile.exists()) {
                    this.parseDefaultPropertyFileFromFile(potentialPropertyFile);
                    continue;
                }
                this.parseDefaultPropertyFileFromResource(potentialPropertyFile);
            }
            catch (IOException e) {
                throw new CommandLineParsingException(e);
            }
        }
    }

    private void parseDefaultPropertyFileFromResource(File potentialPropertyFile) throws IOException, CommandLineParsingException {
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(potentialPropertyFile.getPath());){
            if (resourceAsStream != null) {
                this.parsePropertiesFile(resourceAsStream);
            }
        }
    }

    private void parseDefaultPropertyFileFromFile(File potentialPropertyFile) throws IOException, CommandLineParsingException {
        try (InputStream stream = Files.newInputStream(potentialPropertyFile.toPath(), new OpenOption[0]);){
            this.parsePropertiesFile(stream);
        }
    }

    protected String[] fixupArgs(String[] args2) {
        ArrayList<String> fixedArgs = new ArrayList<String>();
        for (int i = 0; i < args2.length; ++i) {
            String arg = args2[i];
            if ((arg.startsWith("--") || arg.startsWith("-D")) && !arg.contains("=")) {
                String nextArg = null;
                if (i + 1 < args2.length) {
                    nextArg = args2[i + 1];
                }
                if (nextArg != null && !nextArg.startsWith("--") && !Main.isCommand(nextArg)) {
                    arg = arg + "=" + nextArg;
                    ++i;
                }
            }
            arg = arg.replace("\\,", ",");
            fixedArgs.add(arg);
        }
        return fixedArgs.toArray(new String[0]);
    }

    protected List<String> checkSetup() {
        ArrayList<String> messages = new ArrayList<String>();
        if (this.command == null) {
            messages.add(coreBundle.getString("command.not.passed"));
        } else if (!Main.isCommand(this.command)) {
            messages.add(String.format(coreBundle.getString("command.unknown"), this.command));
        } else {
            if (StringUtil.trimToNull(this.url) == null && StringUtil.trimToNull(this.referenceUrl) == null) {
                messages.add(String.format(coreBundle.getString("option.required"), "--url"));
            }
            if (Main.isChangeLogRequired(this.command) && StringUtil.trimToNull(this.changeLogFile) == null) {
                messages.add(String.format(coreBundle.getString("option.required"), "--changeLogFile"));
            }
            if (Main.isNoArgCommand(this.command) && !this.commandParams.isEmpty()) {
                messages.add(coreBundle.getString(ERRORMSG_UNEXPECTED_PARAMETERS) + this.commandParams);
            } else {
                this.validateCommandParameters(messages);
            }
        }
        return messages;
    }

    private void checkForUnexpectedCommandParameter(List<String> messages) {
        block17: {
            block19: {
                block18: {
                    block16: {
                        if (!"updateCount".equalsIgnoreCase(this.command) && !"updateCountSQL".equalsIgnoreCase(this.command) && !"updateToTag".equalsIgnoreCase(this.command) && !"updateToTagSQL".equalsIgnoreCase(this.command) && !"calculateCheckSum".equalsIgnoreCase(this.command) && !"dbDoc".equalsIgnoreCase(this.command) && !"tag".equalsIgnoreCase(this.command) && !"tagExists".equalsIgnoreCase(this.command) && !"changelogSyncToTag".equalsIgnoreCase(this.command) && !"changelogSyncToTagSQL".equalsIgnoreCase(this.command)) break block16;
                        if (this.commandParams.isEmpty() || !this.commandParams.iterator().next().startsWith("-")) break block17;
                        messages.add(coreBundle.getString(ERRORMSG_UNEXPECTED_PARAMETERS) + this.commandParams);
                        break block17;
                    }
                    if (!"status".equalsIgnoreCase(this.command) && !"unexpectedChangeSets".equalsIgnoreCase(this.command)) break block18;
                    if (this.commandParams.isEmpty() || this.commandParams.iterator().next().equalsIgnoreCase("--verbose")) break block17;
                    messages.add(coreBundle.getString(ERRORMSG_UNEXPECTED_PARAMETERS) + this.commandParams);
                    break block17;
                }
                if (!"diff".equalsIgnoreCase(this.command) && !"diffChangeLog".equalsIgnoreCase(this.command)) break block19;
                if (this.commandParams.isEmpty()) break block17;
                for (String cmdParm : this.commandParams) {
                    String caseInsensitiveCommandParam = cmdParm.toLowerCase();
                    if (!(caseInsensitiveCommandParam.startsWith("--" + "referenceUsername".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "referencePassword".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "referenceDriver".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "referenceDefaultCatalogName".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "referenceDefaultSchemaName".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "includeSchema".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "includeCatalog".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "includeTablespace".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "schemas".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "outputSchemasAs".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "referenceSchemas".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "referenceUrl".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "excludeObjects".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "includeObjects".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "diffTypes".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "format".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "help".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "snapshotFormat".toLowerCase()))) {
                        messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                    }
                    if (!"diffChangeLog".equalsIgnoreCase(this.command) || !cmdParm.toLowerCase().startsWith("--" + "format".toLowerCase())) continue;
                    messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                }
                break block17;
            }
            if (("snapshot".equalsIgnoreCase(this.command) || "generateChangeLog".equalsIgnoreCase(this.command)) && !this.commandParams.isEmpty()) {
                for (String cmdParm : this.commandParams) {
                    String caseInsensitiveCommandParam = cmdParm.toLowerCase();
                    if (caseInsensitiveCommandParam.startsWith("--" + "includeSchema".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "includeCatalog".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "includeTablespace".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "schemas".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "snapshotFormat".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "dataOutputDirectory".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "outputSchemasAs".toLowerCase())) continue;
                    messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                }
            } else if ("rollbackOneChangeSet".equalsIgnoreCase(this.command)) {
                for (String cmdParm : this.commandParams) {
                    String caseInsensitiveCommandParam = cmdParm.toLowerCase();
                    if (caseInsensitiveCommandParam.startsWith("--" + "changeSetId".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "help".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "force".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "changeSetPath".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "changeSetAuthor".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "rollbackScript".toLowerCase())) continue;
                    messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                }
            } else if ("rollbackOneChangeSetSQL".equalsIgnoreCase(this.command)) {
                for (String cmdParm : this.commandParams) {
                    String caseInsensitiveCommandParam = cmdParm.toLowerCase();
                    if (caseInsensitiveCommandParam.startsWith("--" + "changeSetId".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "help".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "force".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "changeSetPath".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "changeSetAuthor".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "rollbackScript".toLowerCase())) continue;
                    messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                }
            } else if ("rollbackOneUpdate".equalsIgnoreCase(this.command)) {
                for (String cmdParm : this.commandParams) {
                    String caseInsensitiveCommandParam = cmdParm.toLowerCase();
                    if (caseInsensitiveCommandParam.startsWith("--" + "deploymentId".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "help".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "force".toLowerCase())) continue;
                    messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                }
            } else if ("rollbackOneUpdateSQL".equalsIgnoreCase(this.command)) {
                for (String cmdParm : this.commandParams) {
                    String caseInsensitiveCommandParam = cmdParm.toLowerCase();
                    if (caseInsensitiveCommandParam.startsWith("--" + "deploymentId".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "help".toLowerCase()) || caseInsensitiveCommandParam.startsWith("--" + "force".toLowerCase())) continue;
                    messages.add(String.format(coreBundle.getString("unexpected.command.parameter"), cmdParm));
                }
            }
        }
    }

    private void validateCommandParameters(List<String> messages) {
        this.checkForUnexpectedCommandParameter(messages);
        this.checkForMissingCommandParameters(messages);
        this.checkForMalformedCommandParameters(messages);
    }

    private void checkForMissingCommandParameters(List<String> messages) {
        if ((this.commandParams.isEmpty() || this.commandParams.iterator().next().startsWith("-")) && "calculateCheckSum".equalsIgnoreCase(this.command)) {
            messages.add(coreBundle.getString("changeset.identifier.missing"));
        }
    }

    private void checkForMalformedCommandParameters(List<String> messages) {
        if (this.commandParams.isEmpty()) {
            return;
        }
        if ("diffChangeLog".equalsIgnoreCase(this.command) && this.diffTypes != null && this.diffTypes.toLowerCase().contains("data")) {
            messages.add(String.format(coreBundle.getString("including.data.diffchangelog.has.no.effect"), "diffTypes", "generateChangeLog"));
        }
    }

    protected void parsePropertiesFile(InputStream propertiesInputStream) throws IOException, CommandLineParsingException {
        IntegrationDetails integrationDetails = (IntegrationDetails)((Object)Scope.getCurrentScope().get("integrationDetails", IntegrationDetails.class));
        Properties props = new Properties();
        props.load(propertiesInputStream);
        if (runningFromNewCli) {
            this.parsePropertiesFileForNewCli(props);
            return;
        }
        boolean strict = GlobalConfiguration.STRICT.getCurrentValue();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String entryValue = null;
            if (entry.getValue() != null) {
                entryValue = String.valueOf(entry.getValue());
            }
            if (integrationDetails != null) {
                integrationDetails.setParameter("defaultsFile__" + entry.getKey(), entryValue);
            }
            try {
                if ("promptOnNonLocalDatabase".equals(entry.getKey())) continue;
                if (((String)entry.getKey()).startsWith("parameter.")) {
                    this.changeLogParameters.put(((String)entry.getKey()).replaceFirst("^parameter.", ""), entry.getValue());
                    continue;
                }
                if (((String)entry.getKey()).contains(".")) {
                    if (Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getRegisteredDefinition((String)entry.getKey()) == null) {
                        if (strict) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("parameter.unknown"), entry.getKey()));
                        }
                        Scope.getCurrentScope().getLog(this.getClass()).warning(String.format(coreBundle.getString("parameter.ignored"), entry.getKey()));
                    }
                    if (System.getProperty((String)entry.getKey()) != null) continue;
                    DeprecatedConfigurationValueProvider.setData((String)entry.getKey(), entry.getValue());
                    continue;
                }
                Field field2 = this.getDeclaredField((String)entry.getKey());
                Object currentValue = field2.get(this);
                if (currentValue != null) continue;
                String value = entry.getValue().toString().trim();
                if (field2.getType().equals(Boolean.class)) {
                    field2.set(this, Boolean.valueOf(value));
                    continue;
                }
                field2.set(this, value);
            }
            catch (NoSuchFieldException ignored) {
                if (strict) {
                    throw new CommandLineParsingException(String.format(coreBundle.getString("parameter.unknown"), entry.getKey()));
                }
                Scope.getCurrentScope().getLog(this.getClass()).warning(String.format(coreBundle.getString("parameter.ignored"), entry.getKey()));
            }
            catch (IllegalAccessException e) {
                throw new UnexpectedLiquibaseException(String.format(coreBundle.getString("parameter.unknown"), entry.getKey()));
            }
        }
    }

    private void parsePropertiesFileForNewCli(Properties props) {
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            if (!((String)entry.getKey()).startsWith("parameter.")) continue;
            this.changeLogParameters.put(((String)entry.getKey()).replaceFirst("^parameter.", ""), entry.getValue());
        }
    }

    protected void printHelp(List<String> errorMessages, PrintStream stream) {
        stream.println(coreBundle.getString("errors"));
        for (String message : errorMessages) {
            stream.println("  " + message);
        }
        stream.println();
    }

    protected void printHelp(PrintStream stream) {
        this.logLevel = Level.WARNING.toString();
        String helpText = "Help not available when running liquibase.integration.commandline.Main directly. Use liquibase.integration.commandline.LiquibaseCommandLine";
        stream.println(helpText);
    }

    protected static CodePointCheck checkArg(String arg) {
        char[] chars = arg.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char ch = chars[i];
            for (int suspiciousCodePoint : suspiciousCodePoints) {
                if (suspiciousCodePoint != ch) continue;
                CodePointCheck codePointCheck = new CodePointCheck();
                codePointCheck.position = i;
                codePointCheck.ch = ch;
                return codePointCheck;
            }
        }
        return null;
    }

    protected void parseOptions(String[] paramArgs) throws CommandLineParsingException {
        Class clazz;
        String[] args2 = this.fixupArgs(paramArgs);
        boolean seenCommand = false;
        for (String arg : args2) {
            if (Main.isCommand(arg)) {
                this.command = arg;
                if (this.command.equalsIgnoreCase("migrate")) {
                    this.command = "update";
                } else if (this.command.equalsIgnoreCase("migrateSQL")) {
                    this.command = "updateSQL";
                }
                seenCommand = true;
                continue;
            }
            if (seenCommand) {
                if (arg.startsWith("-D")) {
                    String[] splitArg = Main.splitArg(arg);
                    String attributeName = splitArg[0].replaceFirst("^-D", "");
                    String value = splitArg[1];
                    this.changeLogParameters.put(attributeName, value);
                    continue;
                }
                this.commandParams.add(arg);
                if (!arg.startsWith("--")) continue;
                this.parseOptionArgument(arg, true);
                continue;
            }
            if (arg.startsWith("--")) {
                this.parseOptionArgument(arg, false);
                continue;
            }
            throw new CommandLineParsingException(String.format(coreBundle.getString("unexpected.value"), arg));
        }
        this.parseDefaultPropertyFiles();
        if (this.liquibaseProLicenseKey == null) {
            this.liquibaseProLicenseKey = (String)Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class).getCurrentConfiguredValue(null, null, "liquibase.licenseKey").getValue();
        }
        if (this.propertyProviderClass == null && (clazz = LiquibaseCommandLineConfiguration.PROPERTY_PROVIDER_CLASS.getCurrentValue()) != null) {
            this.propertyProviderClass = clazz.getName();
        }
        if (this.databaseClass == null && (clazz = LiquibaseCommandLineConfiguration.DATABASE_CLASS.getCurrentValue()) != null) {
            this.databaseClass = clazz.getName();
        }
    }

    private void parseOptionArgument(String arg, boolean okIfNotAField) throws CommandLineParsingException {
        block8: {
            String PROMPT_FOR_VALUE = "PROMPT";
            if (arg.toLowerCase().startsWith("--verbose") || arg.toLowerCase().startsWith("--help")) {
                return;
            }
            if (arg.toLowerCase().equals("--force") || arg.toLowerCase().equals("--help")) {
                arg = arg + "=true";
            }
            String[] splitArg = Main.splitArg(arg);
            String attributeName = splitArg[0];
            String value = splitArg[1];
            if ("PROMPT".equalsIgnoreCase(StringUtil.trimToEmpty(value))) {
                Console c = System.console();
                if (c == null) {
                    throw new CommandLineParsingException(String.format(MessageFormat.format(coreBundle.getString("cannot.prompt.for.the.value.no.console"), attributeName), new Object[0]));
                }
                value = attributeName.toLowerCase().contains("password") ? new String(c.readPassword(attributeName + ": ", new Object[0])) : c.readLine(attributeName + ": ", new Object[0]);
            }
            try {
                Field field2 = this.getDeclaredField(attributeName);
                if (field2.getType().equals(Boolean.class)) {
                    field2.set(this, Boolean.valueOf(value));
                } else {
                    field2.set(this, value);
                }
            }
            catch (IllegalAccessException | NoSuchFieldException e) {
                if (okIfNotAField) break block8;
                throw new CommandLineParsingException(String.format(coreBundle.getString("option.unknown"), attributeName));
            }
        }
    }

    private Field getDeclaredField(String attributeName) throws NoSuchFieldException {
        Field[] fields;
        for (Field field2 : fields = this.getClass().getDeclaredFields()) {
            if (!field2.getName().equalsIgnoreCase(attributeName)) continue;
            return field2;
        }
        throw new NoSuchFieldException();
    }

    protected void applyDefaults() {
        if (this.promptForNonLocalDatabase == null) {
            this.promptForNonLocalDatabase = Boolean.FALSE;
        }
        if (this.logLevel == null) {
            this.logLevel = "off";
        }
        if (this.includeSystemClasspath == null) {
            this.includeSystemClasspath = Boolean.TRUE;
        }
        if (this.outputDefaultCatalog == null) {
            this.outputDefaultCatalog = "true";
        }
        if (this.outputDefaultSchema == null) {
            this.outputDefaultSchema = "true";
        }
        if (this.defaultsFile == null) {
            this.defaultsFile = "liquibase.properties";
        }
        if (this.includeSchema == null) {
            this.includeSchema = Boolean.FALSE;
        }
        if (this.includeCatalog == null) {
            this.includeCatalog = Boolean.FALSE;
        }
        if (this.includeTablespace == null) {
            this.includeTablespace = Boolean.FALSE;
        }
    }

    protected ClassLoader configureClassLoader() throws CommandLineParsingException {
        ArrayList<URL> urls2 = new ArrayList<URL>();
        if (this.classpath != null) {
            String[] classpathSoFar = this.isWindows() ? this.classpath.split(";") : this.classpath.split(":");
            for (String classpathEntry : classpathSoFar) {
                File classPathFile = new File(classpathEntry);
                if (!classPathFile.exists()) {
                    throw new CommandLineParsingException(String.format(coreBundle.getString("does.not.exist"), classPathFile.getAbsolutePath()));
                }
                if (classpathEntry.endsWith(".war")) {
                    try {
                        Main.addWarFileClasspathEntries(classPathFile, urls2);
                        continue;
                    }
                    catch (IOException e) {
                        throw new CommandLineParsingException(e);
                    }
                }
                if (classpathEntry.endsWith(".ear")) {
                    try (JarFile earZip = new JarFile(classPathFile);){
                        Enumeration<JarEntry> entries = earZip.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            if (entry.getName().toLowerCase().endsWith(".jar")) {
                                File jar = Main.extract(earZip, entry);
                                URL newUrl = new URL("jar:" + jar.toURI().toURL() + "!/");
                                urls2.add(newUrl);
                                LOG.fine(String.format(coreBundle.getString("adding.to.classpath"), newUrl));
                                jar.deleteOnExit();
                                continue;
                            }
                            if (!entry.getName().toLowerCase().endsWith("war")) continue;
                            File warFile = Main.extract(earZip, entry);
                            Main.addWarFileClasspathEntries(warFile, urls2);
                        }
                        continue;
                    }
                    catch (IOException e) {
                        throw new CommandLineParsingException(e);
                    }
                }
                URL newUrl = null;
                try {
                    newUrl = new File(classpathEntry).toURI().toURL();
                }
                catch (MalformedURLException e) {
                    throw new CommandLineParsingException(e);
                }
                LOG.fine(String.format(coreBundle.getString("adding.to.classpath"), newUrl));
                urls2.add(newUrl);
            }
        }
        this.classLoader = this.includeSystemClasspath != false ? (ClassLoader)AccessController.doPrivileged(() -> new URLClassLoader(urls2.toArray(new URL[0]), Thread.currentThread().getContextClassLoader())) : (ClassLoader)AccessController.doPrivileged(() -> new URLClassLoader(urls2.toArray(new URL[0]), null));
        Thread.currentThread().setContextClassLoader(this.classLoader);
        return this.classLoader;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doMigration() throws Exception {
        block105: {
            if ("help".equalsIgnoreCase(this.command)) {
                this.printHelp(System.err);
                return;
            }
            ResourceAccessor fileOpener = this.getFileOpenerResourceAccessor();
            if ("diff".equalsIgnoreCase(this.command) || "diffChangeLog".equalsIgnoreCase(this.command) || "generateChangeLog".equalsIgnoreCase(this.command) || "releaseLocks".equalsIgnoreCase(this.command) || "rollback".equalsIgnoreCase(this.command) || "rollbackSQL".equalsIgnoreCase(this.command)) {
                this.runUsingCommandFramework();
                return;
            }
            Database database = null;
            if (this.url != null) {
                database = CommandLineUtils.createDatabaseObject(fileOpener, this.url, this.username, this.password, this.driver, this.defaultCatalogName, this.defaultSchemaName, Boolean.parseBoolean(this.outputDefaultCatalog), Boolean.parseBoolean(this.outputDefaultSchema), this.databaseClass, this.driverPropertiesFile, this.propertyProviderClass, this.liquibaseCatalogName, this.liquibaseSchemaName, this.databaseChangeLogTableName, this.databaseChangeLogLockTableName);
                Scope.getCurrentScope().addMdcValue("liquibaseTargetUrl", JdbcConnection.sanitizeUrl(this.url));
                Scope.getCurrentScope().addMdcValue("liquibaseCatalogName", database.getLiquibaseCatalogName());
                Scope.getCurrentScope().addMdcValue("liquibaseSchemaName", database.getLiquibaseSchemaName());
                if (this.databaseChangeLogTablespaceName != null) {
                    database.setLiquibaseTablespaceName(this.databaseChangeLogTablespaceName);
                } else {
                    database.setLiquibaseTablespaceName(GlobalConfiguration.LIQUIBASE_TABLESPACE_NAME.getCurrentConfiguredValue().getValue());
                }
            }
            if (GlobalConfiguration.SHOULD_SNAPSHOT_DATA.getCurrentValue().equals(false) && this.dataOutputDirectory != null) {
                DeprecatedConfigurationValueProvider.setData(GlobalConfiguration.SHOULD_SNAPSHOT_DATA, true);
            }
            try {
                if ("snapshot".equalsIgnoreCase(this.command)) {
                    CommandScope snapshotCommand = new CommandScope("internalSnapshot");
                    snapshotCommand.addArgumentValue(InternalSnapshotCommandStep.DATABASE_ARG, database).addArgumentValue(InternalSnapshotCommandStep.SCHEMAS_ARG, InternalSnapshotCommandStep.parseSchemas(database, this.getSchemaParams(database))).addArgumentValue(InternalSnapshotCommandStep.SERIALIZER_FORMAT_ARG, this.getCommandParam("snapshotFormat", null));
                    Writer outputWriter = this.getOutputWriter();
                    CommandResults commandResults = snapshotCommand.execute();
                    String result = InternalSnapshotCommandStep.printSnapshot(snapshotCommand, commandResults);
                    outputWriter.write(result);
                    outputWriter.flush();
                    return;
                }
                Liquibase liquibase = new Liquibase(this.changeLogFile, fileOpener, database);
                if (newCliChangelogParameters != null) {
                    for (Map.Entry<String, String> param2 : newCliChangelogParameters.entrySet()) {
                        liquibase.setChangeLogParameter(param2.getKey(), param2.getValue());
                    }
                }
                ChangeExecListener listener = ChangeExecListenerUtils.getChangeExecListener(liquibase.getDatabase(), liquibase.getResourceAccessor(), this.changeExecListenerClass, this.changeExecListenerPropertiesFile);
                DefaultChangeExecListener defaultChangeExecListener = liquibase.getDefaultChangeExecListener();
                defaultChangeExecListener.addListener(listener);
                liquibase.setChangeExecListener(defaultChangeExecListener);
                if (database != null) {
                    database.setCurrentDateTimeFunction(this.currentDateTimeFunction);
                }
                for (Map.Entry<String, Object> entry : this.changeLogParameters.entrySet()) {
                    liquibase.setChangeLogParameter(entry.getKey(), entry.getValue());
                }
                if ("listLocks".equalsIgnoreCase(this.command)) {
                    liquibase.reportLocks(System.err);
                    return;
                }
                if ("tag".equalsIgnoreCase(this.command)) {
                    liquibase.tag(this.getCommandArgument());
                    return;
                }
                if ("tagExists".equalsIgnoreCase(this.command)) {
                    String tag = this.commandParams.iterator().next();
                    liquibase.tagExists(tag);
                    return;
                }
                if ("dropAll".equalsIgnoreCase(this.command)) {
                    CommandScope dropAllCommand = new CommandScope("dropAll");
                    dropAllCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, liquibase.getDatabase()).addArgumentValue(DropAllCommandStep.CATALOG_AND_SCHEMAS_ARG, InternalSnapshotCommandStep.parseSchemas(database, this.getSchemaParams(database))).addArgumentValue(GenerateChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
                    dropAllCommand.execute();
                    return;
                }
                if ("status".equalsIgnoreCase(this.command)) {
                    boolean runVerbose = false;
                    if (this.commandParams.contains("--verbose")) {
                        runVerbose = true;
                    }
                    liquibase.reportStatus(runVerbose, new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                    return;
                }
                if ("unexpectedChangeSets".equalsIgnoreCase(this.command)) {
                    boolean runVerbose = false;
                    if (this.commandParams.contains("--verbose")) {
                        runVerbose = true;
                    }
                    liquibase.reportUnexpectedChangeSets(runVerbose, this.contexts, this.getOutputWriter());
                    return;
                }
                if ("validate".equalsIgnoreCase(this.command)) {
                    liquibase.validate();
                    Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("no.validation.errors.found"));
                    return;
                }
                if ("clearCheckSums".equalsIgnoreCase(this.command)) {
                    liquibase.clearCheckSums();
                    return;
                }
                if ("calculateCheckSum".equalsIgnoreCase(this.command)) {
                    String[] calculateChecksumCommand = new CommandScope("calculateChecksum");
                    calculateChecksumCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_PATH_ARG, this.getCommandParam("changeSetPath", null)).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_ID_ARG, this.getCommandParam("changeSetId", null)).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_AUTHOR_ARG, this.getCommandParam("changeSetAuthor", null)).addArgumentValue(CalculateChecksumCommandStep.CHANGESET_IDENTIFIER_ARG, this.getCommandParam("changeSetIdentifier", null)).addArgumentValue(CalculateChecksumCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
                    calculateChecksumCommand.execute();
                    return;
                }
                if ("dbDoc".equalsIgnoreCase(this.command)) {
                    if (this.commandParams.isEmpty()) {
                        throw new CommandLineParsingException(coreBundle.getString("dbdoc.requires.output.directory"));
                    }
                    if (this.changeLogFile == null) {
                        throw new CommandLineParsingException(coreBundle.getString("dbdoc.requires.changelog.parameter"));
                    }
                    if (this.schemas != null) {
                        for (String schema : this.schemas.split(",")) {
                            this.schemaList.add(new CatalogAndSchema(null, schema).customize(database));
                        }
                        CatalogAndSchema[] schemaArr = (CatalogAndSchema[])this.schemaList.stream().toArray(CatalogAndSchema[]::new);
                        liquibase.generateDocumentation(this.commandParams.iterator().next(), this.contexts, schemaArr);
                    } else {
                        liquibase.generateDocumentation(this.commandParams.iterator().next(), this.contexts);
                    }
                    return;
                }
                try {
                    HashMap<String, Object> updateScopedObjects;
                    if ("changelogSync".equalsIgnoreCase(this.command)) {
                        liquibase.changeLogSync(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        break block105;
                    }
                    if ("changelogSyncSQL".equalsIgnoreCase(this.command)) {
                        liquibase.changeLogSync(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("changelogSyncToTag".equalsIgnoreCase(this.command)) {
                        liquibase.changeLogSync(this.commandParams.iterator().next(), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        break block105;
                    }
                    if ("changelogSyncToTagSQL".equalsIgnoreCase(this.command)) {
                        liquibase.changeLogSync(this.commandParams.iterator().next(), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("markNextChangeSetRan".equalsIgnoreCase(this.command)) {
                        liquibase.markNextChangeSetRan(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        break block105;
                    }
                    if ("markNextChangeSetRanSQL".equalsIgnoreCase(this.command)) {
                        liquibase.markNextChangeSetRan(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("updateCount".equalsIgnoreCase(this.command)) {
                        try {
                            updateScopedObjects = new HashMap<String, Object>();
                            updateScopedObjects.put("showSummary", this.showSummary);
                            updateScopedObjects.put("outputStream", outputStream);
                            Scope.child(updateScopedObjects, () -> liquibase.update(Integer.parseInt(this.commandParams.iterator().next()), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter())));
                        }
                        catch (LiquibaseException updateException) {
                            this.handleUpdateException(database, updateException, defaultChangeExecListener, this.rollbackOnError);
                        }
                        break block105;
                    }
                    if ("update".equalsIgnoreCase(this.command)) {
                        liquibase.update(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        break block105;
                    }
                    if ("updateCountSQL".equalsIgnoreCase(this.command)) {
                        liquibase.update(Integer.parseInt(this.commandParams.iterator().next()), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("updateToTag".equalsIgnoreCase(this.command)) {
                        if (this.commandParams == null || this.commandParams.isEmpty()) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.tag"), "updateToTag"));
                        }
                        try {
                            updateScopedObjects = new HashMap();
                            updateScopedObjects.put("showSummary", this.showSummary);
                            updateScopedObjects.put("outputStream", outputStream);
                            Scope.child(updateScopedObjects, () -> liquibase.update(this.commandParams.iterator().next(), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter())));
                        }
                        catch (LiquibaseException updateException) {
                            this.handleUpdateException(database, updateException, defaultChangeExecListener, this.rollbackOnError);
                        }
                        break block105;
                    }
                    if ("updateToTagSQL".equalsIgnoreCase(this.command)) {
                        if (this.commandParams == null || this.commandParams.isEmpty()) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.tag"), "updateToTagSQL"));
                        }
                        liquibase.update(this.commandParams.iterator().next(), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("updateSQL".equalsIgnoreCase(this.command)) {
                        liquibase.update(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("rollbackToDate".equalsIgnoreCase(this.command)) {
                        if (this.getCommandArgument() == null) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.timestamp"), "rollbackToDate"));
                        }
                        liquibase.rollback(new ISODateFormat().parse(this.getCommandArgument()), this.getCommandParam("rollbackScript", null), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        break block105;
                    }
                    if ("rollbackCount".equalsIgnoreCase(this.command)) {
                        Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", "rollbackCount");
                        liquibase.rollback(Integer.parseInt(this.getCommandArgument()), this.getCommandParam("rollbackScript", null), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        break block105;
                    }
                    if ("rollbackToDateSQL".equalsIgnoreCase(this.command)) {
                        if (this.getCommandArgument() == null) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.timestamp"), "rollbackToDateSQL"));
                        }
                        liquibase.rollback(new ISODateFormat().parse(this.getCommandArgument()), this.getCommandParam("rollbackScript", null), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("rollbackCountSQL".equalsIgnoreCase(this.command)) {
                        if (this.getCommandArgument() == null) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.count"), "rollbackCountSQL"));
                        }
                        liquibase.rollback(Integer.parseInt(this.getCommandArgument()), this.getCommandParam("rollbackScript", null), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("futureRollbackSQL".equalsIgnoreCase(this.command)) {
                        liquibase.futureRollbackSQL(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("futureRollbackCountSQL".equalsIgnoreCase(this.command)) {
                        if (this.getCommandArgument() == null) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.count"), "futureRollbackCountSQL"));
                        }
                        liquibase.futureRollbackSQL(Integer.valueOf(this.getCommandArgument()), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("futureRollbackFromTagSQL".equalsIgnoreCase(this.command)) {
                        if (this.getCommandArgument() == null) {
                            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.tag"), "futureRollbackFromTagSQL"));
                        }
                        liquibase.futureRollbackSQL(this.getCommandArgument(), new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()), this.getOutputWriter());
                        break block105;
                    }
                    if ("updateTestingRollback".equalsIgnoreCase(this.command)) {
                        try {
                            liquibase.updateTestingRollback(new Contexts(this.contexts), new LabelExpression(this.getLabelFilter()));
                        }
                        catch (LiquibaseException updateException) {
                            this.handleUpdateException(database, updateException, defaultChangeExecListener, this.rollbackOnError);
                        }
                        break block105;
                    }
                    if ("history".equalsIgnoreCase(this.command)) {
                        CommandScope historyCommand = new CommandScope("internalHistory");
                        historyCommand.addArgumentValue(InternalHistoryCommandStep.DATABASE_ARG, database);
                        historyCommand.addArgumentValue(InternalHistoryCommandStep.FORMAT_ARG, HistoryFormat.valueOf(this.format));
                        historyCommand.setOutput(this.getOutputStream());
                        historyCommand.execute();
                        break block105;
                    }
                    throw new CommandLineParsingException(String.format(coreBundle.getString("command.unknown"), this.command));
                }
                catch (ParseException ignored) {
                    throw new CommandLineParsingException(coreBundle.getString("timeformat.invalid"));
                }
            }
            finally {
                try {
                    if (database != null) {
                        database.rollback();
                        database.close();
                    }
                }
                catch (DatabaseException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning(coreBundle.getString("problem.closing.connection"), e);
                }
            }
        }
    }

    private void runUsingCommandFramework() throws CommandLineParsingException, LiquibaseException, IOException {
        if ("diff".equalsIgnoreCase(this.command)) {
            this.runDiffCommandStep();
        } else if ("diffChangeLog".equalsIgnoreCase(this.command)) {
            this.runDiffChangelogCommandStep();
        } else if ("generateChangeLog".equalsIgnoreCase(this.command)) {
            this.runGenerateChangelogCommandStep();
        } else if ("update".equalsIgnoreCase(this.command)) {
            this.runUpdateCommandStep();
        } else if ("releaseLocks".equalsIgnoreCase(this.command)) {
            this.runReleaseLocksCommand();
        } else if ("rollback".equalsIgnoreCase(this.command)) {
            this.runRollbackCommand(null);
        } else if ("rollbackSQL".equalsIgnoreCase(this.command)) {
            this.runRollbackSqlCommand();
        } else if ("executeSql".equalsIgnoreCase(this.command)) {
            this.runExecuteSqlCommand();
        } else if ("rollbackOneChangeSet".equalsIgnoreCase(this.command)) {
            this.runRollbackOneChangeSetCommandStep();
        } else if ("rollbackOneChangeSetSQL".equalsIgnoreCase(this.command)) {
            this.runRollbackOneChangeSetSqlCommandStep();
        } else if ("rollbackOneUpdate".equalsIgnoreCase(this.command)) {
            this.runRollbackOneUpdateCommandStep();
        } else if ("rollbackOneUpdateSQL".equalsIgnoreCase(this.command)) {
            this.runRollbackOneUpdateSqlCommandStep();
        }
    }

    private void runRollbackSqlCommand() throws CommandLineParsingException, IOException, CommandExecutionException {
        this.runRollbackCommand(this.getOutputWriter());
    }

    private void runRollbackCommand(Writer outputWriter) throws CommandLineParsingException, CommandExecutionException {
        Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", "rollback");
        if (this.getCommandArgument() == null) {
            throw new CommandLineParsingException(String.format(coreBundle.getString("command.requires.tag"), "rollback"));
        }
        String[] commandToRun = outputWriter == null ? RollbackCommandStep.COMMAND_NAME : RollbackSqlCommandStep.COMMAND_NAME;
        CommandScope commandScope = new CommandScope(commandToRun).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DatabaseChangelogCommandStep.CONTEXTS_ARG, this.contexts).addArgumentValue(DatabaseChangelogCommandStep.LABEL_FILTER_ARG, this.getLabelFilter()).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, this.changeExecListenerClass).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, this.changeExecListenerPropertiesFile).addArgumentValue(RollbackCommandStep.TAG_ARG, this.getCommandArgument()).addArgumentValue(AbstractRollbackCommandStep.ROLLBACK_SCRIPT_ARG, this.getCommandParam("rollbackScript", null));
        this.setDatabaseArgumentsToCommand(commandScope);
        if (outputWriter != null) {
            commandScope.setOutput(new WriterOutputStream(outputWriter, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        }
        commandScope.execute();
    }

    private void runReleaseLocksCommand() throws CommandExecutionException {
        CommandScope commandScope = new CommandScope(ReleaseLocksCommandStep.COMMAND_NAME[0]);
        this.setDatabaseArgumentsToCommand(commandScope);
        commandScope.execute();
    }

    private void runGenerateChangelogCommandStep() throws LiquibaseException, IOException, CommandLineParsingException {
        boolean shouldOverwriteOutputFile = Boolean.parseBoolean(this.overwriteOutputFile);
        CommandScope generateChangelogCommand = new CommandScope(GenerateChangelogCommandStep.COMMAND_NAME[0]).addArgumentValue(GenerateChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DiffOutputControlCommandStep.INCLUDE_CATALOG_ARG, this.includeCatalog).addArgumentValue(DiffOutputControlCommandStep.INCLUDE_SCHEMA_ARG, this.includeSchema).addArgumentValue(DiffOutputControlCommandStep.INCLUDE_TABLESPACE_ARG, this.includeTablespace).addArgumentValue(GenerateChangelogCommandStep.AUTHOR_ARG, StringUtil.trimToNull(this.changeSetAuthor)).addArgumentValue(GenerateChangelogCommandStep.CONTEXT_ARG, StringUtil.trimToNull(this.changeSetContext)).addArgumentValue(GenerateChangelogCommandStep.DATA_OUTPUT_DIR_ARG, StringUtil.trimToNull(this.dataOutputDirectory)).addArgumentValue(GenerateChangelogCommandStep.OVERWRITE_OUTPUT_FILE_ARG, shouldOverwriteOutputFile).setOutput(System.out);
        this.setDatabaseArgumentsToCommand(generateChangelogCommand);
        this.setPreCompareArgumentsToCommand(generateChangelogCommand);
    }

    private void runDiffChangelogCommandStep() throws CommandExecutionException, CommandLineParsingException, IOException {
        CommandScope diffChangelogCommand = new CommandScope("diffChangelog").addArgumentValue(DiffChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue(DiffOutputControlCommandStep.INCLUDE_CATALOG_ARG, this.includeCatalog).addArgumentValue(DiffOutputControlCommandStep.INCLUDE_SCHEMA_ARG, this.includeSchema).addArgumentValue(DiffOutputControlCommandStep.INCLUDE_TABLESPACE_ARG, this.includeTablespace).addArgumentValue(DiffChangelogCommandStep.AUTHOR_ARG, StringUtil.trimToNull(this.changeSetAuthor)).setOutput(this.getOutputStream());
        this.setPreCompareArgumentsToCommand(diffChangelogCommand);
        this.setDatabaseArgumentsToCommand(diffChangelogCommand);
        this.setReferenceDatabaseArgumentsToCommand(diffChangelogCommand);
        diffChangelogCommand.execute();
    }

    private void runDiffCommandStep() throws CommandLineParsingException, CommandExecutionException, IOException {
        CommandScope diffCommand = new CommandScope("diff").addArgumentValue(DiffCommandStep.FORMAT_ARG, this.getCommandParam("format", "TXT")).setOutput(this.getOutputStream());
        this.setPreCompareArgumentsToCommand(diffCommand);
        this.setDatabaseArgumentsToCommand(diffCommand);
        this.setReferenceDatabaseArgumentsToCommand(diffCommand);
        diffCommand.execute();
    }

    private void runUpdateCommandStep() throws CommandLineParsingException, CommandExecutionException, IOException {
        CommandScope updateCommand = new CommandScope("update");
        updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile);
        updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, this.contexts);
        updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, this.labelFilter);
        updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, this.changeExecListenerClass);
        updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, this.changeExecListenerPropertiesFile);
        this.setDatabaseArgumentsToCommand(updateCommand);
        updateCommand.execute();
    }

    private void runRollbackOneChangeSetCommandStep() throws CommandExecutionException, CommandLineParsingException {
        CommandScope rollbackOneChangeSet = new CommandScope("rollbackOneChangeset");
        this.setDatabaseArgumentsToCommand(rollbackOneChangeSet);
        rollbackOneChangeSet.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, this.changeExecListenerClass).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, this.changeExecListenerPropertiesFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue("changesetId", this.getCommandParam("changeSetId", null)).addArgumentValue("changesetAuthor", this.getCommandParam("changeSetAuthor", null)).addArgumentValue("changesetPath", this.getCommandParam("changeSetPath", null)).addArgumentValue("force", this.getCommandParam("force", null));
        String internalCommand = "rollbackOneChangeset";
        Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", internalCommand);
        rollbackOneChangeSet.execute();
    }

    private void runRollbackOneChangeSetSqlCommandStep() throws CommandExecutionException, CommandLineParsingException, IOException {
        CommandScope rollbackOneChangeSet = new CommandScope("rollbackOneChangesetSql");
        this.setDatabaseArgumentsToCommand(rollbackOneChangeSet);
        rollbackOneChangeSet.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, this.changeExecListenerClass).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, this.changeExecListenerPropertiesFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue("changesetId", this.getCommandParam("changeSetId", null)).addArgumentValue("changesetAuthor", this.getCommandParam("changeSetAuthor", null)).addArgumentValue("changesetPath", this.getCommandParam("changeSetPath", null)).addArgumentValue("force", this.getCommandParam("force", null));
        String internalCommand = "rollbackOneChangeset";
        Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", internalCommand);
        rollbackOneChangeSet.setOutput(new WriterOutputStream(this.getOutputWriter(), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        rollbackOneChangeSet.execute();
    }

    private void runRollbackOneUpdateCommandStep() throws CommandExecutionException, CommandLineParsingException {
        CommandScope rollbackOneUpdate = new CommandScope("rollbackOneUpdate");
        this.setDatabaseArgumentsToCommand(rollbackOneUpdate);
        rollbackOneUpdate.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, this.changeExecListenerClass).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, this.changeExecListenerPropertiesFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue("deploymentId", this.getCommandParam("deploymentId", null)).addArgumentValue("force", this.getCommandParam("force", null));
        String internalCommand = "rollbackOneUpdate";
        Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", internalCommand);
        rollbackOneUpdate.execute();
    }

    private void runRollbackOneUpdateSqlCommandStep() throws CommandExecutionException, CommandLineParsingException, IOException {
        CommandScope rollbackOneUpdate = new CommandScope("rollbackOneUpdateSql");
        this.setDatabaseArgumentsToCommand(rollbackOneUpdate);
        rollbackOneUpdate.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_CLASS_ARG, this.changeExecListenerClass).addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG, this.changeExecListenerPropertiesFile).addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_FILE_ARG, this.changeLogFile).addArgumentValue("deploymentId", this.getCommandParam("deploymentId", null)).addArgumentValue("force", this.getCommandParam("force", null));
        String internalCommand = "rollbackOneUpdate";
        Scope.getCurrentScope().addMdcValue("liquibaseInternalCommand", internalCommand);
        rollbackOneUpdate.setOutput(new WriterOutputStream(this.getOutputWriter(), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        rollbackOneUpdate.execute();
    }

    private void setDatabaseArgumentsToCommand(CommandScope command) {
        command.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DEFAULT_SCHEMA_NAME_ARG, this.defaultSchemaName).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DEFAULT_CATALOG_NAME_ARG, this.defaultCatalogName).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DRIVER_ARG, this.driver).addArgumentValue(DbUrlConnectionArgumentsCommandStep.DRIVER_PROPERTIES_FILE_ARG, this.driverPropertiesFile).addArgumentValue(DbUrlConnectionArgumentsCommandStep.USERNAME_ARG, this.username).addArgumentValue(DbUrlConnectionArgumentsCommandStep.PASSWORD_ARG, this.password).addArgumentValue(DbUrlConnectionArgumentsCommandStep.URL_ARG, this.url);
    }

    private void setPreCompareArgumentsToCommand(CommandScope command) {
        command.addArgumentValue(PreCompareCommandStep.EXCLUDE_OBJECTS_ARG, this.excludeObjects).addArgumentValue(PreCompareCommandStep.INCLUDE_OBJECTS_ARG, this.includeObjects).addArgumentValue(PreCompareCommandStep.DIFF_TYPES_ARG, this.diffTypes).addArgumentValue(PreCompareCommandStep.SCHEMAS_ARG, this.schemas).addArgumentValue(PreCompareCommandStep.OUTPUT_SCHEMAS_ARG, this.outputSchemasAs).addArgumentValue(PreCompareCommandStep.REFERENCE_SCHEMAS_ARG, this.referenceSchemas);
    }

    private void setReferenceDatabaseArgumentsToCommand(CommandScope command) throws CommandLineParsingException {
        String refDriver = this.referenceDriver;
        String refUrl = this.referenceUrl;
        String refUsername = this.referenceUsername;
        String refPassword = this.referencePassword;
        String refSchemaName = this.referenceDefaultSchemaName;
        String refCatalogName = this.referenceDefaultCatalogName;
        for (String param2 : this.commandParams) {
            String[] splitArg = Main.splitArg(param2);
            String attributeName = splitArg[0];
            String value = splitArg[1];
            if ("referenceDriver".equalsIgnoreCase(attributeName)) {
                refDriver = value;
                continue;
            }
            if ("referenceUrl".equalsIgnoreCase(attributeName)) {
                refUrl = value;
                continue;
            }
            if ("referenceUsername".equalsIgnoreCase(attributeName)) {
                refUsername = value;
                continue;
            }
            if ("referencePassword".equalsIgnoreCase(attributeName)) {
                refPassword = value;
                continue;
            }
            if ("referenceDefaultCatalogName".equalsIgnoreCase(attributeName)) {
                refCatalogName = value;
                continue;
            }
            if ("referenceDefaultSchemaName".equalsIgnoreCase(attributeName)) {
                refSchemaName = value;
                continue;
            }
            if (!"dataOutputDirectory".equalsIgnoreCase(attributeName)) continue;
            this.dataOutputDirectory = value;
        }
        if (refUrl == null) {
            throw new CommandLineParsingException(String.format(coreBundle.getString("option.required"), "--referenceUrl"));
        }
        command.addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DEFAULT_SCHEMA_NAME_ARG, refSchemaName).addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_URL_ARG, refUrl).addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DEFAULT_CATALOG_NAME_ARG, refCatalogName).addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DRIVER_ARG, refDriver).addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DRIVER_PROPERTIES_FILE_ARG, null).addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_USERNAME_ARG, refUsername).addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_PASSWORD_ARG, refPassword);
    }

    private void runExecuteSqlCommand() throws CommandExecutionException, CommandLineParsingException {
        CommandScope executeSql = new CommandScope(ExecuteSqlCommandStep.COMMAND_NAME[0]);
        executeSql.addArgumentValue(ExecuteSqlCommandStep.SQL_ARG, this.getCommandParam("sql", null));
        executeSql.addArgumentValue(ExecuteSqlCommandStep.SQLFILE_ARG, this.getCommandParam("sqlFile", null));
        executeSql.addArgumentValue(ExecuteSqlCommandStep.DELIMITER_ARG, this.getCommandParam("delimiter", ";"));
        this.setDatabaseArgumentsToCommand(executeSql);
        executeSql.execute();
    }

    private ResourceAccessor getFileOpenerResourceAccessor() throws FileNotFoundException {
        ResourceAccessor fileOpener = runningFromNewCli ? Scope.getCurrentScope().getResourceAccessor() : new CompositeResourceAccessor(new DirectoryResourceAccessor(Paths.get(".", new String[0]).toAbsolutePath().toFile()), new ClassLoaderResourceAccessor(this.classLoader));
        return fileOpener;
    }

    private String getLabelFilter() {
        if (this.labelFilter == null) {
            return this.labels;
        }
        return this.labelFilter;
    }

    private void loadChangeSetInfoToMap(Map<String, Object> argsMap) throws CommandLineParsingException {
        argsMap.put("changeSetId", this.getCommandParam("changeSetId", null));
        argsMap.put("changeSetAuthor", this.getCommandParam("changeSetAuthor", null));
        argsMap.put("changeSetPath", this.getCommandParam("changeSetPath", null));
    }

    private boolean isFormattedDiff() throws CommandLineParsingException {
        String formatValue = this.getCommandParam("format", "txt");
        return !formatValue.equalsIgnoreCase("txt") && !formatValue.isEmpty();
    }

    private String getSchemaParams(Database database) throws CommandLineParsingException {
        String schemaParams = this.getCommandParam("schemas", this.schemas);
        if (schemaParams == null || schemaParams.isEmpty()) {
            return database.getDefaultSchema().getSchemaName();
        }
        return schemaParams;
    }

    private CommandScope createLiquibaseCommand(Database database, Liquibase liquibase, String commandName, Map<String, Object> argsMap) throws LiquibaseException {
        argsMap.put("rollbackScript", this.rollbackScript);
        argsMap.put("changeLogFile", this.changeLogFile);
        argsMap.put("database", database);
        argsMap.put("liquibase", liquibase);
        if (!this.commandParams.contains("--help")) {
            argsMap.put("changeLog", liquibase.getDatabaseChangeLog());
        }
        ChangeLogParameters clp = new ChangeLogParameters(database);
        for (Map.Entry<String, Object> entry : this.changeLogParameters.entrySet()) {
            clp.set(entry.getKey(), entry.getValue());
        }
        argsMap.put("changeLogParameters", clp);
        if (this.commandParams.contains("--force") || this.commandParams.contains("--force=true")) {
            argsMap.put("force", Boolean.TRUE);
        }
        if (this.commandParams.contains("--help")) {
            argsMap.put("help", Boolean.TRUE);
        }
        if (this.liquibaseProLicenseKey != null) {
            argsMap.put("liquibaseProLicenseKey", this.liquibaseProLicenseKey);
        }
        CommandScope liquibaseCommand = new CommandScope(commandName);
        for (Map.Entry<String, Object> entry : argsMap.entrySet()) {
            liquibaseCommand.addArgumentValue(entry.getKey(), entry.getValue());
        }
        return liquibaseCommand;
    }

    private String getCommandArgument() {
        for (String param2 : this.commandParams) {
            if (param2.contains("=")) continue;
            return param2;
        }
        return null;
    }

    private String getCommandParam(String paramName, String defaultValue) throws CommandLineParsingException {
        for (String param2 : this.commandParams) {
            if (!param2.contains("=")) continue;
            String[] splitArg = Main.splitArg(param2);
            String attributeName = splitArg[0];
            String value = splitArg[1];
            if (!attributeName.equalsIgnoreCase(paramName)) continue;
            return value;
        }
        return defaultValue;
    }

    private OutputStream getOutputStream() throws IOException {
        if (outputStream != null) {
            return outputStream;
        }
        if (this.outputFile != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(this.outputFile, false);
                return fileOut;
            }
            catch (IOException e) {
                Scope.getCurrentScope().getLog(this.getClass()).severe(String.format(coreBundle.getString("could.not.create.output.file"), this.outputFile));
                throw e;
            }
        }
        return outputStream;
    }

    private void handleUpdateException(Database database, LiquibaseException exception, DefaultChangeExecListener defaultChangeExecListener, boolean rollbackOnError) throws LiquibaseException {
        try {
            CommandScope commandScope = new CommandScope("internalRollbackOnError");
            commandScope.addArgumentValue("database", database);
            commandScope.addArgumentValue("exception", exception);
            commandScope.addArgumentValue("listener", defaultChangeExecListener);
            commandScope.addArgumentValue("rollbackOnError", (Object)rollbackOnError);
            commandScope.execute();
        }
        catch (IllegalArgumentException ignoredCommandNotFound) {
            throw exception;
        }
    }

    public static PrintStream setOutputStream(PrintStream outputStream2) {
        outputStream = outputStream2;
        return outputStream2;
    }

    private Writer getOutputWriter() throws IOException {
        String charsetName = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
        return new OutputStreamWriter(this.getOutputStream(), charsetName);
    }

    public boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows ");
    }

    static {
        outputStream = System.out;
        LOG = Scope.getCurrentScope().getLog(Main.class);
        coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
        managingLogConfig = null;
        suspiciousCodePoints = new int[]{160, 225, 226, 227, 228, 229, 230, 198, 200, 201, 202, 203, 204, 205, 206, 207, 209, 210, 211, 212, 213, 214, 217, 218, 219, 220, 222, 223, 232, 233, 234, 235, 236, 237, 238, 239, 241, 249, 250, 251, 252, 255, 284, 332, 333, 334, 335, 336, 337, 359, 360, 361, 362, 363, 364, 365, 366, 367, 377, 399, 8192, 8193, 8194, 8196, 8197, 8199, 8200, 8201, 8202, 8203, 8211, 8287};
    }

    private static final class OPTIONS
    extends Enum<OPTIONS> {
        private static final String VERBOSE = "verbose";
        private static final String CHANGELOG_FILE = "changeLogFile";
        private static final String DATA_OUTPUT_DIRECTORY = "dataOutputDirectory";
        private static final String DIFF_TYPES = "diffTypes";
        public static final String CHANGE_SET_IDENTIFIER = "changeSetIdentifier";
        private static final String CHANGE_SET_ID = "changeSetId";
        private static final String CHANGE_SET_AUTHOR = "changeSetAuthor";
        private static final String CHANGE_SET_PATH = "changeSetPath";
        private static final String DEPLOYMENT_ID = "deploymentId";
        private static final String OUTPUT_FILE = "outputFile";
        private static final String FORCE = "force";
        private static final String FORMAT = "format";
        private static final String ROLLBACK_SCRIPT = "rollbackScript";
        private static final String EXCLUDE_OBJECTS = "excludeObjects";
        private static final String INCLUDE_CATALOG = "includeCatalog";
        private static final String INCLUDE_OBJECTS = "includeObjects";
        private static final String INCLUDE_SCHEMA = "includeSchema";
        private static final String INCLUDE_TABLESPACE = "includeTablespace";
        private static final String OUTPUT_SCHEMAS_AS = "outputSchemasAs";
        private static final String REFERENCE_DEFAULT_CATALOG_NAME = "referenceDefaultCatalogName";
        private static final String REFERENCE_DEFAULT_SCHEMA_NAME = "referenceDefaultSchemaName";
        private static final String REFERENCE_DRIVER = "referenceDriver";
        private static final String REFERENCE_PASSWORD = "referencePassword";
        private static final String REFERENCE_SCHEMAS = "referenceSchemas";
        private static final String REFERENCE_URL = "referenceUrl";
        private static final String REFERENCE_USERNAME = "referenceUsername";
        private static final String SCHEMAS = "schemas";
        private static final String URL = "url";
        private static final String HELP = "help";
        private static final String VERSION = "version";
        private static final String SNAPSHOT_FORMAT = "snapshotFormat";
        private static final String LOG_FILE = "logFile";
        private static final String LOG_LEVEL = "logLevel";
        private static final /* synthetic */ OPTIONS[] $VALUES;

        public static OPTIONS[] values() {
            return (OPTIONS[])$VALUES.clone();
        }

        public static OPTIONS valueOf(String name) {
            return Enum.valueOf(OPTIONS.class, name);
        }

        private static /* synthetic */ OPTIONS[] $values() {
            return new OPTIONS[0];
        }

        static {
            $VALUES = OPTIONS.$values();
        }
    }

    private static final class COMMANDS
    extends Enum<COMMANDS> {
        private static final String CALCULATE_CHECKSUM = "calculateCheckSum";
        private static final String CHANGELOG_SYNC = "changelogSync";
        private static final String CHANGELOG_SYNC_SQL = "changelogSyncSQL";
        private static final String CHANGELOG_SYNC_TO_TAG = "changelogSyncToTag";
        private static final String CHANGELOG_SYNC_TO_TAG_SQL = "changelogSyncToTagSQL";
        private static final String CLEAR_CHECKSUMS = "clearCheckSums";
        private static final String DB_DOC = "dbDoc";
        private static final String DIFF = "diff";
        private static final String DIFF_CHANGELOG = "diffChangeLog";
        private static final String DROP_ALL = "dropAll";
        private static final String EXECUTE_SQL = "executeSql";
        private static final String FUTURE_ROLLBACK_COUNT_SQL = "futureRollbackCountSQL";
        private static final String FUTURE_ROLLBACK_FROM_TAG_SQL = "futureRollbackFromTagSQL";
        private static final String FUTURE_ROLLBACK_SQL = "futureRollbackSQL";
        private static final String GENERATE_CHANGELOG = "generateChangeLog";
        private static final String HELP = "help";
        private static final String HISTORY = "history";
        private static final String LIST_LOCKS = "listLocks";
        private static final String MARK_NEXT_CHANGESET_RAN = "markNextChangeSetRan";
        private static final String MARK_NEXT_CHANGESET_RAN_SQL = "markNextChangeSetRanSQL";
        private static final String MIGRATE = "migrate";
        private static final String MIGRATE_SQL = "migrateSQL";
        private static final String RELEASE_LOCKS = "releaseLocks";
        private static final String ROLLBACK_ONE_CHANGE_SET = "rollbackOneChangeSet";
        private static final String ROLLBACK_ONE_CHANGE_SET_SQL = "rollbackOneChangeSetSQL";
        private static final String ROLLBACK_ONE_UPDATE = "rollbackOneUpdate";
        private static final String ROLLBACK_ONE_UPDATE_SQL = "rollbackOneUpdateSQL";
        private static final String FORMATTED_DIFF = "formattedDiff";
        private static final String ROLLBACK = "rollback";
        private static final String ROLLBACK_COUNT = "rollbackCount";
        private static final String ROLLBACK_COUNT_SQL = "rollbackCountSQL";
        private static final String ROLLBACK_SCRIPT = "rollbackScript";
        private static final String ROLLBACK_SQL = "rollbackSQL";
        private static final String ROLLBACK_TO_DATE = "rollbackToDate";
        private static final String ROLLBACK_TO_DATE_SQL = "rollbackToDateSQL";
        private static final String SNAPSHOT = "snapshot";
        private static final String SNAPSHOT_REFERENCE = "snapshotReference";
        private static final String STATUS = "status";
        private static final String TAG = "tag";
        private static final String TAG_EXISTS = "tagExists";
        private static final String UNEXPECTED_CHANGESETS = "unexpectedChangeSets";
        private static final String UPDATE = "update";
        private static final String UPDATE_COUNT = "updateCount";
        private static final String UPDATE_COUNT_SQL = "updateCountSQL";
        private static final String UPDATE_SQL = "updateSQL";
        private static final String UPDATE_TESTING_ROLLBACK = "updateTestingRollback";
        private static final String UPDATE_TO_TAG = "updateToTag";
        private static final String UPDATE_TO_TAG_SQL = "updateToTagSQL";
        private static final String VALIDATE = "validate";
        private static final /* synthetic */ COMMANDS[] $VALUES;

        public static COMMANDS[] values() {
            return (COMMANDS[])$VALUES.clone();
        }

        public static COMMANDS valueOf(String name) {
            return Enum.valueOf(COMMANDS.class, name);
        }

        private static /* synthetic */ COMMANDS[] $values() {
            return new COMMANDS[0];
        }

        static {
            $VALUES = COMMANDS.$values();
        }
    }

    private static final class FILE_SUFFIXES
    extends Enum<FILE_SUFFIXES> {
        private static final String FILE_SUFFIX_EAR = ".ear";
        private static final String WAR_FILE_SUFFIX = ".war";
        private static final /* synthetic */ FILE_SUFFIXES[] $VALUES;

        public static FILE_SUFFIXES[] values() {
            return (FILE_SUFFIXES[])$VALUES.clone();
        }

        public static FILE_SUFFIXES valueOf(String name) {
            return Enum.valueOf(FILE_SUFFIXES.class, name);
        }

        private static /* synthetic */ FILE_SUFFIXES[] $values() {
            return new FILE_SUFFIXES[0];
        }

        static {
            $VALUES = FILE_SUFFIXES.$values();
        }
    }

    protected static class CodePointCheck {
        public int position;
        public char ch;

        protected CodePointCheck() {
        }
    }
}

