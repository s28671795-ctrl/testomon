/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.systemlogs;

import com.incoresoft.middleware.vms.systemlogs.SystemLogEvent;
import com.incoresoft.middleware.vms.systemlogs.SystemLogEventAction;

public interface SystemLogger {
    public void log(SystemLogEvent var1);

    public void log(SystemLogEventAction var1, String var2);

    public void log(SystemLogEventAction var1, String var2, Object var3);
}

