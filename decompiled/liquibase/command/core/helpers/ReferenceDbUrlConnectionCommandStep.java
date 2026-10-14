/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.AbstractDatabaseConnectionCommandStep;
import liquibase.command.providers.ReferenceDatabase;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.util.StringUtil;

public class ReferenceDbUrlConnectionCommandStep
extends AbstractDatabaseConnectionCommandStep
implements CleanUpCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"referenceDbUrlConnectionCommandStep"};
    public static final CommandArgumentDefinition<Database> REFERENCE_DATABASE_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_USERNAME_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_URL_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_DEFAULT_SCHEMA_NAME_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_DEFAULT_CATALOG_NAME_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_DRIVER_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_DRIVER_PROPERTIES_FILE_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_LIQUIBASE_SCHEMA_NAME_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_LIQUIBASE_CATALOG_NAME_ARG;

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.singletonList(ReferenceDatabase.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        commandScope.provideDependency(ReferenceDatabase.class, this.obtainDatabase(commandScope));
    }

    private Database obtainDatabase(CommandScope commandScope) throws DatabaseException {
        if (commandScope.getArgumentValue(REFERENCE_DATABASE_ARG) == null) {
            String url2 = commandScope.getArgumentValue(REFERENCE_URL_ARG);
            String username = commandScope.getArgumentValue(REFERENCE_USERNAME_ARG);
            String password = commandScope.getArgumentValue(REFERENCE_PASSWORD_ARG);
            String defaultSchemaName = commandScope.getArgumentValue(REFERENCE_DEFAULT_SCHEMA_NAME_ARG);
            String defaultCatalogName = commandScope.getArgumentValue(REFERENCE_DEFAULT_CATALOG_NAME_ARG);
            String driver = commandScope.getArgumentValue(REFERENCE_DRIVER_ARG);
            String driverPropertiesFile = commandScope.getArgumentValue(REFERENCE_DRIVER_PROPERTIES_FILE_ARG);
            ReferenceDbUrlConnectionCommandStep.logMdc(url2, username, defaultSchemaName, defaultCatalogName);
            return this.createDatabaseObject(url2, username, password, defaultSchemaName, defaultCatalogName, driver, driverPropertiesFile, StringUtil.trimToNull(commandScope.getArgumentValue(REFERENCE_LIQUIBASE_CATALOG_NAME_ARG)), StringUtil.trimToNull(commandScope.getArgumentValue(REFERENCE_LIQUIBASE_SCHEMA_NAME_ARG)));
        }
        return commandScope.getArgumentValue(REFERENCE_DATABASE_ARG);
    }

    public static void logMdc(String url2, String username, String defaultSchemaName, String defaultCatalogName) {
        Scope.getCurrentScope().addMdcValue("liquibaseReferenceUrl", JdbcConnection.sanitizeUrl(url2));
        Scope.getCurrentScope().addMdcValue("referenceUsername", username);
        Scope.getCurrentScope().addMdcValue("referenceDefaultSchemaName", defaultSchemaName);
        Scope.getCurrentScope().addMdcValue("referenceDefaultCatalogName", defaultCatalogName);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        REFERENCE_DEFAULT_SCHEMA_NAME_ARG = builder.argument("referenceDefaultSchemaName", String.class).description("The default schema name to use for the reference database connection").build();
        REFERENCE_DEFAULT_CATALOG_NAME_ARG = builder.argument("referenceDefaultCatalogName", String.class).description("The default catalog name to use for the reference database connection").build();
        REFERENCE_DRIVER_ARG = builder.argument("referenceDriver", String.class).description("The JDBC driver class for the reference database").build();
        REFERENCE_DRIVER_PROPERTIES_FILE_ARG = builder.argument("referenceDriverPropertiesFile", String.class).description("The JDBC driver properties file for the reference database").build();
        REFERENCE_USERNAME_ARG = builder.argument("referenceUsername", String.class).description("The reference database username").build();
        REFERENCE_PASSWORD_ARG = builder.argument("referencePassword", String.class).description("The reference database password").setValueObfuscator(ConfigurationValueObfuscator.STANDARD).build();
        REFERENCE_DATABASE_ARG = builder.argument("referenceDatabase", Database.class).hidden().build();
        REFERENCE_URL_ARG = builder.argument("referenceUrl", String.class).required().supersededBy(REFERENCE_DATABASE_ARG).description("The JDBC reference database connection URL").build();
        REFERENCE_DATABASE_ARG.setSupersededBy(REFERENCE_URL_ARG);
        REFERENCE_LIQUIBASE_SCHEMA_NAME_ARG = builder.argument("referenceLiquibaseSchemaName", String.class).description("Reference schema to use for Liquibase objects").build();
        REFERENCE_LIQUIBASE_CATALOG_NAME_ARG = builder.argument("referenceLiquibaseCatalogName", String.class).description("Reference catalog to use for Liquibase objects").build();
    }
}

