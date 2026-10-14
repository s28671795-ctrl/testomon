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
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.AbstractChangeLogBasedTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.util.FileUtils;

public class DatabaseUpdateTask
extends AbstractChangeLogBasedTask {
    private boolean dropFirst;
    private String toTag;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Writer writer = null;
        Liquibase liquibase = this.getLiquibase();
        try {
            FileResource outputFile = this.getOutputFile();
            if (outputFile != null) {
                writer = this.getOutputFileWriter();
                liquibase.update(this.toTag, new Contexts(this.getContexts()), this.getLabelFilter(), writer);
            } else {
                if (this.dropFirst) {
                    liquibase.dropAll();
                }
                liquibase.update(this.toTag, new Contexts(this.getContexts()), this.getLabelFilter());
            }
        }
        catch (LiquibaseException e) {
            try {
                throw new BuildException("Unable to update database: " + e.getMessage(), (Throwable)e);
                catch (UnsupportedEncodingException e2) {
                    throw new BuildException("Unable to generate update SQL. Encoding [" + this.getOutputEncoding() + "] is not supported.", (Throwable)e2);
                }
                catch (IOException e3) {
                    throw new BuildException("Unable to generate update SQL. Error creating output writer.", (Throwable)e3);
                }
            }
            catch (Throwable throwable) {
                FileUtils.close(writer);
                throw throwable;
            }
        }
        FileUtils.close((Writer)writer);
    }

    public boolean isDropFirst() {
        return this.dropFirst;
    }

    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }

    public String getToTag() {
        return this.toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }
}

