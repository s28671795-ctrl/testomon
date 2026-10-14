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
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.AbstractChangeLogBasedTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.util.FileUtils;

public class ChangeLogSyncToTagTask
extends AbstractChangeLogBasedTask {
    private String toTag;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        OutputStreamWriter writer = null;
        try {
            FileResource outputFile = this.getOutputFile();
            if (outputFile != null) {
                writer = new OutputStreamWriter(outputFile.getOutputStream(), this.getOutputEncoding());
                liquibase.changeLogSync(this.toTag, new Contexts(this.getContexts()), this.getLabelFilter(), writer);
            } else {
                liquibase.changeLogSync(this.toTag, new Contexts(this.getContexts()), this.getLabelFilter());
            }
        }
        catch (UnsupportedEncodingException e) {
            try {
                throw new BuildException("Unable to generate sync SQL. Encoding [" + this.getOutputEncoding() + "] is not supported.", (Throwable)e);
                catch (IOException e2) {
                    throw new BuildException("Unable to generate sync SQL. Error creating output writer.", (Throwable)e2);
                }
                catch (LiquibaseException e3) {
                    throw new BuildException("Unable to sync change log: " + e3.getMessage(), (Throwable)e3);
                }
            }
            catch (Throwable throwable) {
                FileUtils.close(writer);
                throw throwable;
            }
        }
        FileUtils.close((Writer)writer);
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }

    public String getToTag() {
        return this.toTag;
    }
}

