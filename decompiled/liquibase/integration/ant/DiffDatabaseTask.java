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
import liquibase.GlobalConfiguration;
import liquibase.diff.DiffResult;
import liquibase.diff.output.report.DiffToReport;
import liquibase.exception.DatabaseException;
import liquibase.integration.ant.AbstractDatabaseDiffTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.util.FileUtils;

public class DiffDatabaseTask
extends AbstractDatabaseDiffTask {
    private FileResource outputFile;
    private String outputEncoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(this.outputFile.getOutputStream(), true, this.getOutputEncoding());
            DiffResult diffResult = this.getDiffResult();
            DiffToReport diffReport = new DiffToReport(diffResult, printStream);
            this.log("Writing diff report " + this.outputFile.toString(), 2);
            diffReport.print();
        }
        catch (DatabaseException e) {
            try {
                throw new BuildException("Unable to make diff report: " + e.getMessage(), (Throwable)e);
                catch (UnsupportedEncodingException e2) {
                    throw new BuildException("Unable to make diff report. Encoding [" + this.outputEncoding + "] is not supported.", (Throwable)e2);
                }
                catch (IOException e3) {
                    throw new BuildException("Unable to make diff report. Error opening output stream.", (Throwable)e3);
                }
            }
            catch (Throwable throwable) {
                FileUtils.close(printStream);
                throw throwable;
            }
        }
        FileUtils.close((OutputStream)printStream);
    }

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (this.outputFile == null) {
            throw new BuildException("Unable to make diff report. Output file is required.");
        }
    }

    public void setOutputFile(FileResource outputFile) {
        this.outputFile = outputFile;
    }

    public String getOutputEncoding() {
        return this.outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }
}

