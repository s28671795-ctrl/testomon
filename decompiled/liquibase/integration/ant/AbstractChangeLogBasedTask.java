/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.types.resources.FileResource
 */
package liquibase.integration.ant;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import liquibase.LabelExpression;
import liquibase.integration.ant.BaseLiquibaseTask;
import liquibase.util.StringUtil;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;

public abstract class AbstractChangeLogBasedTask
extends BaseLiquibaseTask {
    private String searchPath;
    private String changeLogDirectory;
    private String changeLogFile;
    private String contexts;
    private LabelExpression labelFilter;
    private FileResource outputFile;
    private String outputEncoding;

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (this.changeLogFile == null) {
            throw new BuildException("Change log file is required.");
        }
    }

    protected Writer getOutputFileWriter() throws IOException {
        return new OutputStreamWriter(this.outputFile.getOutputStream(), this.getOutputEncoding());
    }

    @Override
    public String getChangeLogDirectory() {
        return this.changeLogDirectory;
    }

    @Override
    public String getSearchPath() {
        return this.searchPath;
    }

    @Override
    public String getChangeLogFile() {
        return this.changeLogFile;
    }

    public LabelExpression getLabels() {
        return this.getLabelFilter();
    }

    public void setLabels(String labelFilter) {
        this.setLabelFilter(labelFilter);
    }

    public void setLabelFilter(String labelFilter) {
        this.labelFilter = new LabelExpression(labelFilter);
    }

    public String getOutputEncoding() {
        return StringUtil.trimToNull(this.outputEncoding) == null ? this.getDefaultOutputEncoding() : this.outputEncoding.trim();
    }

    public void setSearchPath(String searchPath) {
        this.searchPath = searchPath;
    }

    public void setChangeLogDirectory(String changeLogDirectory) {
        this.changeLogDirectory = changeLogDirectory;
    }

    public void setChangeLogFile(String changeLogFile) {
        this.changeLogFile = changeLogFile;
    }

    public String getContexts() {
        return this.contexts;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public LabelExpression getLabelFilter() {
        return this.labelFilter;
    }

    public FileResource getOutputFile() {
        return this.outputFile;
    }

    public void setOutputFile(FileResource outputFile) {
        this.outputFile = outputFile;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }
}

