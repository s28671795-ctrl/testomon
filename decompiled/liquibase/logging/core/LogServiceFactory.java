/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import liquibase.logging.LogService;
import liquibase.plugin.AbstractPluginFactory;

public class LogServiceFactory
extends AbstractPluginFactory<LogService> {
    private LogServiceFactory() {
    }

    @Override
    protected Class<LogService> getPluginClass() {
        return LogService.class;
    }

    @Override
    protected int getPriority(LogService obj, Object ... args2) {
        return obj.getPriority();
    }

    public LogService getDefaultLogService() {
        return (LogService)this.getPlugin(new Object[0]);
    }
}

