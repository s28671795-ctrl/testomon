/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.plugin.commons.params.models.FileFormat;

public class ExportParams {
    @JSONField(name="format", defaultValue="json")
    private FileFormat format;
    @JSONField(name="language")
    private String language;

    public FileFormat getFormat() {
        return this.format;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setFormat(FileFormat format) {
        this.format = format;
    }

    public void setLanguage(String language2) {
        this.language = language2;
    }

    public String toString() {
        return "ExportParams(format=" + String.valueOf((Object)this.getFormat()) + ", language=" + this.getLanguage() + ")";
    }
}

