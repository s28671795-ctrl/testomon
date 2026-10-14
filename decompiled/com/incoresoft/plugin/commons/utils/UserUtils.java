/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.model.user.User;
import io.javalin.http.ForbiddenResponse;

public class UserUtils {
    private UserUtils() {
        throw new AssertionError((Object)"this class should never be instantiated");
    }

    public static JSONObject userToInfoJSON(User user) {
        return new JSONObject().fluentPut("id", user.getId());
    }

    public static void requireRoot(User user) {
        if (!user.isRoot()) {
            throw new ForbiddenResponse();
        }
    }
}

