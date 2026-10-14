/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.systemlogs;

import com.incoresoft.middleware.vms.systemlogs.SystemLogEventCategory;
import com.incoresoft.middleware.vms.systemlogs.SystemLogLevel;

public interface SystemLogEventAction {
    public int getId();

    public String getName();

    public String getLocalizationKey();

    public SystemLogEventCategory getCategory();

    public SystemLogLevel getLevel();
}

