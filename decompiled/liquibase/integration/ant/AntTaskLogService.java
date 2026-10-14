/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.Task
 */
package liquibase.integration.ant;

import liquibase.integration.ant.AntTaskLogger;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogService;
import org.apache.tools.ant.Task;

public final class AntTaskLogService
extends AbstractLogService {
    private final AntTaskLogger logger;

    public AntTaskLogService(Task task) {
        this.logger = new AntTaskLogger(task);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public Logger getLog(Class clazz) {
        return this.logger;
    }

    @Override
    public void close() {
    }
}

