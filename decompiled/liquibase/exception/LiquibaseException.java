/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.util.logging.Level;

public class LiquibaseException
extends Exception {
    private static final long serialVersionUID = 1L;
    private String timestamp;
    private String details;
    private Level logLevel = null;

    public LiquibaseException() {
    }

    public LiquibaseException(String message) {
        super(message);
    }

    public LiquibaseException(String message, Level logLevel) {
        super(message);
        this.logLevel = logLevel;
    }

    public LiquibaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LiquibaseException(Throwable cause) {
        super(cause);
    }

    public Level getLogLevel() {
        return this.logLevel;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

