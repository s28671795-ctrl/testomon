/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.configuration.ConfiguredValue;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.integration.servlet.GenericServletWrapper;
import liquibase.integration.servlet.ServletConfigurationValueProvider;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.SearchPathResourceAccessor;
import liquibase.util.NetUtil;
import liquibase.util.StringUtil;

abstract class GenericServletListener {
    private static final String LIQUIBASE_CHANGELOG = "liquibase.changelog";
    private static final String LIQUIBASE_CONTEXTS = "liquibase.contexts";
    private static final String LIQUIBASE_LABELS = "liquibase.labels";
    private static final String LIQUIBASE_LABEL_FILTER = "liquibase.labelFilter";
    private static final String LIQUIBASE_DATASOURCE = "liquibase.datasource";
    private static final String LIQUIBASE_HOST_EXCLUDES = "liquibase.host.excludes";
    private static final String LIQUIBASE_HOST_INCLUDES = "liquibase.host.includes";
    private static final String LIQUIBASE_ONERROR_FAIL = "liquibase.onerror.fail";
    private static final String LIQUIBASE_PARAMETER = "liquibase.parameter";
    private static final String LIQUIBASE_SCHEMA_DEFAULT = "liquibase.schema.default";
    private String changeLogFile;
    private String dataSourceName;
    private String contexts;
    private String labelFilter;
    private String defaultSchema;
    private String hostName;

    GenericServletListener() {
    }

    public String getChangeLogFile() {
        return this.changeLogFile;
    }

    public void setChangeLogFile(String changeLogFile) {
        this.changeLogFile = changeLogFile;
    }

    public String getContexts() {
        return this.contexts;
    }

    public void setContexts(String ctxt) {
        this.contexts = ctxt;
    }

    @Deprecated
    public String getLabels() {
        return this.getLabelFilter();
    }

    @Deprecated
    public void setLabels(String labels) {
        this.setLabelFilter(labels);
    }

    public String getLabelFilter() {
        return this.labelFilter;
    }

    public void setLabelFilter(String labelFilter) {
        this.labelFilter = labelFilter;
    }

    public String getDataSource() {
        return this.dataSourceName;
    }

    public void setDataSource(String dataSource) {
        this.dataSourceName = dataSource;
    }

    public String getDefaultSchema() {
        return this.defaultSchema;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void contextInitialized(GenericServletWrapper.ServletContext servletContext) {
        LiquibaseConfiguration liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
        try {
            this.hostName = NetUtil.getLocalHostName();
        }
        catch (Exception e) {
            servletContext.log("Cannot find hostname: " + e.getMessage());
            return;
        }
        InitialContext ic = null;
        String failOnError = null;
        ServletConfigurationValueProvider servletConfigurationValueProvider = new ServletConfigurationValueProvider(servletContext, ic);
        try {
            ic = new InitialContext();
            liquibaseConfiguration.registerProvider(servletConfigurationValueProvider);
            failOnError = (String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_ONERROR_FAIL).getValue();
            if (this.checkPreconditions(servletContext)) {
                this.executeUpdate(servletContext, ic);
            }
        }
        catch (Exception e) {
            if (!"false".equals(failOnError)) {
                throw new RuntimeException(e);
            }
        }
        finally {
            if (ic != null) {
                try {
                    ic.close();
                }
                catch (NamingException namingException) {}
            }
            liquibaseConfiguration.removeProvider(servletConfigurationValueProvider);
        }
    }

    private boolean checkPreconditions(GenericServletWrapper.ServletContext servletContext) {
        if (!LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentValue().booleanValue()) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Liquibase did not run on " + this.hostName + " because " + LiquibaseCommandLineConfiguration.SHOULD_RUN.getKey() + " was set to false");
            return false;
        }
        LiquibaseConfiguration liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
        String machineIncludes = (String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_HOST_INCLUDES).getValue();
        String machineExcludes = (String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_HOST_EXCLUDES).getValue();
        boolean shouldRun = false;
        if (machineIncludes == null && machineExcludes == null) {
            shouldRun = true;
        } else if (machineIncludes != null) {
            for (String machine : machineIncludes.split(",")) {
                if (!this.hostName.equalsIgnoreCase(machine = machine.trim())) continue;
                shouldRun = true;
                break;
            }
        } else if (machineExcludes != null) {
            shouldRun = true;
            for (String machine : machineExcludes.split(",")) {
                if (!this.hostName.equalsIgnoreCase(machine = machine.trim())) continue;
                shouldRun = false;
                break;
            }
        }
        ConfiguredValue<Boolean> shouldRunValue = LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentConfiguredValue();
        if (LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentValue().booleanValue() && !shouldRunValue.wasDefaultValueUsed()) {
            shouldRun = true;
            servletContext.log("ignoring liquibase.host.includes and liquibase.host.excludes, since " + shouldRunValue.getProvidedValue().describe() + "=true");
        }
        if (!shouldRun) {
            servletContext.log(this.getClass().getSimpleName() + " did not run due to " + LIQUIBASE_HOST_INCLUDES + " and/or " + LIQUIBASE_HOST_EXCLUDES + "");
            return false;
        }
        return true;
    }

