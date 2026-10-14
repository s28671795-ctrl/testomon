/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans;

import com.fasterxml.jackson.databind.JsonNode;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.vms.mapplans.MapPlanEvent;
import java.util.Map;

public interface MapPlanEventFactory {
    public MapPlanEvent create(User var1, Map<String, JsonNode> var2);
}

