/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import liquibase.logging.LogMessageFilter;
import liquibase.logging.LogService;

public abstract class AbstractLogService
implements LogService {
    @Override
    public void close() {
    }

    @Override
    @Deprecated
    public LogMessageFilter getFilter() {
        return null;
    }

    @Override
    @Deprecated
    public void setFilter(LogMessageFilter filter2) {
    }
}

