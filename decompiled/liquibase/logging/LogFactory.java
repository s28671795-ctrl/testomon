/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging;

import liquibase.Scope;
import liquibase.logging.LogService;
import liquibase.logging.Logger;
import liquibase.plugin.AbstractPluginFactory;

public class LogFactory
extends AbstractPluginFactory<LogService> {
    private LogFactory() {
    }

    @Override
    protected Class<LogService> getPluginClass() {
        return LogService.class;
    }

    @Override
    protected int getPriority(LogService logService, Object ... args2) {
        return logService.getPriority();
    }

    @Deprecated
    public static Logger getLogger(String ignored) {
        return Scope.getCurrentScope().getLog(LogFactory.class);
    }

    @Deprecated
    public static Logger getLogger() {
        return Scope.getCurrentScope().getLog(LogFactory.class);
    }

    public static LogFactory getInstance() {
        return Scope.getCurrentScope().getSingleton(LogFactory.class);
    }
}

