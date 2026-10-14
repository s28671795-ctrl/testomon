/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventmanager;

import java.util.Map;

public interface Event {
    public String getId();

    public Map<String, String> getParams();
}

