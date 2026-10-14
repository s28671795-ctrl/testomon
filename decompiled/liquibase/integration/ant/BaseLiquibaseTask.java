/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.AntClassLoader
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.Task
 *  org.apache.tools.ant.types.Path
 *  org.apache.tools.ant.types.Reference
 */
package liquibase.integration.ant;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import liquibase.GlobalConfiguration;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.integration.ant.AntResourceAccessor;
import liquibase.integration.ant.AntTaskLogService;
import liquibase.integration.ant.type.ChangeLogParametersType;
import liquibase.integration.ant.type.DatabaseType;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.SearchPathResourceAccessor;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

public abstract class BaseLiquibaseTask
extends Task {
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    private final Map<String, Object> scopeValues = new HashMap<String, Object>();
    private AntClassLoader classLoader;
    private Liquibase liquibase;
    private ResourceAccessor resourceAccessor;
    private Path classpath;
    private DatabaseType databaseType;
    private ChangeLogParametersType changeLogParameters;

    public void init() throws BuildException {
        this.scopeValues.put(Scope.Attr.logService.name(), new AntTaskLogService(this));
        this.classpath = new Path(this.getProject());
    }

    public final void execute() throws BuildException {
        super.execute();
        this.log(coreBundle.getString("starting.liquibase"), 2);
        this.classLoader = this.getProject().createClassLoader(this.classpath);
        this.classLoader.setParent(((Object)((Object)this)).getClass().getClassLoader());
        this.classLoader.setThreadContextLoader();
        this.validateParameters();
        Database[] database = new Database[]{null};
        try {
            this.resourceAccessor = this.createResourceAccessor(this.classLoader);
            this.scopeValues.put(Scope.Attr.resourceAccessor.name(), this.resourceAccessor);
            this.scopeValues.put(Scope.Attr.classLoader.name(), this.classLoader);
            Scope.child(this.scopeValues, () -> {
                database[0] = this.createDatabaseFromType(this.databaseType, this.resourceAccessor);
                this.liquibase = new Liquibase(this.getChangeLogFile(), this.resourceAccessor, database[0]);
                if (this.changeLogParameters != null) {
                    this.changeLogParameters.applyParameters(this.liquibase);
                }
                if (this.shouldRun()) {
                    this.executeWithLiquibaseClassloader();
                }
            });
        }
        catch (Exception e) {
            throw new BuildException("Unable to initialize Liquibase: " + e.getMessage(), (Throwable)e);
        }
        finally {
            this.closeDatabase(database[0]);
            this.classLoader.resetThreadContextLoader();
            this.classLoader.cleanup();
            this.classLoader = null;
        }
    }

    protected abstract void executeWithLiquibaseClassloader() throws BuildException;

    protected Database createDatabaseFromConfiguredDatabaseType() {
        return this.createDatabaseFromType(this.databaseType, this.getResourceAccessor());
    }

    protected Database createDatabaseFromType(DatabaseType databaseType, ResourceAccessor resourceAccessor) {
        return databaseType.createDatabase(resourceAccessor);
    }

    protected Liquibase getLiquibase() {
        return this.liquibase;
    }

    protected ResourceAccessor getResourceAccessor() {
        if (this.resourceAccessor == null) {
            throw new IllegalStateException("The ResourceAccessor has not been initialized. This usually means this method has been called before the task's execute method has called.");
        }
        return this.resourceAccessor;
    }

    public String getChangeLogDirectory() {
        return null;
    }

    public String getSearchPath() {
        return null;
    }

    protected String getChangeLogFile() {
        return null;
    }

    protected boolean shouldRun() {
        if (!LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentValue().booleanValue()) {
            this.log("Liquibase did not run because " + LiquibaseCommandLineConfiguration.SHOULD_RUN.getKey() + " was set to false", 2);
            return false;
        }
        return true;
    }

    protected String getDefaultOutputEncoding() {
        return GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
    }

    protected void validateParameters() {
        if (this.databaseType == null) {
            throw new BuildException("A database or databaseref is required.");
        }
    }

    private ResourceAccessor createResourceAccessor(AntClassLoader classLoader) {
        return new SearchPathResourceAccessor(this.getSearchPath(), new AntResourceAccessor(classLoader, this.getChangeLogDirectory()), new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader()));
    }

    protected void closeDatabase(Database database) {
        try {
            if (database != null) {
                database.close();
            }
        }
        catch (DatabaseException e) {
            this.log("Error closing the database connection.", e, 1);
        }
    }

    public Path createClasspath() {
        if (this.classpath == null) {
            this.classpath = new Path(this.getProject());
        }
        return this.classpath.createPath();
    }

    public void setClasspathRef(Reference r) {
        this.createClasspath().setRefid(r);
    }

    public void addDatabase(DatabaseType databaseType) {
        if (this.databaseType != null) {
            throw new BuildException("Only one <database> element is allowed.");
        }
        this.databaseType = databaseType;
    }

    public void setDatabaseRef(Reference databaseRef) {
        this.databaseType = new DatabaseType(this.getProject());
        this.databaseType.setRefid(databaseRef);
    }

    public void addChangeLogParameters(ChangeLogParametersType changeLogParameters) {
        if (this.changeLogParameters != null) {
            throw new BuildException("Only one <changeLogParameters> element is allowed.");
        }
        this.changeLogParameters = changeLogParameters;
    }

    public void setChangeLogParametersRef(Reference changeLogParametersRef) {
        this.changeLogParameters = new ChangeLogParametersType(this.getProject());
        this.changeLogParameters.setRefid(changeLogParametersRef);
    }

    public boolean isPromptOnNonLocalDatabase() {
        return false;
    }

    public void setPromptOnNonLocalDatabase(boolean promptOnNonLocalDatabase) {
        this.log("NOTE: The promptOnLocalDatabase functionality has been removed", 2);
    }
}

