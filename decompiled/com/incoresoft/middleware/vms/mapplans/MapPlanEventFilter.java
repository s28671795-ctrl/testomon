/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans;

import com.fasterxml.jackson.databind.JsonNode;
import com.incoresoft.middleware.model.user.User;
import java.util.Map;

public interface MapPlanEventFilter {
    public boolean isAvailable(User var1, Map<String, JsonNode> var2);
}

