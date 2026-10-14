/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers.Ws;

import com.incoresoft.plugin.commons.http.answers.Answer;

public class WsAnswer
extends Answer {
    protected String method;

    public WsAnswer(String method2) {
        super("ok");
        this.method = method2;
    }

    public WsAnswer(String status, String method2) {
        super(status);
        this.method = method2;
    }

    public String getMethod() {
        return this.method;
    }
}

