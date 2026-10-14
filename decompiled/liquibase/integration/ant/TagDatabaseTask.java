/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 */
package liquibase.integration.ant;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.BaseLiquibaseTask;
import liquibase.util.StringUtil;
import org.apache.tools.ant.BuildException;

public class TagDatabaseTask
extends BaseLiquibaseTask {
    private String tag;

    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Liquibase liquibase = this.getLiquibase();
        try {
            liquibase.tag(this.tag);
        }
        catch (LiquibaseException e) {
            throw new BuildException("Unable to tag database: " + e.getMessage(), (Throwable)e);
        }
    }

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (StringUtil.trimToNull(this.tag) == null) {
            throw new BuildException("Unable to tag database. The tag attribute is required.");
        }
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

