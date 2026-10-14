/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.logging.LogMessageFilter;
import liquibase.logging.core.AbstractLogger;

public class JavaLogger
extends AbstractLogger {
    private final String className;
    private final Logger logger;

    @Deprecated
    public JavaLogger(Logger logger2, LogMessageFilter filter2) {
        this(logger2);
    }

    public JavaLogger(Logger logger2) {
        this.logger = logger2;
        this.className = logger2.getName();
    }

    @Override
    public void log(Level level, String message, Throwable e) {
        if (level.equals(Level.OFF)) {
            return;
        }
        if (!this.logger.isLoggable(level)) {
            return;
        }
        this.logger.logp(level, this.className, null, message, e);
    }
}

