/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.Collections;
import java.util.List;
import liquibase.Beta;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.AbstractDatabaseConnectionCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.configuration.ConfiguredValue;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.util.StringUtil;

public class DbUrlConnectionCommandStep
extends AbstractDatabaseConnectionCommandStep
implements CleanUpCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"dbUrlConnectionCommandStep"};
    @Deprecated
    public static final CommandArgumentDefinition<Database> DATABASE_ARG = DbUrlConnectionArgumentsCommandStep.DATABASE_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> URL_ARG = DbUrlConnectionArgumentsCommandStep.URL_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> DEFAULT_SCHEMA_NAME_ARG = DbUrlConnectionArgumentsCommandStep.DEFAULT_SCHEMA_NAME_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> DEFAULT_CATALOG_NAME_ARG = DbUrlConnectionArgumentsCommandStep.DEFAULT_CATALOG_NAME_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> USERNAME_ARG = DbUrlConnectionArgumentsCommandStep.USERNAME_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> PASSWORD_ARG = DbUrlConnectionArgumentsCommandStep.PASSWORD_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> DRIVER_ARG = DbUrlConnectionArgumentsCommandStep.DRIVER_ARG;
    @Deprecated
    public static final CommandArgumentDefinition<String> DRIVER_PROPERTIES_FILE_ARG = DbUrlConnectionArgumentsCommandStep.DRIVER_PROPERTIES_FILE_ARG;
    @Deprecated
    @Beta
    public static final CommandArgumentDefinition<Boolean> SKIP_DATABASE_STEP_ARG = DbUrlConnectionArgumentsCommandStep.SKIP_DATABASE_STEP_ARG;

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        if (commandScope.getArgumentValue(DbUrlConnectionArgumentsCommandStep.SKIP_DATABASE_STEP_ARG).booleanValue()) {
            return;
        }
        commandScope.provideDependency(Database.class, this.obtainDatabase(commandScope));
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.singletonList(DbUrlConnectionArgumentsCommandStep.class);
    }

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.singletonList(Database.class);
    }

    public Database obtainDatabase(CommandScope commandScope) throws DatabaseException {
        if (commandScope.getArgumentValue(DATABASE_ARG) == null) {
            String url2 = commandScope.getArgumentValue(URL_ARG);
            String username = commandScope.getArgumentValue(USERNAME_ARG);
            String password = commandScope.getArgumentValue(PASSWORD_ARG);
            String defaultSchemaName = commandScope.getArgumentValue(DbUrlConnectionArgumentsCommandStep.DEFAULT_SCHEMA_NAME_ARG);
            String defaultCatalogName = commandScope.getArgumentValue(DbUrlConnectionArgumentsCommandStep.DEFAULT_CATALOG_NAME_ARG);
            String driver = DbUrlConnectionCommandStep.getDriver(commandScope);
            String driverPropertiesFile = commandScope.getArgumentValue(DbUrlConnectionArgumentsCommandStep.DRIVER_PROPERTIES_FILE_ARG);
            Database database = this.createDatabaseObject(url2, username, password, defaultSchemaName, defaultCatalogName, driver, driverPropertiesFile, StringUtil.trimToNull(GlobalConfiguration.LIQUIBASE_CATALOG_NAME.getCurrentValue()), StringUtil.trimToNull(GlobalConfiguration.LIQUIBASE_SCHEMA_NAME.getCurrentValue()));
            DbUrlConnectionCommandStep.logMdc(url2, database);
            return database;
        }
        return commandScope.getArgumentValue(DATABASE_ARG);
    }

    private static String getDriver(CommandScope commandScope) {
        ConfiguredValue<String> globalConfig = LiquibaseCommandLineConfiguration.DRIVER.getCurrentConfiguredValue();
        ConfiguredValue<String> commandConfig = commandScope.getConfiguredValue(DbUrlConnectionArgumentsCommandStep.DRIVER_ARG);
        if (globalConfig.found() && commandConfig.found()) {
            Scope.getCurrentScope().getLog(DbUrlConnectionCommandStep.class).warning("Ignoring the global " + LiquibaseCommandLineConfiguration.DRIVER.getKey() + " value in favor of the command value.");
        }
        if (commandConfig.found()) {
            return commandConfig.getValue();
        }
        if (globalConfig.found()) {
            return globalConfig.getValue();
        }
        return null;
    }

    public static void logMdc(String url2, Database database) {
        Scope.getCurrentScope().addMdcValue("liquibaseTargetUrl", JdbcConnection.sanitizeUrl(url2));
        Scope.getCurrentScope().addMdcValue("liquibaseCatalogName", database.getLiquibaseCatalogName());
        Scope.getCurrentScope().addMdcValue("liquibaseSchemaName", database.getLiquibaseSchemaName());
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }
}

