/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.types.resources.FileResource
 */
package liquibase.integration.ant;

import java.io.File;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.BaseLiquibaseTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;

public class DBDocTask
extends BaseLiquibaseTask {
    private FileResource outputDirectory;
    private String changeLog;
    private String contexts;

    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        boolean success;
        File outputDirFile = this.outputDirectory.getFile();
        if (!outputDirFile.exists() && !(success = outputDirFile.mkdirs())) {
            throw new BuildException("Unable to create output directory.");
        }
        if (!outputDirFile.isDirectory()) {
            throw new BuildException("Output path is not a directory.");
        }
        Liquibase liquibase = this.getLiquibase();
        try {
            if (this.contexts != null) {
                liquibase.generateDocumentation(this.outputDirectory.toString(), this.contexts);
            } else {
                liquibase.generateDocumentation(this.outputDirectory.toString());
            }
        }
        catch (LiquibaseException e) {
            throw new BuildException("Liquibase encountered an error while creating database documentation. " + e, (Throwable)e);
        }
    }

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (this.changeLog == null) {
            throw new BuildException("Change log is required.");
        }
        if (this.outputDirectory == null) {
            throw new BuildException("Output directory is required.");
        }
    }

    @Override
    protected String getChangeLogFile() {
        return this.changeLog;
    }

    public void setChangeLogFile(String changeLog) {
        this.changeLog = changeLog;
    }

    public FileResource getOutputDirectory() {
        return this.outputDirectory;
    }

    public void setOutputDirectory(FileResource outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }
}

