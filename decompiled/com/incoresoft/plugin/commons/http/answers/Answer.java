/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.http.answers;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="Answer", description="Answer description")
public class Answer {
    public static final String OK = "ok";
    public static final String ERROR = "error";
    @Schema(name="status", description="Answer status", example="ok", allowableValues={"ok", "error"})
    protected String status;

    public Answer(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}

