/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.incoresoft.plugin.commons.http.answers.Answer;

public class ObjectAnswer<T>
extends Answer {
    protected T data;

    public ObjectAnswer(T data) {
        super("ok");
        this.data = data;
    }

    public T getData() {
        return this.data;
    }
}

