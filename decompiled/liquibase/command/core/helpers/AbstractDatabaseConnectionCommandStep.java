/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.util.ResourceBundle;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.core.helpers.AbstractHelperCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.DatabaseUtils;
import liquibase.exception.DatabaseException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.resource.ResourceAccessor;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public abstract class AbstractDatabaseConnectionCommandStep
extends AbstractHelperCommandStep
implements CleanUpCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"abstractDatabaseConnectionCommandStep"};
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    private Database database;

    protected Database createDatabaseObject(String url2, String username, String password, String defaultSchemaName, String defaultCatalogName, String driver, String driverPropertiesFile, String liquibaseCatalogName, String liquibaseSchemaName) throws DatabaseException {
        ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
        String databaseClassName = null;
        Class databaseClass = LiquibaseCommandLineConfiguration.DATABASE_CLASS.getCurrentValue();
        if (databaseClass != null) {
            databaseClassName = databaseClass.getCanonicalName();
        }
        String propertyProviderClass = null;
        Class clazz = LiquibaseCommandLineConfiguration.PROPERTY_PROVIDER_CLASS.getCurrentValue();
        if (clazz != null) {
            propertyProviderClass = clazz.getName();
        }
        String databaseChangeLogTablespaceName = StringUtil.trimToNull(GlobalConfiguration.LIQUIBASE_TABLESPACE_NAME.getCurrentValue());
        String databaseChangeLogLockTableName = StringUtil.trimToNull(GlobalConfiguration.DATABASECHANGELOGLOCK_TABLE_NAME.getCurrentValue());
        String databaseChangeLogTableName = StringUtil.trimToNull(GlobalConfiguration.DATABASECHANGELOG_TABLE_NAME.getCurrentValue());
        try {
            defaultCatalogName = StringUtil.trimToNull(defaultCatalogName);
            defaultSchemaName = StringUtil.trimToNull(defaultSchemaName);
            this.database = DatabaseFactory.getInstance().openDatabase(url2, username, password, driver, databaseClassName, driverPropertiesFile, propertyProviderClass, resourceAccessor);
            if (!this.database.supports(Schema.class)) {
                if (defaultSchemaName != null && defaultCatalogName == null) {
                    defaultCatalogName = defaultSchemaName;
                }
                if (liquibaseSchemaName != null && liquibaseCatalogName == null) {
                    liquibaseCatalogName = liquibaseSchemaName;
                }
            }
            defaultCatalogName = StringUtil.trimToNull(defaultCatalogName);
            defaultSchemaName = StringUtil.trimToNull(defaultSchemaName);
            this.database.setDefaultCatalogName(defaultCatalogName);
            this.database.setDefaultSchemaName(defaultSchemaName);
            this.database.setOutputDefaultCatalog(true);
            this.database.setOutputDefaultSchema(true);
            this.database.setLiquibaseCatalogName(liquibaseCatalogName);
            this.database.setLiquibaseTablespaceName(databaseChangeLogTablespaceName);
            this.database.setLiquibaseSchemaName(liquibaseSchemaName);
            if (databaseChangeLogTableName != null) {
                this.database.setDatabaseChangeLogTableName(databaseChangeLogTableName);
                if (databaseChangeLogLockTableName != null) {
                    this.database.setDatabaseChangeLogLockTableName(databaseChangeLogLockTableName);
                } else {
                    this.database.setDatabaseChangeLogLockTableName(databaseChangeLogTableName + "LOCK");
                }
            }
            DatabaseUtils.initializeDatabase(defaultCatalogName, defaultSchemaName, this.database);
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
        return this.database;
    }

    @Override
    public void cleanUp(CommandResultsBuilder resultsBuilder) {
        if (this.database != null) {
            try {
                this.database.close();
                this.database = null;
            }
            catch (Exception e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning(coreBundle.getString("problem.closing.connection"), e);
            }
        }
    }
}

