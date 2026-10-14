/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging;

import liquibase.logging.LogMessageFilter;
import liquibase.logging.Logger;
import liquibase.plugin.Plugin;

public interface LogService
extends Plugin {
    public int getPriority();

    public Logger getLog(Class var1);

    public void close();

    public LogMessageFilter getFilter();

    public void setFilter(LogMessageFilter var1);
}

