/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.types.resources.FileResource
 *  org.apache.tools.ant.util.FileUtils
 */
package liquibase.integration.ant;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.StandardObjectChangeFilter;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.integration.ant.BaseLiquibaseTask;
import liquibase.integration.ant.type.ChangeLogOutputFile;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.ChangeLogSerializerFactory;
import liquibase.serializer.core.json.JsonChangeLogSerializer;
import liquibase.serializer.core.string.StringChangeLogSerializer;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.util.FileUtils;

public class GenerateChangeLogTask
extends BaseLiquibaseTask {
    private final Set<ChangeLogOutputFile> changeLogOutputFiles = new LinkedHashSet<ChangeLogOutputFile>();
    private boolean includeSchema = true;
    private boolean includeCatalog = true;
    private boolean includeTablespace = true;
    private String includeObjects;
    private String excludeObjects;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        Database database = liquibase.getDatabase();
        CatalogAndSchema catalogAndSchema = this.buildCatalogAndSchema(database);
        DiffOutputControl diffOutputControl = this.getDiffOutputControl();
        DiffToChangeLog diffToChangeLog = new DiffToChangeLog(diffOutputControl);
        Iterator<ChangeLogOutputFile> iterator2 = this.changeLogOutputFiles.iterator();
        while (iterator2.hasNext()) {
            ChangeLogOutputFile changeLogOutputFile = iterator2.next();
            String encoding = this.getOutputEncoding(changeLogOutputFile);
            PrintStream printStream = null;
            try {
                FileResource outputFile = changeLogOutputFile.getOutputFile();
                ChangeLogSerializer changeLogSerializer = changeLogOutputFile.getChangeLogSerializer();
                this.log("Writing change log file " + outputFile.toString(), 2);
                printStream = new PrintStream(outputFile.getOutputStream(), true, encoding);
                liquibase.generateChangeLog(catalogAndSchema, diffToChangeLog, printStream, changeLogSerializer, new Class[0]);
            }
            catch (UnsupportedEncodingException e) {
                try {
                    throw new BuildException("Unable to generate a change log. Encoding [" + encoding + "] is not supported.", (Throwable)e);
                    catch (IOException e2) {
                        throw new BuildException("Unable to generate a change log. Error creating output stream.", (Throwable)e2);
                    }
                    catch (DatabaseException e3) {
                        throw new BuildException("Unable to generate a change log: " + e3.getMessage(), (Throwable)e3);
                    }
                    catch (CommandExecutionException e4) {
                        throw new BuildException("Unable to generate a change log, command not found: " + e4.getMessage(), (Throwable)e4);
                    }
                }
                catch (Throwable throwable) {
                    FileUtils.close(printStream);
                    throw throwable;
                }
            }
            FileUtils.close((OutputStream)printStream);
        }
        return;
    }

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (this.changeLogOutputFiles.isEmpty()) {
            throw new BuildException("Unable to generate a change log. No output file defined. Add at least one <xml>, <json>, <yaml>, or <txt> nested element.");
        }
    }

    private String getOutputEncoding(ChangeLogOutputFile changeLogOutputFile) {
        String encoding = changeLogOutputFile.getEncoding();
        return encoding == null ? this.getDefaultOutputEncoding() : encoding;
    }

    private CatalogAndSchema buildCatalogAndSchema(Database database) {
        return new CatalogAndSchema(database.getDefaultCatalogName(), database.getDefaultSchemaName());
    }

    private DiffOutputControl getDiffOutputControl() {
        DiffOutputControl diffOutputControl = new DiffOutputControl(this.includeCatalog, this.includeSchema, this.includeTablespace, null);
        if (this.excludeObjects != null && this.includeObjects != null) {
            throw new UnexpectedLiquibaseException("Cannot specify both excludeObjects and includeObjects");
        }
        if (this.excludeObjects != null) {
            diffOutputControl.setObjectChangeFilter(new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.EXCLUDE, this.excludeObjects));
        }
        if (this.includeObjects != null) {
            diffOutputControl.setObjectChangeFilter(new StandardObjectChangeFilter(StandardObjectChangeFilter.FilterType.INCLUDE, this.includeObjects));
        }
        return diffOutputControl;
    }

    public void addConfiguredJson(ChangeLogOutputFile changeLogOutputFile) {
        changeLogOutputFile.setChangeLogSerializer(new JsonChangeLogSerializer());
        this.changeLogOutputFiles.add(changeLogOutputFile);
    }

    public void addConfiguredXml(ChangeLogOutputFile changeLogOutputFile) {
        changeLogOutputFile.setChangeLogSerializer(ChangeLogSerializerFactory.getInstance().getSerializer("xml"));
        this.changeLogOutputFiles.add(changeLogOutputFile);
    }

    public void addConfiguredYaml(ChangeLogOutputFile changeLogOutputFile) {
        changeLogOutputFile.setChangeLogSerializer(ChangeLogSerializerFactory.getInstance().getSerializer("yaml"));
        this.changeLogOutputFiles.add(changeLogOutputFile);
    }

    public void addConfiguredTxt(ChangeLogOutputFile changeLogOutputFile) {
        changeLogOutputFile.setChangeLogSerializer(new StringChangeLogSerializer());
        this.changeLogOutputFiles.add(changeLogOutputFile);
    }

    public boolean getIncludeCatalog() {
        return this.includeCatalog;
    }

    public void setIncludeCatalog(boolean includeCatalog) {
        this.includeCatalog = includeCatalog;
    }

    public boolean getIncludeSchema() {
        return this.includeSchema;
    }

    public void setIncludeSchema(boolean includeSchema) {
        this.includeSchema = includeSchema;
    }

    public boolean getIncludeTablespace() {
        return this.includeTablespace;
    }

    public void setIncludeTablespace(boolean includeTablespace) {
        this.includeTablespace = includeTablespace;
    }

    public String getIncludeObjects() {
        return this.includeObjects;
    }

    public void setIncludeObjects(String includeObjects) {
        this.includeObjects = includeObjects;
    }

    public String getExcludeObjects() {
        return this.excludeObjects;
    }

    public void setExcludeObjects(String excludeObjects) {
        this.excludeObjects = excludeObjects;
    }
}

