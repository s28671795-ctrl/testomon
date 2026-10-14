/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager.logic.actors;

import com.alibaba.fastjson.JSONObject;

public class InputParamsMessage {
    private final JSONObject params;

    public JSONObject getParams() {
        return this.params;
    }

    public InputParamsMessage(JSONObject params) {
        this.params = params;
    }
}

