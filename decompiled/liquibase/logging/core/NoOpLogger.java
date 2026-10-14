/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.util.logging.Level;
import liquibase.logging.core.AbstractLogger;

public class NoOpLogger
extends AbstractLogger {
    @Override
    public void log(Level level, String message, Throwable e) {
    }
}

