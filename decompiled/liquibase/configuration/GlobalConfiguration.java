/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.Set;
import liquibase.configuration.AbstractConfigurationContainer;
import liquibase.configuration.ConfigurationContainer;
import liquibase.configuration.ConfigurationProperty;
import liquibase.configuration.core.DeprecatedConfigurationValueProvider;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;

public class GlobalConfiguration
extends liquibase.GlobalConfiguration
implements ConfigurationContainer {
    public static final String SHOULD_RUN = LiquibaseCommandLineConfiguration.SHOULD_RUN.getKey();
    public static final String DATABASECHANGELOG_TABLE_NAME = liquibase.GlobalConfiguration.DATABASECHANGELOG_TABLE_NAME.getKey();
    public static final String DATABASECHANGELOGLOCK_TABLE_NAME = liquibase.GlobalConfiguration.DATABASECHANGELOGLOCK_TABLE_NAME.getKey();
    public static final String LIQUIBASE_TABLESPACE_NAME = liquibase.GlobalConfiguration.LIQUIBASE_TABLESPACE_NAME.getKey();
    public static final String LIQUIBASE_CATALOG_NAME = liquibase.GlobalConfiguration.LIQUIBASE_CATALOG_NAME.getKey();
    public static final String LIQUIBASE_SCHEMA_NAME = liquibase.GlobalConfiguration.LIQUIBASE_SCHEMA_NAME.getKey();
    public static final String OUTPUT_LINE_SEPARATOR = liquibase.GlobalConfiguration.OUTPUT_LINE_SEPARATOR.getKey();
    public static final String OUTPUT_ENCODING = liquibase.GlobalConfiguration.OUTPUT_FILE_ENCODING.getKey();
    public static final String CHANGELOGLOCK_WAIT_TIME = liquibase.GlobalConfiguration.CHANGELOGLOCK_WAIT_TIME.getKey();
    public static final String CHANGELOGLOCK_POLL_RATE = liquibase.GlobalConfiguration.CHANGELOGLOCK_POLL_RATE.getKey();
    public static final String CONVERT_DATA_TYPES = liquibase.GlobalConfiguration.CONVERT_DATA_TYPES.getKey();
    public static final String GENERATE_CHANGESET_CREATED_VALUES = liquibase.GlobalConfiguration.GENERATE_CHANGESET_CREATED_VALUES.getKey();
    public static final String AUTO_REORG = liquibase.GlobalConfiguration.AUTO_REORG.getKey();
    public static final String DIFF_COLUMN_ORDER = liquibase.GlobalConfiguration.DIFF_COLUMN_ORDER.getKey();
    public static final String ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA = liquibase.GlobalConfiguration.ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA.getKey();
    public static final String GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION = liquibase.GlobalConfiguration.GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION.getKey();
    public static final String INCLUDE_CATALOG_IN_SPECIFICATION = liquibase.GlobalConfiguration.INCLUDE_CATALOG_IN_SPECIFICATION.getKey();
    public static final String SHOULD_SNAPSHOT_DATA = liquibase.GlobalConfiguration.SHOULD_SNAPSHOT_DATA.getKey();
    public static final String FILTER_LOG_MESSAGES = liquibase.GlobalConfiguration.FILTER_LOG_MESSAGES.getKey();
    public static final String HEADLESS = liquibase.GlobalConfiguration.HEADLESS.getKey();
    private static final AbstractConfigurationContainer.DelegatedConfigurationContainer containerDelegate = new AbstractConfigurationContainer.DelegatedConfigurationContainer("liquibase");

    public boolean getShouldRun() {
        return LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentValue();
    }

    public GlobalConfiguration setShouldRun(boolean shouldRun) {
        DeprecatedConfigurationValueProvider.setData(LiquibaseCommandLineConfiguration.SHOULD_RUN, shouldRun);
        return this;
    }

    public String getDatabaseChangeLogTableName() {
        return liquibase.GlobalConfiguration.DATABASECHANGELOG_TABLE_NAME.getCurrentValue();
    }

    public GlobalConfiguration setDatabaseChangeLogTableName(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.DATABASECHANGELOG_TABLE_NAME, name);
        return this;
    }

    public String getDatabaseChangeLogLockTableName() {
        return liquibase.GlobalConfiguration.DATABASECHANGELOGLOCK_TABLE_NAME.getCurrentValue();
    }

    public GlobalConfiguration setDatabaseChangeLogLockTableName(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.DATABASECHANGELOGLOCK_TABLE_NAME, name);
        return this;
    }

    public Long getDatabaseChangeLogLockWaitTime() {
        return liquibase.GlobalConfiguration.CHANGELOGLOCK_WAIT_TIME.getCurrentValue();
    }

    public GlobalConfiguration setDatabaseChangeLogLockWaitTime(Long minutes) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.CHANGELOGLOCK_WAIT_TIME, minutes);
        return this;
    }

    public Long getDatabaseChangeLogLockPollRate() {
        return liquibase.GlobalConfiguration.CHANGELOGLOCK_POLL_RATE.getCurrentValue();
    }

    public GlobalConfiguration setDatabaseChangeLogLockPollRate(Long seconds) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.CHANGELOGLOCK_POLL_RATE, seconds);
        return this;
    }

    public String getLiquibaseTablespaceName() {
        return liquibase.GlobalConfiguration.LIQUIBASE_TABLESPACE_NAME.getCurrentValue();
    }

    public GlobalConfiguration setLiquibaseTablespaceName(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.LIQUIBASE_TABLESPACE_NAME, name);
        return this;
    }

    public boolean getShouldSnapshotData() {
        return liquibase.GlobalConfiguration.SHOULD_SNAPSHOT_DATA.getCurrentValue();
    }

    public GlobalConfiguration setShouldSnapshotData(boolean shouldSnapshotData) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.SHOULD_SNAPSHOT_DATA, shouldSnapshotData);
        return this;
    }

    public boolean getShouldFilterLogMessages() {
        return false;
    }

    public GlobalConfiguration setShouldFilterLogMessages(boolean ignored) {
        return this;
    }

    public boolean getHeadless() {
        return liquibase.GlobalConfiguration.HEADLESS.getCurrentValue();
    }

    public GlobalConfiguration setHeadless(boolean headless) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.HEADLESS, headless);
        return this;
    }

    public String getLiquibaseCatalogName() {
        return liquibase.GlobalConfiguration.LIQUIBASE_CATALOG_NAME.getCurrentValue();
    }

    public GlobalConfiguration setLiquibaseCatalogName(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.LIQUIBASE_CATALOG_NAME, name);
        return this;
    }

    public String getLiquibaseSchemaName() {
        return liquibase.GlobalConfiguration.LIQUIBASE_SCHEMA_NAME.getCurrentValue();
    }

    public GlobalConfiguration setLiquibaseSchemaName(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.LIQUIBASE_SCHEMA_NAME, name);
        return this;
    }

    public String getOutputLineSeparator() {
        return liquibase.GlobalConfiguration.OUTPUT_LINE_SEPARATOR.getCurrentValue();
    }

    public GlobalConfiguration setOutputLineSeparator(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.OUTPUT_LINE_SEPARATOR, name);
        return this;
    }

    public String getOutputEncoding() {
        return liquibase.GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
    }

    public GlobalConfiguration setOutputEncoding(String name) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.OUTPUT_FILE_ENCODING, name);
        return this;
    }

    public Boolean getDiffColumnOrder() {
        return liquibase.GlobalConfiguration.DIFF_COLUMN_ORDER.getCurrentValue();
    }

    public GlobalConfiguration setDiffColumnOrder(boolean diff) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.DIFF_COLUMN_ORDER, diff);
        return this;
    }

    public Boolean getAlwaysOverrideStoredLogicSchema() {
        return liquibase.GlobalConfiguration.ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA.getCurrentValue();
    }

    public GlobalConfiguration setAlwaysOverrideStoredLogicSchema(boolean override) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.ALWAYS_OVERRIDE_STORED_LOGIC_SCHEMA, override);
        return this;
    }

    public Boolean getGeneratedChangeSetIdsContainDescription() {
        return liquibase.GlobalConfiguration.GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION.getCurrentValue();
    }

    public GlobalConfiguration setGeneratedChangeSetIdsContainDescription(boolean containDescription) {
        DeprecatedConfigurationValueProvider.setData(liquibase.GlobalConfiguration.GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION, containDescription);
        return this;
    }

    @Override
    public ConfigurationProperty getProperty(String propertyName) {
        return containerDelegate.getProperty(propertyName);
    }

    @Override
    public Set<ConfigurationProperty> getProperties() {
        return containerDelegate.getProperties();
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> returnType) {
        return containerDelegate.getValue(propertyName, returnType);
    }

    @Override
    public void setValue(String propertyName, Object value) {
        containerDelegate.setValue(propertyName, value);
    }

    @Override
    public String getNamespace() {
        return containerDelegate.getNamespace();
    }
}

