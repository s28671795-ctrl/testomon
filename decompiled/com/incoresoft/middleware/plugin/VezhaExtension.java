/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.plugin;

import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.permission.PluginPermissions;
import com.incoresoft.middleware.plugin.ExtensionContext;
import com.incoresoft.middleware.plugin.SystemExtension;
import org.pf4j.Extension;

@Extension
public abstract class VezhaExtension
extends SystemExtension<ExtensionContext> {
    public abstract PluginPermissions customPermissions();

    public abstract Permission getViewAnalyticsPermission();

    public abstract Permission getManageAnalyticsPermission();
}

