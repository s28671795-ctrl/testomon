/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.Scope;
import liquibase.logging.LogService;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogService;
import liquibase.logging.core.CompositeLogger;

public class CompositeLogService
extends AbstractLogService {
    private List<LogService> services = new ArrayList<LogService>();

    public CompositeLogService() {
    }

    public CompositeLogService(boolean includeCurrentScopeLogService, LogService ... logService) {
        this.services = new ArrayList<LogService>(Arrays.asList(logService));
        if (includeCurrentScopeLogService) {
            this.services.add((LogService)((Object)Scope.getCurrentScope().get((Enum)Scope.Attr.logService, LogService.class)));
        }
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public Logger getLog(Class clazz) {
        ArrayList<Logger> loggers = new ArrayList<Logger>();
        for (LogService service2 : this.services) {
            loggers.add(service2.getLog(clazz));
        }
        return new CompositeLogger(loggers);
    }
}

