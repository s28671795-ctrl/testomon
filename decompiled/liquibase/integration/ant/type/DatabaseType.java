/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.Project
 *  org.apache.tools.ant.types.DataType
 *  org.apache.tools.ant.types.Reference
 */
package liquibase.integration.ant.type;

import java.util.Map;
import java.util.Properties;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.DatabaseException;
import liquibase.integration.ant.type.ConnectionProperties;
import liquibase.resource.ResourceAccessor;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.Reference;

public class DatabaseType
extends DataType {
    private static final String USER_PROPERTY_NAME = "user";
    private static final String PASSWORD_PROPERTY_NAME = "password";
    private String driver;
    private String url;
    private String user;
    private String password;
    private ConnectionProperties connectionProperties;
    private String defaultSchemaName;
    private String defaultCatalogName;
    private String currentDateTimeFunction;
    private boolean outputDefaultSchema = true;
    private boolean outputDefaultCatalog = true;
    private String liquibaseSchemaName;
    private String liquibaseCatalogName;
    private String databaseClass;
    private String databaseChangeLogTableName;
    private String databaseChangeLogLockTableName;
    private String liquibaseTablespaceName;

    public DatabaseType(Project project) {
        this.setProject(project);
    }

    public Database createDatabase(ResourceAccessor resourceAccessor) {
        this.logParameters();
        this.validateParameters();
        try {
            String connLiquibaseTablespaceName;
            String connDatabaseChangeLogLockTableName;
            String connDatabaseChangeLogTableName;
            String connLiquibaseCatalogName;
            String dbmsCurrentDateTimeFunction;
            String catalogName;
            ConnectionProperties dbConnectionProperties;
            String connectionPassword;
            DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
            Properties connectionProps = new Properties();
            String connectionUserName = this.getUser();
            if (connectionUserName != null && !connectionUserName.isEmpty()) {
                connectionProps.setProperty(USER_PROPERTY_NAME, connectionUserName);
            }
            if ((connectionPassword = this.getPassword()) != null && !connectionPassword.isEmpty()) {
                connectionProps.setProperty(PASSWORD_PROPERTY_NAME, connectionPassword);
            }
            if ((dbConnectionProperties = this.getConnectionProperties()) != null) {
                connectionProps.putAll((Map<?, ?>)dbConnectionProperties.buildProperties());
            }
            Database database = databaseFactory.openDatabase(this.getUrl(), this.getUser(), this.getDriver(), this.getDatabaseClass(), connectionProps, resourceAccessor);
            String schemaName = this.getDefaultSchemaName();
            if (schemaName != null) {
                database.setDefaultSchemaName(schemaName);
            }
            if ((catalogName = this.getDefaultCatalogName()) != null) {
                database.setDefaultCatalogName(catalogName);
            }
            if ((dbmsCurrentDateTimeFunction = this.getCurrentDateTimeFunction()) != null) {
                database.setCurrentDateTimeFunction(dbmsCurrentDateTimeFunction);
            }
            database.setOutputDefaultSchema(this.isOutputDefaultSchema());
            database.setOutputDefaultCatalog(this.isOutputDefaultCatalog());
            String connLiquibaseSchemaName = this.getLiquibaseSchemaName();
            if (this.liquibaseSchemaName != null) {
                database.setLiquibaseSchemaName(connLiquibaseSchemaName);
            }
            if ((connLiquibaseCatalogName = this.getLiquibaseCatalogName()) != null) {
                database.setLiquibaseCatalogName(connLiquibaseCatalogName);
            }
            if ((connDatabaseChangeLogTableName = this.getDatabaseChangeLogTableName()) != null) {
                database.setDatabaseChangeLogTableName(connDatabaseChangeLogTableName);
            }
            if ((connDatabaseChangeLogLockTableName = this.getDatabaseChangeLogLockTableName()) != null) {
                database.setDatabaseChangeLogLockTableName(connDatabaseChangeLogLockTableName);
            }
            if ((connLiquibaseTablespaceName = this.getLiquibaseTablespaceName()) != null) {
                database.setLiquibaseTablespaceName(connLiquibaseTablespaceName);
            }
            return database;
        }
        catch (DatabaseException e) {
            throw new BuildException("Unable to create Liquibase database instance. " + e, (Throwable)e);
        }
    }

    private void validateParameters() {
        if (this.getUrl() == null) {
            throw new BuildException("JDBC URL is required.");
        }
    }

    private void logParameters() {
        this.log("Creating Liquibase Database", 4);
        this.log("JDBC driver: " + this.driver, 4);
        this.log("JDBC URL: " + this.url, 4);
        this.log("JDBC username: " + this.user, 4);
        this.log("Default catalog name: " + this.defaultCatalogName, 4);
        this.log("Default schema name: " + this.defaultSchemaName, 4);
        this.log("Liquibase catalog name: " + this.liquibaseCatalogName, 4);
        this.log("Liquibase schema name: " + this.liquibaseSchemaName, 4);
        this.log("Liquibase tablespace name: " + this.liquibaseTablespaceName, 4);
        this.log("Database changelog table name: " + this.databaseChangeLogTableName, 4);
        this.log("Database changelog lock table name: " + this.databaseChangeLogLockTableName, 4);
        this.log("Output default catalog: " + this.outputDefaultCatalog, 4);
        this.log("Output default schema: " + this.outputDefaultSchema, 4);
        this.log("Current date/time function: " + this.currentDateTimeFunction, 4);
        this.log("Database class: " + this.databaseClass, 4);
    }

    public void setRefid(Reference ref) {
        if (this.driver != null || this.url != null || this.user != null || this.password != null || this.defaultSchemaName != null || this.defaultCatalogName != null || this.currentDateTimeFunction != null || this.databaseClass != null || this.liquibaseSchemaName != null || this.liquibaseCatalogName != null || this.databaseChangeLogTableName != null || this.databaseChangeLogLockTableName != null || this.liquibaseTablespaceName != null) {
            throw this.tooManyAttributes();
        }
        super.setRefid(ref);
    }

    public String getDriver() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getDriver() : this.driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getUrl() : this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getUser() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getUser() : this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getPassword() : this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ConnectionProperties getConnectionProperties() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getConnectionProperties() : this.connectionProperties;
    }

    public void addConnectionProperties(ConnectionProperties connectionProperties) {
        if (this.connectionProperties != null) {
            throw new BuildException("Only one <connectionProperties> element is allowed.");
        }
        this.connectionProperties = connectionProperties;
    }

    public String getDefaultSchemaName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getDefaultSchemaName() : this.defaultSchemaName;
    }

    public void setDefaultSchemaName(String defaultSchemaName) {
        this.defaultSchemaName = defaultSchemaName;
    }

    public String getDefaultCatalogName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getDefaultCatalogName() : this.defaultCatalogName;
    }

    public void setDefaultCatalogName(String defaultCatalogName) {
        this.defaultCatalogName = defaultCatalogName;
    }

    public String getCurrentDateTimeFunction() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getCurrentDateTimeFunction() : this.currentDateTimeFunction;
    }

    public void setCurrentDateTimeFunction(String currentDateTimeFunction) {
        this.currentDateTimeFunction = currentDateTimeFunction;
    }

    public boolean isOutputDefaultSchema() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).isOutputDefaultSchema() : this.outputDefaultSchema;
    }

    public void setOutputDefaultSchema(boolean outputDefaultSchema) {
        this.outputDefaultSchema = outputDefaultSchema;
    }

    public boolean isOutputDefaultCatalog() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).isOutputDefaultCatalog() : this.outputDefaultCatalog;
    }

    public void setOutputDefaultCatalog(boolean outputDefaultCatalog) {
        this.outputDefaultCatalog = outputDefaultCatalog;
    }

    public String getDatabaseClass() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getDatabaseClass() : this.databaseClass;
    }

    public void setDatabaseClass(String databaseClass) {
        this.databaseClass = databaseClass;
    }

    public String getLiquibaseSchemaName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getLiquibaseSchemaName() : this.liquibaseSchemaName;
    }

    public void setLiquibaseSchemaName(String liquibaseSchemaName) {
        this.liquibaseSchemaName = liquibaseSchemaName;
    }

    public String getLiquibaseCatalogName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getLiquibaseCatalogName() : this.liquibaseCatalogName;
    }

    public void setLiquibaseCatalogName(String liquibaseCatalogName) {
        this.liquibaseCatalogName = liquibaseCatalogName;
    }

    public String getDatabaseChangeLogTableName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getDatabaseChangeLogTableName() : this.databaseChangeLogTableName;
    }

    public void setDatabaseChangeLogTableName(String databaseChangeLogTableName) {
        this.databaseChangeLogTableName = databaseChangeLogTableName;
    }

    public String getDatabaseChangeLogLockTableName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getDatabaseChangeLogLockTableName() : this.databaseChangeLogLockTableName;
    }

    public void setDatabaseChangeLogLockTableName(String databaseChangeLogLockTableName) {
        this.databaseChangeLogLockTableName = databaseChangeLogLockTableName;
    }

    public String getLiquibaseTablespaceName() {
        return this.isReference() ? ((DatabaseType)((Object)this.getCheckedRef())).getLiquibaseTablespaceName() : this.liquibaseTablespaceName;
    }

    public void setLiquibaseTablespaceName(String liquibaseTablespaceName) {
        this.liquibaseTablespaceName = liquibaseTablespaceName;
    }
}

