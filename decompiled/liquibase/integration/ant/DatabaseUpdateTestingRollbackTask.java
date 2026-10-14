/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 */
package liquibase.integration.ant;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.AbstractChangeLogBasedTask;
import org.apache.tools.ant.BuildException;

public class DatabaseUpdateTestingRollbackTask
extends AbstractChangeLogBasedTask {
    private boolean dropFirst;

    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        try {
            if (this.isDropFirst()) {
                liquibase.dropAll();
            }
            liquibase.updateTestingRollback(new Contexts(this.getContexts()), this.getLabelFilter());
        }
        catch (LiquibaseException e) {
            throw new BuildException("Unable to update database with a rollback test: " + e.getMessage(), (Throwable)e);
        }
    }

    public boolean isDropFirst() {
        return this.dropFirst;
    }

    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }
}

