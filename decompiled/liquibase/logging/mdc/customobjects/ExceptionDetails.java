/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.io.PrintWriter;
import java.io.StringWriter;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.util.LiquibaseUtil;

public class ExceptionDetails
implements CustomMdcObject {
    private String primaryException;
    private String primaryExceptionReason;
    private String primaryExceptionSource;
    private String exception;

    public ExceptionDetails() {
    }

    public ExceptionDetails(Throwable exception, String source2) {
        Throwable primaryException;
        for (primaryException = exception; primaryException != null && primaryException.getCause() != null; primaryException = primaryException.getCause()) {
        }
        if (primaryException != null) {
            if (primaryException instanceof LiquibaseException || source2 == null) {
                source2 = LiquibaseUtil.getBuildVersionInfo();
            }
            this.primaryException = primaryException.getClass().getSimpleName();
            this.primaryExceptionReason = primaryException.getMessage();
            this.primaryExceptionSource = source2;
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exception.printStackTrace(printWriter);
            this.exception = stringWriter.toString();
        }
    }

    public String getFormattedPrimaryException() {
        return this.getPrimaryException() != null ? String.format("ERROR: Exception Primary Class:  %s", this.getPrimaryException()) : "";
    }

    public String getFormattedPrimaryExceptionReason() {
        return this.getPrimaryExceptionReason() != null ? String.format("ERROR: Exception Primary Reason:  %s", this.getPrimaryExceptionReason()) : "";
    }

    public String getFormattedPrimaryExceptionSource() {
        return this.getPrimaryExceptionSource() != null ? String.format("ERROR: Exception Primary Source:  %s", this.getPrimaryExceptionSource()) : "";
    }

    public static String findSource(Database database) {
        try {
            String source2;
            try {
                source2 = String.format("%s %s", database.getDatabaseProductName(), database.getDatabaseProductVersion());
            }
            catch (DatabaseException dbe) {
                source2 = database.getDatabaseProductName();
            }
            return source2;
        }
        catch (RuntimeException ignored) {
            return database.getDisplayName();
        }
    }

    public String getPrimaryException() {
        return this.primaryException;
    }

    public String getPrimaryExceptionReason() {
        return this.primaryExceptionReason;
    }

    public String getPrimaryExceptionSource() {
        return this.primaryExceptionSource;
    }

    public String getException() {
        return this.exception;
    }

    public void setPrimaryException(String primaryException) {
        this.primaryException = primaryException;
    }

    public void setPrimaryExceptionReason(String primaryExceptionReason) {
        this.primaryExceptionReason = primaryExceptionReason;
    }

    public void setPrimaryExceptionSource(String primaryExceptionSource) {
        this.primaryExceptionSource = primaryExceptionSource;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}

