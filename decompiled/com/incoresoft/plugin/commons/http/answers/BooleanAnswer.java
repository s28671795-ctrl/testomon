/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.plugin.commons.http.answers.Answer;

public class BooleanAnswer
extends Answer {
    protected boolean data;

    public BooleanAnswer(boolean string) {
        super("ok");
        this.data = string;
    }

    public String toString() {
        return new JSONObject(true).fluentPut("status", this.status).fluentPut("data", this.data).toJSONString();
    }

    public boolean getData() {
        return this.data;
    }
}

