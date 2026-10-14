/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.util.List;
import java.util.logging.Level;
import liquibase.logging.LogMessageFilter;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogger;

public class CompositeLogger
extends AbstractLogger {
    private final List<Logger> loggers;

    public CompositeLogger(List<Logger> loggers, LogMessageFilter filter2) {
        this(loggers);
    }

    public CompositeLogger(List<Logger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void close() throws Exception {
        for (Logger logger2 : this.loggers) {
            logger2.close();
        }
    }

    @Override
    public void log(Level level, String message, Throwable e) {
        if (level == Level.OFF) {
            return;
        }
        for (Logger logger2 : this.loggers) {
            logger2.log(level, message, e);
        }
    }
}

