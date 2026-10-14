/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventmanager;

import java.util.Map;

public interface EventNode {
    public String getId();

    public String getExtensionId();

    public Map<String, String> getParams();
}

