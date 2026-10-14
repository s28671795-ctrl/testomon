/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.plugin.commons.http.answers.OkAnswer;

public class StringAnswer
extends OkAnswer {
    protected String data;

    public StringAnswer(String string) {
        this.data = string;
    }

    public String toString() {
        return new JSONObject(true).fluentPut("status", this.status).fluentPut("data", this.data).toJSONString();
    }

    @Override
    public String getData() {
        return this.data;
    }
}

