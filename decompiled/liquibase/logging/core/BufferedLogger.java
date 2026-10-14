/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.util.logging.Level;
import liquibase.logging.LogMessageFilter;
import liquibase.logging.core.AbstractLogger;
import liquibase.logging.core.BufferedLogService;

@Deprecated
public class BufferedLogger
extends AbstractLogger {
    private final BufferedLogService bufferedLogService;
    private final Class clazz;

    public BufferedLogger(Class clazz, BufferedLogService bufferedLogService, LogMessageFilter ignored) {
        this(clazz, bufferedLogService);
    }

    public BufferedLogger(Class clazz, BufferedLogService bufferedLogService) {
        this.clazz = clazz;
        this.bufferedLogService = bufferedLogService;
    }

    @Override
    public void log(Level level, String message, Throwable e) {
        if (level == Level.OFF) {
            return;
        }
        this.bufferedLogService.addLog(new BufferedLogService.BufferedLogMessage(level, this.clazz, this.filterMessage(message), e));
    }

    @Override
    public void close() throws Exception {
    }
}

