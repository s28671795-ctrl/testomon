/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogService;
import liquibase.logging.core.NoOpLogger;

public class NoOpLogService
extends AbstractLogService {
    private static final NoOpLogger NO_OP_LOGGER = new NoOpLogger();

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public Logger getLog(Class clazz) {
        return NO_OP_LOGGER;
    }
}