    private void executeUpdate(GenericServletWrapper.ServletContext servletContext, InitialContext ic) throws NamingException, SQLException, LiquibaseException {
        LiquibaseConfiguration liquibaseConfiguration = Scope.getCurrentScope().getSingleton(LiquibaseConfiguration.class);
        this.setDataSource((String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_DATASOURCE).getValue());
        if (this.getDataSource() == null) {
            throw new RuntimeException("Cannot run Liquibase, liquibase.datasource is not set");
        }
        this.setChangeLogFile((String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_CHANGELOG).getValue());
        if (this.getChangeLogFile() == null) {
            throw new RuntimeException("Cannot run Liquibase, liquibase.changelog is not set");
        }
        this.setContexts((String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_CONTEXTS).getValue());
        this.setLabelFilter((String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_LABEL_FILTER).getValue());
        if (this.getLabelFilter() == null) {
            this.setLabelFilter((String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_LABELS).getValue());
        }
        this.defaultSchema = StringUtil.trimToNull((String)liquibaseConfiguration.getCurrentConfiguredValue(null, null, LIQUIBASE_SCHEMA_DEFAULT).getValue());
        Connection connection = null;
        Database database = null;
        Liquibase liquibase = null;
        try {
            DataSource dataSource = (DataSource)ic.lookup(this.dataSourceName);
            connection = dataSource.getConnection();
            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            ClassLoaderResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);
            ClassLoaderResourceAccessor clFO = new ClassLoaderResourceAccessor();
            DirectoryResourceAccessor fsFO = new DirectoryResourceAccessor(new File("."));
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(this.getDefaultSchema());
            liquibase = new Liquibase(this.getChangeLogFile(), (ResourceAccessor)new SearchPathResourceAccessor(clFO, fsFO, threadClFO), database);
            Enumeration<String> initParameters = servletContext.getInitParameterNames();
            while (initParameters.hasMoreElements()) {
                String name = initParameters.nextElement().trim();
                if (!name.startsWith("liquibase.parameter.")) continue;
                liquibase.setChangeLogParameter(name.substring(LIQUIBASE_PARAMETER.length() + 1), liquibaseConfiguration.getCurrentConfiguredValue(null, null, name));
            }
            liquibase.update(new Contexts(this.getContexts()), new LabelExpression(this.getLabelFilter()));
            if (database instanceof DerbyDatabase) {
                ((DerbyDatabase)database).setShutdownEmbeddedDerby(false);
            }
        }
        catch (IOException e) {
            throw new LiquibaseException(e);
        }
        finally {
            if (liquibase != null) {
                liquibase.close();
            } else if (connection != null) {
                connection.close();
            }
        }
    }
}

