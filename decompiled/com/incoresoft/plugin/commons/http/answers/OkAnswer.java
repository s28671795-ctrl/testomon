/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import com.incoresoft.plugin.commons.http.answers.Answer;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="OkAnswer", description="OK Answer")
public class OkAnswer
extends Answer {
    @Schema(name="data", description="Data object", example="null", nullable=true, implementation=String.class)
    private Object data;

    public OkAnswer() {
        super("ok");
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

