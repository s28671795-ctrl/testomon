/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.ResourceLoader
 */
package liquibase.integration.spring;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import liquibase.Contexts;
import liquibase.GlobalConfiguration;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.configuration.ConfiguredValue;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.OfflineConnection;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.integration.spring.Customizer;
import liquibase.integration.spring.SpringResourceAccessor;
import liquibase.logging.Logger;
import liquibase.resource.ResourceAccessor;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.ui.UIServiceEnum;
import liquibase.util.StringUtil;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class SpringLiquibase
implements InitializingBean,
BeanNameAware,
ResourceLoaderAware {
    protected final Logger log = Scope.getCurrentScope().getLog(SpringLiquibase.class);
    protected String beanName;
    protected ResourceLoader resourceLoader;
    protected DataSource dataSource;
    protected String changeLog;
    protected String contexts;
    protected String labelFilter;
    protected String tag;
    protected Map<String, String> parameters;
    protected String defaultSchema;
    protected String liquibaseSchema;
    protected String databaseChangeLogTable;
    protected String databaseChangeLogLockTable;
    protected String liquibaseTablespace;
    protected boolean dropFirst;
    protected boolean clearCheckSums;
    protected boolean shouldRun = true;
    protected File rollbackFile;
    protected UpdateSummaryEnum showSummary;
    protected UpdateSummaryOutputEnum showSummaryOutput = UpdateSummaryOutputEnum.LOG;
    protected boolean testRollbackOnUpdate = false;
    protected UIServiceEnum uiService = UIServiceEnum.LOGGER;
    protected Customizer<Liquibase> customizer;

    public String getDatabaseProductName() throws DatabaseException {
        Connection connection = null;
        Database database = null;
        String name = "unknown";
        try {
            connection = this.getDataSource().getConnection();
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            name = database.getDatabaseProductName();
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            if (database != null) {
                database.close();
            } else if (connection != null) {
                try {
                    if (!connection.getAutoCommit()) {
                        connection.rollback();
                    }
                    connection.close();
                }
                catch (SQLException e) {
                    this.log.warning("problem closing connection", e);
                }
            }
        }
        return name;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getChangeLog() {
        return this.changeLog;
    }

    public void setChangeLog(String dataModel) {
        this.changeLog = dataModel;
    }

    @Deprecated
    public String getLabels() {
        return this.getLabelFilter();
    }

    @Deprecated
    public void setLabels(String labels) {
        this.setLabelFilter(labels);
    }

    public boolean isTestRollbackOnUpdate() {
        return this.testRollbackOnUpdate;
    }

    public void setTestRollbackOnUpdate(boolean testRollbackOnUpdate) {
        this.testRollbackOnUpdate = testRollbackOnUpdate;
    }

    public void afterPropertiesSet() throws LiquibaseException {
        ConfiguredValue<Boolean> shouldRunProperty = LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentConfiguredValue();
        if (!shouldRunProperty.getValue().booleanValue()) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Liquibase did not run because " + shouldRunProperty.getProvidedValue().describe() + " was set to false");
            return;
        }
        if (!this.shouldRun) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Liquibase did not run because 'shouldRun' property was set to false on " + this.getBeanName() + " Liquibase Spring bean.");
            return;
        }
        try {
            Scope.child(Scope.Attr.ui.name(), (Object)this.uiService.getUiServiceClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]), () -> {
                try (Liquibase liquibase = null;){
                    Connection c = this.getDataSource().getConnection();
                    liquibase = this.createLiquibase(c);
                    this.generateRollbackFile(liquibase);
                    this.performUpdate(liquibase);
                }
            });
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private void generateRollbackFile(Liquibase liquibase) throws LiquibaseException {
        if (this.rollbackFile != null) {
            try (OutputStream outputStream2 = Files.newOutputStream(this.rollbackFile.toPath(), new OpenOption[0]);
                 OutputStreamWriter output = new OutputStreamWriter(outputStream2, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());){
                if (this.tag != null) {
                    liquibase.futureRollbackSQL(this.tag, new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()), (Writer)output);
                } else {
                    liquibase.futureRollbackSQL(new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()), (Writer)output);
                }
            }
            catch (IOException e) {
                throw new LiquibaseException("Unable to generate rollback file.", e);
            }
        }
    }

    protected void performUpdate(Liquibase liquibase) throws LiquibaseException {
        if (this.isClearCheckSums()) {
            liquibase.clearCheckSums();
        }
        if (this.isTestRollbackOnUpdate()) {
            if (this.tag != null) {
                liquibase.updateTestingRollback(this.tag, new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()));
            } else {
                liquibase.updateTestingRollback(new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()));
            }
        } else if (this.tag != null) {
            liquibase.update(this.tag, new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()));
        } else {
            liquibase.update(new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()));
        }
    }

    protected Liquibase createLiquibase(Connection c) throws LiquibaseException {
        SpringResourceAccessor resourceAccessor = this.createResourceOpener();
        Liquibase liquibase = new Liquibase(this.getChangeLog(), (ResourceAccessor)resourceAccessor, this.createDatabase(c, resourceAccessor));
        if (this.parameters != null) {
            for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
                liquibase.setChangeLogParameter(entry.getKey(), entry.getValue());
            }
        }
        if (this.isDropFirst()) {
            liquibase.dropAll();
        }
        liquibase.setShowSummaryOutput(this.showSummaryOutput);
        liquibase.setShowSummary(this.showSummary);
        if (liquibase.getDatabase() instanceof DerbyDatabase) {
            ((DerbyDatabase)liquibase.getDatabase()).setShutdownEmbeddedDerby(false);
        }
        if (this.customizer != null) {
            this.customizer.customize(liquibase);
        }
        return liquibase;
    }

    protected Database createDatabase(Connection c, ResourceAccessor resourceAccessor) throws DatabaseException {
        DatabaseConnection liquibaseConnection;
        if (c == null) {
            this.log.warning("Null connection returned by liquibase datasource. Using offline unknown database");
            liquibaseConnection = new OfflineConnection("offline:unknown", resourceAccessor);
        } else {
            liquibaseConnection = new JdbcConnection(c);
        }
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(liquibaseConnection);
        if (StringUtil.trimToNull(this.defaultSchema) != null) {
            if (database.supports(Schema.class)) {
                database.setDefaultSchemaName(this.defaultSchema);
            } else if (database.supports(Catalog.class)) {
                database.setDefaultCatalogName(this.defaultSchema);
            }
        }
        if (StringUtil.trimToNull(this.liquibaseSchema) != null) {
            if (database.supports(Schema.class)) {
                database.setLiquibaseSchemaName(this.liquibaseSchema);
            } else if (database.supports(Catalog.class)) {
                database.setLiquibaseCatalogName(this.liquibaseSchema);
            }
        }
        if (StringUtil.trimToNull(this.liquibaseTablespace) != null && database.supportsTablespaces()) {
            database.setLiquibaseTablespaceName(this.liquibaseTablespace);
        }
        if (StringUtil.trimToNull(this.databaseChangeLogTable) != null) {
            database.setDatabaseChangeLogTableName(this.databaseChangeLogTable);
        }
        if (StringUtil.trimToNull(this.databaseChangeLogLockTable) != null) {
            database.setDatabaseChangeLogLockTableName(this.databaseChangeLogLockTable);
        }
        return database;
    }

    public void setChangeLogParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    protected SpringResourceAccessor createResourceOpener() {
        return new SpringResourceAccessor(this.resourceLoader);
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void setShowSummaryOutput(UpdateSummaryOutputEnum showSummaryOutput) {
        if (showSummaryOutput == null) {
            return;
        }
        this.showSummaryOutput = showSummaryOutput;
    }

    public void setUiService(UIServiceEnum uiService) {
        if (uiService == null) {
            return;
        }
        this.uiService = uiService;
    }

    public String toString() {
        return this.getClass().getName() + "(" + this.getResourceLoader().toString() + ")";
    }

    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getContexts() {
        return this.contexts;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public String getLabelFilter() {
        return this.labelFilter;
    }

    public void setLabelFilter(String labelFilter) {
        this.labelFilter = labelFilter;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDefaultSchema() {
        return this.defaultSchema;
    }

    public void setDefaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    public String getLiquibaseSchema() {
        return this.liquibaseSchema;
    }

    public void setLiquibaseSchema(String liquibaseSchema) {
        this.liquibaseSchema = liquibaseSchema;
    }

    public String getDatabaseChangeLogTable() {
        return this.databaseChangeLogTable;
    }

    public void setDatabaseChangeLogTable(String databaseChangeLogTable) {
        this.databaseChangeLogTable = databaseChangeLogTable;
    }

    public String getDatabaseChangeLogLockTable() {
        return this.databaseChangeLogLockTable;
    }

    public void setDatabaseChangeLogLockTable(String databaseChangeLogLockTable) {
        this.databaseChangeLogLockTable = databaseChangeLogLockTable;
    }

    public String getLiquibaseTablespace() {
        return this.liquibaseTablespace;
    }

    public void setLiquibaseTablespace(String liquibaseTablespace) {
        this.liquibaseTablespace = liquibaseTablespace;
    }

    public boolean isDropFirst() {
        return this.dropFirst;
    }

    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }

    public boolean isClearCheckSums() {
        return this.clearCheckSums;
    }

    public void setClearCheckSums(boolean clearCheckSums) {
        this.clearCheckSums = clearCheckSums;
    }

    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

    public void setRollbackFile(File rollbackFile) {
        this.rollbackFile = rollbackFile;
    }

    public void setShowSummary(UpdateSummaryEnum showSummary) {
        this.showSummary = showSummary;
    }

    public UIServiceEnum getUiService() {
        return this.uiService;
    }

    public Customizer<Liquibase> getCustomizer() {
        return this.customizer;
    }

    public void setCustomizer(Customizer<Liquibase> customizer) {
        this.customizer = customizer;
    }
}

