/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 */
package liquibase.integration.ant;

import java.io.IOException;
import java.io.Writer;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.AbstractChangeLogBasedTask;
import org.apache.tools.ant.BuildException;

public class DatabaseRollbackFutureTask
extends AbstractChangeLogBasedTask {
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        try (Writer outputFileWriter = this.getOutputFileWriter();){
            liquibase.futureRollbackSQL(new Contexts(this.getContexts()), this.getLabelFilter(), outputFileWriter);
        }
        catch (LiquibaseException e) {
            throw new BuildException("Unable to generate future rollback SQL: " + e.getMessage(), (Throwable)e);
        }
        catch (IOException e) {
            throw new BuildException("Unable to generate future rollback SQL. Error creating output writer.", (Throwable)e);
        }
    }

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (this.getOutputFile() == null) {
            throw new BuildException("Output file is required.");
        }
    }
}

