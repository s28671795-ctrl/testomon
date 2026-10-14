/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers.Ws;

import com.incoresoft.plugin.commons.http.answers.Ws.WsAnswer;

public class WsObjectAnswer<T>
extends WsAnswer {
    protected T data;

    public WsObjectAnswer(String method2, T data) {
        super("ok", method2);
        this.data = data;
    }

    public WsObjectAnswer(T data) {
        super("ok");
        this.data = data;
    }

    public T getData() {
        return this.data;
    }
}

