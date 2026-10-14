/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.ResourceLoader
 */
package liquibase.integration.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class MultiTenantSpringLiquibase
implements InitializingBean,
ResourceLoaderAware {
    private final List<DataSource> dataSources = new ArrayList<DataSource>();
    private String jndiBase;
    private DataSource dataSource;
    private List<String> schemas;
    private ResourceLoader resourceLoader;
    private String changeLog;
    private String contexts;
    private String labelFilter;
    private Map<String, String> parameters;
    private String defaultSchema;
    private String liquibaseSchema;
    private String liquibaseTablespace;
    private String databaseChangeLogTable;
    private String databaseChangeLogLockTable;
    private boolean dropFirst;
    private boolean clearCheckSums;
    private boolean shouldRun = true;
    private File rollbackFile;

    public void afterPropertiesSet() throws Exception {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        if (this.dataSource != null || this.schemas != null) {
            if (this.dataSource == null && this.schemas != null) {
                throw new LiquibaseException("When schemas are defined you should also define a base dataSource");
            }
            if (this.dataSource != null) {
                log.info("Schema based multitenancy enabled");
                if (this.schemas == null || this.schemas.isEmpty()) {
                    log.warning("Schemas not defined, using defaultSchema only");
                    this.schemas = new ArrayList<String>();
                    this.schemas.add(this.defaultSchema);
                }
                this.runOnAllSchemas();
            }
        } else {
            log.info("DataSources based multitenancy enabled");
            this.resolveDataSources();
            this.runOnAllDataSources();
        }
    }

    private void resolveDataSources() throws NamingException {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        InitialContext context = new InitialContext();
        int lastIndexOf = this.jndiBase.lastIndexOf("/");
        String jndiRoot = this.jndiBase.substring(0, lastIndexOf);
        String jndiParent = this.jndiBase.substring(lastIndexOf + 1);
        Context base = (Context)context.lookup(jndiRoot);
        NamingEnumeration<NameClassPair> list = base.list(jndiParent);
        while (list.hasMoreElements()) {
            NameClassPair entry = (NameClassPair)list.nextElement();
            String name = entry.getName();
            String jndiUrl = entry.isRelative() ? this.jndiBase + "/" + name : name;
            Object lookup = context.lookup(jndiUrl);
            if (lookup instanceof DataSource) {
                this.dataSources.add((DataSource)lookup);
                log.fine("Added a data source at " + jndiUrl);
                continue;
            }
            log.info("Skipping a resource " + jndiUrl + " not compatible with DataSource.");
        }
    }

    private void runOnAllDataSources() throws LiquibaseException {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        for (DataSource aDataSource : this.dataSources) {
            log.info("Initializing Liquibase for data source " + aDataSource);
            SpringLiquibase liquibase = this.getSpringLiquibase(aDataSource);
            liquibase.afterPropertiesSet();
            log.info("Liquibase ran for data source " + aDataSource);
        }
    }

    private void runOnAllSchemas() throws LiquibaseException {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        for (String schema : this.schemas) {
            if ("default".equals(schema)) {
                schema = null;
            }
            log.info("Initializing Liquibase for schema " + schema);
            SpringLiquibase liquibase = this.getSpringLiquibase(this.dataSource);
            liquibase.setDefaultSchema(schema);
            liquibase.afterPropertiesSet();
            log.info("Liquibase ran for schema " + schema);
        }
    }

    private SpringLiquibase getSpringLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(this.changeLog);
        liquibase.setChangeLogParameters(this.parameters);
        liquibase.setContexts(this.contexts);
        liquibase.setLabelFilter(this.labelFilter);
        liquibase.setDropFirst(this.dropFirst);
        liquibase.setClearCheckSums(this.clearCheckSums);
        liquibase.setShouldRun(this.shouldRun);
        liquibase.setRollbackFile(this.rollbackFile);
        liquibase.setResourceLoader(this.resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(this.defaultSchema);
        liquibase.setLiquibaseSchema(this.liquibaseSchema);
        liquibase.setLiquibaseTablespace(this.liquibaseTablespace);
        liquibase.setDatabaseChangeLogTable(this.databaseChangeLogTable);
        liquibase.setDatabaseChangeLogLockTable(this.databaseChangeLogLockTable);
        return liquibase;
    }

    @Deprecated
    public String getLabels() {
        return this.getLabelFilter();
    }

    @Deprecated
    public void setLabels(String labels) {
        this.setLabelFilter(labels);
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getJndiBase() {
        return this.jndiBase;
    }

    public void setJndiBase(String jndiBase) {
        this.jndiBase = jndiBase;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<String> getSchemas() {
        return this.schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    public String getChangeLog() {
        return this.changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
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

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
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

    public String getLiquibaseTablespace() {
        return this.liquibaseTablespace;
    }

    public void setLiquibaseTablespace(String liquibaseTablespace) {
        this.liquibaseTablespace = liquibaseTablespace;
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

    public boolean isShouldRun() {
        return this.shouldRun;
    }

    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

    public File getRollbackFile() {
        return this.rollbackFile;
    }

    public void setRollbackFile(File rollbackFile) {
        this.rollbackFile = rollbackFile;
    }
}

