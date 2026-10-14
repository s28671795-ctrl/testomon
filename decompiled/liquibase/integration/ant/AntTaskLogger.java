/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.Task
 */
package liquibase.integration.ant;

import java.util.logging.Level;
import liquibase.logging.LogMessageFilter;
import liquibase.logging.core.AbstractLogger;
import org.apache.tools.ant.Task;

public final class AntTaskLogger
extends AbstractLogger {
    private final Task task;

    @Deprecated
    public AntTaskLogger(Task task, LogMessageFilter ignored) {
        this(task);
    }

    public AntTaskLogger(Task task) {
        this.task = task;
    }

    @Override
    public void log(Level level, String message, Throwable e) {
        if (level == Level.OFF) {
            return;
        }
        int antLevel = level.intValue() == Level.SEVERE.intValue() ? 0 : (level.intValue() == Level.WARNING.intValue() ? 1 : (level.intValue() == Level.INFO.intValue() ? 2 : (level.intValue() == Level.CONFIG.intValue() ? 4 : (level.intValue() == Level.FINE.intValue() ? 4 : 4))));
        this.task.log(this.filterMessage(message), e, antLevel);
    }
}

