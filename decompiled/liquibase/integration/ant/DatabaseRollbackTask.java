/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.types.resources.FileResource
 *  org.apache.tools.ant.util.DateUtils
 *  org.apache.tools.ant.util.FileUtils
 */
package liquibase.integration.ant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.AbstractChangeLogBasedTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.FileUtils;

public class DatabaseRollbackTask
extends AbstractChangeLogBasedTask {
    private Date rollbackDate;
    private String rollbackTag;
    private Integer rollbackCount;
    private String rollbackScript;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {
        Writer writer;
        block14: {
            writer = null;
            Liquibase liquibase = this.getLiquibase();
            try {
                FileResource outputFile = this.getOutputFile();
                if (this.rollbackCount != null) {
                    if (outputFile != null) {
                        writer = this.getOutputFileWriter();
                        liquibase.rollback(this.rollbackCount, this.rollbackScript, new Contexts(this.getContexts()), this.getLabelFilter(), writer);
                        break block14;
                    } else {
                        liquibase.rollback((int)this.rollbackCount, this.rollbackScript, new Contexts(this.getContexts()), this.getLabelFilter());
                    }
                    break block14;
                }
                if (this.rollbackTag != null) {
                    if (outputFile != null) {
                        writer = this.getOutputFileWriter();
                        liquibase.rollback(this.rollbackTag, this.rollbackScript, new Contexts(this.getContexts()), this.getLabelFilter(), writer);
                        break block14;
                    } else {
                        liquibase.rollback(this.rollbackTag, this.rollbackScript, new Contexts(this.getContexts()), this.getLabelFilter());
                    }
                    break block14;
                }
                if (this.rollbackDate == null) throw new BuildException("Unable to rollback database. No count, tag, or date set.");
                if (outputFile != null) {
                    writer = this.getOutputFileWriter();
                    liquibase.rollback(this.rollbackDate, this.rollbackScript, new Contexts(this.getContexts()), this.getLabelFilter(), writer);
                } else {
                    liquibase.rollback(this.rollbackDate, this.rollbackScript, new Contexts(this.getContexts()), this.getLabelFilter());
                }
            }
            catch (LiquibaseException e) {
                try {
                    throw new BuildException("Unable to rollback database: " + e.getMessage(), (Throwable)e);
                    catch (UnsupportedEncodingException e2) {
                        throw new BuildException("Unable to generate rollback SQL. Encoding [" + this.getOutputEncoding() + "] is not supported.", (Throwable)e2);
                    }
                    catch (IOException e3) {
                        throw new BuildException("Unable to generate rollback SQL. Error creating output writer.", (Throwable)e3);
                    }
                }
                catch (Throwable throwable) {
                    FileUtils.close(writer);
                    throw throwable;
                }
            }
        }
        FileUtils.close((Writer)writer);
    }

    public Date getRollbackDate() {
        if (this.rollbackDate == null) {
            return null;
        }
        return new Date(this.rollbackDate.getTime());
    }

    public void setRollbackDate(String rollbackDateStr) {
        if (this.rollbackTag != null || this.rollbackCount != null) {
            throw new BuildException("Unable to rollback database. A tag or count has already been set.");
        }
        try {
            this.rollbackDate = DateUtils.parseIso8601DateTimeOrDate((String)rollbackDateStr);
        }
        catch (ParseException e) {
            throw new BuildException("Unable to parse rollback date/time string into a Date object. Please make sure the date or date/time is in ISO 8601 format (yyyy-MM-dd or yyyy-MM-ddTHH:mm:ss).", (Throwable)e);
        }
    }

    public String getRollbackTag() {
        return this.rollbackTag;
    }

    public void setRollbackTag(String rollbackTag) {
        if (this.rollbackDate != null || this.rollbackCount != null) {
            throw new BuildException("Unable to rollback database. A date or count has already been set.");
        }
        this.rollbackTag = rollbackTag;
    }

    public Integer getRollbackCount() {
        return this.rollbackCount;
    }

    public void setRollbackCount(Integer rollbackCount) {
        if (this.rollbackDate != null || this.rollbackTag != null) {
            throw new BuildException("Unable to rollback database. A date or tag has already been set.");
        }
        this.rollbackCount = rollbackCount;
    }

    public String getRollbackScript() {
        return this.rollbackScript;
    }

    public void setRollbackScript(String rollbackScript) {
        this.rollbackScript = rollbackScript;
    }
}

