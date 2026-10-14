/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.errors;

import com.alibaba.fastjson.JSONObject;

public interface Error {
    public String getLocaleKey();

    default public String getLocaleMessage(Object ... args2) {
        return new JSONObject().fluentPut("key", this.getLocaleKey()).fluentPut("objects", args2).toJSONString();
    }
}

