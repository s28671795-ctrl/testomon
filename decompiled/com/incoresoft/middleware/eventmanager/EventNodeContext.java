/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventmanager;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.eventmanager.EventNode;

public interface EventNodeContext {
    public EventNode getCurrentNode();

    public JSONObject getInputParams();
}

