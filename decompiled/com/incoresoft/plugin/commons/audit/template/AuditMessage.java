/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template;

import java.util.Map;

public abstract class AuditMessage {
    protected abstract String getTemplateId();

    public Object build() {
        return Map.of("template", this.getTemplateId(), "args", this);
    }
}

