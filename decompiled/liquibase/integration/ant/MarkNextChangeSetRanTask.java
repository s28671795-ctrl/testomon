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
import java.io.Writer;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.AbstractChangeLogBasedTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.util.FileUtils;

public class MarkNextChangeSetRanTask
extends AbstractChangeLogBasedTask {
    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        Writer writer = null;
        try {
            FileResource outputFile = this.getOutputFile();
            if (outputFile != null) {
                writer = this.getOutputFileWriter();
                liquibase.markNextChangeSetRan(new Contexts(this.getContexts()), this.getLabelFilter(), writer);
            } else {
                liquibase.markNextChangeSetRan(new Contexts(this.getContexts()), this.getLabelFilter());
            }
        }
        catch (LiquibaseException e) {
            try {
                throw new BuildException("Unable to mark next changeset as ran: " + e.getMessage(), (Throwable)e);
                catch (IOException e2) {
                    throw new BuildException("Unable to mark next changeset as ran. Error creating output writer.", (Throwable)e2);
                }
            }
            catch (Throwable throwable) {
                FileUtils.close(writer);
                throw throwable;
            }
        }
        FileUtils.close((Writer)writer);
    }
}

